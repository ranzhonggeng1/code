package com.wslint.wisdomreport.domain.vo.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 批次工作流对象
 *
 * @author yuxr
 * @since 2018/11/23 10:22
 */
@ApiModel(value = "批次工作流对象")
public class BatchWorkflowVO {

  @ApiModelProperty(value = "批次数据id", required = true, example = "")
  private Long batchId;

  @ApiModelProperty(value = "操作码", required = true, example = "")
  private Integer operation;

  @ApiModelProperty(value = "下一操作人id", required = true, example = "0")
  private Long nextOperator;

  @ApiModelProperty(value = "提交原因", required = false, example = "测试批次提交审核")
  private String reason;

  public Long getBatchId() {
    return batchId;
  }

  public void setBatchId(Long batchId) {
    this.batchId = batchId;
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
}
