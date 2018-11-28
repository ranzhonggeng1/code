package com.wslint.wisdomreport.xmldao;

import com.wslint.wisdomreport.domain.dto.xml.program.ProgramDTO;

/**
 * 程序数据接口
 *
 * @author yuxr
 * @since 2018/11/1 14:37
 */
public interface IProgramDao {

  /**
   * 根据药品id获取程序对象
   *
   * @return 程序对象
   */
  ProgramDTO getPrograms();
}
