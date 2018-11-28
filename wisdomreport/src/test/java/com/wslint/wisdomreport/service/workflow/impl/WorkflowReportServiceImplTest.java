package com.wslint.wisdomreport.service.workflow.impl;

import static com.wslint.wisdomreport.common.WorkflowControl.getReportNextStatusByOperation;
import static com.wslint.wisdomreport.common.WorkflowControl.getReportStatusByOperation;
import static com.wslint.wisdomreport.constant.WorkflowConstant.BATCH_OPERATION_COMMIT;
import static com.wslint.wisdomreport.constant.WorkflowConstant.BATCH_OPERATION_REJECT;
import static com.wslint.wisdomreport.constant.WorkflowConstant.OPERATION_COMPLETE;
import static com.wslint.wisdomreport.constant.WorkflowConstant.REPORT_OPERATION_EDIT_DRAFT;
import static com.wslint.wisdomreport.constant.WorkflowConstant.REPORT_OPERATION_EDIT_EFFECTIVE;
import static com.wslint.wisdomreport.constant.WorkflowConstant.REPORT_OPERATION_REVIEW_PASS;
import static com.wslint.wisdomreport.constant.WorkflowConstant.REPORT_OPERATION_REVIEW_REJECT;
import static com.wslint.wisdomreport.constant.WorkflowConstant.REPORT_OPERATION_REVIEW_START;
import static com.wslint.wisdomreport.constant.WorkflowConstant.REPORT_OPERATION_SAVE;
import static com.wslint.wisdomreport.constant.WorkflowConstant.REPORT_STATUS_APPROVE;
import static com.wslint.wisdomreport.constant.WorkflowConstant.REPORT_STATUS_DRAFT;
import static com.wslint.wisdomreport.constant.WorkflowConstant.REPORT_STATUS_EFFECTIVE;
import static com.wslint.wisdomreport.constant.WorkflowConstant.REPORT_STATUS_REVIEW;
import static com.wslint.wisdomreport.constant.WorkflowConstant.STATUS_END;
import static com.wslint.wisdomreport.constant.WorkflowConstant.STATUS_START;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.constant.ReturnConstant;
import com.wslint.wisdomreport.dao.data.DataReportDao;
import com.wslint.wisdomreport.dao.workflow.WorkflowReportDao;
import com.wslint.wisdomreport.domain.dto.data.report.DataReportDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowReportTaskDTO;
import com.wslint.wisdomreport.service.workflow.IWorkflowReportService;
import com.wslint.wisdomreport.util.TestUtils;
import com.wslint.wisdomreport.utils.CommonUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.After;
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
public class WorkflowReportServiceImplTest {
  @Autowired private SecurityManager securityManager;
  @Autowired private WebApplicationContext webApplicationContext;
  @Autowired private DataReportDao dataReportDao;
  @Autowired private WorkflowReportDao workflowReportDao;
  @Autowired private IWorkflowReportService iWorkflowReportService;

  @Before
  public void setUp() {
    MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    SecurityUtils.setSecurityManager(securityManager);
    TestUtils.login(
        Constant.STR_ADMIN, Constant.SIR_DEFAULT_R_AP, webApplicationContext, securityManager);
    setUpData();
  }

  /**
   * 功能测试,所有操作批次处理数据通过.
   *
   * <p>通过检验标准:数据状态正确,流程记录准确
   */
  @Test
  public void doWorkflow() throws Exception {
    List<WorkflowReportTaskDTO> workflowReportTaskDTOList = setUpTasks();
    int result = iWorkflowReportService.doWorkflow(workflowReportTaskDTOList);
    Assert.assertEquals(result, ReturnConstant.WORKFLOW_SUCCESS);
    // 校验
    List<DataReportDTO> dataReportDTOList = dataReportDao.getReportsByBatchId(-1L);
    for (DataReportDTO dataReportDTO : dataReportDTOList) {
      check(dataReportDTO);
    }
  }

  @After
  public void tearDown() {}

  private void check(DataReportDTO dataReportDTO) {
    int operation = new Double(dataReportDTO.getId().doubleValue() / 1000000).intValue();
    int status = getReportStatusByOperation(operation);
    int nextStatus = getReportNextStatusByOperation(operation);
    checkDatas(dataReportDTO, operation, nextStatus);
    checkTasks(dataReportDTO, operation, status, nextStatus);
  }

  private void checkTasks(DataReportDTO dataReportDTO, int operation, int status, int nextStatus) {
    List<WorkflowReportTaskDTO> workflowReportTaskDTOList = new ArrayList<>();
    if (nextStatus == STATUS_END) {
      workflowReportTaskDTOList =
          workflowReportDao.getWorkflowCompleteTasksByReportId(dataReportDTO.getId());
    } else {
      workflowReportTaskDTOList =
          workflowReportDao.getWorkflowCurrentTasksByReportId(dataReportDTO.getId());
    }
    WorkflowReportTaskDTO workflowReportTaskDTO = workflowReportTaskDTOList.get(0);
    Assert.assertEquals(workflowReportTaskDTO.getOperation(), new Integer(operation));
    Assert.assertEquals(workflowReportTaskDTO.getOperator(), Constant.ADMIN_ID);
    Assert.assertEquals(workflowReportTaskDTO.getReason(), "test");
    Assert.assertEquals(workflowReportTaskDTO.getStatus(), new Integer(status));
    Assert.assertEquals(workflowReportTaskDTO.getNextStatus(), new Integer(nextStatus));
    Assert.assertNotNull(workflowReportTaskDTO.getGmtCreate());
    Assert.assertNotNull(workflowReportTaskDTO.getGmtModified());
  }

  private void checkDatas(DataReportDTO dataReportDTO, int operation, int rightStatus) {
    if (operation == REPORT_OPERATION_EDIT_DRAFT || operation == REPORT_OPERATION_EDIT_EFFECTIVE) {
      Assert.assertEquals(dataReportDTO.getData(), "new_data");
    }
    if (rightStatus == REPORT_STATUS_REVIEW || rightStatus == REPORT_STATUS_APPROVE) {
      Assert.assertEquals(dataReportDTO.getNextOperator(), Constant.ADMIN_ID);
    } else {
      Assert.assertNull(dataReportDTO.getNextOperator());
    }
    Assert.assertEquals(dataReportDTO.getStatus(), new Integer(rightStatus));
    Assert.assertNotNull(dataReportDTO.getGmtModified());
    Assert.assertEquals(dataReportDTO.getModifyUser(), Constant.ADMIN_ID);
  }

  private List<WorkflowReportTaskDTO> setUpTasks() {
    List<WorkflowReportTaskDTO> tasks = new ArrayList<>();
    tasks.add(setUpTask(201000001L, REPORT_OPERATION_SAVE, null, null));
    tasks.add(setUpTask(201000002L, REPORT_OPERATION_SAVE, null, null));
    tasks.add(setUpTask(202000001L, REPORT_OPERATION_REVIEW_START, Constant.ADMIN_ID, null));
    tasks.add(setUpTask(202000002L, REPORT_OPERATION_REVIEW_START, Constant.ADMIN_ID, null));
    tasks.add(setUpTask(204000001L, REPORT_OPERATION_REVIEW_PASS, null, null));
    tasks.add(setUpTask(204000002L, REPORT_OPERATION_REVIEW_PASS, null, null));
    tasks.add(setUpTask(206000001L, REPORT_OPERATION_REVIEW_REJECT, null, null));
    tasks.add(setUpTask(206000002L, REPORT_OPERATION_REVIEW_REJECT, null, null));
    tasks.add(setUpTask(207000001L, REPORT_OPERATION_EDIT_DRAFT, null, "new_data"));
    tasks.add(setUpTask(207000002L, REPORT_OPERATION_EDIT_DRAFT, null, "new_data"));
    tasks.add(setUpTask(208000001L, REPORT_OPERATION_EDIT_EFFECTIVE, null, "new_data"));
    tasks.add(setUpTask(208000002L, REPORT_OPERATION_EDIT_EFFECTIVE, null, "new_data"));
    tasks.add(setUpTask(209000001L, OPERATION_COMPLETE, null, null));
    tasks.add(setUpTask(209000002L, OPERATION_COMPLETE, null, null));

    tasks.add(setUpTask(5000001L, BATCH_OPERATION_COMMIT, Constant.ADMIN_ID, null));
    tasks.add(setUpTask(5000002L, BATCH_OPERATION_COMMIT, Constant.ADMIN_ID, null));
    tasks.add(setUpTask(9000001L, BATCH_OPERATION_REJECT, null, null));
    tasks.add(setUpTask(9000002L, BATCH_OPERATION_REJECT, null, null));
    return tasks;
  }

  private WorkflowReportTaskDTO setUpTask(
      Long reportId, Integer operation, Long nextOperator, String newData) {
    WorkflowReportTaskDTO workflowReportTaskDTO = new WorkflowReportTaskDTO();
    workflowReportTaskDTO.setReportId(reportId);
    workflowReportTaskDTO.setOperation(operation);
    workflowReportTaskDTO.setNextOperator(nextOperator);
    workflowReportTaskDTO.setReason("test");
    workflowReportTaskDTO.setNewData(newData);
    return workflowReportTaskDTO;
  }

  private void setUpData() {
    List<DataReportDTO> datas = new ArrayList<>();
    datas.add(getData(201000001L, "20101_old_data", STATUS_START, null));
    datas.add(getData(201000002L, "20102_old_data", STATUS_START, null));
    datas.add(getData(202000001L, "20201_old_data", REPORT_STATUS_DRAFT, null));
    datas.add(getData(202000002L, "20202_old_data", REPORT_STATUS_DRAFT, null));
    datas.add(getData(204000001L, "20401_old_data", REPORT_STATUS_REVIEW, Constant.ADMIN_ID));
    datas.add(getData(204000002L, "20402_old_data", REPORT_STATUS_REVIEW, Constant.ADMIN_ID));
    datas.add(getData(206000001L, "20601_old_data", REPORT_STATUS_REVIEW, Constant.ADMIN_ID));
    datas.add(getData(206000002L, "20602_old_data", REPORT_STATUS_REVIEW, Constant.ADMIN_ID));
    datas.add(getData(207000001L, "20701_old_data", REPORT_STATUS_DRAFT, null));
    datas.add(getData(207000002L, "20702_old_data", REPORT_STATUS_DRAFT, null));
    datas.add(getData(208000001L, "20801_old_data", REPORT_STATUS_EFFECTIVE, null));
    datas.add(getData(208000002L, "20802_old_data", REPORT_STATUS_EFFECTIVE, null));
    datas.add(getData(209000001L, "20901_old_data", REPORT_STATUS_APPROVE, Constant.ADMIN_ID));
    datas.add(getData(209000002L, "20902_old_data", REPORT_STATUS_APPROVE, Constant.ADMIN_ID));

    datas.add(getData(5000001L, "501_old_data", REPORT_STATUS_EFFECTIVE, null));
    datas.add(getData(5000002L, "502_old_data", REPORT_STATUS_EFFECTIVE, null));
    datas.add(getData(9000001L, "901_old_data", REPORT_STATUS_APPROVE, Constant.ADMIN_ID));
    datas.add(getData(9000002L, "902_old_data", REPORT_STATUS_APPROVE, Constant.ADMIN_ID));
    dataReportDao.insertDataReports(datas);
  }

  private DataReportDTO getData(Long id, String data, Integer status, Long nextOperator) {
    DataReportDTO dataReportDTO = new DataReportDTO();
    dataReportDTO.setId(id);
    dataReportDTO.setOrderId(-1L);
    dataReportDTO.setBatchId(-1L);
    dataReportDTO.setData(data);
    dataReportDTO.setImgUrl("");
    dataReportDTO.setStatus(status);
    dataReportDTO.setNextOperator(nextOperator);
    dataReportDTO.setGmtCreate(CommonUtils.getNowTime());
    dataReportDTO.setGmtModified(CommonUtils.getNowTime());
    dataReportDTO.setCreateUser(CommonUtils.getCurrentUserId());
    dataReportDTO.setModifyUser(CommonUtils.getCurrentUserId());
    return dataReportDTO;
  }
}
