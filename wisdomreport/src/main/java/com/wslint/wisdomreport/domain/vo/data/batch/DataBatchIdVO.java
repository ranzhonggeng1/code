package com.wslint.wisdomreport.domain.vo.data.batch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 批次id对象
 *
 * @author yuxr
 * @since 2018/11/27 18:23
 */
@ApiModel(value = "批次对象")
public class DataBatchIdVO {
  @ApiModelProperty(value = "批次id", required = true, example = "")
  private Long batchId;

  public Long getBatchId() {
    return batchId;
  }

  public void setBatchId(Long batchId) {
    this.batchId = batchId;
  }
}
