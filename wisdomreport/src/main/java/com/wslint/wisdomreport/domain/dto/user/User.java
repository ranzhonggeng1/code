package com.wslint.wisdomreport.domain.dto.user;

import com.wslint.wisdomreport.domain.dto.role.Role;
import java.util.List;

public class User {

  private Long id;
  private String userCode;
  private String userName;
  private String password;
  private String oldPassword;
  private String sign;
  private Integer complete;
  private Boolean forcedDelete;
  private List<Role> roles;
  private List<String> permissionCodeList;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserCode() {
    return userCode;
  }

  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public Integer getComplete() {
    return complete;
  }

  public void setComplete(Integer complete) {
    this.complete = complete;
  }

  public List<String> getPermissionCodeList() {
    return permissionCodeList;
  }

  public void setPermissionCodeList(List<String> permissionCodeList) {
    this.permissionCodeList = permissionCodeList;
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public Boolean isForcedDelete() {
    return forcedDelete;
  }

  public void setForcedDelete(Boolean forcedDelete) {
    this.forcedDelete = forcedDelete;
  }

}
