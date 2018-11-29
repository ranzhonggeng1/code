package com.wslint.wisdomreport.controller.workflow;

import com.wslint.wisdomreport.constant.ReturnConstant;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowRecordTaskDTO;
import com.wslint.wisdomreport.domain.vo.data.DataIdVO;
import com.wslint.wisdomreport.domain.vo.workflow.RecordWorkflowVO;
import com.wslint.wisdomreport.domain.vo.workflow.WorkflowRecordTraceReturnVO;
import com.wslint.wisdomreport.service.workflow.IWorkflowRecordService;
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
 * 记录工作流接口
 *
 * @author yuxr
 * @since 2018/11/13 14:22
 */
@Api(tags = "3 工作流接口", description = "提供工作流流转接口")
@RestController
@RequestMapping(value = "/workflow/record")
public class WorkflowRecordController {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowRecordController.class);

  @Autowired private IWorkflowRecordService iWorkflowRecordService;
  /**
   * 记录走工作流
   *
   * @param recordWorkflowVOList 记录工作流参数
   * @return 处理结果
   */
  @ApiOperation(value = "记录走工作流接口", notes = "提供记录走工作流功能")
  @RequestMapping(value = "/work", method = RequestMethod.POST)
  Map<String, Object> work(@RequestBody List<RecordWorkflowVO> recordWorkflowVOList)
      throws Exception {
    List<WorkflowRecordTaskDTO> workflowRecordTaskDTOList =
        BeanCopyUtil.copyList(recordWorkflowVOList, WorkflowRecordTaskDTO.class);
    int result = iWorkflowRecordService.doWorkflow(workflowRecordTaskDTOList);
    if (result == ReturnConstant.WORKFLOW_SUCCESS) {
      LOGGER.info("记录流程流转成功！");
      return ReturnUtils.successMap("记录流程流转成功！");
    }
    LOGGER.info("记录流程流转失败！");
    return ReturnUtils.failureMap("记录流程流转失败！");
  }

  /**
   * 记录数据追溯
   *
   * @return 记录数据list
   */
  @ApiOperation(value = "记录数据追溯", notes = "提供记录数据追溯功能")
  @RequestMapping(value = "/trace", method = RequestMethod.POST)
  public Map<String, Object> trace(@RequestBody DataIdVO dataIdVO) throws Exception {
    List<WorkflowRecordTaskDTO> workflowRecordTaskDTOList =
        iWorkflowRecordService.trace(dataIdVO.getId());
    List<WorkflowRecordTraceReturnVO> workflowRecordTraceReturnVOList =
        BeanCopyUtil.copyList(workflowRecordTaskDTOList, WorkflowRecordTraceReturnVO.class);
    return ReturnUtils.successMap(workflowRecordTraceReturnVOList, "记录追溯数据查询成功！");
  }


}
