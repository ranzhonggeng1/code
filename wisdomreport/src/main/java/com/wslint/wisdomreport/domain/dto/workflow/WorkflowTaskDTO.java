package com.wslint.wisdomreport.domain.dto.workflow;

import java.sql.Timestamp;

/**
 * 工作流基类
 *
 * @author yuxr
 * @since 2018/11/21 13:58
 */
public class WorkflowTaskDTO {
  private Long id;
  private Integer operation;
  private String operationName;
  private Long operator;
  private String operatorName;
  private Long nextOperator;
  private String nextOperatorName;
  private String reason;

  private Integer status;
  private String statusName;
  private Integer nextStatus;
  private String nextStatusName;
  private String hold1;
  private String hold2;
  private String hold3;
  private Timestamp gmtCreate;
  private Timestamp gmtModified;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getOperation() {
    return operation;
  }

  public void setOperation(Integer operation) {
    this.operation = operation;
  }

  public String getOperationName() {
    return operationName;
  }

  public void setOperationName(String operationName) {
    this.operationName = operationName;
  }

  public Long getOperator() {
    return operator;
  }

  public void setOperator(Long operator) {
    this.operator = operator;
  }

  public String getOperatorName() {
    return operatorName;
  }

  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  public Long getNextOperator() {
    return nextOperator;
  }

  public void setNextOperator(Long nextOperator) {
    this.nextOperator = nextOperator;
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

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getStatusName() {
    return statusName;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }

  public Integer getNextStatus() {
    return nextStatus;
  }

  public void setNextStatus(Integer nextStatus) {
    this.nextStatus = nextStatus;
  }

  public String getNextStatusName() {
    return nextStatusName;
  }

  public void setNextStatusName(String nextStatusName) {
    this.nextStatusName = nextStatusName;
  }

  public String getHold1() {
    return hold1;
  }

  public void setHold1(String hold1) {
    this.hold1 = hold1;
  }

  public String getHold2() {
    return hold2;
  }

  public void setHold2(String hold2) {
    this.hold2 = hold2;
  }

  public String getHold3() {
    return hold3;
  }

  public void setHold3(String hold3) {
    this.hold3 = hold3;
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
