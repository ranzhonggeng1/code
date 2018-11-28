package com.wslint.wisdomreport.xmldao;

import com.wslint.wisdomreport.domain.dto.xml.medicine.MedicineDTO;

/**
 * 药品dao
 *
 * @author yxur
 * @since 2018/10/29 13:43
 */
public interface IMedicineDao {

  /**
   * 根据药品id 加载药品信息
   *
   * @param medicineId 药品id
   * @return 药品信息
   */
  MedicineDTO getMedicinesById(Long medicineId);

  /**
   * 生成药品文件
   *
   * @param id 药品 id
   * @param medicineDTO 药品
   */
  boolean setMedicine(Long id, MedicineDTO medicineDTO);
}
