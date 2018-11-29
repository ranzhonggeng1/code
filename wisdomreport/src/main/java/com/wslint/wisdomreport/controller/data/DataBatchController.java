package com.wslint.wisdomreport.controller.data;

import com.wslint.wisdomreport.domain.dto.data.batch.DataBatchDTO;
import com.wslint.wisdomreport.domain.dto.data.clazz.DataClassDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowBatchTaskDTO;
import com.wslint.wisdomreport.domain.vo.data.batch.DataBatchGetReturnVO;
import com.wslint.wisdomreport.domain.vo.data.batch.DataBatchGetVO;
import com.wslint.wisdomreport.domain.vo.data.batch.DataBatchScheduleReturnVO;
import com.wslint.wisdomreport.domain.vo.data.batch.DataBatchScheduleVO;
import com.wslint.wisdomreport.domain.vo.data.batch.DataBatchTodoReturnVO;
import com.wslint.wisdomreport.domain.vo.data.batch.DataClassSaveVO;
import com.wslint.wisdomreport.service.data.IDataBatchService;
import com.wslint.wisdomreport.service.workflow.IWorkflowBatchService;
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
 * 批次数据处理controller
 *
 * @author yuxr
 * @since 2018/11/12 10:36
 */
@Api(tags = "2 数据接口", description = "提供数据接口")
@RestController
@RequestMapping(value = "/data/batch")
public class DataBatchController {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(DataBatchController.class);

  @Autowired IWorkflowBatchService iWorkFlowBatchService;
  @Autowired IDataBatchService iDataBatchService;

  /**
   * 创建批次
   *
   * @return 处理结果
   */
  @ApiOperation(value = "创建批次", notes = "提供创建批次功能")
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public Map<String, Object> save(@RequestBody List<DataClassSaveVO> dataClassSaveVOS)
      throws Exception {
    List<DataClassDTO> dataClassDTOList =
        BeanCopyUtil.copyList(dataClassSaveVOS, DataClassDTO.class);
    LOGGER.info("记录数据新增开始！");
    return iDataBatchService.save(dataClassDTOList);
  }

  /**
   * 批次列表查询
   *
   * @param dataBatchGetVO 批次列表查询参数
   * @return 查询出的批次信息
   */
  @ApiOperation(value = "批次列表查询", notes = "提供批次列表查询功能")
  @RequestMapping(value = "/get", method = RequestMethod.POST)
  public Map<String, Object> get(@RequestBody DataBatchGetVO dataBatchGetVO) throws Exception {
    List<DataBatchDTO> dataBatchDTOList =
        iDataBatchService.getDataBatchByMedicineIdAndStatus(
            dataBatchGetVO.getMedicineId(),
            dataBatchGetVO.getStatus(),
            dataBatchGetVO.getOffset(),
            dataBatchGetVO.getLimit());
    if (dataBatchDTOList == null) {
      return ReturnUtils.failureMap("批次列表数据查询失败！");
    }
    // 整理成前端需要的数据
    List<DataBatchGetReturnVO> dataBatchGetReturnVOList =
        BeanCopyUtil.copyList(dataBatchDTOList, DataBatchGetReturnVO.class);
    return ReturnUtils.successMap(dataBatchGetReturnVOList, "批次列表数据查询成功！");
  }

  /**
   * 审批进度查看
   *
   * @param dataBatchScheduleVO 审批进度查询参数
   * @return 审批进度
   */
  @ApiOperation(value = "审批进度查询", notes = "提供审批进度查询功能")
  @RequestMapping(value = "/schedule", method = RequestMethod.POST)
  public Map<String, Object> schedule(@RequestBody DataBatchScheduleVO dataBatchScheduleVO)
      throws Exception {
    List<WorkflowBatchTaskDTO> workflowBatchTaskDTOList =
        iWorkFlowBatchService.getScheduleByBatchId(dataBatchScheduleVO.getBatchId());
    List<DataBatchScheduleReturnVO> dataBatchScheduleReturnVOS =
        BeanCopyUtil.copyList(workflowBatchTaskDTOList, DataBatchScheduleReturnVO.class);
    return ReturnUtils.successMap(dataBatchScheduleReturnVOS, "审批进度查询成功！");
  }

  /**
   * 查看当前用户待办列表
   *
   * @return 待办列表
   */
  @ApiOperation(value = "待办列表查询", notes = "提供待办列表查询功能")
  @RequestMapping(value = "/todo", method = RequestMethod.POST)
  public Map<String, Object> todo() throws Exception {
    List<DataBatchDTO> dataBatchDTOList = iDataBatchService.getDataBatchByNextOperator();
    List<DataBatchTodoReturnVO> dataBatchTodoReturnVOS =
        BeanCopyUtil.copyList(dataBatchDTOList, DataBatchTodoReturnVO.class);
    return ReturnUtils.successMap(dataBatchTodoReturnVOS, "待办列表查询成功！");
  }
}
