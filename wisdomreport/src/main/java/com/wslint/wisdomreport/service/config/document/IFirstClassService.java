package com.wslint.wisdomreport.service.config.document;

import com.wslint.wisdomreport.domain.dto.xml.firstclass.FirstClassDTO;
import java.util.List;
import java.util.Map;

/**
 * 大类服务接口
 *
 * @author yuxr
 * @since 2018/10/29 14:05
 */
public interface IFirstClassService {

  /**
   * 根据药品id获取大类信息
   *
   * @param medicineId 药品id
   * @return 大类信息
   */
  List<FirstClassDTO> getFirstClasssByMedicineId(Long medicineId);

  /**
   * 新增大类信息
   *
   * @param medicineId 药品id
   * @param name 名称
   * @return 新的id
   */
  Map<String, Object> addFirstClass(Long medicineId, String name);

  /**
   * 根据药品和大类id获取大类对象
   *
   * @param medicineId 药品id
   * @param firstClassId 大类id
   * @return 大类id
   */
  FirstClassDTO getFirstClassByMedicineFirstClassId(Long medicineId, Long firstClassId);

  /**
   * 修改大类名称
   *
   * @param medicineId 药品id
   * @param id 大类id
   * @param name 大类名称
   * @return 处理结果
   */
  Map<String, Object> updateFirstClass(Long medicineId, Long id, String name);
}
