package com.wslint.wisdomreport.domain.dto.data;

import java.sql.Timestamp;

/**
 * 走工作流带修改数据的对象
 *
 * @author yuxr
 * @since 2018/11/21 14:35
 */
public class DataWorkflowWithDataDTO extends DataWorkflowDTO {

  private Long orderId;

  private String remark;
  private Timestamp remarkTime;
  private Long remarker;
  private String remarkerName;
  private String data;
  private String imgUrl;

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

  public Timestamp getRemarkTime() {
    return remarkTime;
  }

  public void setRemarkTime(Timestamp remarkTime) {
    this.remarkTime = remarkTime;
  }

  public Long getRemarker() {
    return remarker;
  }

  public void setRemarker(Long remarker) {
    this.remarker = remarker;
  }

  public String getRemarkerName() {
    return remarkerName;
  }

  public void setRemarkerName(String remarkerName) {
    this.remarkerName = remarkerName;
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
