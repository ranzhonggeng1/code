package com.wslint.wisdomreport.service.user.impl;

import com.wslint.wisdomreport.domain.dto.user.User;
import com.wslint.wisdomreport.service.user.ILoginService;
import com.wslint.wisdomreport.service.user.IUserService;
import jodd.crypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户管理服务
 *
 * @author yuxr
 * @since 2018/8/21 15:55
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements ILoginService {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

  @Autowired private IUserService iUserService;

  /**
   * 校验用户信息
   *
   * @param userCode 用户编码
   * @param password 密码
   * @return 校验结果
   */
  @Override
  public User checkUserInfo(String userCode, String password) {
    User user = iUserService.getUserByCode(userCode);
    if (user == null) {
      LOGGER.info("------------------ 用户不存在！------------------");
      return null;
    }
    if (!BCrypt.checkpw(password, user.getPassword())) {
      LOGGER.info("------------------ 用户验证不通过！------------------");
      return null;
    }
    LOGGER.info("------------------ 用户验证通过！------------------");
    return user;
  }

  /**
   * 校验用户信息
   *
   * @param userId 用户id
   * @param password 密码
   * @return 校验结果
   */
  @Override
  public User checkUserInfo(Long userId, String password) {
    User user = iUserService.getUserById(userId);
    if (user == null) {
      LOGGER.info("------------------ 用户不存在！------------------");
      return null;
    }
    if (!BCrypt.checkpw(password, user.getPassword())) {
      LOGGER.info("------------------ 用户验证不通过！------------------");
      return null;
    }
    LOGGER.info("------------------ 用户验证通过！------------------");
    return user;
  }
}
