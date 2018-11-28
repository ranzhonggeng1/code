package com.wslint.wisdomreport.service.user;

import com.wslint.wisdomreport.domain.dto.user.User;

/**
 * 用户管理服务
 *
 * @author yuxr
 * @since 2018/8/21 15:55
 */
public interface ILoginService {

  /**
   * 校验用户信息
   *
   * @param userCode 用户编码
   * @param password 密码
   * @return 校验结果
   */
  User checkUserInfo(String userCode, String password);
  /**
   * 校验用户信息
   *
   * @param userId 用户id
   * @param password 密码
   * @return 校验结果
   */
  User checkUserInfo(Long userId, String password);
}
