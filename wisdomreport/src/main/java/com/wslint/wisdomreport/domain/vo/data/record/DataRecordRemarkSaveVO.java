package com.wslint.wisdomreport.domain.vo.data.record;

import com.wslint.wisdomreport.domain.vo.data.clazz.DataClassVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 记录数据保存对象
 *
 * @author yuxr
 * @since 2018/11/16 10:57
 */
@ApiModel(value = "保存记录备注对象")
public class DataRecordRemarkSaveVO extends DataClassVO {

  @ApiModelProperty(value = "空格id", required = true, example = "233")
  private Long orderId;

  @ApiModelProperty(value = "备注", required = true, example = "测试备注")
  private String remark;

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
