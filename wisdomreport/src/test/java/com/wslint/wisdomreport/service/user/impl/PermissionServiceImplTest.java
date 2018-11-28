package com.wslint.wisdomreport.service.user.impl;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.dao.user.RoleDao;
import com.wslint.wisdomreport.dao.user.UserDao;
import com.wslint.wisdomreport.domain.dto.user.User;
import com.wslint.wisdomreport.service.user.IPermissionService;
import com.wslint.wisdomreport.util.TestConstant;
import com.wslint.wisdomreport.util.TestUserManageUtils;
import java.util.List;
import java.util.Map;
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
public class PermissionServiceImplTest {

  @Autowired private IPermissionService iPermissionService;
  @Autowired private UserDao userDao;
  @Autowired private RoleDao roleDao;
  private Map<String, Object> rtnMap;

  @Before
  public void setUp() {
    TestUserManageUtils.initUserRolePermission(userDao, roleDao);
  }

  @After
  public void tearDown() {}

  @Test
  public void getAllPermissions() {
    // 测试成功查询所有角色数据
    rtnMap = iPermissionService.getAllPermissions();
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_SUCCESS);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "查询所有权限成功！");
    Assert.assertNotNull(rtnMap.get(Constant.STR_DATA));
    List permissions = (List) rtnMap.get(Constant.STR_DATA);
    Assert.assertEquals(permissions.size(), Constant.INIT_PERMISSION);
  }

  @Test
  public void getUsersByPermissionCode() {
    // 测试成功根据权限码获取用户
    rtnMap = iPermissionService.getUsersByPermissionCode(Constant.STR_0);
    Assert.assertNotNull(rtnMap);
    Assert.assertEquals(rtnMap.get(Constant.STR_CODE), Constant.RETURN_CODE_SUCCESS);
    Assert.assertEquals(rtnMap.get(Constant.STR_MSG), "根据权限码获取用户成功！");
    Assert.assertNotNull(rtnMap.get(Constant.STR_DATA));
    List userList = (List) rtnMap.get(Constant.STR_DATA);
    int testUserNum = 0;
    for (Object user : userList) {
      Long id = ((User) user).getId();
      if (id.equals(TestConstant.TEST_USER_ID_1) || id.equals(TestConstant.TEST_USER_ID_2)) {
        testUserNum++;
      }
    }
    Assert.assertEquals(testUserNum, 2);
  }
}
