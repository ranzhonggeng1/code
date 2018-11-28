package com.wslint.wisdomreport.service.user.impl;

import com.google.gson.Gson;
import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.constant.UserConstant;
import com.wslint.wisdomreport.dao.user.UserDao;
import com.wslint.wisdomreport.domain.dto.permission.Permission;
import com.wslint.wisdomreport.domain.dto.role.Role;
import com.wslint.wisdomreport.domain.dto.user.User;
import com.wslint.wisdomreport.service.config.system.ISysConfigService;
import com.wslint.wisdomreport.service.user.ILoginService;
import com.wslint.wisdomreport.service.user.IRoleService;
import com.wslint.wisdomreport.service.user.IUserService;
import com.wslint.wisdomreport.utils.CommonUtils;
import com.wslint.wisdomreport.utils.ReturnUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jodd.crypt.BCrypt;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户管理
 *
 * @author yuxr
 * @since 2018/9/12 11:43
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements IUserService {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired private UserDao userDao;
  @Autowired private IRoleService iRoleService;
  @Autowired private ILoginService iLoginService;
  @Autowired private ISysConfigService iSysConfigService;

  /**
   * 新增用户
   *
   * @param user 用户信息
   * @return 处理结果
   */
  @Override
  public Map<String, Object> addUser(User user) {
    if (userDao.isUserExisted(user)) {
      LOGGER.info("------------------ 该用户已被注册！------------------");

      return ReturnUtils.failureMap("该用户已被注册！");
    }
    // 补充默认信息
    user.setId(CommonUtils.getNextId());
    user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
    user.setComplete(UserConstant.STATUS_COMPLETED);

    LOGGER.debug("------------------ 添加用户情况:{} ------------------", new Gson().toJson(user));

    // 添加用户
    userDao.addUser(user);
    LOGGER.info("------------------ 添加用户成功！------------------");
    return ReturnUtils.successMap("添加用户成功！");
  }

  /**
   * 删除用户
   *
   * @param user 用户信息
   * @return 处理结果
   */
  @Override
  public Map<String, Object> deleteUserById(User user) {
    if (user.getId().equals(Constant.ID_0)) {
      LOGGER.info("------------------ 无法删除系统管理员！------------------");
      return ReturnUtils.failureMap("无法删除系统管理员！");
    }
    User deleteUser = userDao.getUserById(user.getId());
    if (deleteUser == null) {
      LOGGER.info("------------------ 找不到用户！------------------");
      return ReturnUtils.failureMap("找不到用户！");
    }
    return deleteUserByIdForced(user);
  }

  /**
   * 根据用户id和密码修改用户信息
   *
   * @param user 输入用户信息
   * @return 处理结果
   */
  @Override
  public Map<String, Object> updateUser(User user) {
    User updateUser = iLoginService.checkUserInfo(user.getId(), user.getOldPassword());
    if (null == updateUser) {
      return ReturnUtils.failureMap("密码验证不通过！");
    }
    if (user.getComplete() != null && user.getComplete().equals(UserConstant.STATUS_COMPLETED)) {
      updateUser.setComplete(user.getComplete());
      updateUser.setSign(user.getSign());
    }
    userDao.updateUser(updateUser);
    LOGGER.info("------------------ 修改用户信息成功！------------------");
    return ReturnUtils.successMap("修改用户信息成功！");
  }

  /**
   * 修改用户密码
   *
   * @param user 输入用户信息
   * @return 处理结果
   */
  @Override
  public Map<String, Object> passwordUpdate(User user) {
    User updateUser = iLoginService.checkUserInfo(user.getId(), user.getOldPassword());
    if (null == updateUser) {
      return ReturnUtils.failureMap("密码验证不通过！");
    }
    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
      updateUser.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
      updateUser.setComplete(UserConstant.STATUS_COMPLETED);
    }
    if (userDao.passwordUpdate(updateUser)) {
      LOGGER.info("------------------ 修改用户密码成功！------------------");
      Subject subject = SecurityUtils.getSubject();
      LOGGER.info("------------------ {} 用户退出 ------------------", subject.getPrincipal());
      subject.logout();
      return ReturnUtils.successMap("修改用户密码成功！");
    }
    LOGGER.info("------------------ 修改用户密码失败！------------------");
    return ReturnUtils.successMap("修改用户密码失败！");
  }

  /**
   * 重置用户密码
   *
   * @param userId 被重置用户id
   * @return 处理结果
   */
  @Override
  public Map<String, Object> passwordReset(Long userId) {
    // 判断用户是否是系统管理员
    User currentUser = CommonUtils.getCurrentUser();
    if (!currentUser.getUserCode().equals(Constant.STR_ADMIN)) {
      return ReturnUtils.failureMap("当前用户不是系统管理员，无法重置用户密码！");
    }
    // 获取系统默认密码
    User user = new User();
    user.setId(userId);
    // 随机生成四位数字密码
    String password = CommonUtils.getRandomNumber(UserConstant.PASSWORD_DIGIT);
    user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
    user.setComplete(UserConstant.STATUS_RESET);
    Map<String, Object> map = new HashMap<>();
    map.put("password", password);
    // 修改密码
    if (userDao.passwordUpdate(user)) {
      // 将明文密码传递给前端
      LOGGER.info("------------------ 重置用户密码成功！------------------");
      return ReturnUtils.successMap(map, "重置用户密码成功！");
    }
    LOGGER.info("------------------ 重置用户密码失败！------------------");
    return ReturnUtils.successMap("重置用户密码失败！");
  }

  /**
   * 查询所有用户
   *
   * @return 查询数据
   */
  @Override
  public Map<String, Object> getAllUsers() {
    List<User> users = userDao.getAllUsers();
    LOGGER.info("------------------ 查询所有用户成功！------------------");
    LOGGER.debug("------------------ 查询所有用户:{} ------------------", new Gson().toJson(users));
    return ReturnUtils.successMap(users, "查询所有用户成功！");
  }

  /**
   * 根据用户id查询用户信息
   *
   * @param id id
   * @return 用户信息
   */
  @Override
  public User getUserById(Long id) {
    return userDao.getUserById(id);
  }

  /**
   * 根据用户名查询用户信息
   *
   * @param userCode 用户名
   * @return 返回用户对象
   */
  @Override
  public User getUserByCode(String userCode) {
    User user = userDao.gerUserByCode(userCode);
    if (user != null) {
      List<Role> roles = userDao.getRolesByUserId(user.getId());
      user.setRoles(roles);
      List<String> permissionCodeList = new ArrayList<>();
      user.setPermissionCodeList(permissionCodeList);
      if (roles != null) {
        for (Role role : roles) {
          List<Permission> permissions = iRoleService.getPermissionsByRoleId(role.getId());
          role.setPermissions(permissions);
          for (Permission permission : permissions) {
            permissionCodeList.add(permission.getPermissionCode());
          }
        }
      }
    }
    LOGGER.debug("------------------ 查询用户情况:{} ------------------", new Gson().toJson(user));
    return user;
  }

  /**
   * 删除用户，并根据是否强制删除，处理关联数据
   *
   * @param user 角色信息
   * @return 处理结果
   */
  private Map<String, Object> deleteUserByIdForced(User user) {

    // 判断角色是否有关联用户并处理
    List<Role> roles = userDao.getRolesByUserId(user.getId());
    if (roles != null && !roles.isEmpty()) {
      if (user.isForcedDelete() != null && user.isForcedDelete()) {
        userDao.deleteUserRolesByUserId(user.getId());
      } else {
        LOGGER.info("------------------ 该用户仍在使用，是否删除！------------------");
        return ReturnUtils.wrongMap("该用户仍在使用，是否删除？");
      }
    }

    userDao.deleteUserById(user.getId());
    LOGGER.info("------------------ 删除用户成功！------------------");
    return ReturnUtils.successMap("删除用户成功！");
  }
}
