package com.wslint.wisdomreport.service.workflow;

import com.wslint.wisdomreport.domain.dto.workflow.WorkflowBatchRedoDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowBatchTaskDTO;
import java.util.List;

/**
 * 批次工作流服务
 *
 * @author yuxr
 * @since 2018/11/9 11:57
 */
public interface IWorkflowBatchService {
  /**
   * 走工作流
   *
   * @param workflowBatchTaskDTOList 工作流数据
   * @return 处理结果
   */
  int doWorkflow(List<WorkflowBatchTaskDTO> workflowBatchTaskDTOList) throws Exception;

  /**
   * 复测流程
   * @param workflowBatchRedoDTOS 复测流程数据对象
   * @return 复测流程结果
   */
  int redo(List<WorkflowBatchRedoDTO> workflowBatchRedoDTOS) throws Exception;

  /**
   * 走工作流
   *
   * @param workflowBatchTaskDTO 工作流数据
   * @return 处理结果
   */
  int doWorkflowBak(WorkflowBatchTaskDTO workflowBatchTaskDTO) throws Exception;

  /**
   * 根据批次id查询审核进度
   *
   * @param batchId 批次id
   * @return 工作流数据
   */
  List<WorkflowBatchTaskDTO> getScheduleByBatchId(Long batchId);
}
