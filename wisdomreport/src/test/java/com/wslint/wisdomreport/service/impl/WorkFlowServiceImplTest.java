package com.wslint.wisdomreport.service.impl;

import com.google.gson.Gson;
import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.dao.WorkFlowDao;
import com.wslint.wisdomreport.domain.dto.workflow.WrWfTask;
import com.wslint.wisdomreport.service.IWorkFlowService;
import com.wslint.wisdomreport.util.TestUtils;
import com.wslint.wisdomreport.utils.CommonUtils;
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
public class WorkFlowServiceImplTest {

  @Autowired private IWorkFlowService iWorkFlowService;
  @Autowired private WorkFlowDao workFlowDao;

  @Autowired private SecurityManager securityManager;
  @Autowired private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  private WrWfTask inputWrWfTask;
  private List<WrWfTask> wrWfCurrentTasks;
  private List<WrWfTask> wrWfCompleteTasks;
  private WrWfTask wrWfTask;
  private Long testTaskId;
  private Long medicineId;
  private Long batchNo;
  private List<WrWfTask> wrWfTaskList;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    // 登录必须要添加安全管理
    SecurityUtils.setSecurityManager(securityManager);
    TestUtils.login(
        Constant.STR_ADMIN, Constant.SIR_DEFAULT_R_AP, webApplicationContext, securityManager);
    // 预制数据
    testTaskId = CommonUtils.getNextId();
    medicineId = CommonUtils.getNextId();
    batchNo = CommonUtils.getNextId();
    inputWrWfTask = new WrWfTask();
    inputWrWfTask.setTaskId(testTaskId);
    inputWrWfTask.setMedicineId(medicineId);
    inputWrWfTask.setBatchNo(batchNo);
    inputWrWfTask.setGmtCreate(CommonUtils.getNowTime());
    inputWrWfTask.setGmtModified(CommonUtils.getNowTime());
    inputWrWfTask.setStatus(Constant.WORK_FLOW_STATUS_EXPERIMENT);
    inputWrWfTask.setHold1("预制测试数据！");
    inputWrWfTask.setReviewerId(null);
    inputWrWfTask.setReviewComment(null);
    inputWrWfTask.setOperatorId(0L);
    inputWrWfTask.setOperatorName("系统管理员");
    workFlowDao.insertWrWfCompleteTask(inputWrWfTask);

    inputWrWfTask = new WrWfTask();
    inputWrWfTask.setTaskId(testTaskId);
    inputWrWfTask.setMedicineId(medicineId);
    inputWrWfTask.setBatchNo(batchNo);
    inputWrWfTask.setGmtCreate(CommonUtils.getNowTime());
    inputWrWfTask.setGmtModified(CommonUtils.getNowTime());
    inputWrWfTask.setStatus(Constant.WORK_FLOW_STATUS_REVIEW);
    inputWrWfTask.setHold1("预制测试数据！");
    inputWrWfTask.setReviewerId(0L);
    inputWrWfTask.setReviewComment(null);
    inputWrWfTask.setOperatorId(0L);
    inputWrWfTask.setOperatorName("系统管理员");
    workFlowDao.insertWrWfCompleteTask(inputWrWfTask);

    inputWrWfTask = new WrWfTask();
    inputWrWfTask.setTaskId(testTaskId);
    inputWrWfTask.setMedicineId(medicineId);
    inputWrWfTask.setBatchNo(batchNo);
    inputWrWfTask.setGmtCreate(CommonUtils.getNowTime());
    inputWrWfTask.setGmtModified(CommonUtils.getNowTime());
    inputWrWfTask.setStatus(Constant.WORK_FLOW_STATUS_END);
    inputWrWfTask.setHold1("预制测试数据！");
    inputWrWfTask.setReviewerId(null);
    inputWrWfTask.setReviewComment(null);
    inputWrWfTask.setOperatorId(0L);
    inputWrWfTask.setOperatorName("系统管理员");
    workFlowDao.insertWrWfCompleteTask(inputWrWfTask);

    inputWrWfTask = new WrWfTask();
    inputWrWfTask.setTaskId(testTaskId);
    inputWrWfTask.setMedicineId(medicineId);
    inputWrWfTask.setBatchNo(batchNo);
    inputWrWfTask.setGmtCreate(CommonUtils.getNowTime());
    inputWrWfTask.setGmtModified(CommonUtils.getNowTime());
    inputWrWfTask.setStatus(Constant.WORK_FLOW_STATUS_REVIEW);
    inputWrWfTask.setHold1("预制测试数据！");
    inputWrWfTask.setReviewerId(1L);
    inputWrWfTask.setReviewComment("继续审批");
    inputWrWfTask.setOperatorId(0L);
    inputWrWfTask.setOperatorName("系统管理员");
    workFlowDao.insertWrWfCurrentTask(inputWrWfTask);

    inputWrWfTask = new WrWfTask();
    inputWrWfTask.setTaskId(CommonUtils.getNextId());
    inputWrWfTask.setMedicineId(medicineId);
    inputWrWfTask.setBatchNo(CommonUtils.getNextId());
    inputWrWfTask.setGmtCreate(CommonUtils.getNowTime());
    inputWrWfTask.setGmtModified(CommonUtils.getNowTime());
    inputWrWfTask.setStatus(Constant.WORK_FLOW_STATUS_EXPERIMENT);
    inputWrWfTask.setHold1("预制测试数据！");
    inputWrWfTask.setReviewerId(null);
    inputWrWfTask.setReviewComment(null);
    inputWrWfTask.setOperatorId(0L);
    inputWrWfTask.setOperatorName("系统管理员");
    workFlowDao.insertWrWfCurrentTask(inputWrWfTask);

    inputWrWfTask = new WrWfTask();
    inputWrWfTask.setTaskId(CommonUtils.getNextId());
    inputWrWfTask.setMedicineId(medicineId);
    inputWrWfTask.setBatchNo(CommonUtils.getNextId());
    inputWrWfTask.setGmtCreate(CommonUtils.getNowTime());
    inputWrWfTask.setGmtModified(CommonUtils.getNowTime());
    inputWrWfTask.setStatus(Constant.WORK_FLOW_STATUS_REVIEW);
    inputWrWfTask.setHold1("预制测试数据！");
    inputWrWfTask.setReviewerId(1L);
    inputWrWfTask.setReviewComment(null);
    inputWrWfTask.setOperatorId(0L);
    inputWrWfTask.setOperatorName("系统管理员");
    workFlowDao.insertWrWfCurrentTask(inputWrWfTask);
  }

  @After
  public void tearDown() throws Exception {
    // 退出
    TestUtils.logout(mockMvc);
  }

  /** 审批流程测试 */
  @Test
  public void testWorkFlow() {
    // 创建批次
    inputWrWfTask = new WrWfTask();
    inputWrWfTask.setMedicineId(CommonUtils.getNextId());
    inputWrWfTask.setBatchNo(CommonUtils.getNextId());
    inputWrWfTask.setHold1("测试数据！");
    testTaskId = iWorkFlowService.start(inputWrWfTask);

    wrWfCurrentTasks = workFlowDao.getWrWfCurrentTasksByTaskId(testTaskId);
    wrWfCompleteTasks = workFlowDao.getWrWfCompleteTasksByTaskId(testTaskId);
    Assert.assertEquals(wrWfCurrentTasks.size(), Constant.NUM_1);
    Assert.assertEquals(wrWfCompleteTasks.size(), Constant.NUM_0);
    wrWfTask = wrWfCurrentTasks.get(0);
    Assert.assertEquals(wrWfTask.getStatus(), Constant.WORK_FLOW_STATUS_EXPERIMENT);
    Assert.assertEquals(wrWfTask.getOperatorId(), Constant.ID_0);
    Assert.assertEquals(wrWfTask.getOperatorName(), Constant.STR_ADMIN_NAME);

    // 发起审批
    inputWrWfTask = initWrWfTaskData();
    inputWrWfTask.setReviewerId(Constant.ID_0);
    iWorkFlowService.commit(inputWrWfTask);

    wrWfCurrentTasks = workFlowDao.getWrWfCurrentTasksByTaskId(testTaskId);
    wrWfCompleteTasks = workFlowDao.getWrWfCompleteTasksByTaskId(testTaskId);
    Assert.assertEquals(wrWfCurrentTasks.size(), Constant.NUM_1);
    Assert.assertEquals(wrWfCompleteTasks.size(), Constant.NUM_1);
    wrWfTask = wrWfCurrentTasks.get(0);
    Assert.assertEquals(wrWfTask.getStatus(), Constant.WORK_FLOW_STATUS_REVIEW);
    Assert.assertEquals(wrWfTask.getOperatorId(), Constant.ID_0);
    Assert.assertEquals(wrWfTask.getOperatorName(), Constant.STR_ADMIN_NAME);
    Assert.assertEquals(wrWfTask.getReviewerId(), Constant.ID_0);

    // 继续审批
    inputWrWfTask = initWrWfTaskData();
    inputWrWfTask.setReviewerId(Constant.ID_0);
    inputWrWfTask.setReviewComment("继续审批！");
    iWorkFlowService.passReview(inputWrWfTask);

    wrWfCurrentTasks = workFlowDao.getWrWfCurrentTasksByTaskId(testTaskId);
    wrWfCompleteTasks = workFlowDao.getWrWfCompleteTasksByTaskId(testTaskId);
    Assert.assertEquals(wrWfCurrentTasks.size(), Constant.NUM_1);
    Assert.assertEquals(wrWfCompleteTasks.size(), Constant.NUM_2);
    wrWfTask = wrWfCurrentTasks.get(0);
    Assert.assertEquals(wrWfTask.getStatus(), Constant.WORK_FLOW_STATUS_REVIEW);
    Assert.assertEquals(wrWfTask.getOperatorId(), Constant.ID_0);
    Assert.assertEquals(wrWfTask.getOperatorName(), Constant.STR_ADMIN_NAME);
    Assert.assertEquals(wrWfTask.getReviewerId(), Constant.ID_0);
    Assert.assertEquals(wrWfTask.getReviewComment(), "继续审批！");

    // 审批不通过
    inputWrWfTask = initWrWfTaskData();
    inputWrWfTask.setReviewComment("审批不通过！");
    iWorkFlowService.fail(inputWrWfTask);

    wrWfCurrentTasks = workFlowDao.getWrWfCurrentTasksByTaskId(testTaskId);
    wrWfCompleteTasks = workFlowDao.getWrWfCompleteTasksByTaskId(testTaskId);
    Assert.assertEquals(wrWfCurrentTasks.size(), Constant.NUM_1);
    Assert.assertEquals(wrWfCompleteTasks.size(), Constant.NUM_3);
    wrWfTask = wrWfCurrentTasks.get(0);
    Assert.assertEquals(wrWfTask.getStatus(), Constant.WORK_FLOW_STATUS_REVIEW_FAILED);
    Assert.assertEquals(wrWfTask.getOperatorId(), Constant.ID_0);
    Assert.assertEquals(wrWfTask.getOperatorName(), Constant.STR_ADMIN_NAME);
    Assert.assertEquals(wrWfTask.getReviewComment(), "审批不通过！");

    // 重做实验
    inputWrWfTask = initWrWfTaskData();
    iWorkFlowService.redo(inputWrWfTask);

    wrWfCurrentTasks = workFlowDao.getWrWfCurrentTasksByTaskId(testTaskId);
    wrWfCompleteTasks = workFlowDao.getWrWfCompleteTasksByTaskId(testTaskId);
    Assert.assertEquals(wrWfCurrentTasks.size(), Constant.NUM_1);
    Assert.assertEquals(wrWfCompleteTasks.size(), Constant.NUM_4);
    wrWfTask = wrWfCurrentTasks.get(0);
    Assert.assertEquals(wrWfTask.getStatus(), Constant.WORK_FLOW_STATUS_EXPERIMENT);
    Assert.assertEquals(wrWfTask.getOperatorId(), Constant.ID_0);
    Assert.assertEquals(wrWfTask.getOperatorName(), Constant.STR_ADMIN_NAME);

    // 发起审批
    inputWrWfTask = initWrWfTaskData();
    inputWrWfTask.setReviewerId(Constant.ID_0);
    iWorkFlowService.commit(inputWrWfTask);

    wrWfCurrentTasks = workFlowDao.getWrWfCurrentTasksByTaskId(testTaskId);
    wrWfCompleteTasks = workFlowDao.getWrWfCompleteTasksByTaskId(testTaskId);
    Assert.assertEquals(wrWfCurrentTasks.size(), Constant.NUM_1);
    Assert.assertEquals(wrWfCompleteTasks.size(), Constant.NUM_5);
    wrWfTask = wrWfCurrentTasks.get(0);
    Assert.assertEquals(wrWfTask.getStatus(), Constant.WORK_FLOW_STATUS_REVIEW);
    Assert.assertEquals(wrWfTask.getOperatorId(), Constant.ID_0);
    Assert.assertEquals(wrWfTask.getOperatorName(), Constant.STR_ADMIN_NAME);
    Assert.assertEquals(wrWfTask.getReviewerId(), Constant.ID_0);

    // 审批通过
    inputWrWfTask = initWrWfTaskData();
    inputWrWfTask.setReviewComment("审批通过！");
    iWorkFlowService.passEnd(inputWrWfTask);

    wrWfCurrentTasks = workFlowDao.getWrWfCurrentTasksByTaskId(testTaskId);
    wrWfCompleteTasks = workFlowDao.getWrWfCompleteTasksByTaskId(testTaskId);
    Assert.assertEquals(wrWfCurrentTasks.size(), Constant.NUM_0);
    Assert.assertEquals(wrWfCompleteTasks.size(), Constant.NUM_7);
    wrWfTask = wrWfCompleteTasks.get(6);
    Assert.assertEquals(wrWfTask.getStatus(), Constant.WORK_FLOW_STATUS_END);
    Assert.assertEquals(wrWfTask.getOperatorId(), Constant.ID_0);
    Assert.assertEquals(wrWfTask.getOperatorName(), Constant.STR_ADMIN_NAME);
    Assert.assertEquals(wrWfTask.getReviewComment(), "审批通过！");
  }

  /**
   * 初始化工作流数据
   *
   * @return 构建好的工作流的数据
   */
  private WrWfTask initWrWfTaskData() {
    WrWfTask wrWfTask = new WrWfTask();
    wrWfTask.setTaskId(testTaskId);
    wrWfTask.setHold1("测试数据！");
    return wrWfTask;
  }

  @Test
  public void getWrWfCurrentTasksByMedicineIdAndStatus() {
    // 查询所有状态数据
    wrWfTaskList =
        iWorkFlowService.getWrWfTasksByMedicineIdAndStatus(
            medicineId, Constant.WORK_FLOW_STATUS_ALL);
    Assert.assertEquals(wrWfTaskList.size(), Constant.NUM_3);

    // 查询审批状态数据
    wrWfTaskList =
        iWorkFlowService.getWrWfTasksByMedicineIdAndStatus(
            medicineId, Constant.WORK_FLOW_STATUS_REVIEW);
    Assert.assertEquals(wrWfTaskList.size(), Constant.NUM_2);

    // 查询已完成状态数据
    wrWfTaskList =
        iWorkFlowService.getWrWfTasksByMedicineIdAndStatus(
            medicineId, Constant.WORK_FLOW_STATUS_END);
    Assert.assertEquals(wrWfTaskList.size(), Constant.NUM_1);
  }

  @Test
  public void getWrWfCompleteTasksByMedicineId() {
    wrWfTaskList = iWorkFlowService.getWrWfCompleteTasksByMedicineId(medicineId);
    Assert.assertEquals(wrWfTaskList.size(), Constant.NUM_3);
  }

  @Test
  public void getWrWfAllTasksByMedicineIdAndBatchNo() {
    wrWfTaskList = iWorkFlowService.getWrWfAllTasksByMedicineIdAndBatchNo(medicineId, batchNo);
    Assert.assertEquals(wrWfTaskList.size(), Constant.NUM_4);
  }

  @Test
  public void getWrWfCurrentTasksByReviewerId() {
    wrWfTaskList = iWorkFlowService.getWrWfCurrentTasksByReviewerId(1L);
    System.out.print(new Gson().toJson(wrWfTaskList));
    Assert.assertEquals(wrWfTaskList.size(), Constant.NUM_2);
  }
}
