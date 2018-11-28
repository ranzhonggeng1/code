package com.wslint.wisdomreport.domain.vo.data.report;

import com.wslint.wisdomreport.domain.vo.data.batch.DataBatchIdVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 报告数据保存对象
 *
 * @author yuxr
 * @since 2018/11/16 10:57
 */
@ApiModel(value = "保存报告数据对象")
public class DataReportSaveVO extends DataBatchIdVO {

  @ApiModelProperty(value = "空格id", required = true, example = "233")
  private Long orderId;

  @ApiModelProperty(value = "数据", required = true, example = "测试数据")
  private String data;

  @ApiModelProperty(value = "图片链接", required = true, example = "ip:port/img/...")
  private String imgUrl;

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }
}
