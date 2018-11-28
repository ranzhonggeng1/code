package com.wslint.wisdomreport.service.config.document.impl;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.domain.dto.xml.firstclass.FirstClassDTO;
import com.wslint.wisdomreport.domain.dto.xml.product.ConfigDTO;
import com.wslint.wisdomreport.domain.dto.xml.product.ProductDTO;
import com.wslint.wisdomreport.domain.dto.xml.relation.IdDTO;
import com.wslint.wisdomreport.domain.dto.xml.secondclass.SecondClassDTO;
import com.wslint.wisdomreport.domain.dto.xml.secondclass.SpaceDTO;
import com.wslint.wisdomreport.service.config.document.IFirstClassService;
import com.wslint.wisdomreport.service.config.document.ISecondClassService;
import com.wslint.wisdomreport.utils.ReturnUtils;
import com.wslint.wisdomreport.xmldao.IFirstClassDao;
import com.wslint.wisdomreport.xmldao.IMedicineDao;
import com.wslint.wisdomreport.xmldao.IProductDao;
import com.wslint.wisdomreport.xmldao.ISecondClassDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 小类数据服务
 *
 * @author yuxr
 * @since 2018/10/29 16:18
 */
@Service
public class SecondClassServiceImpl implements ISecondClassService {

  @Autowired IMedicineDao iMedicineDao;
  @Autowired IFirstClassDao iFirstClassDao;
  @Autowired ISecondClassDao iSecondClassDao;
  @Autowired IFirstClassService iFirstClassService;
  @Autowired IProductDao iProductDao;

  /**
   * 根据药品id和大类id获取小类数据
   *
   * @param medicineId 药品id
   * @param firstClassId 大类id
   * @return 小类数据
   */
  @Override
  public List<SecondClassDTO> getSecondClasssByMedicineFirstClassId(
      Long medicineId, Long firstClassId) {
    List<SecondClassDTO> secondClassDTOList = new ArrayList<>();
    List<FirstClassDTO> firstClassDTOList =
        iFirstClassService.getFirstClasssByMedicineId(medicineId);
    for (FirstClassDTO firstClassDTO : firstClassDTOList) {
      if (firstClassId.equals(firstClassDTO.getId())) {
        List<IdDTO> secondClasss = firstClassDTO.getSecondClasss();
        for (IdDTO secondClass : secondClasss) {
          SecondClassDTO secondClassDTO =
              iSecondClassDao.getSecondClassByMedicineSecondClassId(
                  medicineId, secondClass.getId());
          secondClassDTOList.add(secondClassDTO);
        }
        break;
      }
    }
    return secondClassDTOList;
  }

  /**
   * 根据药品id、大类id和小类id获取小类数据
   *
   * @param medicineId 药品id
   * @param secondClassId 小类id
   * @return 小类对象
   */
  @Override
  public SecondClassDTO getSecondClassByMedicineSecondClassId(Long medicineId, Long secondClassId) {
    return iSecondClassDao.getSecondClassByMedicineSecondClassId(medicineId, secondClassId);
  }

  /**
   * 新增小类数据
   *
   * @param medicineId 药品id
   * @param firstClassId 大类id
   * @param name 小类名称
   * @return 新增信息
   */
  @Override
  public Map<String, Object> addSecondClass(Long medicineId, Long firstClassId, String name) {
    // 生成id，更新配置
    ProductDTO productDTO = iProductDao.getProduct();
    ConfigDTO configDTO = productDTO.getConfig();

    // 生成小类对象
    SecondClassDTO secondClassDTO = new SecondClassDTO();
    secondClassDTO.setId(configDTO.getNewSecondClassId());
    secondClassDTO.setName(name);

    // 小类在大类文件中注册
    FirstClassDTO firstClassDTO =
        iFirstClassService.getFirstClassByMedicineFirstClassId(medicineId, firstClassId);
    List<IdDTO> secondClasss = firstClassDTO.getSecondClasss();
    IdDTO secondClass = new IdDTO();
    secondClass.setId(secondClassDTO.getId());
    secondClasss.add(secondClass);

    // 生成xml
    if (!iProductDao.setProduct(productDTO)) {
      return ReturnUtils.failureMap("产品文件存储失败，请联系系统管理员！");
    }
    if (!iSecondClassDao.setSecondClass(medicineId, secondClassDTO)) {
      return ReturnUtils.failureMap("药品文件存储失败，请联系系统管理员！");
    }
    if (!iFirstClassDao.setFirstClass(medicineId, firstClassDTO)) {
      return ReturnUtils.failureMap("大类文件存储失败，请联系系统管理员！");
    }
    Map<String, Object> rtnMap = new HashMap<>();
    rtnMap.put(Constant.STR_ID, secondClassDTO.getId());
    return ReturnUtils.successMap(rtnMap, "新增小类成功！");
  }

  /**
   * 修改小类名称
   *
   * @param medicineId 药品id
   * @param secondClassId 小类id
   * @param name 小类名称
   * @return 处理结果
   */
  @Override
  public Map<String, Object> updateFirstClass(Long medicineId, Long secondClassId, String name) {
    SecondClassDTO secondClassDTO =
        iSecondClassDao.getSecondClassByMedicineSecondClassId(medicineId, secondClassId);
    secondClassDTO.setName(name);
    if (!iSecondClassDao.setSecondClass(medicineId, secondClassDTO)) {
      return ReturnUtils.failureMap("小类文件存储失败，请联系系统管理员！");
    }
    return ReturnUtils.successMap("修改小类成功！");
  }

  /**
   * 上传html模板数据的处理
   *
   * @param medicineId 药品id
   * @param secondClassId 小类id
   * @param htmlUrl html url
   * @param spaceDTOList 空格信息
   */
  @Override
  public Map<String, Object> uploadHtml(
      Long medicineId, Long secondClassId, String htmlUrl, List<SpaceDTO> spaceDTOList) {
    SecondClassDTO secondClassDTO =
        iSecondClassDao.getSecondClassByMedicineSecondClassId(medicineId, secondClassId);
    secondClassDTO.setHtmlURL(htmlUrl);
    secondClassDTO.setSpaces(spaceDTOList);
    if (!iSecondClassDao.setSecondClass(medicineId, secondClassDTO)) {
      return ReturnUtils.failureMap("小类文件存储失败，请联系系统管理员！");
    }
    return ReturnUtils.successMap("上传文件成功！");
  }
}
