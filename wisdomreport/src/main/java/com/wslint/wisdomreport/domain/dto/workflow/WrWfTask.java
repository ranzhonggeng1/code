package com.wslint.wisdomreport.domain.dto.workflow;

public class WrWfTask {

  private Long taskId;
  private Long medicineId;
  private Long batchNo;
  private Long reviewerId;
  private String reviewComment;
  private java.sql.Timestamp gmtCreate;
  private java.sql.Timestamp gmtModified;
  private Long operatorId;
  private String operatorName;
  private int status;
  private String hold1;
  private boolean pass;
  private boolean end;

  public boolean isPass() {
    return pass;
  }

  public void setPass(boolean pass) {
    this.pass = pass;
  }

  public boolean isEnd() {
    return end;
  }

  public void setEnd(boolean end) {
    this.end = end;
  }

  public Long getTaskId() {
    return taskId;
  }

  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }

  public Long getMedicineId() {
    return medicineId;
  }

  public void setMedicineId(Long medicineId) {
    this.medicineId = medicineId;
  }

  public Long getBatchNo() {
    return batchNo;
  }

  public void setBatchNo(Long batchNo) {
    this.batchNo = batchNo;
  }

  public java.sql.Timestamp getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(java.sql.Timestamp gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  public java.sql.Timestamp getGmtModified() {
    return gmtModified;
  }

  public void setGmtModified(java.sql.Timestamp gmtModified) {
    this.gmtModified = gmtModified;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getHold1() {
    return hold1;
  }

  public void setHold1(String hold1) {
    this.hold1 = hold1;
  }

  public Long getReviewerId() {
    return reviewerId;
  }

  public void setReviewerId(Long reviewerId) {
    this.reviewerId = reviewerId;
  }

  public String getReviewComment() {
    return reviewComment;
  }

  public void setReviewComment(String reviewComment) {
    this.reviewComment = reviewComment;
  }

  public Long getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(Long operatorId) {
    this.operatorId = operatorId;
  }

  public String getOperatorName() {
    return operatorName;
  }

  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }
}
