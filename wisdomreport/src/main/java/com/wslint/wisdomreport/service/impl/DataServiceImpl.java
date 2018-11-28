package com.wslint.wisdomreport.service.impl;

import com.wslint.wisdomreport.common.ActivityStatus;
import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.dao.IDataDao;
import com.wslint.wisdomreport.domain.dto.activity.WrReportApprove;
import com.wslint.wisdomreport.domain.dto.data.WrReport;
import com.wslint.wisdomreport.domain.dto.data.WrReportAllData;
import com.wslint.wisdomreport.domain.dto.data.WrReportData;
import com.wslint.wisdomreport.service.IDataService;
import com.wslint.wisdomreport.utils.CommonUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据处理对外提供的接口的实现 通过异常控制事物
 *
 * @author yuxr
 * @since 2018/8/13 16:13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DataServiceImpl implements IDataService {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired private IDataDao iDataDao;

  /**
   * 保存主表和关联从表信息
   *
   * @param wrReport 传入信息
   * @throws Exception 异常信息
   */
  @Override
  public void saveReport(WrReport wrReport) throws Exception {
    // 插入之前先检查主表是否存在相同数据
    Long reportId = iDataDao.getWrReportId(wrReport);
    int iReportNum = 1;
    if (null == reportId) {
      iReportNum = iDataDao.insertWrReport(wrReport);
    } else {
      wrReport.setId(reportId);
      List<WrReportData> dataList = wrReport.getWrReportDataList();
      for (WrReportData data : dataList) {
        data.setReportId(reportId);
      }
    }
    int iReportDataNum = 0;
    List<WrReportData> wrReportDataList = wrReport.getWrReportDataList();
    if (wrReportDataList.size() > 0) {
      iReportDataNum = iDataDao.batchInsertWrReportData(wrReportDataList);
    }
    if (iReportNum != 1 || iReportDataNum != wrReportDataList.size()) {
      logger.debug("成功保存数据与传入数据不匹配！");
      throw new Exception();
    }
  }

  /**
   * 创建批次
   *
   * @param param 批次信息
   * @return 是否创建成功
   */
  @Override
  public void createBatch(List<WrReport> param) throws Exception {
    int i = iDataDao.createBatch(param);
    if (i != param.size()) {
      logger.debug("成功保存数据与传入数据不匹配！");
      throw new Exception();
    }
  }

  /**
   * 构造工作流数据
   *
   * @param param 主表数据
   */
  private WrReportApprove createActivity(WrReport param) {
    long batchNo = param.getBatchNo();
    long medicine = param.getMedicine();

    WrReportApprove wrReportApprove = new WrReportApprove();
    wrReportApprove.setMedicine(medicine);
    wrReportApprove.setBatchNo(batchNo);
    wrReportApprove.setStatus(ActivityStatus.STATUS_PROCESSING.getValue());
    wrReportApprove.setGmtModified(CommonUtils.getNowTime());
    wrReportApprove.setGmtCreate(CommonUtils.getNowTime());
    // todo 缺失人员管理系统，先构造一个
    wrReportApprove.setApprover(Constant.NUM_0);
    return wrReportApprove;
  }

  /**
   * 查询某个小类下的数据
   *
   * @param param 查询条件
   * @return 查询出的数据
   */
  @Override
  public List<WrReportData> getOneClassData(WrReport param) {
    List<WrReportData> dataList = iDataDao.getOneClassData(param);
    // 将相同orderId数据根据version过滤，只取version最大的一条数据返回给客户端
    HashMap<Long, WrReportData> hashMap = new HashMap<>(Constant.NUM_16);
    for (WrReportData data : dataList) {
      if (!hashMap.containsKey(data.getOrderId())) {
        hashMap.put(data.getOrderId(), data);
      } else if (hashMap.get(data.getOrderId()).getVersion() < data.getVersion()) {
        hashMap.put(data.getOrderId(), data);
      }
    }
    return new ArrayList<>(hashMap.values());
  }

  /**
   * 查询某个药品某个批次下所有大类所有小类的所有数据（只返回version最大的数据）
   *
   * @param param 查询条件
   */
  @Override
  public List<WrReport> getAllClassData(WrReport param) {
    List<WrReportAllData> wrReports = iDataDao.getAllClassData(param);
    Map<Long, Integer> map = new HashMap<>(Constant.NUM_16);
    List<WrReport> reportList = new ArrayList<>();
    Integer mapNum = 0;
    for (WrReportAllData wrReportAllData : wrReports) {
      if (!map.containsKey(wrReportAllData.getId())) {
        WrReport wrReport = new WrReport();
        map.put(wrReportAllData.getId(), mapNum++);
        wrReport.setId(wrReportAllData.getId());
        wrReport.setBatchNo(wrReportAllData.getBatchNo());
        wrReport.setMedicine(wrReportAllData.getMedicine());
        wrReport.setFirstClass(wrReportAllData.getFirstClass());
        wrReport.setSecondClass(wrReportAllData.getSecondClass());
        wrReport.setThirdClass(wrReportAllData.getThirdClass());
        wrReport.setClass1(wrReportAllData.getClass1());
        wrReport.setClass2(wrReportAllData.getClass2());
        wrReport.setClass3(wrReportAllData.getClass3());
        wrReport.setHold1(wrReportAllData.getHold1());
        wrReport.setHold2(wrReportAllData.getHold2());
        wrReport.setHold3(wrReportAllData.getHold3());
        wrReport.setGmtCreate(wrReportAllData.getGmtCreate());
        wrReport.setGmtModified(wrReportAllData.getGmtModified());
        reportList.add(wrReport);
      }
      WrReport wrReport = reportList.get(map.get(wrReportAllData.getId()));
      WrReportData wrReportData = new WrReportData();
      wrReportData.setId(wrReportAllData.getDataId());
      wrReportData.setReportId(wrReportAllData.getDataReportId());
      wrReportData.setOrderId(wrReportAllData.getDataOrderId());
      wrReportData.setData(wrReportAllData.getDataData());
      wrReportData.setInputer(wrReportAllData.getDataInputer());
      wrReportData.setReviewer(wrReportAllData.getDataReviewer());
      wrReportData.setChangeReason(wrReportAllData.getDataChangeReason());
      wrReportData.setGmtCreate(wrReportAllData.getDataGmtCreate());
      wrReportData.setGmtModified(wrReportAllData.getDataGmtModified());
      wrReportData.setImageUrl(wrReportAllData.getDataImageUrl());
      wrReportData.setModifyReviewer(wrReportAllData.getDataModifyReviewer());
      wrReportData.setVersion(wrReportAllData.getDataVersion());
      wrReportData.setStatus(wrReportAllData.getDataStatus());
      wrReportData.setHold1(wrReportAllData.getDataHold1());
      wrReportData.setHold2(wrReportAllData.getDataHold2());
      wrReportData.setHold3(wrReportAllData.getDataHold3());
      wrReportData.setHold4(wrReportAllData.getDataHold4());
      wrReportData.setHold5(wrReportAllData.getDataHold5());
      wrReportData.setInputerName(wrReportAllData.getDataInputerName());
      wrReportData.setReviewerName(wrReportAllData.getDataReviewerName());
      wrReportData.setModifyReviewerName(wrReportAllData.getDataModifyReviewerName());
      List<WrReportData> list = wrReport.getWrReportDataList();
      if (null == list) {
        list = new ArrayList<>();
      }
      list.add(wrReportData);
      wrReport.setWrReportDataList(list);
    }
    // 合并数据，只返回相同药品，相同批次，相同order的version最大的值
    for (WrReport wrReport : reportList) {
      Map<Long, WrReportData> map1 = new HashMap<>(Constant.NUM_16);
      long id = wrReport.getId();
      long medicine = wrReport.getMedicine();
      for (WrReportData wrReportData : wrReport.getWrReportDataList()) {
        long orderId = wrReportData.getOrderId();
        if (map1.containsKey(orderId)) {
          WrReportData wrReportData1 = map1.get(orderId);
          if (wrReportData1.getVersion() < wrReportData.getVersion()) {
            map1.put(orderId, wrReportData);
          }
        } else {
          map1.put(id + medicine + orderId, wrReportData);
        }
      }
      wrReport.setWrReportDataList(new ArrayList<>(map1.values()));
    }
    return reportList;
  }
  /**
   * 复核列表查看service实现
   *
   * @param userId 用户id
   * @return 复核列表
   */
  @Override
  public List<WrReport> getReviewList(long userId) {
    List<WrReport> data = iDataDao.getReviewList(userId);
    Map<String, WrReport> map = new HashMap<>(Constant.NUM_16);
    StringBuilder stringBuilder = new StringBuilder();
    if (null != data) {
      for (WrReport wrReport : data) {
        if (null != wrReport) {
          String key =
              stringBuilder.append(wrReport.getMedicine()).append(wrReport.getBatchNo()).toString();
          stringBuilder.delete(0, stringBuilder.length());
          map.put(key, wrReport);
        }
      }
    }
    return new ArrayList<>(map.values());
  }

  /**
   * 修改复核列表查看service实现
   *
   * @param userId 用户id
   * @return
   */
  @Override
  public List<WrReport> getModifyReviewList(long userId) {
    List<WrReport> data = iDataDao.getModifyReviewList(userId);
    Map<String, WrReport> map = new HashMap<>(Constant.NUM_16);
    StringBuilder stringBuilder = new StringBuilder();
    if (null != data) {
      for (WrReport wrReport : data) {
        if (null != wrReport) {
          String key =
              stringBuilder.append(wrReport.getMedicine()).append(wrReport.getBatchNo()).toString();
          stringBuilder.delete(0, stringBuilder.length());
          map.put(key, wrReport);
        }
      }
    }
    return new ArrayList<>(map.values());
  }

  /**
   * 根据数据id获取历史数据
   *
   * @param reportId 模板id
   * @param orderId 空格id
   * @return 历史数据
   */
  @Override
  public List<WrReportData> getDataByReportIdAndOrderId(Long reportId, Long orderId) {
    WrReportData wrReportData = new WrReportData();
    wrReportData.setReportId(reportId);
    wrReportData.setOrderId(orderId);
    return iDataDao.getDataByReportIdAndOrderId(wrReportData);
  }

  /**
   * 根据药品id 和 批次号获取模板信息
   *
   * @param medicine 药品id
   * @param batchNo 批次号
   * @return 模板信息
   */
  @Override
  public WrReport getReportIdByMedicineIdAndBatchNo(Long medicine, Long batchNo) {
    WrReport wrReport = new WrReport();
    wrReport.setMedicine(medicine);
    wrReport.setBatchNo(batchNo);
    return iDataDao.getReportIdByMedicineIdAndBatchNo(wrReport);
  }
}
