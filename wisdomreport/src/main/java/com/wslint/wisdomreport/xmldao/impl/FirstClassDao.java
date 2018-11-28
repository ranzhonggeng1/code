package com.wslint.wisdomreport.xmldao.impl;

import com.wslint.wisdomreport.domain.dto.xml.firstclass.FirstClassDTO;
import com.wslint.wisdomreport.utils.FileUtils;
import com.wslint.wisdomreport.utils.XMLUtils;
import com.wslint.wisdomreport.xmldao.IFirstClassDao;
import org.springframework.stereotype.Service;

/**
 * 大类信息加载
 *
 * @author yxur
 * @since 2018/10/29 14:13
 */
@Service
public class FirstClassDao implements IFirstClassDao {

  /**
   * 根据药品id和大类id加载大类信息
   *
   * @param medicineId 药品id
   * @param firstClassId 大类id
   * @return 大类数据
   */
  @Override
  public FirstClassDTO getFirstClassByMedicineFirstClassId(Long medicineId, Long firstClassId) {
    String path = FileUtils.getFirstClassPath(medicineId);
    return XMLUtils.xmlFileToDto(FirstClassDTO.class, path, firstClassId.toString());
  }

  /**
   * 生成大类文件
   *
   * @param medicineId 药品id
   * @param firstClassDTO 大类对象
   */
  @Override
  public boolean setFirstClass(Long medicineId, FirstClassDTO firstClassDTO) {
    String path = FileUtils.getFirstClassPath(medicineId);
    return XMLUtils.dtoToXml(firstClassDTO, path, firstClassDTO.getId().toString());
  }
}
