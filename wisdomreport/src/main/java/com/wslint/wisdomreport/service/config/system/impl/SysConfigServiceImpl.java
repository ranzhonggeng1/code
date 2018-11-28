package com.wslint.wisdomreport.service.config.system.impl;

import com.wslint.wisdomreport.dao.config.SysConfigDao;
import com.wslint.wisdomreport.service.config.system.ISysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统配置实现
 *
 * @author win 10
 * @since 2018/9/30 14:17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysConfigServiceImpl implements ISysConfigService {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(SysConfigServiceImpl.class);

  @Autowired private SysConfigDao sysConfigDao;

  /**
   * 根据配置code获取配置value
   *
   * @param code 配置code
   * @return 配置value
   */
  @Override
  public String getValueByCode(String code) {
    return sysConfigDao.getValueByCode(code);
  }
}
