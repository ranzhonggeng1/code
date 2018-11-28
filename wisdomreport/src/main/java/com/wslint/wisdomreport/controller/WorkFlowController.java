package com.wslint.wisdomreport.controller;

import com.wslint.wisdomreport.domain.dto.workflow.WrWfTask;
import com.wslint.wisdomreport.service.IWorkFlowService;
import com.wslint.wisdomreport.utils.CommonUtils;
import com.wslint.wisdomreport.utils.ReturnUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 工作流控制
 *
 * @author yuxr
 * @since 2018/9/13 16:10
 */
@ApiIgnore
@Api(tags = "3 流程管理接口", description = "提供流程管理的相关接口")
@RestController
@RequestMapping(value = "/workflow")
public class WorkFlowController {

  @Autowired private IWorkFlowService iWorkFlowService;

  /**
   * 发起审批controller
   *
   * @param wrWfTask 实验批次信息
   * @return 发起审批状态信息
   */
  @ApiOperation(value = "发起审批接口", notes = "发起审批")
  @ApiImplicitParam(name = "wrWfTask", value = "流程信息", required = true, dataType = "WrWfTask")
  @RequestMapping(value = "/startApprove", method = RequestMethod.POST)
  public Map startApprove(@RequestBody WrWfTask wrWfTask) {
    // 数据检查和预处理
    if (null == wrWfTask.getTaskId()) {
      return ReturnUtils.failureMap("任务ID为空！");
    }
    if (iWorkFlowService.commit(wrWfTask)) {
      return ReturnUtils.successMap("发起审批成功！");
    }
    return ReturnUtils.failureMap("发起审批失败！");
  }

  /**
   * 执行审批controller
   *
   * @param wrWfTask 前端传入的审批信息
   * @return 审批结果
   */
  @ApiOperation(value = "执行审批接口", notes = "执行审批")
  @ApiImplicitParam(name = "wrWfTask", value = "流程信息", required = true, dataType = "WrWfTask")
  @RequestMapping(value = "/executeApprove", method = RequestMethod.POST)
  public Map executeApprove(@RequestBody WrWfTask wrWfTask) {
    // 数据检查和预处理
    if (null == wrWfTask.getTaskId()) {
      return ReturnUtils.failureMap("任务ID为空！");
    }
    if (wrWfTask.isPass()) {
      if (!wrWfTask.isEnd()) {
        if (iWorkFlowService.passReview(wrWfTask)) {
          return ReturnUtils.successMap("执行继续审批成功！");
        }
      } else {
        if (iWorkFlowService.passEnd(wrWfTask)) {
          return ReturnUtils.successMap("执行审批结束成功！");
        }
      }
    } else {
      if (iWorkFlowService.fail(wrWfTask)) {
        return ReturnUtils.successMap("执行审批不通过成功！");
      }
    }
    return ReturnUtils.failureMap("执行审批失败！");
  }

  /**
   * 审批进度查看controller
   *
   * @param param 查询条件
   * @return 审批进度
   */
  @ApiOperation(value = "查询审批进度接口", notes = "查询审批进度")
  @ApiImplicitParam(name = "wrWfTask", value = "流程信息", required = true, dataType = "WrWfTask")
  @RequestMapping(value = "/approveSchedule", method = RequestMethod.POST)
  public Map approveSchedule(@RequestBody WrWfTask param) {
    // 数据检查和预处理
    if (null == param.getMedicineId() || null == param.getBatchNo()) {
      return ReturnUtils.failureMap("药品ID或批次号为空！");
    }
    List<WrWfTask> wrWfTasks =
        iWorkFlowService.getWrWfAllTasksByMedicineIdAndBatchNo(
            param.getMedicineId(), param.getBatchNo());
    List<Map> rtnMap = new ArrayList<>();
    for (WrWfTask wrWfTask : wrWfTasks) {
      Map map = CommonUtils.beanToMap(wrWfTask);
      rtnMap.add(map);
    }
    return ReturnUtils.successMap(rtnMap, "审批进度查看成功！");
  }

  /**
   * 查看审批任务列表controller
   *
   * @return 审批列表
   */
  @ApiOperation(value = "查询审批任务接口", notes = "查询审批任务")
  @RequestMapping(value = "/getApproveList", method = RequestMethod.POST)
  public Map getApproveList() {
    List<WrWfTask> result =
        iWorkFlowService.getWrWfCurrentTasksByReviewerId(CommonUtils.getCurrentUserId());
    List<Map> rtnMap = new ArrayList<>();
    for (WrWfTask wrWfTask : result) {
      Map map = CommonUtils.beanToMap(wrWfTask);
      rtnMap.add(map);
    }
    return ReturnUtils.successMap(rtnMap, "审批任务查看成功！");
  }
}
