package com.wslint.wisdomreport.domain.dto.role;

import com.wslint.wisdomreport.domain.dto.permission.Permission;
import java.util.List;

public class Role {

  private Long id;
  private String roleName;
  private List<Permission> permissions;
  private Boolean forcedDelete;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public List<Permission> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<Permission> permissions) {
    this.permissions = permissions;
  }

  public Boolean isForcedDelete() {
    return forcedDelete;
  }

  public void setForcedDelete(Boolean forcedDelete) {
    this.forcedDelete = forcedDelete;
  }
}
