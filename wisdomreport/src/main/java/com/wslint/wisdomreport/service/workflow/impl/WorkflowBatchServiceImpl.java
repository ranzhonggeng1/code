package com.wslint.wisdomreport.service.workflow.impl;

import static com.wslint.wisdomreport.common.WorkflowControl.getBatchNextStatusByOperation;
import static com.wslint.wisdomreport.common.WorkflowControl.getBatchOperationNameByCode;
import static com.wslint.wisdomreport.common.WorkflowControl.getBatchStatusByOperation;
import static com.wslint.wisdomreport.common.WorkflowControl.getBatchStatusNameByCode;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.constant.ReturnConstant;
import com.wslint.wisdomreport.constant.WorkflowConstant;
import com.wslint.wisdomreport.dao.data.DataBatchDao;
import com.wslint.wisdomreport.dao.data.DataClassDao;
import com.wslint.wisdomreport.dao.data.DataRecordDao;
import com.wslint.wisdomreport.dao.data.DataReportDao;
import com.wslint.wisdomreport.dao.workflow.WorkflowBatchDao;
import com.wslint.wisdomreport.dao.workflow.WorkflowRecordDao;
import com.wslint.wisdomreport.domain.dto.data.batch.DataBatchDTO;
import com.wslint.wisdomreport.domain.dto.data.clazz.DataClassDTO;
import com.wslint.wisdomreport.domain.dto.data.record.DataRecordDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowBatchRedoDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowBatchTaskDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowRecordTaskDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowReportTaskDTO;
import com.wslint.wisdomreport.exception.WorkflowException;
import com.wslint.wisdomreport.service.data.IDataBatchService;
import com.wslint.wisdomreport.service.workflow.IWorkflowBatchService;
import com.wslint.wisdomreport.service.workflow.IWorkflowRecordService;
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
 * 批次工作流接口实现
 *
 * @author yuxr
 * @since 2018/11/9 11:58
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WorkflowBatchServiceImpl implements IWorkflowBatchService {

  /** logger 日志批次 */
  private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowBatchServiceImpl.class);

  @Autowired private IWorkflowRecordService iWorkflowRecordService;
  @Autowired private IWorkflowReportService iWorkflowReportService;
  @Autowired private IDataBatchService iDataBatchService;
  @Autowired private WorkflowBatchDao workflowBatchDao;
  @Autowired private WorkflowRecordDao workflowRecordDao;
  @Autowired private DataBatchDao dataBatchDao;
  @Autowired private DataClassDao dataClassDao;
  @Autowired private DataRecordDao dataRecordDao;
  @Autowired private DataReportDao dataReportDao;

  /**
   * 走工作流
   *
   * @param workflowBatchTaskDTOList 工作流数据
   * @return 处理结果
   */
  @Override
  public int doWorkflow(List<WorkflowBatchTaskDTO> workflowBatchTaskDTOList) throws Exception {
    List<DataBatchDTO> dataBatchDTOList =
        dataBatchDao.getBatchsByBatchIds(workflowBatchTaskDTOList);
    Map dataMap = CommonUtils.getMapByKeyFromList(Constant.PROPERTY_ID, dataBatchDTOList);
    Map<Integer, Integer> logMap = new HashMap<>();
    List<Long> backUpIds = new ArrayList<>();
    List<WorkflowBatchTaskDTO> unionBatchTasks = new ArrayList<>();
    for (WorkflowBatchTaskDTO workflowData : workflowBatchTaskDTOList) {
      Integer op = workflowData.getOperation();
      logMap.merge(op, 1, (oldValue, newValue) -> oldValue + 1);
      int status = getBatchStatusByOperation(op);
      int nextStatus = getBatchNextStatusByOperation(op);
      DataBatchDTO dataBatchDTO = (DataBatchDTO) dataMap.get(workflowData.getBatchId());
      if (!checkWorkflowRight(dataBatchDTO, status)) {
        LOGGER.error("id = {} 的批次数据工作流权限校验失败!", workflowData.getBatchId());
        throw new WorkflowException("工作流执行失败!");
      }
      completeBatchTask(workflowData, status, nextStatus);
      completeBatchData(dataBatchDTO, workflowData, nextStatus);
      if (op == WorkflowConstant.BATCH_OPERATION_COMMIT
          || op == WorkflowConstant.BATCH_OPERATION_REJECT) {
        unionBatchTasks.add(workflowData);
      }
      if (nextStatus == WorkflowConstant.STATUS_END) {
        unionBatchTasks.add(newEndBatchUnionTaskDTO(workflowData));
        backUpIds.add(workflowData.getBatchId());
      }
    }
    LOGGER.info(
        "批次工作流开始, 操作人: {}-{} ",
        CommonUtils.getCurrentUser().getUserCode(),
        CommonUtils.getCurrentUser().getUserName());
    logMap.forEach(
        (key, value) ->
            LOGGER.info(" -- {}-{} 数据 {} 条", key, getBatchOperationNameByCode(key), value));

    if (!workflowBatchDao.batchInsertWorkflowTask(workflowBatchTaskDTOList)) {
      LOGGER.error("工作流数据保存失败!");
      throw new WorkflowException("工作流执行失败!");
    }
    if (!dataBatchDao.batchUpdateBatchData(dataBatchDTOList)) {
      LOGGER.error("批次数据状态更新失败!");
      throw new WorkflowException("工作流执行失败!");
    }
    if (!unionBatchTasks.isEmpty()) {
      LOGGER.info(" -- 批次联合工作流 {} 条", unionBatchTasks.size());
      if (!doDataWorkflowByBatch(unionBatchTasks)) {
        LOGGER.error(" -- 批次联合工作流失败！");
        throw new WorkflowException("工作流执行失败!");
      }
    }
    if (!backUpIds.isEmpty()) {
      LOGGER.info(" -- 批次工作流数据备份迁移 {} 条", backUpIds.size());
      if (!backupWorkflowByOrderId(backUpIds)) {
        LOGGER.error("批次工作流数据备份失败!");
        throw new WorkflowException("工作流执行失败!");
      }
    }
    LOGGER.info("批次工作流完成!");
    return ReturnConstant.WORKFLOW_SUCCESS;
  }

  /**
   * 复测流程
   *
   * @param workflowRedoDTOS 复测流程数据对象
   * @return 复测流程结果
   */
  @Override
  public int redo(List<WorkflowBatchRedoDTO> workflowRedoDTOS) throws Exception {
    List<WorkflowBatchTaskDTO> workflowBatchTaskDTOS = new ArrayList<>();
    for (WorkflowBatchRedoDTO workflowRedoDTO : workflowRedoDTOS) {
      workflowBatchTaskDTOS.add(newRedoBatchTaskDTO(workflowRedoDTO));
      List<DataClassDTO> dataClassDTOS =
          dataClassDao.getClasssByBatchId(workflowRedoDTO.getBatchId());
      // 创建新的批次，之前的批次置为无效状态
      if (!dataBatchDao.setIsValidFalseByBatchId(workflowRedoDTO.getBatchId())) {
        LOGGER.error("批次状态置为无效失败!");
        throw new WorkflowException("复测流程流转失败！");
      }
      iDataBatchService.save(dataClassDTOS);
      // 整理出不需要复测的检查项
      Map<Long, Long> oldNewClassIdMap = new HashMap<>();
      Map<Long, Long> oldNewBatchIdMap = new HashMap<>();
      List<Long> copyClassIds = new ArrayList<>();
      List<DataClassDTO> redoDTOS = workflowRedoDTO.getDataClassDTOS();
      dataClassDTOS.forEach(
          classDTO ->
              redoDTOS.forEach(
                  redoDTO -> {
                    if (!redoDTO.getFirstClassId().equals(classDTO.getFirstClassId())
                        || !redoDTO.getSecondClassId().equals(classDTO.getSecondClassId())) {
                      copyClassIds.add(classDTO.getOldClassId());
                      oldNewClassIdMap.put(classDTO.getOldClassId(), classDTO.getId());
                      oldNewBatchIdMap.put(classDTO.getOldBatchId(), classDTO.getBatchId());
                    }
                  }));
      ///      if (redoDTOS.size() > 0) {
      ///        LOGGER.error("复测检查项与已有检查项不匹配!");
      ///        throw new WorkflowException("复测流程流转失败！");
      ///      }

      // 复制不需要复测的检查项的原始记录 复制 并 处理
      if (!copyClassIds.isEmpty()) {
        List<DataRecordDTO> datas = dataRecordDao.getCopyRecordDatasByClassIds(copyClassIds);
        List<Long> copyRecordIds = new ArrayList<>();
        Map<Long, Long> oldNewRecordIdMap = new HashMap<>();
        datas.forEach(
            data -> {
              data.setOldId(data.getId());
              data.setId(CommonUtils.getNextId());
              data.setStatus(WorkflowConstant.RECORD_STATUS_EFFECTIVE);
              data.setBatchId(oldNewBatchIdMap.get(data.getBatchId()));
              data.setClassId(oldNewClassIdMap.get(data.getClassId()));
              copyRecordIds.add(data.getOldId());
              oldNewRecordIdMap.put(data.getOldId(), data.getId());
            });
        if (!datas.isEmpty() && !dataRecordDao.insertDataRecords(datas)) {
          LOGGER.error("复制原始记录数据失败!");
          throw new WorkflowException("复测流程流转失败！");
        }
        // 复制不需要复测的检查项的原始记录的流程记录 复制 并 处理
        if (!copyRecordIds.isEmpty()) {
          List<WorkflowRecordTaskDTO> tasks =
              workflowRecordDao.getCopyRecordTasksByRecordIds(copyRecordIds);
          tasks.forEach(
              task -> {
                task.setId(CommonUtils.getNextId());
                task.setRecordId(oldNewRecordIdMap.get(task.getRecordId()));
              });
          if (!tasks.isEmpty() && !workflowRecordDao.batchInsertWorkflowTask(tasks)) {
            LOGGER.error("复制原始记录工作流失败!");
            throw new WorkflowException("复测流程流转失败！");
          }
        }
      }
    }
    // 当前批次数据走工作流-复测 将所有原数据结束
    return doWorkflow(workflowBatchTaskDTOS);
  }

  /**
   * 创建一个新的走复测工作流的对象
   *
   * @param workflowRedoDTO 数据来源
   * @return 新的对象
   */
  private WorkflowBatchTaskDTO newRedoBatchTaskDTO(WorkflowBatchRedoDTO workflowRedoDTO) {
    WorkflowBatchTaskDTO redoTaskDTO = new WorkflowBatchTaskDTO();
    redoTaskDTO.setBatchId(workflowRedoDTO.getBatchId());
    redoTaskDTO.setOperation(WorkflowConstant.BATCH_OPERATION_REDO);
    redoTaskDTO.setReason(workflowRedoDTO.getReason());
    return redoTaskDTO;
  }

  /**
   * 创建一个新的完成工作流的联合工作流对象
   *
   * @param workflowData 批次工作流对象
   * @return 联合工作流对象
   */
  private WorkflowBatchTaskDTO newEndBatchUnionTaskDTO(WorkflowBatchTaskDTO workflowData) {
    WorkflowBatchTaskDTO unionTask = new WorkflowBatchTaskDTO();
    unionTask.setBatchId(workflowData.getBatchId());
    unionTask.setOperation(WorkflowConstant.OPERATION_COMPLETE);
    unionTask.setReason(WorkflowConstant.OPERATION_NAME_COMPLETE);
    return unionTask;
  }

  /**
   * 封装批次工作流数据
   *
   * @param batchTaskDTO 工作流数据
   */
  private void completeBatchTask(
      WorkflowBatchTaskDTO batchTaskDTO, Integer status, Integer nextStatus) {
    WorkflowUtils.completeWorkflowTask(batchTaskDTO, status, nextStatus);
  }

  /**
   * 封装批次数据
   *
   * @param dataBatchDTO 批次数据
   */
  private void completeBatchData(
      DataBatchDTO dataBatchDTO, WorkflowBatchTaskDTO batchTaskDTO, Integer nextStatus) {
    // 只有修改操作才允许修改数据
    dataBatchDTO.setStatus(nextStatus);
    dataBatchDTO.setNextOperator(batchTaskDTO.getNextOperator());
    dataBatchDTO.setModifyUser(batchTaskDTO.getOperator());
    dataBatchDTO.setGmtModified(CommonUtils.getNowTime());
  }

  /**
   * 检查待走工作流数据的权限
   *
   * @param dataBatchDTO 待校验数据
   * @param status 核对状态
   * @return 是否满足工作流条件
   */
  private boolean checkWorkflowRight(DataBatchDTO dataBatchDTO, int status) {
    if (dataBatchDTO == null) {
      LOGGER.error("对应数据不存在!");
      return false;
    }
    if (dataBatchDTO.getStatus() != status) {
      LOGGER.error(
          "状态校验失败! 当前数据状态为:{}-{}, 需要状态为:{}-{}",
          dataBatchDTO.getStatus(),
          getBatchStatusNameByCode(dataBatchDTO.getStatus()),
          status,
          getBatchStatusNameByCode(status));
      return false;
    }
    return DataUtils.checkWorkflowRight(dataBatchDTO);
  }

  /**
   * 备份批次相关工作流的数据
   *
   * @param backUpIds 待备份数据ids
   * @return 是否备份成功
   */
  private boolean backupWorkflowByOrderId(List<Long> backUpIds) {
    if (!workflowBatchDao.insertCompleteFromCurrentByBatchIds(backUpIds)) {
      return false;
    }
    return workflowBatchDao.deleteCurrentByBatchIds(backUpIds);
  }

  /**
   * 根据批次ids和操作对批次对应的数据走工作流
   *
   * @return 是否完成工作流
   */
  private boolean doDataWorkflowByBatch(List<WorkflowBatchTaskDTO> batchTaskDTOS) throws Exception {
    List<WorkflowRecordTaskDTO> recordTaskDTOS =
        dataRecordDao.getRecordTasksByBatchTasks(batchTaskDTOS);
    List<WorkflowReportTaskDTO> reportTaskDTOS =
        dataReportDao.getReportTasksByBatchTasks(batchTaskDTOS);
    // 执行相关流程
    if (!recordTaskDTOS.isEmpty()) {
      LOGGER.info(" -- 批次联合记录工作流 {} 条", recordTaskDTOS.size());
      int result = iWorkflowRecordService.doWorkflow(recordTaskDTOS);
      if (result != ReturnConstant.WORKFLOW_SUCCESS) {
        LOGGER.info(" -- 批次联合记录工作流失败！ - {} ", result);
        return false;
      }
    }
    if (!reportTaskDTOS.isEmpty()) {
      LOGGER.info(" -- 批次联合报告工作流 {} 条", reportTaskDTOS.size());
      int result = iWorkflowReportService.doWorkflow(reportTaskDTOS);
      if (result != ReturnConstant.WORKFLOW_SUCCESS) {
        LOGGER.info(" -- 批次联合报告工作流失败！ - {} ", result);
        return false;
      }
    }
    return true;
  }

  // todo 重构分界线 ==========================================
  /**
   * 走工作流
   *
   * @param workflowBatchTaskDTO 工作流数据
   * @return 是否成功
   */
  @Override
  public int doWorkflowBak(WorkflowBatchTaskDTO workflowBatchTaskDTO) throws Exception {

    if (workflowBatchTaskDTO.getOperation() == WorkflowConstant.BATCH_OPERATION_CREATE_BAK) {
      LOGGER.info("创建批次工作流开始！");
      // 封装数据
      WorkflowBatchTaskDTO task =
          completeInsertWorkflowDTO(
              workflowBatchTaskDTO,
              WorkflowConstant.BATCH_OPERATION_CREATE_BAK,
              WorkflowConstant.STATUS_START,
              WorkflowConstant.BATCH_STATUS_EXPERIMENTING_BAK);
      return work(task);
    }

    if (workflowBatchTaskDTO.getOperation() == WorkflowConstant.BATCH_OPERATION_COMMIT_BAK) {
      LOGGER.info("提交审核工作流开始！");
      WorkflowBatchTaskDTO task =
          completeInsertWorkflowDTO(
              workflowBatchTaskDTO,
              WorkflowConstant.BATCH_OPERATION_COMMIT_BAK,
              WorkflowConstant.BATCH_STATUS_EXPERIMENTING_BAK,
              WorkflowConstant.BATCH_STATUS_APPROVING_BAK);
      return work(task);
    }

    if (workflowBatchTaskDTO.getOperation() == WorkflowConstant.BATCH_OPERATION_PASS_BAK) {
      LOGGER.info("审核通过工作流开始！");
      WorkflowBatchTaskDTO task =
          completeInsertWorkflowDTO(
              workflowBatchTaskDTO,
              WorkflowConstant.BATCH_OPERATION_PASS_BAK,
              WorkflowConstant.BATCH_STATUS_APPROVING_BAK,
              WorkflowConstant.STATUS_END);
      return work(task);
    }

    if (workflowBatchTaskDTO.getOperation() == WorkflowConstant.BATCH_OPERATION_REJECT_BAK) {
      LOGGER.info("审核驳回工作流开始！");
      WorkflowBatchTaskDTO task =
          completeInsertWorkflowDTO(
              workflowBatchTaskDTO,
              WorkflowConstant.BATCH_OPERATION_REJECT_BAK,
              WorkflowConstant.BATCH_STATUS_APPROVING_BAK,
              WorkflowConstant.BATCH_STATUS_NOT_PASS_BAK);
      return work(task);
    }

    if (workflowBatchTaskDTO.getOperation() == WorkflowConstant.BATCH_OPERATION_UNDO_BAK) {
      LOGGER.info("重做实验工作流开始！");
      WorkflowBatchTaskDTO task =
          completeInsertWorkflowDTO(
              workflowBatchTaskDTO,
              WorkflowConstant.BATCH_OPERATION_UNDO_BAK,
              WorkflowConstant.BATCH_STATUS_NOT_PASS_BAK,
              WorkflowConstant.BATCH_STATUS_EXPERIMENTING_BAK);
      return work(task);
    }

    return ReturnConstant.WORKFLOW_FAILED;
  }

  /**
   * 根据批次id查询审核进度
   *
   * @param batchId 批次id
   * @return 工作流数据
   */
  @Override
  public List<WorkflowBatchTaskDTO> getScheduleByBatchId(Long batchId) {
    // 先查看当前批次的状态
    DataBatchDTO dataBatchDTO = dataBatchDao.getDataBatchById(batchId);
    if (dataBatchDTO == null) {
      LOGGER.info("当前批次没有历史数据！");
      return new ArrayList<>();
    }
    // 根据状态去不同的表获取工作流数据
    if (dataBatchDTO.getStatus() == WorkflowConstant.STATUS_END) {
      return workflowBatchDao.getWfCompleteTasksByBatchId(batchId);
    }
    return workflowBatchDao.getWfCurrentTasksByBatchId(batchId);
  }

  /**
   * 走工作流
   *
   * @param task 工作流参数
   * @return 处理结果
   */
  private int work(WorkflowBatchTaskDTO task) throws Exception {
    // 校验当前数据是否满足流程更新条件，日后可以加权限
    if (!dataBatchDao.checkWorkflowRight(task.getBatchId(), task.getStatus(), task.getOperator())) {
      LOGGER.error("当前用户无操作该数据的权限！");
      return ReturnConstant.WORKFLOW_FAILED_RIGHT_ERROR;
    }
    if (!workflowBatchDao.insertCurrent(task)) {
      LOGGER.error("新增工作流数据失败！");
      return ReturnConstant.WORKFLOW_FAILED_INSERT;
    }
    DataBatchDTO batchDTO =
        completeUpdateDataDTO(task.getBatchId(), task.getNextStatus(), task.getNextOperator());
    if (!dataBatchDao.updateBatchDataStatus(batchDTO)) {
      LOGGER.error("更新数据状态失败！");
      return ReturnConstant.DATA_UPDATE_FAILED;
    }
    // 如果是提交审批，对应记录数据 提交审批

    // 如果是重做实验，对应记录数据 撤回审批

    // 任务完成后迁移已完成工作流数据，提高系统高频查询的效率
    if (task.getNextStatus() == WorkflowConstant.STATUS_END) {
      if (!backupWorkflowByBatchId(task.getBatchId())) {
        LOGGER.error("备份工作流数据失败！");
        return ReturnConstant.WORKFLOW_FAILED_BACKUP;
      }
      if (!completeRecordWorkflowByBatchId(task.getBatchId())) {
        LOGGER.error("完成记录工作流失败！");
        return ReturnConstant.WORKFLOW_FAILED;
      }
    }
    LOGGER.info("工作流执行成功！");
    return ReturnConstant.WORKFLOW_SUCCESS;
  }

  /**
   * 完善工作流信息
   *
   * @param workflowBatchTaskDTO 信息源
   * @param operation 操作
   * @param status 当前状态
   * @param nextStatus 下一状态
   * @return 完善后的信息
   */
  private WorkflowBatchTaskDTO completeInsertWorkflowDTO(
      WorkflowBatchTaskDTO workflowBatchTaskDTO, int operation, int status, int nextStatus) {
    workflowBatchTaskDTO.setId(CommonUtils.getNextId());
    workflowBatchTaskDTO.setOperation(operation);
    workflowBatchTaskDTO.setStatus(status);
    workflowBatchTaskDTO.setNextStatus(nextStatus);
    workflowBatchTaskDTO.setOperator(CommonUtils.getCurrentUserId());
    workflowBatchTaskDTO.setGmtCreate(CommonUtils.getNowTime());
    workflowBatchTaskDTO.setGmtModified(CommonUtils.getNowTime());
    return workflowBatchTaskDTO;
  }

  /**
   * 完善更新数据对象
   *
   * @param batchId 批次id
   * @param status 状态
   * @param nextOperator 下一审核人
   * @return 生成的数据
   */
  private DataBatchDTO completeUpdateDataDTO(Long batchId, int status, Long nextOperator) {
    DataBatchDTO batchDTO = new DataBatchDTO();
    batchDTO.setId(batchId);
    batchDTO.setStatus(status);
    batchDTO.setNextOperator(nextOperator);
    batchDTO.setModifyUser(CommonUtils.getCurrentUserId());
    batchDTO.setGmtModified(CommonUtils.getNowTime());
    return batchDTO;
  }

  /**
   * 备份工作流数据
   *
   * @param batchId 数据id
   * @return 是否备份成功
   */
  private boolean backupWorkflowByBatchId(Long batchId) {
    if (!workflowBatchDao.insertCompleteFromCurrentByBatchId(batchId)) {
      return false;
    }
    return workflowBatchDao.deleteCurrentByBatchId(batchId);
  }

  /**
   * 完成记录数据的工作流
   *
   * @param batchId 批次id
   * @return 是否完成
   */
  private boolean completeRecordWorkflowByBatchId(Long batchId) throws Exception {
    List<DataRecordDTO> dataRecordDTOList = dataRecordDao.getRecordsByBatchId(batchId);
    List<WorkflowRecordTaskDTO> workflowRecordTaskDTOList = new ArrayList<>();
    for (DataRecordDTO dataRecordDTO : dataRecordDTOList) {
      WorkflowRecordTaskDTO workflowRecordTaskDTO = new WorkflowRecordTaskDTO();
      workflowRecordTaskDTO.setRecordId(dataRecordDTO.getId());
      workflowRecordTaskDTO.setOperation(WorkflowConstant.BATCH_OPERATION_PASS);
      workflowRecordTaskDTOList.add(workflowRecordTaskDTO);
    }
    int result = iWorkflowRecordService.doWorkflow(workflowRecordTaskDTOList);
    if (result == ReturnConstant.WORKFLOW_SUCCESS) {
      LOGGER.info("记录流程流转成功！");
      return true;
    }
    LOGGER.info("记录流程流转失败！");
    return false;
  }
}
