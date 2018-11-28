package com.wslint.wisdomreport.domain.vo.data.clazz;

import com.wslint.wisdomreport.domain.vo.data.batch.DataBatchVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 类别对象
 *
 * @author yuxr
 * @since 2018/11/16 10:51
 */
@ApiModel(value = "类别对象")
public class DataClassVO extends DataBatchVO {
  @ApiModelProperty(value = "大类id", required = true, example = "3")
  private Long firstClassId;

  @ApiModelProperty(value = "小类id", required = true, example = "12")
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
