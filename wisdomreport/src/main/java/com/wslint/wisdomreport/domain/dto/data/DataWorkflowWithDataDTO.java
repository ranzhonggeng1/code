package com.wslint.wisdomreport.domain.dto.data;

/**
 * 走工作流带修改数据的对象
 *
 * @author yuxr
 * @since 2018/11/21 14:35
 */
public class DataWorkflowWithDataDTO extends DataWorkflowDTO {

  private Long orderId;

  private String data;
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
