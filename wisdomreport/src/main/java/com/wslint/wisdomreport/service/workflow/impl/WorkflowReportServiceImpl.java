package com.wslint.wisdomreport.service.workflow.impl;

import static com.wslint.wisdomreport.common.WorkflowControl.getReportNextStatusByOperation;
import static com.wslint.wisdomreport.common.WorkflowControl.getReportOperationNameByCode;
import static com.wslint.wisdomreport.common.WorkflowControl.getReportStatusByOperation;
import static com.wslint.wisdomreport.common.WorkflowControl.getReportStatusNameByCode;

import com.wslint.wisdomreport.common.WorkflowControl;
import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.constant.ReturnConstant;
import com.wslint.wisdomreport.constant.WorkflowConstant;
import com.wslint.wisdomreport.dao.data.DataReportDao;
import com.wslint.wisdomreport.dao.workflow.WorkflowReportDao;
import com.wslint.wisdomreport.domain.dto.data.report.DataReportDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowReportTaskDTO;
import com.wslint.wisdomreport.exception.WorkflowException;
import com.wslint.wisdomreport.service.workflow.IWorkflowReportService;
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
public class WorkflowReportServiceImpl implements IWorkflowReportService {

  /** logger 日志报告 */
  private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowReportServiceImpl.class);

  @Autowired private WorkflowReportDao workflowReportDao;
  @Autowired private DataReportDao dataReportDao;

  /**
   * 走工作流接口
   *
   * @param workflowReportTaskDTOList 待处理数据
   * @return 处理结果
   */
  @Override
  public int doWorkflow(List<WorkflowReportTaskDTO> workflowReportTaskDTOList) throws Exception {
    List<DataReportDTO> dataReportDTOList =
        dataReportDao.getReportsByReportIds(workflowReportTaskDTOList);
    Map dataMap = CommonUtils.getMapByKeyFromList(Constant.PROPERTY_ID, dataReportDTOList);
    Map<Integer, Integer> logMap = new HashMap<>();
    List<Long> backUpIds = new ArrayList<>();
    for (WorkflowReportTaskDTO workflowData : workflowReportTaskDTOList) {
      Integer op = workflowData.getOperation();
      logMap.merge(op, 1, (oldValue, newValue) -> oldValue + 1);
      int status = getReportStatusByOperation(op);
      int nextStatus = getReportNextStatusByOperation(op);
      DataReportDTO dataReportDTO = (DataReportDTO) dataMap.get(workflowData.getReportId());
      if (!checkWorkflowRight(dataReportDTO, status)) {
        LOGGER.error("id = {} 的报告数据工作流权限校验失败!", workflowData.getReportId());
        throw new WorkflowException("工作流执行失败!");
      }
      completeReportTask(workflowData, dataReportDTO.getData(), status, nextStatus);
      completeReportData(dataReportDTO, workflowData, nextStatus);
      if (nextStatus == WorkflowConstant.STATUS_END) {
        backUpIds.add(workflowData.getReportId());
      }
    }
    LOGGER.info(
        "报告工作流开始, 操作人: {}-{} ",
        CommonUtils.getCurrentUser().getUserCode(),
        CommonUtils.getCurrentUser().getUserName());

    logMap.forEach(
        (key, value) ->
            LOGGER.info(" -- {}-{} 数据 {} 条", key, getReportOperationNameByCode(key), value));

    if (!workflowReportDao.batchInsertWorkflowTask(workflowReportTaskDTOList)) {
      LOGGER.error("工作流数据保存失败!");
      throw new WorkflowException("工作流执行失败!");
    }
    if (!dataReportDao.batchUpdateReportData(dataReportDTOList)) {
      LOGGER.error("报告数据状态更新失败!");
      throw new WorkflowException("工作流执行失败!");
    }
    if (!backUpIds.isEmpty()) {
      LOGGER.info(" -- 报告工作流数据备份迁移 {} 条", backUpIds.size());
      if (!backupWorkflowByOrderId(backUpIds)) {
        LOGGER.error("报告工作流数据备份失败!");
        throw new WorkflowException("工作流执行失败!");
      }
    }
    LOGGER.info("报告工作流完成!");
    return ReturnConstant.WORKFLOW_SUCCESS;
  }

  /**
   * 追溯工作流数据
   *
   * @param reportId 报告数据id
   * @return 工作流数据
   */
  @Override
  public List<WorkflowReportTaskDTO> trace(Long reportId) {
    DataReportDTO dataReportDTO = dataReportDao.getReportByReportId(reportId);
    if (dataReportDTO == null) {
      LOGGER.error("追溯数据不存在!");
      return new ArrayList<>();
    }
    List<WorkflowReportTaskDTO> workflowReportTaskDTOList;
    if (dataReportDTO.getStatus() == WorkflowConstant.STATUS_END) {
      workflowReportTaskDTOList = workflowReportDao.getWorkflowCompleteTasksByReportId(reportId);
    } else {
      workflowReportTaskDTOList = workflowReportDao.getWorkflowCurrentTasksByReportId(reportId);
    }
    workflowReportTaskDTOList.forEach(
        task -> {
          task.setImgUrl(dataReportDTO.getImgUrl());
          task.setOperationName(getReportOperationNameByCode(task.getOperation()));
          task.setStatusName(getReportStatusNameByCode(task.getStatus()));
          task.setNextStatusName(getReportStatusNameByCode(task.getNextStatus()));
        });
    return workflowReportTaskDTOList;
  }

  /**
   * 封装报告工作流数据
   *
   * @param reportTaskDTO 工作流数据
   */
  private void completeReportTask(
      WorkflowReportTaskDTO reportTaskDTO, String data, Integer status, Integer nextStatus) {
    reportTaskDTO.setOldData(data);
    WorkflowUtils.completeWorkflowTask(reportTaskDTO, status, nextStatus);
  }

  /**
   * 封装报告数据
   *
   * @param dataReportDTO 报告数据
   */
  private void completeReportData(
      DataReportDTO dataReportDTO, WorkflowReportTaskDTO reportTaskDTO, Integer nextStatus) {
    // 只有修改操作才允许修改数据
    boolean isEdit = WorkflowControl.isEditReportData(reportTaskDTO.getOperation());
    String newData = reportTaskDTO.getNewData();
    if (isEdit && newData != null && !newData.isEmpty()) {
      dataReportDTO.setData(newData);
    }
    dataReportDTO.setStatus(nextStatus);
    dataReportDTO.setNextOperator(reportTaskDTO.getNextOperator());
    dataReportDTO.setModifyUser(CommonUtils.getCurrentUserId());
    dataReportDTO.setGmtModified(CommonUtils.getNowTime());
  }

  /**
   * 检查待走工作流数据的权限
   *
   * @param dataReportDTO 待校验数据
   * @param status 核对状态
   * @return 是否满足工作流条件
   */
  private boolean checkWorkflowRight(DataReportDTO dataReportDTO, int status) {
    if (dataReportDTO == null) {
      LOGGER.error("对应数据不存在!");
      return false;
    }
    if (dataReportDTO.getStatus() != status) {
      LOGGER.error(
          "状态校验失败! 当前数据状态为:{}-{}, 需要状态为:{}-{}",
          dataReportDTO.getStatus(),
          getReportStatusNameByCode(dataReportDTO.getStatus()),
          status,
          getReportStatusNameByCode(status));
      return false;
    }
    return DataUtils.checkWorkflowRight(dataReportDTO);
  }

  /**
   * 备份报告相关工作流的数据
   *
   * @param backUpIds 待备份数据ids
   * @return 是否备份成功
   */
  private boolean backupWorkflowByOrderId(List<Long> backUpIds) {
    if (!workflowReportDao.insertCompleteFromCurrentByReportId(backUpIds)) {
      return false;
    }
    return workflowReportDao.deleteCurrentByReportId(backUpIds);
  }
}
