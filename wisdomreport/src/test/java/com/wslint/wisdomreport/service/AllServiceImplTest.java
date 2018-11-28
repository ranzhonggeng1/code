package com.wslint.wisdomreport.service;

import com.wslint.wisdomreport.service.file.impl.AllFileServiceImplTest;
import com.wslint.wisdomreport.service.user.impl.AllUserServiceImplTest;
import com.wslint.wisdomreport.service.workflow.impl.AllWorkflowServiceImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  AllFileServiceImplTest.class,
  AllUserServiceImplTest.class,
  AllWorkflowServiceImplTest.class
})
public class AllServiceImplTest {}
