package com.wslint.wisdomreport.controller.config.document;

import com.wslint.wisdomreport.domain.dto.xml.relation.IdNameDTO;
import com.wslint.wisdomreport.domain.vo.xml.MedicineIdNameVO;
import com.wslint.wisdomreport.domain.vo.xml.MedicineVO;
import com.wslint.wisdomreport.service.config.document.IMedicineService;
import com.wslint.wisdomreport.utils.ReturnUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文档controller
 *
 * @author yuxe
 * @since 2018/10/29 11:44
 */
@Api(tags = "5 文档配置接口", description = "提供文档配置的相关接口")
@RestController
@RequestMapping(value = "/config/document/medicine")
public class MedicineController {

  @Autowired private IMedicineService iMedicineService;

  /**
   * 获取所有药品
   *
   * @return 所有药品信息
   */
  @ApiOperation(value = "获取所有药品", notes = "获取所有药品")
  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public Map<String, Object> get() {
    List<IdNameDTO> medicines = iMedicineService.getAllMedicines();
    return ReturnUtils.successMap(medicines, "获取所有药品成功！");
  }

  /**
   * 新增药品
   *
   * @param medicineVO 药品对象
   * @return 处理结果
   */
  @ApiOperation(value = "新增药品", notes = "新增药品")
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public Map<String, Object> add(@RequestBody MedicineVO medicineVO) {
    return iMedicineService.addMedicine(medicineVO.getName(), medicineVO.getFactory());
  }

  /**
   * 修改药品名称
   *
   * @param medicineIdNameVO 药品对象
   * @return 处理结果
   */
  @ApiOperation(value = "修改药品名称", notes = "修改药品名称")
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public Map<String, Object> update(@RequestBody MedicineIdNameVO medicineIdNameVO) {
    return iMedicineService.updateMedicine(medicineIdNameVO.getId(), medicineIdNameVO.getName());
  }
}
