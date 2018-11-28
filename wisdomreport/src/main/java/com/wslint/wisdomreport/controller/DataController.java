package com.wslint.wisdomreport.controller;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.domain.dto.data.WrReport;
import com.wslint.wisdomreport.domain.dto.data.WrReportData;
import com.wslint.wisdomreport.domain.dto.workflow.WrWfTask;
import com.wslint.wisdomreport.service.IDataService;
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
 * 数据处理控制层
 *
 * @author yuxr
 * @since 2018/8/14 9:12
 */
@ApiIgnore
@Api(tags = "2 数据管理接口", description = "提供数据管理的相关接口")
@RestController
@RequestMapping(value = "/data")
public class DataController {

  @Autowired private IDataService iDataService;
  @Autowired private IWorkFlowService iWorkFlowService;
  /**
   * 批次列表查询
   *
   * @param param 前端传入的查询条件
   * @return 查询出的批次信息
   */
  @RequestMapping(value = "/getBatchList", method = RequestMethod.POST)
  public Map getBatchList(@RequestBody WrWfTask param) {
    try {
      if (null == param) {
        return CommonUtils.getReturnMessage(
            Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
      }
      List<WrWfTask> tasks =
          iWorkFlowService.getWrWfTasksByMedicineIdAndStatus(
              param.getMedicineId(), param.getStatus());

      List<Map> rtnMap = new ArrayList<>();
      if (null != tasks) {
        for (WrWfTask task : tasks) {
          Map map = CommonUtils.beanToMap(task);
          rtnMap.add(map);
        }
      }
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_SUCCESS, Constant.RETURN_MESSAGE_SUCCESS, rtnMap);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
    }
  }

  /**
   * 创建批次列表controller //批次号不能重复
   *
   * @param param 创建条件
   * @return 创建结果信息
   */
  @RequestMapping(value = "/createBatch", method = RequestMethod.POST)
  public Map createBatch(@RequestBody List<WrReport> param) {
    try {
      if (null == param || param.size() < Constant.NUM_1 || !checkCreateBatchInfo(param)) {
        return CommonUtils.getReturnMessage(
            Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
      }
      iDataService.createBatch(param);
      // 创建工作流信息
      WrWfTask wrWfTask = new WrWfTask();
      wrWfTask.setMedicineId(param.get(0).getMedicine());
      wrWfTask.setBatchNo(param.get(0).getBatchNo());
      iWorkFlowService.start(wrWfTask);
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_SUCCESS, Constant.RETURN_MESSAGE_SUCCESS, null);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
    }
    // List<WrReport> wrReport = getCreateBatchInfo(param);
  }

  /**
   * 查询某个小类的所有数据
   *
   * @param wrReport 查询条件
   * @return 查询信息
   */
  @ApiOperation(value = "查询某个小类的所有数据接口", notes = "查询某个小类的所有数据")
  @ApiImplicitParam(name = "wrReport", value = "模板信息", required = true, dataType = "WrReport")
  @RequestMapping(value = "/getOneClassData", method = RequestMethod.POST)
  public Map getOneClassData(@RequestBody WrReport wrReport) {
    if (null == wrReport) {
      return ReturnUtils.failureMap("传入参数为空！");
    }
    List<WrReportData> wrReportDataList = iDataService.getOneClassData(wrReport);
    return ReturnUtils.successMap(wrReportDataList, "查询成功！");
  }

  /**
   * 查询某个药品的所有数据
   *
   * @param wrReport 查询条件
   * @return 查询出来的数据
   */
  @ApiOperation(value = "查询某个药品的所有数据接口", notes = "查询某个药品的所有数据")
  @ApiImplicitParam(name = "wrReport", value = "模板信息", required = true, dataType = "WrReport")
  @RequestMapping(value = "/getAllClassData", method = RequestMethod.POST)
  public Map getAllClassData(@RequestBody WrReport wrReport) {
    if (null == wrReport) {
      return ReturnUtils.failureMap("传入参数为空！");
    }
    List<WrReport> wrReportList = iDataService.getAllClassData(wrReport);
    return ReturnUtils.successMap(wrReportList, "查询成功！");
  }

  /**
   * 保存数据controller
   *
   * @param param 需要保存的参数
   * @return 保存成功与否的信息
   */
  @RequestMapping(value = "/saveData", method = RequestMethod.POST)
  public Map saveData(@RequestBody WrReport param) {
    try {
      // 数据检查和预处理
      if (null == param || !checkSaveData(param)) {
        return CommonUtils.getReturnMessage(
            Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
      }
      iDataService.saveReport(param);
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_SUCCESS, Constant.RETURN_MESSAGE_SUCCESS, null);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
    }
  }

  /**
   * 修改数据controller
   *
   * @param param 传入数据
   * @return 返回信息
   */
  @RequestMapping(value = "/modify", method = RequestMethod.POST)
  public Map modify(@RequestBody WrReport param) {
    try {
      // 数据检查和预处理
      if (null == param || !checkSaveData(param) || !checkVersion(param)) {
        return CommonUtils.getReturnMessage(
            Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
      }
      // 修改钱进行数据校验
      List<WrReportData> datas = iDataService.getOneClassData(param);
      iDataService.saveReport(param);
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_SUCCESS, Constant.RETURN_MESSAGE_SUCCESS, null);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
    }
  }
  /**
   * 现场复核controller
   *
   * @param param 需要复核的数据
   * @return 复核成功与否的信息
   */
  @RequestMapping(value = "/review", method = RequestMethod.POST)
  public Map review(@RequestBody WrReport param) {
    try {
      // 数据检查和预处理
      if (null == param || !checkSaveData(param) || !checkVersion(param)) {
        return CommonUtils.getReturnMessage(
            Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
      }
      iDataService.saveReport(param);
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_SUCCESS, Constant.RETURN_MESSAGE_SUCCESS, null);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
    }
  }

  /**
   * 检查非录入数据Version字段
   *
   * @param param 传入数据
   * @return Version字段是否合法
   */
  private boolean checkVersion(WrReport param) {
    if (null != param && null != param.getWrReportDataList()) {
      List<WrReportData> dataList = param.getWrReportDataList();
      for (WrReportData data : dataList) {
        if (data.getVersion() <= Constant.NUM_0) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * 复核列表查看controller
   *
   * @return 当前用户复核任务
   */
  @RequestMapping(value = "/getReviewList", method = RequestMethod.POST)
  public Map getReviewList() {
    try {
      // todo 当前用户id
      List<WrReport> reportList = iDataService.getReviewList(CommonUtils.getCurrentUserId());
      List<Map> rtnMap = new ArrayList<>();
      for (WrReport wrReport : reportList) {
        Map map = CommonUtils.beanToMap(wrReport);
        rtnMap.add(map);
      }
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_SUCCESS, Constant.RETURN_MESSAGE_SUCCESS, rtnMap);
    } catch (Exception e) {
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
    }
  }

  /**
   * 修改复核Controller
   *
   * @return
   */
  @RequestMapping(value = "/getModifyReviewList", method = RequestMethod.POST)
  public Map getModifyReviewList() {
    try {
      // todo 当前用户id
      List<WrReport> reportList = iDataService.getModifyReviewList(CommonUtils.getCurrentUserId());
      List<Map> rtnMap = new ArrayList<>();
      for (WrReport wrReport : reportList) {
        Map map = CommonUtils.beanToMap(wrReport);
        rtnMap.add(map);
      }
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_SUCCESS, Constant.RETURN_MESSAGE_SUCCESS, rtnMap);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
    }
  }

  /**
   * 修改复核通过controller
   *
   * @param param 复核的数据
   * @return 保存的结果信息
   */
  @RequestMapping(value = "/reviewPass", method = RequestMethod.POST)
  public Map reviewPass(@RequestBody WrReport param) {
    try {
      // 数据检查和预处理
      if (null == param || !checkSaveData(param) || !checkVersion(param)) {
        return CommonUtils.getReturnMessage(
            Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
      }
      iDataService.saveReport(param);
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_SUCCESS, Constant.RETURN_MESSAGE_SUCCESS, null);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
    }
  }

  /**
   * 复核不通过controller
   *
   * @param param 复核的数据
   * @return 保存的结果信息
   */
  @RequestMapping(value = "/reviewNotPass", method = RequestMethod.POST)
  public Map reviewNotPass(@RequestBody WrReport param) {
    try {
      // 数据检查和预处理
      if (null == param || !checkSaveData(param) || !checkVersion(param)) {
        return CommonUtils.getReturnMessage(
            Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
      }
      iDataService.saveReport(param);
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_SUCCESS, Constant.RETURN_MESSAGE_SUCCESS, null);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
    }
  }

  /**
   * 修改复核通过controller
   *
   * @param param 复核的数据
   * @return 保存的结果信息
   */
  @RequestMapping(value = "/modifyReviewPass", method = RequestMethod.POST)
  public Map modifyReviewPass(@RequestBody WrReport param) {
    try {
      // 数据检查和预处理
      if (null == param || !checkSaveData(param) || !checkVersion(param)) {
        return CommonUtils.getReturnMessage(
            Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
      }
      iDataService.saveReport(param);
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_SUCCESS, Constant.RETURN_MESSAGE_SUCCESS, null);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
    }
  }

  /**
   * 修改复核不通过controller
   *
   * @param param
   * @return
   */
  @RequestMapping(value = "/modifyReviewNotPass", method = RequestMethod.POST)
  public Map modifyReviewNotPass(@RequestBody WrReport param) {
    try {
      // 数据检查和预处理
      if (null == param || !checkSaveData(param) || !checkVersion(param)) {
        return CommonUtils.getReturnMessage(
            Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
      }
      iDataService.saveReport(param);
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_SUCCESS, Constant.RETURN_MESSAGE_SUCCESS, null);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonUtils.getReturnMessage(
          Constant.RETURN_CODE_FAILURE, Constant.RETURN_MESSAGE_FAILURE, null);
    }
  }
  /**
   * 检查传入保存数据合法性并添加缺省数据
   *
   * @param param 传入保存的数据
   * @return 是否合法
   */
  private boolean checkSaveData(WrReport param) {
    if ((param.getMedicine() | param.getBatchNo() | param.getFirstClass() | param.getSecondClass())
        == Constant.NUM_0) {
      return false;
    } else {
      param.setId(CommonUtils.getNextId());
      param.setGmtCreate(CommonUtils.getNowTime());
      param.setGmtModified(CommonUtils.getNowTime());
      long reportId = param.getId();
      if (null != param.getWrReportDataList()) {
        for (WrReportData data : param.getWrReportDataList()) {
          data.setReportId(reportId);
          data.setGmtCreate(CommonUtils.getNowTime());
          data.setGmtModified(CommonUtils.getNowTime());
        }
      }
      return true;
    }
  }

  /**
   * 验证输入的创建批次的信息是否正确并绑定ID
   *
   * @param param 输入参数
   * @return 返回是否正确
   */
  private boolean checkCreateBatchInfo(List<WrReport> param) {
    long medicine = param.get(0).getMedicine();
    long batchNo = param.get(0).getBatchNo();
    for (WrReport info : param) {
      info.setId(CommonUtils.getNextId());
      info.setGmtCreate(CommonUtils.getNowTime());
      info.setGmtModified(CommonUtils.getNowTime());
      if (medicine != info.getMedicine() || batchNo != info.getBatchNo()) {
        return false;
      }
    }
    return true;
  }

  /**
   * 根据数据id获取历史数据
   *
   * @param wrReportData 传入数据
   * @return 历史数据
   */
  @ApiOperation(value = "查询某个数据的历史信息接口", notes = "查询某个数据的历史信息")
  @ApiImplicitParam(
      name = "wrReportData",
      value = "模板数据信息",
      required = true,
      dataType = "WrReportData")
  @RequestMapping(value = "/getHistoryData", method = RequestMethod.POST)
  public Map getHistoryData(@RequestBody WrReportData wrReportData) {
    // todo 暂时使用这种方式写死
    //    if (param.getDataReportId() == null || param.getDataOrderId() == null) {
    //      return ReturnUtils.failureMap("传入查询数据为空！");
    //    }
    WrReport wrReport =
        iDataService.getReportIdByMedicineIdAndBatchNo(
            wrReportData.getMedicine(), wrReportData.getBatchNo());
    List<WrReportData> wrReportDataList =
        iDataService.getDataByReportIdAndOrderId(wrReport.getId(), wrReportData.getOrderId());
    return ReturnUtils.successMap(wrReportDataList, "查询成功！");
  }
}
