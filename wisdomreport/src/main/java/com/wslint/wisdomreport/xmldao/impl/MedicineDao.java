package com.wslint.wisdomreport.xmldao.impl;

import com.wslint.wisdomreport.constant.XMLConstant;
import com.wslint.wisdomreport.domain.dto.xml.medicine.MedicineDTO;
import com.wslint.wisdomreport.utils.FileUtils;
import com.wslint.wisdomreport.utils.XMLUtils;
import com.wslint.wisdomreport.xmldao.IMedicineDao;
import org.springframework.stereotype.Service;

/**
 * 药品dao
 *
 * @author yuxr
 * @since 2018/10/29 13:44
 */
@Service
public class MedicineDao implements IMedicineDao {

  /**
   * 根据药品id 加载药品信息
   *
   * @param medicineId 药品id
   * @return 药品信息
   */
  @Override
  public MedicineDTO getMedicinesById(Long medicineId) {
    String path = FileUtils.getMedicinePath(medicineId);
    return XMLUtils.xmlFileToDto(MedicineDTO.class, path, XMLConstant.FILE_MEDICINE);
  }

  /**
   * 生成药品文件
   *
   * @param id 药品id
   * @param medicineDTO 药品
   */
  @Override
  public boolean setMedicine(Long id, MedicineDTO medicineDTO) {
    String path = FileUtils.getMedicinePath(id);
    return XMLUtils.dtoToXml(medicineDTO, path, XMLConstant.FILE_MEDICINE);
  }
}
