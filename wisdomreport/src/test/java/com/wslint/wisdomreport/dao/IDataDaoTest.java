package com.wslint.wisdomreport.dao;

import com.wslint.wisdomreport.domain.dto.data.WrReport;
import com.wslint.wisdomreport.domain.dto.data.WrReportAllData;
import com.wslint.wisdomreport.domain.dto.data.WrReportData;
import com.wslint.wisdomreport.utils.CommonUtils;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class IDataDaoTest {

  @Autowired private IDataDao iDataDao;

  @Test
  public void insertWrReport() {
    // 拼装数据
    WrReport wrReport = new WrReport();
    wrReport.setId(CommonUtils.getNextId());
    wrReport.setBatchNo(213L);
    wrReport.setMedicine(123L);
    wrReport.setFirstClass(332L);
    wrReport.setSecondClass(3213L);
    wrReport.setThirdClass(1111111111L);
    wrReport.setClass1(231L);
    wrReport.setClass2(2321321L);
    wrReport.setClass3(12321321L);
    wrReport.setHold1(null);
    wrReport.setHold2("hold2");
    wrReport.setHold3("hold4");
    wrReport.setGmtCreate(CommonUtils.getNowTime());
    wrReport.setGmtModified(CommonUtils.getNowTime());

    // 插入数据，检查成功条数
    int i = iDataDao.insertWrReport(wrReport);
    Assert.assertEquals(1, i);
  }

  @Test
  public void batchInsertWrReport() {
    // 拼装数据
    List<WrReport> wrReportList = new ArrayList<>();

    WrReport wrReport1 = new WrReport();
    wrReport1.setId(CommonUtils.getNextId());
    wrReport1.setBatchNo(88L);
    wrReport1.setMedicine(88L);
    wrReport1.setFirstClass(88L);
    wrReport1.setSecondClass(88L);
    wrReport1.setGmtCreate(CommonUtils.getNowTime());
    wrReport1.setGmtModified(CommonUtils.getNowTime());
    wrReportList.add(wrReport1);

    WrReport wrReport2 = new WrReport();
    wrReport2.setId(CommonUtils.getNextId());
    wrReport2.setBatchNo(99L);
    wrReport2.setMedicine(99L);
    wrReport2.setFirstClass(99L);
    wrReport2.setSecondClass(99L);
    wrReport2.setGmtCreate(CommonUtils.getNowTime());
    wrReport2.setGmtModified(CommonUtils.getNowTime());
    wrReportList.add(wrReport2);

    // 插入数据，检查成功条数
    int i = iDataDao.batchInserWrReport(wrReportList);
    Assert.assertEquals(wrReportList.size(), i);
  }

  @Test
  public void batchInsertWrReportData() {
    // 拼装一组待插入的数据
    List<WrReportData> wrReportDataList = new ArrayList<>();

    WrReportData wrReportData = new WrReportData();
    wrReportData.setReportId(CommonUtils.getNextId());
    wrReportData.setOrderId(1L);
    wrReportData.setData("testData1");
    wrReportData.setInputer(12312L);
    wrReportData.setReviewer(12312321L);
    wrReportData.setChangeReason("no reason!");
    wrReportData.setGmtCreate(CommonUtils.getNowTime());
    wrReportData.setGmtModified(CommonUtils.getNowTime());
    wrReportData.setVersion(1L);
    wrReportData.setStatus(1L);
    wrReportData.setHold1("hold1");
    wrReportData.setHold2("hold2");
    wrReportData.setHold3("hold3");
    wrReportData.setHold4(111L);
    wrReportData.setHold5(222L);
    wrReportDataList.add(wrReportData);

    wrReportData = new WrReportData();
    wrReportData.setReportId(CommonUtils.getNextId());
    wrReportData.setOrderId(2L);
    wrReportData.setData("testData2");
    wrReportData.setInputer(123L);
    wrReportData.setReviewer(12312L);
    wrReportData.setChangeReason("no reason!");
    wrReportData.setGmtCreate(CommonUtils.getNowTime());
    wrReportData.setGmtModified(CommonUtils.getNowTime());
    wrReportData.setVersion(2L);
    wrReportData.setStatus(2L);
    wrReportDataList.add(wrReportData);
    // 批量插入，检查成功条数
    int i = iDataDao.batchInsertWrReportData(wrReportDataList);
    Assert.assertEquals(2, i);
  }

  /** 创建批次dao测试 */
  @Test
  public void createBatch() {
    List data = new ArrayList();
    WrReport wrReport = new WrReport();
    wrReport.setId(CommonUtils.getNextId());
    wrReport.setMedicine(1L);
    wrReport.setBatchNo(222L);
    wrReport.setSecondClass(1L);
    wrReport.setFirstClass(1L);
    wrReport.setGmtCreate(CommonUtils.getNowTime());
    wrReport.setGmtModified(CommonUtils.getNowTime());

    WrReport wrReport2 = new WrReport();
    wrReport2.setId(CommonUtils.getNextId());
    wrReport2.setMedicine(1L);
    wrReport2.setBatchNo(222L);
    wrReport2.setSecondClass(1L);
    wrReport2.setFirstClass(2L);
    wrReport2.setGmtCreate(CommonUtils.getNowTime());
    wrReport2.setGmtModified(CommonUtils.getNowTime());
    data.add(wrReport);
    data.add(wrReport2);

    int i = iDataDao.createBatch(data);

    Assert.assertEquals(2, i);
  }

  /** 得到某一个小类下的数据dao测试 */
  @Test
  public void getOneClassData() {
    WrReport wrReport = new WrReport();
    wrReport.setMedicine(1L);
    wrReport.setBatchNo(222L);
    wrReport.setFirstClass(2L);
    wrReport.setSecondClass(1L);
    List<WrReportData> reports = iDataDao.getOneClassData(wrReport);
  }

  /** 得到某药品所有大类小类数据dao测试 */
  @Test
  public void getAllClassData() {
    WrReport wrReport = new WrReport();
    wrReport.setMedicine(1L);
    wrReport.setBatchNo(222L);
    List<WrReportAllData> reports = iDataDao.getAllClassData(wrReport);
    Assert.assertNotNull(reports);
  }

  /** 根据批次实验信息得到主表id dao测试 */
  @Test
  public void getReportId() {
    WrReport wrReport = new WrReport();
    wrReport.setMedicine(1L);
    wrReport.setFirstClass(0L);
    wrReport.setSecondClass(0L);
    wrReport.setBatchNo(222L);

    Long id = iDataDao.getWrReportId(wrReport);
  }

  /** 根据用户id查询复核任务 */
  @Test
  public void getReviewList() {
    List<WrReport> list = iDataDao.getReviewList(0);
    Assert.assertNotNull(list);
  }

  /** 根据用户id查询修改复核任务 */
  @Test
  public void getModifyReviewList() {
    List<WrReport> list = iDataDao.getModifyReviewList(0);
    Assert.assertNotNull(list);
  }
}
