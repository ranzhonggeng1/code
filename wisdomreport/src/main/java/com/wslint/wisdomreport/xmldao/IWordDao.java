package com.wslint.wisdomreport.xmldao;

/**
 * @Author: rzg
 * @Date: 2018/11/22 12:04
 */

import com.wslint.wisdomreport.domain.dto.xml.word.VersionDTO;

/**
 * word版本XML读写
 */
public interface IWordDao {

  /**
   * 获取药品word版本信息
   *
   * @param medicineId 药品ID
   * @return 获取word版本信息
   */
  VersionDTO getWordVersionInfoById(Long medicineId);

  /**
   * 药品id 更新药品word版本
   *
   * @param versionDTO 版本对象
   * @param medicineId 药品ID
   */
  boolean setWordVersionInfoById(Long medicineId, VersionDTO versionDTO);
}
