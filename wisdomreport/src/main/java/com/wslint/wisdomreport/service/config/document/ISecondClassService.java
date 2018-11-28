package com.wslint.wisdomreport.service.config.document;

import com.wslint.wisdomreport.domain.dto.xml.secondclass.SecondClassDTO;
import com.wslint.wisdomreport.domain.dto.xml.secondclass.SpaceDTO;
import java.util.List;
import java.util.Map;

/**
 * 小类数据服务
 *
 * @author yuxr
 * @since 2018/10/29 16:18
 */
public interface ISecondClassService {
  /**
   * 根据药品id和大类id获取小类数据
   *
   * @param medicineId 药品id
   * @param firstClassId 大类id
   * @return 小类数据
   */
  List<SecondClassDTO> getSecondClasssByMedicineFirstClassId(Long medicineId, Long firstClassId);

  /**
   * 根据药品id、小类id获取小类数据
   *
   * @param medicineId 药品id
   * @param secondClassId 小类id
   * @return 小类对象
   */
  SecondClassDTO getSecondClassByMedicineSecondClassId(Long medicineId, Long secondClassId);
  /**
   * 新增小类数据
   *
   * @param medicineId 药品id
   * @param firstClassId 大类id
   * @param name 小类名称
   * @return 新增信息
   */
  Map<String, Object> addSecondClass(Long medicineId, Long firstClassId, String name);

  /**
   * 修改小类名称
   *
   * @param medicineId 药品id
   * @param secondClassId 小类id
   * @param name 小类名称
   * @return 处理结果
   */
  Map<String, Object> updateFirstClass(Long medicineId, Long secondClassId, String name);

  /**
   * 上传html模板数据的处理
   *
   * @param medicineId 药品id
   * @param secondClassId 小类id
   * @param htmlUrl html url
   * @param spaceDTOList 空格信息
   */
  Map<String, Object> uploadHtml(
      Long medicineId, Long secondClassId, String htmlUrl, List<SpaceDTO> spaceDTOList);
}
