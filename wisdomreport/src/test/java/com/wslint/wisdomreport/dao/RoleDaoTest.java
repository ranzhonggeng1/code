package com.wslint.wisdomreport.dao;

import com.wslint.wisdomreport.dao.user.RoleDao;
import com.wslint.wisdomreport.domain.dto.role.RolePermission;
import java.util.ArrayList;
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
public class RoleDaoTest {

  @Autowired private RoleDao roleDao;

  @Test
  public void addRolePermissions() {
    RolePermission rolePermission = new RolePermission();
    rolePermission.setRoleId(488749064020692992L);
    List<Long> ids = new ArrayList<>();
    ids.add(111L);
    ids.add(222L);
    ids.add(333L);
    rolePermission.setPermissionIdList(ids);
    roleDao.addRolePermissions(rolePermission);
  }

  @Test
  public void deleteRolePermissions() {
    RolePermission rolePermission = new RolePermission();
    rolePermission.setRoleId(488749064020692992L);
    List<Long> ids = new ArrayList<>();
    ids.add(111L);
    ids.add(222L);
    ids.add(333L);
    rolePermission.setPermissionIdList(ids);
    roleDao.deleteRolePermissions(rolePermission);
  }
}
