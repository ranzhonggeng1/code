package com.wslint.wisdomreport.dao;

import com.google.gson.Gson;
import com.wslint.wisdomreport.domain.dto.workflow.WrWfTask;
import java.sql.Timestamp;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class WorkFlowDaoTest {

  @Autowired private WorkFlowDao workFlowDao;

  @Test
  public void insertWrWfCurrentTask() {
    WrWfTask wrWfTask = new WrWfTask();
    wrWfTask.setTaskId(1L);
    wrWfTask.setMedicineId(0L);
    wrWfTask.setBatchNo(0L);
    wrWfTask.setGmtCreate(new Timestamp(new java.util.Date().getTime()));
    wrWfTask.setGmtModified(new Timestamp(new java.util.Date().getTime()));
    wrWfTask.setStatus(0);
    wrWfTask.setHold1("1231");
    wrWfTask.setReviewerId(0L);
    wrWfTask.setReviewComment("12312");
    wrWfTask.setOperatorId(0L);
    wrWfTask.setOperatorName("系统管理员");
    workFlowDao.insertWrWfCurrentTask(wrWfTask);
  }

  @Test
  public void insertWrWfCompleteTask() {
    WrWfTask wrWfTask = new WrWfTask();
    wrWfTask.setTaskId(1L);
    wrWfTask.setMedicineId(0L);
    wrWfTask.setBatchNo(0L);
    wrWfTask.setGmtCreate(new Timestamp(new java.util.Date().getTime()));
    wrWfTask.setGmtModified(new Timestamp(new java.util.Date().getTime()));
    wrWfTask.setStatus(0);
    wrWfTask.setHold1("1231");
    wrWfTask.setReviewerId(0L);
    wrWfTask.setReviewComment("12312");
    wrWfTask.setOperatorId(0L);
    wrWfTask.setOperatorName("系统管理员");
    workFlowDao.insertWrWfCompleteTask(wrWfTask);
  }

  @Test
  public void getWrWfCurrentTaskByTaskId() {
    WrWfTask wrWfTask = workFlowDao.getWrWfCurrentTaskByTaskId(0L);
  }

  @Test
  public void deleteWrWfCurrentTaskByTaskId() {
    workFlowDao.deleteWrWfCurrentTaskByTaskId(0L);
  }

  @Test
  public void getWrWfCurrentTasksByMedicineIdAndStatus() {
    WrWfTask wrWfTask = new WrWfTask();
    wrWfTask.setMedicineId(489504362482241536L);
    wrWfTask.setStatus(1);
    List list = workFlowDao.getWrWfCurrentTasksByMedicineIdAndStatus(wrWfTask);
    System.out.print(new Gson().toJson(list));
  }

  @Test
  public void getWrWfCompleteTasksByMedicineId() {
    WrWfTask wrWfTask = new WrWfTask();
    wrWfTask.setMedicineId(489504362482241536L);
    List list = workFlowDao.getWrWfCompleteTasksByMedicineId(wrWfTask);
    System.out.print(new Gson().toJson(list));
  }

  @Test
  public void getWrWfAllTasksByMedicineIdAndBatchNo() {
    WrWfTask wrWfTask = new WrWfTask();
    wrWfTask.setMedicineId(489504362482241536L);
    wrWfTask.setBatchNo(489504362482241537L);
    List list = workFlowDao.getWrWfAllTasksByMedicineIdAndBatchNo(wrWfTask);
    System.out.print(new Gson().toJson(list));
  }

  @Test
  public void getWrWfCurrentTasksByReviewerId() {
    WrWfTask wrWfTask = new WrWfTask();
    wrWfTask.setReviewerId(0L);
    List list = workFlowDao.getWrWfCurrentTasksByReviewerId(wrWfTask);
    System.out.print(new Gson().toJson(list));
  }
}
