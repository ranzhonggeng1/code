package com.wslint.wisdomreport.util;

import com.wslint.wisdomreport.dao.user.RoleDao;
import com.wslint.wisdomreport.dao.user.UserDao;
import com.wslint.wisdomreport.domain.dto.role.Role;
import com.wslint.wisdomreport.domain.dto.role.RolePermission;
import com.wslint.wisdomreport.domain.dto.user.User;
import com.wslint.wisdomreport.domain.dto.user.UserRole;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理工具类
 *
 * @author yuxr
 * @since 2018/9/17 16:51
 */
public class TestUserManageUtils {

  /** 初始化测试用户1，2；测试角色1，2；1用户拥有所有角色；2用户只有2权限；1，2角色都有全部权限 */
  public static void initUserRolePermission(UserDao userDao, RoleDao roleDao) {
    User inputUser;
    Role inputRole;
    UserRole inputUserRole;
    RolePermission inputRolepermission;
    List<Long> ids;

    inputUser = new User();
    inputUser.setId(TestConstant.TEST_USER_ID_1);
    inputUser.setUserCode(TestConstant.TEST_USER_CODE_1);
    inputUser.setUserName(TestConstant.TEST_USER_NAME_1);
    inputUser.setPassword(TestConstant.DEFAULT_PASSWORD);
    userDao.addUser(inputUser);
    inputUser = new User();
    inputUser.setId(TestConstant.TEST_USER_ID_2);
    inputUser.setUserCode(TestConstant.TEST_USER_CODE_2);
    inputUser.setUserName(TestConstant.TEST_USER_NAME_2);
    inputUser.setPassword(TestConstant.DEFAULT_PASSWORD);
    userDao.addUser(inputUser);

    inputRole = new Role();
    inputRole.setId(TestConstant.TEST_ROLE_ID_1);
    inputRole.setRoleName(TestConstant.TEST_ROLE_NAME_1);
    roleDao.addRole(inputRole);
    inputRole = new Role();
    inputRole.setId(TestConstant.TEST_ROLE_ID_2);
    inputRole.setRoleName(TestConstant.TEST_ROLE_NAME_2);
    roleDao.addRole(inputRole);

    inputUserRole = new UserRole();
    inputUserRole.setRoleId(TestConstant.TEST_ROLE_ID_1);
    ids = new ArrayList<>();
    ids.add(TestConstant.TEST_USER_ID_1);
    ids.add(TestConstant.TEST_USER_ID_2);
    inputUserRole.setUserIdList(ids);
    roleDao.addUserRoles(inputUserRole);
    inputUserRole = new UserRole();
    inputUserRole.setRoleId(TestConstant.TEST_ROLE_ID_2);
    ids = new ArrayList<>();
    ids.add(TestConstant.TEST_USER_ID_2);
    inputUserRole.setUserIdList(ids);
    roleDao.addUserRoles(inputUserRole);

    inputRolepermission = new RolePermission();
    inputRolepermission.setRoleId(TestConstant.TEST_ROLE_ID_1);
    ids = new ArrayList<>();
    ids.add(0L);
    ids.add(1L);
    ids.add(2L);
    ids.add(3L);
    ids.add(4L);
    ids.add(5L);
    ids.add(6L);
    ids.add(7L);
    ids.add(8L);
    ids.add(9L);
    ids.add(10L);
    ids.add(11L);
    inputRolepermission.setPermissionIdList(ids);
    roleDao.addRolePermissions(inputRolepermission);
    inputRolepermission = new RolePermission();
    inputRolepermission.setRoleId(TestConstant.TEST_ROLE_ID_2);
    ids = new ArrayList<>();
    ids.add(0L);
    ids.add(1L);
    ids.add(2L);
    ids.add(3L);
    ids.add(4L);
    ids.add(5L);
    ids.add(6L);
    ids.add(7L);
    ids.add(8L);
    ids.add(9L);
    ids.add(10L);
    ids.add(11L);
    inputRolepermission.setPermissionIdList(ids);
    roleDao.addRolePermissions(inputRolepermission);
  }
}
