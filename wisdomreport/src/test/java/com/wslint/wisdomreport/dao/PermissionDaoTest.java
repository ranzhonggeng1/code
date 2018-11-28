package com.wslint.wisdomreport.dao;

import com.google.gson.Gson;
import com.wslint.wisdomreport.dao.user.PermissionDao;
import com.wslint.wisdomreport.domain.dto.user.User;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PermissionDaoTest {

  @Autowired private PermissionDao permissionDao;

  @Test
  public void getUsersByPermissionCode() {
    List<User> user = permissionDao.getUsersByPermissionCode("0");
    System.out.print(new Gson().toJson(user));
  }
}
