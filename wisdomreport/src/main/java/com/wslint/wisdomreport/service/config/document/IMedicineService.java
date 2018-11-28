package com.wslint.wisdomreport.service.config.document;

import com.wslint.wisdomreport.domain.dto.xml.relation.IdNameDTO;
import java.util.List;
import java.util.Map;

/**
 * 药品数据服务接口
 *
 * @author yuxr
 * @since 2018/10/29 12:16
 */
public interface IMedicineService {

  /**
   * 获取所有药品
   *
   * @return 所有药品信息
   */
  List<IdNameDTO> getAllMedicines();

  /**
   * 新增药品
   *
   * @param name 药品名称
   * @param factory 工厂信息
   */
  Map<String, Object> addMedicine(String name, String factory);

  /**
   * 修改药品名称
   *
   * @param id 药品id
   * @param name 药品名称
   * @return 处理结果
   */
  Map<String, Object> updateMedicine(Long id, String name);
}
