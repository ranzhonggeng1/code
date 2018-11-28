package com.wslint.wisdomreport.domain.dto.user;

import java.util.List;

public class UserRole {

  private Long userId;
  private Long roleId;
  private List<Long> userIdList;

  public List<Long> getUserIdList() {
    return userIdList;
  }

  public void setUserIdList(List<Long> userIdList) {
    this.userIdList = userIdList;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }
}
