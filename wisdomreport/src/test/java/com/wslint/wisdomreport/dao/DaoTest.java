package com.wslint.wisdomreport.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Dao整合测试
 *
 * @author yuxr
 * @since 2018/9/19 13:57
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
  IDataDaoTest.class,
  PermissionDaoTest.class,
  RoleDaoTest.class,
  UserDaoTest.class,
  WorkFlowDaoTest.class
})
public class DaoTest {}
