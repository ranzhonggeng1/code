package com.wslint.wisdomreport.domain.vo.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 类型id对象
 *
 * @author yuxr
 * @since 2018/11/27 16:18
 */
@ApiModel(value = "类型id对象")
public class ClassIdVO {
  @ApiModelProperty(value = "大类数据id", required = true, example = "3")
  private Long firstClassId;

  @ApiModelProperty(value = "小类数据id", required = true, example = "12")
  private Long secondClassId;

  public Long getFirstClassId() {
    return firstClassId;
  }

  public void setFirstClassId(Long firstClassId) {
    this.firstClassId = firstClassId;
  }

  public Long getSecondClassId() {
    return secondClassId;
  }

  public void setSecondClassId(Long secondClassId) {
    this.secondClassId = secondClassId;
  }
}
