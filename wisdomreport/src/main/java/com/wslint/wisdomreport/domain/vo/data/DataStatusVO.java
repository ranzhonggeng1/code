package com.wslint.wisdomreport.domain.vo.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 状态对象
 *
 * @author yuxr
 * @since 2018/11/16 11:08
 */
@ApiModel(value = "状态对象")
public class DataStatusVO {

  @ApiModelProperty(value = "状态", required = true, example = "1")
  private Integer status;

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
