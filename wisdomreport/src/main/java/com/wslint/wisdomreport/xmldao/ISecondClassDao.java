package com.wslint.wisdomreport.xmldao;

import com.wslint.wisdomreport.domain.dto.xml.secondclass.SecondClassDTO;

/**
 * 小类数据查询
 *
 * @author yuxr
 * @since 2018/10/29 16:20
 */
public interface ISecondClassDao {

  /**
   * 根据药品和小类id加载小类数据
   *
   * @param medicineId 药品id
   * @param secondClassId 小类id
   * @return 小类数据
   */
  SecondClassDTO getSecondClassByMedicineSecondClassId(Long medicineId, Long secondClassId);

  /**
   * 生成小类文件
   *
   * @param medicineId 药品id
   * @param secondClassDTO 小类对象
   * @return 是否成功生成文件
   */
  boolean setSecondClass(Long medicineId, SecondClassDTO secondClassDTO);
}
