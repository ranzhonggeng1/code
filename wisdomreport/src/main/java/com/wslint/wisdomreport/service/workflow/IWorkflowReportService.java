package com.wslint.wisdomreport.service.workflow;

import com.wslint.wisdomreport.domain.dto.workflow.WorkflowReportTaskDTO;
import java.util.List;

/**
 * 报告走工作流服务接口
 *
 * @author yuxr
 * @since 2018/11/13 14:33
 */
public interface IWorkflowReportService {

  /**
   * 走工作流接口
   *
   * @param workflowReportTaskDTOList 待处理数据
   * @return 处理结果
   */
  int doWorkflow(List<WorkflowReportTaskDTO> workflowReportTaskDTOList) throws Exception;

  /**
   * 追溯工作流数据
   *
   * @param reportId 报告数据id
   * @return 工作流数据
   */
  List<WorkflowReportTaskDTO> trace(Long reportId);
}
