package com.wslint.wisdomreport.domain.vo.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 小类id对象
 *
 * @author yuxr
 * @since 2018/11/9 13:57
 */
@ApiModel(value = "小类id对象")
public class SecondClassIdVO {
  @ApiModelProperty(value = "主键id", required = true, example = "12")
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
