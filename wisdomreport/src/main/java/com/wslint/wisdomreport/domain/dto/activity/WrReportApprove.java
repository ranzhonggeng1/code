package com.wslint.wisdomreport.domain.dto.activity;

public class WrReportApprove {

  private long id;
  private long medicine;
  private long batchNo;
  private long approver;
  private long approverFuture;
  private String approveComment;
  private boolean approveCode;
  private java.sql.Timestamp gmtCreate;
  private java.sql.Timestamp gmtModified;
  private long status;
  private String hold1;

  public boolean isEnd() {
    return isEnd;
  }

  public void setEnd(boolean end) {
    isEnd = end;
  }

  private boolean isEnd;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getMedicine() {
    return medicine;
  }

  public void setMedicine(long medicine) {
    this.medicine = medicine;
  }

  public long getBatchNo() {
    return batchNo;
  }

  public void setBatchNo(long batchNo) {
    this.batchNo = batchNo;
  }

  public long getApprover() {
    return approver;
  }

  public void setApprover(long approver) {
    this.approver = approver;
  }

  public long getApproverFuture() {
    return approverFuture;
  }

  public void setApproverFuture(long approverFuture) {
    this.approverFuture = approverFuture;
  }

  public String getApproveComment() {
    return approveComment;
  }

  public void setApproveComment(String approveComment) {
    this.approveComment = approveComment;
  }

  public boolean isApproveCode() {
    return approveCode;
  }

  public void setApproveCode(boolean approveCode) {
    this.approveCode = approveCode;
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

  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }

  public String getHold1() {
    return hold1;
  }

  public void setHold1(String hold1) {
    this.hold1 = hold1;
  }
}
