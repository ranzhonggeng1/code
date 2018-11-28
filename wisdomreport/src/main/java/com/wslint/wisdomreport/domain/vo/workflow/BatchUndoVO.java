package com.wslint.wisdomreport.domain.vo.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 重做批次实验对象
 *
 * @author yuxr
 * @since 2018/11/10 14:16
 */
@ApiModel(value = "重做批次实验")
public class BatchUndoVO {

  @ApiModelProperty(value = "重做实验批次id", required = true, example = "")
  private Long batchId;

  @ApiModelProperty(value = "重做实验原因", required = false, example = "测试重做批次实验")
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
