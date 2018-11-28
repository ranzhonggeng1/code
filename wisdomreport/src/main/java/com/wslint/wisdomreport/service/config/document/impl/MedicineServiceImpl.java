package com.wslint.wisdomreport.service.config.document.impl;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.domain.dto.xml.medicine.MedicineDTO;
import com.wslint.wisdomreport.domain.dto.xml.product.ConfigDTO;
import com.wslint.wisdomreport.domain.dto.xml.product.ProductDTO;
import com.wslint.wisdomreport.domain.dto.xml.relation.IdDTO;
import com.wslint.wisdomreport.domain.dto.xml.relation.IdNameDTO;
import com.wslint.wisdomreport.service.config.document.IMedicineService;
import com.wslint.wisdomreport.utils.ReturnUtils;
import com.wslint.wisdomreport.xmldao.IMedicineDao;
import com.wslint.wisdomreport.xmldao.IProductDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 药品数据服务接口
 *
 * @author yuxr
 * @since 2018/10/29 12:16
 */
@Service
public class MedicineServiceImpl implements IMedicineService {

  @Autowired IMedicineDao iMedicineDao;
  @Autowired IProductDao iProductDao;

  /**
   * 获取所有药品
   *
   * @return 所有药品信息
   */
  @Override
  public List<IdNameDTO> getAllMedicines() {
    ProductDTO productDTO = iProductDao.getProduct();
    return productDTO.getMedicines();
  }

  /**
   * 新增药品
   *
   * @param name 药品名称
   * @param factory 工厂信息
   */
  @Override
  public Map<String, Object> addMedicine(String name, String factory) {

    // 生成id，更新配置
    ProductDTO productDTO = iProductDao.getProduct();
    ConfigDTO configDTO = productDTO.getConfig();
    // 公共空对象
    List<IdDTO> idDTOList = new ArrayList<>();

    // 生成药品对象
    MedicineDTO medicineDTO = new MedicineDTO();
    medicineDTO.setId(configDTO.getNewMedicineId());
    medicineDTO.setName(name);
    medicineDTO.setFactoryName(factory);
    medicineDTO.setFirstClasss(idDTOList);
    // 产品对象添加药品
    List<IdNameDTO> medicines = productDTO.getMedicines();
    IdNameDTO newMedicine = new IdNameDTO();
    newMedicine.setId(medicineDTO.getId());
    newMedicine.setName(medicineDTO.getName());
    medicines.add(newMedicine);

    // 生成xml
    if (!iProductDao.setProduct(productDTO)) {
      return ReturnUtils.failureMap("产品文件存储失败，请联系管理员！");
    }
    if (!iMedicineDao.setMedicine(medicineDTO.getId(), medicineDTO)) {
      return ReturnUtils.failureMap("药品文件存储失败，请联系管理员！");
    }
    Map<String, Object> rtnMap = new HashMap<>();
    rtnMap.put(Constant.STR_ID, medicineDTO.getId());
    return ReturnUtils.successMap(rtnMap, "新增药品成功！");
  }

  /**
   * 修改药品名称
   *
   * @param id 药品id
   * @param name 药品名称
   * @return 处理结果
   */
  @Override
  public Map<String, Object> updateMedicine(Long id, String name) {

    // 修改产品文件中的药品名称
    ProductDTO productDTO = iProductDao.getProduct();
    List<IdNameDTO> medicines = productDTO.getMedicines();
    for (IdNameDTO medicine : medicines) {
      if (id.equals(medicine.getId())) {
        medicine.setName(name);
        break;
      }
    }
    // 修改药品文件中的名称
    MedicineDTO medicineDTO = iMedicineDao.getMedicinesById(id);
    medicineDTO.setName(name);

    // 生成xml
    if (!iProductDao.setProduct(productDTO)) {
      return ReturnUtils.failureMap("产品文件存储失败，请联系管理员！");
    }
    if (!iMedicineDao.setMedicine(id, medicineDTO)) {
      return ReturnUtils.failureMap("药品文件存储失败，请联系管理员！");
    }
    return ReturnUtils.successMap("修改药品成功！");
  }
}
