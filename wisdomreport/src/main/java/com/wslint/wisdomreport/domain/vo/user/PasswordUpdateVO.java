package com.wslint.wisdomreport.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户修改对象
 *
 * @author yuxr
 * @since 2018/11/6 11:00
 */
@ApiModel
public class PasswordUpdateVO {

  @ApiModelProperty(value = "用户id", required = true, example = "0")
  private Long id;

  @ApiModelProperty(value = "用户新密码", required = true, example = "wsladmin")
  private String password;

  @ApiModelProperty(value = "用户旧密码", required = true, example = "wsladmin")
  private String oldPassword;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }
}
