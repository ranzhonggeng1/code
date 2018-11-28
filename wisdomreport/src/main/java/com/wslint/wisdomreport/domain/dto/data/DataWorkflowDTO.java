package com.wslint.wisdomreport.domain.dto.data;

/**
 * 走工作流的数据对象
 *
 * @author yuxr
 * @since 2018/11/23 11:05
 */
public class DataWorkflowDTO extends DataDTO {

  private Integer status;
  private String statusName;
  private Long nextOperator;
  private String nextOperatorName;

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
}
