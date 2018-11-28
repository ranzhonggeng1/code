package com.wslint.wisdomreport.controller.user;

import com.wslint.wisdomreport.domain.dto.permission.Permission;
import com.wslint.wisdomreport.domain.dto.role.Role;
import com.wslint.wisdomreport.domain.dto.role.RolePermission;
import com.wslint.wisdomreport.domain.dto.user.User;
import com.wslint.wisdomreport.domain.dto.user.UserRole;
import com.wslint.wisdomreport.service.user.IRoleService;
import com.wslint.wisdomreport.utils.ReturnUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色信息处理类
 *
 * @author yuxr
 * @since 2018/9/10 10:10
 */
@Api(tags = "1 用户权限接口", description = "提供用户权限管理的相关接口")
@RestController
@RequestMapping(value = "/role")
public class RoleController {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

  @Autowired private IRoleService iRoleService;

  /**
   * 新增角色接口
   *
   * @param role 角色信息
   * @return 处理结果
   */
  @ApiOperation(value = "新增角色接口", notes = "新增角色")
  @ApiImplicitParam(name = "role", value = "角色信息", required = true, dataType = "Role")
  @RequestMapping(value = "/addRole", method = RequestMethod.POST)
  public Map<String, Object> addUser(@RequestBody Role role) {
    if (role.getRoleName() == null || role.getRoleName().isEmpty()) {
      LOGGER.info("------------------ 角色名称为空！------------------");
      return ReturnUtils.failureMap("角色名称为空！");
    }
    return iRoleService.addRole(role);
  }

  /**
   * 新增角色对权限接口
   *
   * @param rolePermission 角色权限信息
   * @return 处理结果
   */
  @ApiOperation(value = "新增角色对权限接口", notes = "新增角色对权限")
  @ApiImplicitParam(
      name = "rolePermission",
      value = "角色对权限信息",
      required = true,
      dataType = "RolePermission")
  @RequestMapping(value = "/addRolePermissions", method = RequestMethod.POST)
  public Map<String, Object> addRolePermissions(@RequestBody RolePermission rolePermission) {
    if (isInputRolePermissionDataWrong(rolePermission)) {
      return ReturnUtils.failureMap("非法角色权限信息！");
    }
    return iRoleService.addRolePermissions(rolePermission);
  }
  /**
   * 新增角色对用户接口
   *
   * @param userRole 角色权限信息
   * @return 处理结果
   */
  @ApiOperation(value = "新增角色对用户接口", notes = "新增角色对用户")
  @ApiImplicitParam(name = "userRole", value = "角色对用户信息", required = true, dataType = "UserRole")
  @RequestMapping(value = "/addUserRoles", method = RequestMethod.POST)
  public Map<String, Object> addUserRoles(@RequestBody UserRole userRole) {
    if (isInputUserRoleDataWrong(userRole)) {
      return ReturnUtils.failureMap("非法角色权限信息！");
    }
    return iRoleService.addUserRoles(userRole);
  }

  /**
   * 删除角色接口
   *
   * @param role 角色信息
   * @return 处理结果
   */
  @ApiOperation(value = "删除用户接口", notes = "删除用户")
  @ApiImplicitParam(name = "role", value = "角色信息", required = true, dataType = "Role")
  @RequestMapping(value = "/deleteRoleById", method = RequestMethod.POST)
  public Map<String, Object> deleteRole(@RequestBody Role role) {
    if (role.getId() == 0) {
      LOGGER.info("------------------ 系统管理员角色不可删除！------------------");
      return ReturnUtils.failureMap("系统管理员角色不可删除！");
    }
    return iRoleService.deleteRoleById(role);
  }

  /**
   * 删除角色对权限接口
   *
   * @param rolePermission 角色权限信息
   * @return 处理结果
   */
  @ApiOperation(value = "删除角色对权限接口", notes = "删除角色对权限")
  @ApiImplicitParam(
      name = "rolePermission",
      value = "角色对权限信息",
      required = true,
      dataType = "RolePermission")
  @RequestMapping(value = "/deleteRolePermissions", method = RequestMethod.POST)
  public Map<String, Object> deleteRolePermissions(@RequestBody RolePermission rolePermission) {
    if (isInputRolePermissionDataWrong(rolePermission)) {
      return ReturnUtils.failureMap("非法角色权限信息！");
    }
    return iRoleService.deleteRolePermissions(rolePermission);
  }
  /**
   * 删除用户对角色接口
   *
   * @param userRole 角色权限信息
   * @return 处理结果
   */
  @ApiOperation(value = "删除用户对角色接口", notes = "删除用户对角色")
  @ApiImplicitParam(name = "userRole", value = "用户对角色信息", required = true, dataType = "UserRole")
  @RequestMapping(value = "/deleteUserRoles", method = RequestMethod.POST)
  public Map<String, Object> deleteUserRoles(@RequestBody UserRole userRole) {
    if (isInputUserRoleDataWrong(userRole)) {
      return ReturnUtils.failureMap("非法角色权限信息！");
    }
    return iRoleService.deleteUserRoles(userRole);
  }

  /**
   * 查询所有角色
   *
   * @return 处理结果
   */
  @ApiOperation(value = "查询所有角色接口", notes = "查询所有角色")
  @RequestMapping(value = "/getAllRoles", method = RequestMethod.POST)
  public Map<String, Object> getAllRoles() {
    return iRoleService.getAllRoles();
  }

  /**
   * 查询角色对权限
   *
   * @param role 角色信息
   * @return 处理结果
   */
  @ApiOperation(value = "查询角色对权限接口", notes = "查询角色对权限")
  @ApiImplicitParam(name = "role", value = "角色对权限信息", required = true, dataType = "Role")
  @RequestMapping(value = "/getPermissionsByRoleId", method = RequestMethod.POST)
  public Map<String, Object> getPermissionsByRoleId(@RequestBody Role role) {
    if (role.getId() == null) {
      LOGGER.info("------------------ 找不到角色！------------------");
      return ReturnUtils.failureMap("找不到角色！");
    }
    List<Permission> permissions = iRoleService.getPermissionsByRoleId(role.getId());
    return ReturnUtils.successMap(permissions, "角色权限信息查询成功！");
  }
  /**
   * 查询角色对用户
   *
   * @param role 角色信息
   * @return 处理结果
   */
  @ApiOperation(value = "查询角色对用户接口", notes = "查询角色对用户")
  @ApiImplicitParam(name = "role", value = "角色对用户信息", required = true, dataType = "Role")
  @RequestMapping(value = "/getUsersByRoleId", method = RequestMethod.POST)
  public Map<String, Object> getUsersByRoleId(@RequestBody Role role) {
    if (role.getId() == null) {
      LOGGER.info("------------------ 找不到角色！------------------");
      return ReturnUtils.failureMap("找不到角色！");
    }
    List<User> users = iRoleService.getUsersByRoleId(role.getId());
    return ReturnUtils.successMap(users, "角色权限信息查询成功！");
  }

  /**
   * 检查传入角色权限数据是否合法
   *
   * @param rolePermission 传入数据
   * @return 是否合法
   */
  private boolean isInputRolePermissionDataWrong(RolePermission rolePermission) {
    if (rolePermission.getRoleId() == null) {
      LOGGER.info("------------------ 传入角色ID为空！------------------");
      return true;
    }
    if (rolePermission.getPermissionIdList() == null
        || rolePermission.getPermissionIdList().isEmpty()) {
      LOGGER.info("------------------ 传入权限列表为空！------------------");
      return true;
    }
    return false;
  }
  /**
   * 检查传入用户角色数据是否合法
   *
   * @param userRole 传入数据
   * @return 是否合法
   */
  private boolean isInputUserRoleDataWrong(UserRole userRole) {
    if (userRole.getRoleId() == null) {
      LOGGER.info("------------------ 传入角色ID为空！------------------");
      return true;
    }
    if (userRole.getUserIdList() == null || userRole.getUserIdList().isEmpty()) {
      LOGGER.info("------------------ 传入用户列表为空！------------------");
      return true;
    }
    return false;
  }
}
