package com.wslint.wisdomreport.config;

import jodd.crypt.BCrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义密码验证
 *
 * @author yuxr
 * @since 2018/9/3 18:42
 */
public class WslPwdIdentify extends HashedCredentialsMatcher {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(WslPwdIdentify.class);

  @Override
  public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    LOGGER.debug("------------------ 密码验证! ------------------");
    if (token instanceof UsernamePasswordToken) {
      UsernamePasswordToken authcationToken = (UsernamePasswordToken) token;
      String pwd = new String(authcationToken.getPassword());
      String hashedPassword = info.getCredentials().toString();
      if (hashedPassword.equals(pwd)) {
        return true;
      }
      return BCrypt.checkpw(pwd, hashedPassword);
    }
    return false;
  }
}
