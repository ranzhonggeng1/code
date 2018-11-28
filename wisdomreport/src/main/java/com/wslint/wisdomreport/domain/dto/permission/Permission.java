package com.wslint.wisdomreport.domain.dto.permission;

public class Permission {

  private Long id;
  private String permissionCode;
  private String permissionName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPermissionCode() {
    return permissionCode;
  }

  public void setPermissionCode(String permissionCode) {
    this.permissionCode = permissionCode;
  }

  public String getPermissionName() {
    return permissionName;
  }

  public void setPermissionName(String permissionName) {
    this.permissionName = permissionName;
  }
}
