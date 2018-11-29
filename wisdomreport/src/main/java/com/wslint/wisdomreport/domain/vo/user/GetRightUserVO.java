package com.wslint.wisdomreport.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 获取权限用户
 *
 * @author yuxr
 * @since 2018/11/29 16:43
 */
@ApiModel(value = "获取权限用户对象")
public class GetRightUserVO {

  @ApiModelProperty(value = "权限码", required = true, example = "1")
  private String permissionCode;

  @ApiModelProperty(value = "过滤用户列表", required = true)
  private List<Long> users;

  public String getPermissionCode() {
    return permissionCode;
  }

  public void setPermissionCode(String permissionCode) {
    this.permissionCode = permissionCode;
  }

  public List<Long> getUsers() {
    return users;
  }

  public void setUsers(List<Long> users) {
    this.users = users;
  }
}
