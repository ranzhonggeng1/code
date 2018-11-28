package com.wslint.wisdomreport.xmldao.impl;

import com.wslint.wisdomreport.domain.dto.xml.secondclass.SecondClassDTO;
import com.wslint.wisdomreport.utils.FileUtils;
import com.wslint.wisdomreport.utils.XMLUtils;
import com.wslint.wisdomreport.xmldao.ISecondClassDao;
import org.springframework.stereotype.Service;

/**
 * 小类数据查询
 *
 * @author yxur
 * @since 2018/10/29 16:20
 */
@Service
public class SecondClassDao implements ISecondClassDao {

  /**
   * 根据药品和小类id加载小类数据
   *
   * @param medicineId 药品id
   * @param secondClassId 小类id
   * @return 小类数据
   */
  @Override
  public SecondClassDTO getSecondClassByMedicineSecondClassId(Long medicineId, Long secondClassId) {
    String path = FileUtils.getSecondClassPath(medicineId);
    return XMLUtils.xmlFileToDto(SecondClassDTO.class, path, secondClassId.toString());
  }

  /**
   * 生成小类文件
   *
   * @param medicineId 药品id
   * @param secondClassDTO 小类对象
   * @return 是否成功生成文件
   */
  @Override
  public boolean setSecondClass(Long medicineId, SecondClassDTO secondClassDTO) {
    String path = FileUtils.getSecondClassPath(medicineId);
    return XMLUtils.dtoToXml(secondClassDTO, path, secondClassDTO.getId().toString());
  }
}
