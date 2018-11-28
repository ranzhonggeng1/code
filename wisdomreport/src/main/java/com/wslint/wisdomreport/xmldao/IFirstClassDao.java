package com.wslint.wisdomreport.xmldao;

import com.wslint.wisdomreport.domain.dto.xml.firstclass.FirstClassDTO;

/**
 * 大类信息加载
 *
 * @author yxur
 * @since 2018/10/29 14:12
 */
public interface IFirstClassDao {

  /**
   * 根据药品id和大类id加载大类信息
   *
   * @param medicineId 药品id
   * @param firstClassId 大类id
   * @return 大类数据
   */
  FirstClassDTO getFirstClassByMedicineFirstClassId(Long medicineId, Long firstClassId);

  /**
   * 生成大类文件
   *
   * @param medicineId 药品id
   * @param firstClassDTO 大类对象
   */
  boolean setFirstClass(Long medicineId, FirstClassDTO firstClassDTO);
}
