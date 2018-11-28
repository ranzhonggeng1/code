package com.wslint.wisdomreport.xmldao.impl;

import com.wslint.wisdomreport.constant.XMLConstant;
import com.wslint.wisdomreport.domain.dto.xml.word.VersionDTO;
import com.wslint.wisdomreport.utils.FileUtils;
import com.wslint.wisdomreport.utils.XMLUtils;
import com.wslint.wisdomreport.xmldao.IWordDao;
import org.springframework.stereotype.Service;

/**
 * @Author: rzg
 * @Date: 2018/11/22 12:12
 */
@Service
public class WordDao implements IWordDao {

  /**
   * 获取药品word版本信息
   *
   * @return 获取word版本信息
   */
  @Override
  public VersionDTO getWordVersionInfoById(Long medicineId) {
    String path = FileUtils.getMedicineWordVersionXmlPath(medicineId);
    return XMLUtils.xmlFileToDto(VersionDTO.class, path, XMLConstant.FILE_WORD_VERSION);
  }

  /**
   * 根据药品ID更新word版本
   *
   * @param medicineId 药品ID
   * @param versionDTO 版本对象
   * @return 是否更新成功
   */
  @Override
  public boolean setWordVersionInfoById(Long medicineId, VersionDTO versionDTO) {
    String path = FileUtils.getMedicineWordVersionXmlPath(medicineId);
    return XMLUtils.dtoToXml(versionDTO, path, XMLConstant.FILE_WORD_VERSION);
  }
}
