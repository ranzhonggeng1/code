package com.wslint.wisdomreport.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 密码重置对象
 *
 * @author yuxr
 * @since 2018/11/6 12:02
 */
@ApiModel
public class PasswordRestVO {

  @ApiModelProperty(value = "用户id", required = true, example = "0")
  private Long userId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
