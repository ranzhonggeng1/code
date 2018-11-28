package com.wslint.wisdomreport.common;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.service.config.system.ISysConfigService;
import com.wslint.wisdomreport.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 系统缓存
 *
 * @author yuxr
 * @since 2018/10/31 14:51
 */
@Component
public class SystemCache implements CommandLineRunner {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(SystemCache.class);

  @Autowired ISysConfigService iSysConfigService;

  @Override
  public void run(String... args) {
    LOGGER.info("------------------ 加载 系统参数 开始 ------------------");
    if (FileUtils.getFileRootPath() == null) {
      FileUtils.setFileRootPath(iSysConfigService.getValueByCode(Constant.PARAM_ROOT_PATH));
      LOGGER.info(
          "------------------ 加载 文件根路径 : {}  ------------------", FileUtils.getFileRootPath());
    }
  }
}
