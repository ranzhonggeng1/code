package com.wslint.wisdomreport.dao;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.constant.UserConstant;
import com.wslint.wisdomreport.dao.user.UserDao;
import com.wslint.wisdomreport.domain.dto.user.User;
import com.wslint.wisdomreport.utils.CommonUtils;
import java.util.Random;
import jodd.crypt.BCrypt;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserDaoTest {

  @Autowired private UserDao userDao;

  private User inputUser;

  private Random random = new Random();

  @Test
  public void isUserExisted() {

    // 测试userCode重复判断
    inputUser = new User();
    inputUser.setUserCode(Constant.STR_ADMIN);
    Assert.assertTrue(userDao.isUserExisted(inputUser));
    // 测试userName重复判断
    inputUser = new User();
    inputUser.setUserName("系统管理员");
    Assert.assertTrue(userDao.isUserExisted(inputUser));
    // 随机userCode和随机userName重复判断
    inputUser = new User();
    inputUser.setUserCode(CommonUtils.getRandomString(Constant.NUM_16));
    inputUser.setUserName("测试用户" + random.nextInt());
    Assert.assertFalse(userDao.isUserExisted(inputUser));
  }

  @Test
  public void addUserDao() {
    inputUser = new User();
    inputUser.setId(CommonUtils.getNextId());
    inputUser.setUserCode(CommonUtils.getRandomString(Constant.NUM_16));
    inputUser.setUserName("测试用户" + random.nextInt());
    inputUser.setPassword("1");
    inputUser.setId(CommonUtils.getNextId());
    inputUser.setPassword(BCrypt.hashpw(inputUser.getPassword(), BCrypt.gensalt()));
    inputUser.setComplete(UserConstant.STATUS_NOT_COMPLETE);
    Assert.assertTrue(userDao.addUser(inputUser));
  }

  @Test
  public void gerUserByCode() {}

  @Test
  public void getRolesByUserId() {}

  @Test
  public void getPermissionsByRoleId() {}

  @Test
  public void updateUser() {
    inputUser = new User();
    inputUser.setId(Constant.ID_0);
    inputUser.setPassword(Constant.SIR_DEFAULT_R_AP);
    inputUser.setSign(CommonUtils.getRandomString(Constant.NUM_16));
    userDao.updateUser(inputUser);
  }
}
