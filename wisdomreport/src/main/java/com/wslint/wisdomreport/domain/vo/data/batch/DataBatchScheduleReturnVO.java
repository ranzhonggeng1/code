package com.wslint.wisdomreport.domain.vo.data.batch;

import java.sql.Timestamp;

/**
 * 审批进度查询结果对象
 *
 * @author yuxr
 * @since 2018/11/12 11:26
 */
public class DataBatchScheduleReturnVO {

  private String operationName;
  private String operatorName;
  private String nextOperatorName;
  private String statusName;
  private String nextStatusName;
  private String reason;
  private Timestamp gmtCreate;
  private Timestamp gmtModified;

  public String getOperatorName() {
    return operatorName;
  }

  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  public String getOperationName() {
    return operationName;
  }

  public void setOperationName(String operationName) {
    this.operationName = operationName;
  }

  public String getStatusName() {
    return statusName;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }

  public String getNextStatusName() {
    return nextStatusName;
  }

  public void setNextStatusName(String nextStatusName) {
    this.nextStatusName = nextStatusName;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public Timestamp getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(Timestamp gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  public Timestamp getGmtModified() {
    return gmtModified;
  }

  public void setGmtModified(Timestamp gmtModified) {
    this.gmtModified = gmtModified;
  }

  public String getNextOperatorName() {
    return nextOperatorName;
  }

  public void setNextOperatorName(String nextOperatorName) {
    this.nextOperatorName = nextOperatorName;
  }
}
