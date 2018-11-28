package com.wslint.wisdomreport.config;

import com.wslint.wisdomreport.domain.dto.permission.Permission;
import com.wslint.wisdomreport.domain.dto.role.Role;
import com.wslint.wisdomreport.domain.dto.user.User;
import com.wslint.wisdomreport.service.user.IUserService;
import com.wslint.wisdomreport.utils.CommonUtils;
import javax.annotation.PostConstruct;
import jodd.crypt.BCrypt;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 微视联自定义权限管理系统
 *
 * @author yuxr
 * @since 2018/8/21 15:30
 */
public class WslShiroRealm extends AuthorizingRealm {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(WslShiroRealm.class);

  /** 用户信息查询 */
  @Autowired private IUserService iUserService;

  /** 加入这个才能使自定义密码验证生效 */
  @PostConstruct
  public void initCredentialsMatcher() {
    setCredentialsMatcher(new WslPwdIdentify());
  }

  /**
   * 角色权限和对应权限添加
   *
   * @param principalCollection 对应用户权限信息
   * @return 返回对应用户的权限信息的添加
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    LOGGER.info("------------------ 用户权限信息 start ------------------");
    // 获取登录用户名
    String userCode =
        CommonUtils.getUserCodeByShiroCode(principalCollection.getPrimaryPrincipal().toString());
    // 查询用户名称
    User user = iUserService.getUserByCode(userCode);
    // 添加角色和权限
    SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    for (Role role : user.getRoles()) {
      // 添加角色
      simpleAuthorizationInfo.addRole(role.getRoleName());
      for (Permission permission : role.getPermissions()) {
        // 添加权限
        simpleAuthorizationInfo.addStringPermission(permission.getPermissionCode());
      }
    }
    LOGGER.debug(
        "------------------ 当前 -用户:{} -角色:{} -权限:{} ------------------",
        userCode,
        simpleAuthorizationInfo.getRoles(),
        simpleAuthorizationInfo.getStringPermissions());
    LOGGER.info("------------------ 用户权限信息 end ------------------");
    return simpleAuthorizationInfo;
  }

  /**
   * 用户认证
   *
   * @param authenticationToken 用户信息
   * @return 组装好的用户信息
   * @throws AuthenticationException 用户验证结果
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    LOGGER.info("------------------ 用户认证 start ------------------");
    // 加这一步的目的是在Post请求的时候会先进认证，然后再到请求
    if (authenticationToken.getPrincipal() == null) {
      return null;
    }
    // 获取用户信息
    String userCode =
        CommonUtils.getUserCodeByShiroCode(authenticationToken.getPrincipal().toString());
    LOGGER.info("------------------ {} 用户认证 ------------------", userCode);
    User user = iUserService.getUserByCode(userCode);
    if (user == null) {
      // 这里返回后会报出对应异常
      return null;
    } else {
      // 这里验证authenticationToken和simpleAuthenticationInfo的信息
      String shiorCode = CommonUtils.getShiroCodeByUser(user);
      SimpleAuthenticationInfo simpleAuthenticationInfo =
          new SimpleAuthenticationInfo(
              shiorCode, user.getPassword(), ByteSource.Util.bytes(BCrypt.gensalt()), getName());
      LOGGER.info("------------------ 用户认证 end ------------------");

      return simpleAuthenticationInfo;
    }
  }
}
