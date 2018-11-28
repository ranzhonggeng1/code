package com.wslint.wisdomreport.service.user.impl;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.constant.UserConstant;
import com.wslint.wisdomreport.domain.dto.role.Role;
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
public class UserServiceImplTest {

  @Autowired private IUserService iUserService;
  @Autowired private IRoleService iRoleService;
  private Map<String, Object> rtnMap;
  private User inputUser;
  private User testUser;
  private Role testRole;
  private Random random = new Random();

  @Before
  public void setUp() {
    // 新增测试删除用户 --需要挂接角色
    inputUser = new User();
    inputUser.setUserCode("testUser");
    inputUser.setUserName("测试用户");
    inputUser.setPassword(Constant.SIR_DEFAULT_R_AP);
    iUserService.addUser(inputUser);
    testUser = iUserService.getUserByCode("testUser");
    // 测试删除用户挂接角色
    Role inputRole = new Role();
    inputRole.setRoleName("测试用户角色");
    iRoleService.addRole(inputRole);
    testRole = iRoleService.getRoleByRoleName("测试用户角色");
    UserRole inputUserRole = new UserRole();
    inputUserRole.setRoleId(testRole.getId());
    List<Long> ids = new ArrayList<>();
    ids.add(testUser.getId());
    inputUserRole.setUserIdList(ids);
    iRoleService.addUserRoles(inputUserRole);
  }

  @After
  public void tearDown() {
    // 删除测试用户
    testUser.setForcedDelete(true);
    iUserService.deleteUserById(testUser);
    // 删除测试用户角色
    testRole.setForcedDelete(true);
    iRoleService.deleteRoleById(testRole);
  }

  @Test
  public void addUser() {

    // 测试重复用户添加
    inputUser = new User();
    inputUser.setUserCode(Constant.STR_ADMIN);
    inputUser.setUserName("测试admin");
    inputUser.setPassword("1");
    rtnMap = iUserService.addUser(inputUser);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_FAILURE);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "该用户已被注册！");

    // 测试成功添加用户数据
    inputUser = new User();
    inputUser.setUserCode(CommonUtils.getRandomString(Constant.NUM_16));
    inputUser.setUserName("测试用户" + random.nextInt());
    inputUser.setPassword("1");
    rtnMap = iUserService.addUser(inputUser);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_SUCCESS);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "添加用户成功！");
  }

  @Test
  public void deleteUser() {
    // 找不到该用户信息
    User inputUser = new User();
    inputUser.setId(CommonUtils.getNextId());
    rtnMap = iUserService.deleteUserById(inputUser);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_FAILURE);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "找不到用户！");

    // 待删除用户仍然有挂接角色
    rtnMap = iUserService.deleteUserById(testUser);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_WARN);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "该用户仍在使用，是否删除？");

    // 强制删除用户信息
    testUser.setForcedDelete(true);
    rtnMap = iUserService.deleteUserById(testUser);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_SUCCESS);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "删除用户成功！");

    // 强制删除系统管理员
    inputUser = getAdminUser();
    inputUser.setForcedDelete(true);
    rtnMap = iUserService.deleteUserById(inputUser);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_FAILURE);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "无法删除系统管理员！");
  }

  /**
   * 获取系统管理员对象
   *
   * @return 系统管理员对象
   */
  private User getAdminUser() {
    User adminUser = new User();
    adminUser.setId(Constant.ID_0);
    adminUser.setUserName("系统管理员");
    return adminUser;
  }

  @Test
  public void updateUser() {

    User user;
    // 根据用户ID找不到用户数据
    inputUser = new User();
    inputUser.setId(Constant.ID_NEGATIVE_1);
    rtnMap = iUserService.updateUser(inputUser);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_FAILURE);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "密码验证不通过！");

    // 验证密码不通过
    testUser.setOldPassword(Constant.SIR_DEFAULT_W_AP);
    rtnMap = iUserService.updateUser(testUser);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_FAILURE);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "密码验证不通过！");

    // 成功完善信息
    testUser.setOldPassword(Constant.SIR_DEFAULT_R_AP);
    testUser.setPassword(Constant.SIR_DEFAULT_R_AP);
    testUser.setSign(CommonUtils.getRandomString(Constant.NUM_16));
    testUser.setComplete(UserConstant.STATUS_COMPLETED);
    rtnMap = iUserService.updateUser(testUser);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_SUCCESS);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "修改用户信息成功！");
    user = iUserService.getUserById(testUser.getId());
    Assert.assertEquals(user.getComplete(), new Integer(UserConstant.STATUS_COMPLETED));
  }

  @Test
  public void getAllUsers() {

    // 测试成功查询所有用户数据
    rtnMap = iUserService.getAllUsers();
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_SUCCESS);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "查询所有用户成功！");
    Assert.assertNotNull(rtnMap.get(Constant.STR_DATA));
    List users = (List) rtnMap.get(Constant.STR_DATA);
    Assert.assertTrue(users.size() >= Constant.NUM_1);
  }

  @Test
  public void getUserById() {
    // 测试成功根据用户id查询用户信息
    User adminUser = iUserService.getUserById(Constant.ID_0);
    Assert.assertEquals(adminUser.getUserCode(), Constant.STR_ADMIN);
  }

  @Test
  public void getUserByCode() {

    // 测试系统管理员预制信息
    String userCode = Constant.STR_ADMIN;
    User user = iUserService.getUserByCode(userCode);
    Assert.assertNotNull(user);
    Assert.assertEquals(user.getId(), Constant.ID_0);
    Assert.assertEquals(user.getUserCode(), Constant.STR_ADMIN);
    Assert.assertEquals(user.getUserName(), "系统管理员");
    Assert.assertEquals(user.getComplete(), new Integer(UserConstant.STATUS_COMPLETED));
    Assert.assertEquals(user.getRoles().size(), Constant.NUM_1);
    Assert.assertEquals(user.getPermissionCodeList().size(), Constant.ADMIN_PERMISSION);

    Role role = user.getRoles().get(Constant.NUM_0);
    Assert.assertEquals(role.getId(), Constant.ID_0);
    Assert.assertEquals(role.getRoleName(), "系统管理员角色");
    Assert.assertEquals(role.getPermissions().size(), Constant.ADMIN_PERMISSION);
  }
}
