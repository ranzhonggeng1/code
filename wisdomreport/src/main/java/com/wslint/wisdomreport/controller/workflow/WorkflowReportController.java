package com.wslint.wisdomreport.controller.workflow;

import com.wslint.wisdomreport.constant.ReturnConstant;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowReportTaskDTO;
import com.wslint.wisdomreport.domain.vo.data.DataIdVO;
import com.wslint.wisdomreport.domain.vo.workflow.ReportWorkflowVO;
import com.wslint.wisdomreport.domain.vo.workflow.WorkflowReportTraceReturnVO;
import com.wslint.wisdomreport.service.workflow.IWorkflowReportService;
import com.wslint.wisdomreport.utils.BeanCopyUtil;
import com.wslint.wisdomreport.utils.ReturnUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 报告工作流接口
 *
 * @author yuxr
 * @since 2018/11/13 14:22
 */
@Api(tags = "报告工作流接口", description = "提供报告工作流接口")
@RestController
@RequestMapping(value = "/workflow/report")
public class WorkflowReportController {

  /** logger 日志报告 */
  private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowReportController.class);

  @Autowired private IWorkflowReportService iWorkflowReportService;
  /**
   * 报告走工作流
   *
   * @param reportWorkflowVOList 报告工作流参数
   * @return 处理结果
   */
  @ApiOperation(value = "报告走工作流接口", notes = "提供报告走工作流功能")
  @RequestMapping(value = "/work", method = RequestMethod.POST)
  Map<String, Object> work(@RequestBody List<ReportWorkflowVO> reportWorkflowVOList)
      throws Exception {
    List<WorkflowReportTaskDTO> workflowReportTaskDTOList =
        BeanCopyUtil.copyList(reportWorkflowVOList, WorkflowReportTaskDTO.class);
    int result = iWorkflowReportService.doWorkflow(workflowReportTaskDTOList);
    if (result == ReturnConstant.WORKFLOW_SUCCESS) {
      LOGGER.info("报告流程流转成功！");
      return ReturnUtils.successMap("报告流程流转成功！");
    }
    LOGGER.info("报告流程流转失败！");
    return ReturnUtils.failureMap("报告流程流转失败！");
  }

  /**
   * 报告数据追溯
   *
   * @return 报告数据list
   */
  @ApiOperation(value = "报告数据追溯", notes = "提供报告数据追溯功能")
  @RequestMapping(value = "/trace", method = RequestMethod.POST)
  public Map<String, Object> trace(@RequestBody DataIdVO dataIdVO) throws Exception {
    List<WorkflowReportTaskDTO> workflowReportTaskDTOList =
        iWorkflowReportService.trace(dataIdVO.getId());
    List<WorkflowReportTraceReturnVO> workflowReportTraceReturnVOList =
        BeanCopyUtil.copyList(workflowReportTaskDTOList, WorkflowReportTraceReturnVO.class);
    return ReturnUtils.successMap(workflowReportTraceReturnVOList, "报告追溯数据查询成功！");
  }
}
