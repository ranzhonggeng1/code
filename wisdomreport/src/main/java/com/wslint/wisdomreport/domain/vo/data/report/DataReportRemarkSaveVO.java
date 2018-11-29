package com.wslint.wisdomreport.domain.vo.data.report;

import com.wslint.wisdomreport.domain.vo.data.batch.DataBatchVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 报告备注保存对象
 *
 * @author yuxr
 * @since 2018/11/16 10:57
 */
@ApiModel(value = "保存报告备注对象")
public class DataReportRemarkSaveVO extends DataBatchVO {

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
