package com.wslint.wisdomreport.service.impl;

import com.google.gson.Gson;
import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.dao.WorkFlowDao;
import com.wslint.wisdomreport.domain.dto.user.User;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowBatchTaskDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WrWfTask;
import com.wslint.wisdomreport.service.IWorkFlowService;
import com.wslint.wisdomreport.utils.CommonUtils;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工作流实现类
 *
 * @author yuxr
 * @since 2018/9/12 11:43
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WorkFlowServiceImpl implements IWorkFlowService {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(WorkFlowServiceImpl.class);

  @Autowired private WorkFlowDao workFlowDao;

  /**
   * 创建批次
   *
   * @param wrWfTask 任务
   */
  @Override
  public Long start(WrWfTask wrWfTask) {
    Long taskId = CommonUtils.getNextId();
    // 初始化数据
    initWrWfTaskData(wrWfTask);
    wrWfTask.setTaskId(taskId);
    wrWfTask.setGmtCreate(CommonUtils.getNowTime());
    wrWfTask.setStatus(Constant.WORK_FLOW_STATUS_EXPERIMENT);
    // 存储当前任务表
    workFlowDao.insertWrWfCurrentTask(wrWfTask);
    LOGGER.info(
        "------------------ WorkFlow.taskId = {} -- 创建任务！------------------", wrWfTask.getTaskId());
    return taskId;
  }

  /**
   * 发起审核
   *
   * @param wrWfTask 任务
   */
  @Override
  public boolean commit(WrWfTask wrWfTask) {
    if (!workFlowDao.isWrWFCurrentTaskExist(wrWfTask.getTaskId())) {
      LOGGER.info("------------------ taskId = {} 任务不存在！------------------", wrWfTask.getTaskId());
      return false;
    }
    // 获取并迁移当前待办数据
    WrWfTask currentTask = getAndTransferWfTaskDataFromCurrentToCompleteByTaskId(wrWfTask);
    // 整理数据
    currentTask.setStatus(Constant.WORK_FLOW_STATUS_REVIEW);
    // 存入当前任务
    workFlowDao.insertWrWfCurrentTask(currentTask);
    LOGGER.info(
        "------------------ WorkFlow.taskId = {} -- 发起审核！------------------",
        currentTask.getTaskId());
    return true;
  }

  /**
   * 审核不通过
   *
   * @param wrWfTask 任务
   */
  @Override
  public boolean fail(WrWfTask wrWfTask) {
    if (!workFlowDao.isWrWFCurrentTaskExist(wrWfTask.getTaskId())) {
      LOGGER.info("------------------ taskId = {} 任务不存在！------------------", wrWfTask.getTaskId());
      return false;
    }
    // 获取并迁移当前待办数据
    WrWfTask currentTask = getAndTransferWfTaskDataFromCurrentToCompleteByTaskId(wrWfTask);
    // 整理数据
    currentTask.setStatus(Constant.WORK_FLOW_STATUS_REVIEW_FAILED);
    // 存入当前任务
    workFlowDao.insertWrWfCurrentTask(currentTask);
    LOGGER.info(
        "------------------ WorkFlow.taskId = {} -- 审核不通过！------------------",
        currentTask.getTaskId());
    return true;
  }

  /**
   * 审核通过并结束
   *
   * @param wrWfTask 任务
   */
  @Override
  public boolean passEnd(WrWfTask wrWfTask) {
    if (!workFlowDao.isWrWFCurrentTaskExist(wrWfTask.getTaskId())) {
      LOGGER.info("------------------ taskId = {} 任务不存在！------------------", wrWfTask.getTaskId());
      return false;
    }
    // 获取并迁移当前待办数据
    WrWfTask currentTask = getAndTransferWfTaskDataFromCurrentToCompleteByTaskId(wrWfTask);
    // 整理数据
    currentTask.setStatus(Constant.WORK_FLOW_STATUS_END);
    // 存入历史任务
    workFlowDao.insertWrWfCompleteTask(currentTask);
    LOGGER.info(
        "------------------ WorkFlow.taskId = {} -- 审核通过！------------------",
        currentTask.getTaskId());
    return true;
  }
  /**
   * 审核通过并继续审核
   *
   * @param wrWfTask 任务
   */
  @Override
  public boolean passReview(WrWfTask wrWfTask) {
    if (!workFlowDao.isWrWFCurrentTaskExist(wrWfTask.getTaskId())) {
      LOGGER.info("------------------ taskId = {} 任务不存在！------------------", wrWfTask.getTaskId());
      return false;
    }
    // 获取并迁移当前待办数据
    WrWfTask currentTask = getAndTransferWfTaskDataFromCurrentToCompleteByTaskId(wrWfTask);
    // 整理数据
    currentTask.setStatus(Constant.WORK_FLOW_STATUS_REVIEW);
    // 存入当前任务
    workFlowDao.insertWrWfCurrentTask(currentTask);
    LOGGER.info(
        "------------------ WorkFlow.taskId = {} -- 继续审核！------------------",
        currentTask.getTaskId());
    return true;
  }

  /**
   * 重做实验
   *
   * @param wrWfTask 任务
   */
  @Override
  public boolean redo(WrWfTask wrWfTask) {
    if (!workFlowDao.isWrWFCurrentTaskExist(wrWfTask.getTaskId())) {
      LOGGER.info("------------------ taskId = {} 任务不存在！------------------", wrWfTask.getTaskId());
      return false;
    }
    // 获取并迁移当前待办数据
    WrWfTask currentTask = getAndTransferWfTaskDataFromCurrentToCompleteByTaskId(wrWfTask);
    // 整理数据
    currentTask.setStatus(Constant.WORK_FLOW_STATUS_EXPERIMENT);
    // 存入当前任务
    workFlowDao.insertWrWfCurrentTask(currentTask);
    LOGGER.info(
        "------------------ WorkFlow.taskId = {} -- 重做实验！------------------",
        currentTask.getTaskId());
    return true;
  }

  /**
   * 根据药品Id和状态查询数据
   *
   * @param medicineId 药瓶Id
   * @param status 状态
   * @return 工作流数据
   */
  @Override
  public List<WrWfTask> getWrWfTasksByMedicineIdAndStatus(Long medicineId, int status) {
    WrWfTask wrWfTask = new WrWfTask();
    wrWfTask.setStatus(status);
    wrWfTask.setMedicineId(medicineId);
    // 查询已完成状态数据
    if (status == Constant.WORK_FLOW_STATUS_END) {
      LOGGER.debug(
          "------------------ getWrWfCompleteTasksByMedicineIdAndStatus({},{}) = {} ------------------",
          medicineId,
          status,
          new Gson().toJson(wrWfTask));
      return workFlowDao.getWrWfCompleteTasksByMedicineIdAndStatus(wrWfTask);
    }
    LOGGER.debug(
        "------------------ getWrWfCurrentTasksByMedicineIdAndStatus({},{}) = {} ------------------",
        medicineId,
        status,
        new Gson().toJson(wrWfTask));
    return workFlowDao.getWrWfCurrentTasksByMedicineIdAndStatus(wrWfTask);
  }

  /**
   * 根据药品Id查询已完成数据
   *
   * @param medicineId 药瓶Id
   * @return 工作流数据
   */
  @Override
  public List<WrWfTask> getWrWfCompleteTasksByMedicineId(Long medicineId) {
    WrWfTask wrWfTask = new WrWfTask();
    wrWfTask.setMedicineId(medicineId);
    LOGGER.debug(
        "------------------ getWrWfCompleteTasksByMedicineId({}) = {} ------------------",
        medicineId,
        new Gson().toJson(wrWfTask));
    return workFlowDao.getWrWfCompleteTasksByMedicineId(wrWfTask);
  }

  /**
   * 根据药品Id和状态查询所有工作流数据
   *
   * @param medicineId 药瓶Id
   * @param batchNo 批次号
   * @return 工作流数据
   */
  @Override
  public List<WrWfTask> getWrWfAllTasksByMedicineIdAndBatchNo(Long medicineId, Long batchNo) {
    WrWfTask wrWfTask = new WrWfTask();
    wrWfTask.setMedicineId(medicineId);
    wrWfTask.setBatchNo(batchNo);
    LOGGER.debug(
        "------------------ getWrWfAllTasksByMedicineIdAndBatchNo({},{}) = {} ------------------",
        medicineId,
        batchNo,
        new Gson().toJson(wrWfTask));
    return workFlowDao.getWrWfAllTasksByMedicineIdAndBatchNo(wrWfTask);
  }

  /**
   * 根据审核人查看待办事项
   *
   * @param reviewerId 待审核人
   * @return 工作流数据
   */
  @Override
  public List<WrWfTask> getWrWfCurrentTasksByReviewerId(Long reviewerId) {
    WrWfTask wrWfTask = new WrWfTask();
    wrWfTask.setReviewerId(reviewerId);
    LOGGER.debug(
        "------------------ getWrWfCurrentTasksByReviewerId({}) = {} ------------------",
        reviewerId,
        new Gson().toJson(wrWfTask));
    return workFlowDao.getWrWfCurrentTasksByReviewerId(wrWfTask);
  }

  /**
   * 根据批次id查询审核进度
   *
   * @param batchId 批次id
   * @return 工作流数据
   */
  @Override
  public List<WorkflowBatchTaskDTO> getScheduleByBatchId(String batchId) {
    return null;
  }

  /**
   * 根据任务ID获取并迁移当前任务表数据
   *
   * @param wrWfTask 传入工作流数据
   * @return 获取到的当前任务
   */
  private WrWfTask getAndTransferWfTaskDataFromCurrentToCompleteByTaskId(WrWfTask wrWfTask) {
    // 根据taskId获取当前任务
    WrWfTask currentTask = workFlowDao.getWrWfCurrentTaskByTaskId(wrWfTask.getTaskId());
    // 根据taskId删除当前任务
    workFlowDao.deleteWrWfCurrentTaskByTaskId(wrWfTask.getTaskId());
    // 将当前任务数据存入历史记录
    workFlowDao.insertWrWfCompleteTask(currentTask);
    // 组装新的数据
    return combineWrWfTaskData(currentTask, wrWfTask);
  }

  /**
   * 初始化工作流数据
   *
   * @param currentTask 输出数据源
   * @param wrWfTask 输入数据源
   * @return 输出数据源
   */
  private WrWfTask combineWrWfTaskData(WrWfTask currentTask, WrWfTask wrWfTask) {
    initWrWfTaskData(currentTask);
    currentTask.setReviewerId(wrWfTask.getReviewerId());
    currentTask.setReviewComment(wrWfTask.getReviewComment());
    return currentTask;
  }

  /**
   * 初始化工作流数据
   *
   * @param currentTask 待初始化对象
   */
  private void initWrWfTaskData(WrWfTask currentTask) {
    User user = CommonUtils.getCurrentUser();
    currentTask.setOperatorId(user.getId());
    currentTask.setOperatorName(user.getUserName());
    currentTask.setGmtModified(CommonUtils.getNowTime());
  }
}
