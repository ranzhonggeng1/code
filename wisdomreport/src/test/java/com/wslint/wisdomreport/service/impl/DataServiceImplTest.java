package com.wslint.wisdomreport.service.impl;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.dao.IDataDao;
import com.wslint.wisdomreport.dao.WorkFlowDao;
import com.wslint.wisdomreport.domain.dto.data.WrReport;
import com.wslint.wisdomreport.domain.dto.data.WrReportData;
import com.wslint.wisdomreport.domain.dto.workflow.WrWfTask;
import com.wslint.wisdomreport.service.IDataService;
import com.wslint.wisdomreport.util.TestUtils;
import com.wslint.wisdomreport.utils.CommonUtils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DataServiceImplTest {

  @Autowired private IDataService iDataService;
  private MockMvc mockMvc;
  @Autowired private SecurityManager securityManager;
  @Autowired private WebApplicationContext webApplicationContext;
  @Autowired private IDataDao idataDao;
  @Autowired private WorkFlowDao workFlowDao;
  private WrReport wrReport;
  private Timestamp now;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    // 登录必须要添加安全管理
    SecurityUtils.setSecurityManager(securityManager);
    TestUtils.login(
        Constant.STR_ADMIN, Constant.SIR_DEFAULT_R_AP, webApplicationContext, securityManager);

    // 构造数据
    wrReport = new WrReport();
    wrReport.setMedicine(1L);
    wrReport.setBatchNo(2018091402L);
    wrReport.setGmtCreate(CommonUtils.getNowTime());
    wrReport.setGmtModified(CommonUtils.getNowTime());
    now = CommonUtils.getNowTime();
  }

  //  @Test
  //  public void saveReport() {
  //    wrReport.setFirstClass(1L);
  //    wrReport.setSecondClass(2L);
  //    wrReport.setId(CommonUtils.getNextId());
  //    WrReportData data = new WrReportData();
  //    data.setRecordId(0xf001L);
  //    data.setData("2018091401");
  //    data.setVersion(0L);
  //    data.setStatus(1L);
  //    data.setRecordId(1L);
  //    data.setGmtModified(CommonUtils.getNowTime());
  //    data.setGmtCreate(CommonUtils.getNowTime());
  //
  //    wrReport.setFirstClass(1L);
  //    wrReport.setSecondClass(2L);
  //    WrReportData data2 = new WrReportData();
  //    data2.setRecordId(0xf001L);
  //    data2.setData("2018091401");
  //    data2.setVersion(2L);
  //    data2.setStatus(1L);
  //    data2.setRecordId(1L);
  //
  //    data2.setGmtModified(CommonUtils.getNowTime());
  //    data2.setGmtCreate(CommonUtils.getNowTime());
  //    List<WrReportData> list = new ArrayList<>();
  //    list.add(data);
  //    list.add(data2);
  //
  //    wrReport.setWrReportDataList(list);
  //    try {
  //      iDataService.saveReport(wrReport);
  //    } catch (Exception e) {
  //      e.printStackTrace();
  //    }
  //    List<WrReportData> dataList = idataDao.getOneClassData(wrReport);
  //    Assert.assertEquals(wrReport.getWrReportDataList().size() * 2, dataList.size() * 2);
  //    Long reportId = dataList.get(0).getReportId();
  //    for (WrReportData reportData : dataList) {
  //      Assert.assertEquals(reportId, reportData.getReportId());
  //    }
  //  }

  @Test
  public void createBatch() {
    WrReport report = new WrReport();
    WrReport report1 = new WrReport();
    WrReport report2 = new WrReport();
    WrReport report3 = new WrReport();
    report.setMedicine(1L);
    long nextKey = CommonUtils.getNextId();
    report.setId(nextKey);
    report.setBatchNo(2018091402L);
    report.setGmtModified(now);
    report.setGmtCreate(now);

    report1.setMedicine(1L);
    long nextKey1 = CommonUtils.getNextId();
    report1.setId(nextKey1);
    report1.setBatchNo(2018091402L);
    report1.setGmtModified(now);
    report1.setGmtCreate(now);

    report2.setMedicine(1L);
    long nextKey2 = CommonUtils.getNextId();
    report2.setId(nextKey2);
    report2.setBatchNo(2018091402L);
    report2.setGmtModified(now);
    report2.setGmtCreate(now);

    long nextKey3 = CommonUtils.getNextId();
    report3.setId(nextKey3);
    report3.setMedicine(1L);
    report3.setBatchNo(2018091402L);
    report3.setGmtModified(now);
    report3.setGmtCreate(now);

    report.setFirstClass(1L);
    report.setSecondClass(1L);
    report1.setFirstClass(1L);
    report1.setSecondClass(2L);
    report2.setFirstClass(2L);
    report2.setSecondClass(1L);
    report3.setFirstClass(3L);
    report3.setSecondClass(1L);

    List<WrReport> list = new ArrayList<>();
    list.add(report);
    list.add(report1);
    list.add(report2);
    list.add(report3);

    try {
      iDataService.createBatch(list);
    } catch (Exception e) {
      e.printStackTrace();
    }
    WrWfTask wrWfTask = new WrWfTask();
    wrWfTask.setMedicineId(2018091402L);
    wrWfTask.setStatus(1);
    wrReport.setMedicine(1L);
    wrReport.setBatchNo(2018091402L);
    long reportId = idataDao.getWrReportId(report);
    long reportId1 = idataDao.getWrReportId(report1);
    long reportId2 = idataDao.getWrReportId(report2);
    long reportId3 = idataDao.getWrReportId(report3);
    Assert.assertEquals(reportId, nextKey);
    Assert.assertEquals(reportId1, nextKey1);
    Assert.assertEquals(reportId2, nextKey2);
    Assert.assertEquals(reportId3, nextKey3);
  }

  //  @Test
  //  public void getOneClassData() {
  //    wrReport.setFirstClass(1L);
  //    wrReport.setSecondClass(2L);
  //    wrReport.setId(CommonUtils.getNextId());
  //    wrReport.setGmtCreate(CommonUtils.getNowTime());
  //    wrReport.setGmtModified(CommonUtils.getNowTime());
  //
  //    WrReportData data = new WrReportData();
  //    data.setReportId(wrReport.getId());
  //    data.setData("123456");
  //    data.setRecordId(1L);
  //    data.setGmtCreate(CommonUtils.getNowTime());
  //    data.setGmtModified(CommonUtils.getNowTime());
  //    List<WrReportData> list = new ArrayList<>();
  //    list.add(data);
  //    wrReport.setWrReportDataList(list);
  //    try {
  //      iDataService.saveReport(wrReport);
  //    } catch (Exception e) {
  //      e.printStackTrace();
  //    }
  //    List<WrReportData> dataList = idataDao.getOneClassData(wrReport);
  //    Assert.assertEquals(dataList.size(), wrReport.getWrReportDataList().size());
  //    for (int i = 0; i < dataList.size(); i++) {
  //      WrReportData data1 = dataList.get(i);
  //      WrReportData data2 = wrReport.getWrReportDataList().get(i);
  //      Assert.assertEquals(data1.getData(), data2.getData());
  //    }
  //    List<WrReportData> dataList1 = iDataService.getOneClassData(wrReport);
  //
  //    Assert.assertEquals(dataList.size(), wrReport.getWrReportDataList().size());
  //    for (int i = 0; i < dataList1.size(); i++) {
  //      WrReportData data1 = dataList.get(i);
  //      WrReportData data2 = wrReport.getWrReportDataList().get(i);
  //      Assert.assertEquals(data1.getData(), data2.getData());
  //    }
  //
  //    wrReport.getWrReportDataList().get(0).setVersion(1L);
  //    try {
  //      iDataService.saveReport(wrReport);
  //    } catch (Exception e) {
  //      e.printStackTrace();
  //    }
  //    List<WrReportData> dataList2 = iDataService.getOneClassData(wrReport);
  //    for (int i = 0; i < dataList2.size(); i++) {
  //      WrReportData data1 = dataList.get(i);
  //      WrReportData data2 = wrReport.getWrReportDataList().get(i);
  //      Assert.assertEquals(data1.getData(), data2.getData());
  //    }
  //  }

  //  @Test
  //  public void getAllClassData() {
  //    wrReport.setFirstClass(1L);
  //    wrReport.setSecondClass(5L);
  //    wrReport.setId(CommonUtils.getNextId());
  //    wrReport.setGmtCreate(CommonUtils.getNowTime());
  //    wrReport.setGmtModified(CommonUtils.getNowTime());
  //
  //    WrReportData data = new WrReportData();
  //    data.setReportId(wrReport.getId());
  // data.setRecordId(1L);
  //    data.setGmtCreate(CommonUtils.getNowTime());
  //    data.setGmtModified(CommonUtils.getNowTime());
  //    List<WrReportData> list = new ArrayList<>();
  //    list.add(data);
  //    wrReport.setWrReportDataList(list);
  //    try {
  //      iDataService.saveReport(wrReport);
  //    } catch (Exception e) {
  //      e.printStackTrace();
  //    }
  //    List<WrReportData> dataList = idataDao.getOneClassData(wrReport);
  //    Assert.assertEquals(dataList.size(), wrReport.getWrReportDataList().size());
  //    for (int i = 0; i < dataList.size(); i++) {
  //      WrReportData data1 = dataList.get(i);
  //      WrReportData data2 = wrReport.getWrReportDataList().get(i);
  //      Assert.assertEquals(data1.getData(), data2.getData());
  //    }
  //    wrReport.setFirstClass(1L);
  //    wrReport.setSecondClass(6L);
  //    wrReport.setId(CommonUtils.getNextId());
  //    wrReport.setGmtCreate(CommonUtils.getNowTime());
  //    wrReport.setGmtModified(CommonUtils.getNowTime());
  //
  //    WrReportData data1 = new WrReportData();
  //    data1.setReportId(wrReport.getId());
  //    data1.setData("789456");
  //    data1.setGmtCreate(CommonUtils.getNowTime());
  //    data1.setGmtModified(CommonUtils.getNowTime());
  //    List<WrReportData> list1 = new ArrayList<>();
  //    list1.add(data1);
  //    wrReport.setWrReportDataList(list1);
  //    try {
  //      iDataService.saveReport(wrReport);
  //    } catch (Exception e) {
  //      e.printStackTrace();
  //    }
  //    List<WrReportData> dataList2 = idataDao.getOneClassData(wrReport);
  //    Assert.assertEquals(dataList2.size(), wrReport.getWrReportDataList().size());
  //    for (int i = 0; i < dataList2.size(); i++) {
  //      WrReportData data3 = dataList2.get(i);
  //      WrReportData data4 = wrReport.getWrReportDataList().get(i);
  //      Assert.assertEquals(data3.getData(), data4.getData());
  //    }
  //    List<WrReport> allDataList = iDataService.getAllClassData(wrReport);
  //    Assert.assertEquals(allDataList.size(), list.size() + list1.size());
  //  }

  @Test
  public void getReviewList() {
    wrReport.setId(CommonUtils.getNextId());
    wrReport.setFirstClass(1L);
    wrReport.setSecondClass(1L);
    Map<String, WrReport> map = new HashMap<>();
    WrReportData data = new WrReportData();
    data.setReportId(wrReport.getId());
    data.setStatus(4L);
    data.setReviewer(0L);
    data.setData("测试复核列表");
    data.setGmtCreate(CommonUtils.getNowTime());
    data.setGmtModified(CommonUtils.getNowTime());

    List<WrReportData> dataList = new ArrayList<>();
    dataList.add(data);
    wrReport.setWrReportDataList(dataList);
    StringBuilder stringBuilder = new StringBuilder();
    String key =
        stringBuilder.append(wrReport.getMedicine()).append(wrReport.getBatchNo()).toString();
    map.put(key, wrReport);
    try {
      iDataService.saveReport(wrReport);
    } catch (Exception e) {
      e.printStackTrace();
    }
    List<WrReportData> dataList2 = idataDao.getOneClassData(wrReport);
    for (int i = 0; i < dataList2.size(); i++) {
      WrReportData data3 = dataList2.get(i);
      WrReportData data4 = wrReport.getWrReportDataList().get(i);
      Assert.assertEquals(data3.getData(), data4.getData());
    }

    wrReport.setBatchNo(2018091405L);
    wrReport.setMedicine(1L);
    wrReport.setFirstClass(1L);
    wrReport.setSecondClass(1L);
    wrReport.setId(CommonUtils.getNextId());

    data.setReportId(wrReport.getId());
    data.setStatus(4L);
    data.setReviewer(0L);
    data.setData("测试复核列表");
    data.setGmtCreate(CommonUtils.getNowTime());
    data.setGmtModified(CommonUtils.getNowTime());

    dataList = new ArrayList<>();
    dataList.add(data);
    wrReport.setWrReportDataList(dataList);
    stringBuilder = new StringBuilder();
    String key1 =
        stringBuilder.append(wrReport.getMedicine()).append(wrReport.getBatchNo()).toString();
    map.put(key1, wrReport);
    try {
      iDataService.saveReport(wrReport);
    } catch (Exception e) {
      e.printStackTrace();
    }
    List<WrReportData> dataList3 = idataDao.getOneClassData(wrReport);
    for (int i = 0; i < dataList3.size(); i++) {
      WrReportData data3 = dataList3.get(i);
      WrReportData data4 = wrReport.getWrReportDataList().get(i);
      Assert.assertEquals(data3.getData(), data4.getData());
    }

    List<WrReport> reviewList = iDataService.getReviewList(0);
    for (WrReport wrReport : reviewList) {
      Assert.assertTrue(map.containsKey(wrReport.getMedicine() + "" + wrReport.getBatchNo()));
    }
  }

  @Test
  public void getModifyReviewList() {
    wrReport.setId(CommonUtils.getNextId());
    wrReport.setFirstClass(1L);
    wrReport.setSecondClass(1L);
    Map<String, WrReport> map = new HashMap<>();
    WrReportData data = new WrReportData();
    data.setReportId(wrReport.getId());
    data.setStatus(6L);
    data.setReviewer(0L);
    data.setData("测试复核列表");
    data.setGmtCreate(CommonUtils.getNowTime());
    data.setGmtModified(CommonUtils.getNowTime());

    List<WrReportData> dataList = new ArrayList<>();
    dataList.add(data);
    wrReport.setWrReportDataList(dataList);
    StringBuilder stringBuilder = new StringBuilder();
    String key =
        stringBuilder.append(wrReport.getMedicine()).append(wrReport.getBatchNo()).toString();
    map.put(key, wrReport);
    try {
      iDataService.saveReport(wrReport);
    } catch (Exception e) {
      e.printStackTrace();
    }
    List<WrReportData> dataList2 = idataDao.getOneClassData(wrReport);
    for (int i = 0; i < dataList2.size(); i++) {
      WrReportData data3 = dataList2.get(i);
      WrReportData data4 = wrReport.getWrReportDataList().get(i);
      Assert.assertEquals(data3.getData(), data4.getData());
    }

    wrReport.setBatchNo(2018091405L);
    wrReport.setMedicine(1L);
    wrReport.setFirstClass(1L);
    wrReport.setSecondClass(1L);
    wrReport.setId(CommonUtils.getNextId());

    data.setReportId(wrReport.getId());
    data.setStatus(6L);
    data.setReviewer(0L);
    data.setData("测试复核列表");
    data.setGmtCreate(CommonUtils.getNowTime());
    data.setGmtModified(CommonUtils.getNowTime());

    dataList = new ArrayList<>();
    dataList.add(data);
    wrReport.setWrReportDataList(dataList);
    stringBuilder = new StringBuilder();
    String key1 =
        stringBuilder.append(wrReport.getMedicine()).append(wrReport.getBatchNo()).toString();
    map.put(key1, wrReport);
    try {
      iDataService.saveReport(wrReport);
    } catch (Exception e) {
      e.printStackTrace();
    }
    List<WrReportData> dataList3 = idataDao.getOneClassData(wrReport);
    for (int i = 0; i < dataList3.size(); i++) {
      WrReportData data3 = dataList3.get(i);
      WrReportData data4 = wrReport.getWrReportDataList().get(i);
      Assert.assertEquals(data3.getData(), data4.getData());
    }

    List<WrReport> reviewList = iDataService.getReviewList(0);
    for (WrReport wrReport : reviewList) {
      Assert.assertTrue(map.containsKey(wrReport.getMedicine() + "" + wrReport.getBatchNo()));
    }
  }
}
