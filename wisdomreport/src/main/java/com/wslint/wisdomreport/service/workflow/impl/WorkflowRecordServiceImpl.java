package com.wslint.wisdomreport.service.workflow.impl;

import static com.wslint.wisdomreport.common.WorkflowControl.getRecordNextStatusByOperation;
import static com.wslint.wisdomreport.common.WorkflowControl.getRecordOperationNameByCode;
import static com.wslint.wisdomreport.common.WorkflowControl.getRecordStatusByOperation;
import static com.wslint.wisdomreport.common.WorkflowControl.getRecordStatusNameByCode;

import com.wslint.wisdomreport.common.WorkflowControl;
import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.constant.ReturnConstant;
import com.wslint.wisdomreport.constant.WorkflowConstant;
import com.wslint.wisdomreport.dao.data.DataRecordDao;
import com.wslint.wisdomreport.dao.workflow.WorkflowRecordDao;
import com.wslint.wisdomreport.domain.dto.data.record.DataRecordDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowRecordTaskDTO;
import com.wslint.wisdomreport.exception.WorkflowException;
import com.wslint.wisdomreport.service.workflow.IWorkflowRecordService;
import com.wslint.wisdomreport.utils.CommonUtils;
import com.wslint.wisdomreport.utils.DataUtils;
import com.wslint.wisdomreport.utils.WorkflowUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 走工作流服务实现
 *
 * @author yuxr
 * @since 2018/11/13 14:33
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WorkflowRecordServiceImpl implements IWorkflowRecordService {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowRecordServiceImpl.class);

  @Autowired WorkflowRecordDao workflowRecordDao;
  @Autowired DataRecordDao dataRecordDao;

  /**
   * 走工作流接口
   *
   * @param workflowRecordTaskDTOList 待处理数据
   * @return 处理结果
   */
  @Override
  public int doWorkflow(List<WorkflowRecordTaskDTO> workflowRecordTaskDTOList) throws Exception {
    List<DataRecordDTO> dataRecordDTOList =
        dataRecordDao.getRecordsByRecordIds(workflowRecordTaskDTOList);
    Map dataMap = CommonUtils.getMapByKeyFromList(Constant.PROPERTY_ID, dataRecordDTOList);
    Map<Integer, Integer> logMap = new HashMap<>();
    List<Long> backUpIds = new ArrayList<>();
    for (WorkflowRecordTaskDTO workflowData : workflowRecordTaskDTOList) {
      Integer op = workflowData.getOperation();
      logMap.merge(op, 1, (oldValue, newValue) -> oldValue + 1);
      int status = getRecordStatusByOperation(op);
      int nextStatus = getRecordNextStatusByOperation(op);
      DataRecordDTO dataRecordDTO = (DataRecordDTO) dataMap.get(workflowData.getRecordId());
      if (!checkWorkflowRight(dataRecordDTO, status)) {
        LOGGER.error("id = {} 的记录数据工作流权限校验失败!", workflowData.getRecordId());
        throw new WorkflowException("工作流执行失败!");
      }
      completeRecordTask(workflowData, dataRecordDTO, status, nextStatus);
      completeRecordData(dataRecordDTO, workflowData, nextStatus);
      if (nextStatus == WorkflowConstant.STATUS_END) {
        backUpIds.add(workflowData.getRecordId());
      }
    }
    LOGGER.info(
        "记录工作流开始, 操作人: {}-{} ",
        CommonUtils.getCurrentUser().getUserCode(),
        CommonUtils.getCurrentUser().getUserName());
    logMap.forEach(
        (key, value) ->
            LOGGER.info(" -- {}-{} 数据 {} 条", key, getRecordOperationNameByCode(key), value));

    if (!workflowRecordDao.batchInsertWorkflowTask(workflowRecordTaskDTOList)) {
      LOGGER.error("工作流数据保存失败!");
      throw new WorkflowException("工作流执行失败!");
    }
    if (!dataRecordDao.batchUpdateRecordData(dataRecordDTOList)) {
      LOGGER.error("记录数据状态更新失败!");
      throw new WorkflowException("工作流执行失败!");
    }
    if (!backUpIds.isEmpty()) {
      LOGGER.info(" -- 记录工作流数据备份迁移 {} 条", backUpIds.size());
      if (!backupWorkflowByOrderId(backUpIds)) {
        LOGGER.error("记录工作流数据备份失败!");
        throw new WorkflowException("工作流执行失败!");
      }
    }
    LOGGER.info("记录工作流完成!");
    return ReturnConstant.WORKFLOW_SUCCESS;
  }

  /**
   * 追溯工作流数据
   *
   * @param recordId 记录数据id
   * @return 工作流数据
   */
  @Override
  public List<WorkflowRecordTaskDTO> trace(Long recordId) {
    DataRecordDTO dataRecordDTO = dataRecordDao.getRecordByRecordId(recordId);
    if (dataRecordDTO == null) {
      LOGGER.error("追溯数据不存在!");
      return new ArrayList<>();
    }
    List<WorkflowRecordTaskDTO> workflowRecordTaskDTOList;
    if (dataRecordDTO.getStatus() == WorkflowConstant.STATUS_END) {
      workflowRecordTaskDTOList = workflowRecordDao.getWorkflowCompleteTasksByRecordId(recordId);
    } else {
      workflowRecordTaskDTOList = workflowRecordDao.getWorkflowCurrentTasksByRecordId(recordId);
    }
    workflowRecordTaskDTOList.forEach(
        task -> {
          task.setImgUrl(dataRecordDTO.getImgUrl());
          task.setOperationName(getRecordOperationNameByCode(task.getOperation()));
          task.setStatusName(getRecordStatusNameByCode(task.getStatus()));
          task.setNextStatusName(getRecordStatusNameByCode(task.getNextStatus()));
        });
    return workflowRecordTaskDTOList;
  }

  /**
   * 封装记录工作流数据
   *
   * @param recordTaskDTO 工作流数据
   * @param dataRecordDTO 数据对象
   */
  private void completeRecordTask(
      WorkflowRecordTaskDTO recordTaskDTO,
      DataRecordDTO dataRecordDTO,
      Integer status,
      Integer nextStatus) {
    WorkflowUtils.completeWorkflowTask(recordTaskDTO, status, nextStatus);
    recordTaskDTO.setOldData(dataRecordDTO.getData());
    recordTaskDTO.setOldRemark(dataRecordDTO.getRemark());
    recordTaskDTO.setOldRemarker(dataRecordDTO.getRemarker());
    recordTaskDTO.setOldRemarkTime(dataRecordDTO.getRemarkTime());
    if (recordTaskDTO.getOperation() == WorkflowConstant.RECORD_OPERATION_REVIEW_DIRECT) {
      recordTaskDTO.setOperator(recordTaskDTO.getNextOperator());
      recordTaskDTO.setNextOperator(null);
    }
  }

  /**
   * 封装记录数据
   *
   * @param dataRecordDTO 记录数据
   */
  private void completeRecordData(
      DataRecordDTO dataRecordDTO, WorkflowRecordTaskDTO recordTaskDTO, Integer nextStatus) {
    // 只有修改操作才允许修改数据
    boolean isEdit = WorkflowControl.isEditRecordData(recordTaskDTO.getOperation());
    String newData = recordTaskDTO.getNewData();
    if (isEdit && newData != null && !newData.isEmpty()) {
      dataRecordDTO.setData(newData);
    }
    dataRecordDTO.setRemark(null);
    dataRecordDTO.setRemarker(null);
    dataRecordDTO.setRemarkTime(null);
    dataRecordDTO.setStatus(nextStatus);
    dataRecordDTO.setNextOperator(recordTaskDTO.getNextOperator());
    dataRecordDTO.setModifyUser(recordTaskDTO.getOperator());
    dataRecordDTO.setGmtModified(CommonUtils.getNowTime());
  }

  /**
   * 检查待走工作流数据的权限
   *
   * @param dataRecordDTO 待校验数据
   * @param status 核对状态
   * @return 是否满足工作流条件
   */
  private boolean checkWorkflowRight(DataRecordDTO dataRecordDTO, int status) {
    if (dataRecordDTO == null) {
      LOGGER.error("对应数据不存在!");
      return false;
    }
    if (dataRecordDTO.getStatus() != status) {
      LOGGER.error(
          "状态校验失败! 当前数据状态为:{}-{}, 需要状态为:{}-{}",
          dataRecordDTO.getStatus(),
          getRecordStatusNameByCode(dataRecordDTO.getStatus()),
          status,
          getRecordStatusNameByCode(status));
      return false;
    }
    return DataUtils.checkWorkflowRight(dataRecordDTO);
  }

  /**
   * 备份记录相关工作流的数据
   *
   * @param backUpIds 待备份数据ids
   * @return 是否备份成功
   */
  private boolean backupWorkflowByOrderId(List<Long> backUpIds) {
    if (!workflowRecordDao.insertCompleteFromCurrentByRecordIds(backUpIds)) {
      return false;
    }
    return workflowRecordDao.deleteCurrentByRecordIds(backUpIds);
  }
}
