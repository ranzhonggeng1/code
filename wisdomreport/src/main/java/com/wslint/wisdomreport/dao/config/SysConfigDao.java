package com.wslint.wisdomreport.dao.config;

import org.apache.ibatis.annotations.Select;

/**
 * 系统配置dao
 *
 * @author yuxr
 * @since 2018/9/30 14:23
 */
public interface SysConfigDao {

  /**
   * 根据配置code获取配置value
   *
   * @param code 配置code
   * @return 配置value
   */
  @Select("select value from sys_config where code = #{code}")
  String getValueByCode(String code);
}
