package com.wslint.wisdomreport.service.user.impl;

import com.google.gson.Gson;
import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.dao.user.RoleDao;
import com.wslint.wisdomreport.domain.dto.permission.Permission;
import com.wslint.wisdomreport.domain.dto.role.Role;
import com.wslint.wisdomreport.domain.dto.role.RolePermission;
import com.wslint.wisdomreport.domain.dto.user.User;
import com.wslint.wisdomreport.domain.dto.user.UserRole;
import com.wslint.wisdomreport.service.user.IRoleService;
import com.wslint.wisdomreport.utils.CommonUtils;
import com.wslint.wisdomreport.utils.ReturnUtils;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色信息管理
 *
 * @author yuxr
 * @since 2018/9/10 10:14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements IRoleService {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

  @Autowired private RoleDao roleDao;

  /**
   * 新增角色
   *
   * @param role 角色
   * @return 处理结果
   */
  @Override
  public Map<String, Object> addRole(Role role) {
    if (roleDao.isRoleExisted(role)) {
      LOGGER.info("------------------ 该角色已被注册！------------------");
      return ReturnUtils.failureMap("该角色已被注册！");
    }
    // 补充默认信息
    role.setId(CommonUtils.getNextId());

    LOGGER.debug("------------------ 添加角色情况:{} ------------------", new Gson().toJson(role));

    // 添加用户
    roleDao.addRole(role);
    LOGGER.info("------------------ 添加角色成功！------------------");
    return ReturnUtils.successMap("添加角色成功！");
  }

  /**
   * 添加角色权限信息
   *
   * @param rolePermission 角色权限信息
   * @return 处理结果
   */
  @Override
  public Map<String, Object> addRolePermissions(RolePermission rolePermission) {
    roleDao.addRolePermissions(rolePermission);
    LOGGER.info("------------------ 添加角色权限成功！------------------");
    return ReturnUtils.successMap("添加角色权限成功！");
  }

  /**
   * 添加用户角色信息
   *
   * @param userRole 用户角色信息
   * @return 处理结果
   */
  @Override
  public Map<String, Object> addUserRoles(UserRole userRole) {
    roleDao.addUserRoles(userRole);
    LOGGER.info("------------------ 添加用户角色成功！------------------");
    return ReturnUtils.successMap("添加用户角色成功！");
  }

  /**
   * 删除角色
   *
   * <p>判断角色是否有挂接用户和权限。如果有用户使用，返回101消息码确定；如果还有挂接权限，返回102消息码确定。
   *
   * @param role 角色信息
   * @return 处理结果
   */
  @Override
  public Map<String, Object> deleteRoleById(Role role) {
    if (role.getId().equals(Constant.ID_0)) {
      LOGGER.info("------------------ 无法删除系统管理员角色！------------------");
      return ReturnUtils.failureMap("无法删除系统管理员角色！");
    }
    Role deleteRole = roleDao.getRoleById(role.getId());
    if (deleteRole == null) {
      LOGGER.info("------------------ 找不到角色！------------------");
      return ReturnUtils.failureMap("找不到角色！");
    }
    return deleteRoleByIdForced(role);
  }

  /**
   * 删除角色权限信息
   *
   * @param rolePermission 角色权限信息
   * @return 处理结果
   */
  @Override
  public Map<String, Object> deleteRolePermissions(RolePermission rolePermission) {
    if (rolePermission.getRoleId().equals(Constant.ID_0)) {
      LOGGER.info("------------------ 无法删除系统管理员角色的权限！------------------");
      return ReturnUtils.failureMap("无法删除系统管理员角色的权限！");
    }
    roleDao.deleteRolePermissions(rolePermission);
    LOGGER.info("------------------ 删除角色权限成功！------------------");
    return ReturnUtils.successMap("删除角色权限成功！");
  }

  /**
   * 删除用户角色信息
   *
   * @param userRole 用户角色信息
   * @return 处理结果
   */
  @Override
  public Map<String, Object> deleteUserRoles(UserRole userRole) {
    if (userRole.getRoleId().equals(Constant.ID_0)) {
      LOGGER.info("------------------ 无法删除系统管理员角色的用户！------------------");
      return ReturnUtils.failureMap("无法删除系统管理员角色的用户！");
    }
    roleDao.deleteUserRoles(userRole);
    LOGGER.info("------------------ 删除用户角色成功！------------------");
    return ReturnUtils.successMap("删除用户角色成功！");
  }

  /**
   * 获取所有角色信息
   *
   * @return 所有角色信息
   */
  @Override
  public Map<String, Object> getAllRoles() {
    List<Role> roles = roleDao.getAllRoles();
    LOGGER.info("------------------ 查询所有角色成功！------------------");
    LOGGER.debug("------------------ 查询所有角色:{} ------------------", new Gson().toJson(roles));
    return ReturnUtils.successMap(roles, "查询所有角色成功！");
  }

  /**
   * 根据角色名称查询角色信息
   *
   * @param roleName 角色名称
   * @return 角色信息
   */
  @Override
  public Role getRoleByRoleName(String roleName) {
    return roleDao.getRoleByRoleName(roleName);
  }

  /**
   * 根据角色id获取权限信息
   *
   * @param id 角色id
   * @return 权限信息
   */
  @Override
  public List<Permission> getPermissionsByRoleId(Long id) {
    return roleDao.getPermissionsByRoleId(id);
  }

  /**
   * 根据角色id获取用户信息
   *
   * @param id 角色id
   * @return 用户信息
   */
  @Override
  public List<User> getUsersByRoleId(Long id) {
    return roleDao.getUsersByRoleId(id);
  }

  /**
   * 删除角色，并根据是否强制删除，处理关联数据
   *
   * @param role 角色信息
   * @return 处理结果
   */
  private Map<String, Object> deleteRoleByIdForced(Role role) {

    // 判断角色是否有关联用户并处理
    List<User> users = roleDao.getUsersByRoleId(role.getId());
    if (users != null && !users.isEmpty()) {
      if (role.isForcedDelete() != null && role.isForcedDelete()) {
        roleDao.deleteUserRolesByRoleId(role.getId());
      } else {
        LOGGER.info("------------------ 该角色仍在使用，是否删除！------------------");
        return ReturnUtils.wrongMap("该角色仍在使用，是否删除？");
      }
    }

    // 判断角色是否有关联权限并处理
    List<Permission> permissions = roleDao.getPermissionsByRoleId(role.getId());
    if (permissions != null && !permissions.isEmpty()) {
      if (role.isForcedDelete() != null && role.isForcedDelete()) {
        roleDao.deleteRolePermissionsByRoleId(role.getId());
      } else {
        LOGGER.info("------------------ 该角色拥有权限，是否删除！------------------");
        return ReturnUtils.wrongMap("该角色拥有权限，是否删除？");
      }
    }

    roleDao.deleteRoleById(role.getId());
    LOGGER.info("------------------ 删除角色成功！------------------");
    return ReturnUtils.successMap("删除角色成功！");
  }
}
