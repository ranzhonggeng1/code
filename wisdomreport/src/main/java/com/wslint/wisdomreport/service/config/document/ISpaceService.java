package com.wslint.wisdomreport.service.config.document;

import com.wslint.wisdomreport.domain.dto.xml.secondclass.SpaceDTO;
import java.util.Map;

/**
 * 空格服务
 *
 * @author yuxr
 * @since 2018/10/31 10:51
 */
public interface ISpaceService {
  /**
   * 根据药品id、小类id和空格id获取空格信息
   *
   * @param medicineId 药品id
   * @param secondClassId 小类id
   * @param spaceId 空格id
   * @return 空格对象
   */
  SpaceDTO getSpaceByMedicineSecondClassSpaceId(Long medicineId, Long secondClassId, Long spaceId);

  /**
   * 保存空格信息
   *
   * @param medicineId 药品信息
   * @param secondClassId 小类信息
   * @param newSpaceDTO 空格信息
   */
  Map<String, Object> saveSpace(Long medicineId, Long secondClassId, SpaceDTO newSpaceDTO);
}
