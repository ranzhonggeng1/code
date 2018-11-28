package com.wslint.wisdomreport.domain.vo.data.record;

import java.sql.Timestamp;

/**
 * 记录数据原型
 *
 * @author yuxr
 * @since 2018/11/22 10:10
 */
public class DataRecordReturnVO {
  private Long id;

  private Long orderId;
  private String data;
  private String imgUrl;
  private Integer status;
  private Timestamp gmtCreate;
  private Timestamp gmtModified;
  private String createUserName;
  private String modifyUserName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Timestamp getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(Timestamp gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  public Timestamp getGmtModified() {
    return gmtModified;
  }

  public void setGmtModified(Timestamp gmtModified) {
    this.gmtModified = gmtModified;
  }

  public String getCreateUserName() {
    return createUserName;
  }

  public void setCreateUserName(String createUserName) {
    this.createUserName = createUserName;
  }

  public String getModifyUserName() {
    return modifyUserName;
  }

  public void setModifyUserName(String modifyUserName) {
    this.modifyUserName = modifyUserName;
  }


}