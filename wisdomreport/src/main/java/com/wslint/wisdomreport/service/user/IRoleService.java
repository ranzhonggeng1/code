package com.wslint.wisdomreport.service.user;

import com.wslint.wisdomreport.domain.dto.permission.Permission;
import com.wslint.wisdomreport.domain.dto.role.Role;
import com.wslint.wisdomreport.domain.dto.role.RolePermission;
import com.wslint.wisdomreport.domain.dto.user.User;
import com.wslint.wisdomreport.domain.dto.user.UserRole;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author yuxr
 * @since 2018/9/10 10:13
 */
public interface IRoleService {

  /**
   * 新增角色
   *
   * @param role 角色
   * @return 处理结果
   */
  Map<String, Object> addRole(Role role);

  /**
   * 添加角色权限信息
   *
   * @param rolePermission 角色权限信息
   * @return 处理结果
   */
  Map<String, Object> addRolePermissions(RolePermission rolePermission);

  /**
   * 添加用户角色信息
   *
   * @param userRole 用户角色信息
   * @return 处理结果
   */
  Map<String, Object> addUserRoles(UserRole userRole);

  /**
   * 删除角色
   *
   * <p>判断角色是否有挂接用户和权限。如果有用户使用，返回101消息码确定；如果还有挂接权限，返回102消息码确定。
   *
   * @param role 角色信息
   * @return 处理结果
   */
  Map<String, Object> deleteRoleById(Role role);

  /**
   * 删除角色权限信息
   *
   * @param rolePermission 角色权限信息
   * @return 处理结果
   */
  Map<String, Object> deleteRolePermissions(RolePermission rolePermission);

  /**
   * 删除用户角色信息
   *
   * @param userRole 用户角色信息
   * @return 处理结果
   */
  Map<String, Object> deleteUserRoles(UserRole userRole);

  /**
   * 获取所有角色信息
   *
   * @return 所有角色信息
   */
  Map<String, Object> getAllRoles();

  /**
   * 根据角色名称查询角色信息
   *
   * @param roleName 角色名称
   * @return 角色信息
   */
  Role getRoleByRoleName(String roleName);

  /**
   * 根据角色id获取权限信息
   *
   * @param id 角色id
   * @return 权限信息
   */
  List<Permission> getPermissionsByRoleId(Long id);

  /**
   * 根据角色id获取用户信息
   *
   * @param id 角色id
   * @return 用户信息
   */
  List<User> getUsersByRoleId(Long id);
}
