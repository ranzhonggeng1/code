package com.wslint.wisdomreport.service.file.impl;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.domain.dto.file.CfgfileVersion;
import com.wslint.wisdomreport.service.file.IFileService;
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
public class FileServiceImplTest {

  @Autowired private IFileService iFileService;

  @Before
  public void setUp() {}

  @After
  public void tearDown() {}

  @Test
  public void getCfgfileVersionByMedicineId() {
    CfgfileVersion cfgfileVersion = iFileService.getCfgfileVersionByMedicineId(Constant.ID_0);
    Assert.assertEquals(cfgfileVersion.getVersion(), Constant.STR_0);
    Assert.assertNotNull(cfgfileVersion.getFileName());
    Assert.assertNotNull(cfgfileVersion.getPath());
  }

  @Test
  public void getCfgfileVersionByMedicineIdAndVersion() {
    CfgfileVersion cfgfileVersion =
        iFileService.getCfgfileVersionByMedicineIdAndVersion(Constant.ID_0, Constant.STR_0);
    Assert.assertNotNull(cfgfileVersion.getFileName());
    Assert.assertNotNull(cfgfileVersion.getPath());
  }
}
