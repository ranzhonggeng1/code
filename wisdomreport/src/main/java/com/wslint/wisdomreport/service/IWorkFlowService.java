package com.wslint.wisdomreport.service;

import com.wslint.wisdomreport.domain.dto.workflow.WorkflowBatchTaskDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WrWfTask;
import java.util.List;

/**
 * 工作流实现接口
 *
 * @author yuxr
 * @since 2018/9/12 11:42
 */
public interface IWorkFlowService {

  /**
   * 创建批次
   *
   * @param wrWfTask 任务
   */
  Long start(WrWfTask wrWfTask);

  /**
   * 提交审批
   *
   * @param wrWfTask 任务
   */
  boolean commit(WrWfTask wrWfTask);

  /**
   * 审批不通过
   *
   * @param wrWfTask 任务
   */
  boolean fail(WrWfTask wrWfTask);

  /**
   * 审批并结束
   *
   * @param wrWfTask 任务
   */
  boolean passEnd(WrWfTask wrWfTask);
  /**
   * 审批后继续审批
   *
   * @param wrWfTask 任务
   */
  boolean passReview(WrWfTask wrWfTask);

  /**
   * 重做实验
   *
   * @param wrWfTask 任务
   */
  boolean redo(WrWfTask wrWfTask);

  /**
   * 根据药品Id和状态查询数据
   *
   * @param medicineId 药瓶Id
   * @param status 状态
   * @return 工作流数据
   */
  List<WrWfTask> getWrWfTasksByMedicineIdAndStatus(Long medicineId, int status);

  /**
   * 根据药品Id查询已完成数据
   *
   * @param medicineId 药瓶Id
   * @return 工作流数据
   */
  List<WrWfTask> getWrWfCompleteTasksByMedicineId(Long medicineId);

  /**
   * 根据药品Id和状态查询所有工作流数据
   *
   * @param medicineId 药瓶Id
   * @param batchNo 批次号
   * @return 工作流数据
   */
  List<WrWfTask> getWrWfAllTasksByMedicineIdAndBatchNo(Long medicineId, Long batchNo);

  /**
   * 根据审核人查看待办事项
   *
   * @param reviewerId 待审核人
   * @return 工作流数据
   */
  List<WrWfTask> getWrWfCurrentTasksByReviewerId(Long reviewerId);

  /**
   * 根据批次id查询审核进度
   *
   * @param batchId 批次id
   * @return 工作流数据
   */
  List<WorkflowBatchTaskDTO> getScheduleByBatchId(String batchId);
}
