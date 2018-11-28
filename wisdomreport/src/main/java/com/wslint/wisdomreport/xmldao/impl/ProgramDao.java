package com.wslint.wisdomreport.xmldao.impl;

import com.wslint.wisdomreport.constant.XMLConstant;
import com.wslint.wisdomreport.domain.dto.xml.program.ProgramDTO;
import com.wslint.wisdomreport.utils.FileUtils;
import com.wslint.wisdomreport.utils.XMLUtils;
import com.wslint.wisdomreport.xmldao.IProgramDao;
import org.springframework.stereotype.Service;

/**
 * 数据接口
 *
 * @author yuxr
 * @since 2018/11/1 14:37
 */
@Service
public class ProgramDao implements IProgramDao {
  /**
   * 根据药品id获取程序对象
   *
   * @return 程序对象
   */
  @Override
  public ProgramDTO getPrograms() {
    String path = FileUtils.getProductPath();
    return XMLUtils.xmlFileToDto(ProgramDTO.class, path, XMLConstant.FILE_PROGRAM);
  }
}
