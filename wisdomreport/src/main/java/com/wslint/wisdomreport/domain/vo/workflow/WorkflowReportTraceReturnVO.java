package com.wslint.wisdomreport.domain.vo.workflow;

import java.sql.Timestamp;

/**
 * 报告工作流追溯返回数据
 *
 * @author yuxr
 * @since 2018/11/16 12:11
 */
public class WorkflowReportTraceReturnVO {
  private Long id;
  private String operationName;
  private String operatorName;
  private String nextOperatorName;
  private String reason;
  private String oldData;
  private String oldRemark;
  private Timestamp oldRemarkTime;
  private String oldRemarkerName;
  private String imgUrl;
  private String statusName;
  private String nextStatusName;
  private Timestamp gmtCreate;
  private Timestamp gmtModified;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOperationName() {
    return operationName;
  }

  public void setOperationName(String operationName) {
    this.operationName = operationName;
  }

  public String getOperatorName() {
    return operatorName;
  }

  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  public String getNextOperatorName() {
    return nextOperatorName;
  }

  public void setNextOperatorName(String nextOperatorName) {
    this.nextOperatorName = nextOperatorName;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getOldData() {
    return oldData;
  }

  public void setOldData(String oldData) {
    this.oldData = oldData;
  }

  public String getOldRemark() {
    return oldRemark;
  }

  public void setOldRemark(String oldRemark) {
    this.oldRemark = oldRemark;
  }
  public Timestamp getOldRemarkTime() {
    return oldRemarkTime;
  }

  public void setOldRemarkTime(Timestamp oldRemarkTime) {
    this.oldRemarkTime = oldRemarkTime;
  }

  public String getOldRemarkerName() {
    return oldRemarkerName;
  }

  public void setOldRemarkerName(String oldRemarkerName) {
    this.oldRemarkerName = oldRemarkerName;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
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
}
