package com.wslint.wisdomreport.domain.vo.data.clazz;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 类别对象
 *
 * @author yuxr
 * @since 2018/11/27 18:23
 */
@ApiModel(value = "类别对象")
public class DataClassIdVO {

  @ApiModelProperty(value = "类别id", required = true, example = "")
  private Long classId;

  public Long getClassId() {
    return classId;
  }

  public void setClassId(Long classId) {
    this.classId = classId;
  }
}
