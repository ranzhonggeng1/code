package com.wslint.wisdomreport.service.config.system;

/**
 * 系统配置服务
 *
 * @author yuxr
 * @since 2018/9/30 14:17
 */
public interface ISysConfigService {

  /**
   * 根据配置code获取配置value
   *
   * @param code 配置code
   * @return 配置value
   */
  String getValueByCode(String code);
}
