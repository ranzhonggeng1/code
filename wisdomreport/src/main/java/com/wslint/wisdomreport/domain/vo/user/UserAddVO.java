package com.wslint.wisdomreport.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 新增用户对象
 *
 * @author yxur
 * @since 2018/11/6 14:11
 */
@ApiModel
public class UserAddVO {
  @ApiModelProperty(value = "用户编码", required = true, example = "yuxr")
  private String userCode;

  @ApiModelProperty(value = "用户名称", required = true, example = "俞晓荣")
  private String userName;

  @ApiModelProperty(value = "用户密码", required = true, example = "111111")
  private String password;

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
}
