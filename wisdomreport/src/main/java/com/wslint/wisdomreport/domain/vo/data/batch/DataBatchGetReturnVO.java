package com.wslint.wisdomreport.domain.vo.data.batch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 批次列表查看返回数据
 *
 * @author yuxr
 * @since 2018/11/12 11:10
 */
@ApiModel(value = "批次列表查看返回数据")
public class DataBatchGetReturnVO {

  @ApiModelProperty(value = "批次列表数据id")
  private Long id;

  @ApiModelProperty(value = "批次号")
  private String batchNo;

  @ApiModelProperty(value = "批次数据状态")
  private Integer status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBatchNo() {
    return batchNo;
  }

  public void setBatchNo(String batchNo) {
    this.batchNo = batchNo;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
