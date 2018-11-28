package com.wslint.wisdomreport.domain.vo.data.batch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 批次进度查询对象
 *
 * @author yuxr
 * @since 2018/11/12 11:22
 */
@ApiModel(value = "批次进度查询参数")
public class DataBatchScheduleVO {

  @ApiModelProperty(value = "批次数据id", required = true, example = "")
  private Long batchId;

  public Long getBatchId() {
    return batchId;
  }

  public void setBatchId(Long batchId) {
    this.batchId = batchId;
  }
}
