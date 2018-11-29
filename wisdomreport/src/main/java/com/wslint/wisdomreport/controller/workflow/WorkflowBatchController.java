package com.wslint.wisdomreport.controller.workflow;

import com.wslint.wisdomreport.constant.ReturnConstant;
import com.wslint.wisdomreport.constant.WorkflowConstant;
import com.wslint.wisdomreport.domain.dto.data.clazz.DataClassDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowBatchRedoDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowBatchTaskDTO;
import com.wslint.wisdomreport.domain.vo.workflow.BatchCommitVO;
import com.wslint.wisdomreport.domain.vo.workflow.BatchPassVO;
import com.wslint.wisdomreport.domain.vo.workflow.BatchRedoVO;
import com.wslint.wisdomreport.domain.vo.workflow.BatchRejectVO;
import com.wslint.wisdomreport.domain.vo.workflow.BatchUndoVO;
import com.wslint.wisdomreport.domain.vo.workflow.BatchWorkflowVO;
import com.wslint.wisdomreport.domain.vo.workflow.ClassIdVO;
import com.wslint.wisdomreport.exception.WorkflowException;
import com.wslint.wisdomreport.service.data.IDataBatchService;
import com.wslint.wisdomreport.service.workflow.IWorkflowBatchService;
import com.wslint.wisdomreport.utils.BeanCopyUtil;
import com.wslint.wisdomreport.utils.ReturnUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 批次数据流程接口
 *
 * @author yuxr
 * @since 2018/11/9 11:29
 */
@Api(tags = "3 工作流接口", description = "提供工作流流转接口")
@RestController
@Transactional(rollbackFor = Exception.class)
@RequestMapping(value = "/workflow/batch")
public class WorkflowBatchController {

  /** logger 日志批次 */
  private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowBatchController.class);

  @Autowired private IDataBatchService iDataBatchService;
  @Autowired private IWorkflowBatchService iWorkflowBatchService;

  /**
   * 批次走工作流
   *
   * @param batchWorkflowVOList 批次工作流参数
   * @return 处理结果
   */
  @ApiOperation(value = "批次走工作流接口", notes = "提供批次走工作流功能")
  @RequestMapping(value = "/work", method = RequestMethod.POST)
  Map<String, Object> work(@RequestBody List<BatchWorkflowVO> batchWorkflowVOList)
      throws Exception {
    List<WorkflowBatchTaskDTO> workflowBatchTaskDTOList =
        BeanCopyUtil.copyList(batchWorkflowVOList, WorkflowBatchTaskDTO.class);
    int result = iWorkflowBatchService.doWorkflow(workflowBatchTaskDTOList);
    if (result == ReturnConstant.WORKFLOW_SUCCESS) {
      LOGGER.info("批次流程流转成功！");
      return ReturnUtils.successMap("批次流程流转成功！");
    }
    LOGGER.info("批次流程流转失败！");
    throw new WorkflowException("批次流程流转失败!");
  }

  /** 批次复测 */
  @ApiOperation(value = "批次复测接口", notes = "提供批次复测功能")
  @RequestMapping(value = "/redo", method = RequestMethod.POST)
  Map<String, Object> redo(@RequestBody List<BatchRedoVO> batchRedoVOS) throws Exception {

    List<WorkflowBatchRedoDTO> workflowBatchRedoDTOS = new ArrayList<>();
    batchRedoVOS.forEach(
        vo -> {
          WorkflowBatchRedoDTO dto = new WorkflowBatchRedoDTO();
          workflowBatchRedoDTOS.add(dto);
          dto.setBatchId(vo.getBatchId());
          dto.setReason(vo.getReason());
          List<DataClassDTO> dataClassDTOS = new ArrayList<>();
          dto.setDataClassDTOS(dataClassDTOS);
          ///          List<Long> ids = vo.getClassIds();
          ///          if (ids != null) {
          ///            ids.forEach(
          ///                id -> {
          ///                  DataClassDTO data = new DataClassDTO();
          ///                  dataClassDTOS.add(data);
          ///                  data.setId(id);
          ///                });
          ///          }
          // fk
          List<ClassIdVO> classIdVOS = vo.getClassIdVOS();
          if (!classIdVOS.isEmpty()) {
            classIdVOS.forEach(
                classVO -> {
                  DataClassDTO data = new DataClassDTO();
                  dataClassDTOS.add(data);
                  data.setFirstClassId(classVO.getFirstClassId());
                  data.setSecondClassId(classVO.getSecondClassId());
                  data.setId(
                      iDataBatchService.fkGetClassIdByRubbish(
                          vo.getBatchId(), classVO.getFirstClassId(), classVO.getSecondClassId()));
                });
          }
          // fk
        });
    int result = iWorkflowBatchService.redo(workflowBatchRedoDTOS);
    if (result == ReturnConstant.WORKFLOW_SUCCESS) {
      LOGGER.info("复测流程流转成功！");
      return ReturnUtils.successMap("复测流程流转成功！");
    }
    LOGGER.info("复测流程流转失败！");
    throw new WorkflowException("批次流程流转失败!");
  }

  /**
   * 提交批次审核接口
   *
   * @param commitVO 提交审核对象
   * @return 处理结果
   */
  @ApiIgnore
  @ApiOperation(value = "提交批次审核接口", notes = "提供提交批次审核功能")
  @RequestMapping(value = "/commitBak", method = RequestMethod.POST)
  public Map<String, Object> commit(@RequestBody BatchCommitVO commitVO) throws Exception {
    WorkflowBatchTaskDTO workflowBatchTaskDTO = new WorkflowBatchTaskDTO();
    workflowBatchTaskDTO.setBatchId(commitVO.getBatchId());
    workflowBatchTaskDTO.setReason(
        commitVO.getReason() == null ? WorkflowConstant.BATCH_REASON_COMMIT : commitVO.getReason());
    workflowBatchTaskDTO.setNextOperator(commitVO.getNextOperator());
    workflowBatchTaskDTO.setOperation(WorkflowConstant.BATCH_OPERATION_COMMIT_BAK);
    int result = iWorkflowBatchService.doWorkflowBak(workflowBatchTaskDTO);
    if (result == ReturnConstant.WORKFLOW_SUCCESS) {
      LOGGER.info("提交批次审核成功！");
      return ReturnUtils.successMap("提交批次审核成功！");
    }
    LOGGER.info("提交批次审核失败！");
    throw new Exception("提交批次审核失败！");
  }

  /**
   * 审核批次通过接口
   *
   * @param batchPassVO 审批通过对象
   * @return 处理结果
   */
  @ApiIgnore
  @ApiOperation(value = "审核批次通过接口", notes = "提供审核批次通过功能")
  @RequestMapping(value = "/passBak", method = RequestMethod.POST)
  public Map<String, Object> pass(@RequestBody BatchPassVO batchPassVO) throws Exception {
    WorkflowBatchTaskDTO workflowBatchTaskDTO = new WorkflowBatchTaskDTO();
    workflowBatchTaskDTO.setBatchId(batchPassVO.getBatchId());
    workflowBatchTaskDTO.setReason(
        batchPassVO.getReason() == null
            ? WorkflowConstant.BATCH_REASON_PASS
            : batchPassVO.getReason());
    workflowBatchTaskDTO.setOperation(WorkflowConstant.BATCH_OPERATION_PASS_BAK);
    int result = iWorkflowBatchService.doWorkflowBak(workflowBatchTaskDTO);
    if (result == ReturnConstant.WORKFLOW_SUCCESS) {
      LOGGER.info("审核批次通过成功！");
      return ReturnUtils.successMap("审核批次通过成功！");
    }
    LOGGER.info("审核批次通过失败！");
    throw new Exception("审核批次通过失败！");
  }

  /**
   * 审核批次驳回接口
   *
   * @param batchRejectVO 审批驳回对象
   * @return 处理结果
   */
  @ApiIgnore
  @ApiOperation(value = "审核批次驳回接口", notes = "提供审核批次驳回功能")
  @RequestMapping(value = "/rejectBak", method = RequestMethod.POST)
  public Map<String, Object> reject(@RequestBody BatchRejectVO batchRejectVO) throws Exception {
    WorkflowBatchTaskDTO workflowBatchTaskDTO = new WorkflowBatchTaskDTO();
    workflowBatchTaskDTO.setBatchId(batchRejectVO.getBatchId());
    workflowBatchTaskDTO.setReason(
        batchRejectVO.getReason() == null
            ? WorkflowConstant.BATCH_REASON_REJECT
            : batchRejectVO.getReason());
    workflowBatchTaskDTO.setOperation(WorkflowConstant.BATCH_OPERATION_REJECT_BAK);
    int result = iWorkflowBatchService.doWorkflowBak(workflowBatchTaskDTO);
    if (result == ReturnConstant.WORKFLOW_SUCCESS) {
      LOGGER.info("审核批次驳回成功！");
      return ReturnUtils.successMap("审核批次驳回成功！");
    }
    LOGGER.info("审核批次驳回失败！");
    throw new Exception("审核批次驳回失败！");
  }

  /**
   * 重做批次实验接口
   *
   * @param batchUndoVO 重做实验对象
   * @return 处理结果
   */
  @ApiIgnore
  @ApiOperation(value = "重做批次实验接口", notes = "提供重做批次实验功能")
  @RequestMapping(value = "/undoBak", method = RequestMethod.POST)
  public Map<String, Object> undo(@RequestBody BatchUndoVO batchUndoVO) throws Exception {
    WorkflowBatchTaskDTO workflowBatchTaskDTO = new WorkflowBatchTaskDTO();
    workflowBatchTaskDTO.setBatchId(batchUndoVO.getBatchId());
    workflowBatchTaskDTO.setReason(
        batchUndoVO.getReason() == null
            ? WorkflowConstant.BATCH_REASON_UNDO
            : batchUndoVO.getReason());
    workflowBatchTaskDTO.setOperation(WorkflowConstant.BATCH_OPERATION_UNDO_BAK);
    int result = iWorkflowBatchService.doWorkflowBak(workflowBatchTaskDTO);
    if (result == ReturnConstant.WORKFLOW_SUCCESS) {
      LOGGER.info("重做批次实验成功！");
      return ReturnUtils.successMap("重做批次实验成功！");
    }
    LOGGER.info("重做批次实验失败！");
    throw new Exception("重做批次实验失败！");
  }
}
