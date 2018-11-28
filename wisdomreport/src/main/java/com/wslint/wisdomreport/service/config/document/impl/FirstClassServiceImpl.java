package com.wslint.wisdomreport.service.config.document.impl;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.domain.dto.xml.firstclass.FirstClassDTO;
import com.wslint.wisdomreport.domain.dto.xml.medicine.MedicineDTO;
import com.wslint.wisdomreport.domain.dto.xml.product.ConfigDTO;
import com.wslint.wisdomreport.domain.dto.xml.product.ProductDTO;
import com.wslint.wisdomreport.domain.dto.xml.relation.IdDTO;
import com.wslint.wisdomreport.service.config.document.IFirstClassService;
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
 * 大类服务接口
 *
 * @author yuxr
 * @since 2018/10/29 14:05
 */
@Service
public class FirstClassServiceImpl implements IFirstClassService {

  @Autowired IFirstClassDao iFirstClassDao;
  @Autowired ISecondClassDao iSecondClassDao;
  @Autowired IMedicineDao iMedicineDao;
  @Autowired IProductDao iProductDao;

  /**
   * 根据药品id获取大类信息
   *
   * @param medicineId 药品id
   * @return 大类信息
   */
  @Override
  public List<FirstClassDTO> getFirstClasssByMedicineId(Long medicineId) {
    List<FirstClassDTO> firstClassDTOList = new ArrayList<>();
    // 根据药品id获取药品信息
    MedicineDTO medicineDTO = iMedicineDao.getMedicinesById(medicineId);
    List<IdDTO> firstClasss = medicineDTO.getFirstClasss();
    // 根据药品中的关联信息加载大类信息
    if (firstClasss != null) {
      for (IdDTO firstClass : firstClasss) {
        firstClassDTOList.add(
            iFirstClassDao.getFirstClassByMedicineFirstClassId(medicineId, firstClass.getId()));
      }
    }
    return firstClassDTOList;
  }

  /**
   * 根据药品和大类id获取大类对象
   *
   * @param medicineId 药品id
   * @param firstClassId 大类id
   * @return 大类对象
   */
  @Override
  public FirstClassDTO getFirstClassByMedicineFirstClassId(Long medicineId, Long firstClassId) {
    List<FirstClassDTO> firstClasss = getFirstClasssByMedicineId(medicineId);
    for (FirstClassDTO firstClass : firstClasss) {
      if (firstClassId.equals(firstClass.getId())) {
        return firstClass;
      }
    }
    return new FirstClassDTO();
  }

  /**
   * 新增大类信息
   *
   * @param medicineId 药品id
   * @param name 名称
   * @return 新的id
   */
  @Override
  public Map<String, Object> addFirstClass(Long medicineId, String name) {

    // 生成id，更新配置
    ProductDTO productDTO = iProductDao.getProduct();
    ConfigDTO configDTO = productDTO.getConfig();

    // 公共空对象
    List<IdDTO> idDTOList = new ArrayList<>();

    // 生成大类对象
    FirstClassDTO firstClassDTO = new FirstClassDTO();
    firstClassDTO.setId(configDTO.getNewFirstClassId());
    firstClassDTO.setName(name);
    firstClassDTO.setSecondClasss(idDTOList);

    // 大类在药品文件中注册
    MedicineDTO medicineDTO = iMedicineDao.getMedicinesById(medicineId);
    if (medicineDTO.getFirstClasss() == null) {
      return ReturnUtils.failureMap("未找到对应药品信息！");
    }
    List<IdDTO> firstClasss = medicineDTO.getFirstClasss();
    IdDTO newFirstClass = new IdDTO();
    newFirstClass.setId(firstClassDTO.getId());
    firstClasss.add(newFirstClass);

    // 生成xml
    if (!iProductDao.setProduct(productDTO)) {
      return ReturnUtils.failureMap("产品文件存储失败，请联系系统管理员！");
    }
    if (!iFirstClassDao.setFirstClass(medicineId, firstClassDTO)) {
      return ReturnUtils.failureMap("大类文件存储失败，请联系系统管理员！");
    }
    if (!iMedicineDao.setMedicine(medicineId, medicineDTO)) {
      return ReturnUtils.failureMap("药品文件存储失败，请联系系统管理员！");
    }
    Map<String, Object> rtnMap = new HashMap<>();
    rtnMap.put(Constant.STR_ID, firstClassDTO.getId());
    return ReturnUtils.successMap(rtnMap, "新增大类成功！");
  }

  /**
   * 修改大类名称
   *
   * @param medicineId 药品id
   * @param id 大类id
   * @param name 大类名称
   * @return 处理结果
   */
  @Override
  public Map<String, Object> updateFirstClass(Long medicineId, Long id, String name) {
    FirstClassDTO firstClassDTO = getFirstClassByMedicineFirstClassId(medicineId, id);
    firstClassDTO.setName(name);
    if (!iFirstClassDao.setFirstClass(medicineId, firstClassDTO)) {
      return ReturnUtils.failureMap("大类文件存储失败，请联系系统管理员！");
    }
    return ReturnUtils.successMap("修改大类成功！");
  }
}
