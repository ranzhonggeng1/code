package com.wslint.wisdomreport.domain.vo.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Id 对象
 *
 * @author yuxr
 * @since 2018/11/16 11:18
 */
@ApiModel(value = "id对象")
public class DataIdVO {
  @ApiModelProperty(value = "id对象", required = true, example = "")
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
