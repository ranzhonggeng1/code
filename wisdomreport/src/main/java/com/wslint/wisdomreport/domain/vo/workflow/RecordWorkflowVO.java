package com.wslint.wisdomreport.domain.vo.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 记录数据走工作流参数对象
 *
 * @author yuxr
 * @since 2018/11/13 14:28
 */
@ApiModel(value = "记录走工作流对象")
public class RecordWorkflowVO {

  @ApiModelProperty(value = "记录数据id", required = true, example = "")
  private Long recordId;

  @ApiModelProperty(value = "操作码", required = true, example = "")
  private Integer operation;

  @ApiModelProperty(value = "下一操作人id", example = "0")
  private Long nextOperator;

  @ApiModelProperty(value = "提交原因", example = "测试")
  private String reason;

  @ApiModelProperty(value = "新数据", example = "")
  private String newData;

  public Long getRecordId() {
    return recordId;
  }

  public void setRecordId(Long recordId) {
    this.recordId = recordId;
  }

  public Integer getOperation() {
    return operation;
  }

  public void setOperation(Integer operation) {
    this.operation = operation;
  }

  public Long getNextOperator() {
    return nextOperator;
  }

  public void setNextOperator(Long nextOperator) {
    this.nextOperator = nextOperator;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getNewData() {
    return newData;
  }

  public void setNewData(String newData) {
    this.newData = newData;
  }
}
