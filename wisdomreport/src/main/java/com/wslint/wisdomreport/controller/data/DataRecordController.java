package com.wslint.wisdomreport.controller.data;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.domain.dto.data.record.DataRecordDTO;
import com.wslint.wisdomreport.domain.vo.data.DataStatusVO;
import com.wslint.wisdomreport.domain.vo.data.batch.DataBatchVO;
import com.wslint.wisdomreport.domain.vo.data.clazz.DataClassVO;
import com.wslint.wisdomreport.domain.vo.data.record.DataBatchRecordReturnVO;
import com.wslint.wisdomreport.domain.vo.data.record.DataClassRecordReturnVO;
import com.wslint.wisdomreport.domain.vo.data.record.DataRecordRemarkSaveVO;
import com.wslint.wisdomreport.domain.vo.data.record.DataRecordReturnVO;
import com.wslint.wisdomreport.domain.vo.data.record.DataRecordSaveVO;
import com.wslint.wisdomreport.service.data.IDataBatchService;
import com.wslint.wisdomreport.service.data.IDataRecordService;
import com.wslint.wisdomreport.utils.BeanCopyUtil;
import com.wslint.wisdomreport.utils.ReturnUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 记录数据接口
 *
 * @author yuxr
 * @since 2018/11/16 09:34
 */
@Api(tags = "2 数据接口", description = "提供数据接口")
@RestController
@RequestMapping(value = "/data/record")
public class DataRecordController {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(DataRecordController.class);

  @Autowired private IDataRecordService iDataRecordService;
  @Autowired private IDataBatchService iDataBatchService;

  /**
   * 保存数据
   *
   * @return 处理结果
   */
  @ApiOperation(value = "保存记录数据", notes = "提供保存数据功能")
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public Map<String, Object> save(@RequestBody List<DataRecordSaveVO> dataRecordSaveVOS)
      throws Exception {
    // fk
    List<DataRecordDTO> dataRecordDTOList = new ArrayList<>();
    dataRecordSaveVOS.forEach(
        vo -> {
          DataRecordDTO data = new DataRecordDTO();
          data.setOrderId(vo.getOrderId());
          data.setData(vo.getData());
          data.setImgUrl(vo.getImgUrl());
          data.setClassId(
              iDataBatchService.fkGetClassIdByRubbish(
                  vo.getMedicineId(),
                  vo.getBatchNo(),
                  vo.getFirstClassId(),
                  vo.getSecondClassId()));
          dataRecordDTOList.add(data);
        });
    // fk
    ///    List<DataRecordDTO> dataRecordDTOList =
    ///        BeanCopyUtil.copyList(dataRecordSaveVOS, DataRecordDTO.class);
    LOGGER.info("记录数据新增开始！");
    return iDataRecordService.save(dataRecordDTOList);
  }

  /**
   * 保存记录备注
   *
   * @param dataRecordRemarkSaveVOS 备注数据
   * @return 处理结果
   */
  @ApiOperation(value = "保存记录备注", notes = "提供保存记录备注功能")
  @RequestMapping(value = "/remark/save", method = RequestMethod.POST)
  public Map<String, Object> saveRemark(
      @RequestBody List<DataRecordRemarkSaveVO> dataRecordRemarkSaveVOS) throws Exception {
    // fk
    List<DataRecordDTO> dataRecordDTOList = new ArrayList<>();
    dataRecordRemarkSaveVOS.forEach(
        vo -> {
          DataRecordDTO data = new DataRecordDTO();
          data.setOrderId(vo.getOrderId());
          data.setRemark(vo.getRemark());
          data.setClassId(
              iDataBatchService.fkGetClassIdByRubbish(
                  vo.getMedicineId(),
                  vo.getBatchNo(),
                  vo.getFirstClassId(),
                  vo.getSecondClassId()));
          dataRecordDTOList.add(data);
        });
    // fk
    LOGGER.info("记录数据备注开始！");
    return iDataRecordService.saveRemark(dataRecordDTOList);
  }

  /**
   * 类别记录数据查询
   *
   * @return 记录数据list
   */
  @ApiOperation(value = "类别记录数据查询", notes = "提供类别记录数据查询功能")
  @RequestMapping(value = "/list/class", method = RequestMethod.POST)
  public Map<String, Object> listClass(@RequestBody DataClassVO classVO) throws Exception {
    LOGGER.info("类别记录数据查询开始！");
    List<DataRecordDTO> dataRecordDTOList =
        iDataRecordService.getRecordsByClass(
            classVO.getMedicineId(),
            classVO.getBatchNo(),
            classVO.getFirstClassId(),
            classVO.getSecondClassId());
    List<DataRecordReturnVO> dataClassRecordReturnVOS =
        BeanCopyUtil.copyList(dataRecordDTOList, DataRecordReturnVO.class);
    LOGGER.info("类别记录数据查询成功！");
    return ReturnUtils.successMap(dataClassRecordReturnVOS, "类别记录数据查询成功！");
  }

  /**
   * 批次记录数据查询
   *
   * @return 记录数据list
   */
  @ApiOperation(value = "批次记录数据查询", notes = "提供批次记录数据查询功能，返回list数据")
  @RequestMapping(value = "/list/batch", method = RequestMethod.POST)
  public Map<String, Object> listBatch(@RequestBody DataBatchVO dataBatchVO) throws Exception {
    LOGGER.info("批次记录数据查询开始！");
    List<DataRecordDTO> dataRecordDTOList =
        iDataRecordService.getRecordsByBatch(dataBatchVO.getMedicineId(), dataBatchVO.getBatchNo());
    List<DataClassRecordReturnVO> dataClassRecordReturnVOS =
        BeanCopyUtil.copyList(dataRecordDTOList, DataClassRecordReturnVO.class);
    LOGGER.info("批次记录数据查询成功！");
    return ReturnUtils.successMap(dataClassRecordReturnVOS, "批次记录数据查询成功！");
  }

  /**
   * 批次记录数据查询
   *
   * @return 记录数据map
   */
  @ApiIgnore
  @ApiOperation(value = "批次记录数据查询", notes = "提供批次记录数据查询功能，返回map数据")
  @RequestMapping(value = "/map/batch", method = RequestMethod.POST)
  public Map<String, Object> mapBatch(@RequestBody DataBatchVO dataBatchVO) throws Exception {
    LOGGER.info("批次记录数据查询开始！");
    List<DataRecordDTO> dataRecordDTOList =
        iDataRecordService.getRecordsByBatch(dataBatchVO.getMedicineId(), dataBatchVO.getBatchNo());
    Map<Long, Map<Long, List<DataClassRecordReturnVO>>> firstSecondClassMap = new HashMap<>();
    for (DataRecordDTO dataRecordDTO : dataRecordDTOList) {
      Map<Long, List<DataClassRecordReturnVO>> secondClassDataMap =
          firstSecondClassMap.computeIfAbsent(
              dataRecordDTO.getFirstClassId(), k -> new HashMap<>());
      List<DataClassRecordReturnVO> datas =
          secondClassDataMap.computeIfAbsent(
              dataRecordDTO.getSecondClassId(), k -> new ArrayList<>());
      DataClassRecordReturnVO data =
          BeanCopyUtil.copyBean(dataRecordDTO, DataClassRecordReturnVO.class);
      datas.add(data);
    }
    Map<String, Object> rtnMap = new HashMap<>();
    firstSecondClassMap.forEach(
        (firstMapKey, firstMapValue) -> {
          Map<String, Object> rtnDataMap = new HashMap<>();
          rtnMap.put(Constant.STR_FIRST_CLASS_ID, firstMapKey);
          rtnMap.put(Constant.STR_FIRST_CLASS_LIST, rtnDataMap);
          firstMapValue.forEach(
              (dataKey, dataValue) -> {
                rtnDataMap.put(Constant.STR_SECOND_CLASS_ID, dataKey);
                rtnDataMap.put(Constant.STR_SECOND_CLASS_LIST, dataValue);
              });
        });
    LOGGER.info("批次记录数据查询成功！");
    return ReturnUtils.successMap(rtnMap, "批次记录数据查询成功！");
  }

  /**
   * 根据状态查询记录数据
   *
   * @return 记录数据list
   */
  @ApiOperation(value = "根据状态查询记录数据", notes = "提供根据状态查询记录数据功能")
  @RequestMapping(value = "/list/status", method = RequestMethod.POST)
  public Map<String, Object> listStatus(@RequestBody DataStatusVO dataStatusVO) throws Exception {
    LOGGER.info("根据状态查询记录数据开始！");
    List<DataRecordDTO> dataRecordDTOList =
        iDataRecordService.getRecordsByStatus(dataStatusVO.getStatus());
    List<DataBatchRecordReturnVO> dataBatchRecordReturnVOList =
        BeanCopyUtil.copyList(dataRecordDTOList, DataBatchRecordReturnVO.class);
    LOGGER.info("根据状态查询记录数据成功！");
    return ReturnUtils.successMap(dataBatchRecordReturnVOList, "根据状态查询记录数据成功！");
  }
}
