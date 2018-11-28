package com.wslint.wisdomreport.domain.dto.config;

public class SysConfig {

  private long id;
  private String code;
  private String name;
  private String value;
  private java.sql.Timestamp gmtCreate;
  private java.sql.Timestamp gmtModified;
  private String remark;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public java.sql.Timestamp getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(java.sql.Timestamp gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  public java.sql.Timestamp getGmtModified() {
    return gmtModified;
  }

  public void setGmtModified(java.sql.Timestamp gmtModified) {
    this.gmtModified = gmtModified;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
