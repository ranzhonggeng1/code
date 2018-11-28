package com.wslint.wisdomreport.controller.data;

import com.wslint.wisdomreport.domain.dto.data.report.DataReportDTO;
import com.wslint.wisdomreport.domain.vo.data.DataStatusVO;
import com.wslint.wisdomreport.domain.vo.data.batch.DataBatchVO;
import com.wslint.wisdomreport.domain.vo.data.report.DataBatchReportReturnVO;
import com.wslint.wisdomreport.domain.vo.data.report.DataReportReturnVO;
import com.wslint.wisdomreport.domain.vo.data.report.DataReportSaveVO;
import com.wslint.wisdomreport.service.data.IDataReportService;
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
 * 报告数据接口
 *
 * @author yuxr
 * @since 2018/11/16 09:34
 */
@Api(tags = "报告数据接口", description = "提供报告数据查询接口")
@RestController
@RequestMapping(value = "/data/report")
public class DataReportController {

  /** logger 日志报告 */
  private static final Logger LOGGER = LoggerFactory.getLogger(DataReportController.class);

  @Autowired private IDataReportService iDataReportService;

  /**
   * 保存数据
   *
   * @return 处理结果
   */
  @ApiOperation(value = "保存数据", notes = "提供保存数据功能")
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public Map<String, Object> save(@RequestBody List<DataReportSaveVO> dataReportSaveVOS)
      throws Exception {
    List<DataReportDTO> dataReportDTOList =
        BeanCopyUtil.copyList(dataReportSaveVOS, DataReportDTO.class);
    LOGGER.info("报告数据新增开始！");
    return iDataReportService.save(dataReportDTOList);
  }

  /**
   * 批次报告数据查询
   *
   * @return 报告数据list
   */
  @ApiOperation(value = "批次报告数据查询", notes = "提供批次报告数据查询功能，返回list数据")
  @RequestMapping(value = "/list/batch", method = RequestMethod.POST)
  public Map<String, Object> listBatch(@RequestBody DataBatchVO dataBatchVO) throws Exception {
    List<DataReportDTO> dataReportDTOList =
        iDataReportService.getReportsByBatch(dataBatchVO.getMedicineId(), dataBatchVO.getBatchNo());
    List<DataReportReturnVO> dataReportReturnVOS =
        BeanCopyUtil.copyList(dataReportDTOList, DataReportReturnVO.class);
    LOGGER.info("批次报告数据查询成功！");
    return ReturnUtils.successMap(dataReportReturnVOS, "批次报告数据查询成功！");
  }


  /**
   * 根据状态查询报告数据
   *
   * @return 报告数据list
   */
  @ApiOperation(value = "根据状态查询报告数据", notes = "提供根据状态查询报告数据功能")
  @RequestMapping(value = "/list/status", method = RequestMethod.POST)
  public Map<String, Object> listStatus(@RequestBody DataStatusVO dataStatusVO) throws Exception {
    List<DataReportDTO> dataReportDTOList =
        iDataReportService.getReportsByStatus(dataStatusVO.getStatus());
    List<DataBatchReportReturnVO> dataBatchReportReturnVOList =
        BeanCopyUtil.copyList(dataReportDTOList, DataBatchReportReturnVO.class);
    LOGGER.info("根据状态查询报告数据成功！");
    return ReturnUtils.successMap(dataBatchReportReturnVOList, "根据状态查询报告数据成功！");
  }
}
