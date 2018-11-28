package com.wslint.wisdomreport.domain.vo.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 审核批次通过对象
 *
 * @author yuxr
 * @since 2018/11/10 14:16
 */
@ApiModel(value = "审核批次通过")
public class BatchPassVO {

  @ApiModelProperty(value = "审核通过批次id", required = true, example = "")
  private Long batchId;

  @ApiModelProperty(value = "审核通过原因", required = false, example = "测试审核批次通过")
  private String reason;

  public Long getBatchId() {
    return batchId;
  }

  public void setBatchId(Long batchId) {
    this.batchId = batchId;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }
}
