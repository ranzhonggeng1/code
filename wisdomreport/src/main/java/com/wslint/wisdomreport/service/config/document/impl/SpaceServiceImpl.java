package com.wslint.wisdomreport.service.config.document.impl;

import com.wslint.wisdomreport.domain.dto.xml.secondclass.SecondClassDTO;
import com.wslint.wisdomreport.domain.dto.xml.secondclass.SpaceDTO;
import com.wslint.wisdomreport.service.config.document.ISecondClassService;
import com.wslint.wisdomreport.service.config.document.ISpaceService;
import com.wslint.wisdomreport.utils.ReturnUtils;
import com.wslint.wisdomreport.xmldao.ISecondClassDao;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

/**
 * 空格服务
 *
 * @author yuxr
 * @since 2018/10/31 10:51
 */
@Service
public class SpaceServiceImpl implements ISpaceService {

  @Autowired ISecondClassService iSecondClassService;
  @Autowired ISecondClassDao iSecondClassDao;
  /**
   * 根据药品id、小类id和空格id获取空格信息
   *
   * @param medicineId 药品id
   * @param secondClassId 小类id
   * @param spaceId 空格id
   * @return 空格对象
   */
  @Override
  public SpaceDTO getSpaceByMedicineSecondClassSpaceId(
      Long medicineId, Long secondClassId, Long spaceId) {
    SecondClassDTO secondClassDTO =
        iSecondClassDao.getSecondClassByMedicineSecondClassId(medicineId, secondClassId);
    List<SpaceDTO> spaceDTOList = secondClassDTO.getSpaces();
    for (SpaceDTO spaceDTO : spaceDTOList) {
      if (spaceId.equals(spaceDTO.getId())) {
        return spaceDTO;
      }
    }
    return new SpaceDTO();
  }

  /**
   * 保存空格信息
   *
   * @param medicineId 药品信息
   * @param secondClassId 小类信息
   * @param newSpaceDTO 空格信息
   */
  @Override
  public Map<String, Object> saveSpace(Long medicineId, Long secondClassId, SpaceDTO newSpaceDTO) {
    SecondClassDTO secondClassDTO =
        iSecondClassDao.getSecondClassByMedicineSecondClassId(medicineId, secondClassId);
    List<SpaceDTO> spaceDTOList = secondClassDTO.getSpaces();
    for (SpaceDTO spaceDTO : spaceDTOList) {
      if (newSpaceDTO.getId().equals(spaceDTO.getId())) {
        BeanCopier copier = BeanCopier.create(SpaceDTO.class, SpaceDTO.class, false);
        copier.copy(newSpaceDTO, spaceDTO, null);
        break;
      }
    }
    if (!iSecondClassDao.setSecondClass(medicineId, secondClassDTO)) {
      return ReturnUtils.failureMap("小类文件存储失败，请联系系统管理员！");
    }
    return ReturnUtils.successMap("保存成功");
  }
}
