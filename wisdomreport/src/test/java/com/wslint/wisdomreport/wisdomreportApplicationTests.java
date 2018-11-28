package com.wslint.wisdomreport;

import com.wslint.wisdomreport.controller.ControllerTest;
import com.wslint.wisdomreport.dao.DaoTest;
import com.wslint.wisdomreport.service.AllServiceImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AllServiceImplTest.class, ControllerTest.class, DaoTest.class})
public class wisdomreportApplicationTests {}
