package com.wslint.wisdomreport.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户登录对象
 *
 * @author yuxr
 * @since 2018/11/6 11:33
 */
@ApiModel
public class LoginVO {
  @ApiModelProperty(value = "用户编码", required = true, example = "admin")
  private String userCode;

  @ApiModelProperty(value = "用户密码", required = true, example = "wsladmin")
  private String password;

  public String getUserCode() {
    return userCode;
  }

  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
