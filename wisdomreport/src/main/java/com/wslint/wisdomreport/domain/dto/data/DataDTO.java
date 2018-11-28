package com.wslint.wisdomreport.domain.dto.data;

import java.sql.Timestamp;

/**
 * 数据基类
 *
 * @author yuxr
 * @since 2018/11/21 14:28
 */
public class DataDTO {
  private Long id;

  private String hold1;
  private String hold2;
  private String hold3;

  private Timestamp gmtCreate;
  private Timestamp gmtModified;
  private Long createUser;
  private String createUserName;
  private Long modifyUser;
  private String modifyUserName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getHold1() {
    return hold1;
  }

  public void setHold1(String hold1) {
    this.hold1 = hold1;
  }

  public String getHold2() {
    return hold2;
  }

  public void setHold2(String hold2) {
    this.hold2 = hold2;
  }

  public String getHold3() {
    return hold3;
  }

  public void setHold3(String hold3) {
    this.hold3 = hold3;
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

  public Long getCreateUser() {
    return createUser;
  }

  public void setCreateUser(Long createUser) {
    this.createUser = createUser;
  }

  public String getCreateUserName() {
    return createUserName;
  }

  public void setCreateUserName(String createUserName) {
    this.createUserName = createUserName;
  }

  public Long getModifyUser() {
    return modifyUser;
  }

  public void setModifyUser(Long modifyUser) {
    this.modifyUser = modifyUser;
  }

  public String getModifyUserName() {
    return modifyUserName;
  }

  public void setModifyUserName(String modifyUserName) {
    this.modifyUserName = modifyUserName;
  }
}
