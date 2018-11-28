package com.wslint.wisdomreport.service.user.impl;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.domain.dto.permission.Permission;
import com.wslint.wisdomreport.domain.dto.role.Role;
import com.wslint.wisdomreport.domain.dto.role.RolePermission;
import com.wslint.wisdomreport.domain.dto.user.User;
import com.wslint.wisdomreport.domain.dto.user.UserRole;
import com.wslint.wisdomreport.service.user.IRoleService;
import com.wslint.wisdomreport.service.user.IUserService;
import com.wslint.wisdomreport.utils.CommonUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RoleServiceImplTest {

  @Autowired private IRoleService iRoleService;
  @Autowired private IUserService iUserService;

  private Map<String, Object> rtnMap;
  private Role inputRole;
  private RolePermission inputRolePermission;
  private Role testRolePermission;
  private UserRole inputUserRole;
  private Role testUserRole;
  private User testUser;
  private Role deleteRole;
  private Role deleteForcedRole;
  private Random random = new Random();
  private List<Long> ids;

  @Before
  public void setUp() {
    // 新增测试用户
    User inputUser = new User();
    inputUser.setUserCode("testUser");
    inputUser.setUserName("测试用户");
    inputUser.setPassword("1");
    iUserService.addUser(inputUser);
    testUser = iUserService.getUserByCode("testUser");

    // 测试角色权限数据
    inputRole = new Role();
    inputRole.setRoleName("测试角色权限角色");
    iRoleService.addRole(inputRole);
    testRolePermission = iRoleService.getRoleByRoleName("测试角色权限角色");
    inputRolePermission = new RolePermission();
    inputRolePermission.setRoleId(testRolePermission.getId());
    ids = new ArrayList<>();
    ids.add(1L);
    ids.add(2L);
    ids.add(3L);
    inputRolePermission.setPermissionIdList(ids);
    iRoleService.addRolePermissions(inputRolePermission);

    // 测试用户角色数据
    inputRole = new Role();
    inputRole.setRoleName("测试用户角色角色");
    iRoleService.addRole(inputRole);
    testUserRole = iRoleService.getRoleByRoleName("测试用户角色角色");
    inputUserRole = new UserRole();
    inputUserRole.setRoleId(testUserRole.getId());
    ids = new ArrayList<>();
    ids.add(testUser.getId());
    inputUserRole.setUserIdList(ids);
    iRoleService.addUserRoles(inputUserRole);

    // 测试删除角色数据
    inputRole = new Role();
    inputRole.setRoleName("测试删除角色");
    iRoleService.addRole(inputRole);
    deleteRole = iRoleService.getRoleByRoleName("测试删除角色");

    // 测试强制删除角色数据
    inputRole = new Role();
    inputRole.setRoleName("测试强制删除角色");
    iRoleService.addRole(inputRole);
    deleteForcedRole = iRoleService.getRoleByRoleName("测试强制删除角色");
    inputRolePermission = new RolePermission();
    inputRolePermission.setRoleId(deleteForcedRole.getId());
    ids = new ArrayList<>();
    ids.add(1L);
    ids.add(2L);
    ids.add(3L);
    inputRolePermission.setPermissionIdList(ids);
    iRoleService.addRolePermissions(inputRolePermission);
    inputUserRole = new UserRole();
    inputUserRole.setRoleId(deleteForcedRole.getId());
    ids = new ArrayList<>();
    ids.add(testUser.getId());
    inputUserRole.setUserIdList(ids);
    iRoleService.addUserRoles(inputUserRole);
  }

  @After
  public void tearDown() {
    // 删除测试删除用户
    testUser.setForcedDelete(true);
    iUserService.deleteUserById(testUser);

    // 删除测试角色权限数据
    testRolePermission.setForcedDelete(true);
    iRoleService.deleteRoleById(testRolePermission);

    // 删除测试用户角色数据
    testUserRole.setForcedDelete(true);
    iRoleService.deleteRoleById(testUserRole);

    // 删除测试删除角色数据
    deleteRole.setForcedDelete(true);
    iRoleService.deleteRoleById(deleteRole);

    // 删除测试强制删除角色数据
    deleteForcedRole.setForcedDelete(true);
    iRoleService.deleteRoleById(deleteForcedRole);
  }

  @Test
  public void addRole() {

    // 测试重复角色添加
    inputRole = new Role();
    inputRole.setRoleName("系统管理员角色");
    rtnMap = iRoleService.addRole(inputRole);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_FAILURE);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "该角色已被注册！");

    // 测试成功添加角色数据
    inputRole = new Role();
    inputRole.setRoleName("测试角色" + random.nextInt());
    rtnMap = iRoleService.addRole(inputRole);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_SUCCESS);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "添加角色成功！");
  }

  @Test
  public void addRolePermissions() {
    inputRolePermission = new RolePermission();
    RolePermission rolePermission = new RolePermission();
    rolePermission.setRoleId(testRolePermission.getId());
    List<Long> ids = new ArrayList<>();
    ids.add(4L);
    ids.add(5L);
    ids.add(6L);
    rolePermission.setPermissionIdList(ids);
    rtnMap = iRoleService.addRolePermissions(rolePermission);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_SUCCESS);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "添加角色权限成功！");
    List<Permission> permissions = iRoleService.getPermissionsByRoleId(testRolePermission.getId());
    Assert.assertEquals(permissions.size(), Constant.NUM_6);
  }

  @Test
  public void addUserRoles() {

    // 将该用户挂接在测试用户角色角色上并验证
    inputUserRole = new UserRole();
    inputUserRole.setRoleId(testUserRole.getId());
    ids = new ArrayList<>();
    ids.add(testUser.getId());
    inputUserRole.setUserIdList(ids);
    rtnMap = iRoleService.addUserRoles(inputUserRole);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_SUCCESS);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "添加用户角色成功！");
    List<User> users = iRoleService.getUsersByRoleId(testUserRole.getId());
    Assert.assertEquals(users.size(), Constant.NUM_2);
  }

  @Test
  public void deleteRole() {
    // 找不到该角色信息
    inputRole = new Role();
    inputRole.setId(CommonUtils.getNextId());
    rtnMap = iRoleService.deleteRoleById(inputRole);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_FAILURE);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "找不到角色！");

    // 待删除角色仍然挂接在用户下
    rtnMap = iRoleService.deleteRoleById(testUserRole);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_WARN);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "该角色仍在使用，是否删除？");

    // 待删除用户仍然拥有权限信息
    rtnMap = iRoleService.deleteRoleById(testRolePermission);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_WARN);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "该角色拥有权限，是否删除？");

    // 强制删除角色信息
    deleteForcedRole.setForcedDelete(true);
    rtnMap = iRoleService.deleteRoleById(deleteForcedRole);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_SUCCESS);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "删除角色成功！");

    // 成功删除角色信息
    rtnMap = iRoleService.deleteRoleById(deleteRole);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_SUCCESS);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "删除角色成功！");

    // 强制删除系统管理员角色
    inputRole = getAdminRole();
    inputRole.setForcedDelete(true);
    rtnMap = iRoleService.deleteRoleById(inputRole);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_FAILURE);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "无法删除系统管理员角色！");
  }

  /**
   * 拼装系统管理员
   *
   * @return 系统管理员角色
   */
  private Role getAdminRole() {
    Role adminRole = new Role();
    adminRole.setId(Constant.ID_0);
    adminRole.setRoleName("系统管理员角色");
    return adminRole;
  }

  @Test
  public void deleteRolePermissions() {
    // 无法删除系统管理员角色的权限
    inputRolePermission = new RolePermission();
    inputRolePermission.setRoleId(Constant.ID_0);
    rtnMap = iRoleService.deleteRolePermissions(inputRolePermission);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_FAILURE);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "无法删除系统管理员角色的权限！");

    // 删除角色权限成功
    inputRolePermission = new RolePermission();
    RolePermission rolePermission = new RolePermission();
    rolePermission.setRoleId(testRolePermission.getId());
    List<Long> ids = new ArrayList<>();
    ids.add(1L);
    ids.add(2L);
    ids.add(3L);
    rolePermission.setPermissionIdList(ids);
    rtnMap = iRoleService.deleteRolePermissions(rolePermission);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_SUCCESS);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "删除角色权限成功！");
    List<Permission> permissions = iRoleService.getPermissionsByRoleId(testRolePermission.getId());
    Assert.assertEquals(permissions.size(), Constant.NUM_0);
  }

  @Test
  public void deleteUserRoles() {
    // 无法删除系统管理员角色的用户
    inputUserRole = new UserRole();
    inputUserRole.setRoleId(Constant.ID_0);
    rtnMap = iRoleService.deleteUserRoles(inputUserRole);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_FAILURE);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "无法删除系统管理员角色的用户！");

    // 将该用户挂接在测试用户角色角色上并验证
    inputUserRole = new UserRole();
    inputUserRole.setRoleId(testUserRole.getId());
    ids = new ArrayList<>();
    ids.add(testUser.getId());
    inputUserRole.setUserIdList(ids);
    rtnMap = iRoleService.deleteUserRoles(inputUserRole);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_SUCCESS);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "删除用户角色成功！");
    List<User> users = iRoleService.getUsersByRoleId(testUserRole.getId());
    Assert.assertEquals(users.size(), Constant.NUM_0);
  }

  @Test
  public void getAllRoles() {
    // 测试成功查询所有角色数据
    rtnMap = iRoleService.getAllRoles();
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_SUCCESS);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "查询所有角色成功！");
    Assert.assertNotNull(rtnMap.get(Constant.STR_DATA));
    List roles = (List) rtnMap.get(Constant.STR_DATA);
    Assert.assertTrue(roles.size() >= Constant.NUM_4);
  }

  @Test
  public void getRoleByRoleName() {
    Role role = iRoleService.getRoleByRoleName("系统管理员角色");
    Assert.assertEquals(role.getId(), Constant.ID_0);
  }

  @Test
  public void getPermissionsByRoleId() {
    List<Permission> permissions = iRoleService.getPermissionsByRoleId(Constant.ID_0);
    Assert.assertEquals(permissions.size(), Constant.ADMIN_PERMISSION);
  }

  @Test
  public void getUsersByRoleId() {
    List<User> users = iRoleService.getUsersByRoleId(testUserRole.getId());
    Assert.assertEquals(users.size(), Constant.NUM_1);
  }
}
