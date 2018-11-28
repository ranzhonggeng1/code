package com.wslint.wisdomreport.domain.dto.role;

import java.util.List;

public class RolePermission {

  private Long roleId;
  private Long permissionId;

  private List<Long> permissionIdList;

  public List<Long> getPermissionIdList() {
    return permissionIdList;
  }

  public void setPermissionIdList(List<Long> permissionIdList) {
    this.permissionIdList = permissionIdList;
  }

  public Long getPermissionId() {
    return permissionId;
  }

  public void setPermissionId(Long permissionId) {
    this.permissionId = permissionId;
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }
}
