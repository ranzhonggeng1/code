package com.wslint.wisdomreport.utils;

import com.wslint.wisdomreport.domain.dto.workflow.WorkflowTaskDTO;

/**
 * 工作流工具类
 *
 * @author yuxr
 * @since 2018/11/13 16:52
 */
public class WorkflowUtils {

  /**
   * 完善工作流基类信息
   *
   * @param taskDTO 待完善对象
   * @param status 当前状态
   * @param nextStatus 流转状态
   */
  public static void completeWorkflowTask(
      WorkflowTaskDTO taskDTO, Integer status, Integer nextStatus) {
    taskDTO.setId(CommonUtils.getNextId());
    taskDTO.setOperator(CommonUtils.getCurrentUserId());
    taskDTO.setStatus(status);
    taskDTO.setNextStatus(nextStatus);
    taskDTO.setGmtModified(CommonUtils.getNowTime());
    taskDTO.setGmtCreate(CommonUtils.getNowTime());
  }
}
