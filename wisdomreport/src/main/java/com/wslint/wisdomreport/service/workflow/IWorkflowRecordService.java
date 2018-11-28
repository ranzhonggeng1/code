package com.wslint.wisdomreport.service.workflow;

import com.wslint.wisdomreport.domain.dto.workflow.WorkflowRecordTaskDTO;
import java.util.List;

/**
 * 记录走工作流服务接口
 *
 * @author yuxr
 * @since 2018/11/13 14:33
 */
public interface IWorkflowRecordService {

  /**
   * 走工作流接口
   *
   * @param workflowRecordTaskDTOList 待处理数据
   * @return 处理结果
   */
  int doWorkflow(List<WorkflowRecordTaskDTO> workflowRecordTaskDTOList) throws Exception;

  /**
   * 追溯工作流数据
   *
   * @param recordId 记录数据id
   * @return 工作流数据
   */
  List<WorkflowRecordTaskDTO> trace(Long recordId);
}
