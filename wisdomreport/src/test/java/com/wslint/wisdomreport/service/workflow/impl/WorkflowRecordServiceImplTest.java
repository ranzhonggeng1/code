package com.wslint.wisdomreport.service.workflow.impl;

import static com.wslint.wisdomreport.common.WorkflowControl.getRecordNextStatusByOperation;
import static com.wslint.wisdomreport.common.WorkflowControl.getRecordStatusByOperation;
import static com.wslint.wisdomreport.constant.WorkflowConstant.BATCH_OPERATION_COMMIT;
import static com.wslint.wisdomreport.constant.WorkflowConstant.BATCH_OPERATION_REJECT;
import static com.wslint.wisdomreport.constant.WorkflowConstant.BATCH_STATUS_EXPERIMENT;
import static com.wslint.wisdomreport.constant.WorkflowConstant.OPERATION_COMPLETE;
import static com.wslint.wisdomreport.constant.WorkflowConstant.RECORD_OPERATION_EDIT_DRAFT;
import static com.wslint.wisdomreport.constant.WorkflowConstant.RECORD_OPERATION_EDIT_EFFECTIVE;
import static com.wslint.wisdomreport.constant.WorkflowConstant.RECORD_OPERATION_REVIEW_DIRECT;
import static com.wslint.wisdomreport.constant.WorkflowConstant.RECORD_OPERATION_REVIEW_PASS;
import static com.wslint.wisdomreport.constant.WorkflowConstant.RECORD_OPERATION_REVIEW_REJECT;
import static com.wslint.wisdomreport.constant.WorkflowConstant.RECORD_OPERATION_REVIEW_START;
import static com.wslint.wisdomreport.constant.WorkflowConstant.RECORD_OPERATION_SAVE;
import static com.wslint.wisdomreport.constant.WorkflowConstant.RECORD_STATUS_APPROVE;
import static com.wslint.wisdomreport.constant.WorkflowConstant.RECORD_STATUS_DRAFT;
import static com.wslint.wisdomreport.constant.WorkflowConstant.RECORD_STATUS_EFFECTIVE;
import static com.wslint.wisdomreport.constant.WorkflowConstant.RECORD_STATUS_REVIEW;
import static com.wslint.wisdomreport.constant.WorkflowConstant.STATUS_END;
import static com.wslint.wisdomreport.constant.WorkflowConstant.STATUS_START;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.constant.ReturnConstant;
import com.wslint.wisdomreport.dao.data.DataRecordDao;
import com.wslint.wisdomreport.dao.workflow.WorkflowRecordDao;
import com.wslint.wisdomreport.domain.dto.data.record.DataRecordDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowRecordTaskDTO;
import com.wslint.wisdomreport.service.workflow.IWorkflowRecordService;
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
public class WorkflowRecordServiceImplTest {
  @Autowired private SecurityManager securityManager;
  @Autowired private WebApplicationContext webApplicationContext;
  @Autowired private DataRecordDao dataRecordDao;
  @Autowired private WorkflowRecordDao workflowRecordDao;
  @Autowired private IWorkflowRecordService iWorkflowRecordService;

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
    List<WorkflowRecordTaskDTO> workflowRecordTaskDTOList = setUpTasks();
    int result = iWorkflowRecordService.doWorkflow(workflowRecordTaskDTOList);
    Assert.assertEquals(result, ReturnConstant.WORKFLOW_SUCCESS);
    // 校验
    List<DataRecordDTO> dataRecordDTOList = dataRecordDao.getRecordsByClassId(-1L);
    for (DataRecordDTO dataRecordDTO : dataRecordDTOList) {
      check(dataRecordDTO);
    }
  }

  @After
  public void tearDown() {}

  private void check(DataRecordDTO dataRecordDTO) {
    int operation = new Double(dataRecordDTO.getId().doubleValue() / 1000000).intValue();
    int status = getRecordStatusByOperation(operation);
    int nextStatus = getRecordNextStatusByOperation(operation);
    checkDatas(dataRecordDTO, operation, nextStatus);
    checkTasks(dataRecordDTO, operation, status, nextStatus);
  }

  private void checkTasks(DataRecordDTO dataRecordDTO, int operation, int status, int nextStatus) {
    List<WorkflowRecordTaskDTO> workflowRecordTaskDTOList = new ArrayList<>();
    if (nextStatus == STATUS_END) {
      workflowRecordTaskDTOList =
          workflowRecordDao.getWorkflowCompleteTasksByRecordId(dataRecordDTO.getId());
    } else {
      workflowRecordTaskDTOList =
          workflowRecordDao.getWorkflowCurrentTasksByRecordId(dataRecordDTO.getId());
    }
    WorkflowRecordTaskDTO workflowRecordTaskDTO = workflowRecordTaskDTOList.get(0);
    Assert.assertEquals(workflowRecordTaskDTO.getOperation(), new Integer(operation));
    Assert.assertEquals(workflowRecordTaskDTO.getOperator(), Constant.ADMIN_ID);
    Assert.assertEquals(workflowRecordTaskDTO.getReason(), "test");
    Assert.assertEquals(workflowRecordTaskDTO.getStatus(), new Integer(status));
    Assert.assertEquals(workflowRecordTaskDTO.getNextStatus(), new Integer(nextStatus));
    Assert.assertNotNull(workflowRecordTaskDTO.getGmtCreate());
    Assert.assertNotNull(workflowRecordTaskDTO.getGmtModified());
  }

  private void checkDatas(DataRecordDTO dataRecordDTO, int operation, int rightStatus) {
    if (operation == RECORD_OPERATION_REVIEW_DIRECT) {
      rightStatus = BATCH_STATUS_EXPERIMENT;
    }
    if (operation == RECORD_OPERATION_EDIT_DRAFT || operation == RECORD_OPERATION_EDIT_EFFECTIVE) {
      Assert.assertEquals(dataRecordDTO.getData(), "new_data");
    }
    Assert.assertEquals(dataRecordDTO.getStatus(), new Integer(rightStatus));
    Assert.assertNotNull(dataRecordDTO.getGmtModified());
    Assert.assertEquals(dataRecordDTO.getModifyUser(), Constant.ADMIN_ID);
    if (rightStatus == RECORD_STATUS_REVIEW || rightStatus == RECORD_STATUS_APPROVE) {
      Assert.assertEquals(dataRecordDTO.getNextOperator(), Constant.ADMIN_ID);
    } else {
      Assert.assertNull(dataRecordDTO.getNextOperator());
    }
  }

  private List<WorkflowRecordTaskDTO> setUpTasks() {
    List<WorkflowRecordTaskDTO> tasks = new ArrayList<>();
    tasks.add(setUpTask(101000001L, RECORD_OPERATION_SAVE, null, null));
    tasks.add(setUpTask(101000002L, RECORD_OPERATION_SAVE, null, null));
    tasks.add(setUpTask(102000001L, RECORD_OPERATION_REVIEW_START, Constant.ADMIN_ID, null));
    tasks.add(setUpTask(102000002L, RECORD_OPERATION_REVIEW_START, Constant.ADMIN_ID, null));
    tasks.add(setUpTask(103000001L, RECORD_OPERATION_REVIEW_DIRECT, Constant.ADMIN_ID, null));
    tasks.add(setUpTask(103000002L, RECORD_OPERATION_REVIEW_DIRECT, Constant.ADMIN_ID, null));
    tasks.add(setUpTask(104000001L, RECORD_OPERATION_REVIEW_PASS, null, null));
    tasks.add(setUpTask(104000002L, RECORD_OPERATION_REVIEW_PASS, null, null));
    tasks.add(setUpTask(106000001L, RECORD_OPERATION_REVIEW_REJECT, null, null));
    tasks.add(setUpTask(106000002L, RECORD_OPERATION_REVIEW_REJECT, null, null));
    tasks.add(setUpTask(107000001L, RECORD_OPERATION_EDIT_DRAFT, null, "new_data"));
    tasks.add(setUpTask(107000002L, RECORD_OPERATION_EDIT_DRAFT, null, "new_data"));
    tasks.add(setUpTask(108000001L, RECORD_OPERATION_EDIT_EFFECTIVE, null, "new_data"));
    tasks.add(setUpTask(108000002L, RECORD_OPERATION_EDIT_EFFECTIVE, null, "new_data"));
    tasks.add(setUpTask(109000001L, OPERATION_COMPLETE, null, null));
    tasks.add(setUpTask(109000002L, OPERATION_COMPLETE, null, null));

    tasks.add(setUpTask(5000001L, BATCH_OPERATION_COMMIT, Constant.ADMIN_ID, null));
    tasks.add(setUpTask(5000002L, BATCH_OPERATION_COMMIT, Constant.ADMIN_ID, null));
    tasks.add(setUpTask(9000001L, BATCH_OPERATION_REJECT, null, null));
    tasks.add(setUpTask(9000002L, BATCH_OPERATION_REJECT, null, null));
    return tasks;
  }

  private WorkflowRecordTaskDTO setUpTask(
      Long recordId, Integer operation, Long nextOperator, String newData) {
    WorkflowRecordTaskDTO workflowRecordTaskDTO = new WorkflowRecordTaskDTO();
    workflowRecordTaskDTO.setRecordId(recordId);
    workflowRecordTaskDTO.setOperation(operation);
    workflowRecordTaskDTO.setNextOperator(nextOperator);
    workflowRecordTaskDTO.setReason("test");
    workflowRecordTaskDTO.setNewData(newData);
    return workflowRecordTaskDTO;
  }

  private void setUpData() {
    List<DataRecordDTO> datas = new ArrayList<>();
    datas.add(getData(101000001L, "10101_old_data", STATUS_START, null));
    datas.add(getData(101000002L, "10102_old_data", STATUS_START, null));
    datas.add(getData(102000001L, "10201_old_data", RECORD_STATUS_DRAFT, null));
    datas.add(getData(102000002L, "10202_old_data", RECORD_STATUS_DRAFT, null));
    datas.add(getData(103000001L, "1030101_old_data", RECORD_STATUS_DRAFT, null));
    datas.add(getData(103000002L, "1030102_old_data", RECORD_STATUS_DRAFT, null));
    datas.add(getData(104000001L, "10401_old_data", RECORD_STATUS_REVIEW, Constant.ADMIN_ID));
    datas.add(getData(104000002L, "10402_old_data", RECORD_STATUS_REVIEW, Constant.ADMIN_ID));
    datas.add(getData(106000001L, "10601_old_data", RECORD_STATUS_REVIEW, Constant.ADMIN_ID));
    datas.add(getData(106000002L, "10602_old_data", RECORD_STATUS_REVIEW, Constant.ADMIN_ID));
    datas.add(getData(107000001L, "10701_old_data", RECORD_STATUS_DRAFT, null));
    datas.add(getData(107000002L, "10702_old_data", RECORD_STATUS_DRAFT, null));
    datas.add(getData(108000001L, "10801_old_data", RECORD_STATUS_EFFECTIVE, null));
    datas.add(getData(108000002L, "10802_old_data", RECORD_STATUS_EFFECTIVE, null));
    datas.add(getData(109000001L, "10901_old_data", RECORD_STATUS_APPROVE, Constant.ADMIN_ID));
    datas.add(getData(109000002L, "10902_old_data", RECORD_STATUS_APPROVE, Constant.ADMIN_ID));

    datas.add(getData(5000001L, "501_old_data", RECORD_STATUS_EFFECTIVE, null));
    datas.add(getData(5000002L, "502_old_data", RECORD_STATUS_EFFECTIVE, null));
    datas.add(getData(9000001L, "901_old_data", RECORD_STATUS_APPROVE, Constant.ADMIN_ID));
    datas.add(getData(9000002L, "902_old_data", RECORD_STATUS_APPROVE, Constant.ADMIN_ID));
    dataRecordDao.insertDataRecords(datas);
  }

  private DataRecordDTO getData(Long id, String data, Integer status, Long nextOperator) {
    DataRecordDTO dataRecordDTO = new DataRecordDTO();
    dataRecordDTO.setId(id);
    dataRecordDTO.setOrderId(-1L);
    dataRecordDTO.setClassId(-1L);
    dataRecordDTO.setBatchId(-1L);
    dataRecordDTO.setData(data);
    dataRecordDTO.setImgUrl("");
    dataRecordDTO.setStatus(status);
    dataRecordDTO.setNextOperator(nextOperator);
    dataRecordDTO.setGmtCreate(CommonUtils.getNowTime());
    dataRecordDTO.setGmtModified(CommonUtils.getNowTime());
    dataRecordDTO.setCreateUser(CommonUtils.getCurrentUserId());
    dataRecordDTO.setModifyUser(CommonUtils.getCurrentUserId());
    return dataRecordDTO;
  }
}
