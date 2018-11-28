package com.wslint.wisdomreport.controller.config.document;

import com.wslint.wisdomreport.domain.dto.xml.firstclass.FirstClassDTO;
import com.wslint.wisdomreport.domain.dto.xml.relation.IdNameDTO;
import com.wslint.wisdomreport.domain.vo.xml.FirstClassIdNameVO;
import com.wslint.wisdomreport.domain.vo.xml.FirstClassVO;
import com.wslint.wisdomreport.service.config.document.IFirstClassService;
import com.wslint.wisdomreport.utils.BeanCopyUtil;
import com.wslint.wisdomreport.utils.ReturnUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文档controller
 *
 * @author yuxe
 * @since 2018/10/29 11:44
 */
@Api(tags = "5 文档配置接口", description = "提供文档配置的相关接口")
@RestController
@RequestMapping(value = "/config/document/firstclass")
public class FirstClassController {

  @Autowired private IFirstClassService iFirstClassService;

  /**
   * 根据药品id获取对应大类信息
   *
   * @return 大类信息
   */
  @ApiOperation(value = "获取大类信息", notes = "获取大类信息")
  @ApiImplicitParam(name = "medicineId", value = "药品id", required = true, defaultValue = "1")
  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public Map<String, Object> get(@RequestParam Long medicineId) throws Exception {
    List<FirstClassDTO> firstClasss = iFirstClassService.getFirstClasssByMedicineId(medicineId);
    List<IdNameDTO> idNameDTOList = BeanCopyUtil.copyList(firstClasss, IdNameDTO.class);
    return ReturnUtils.successMap(idNameDTOList, "获取大类成功！");
  }

  /**
   * 新增大类
   *
   * @param firstClassVO 大类对象
   * @return 处理结果
   */
  @ApiOperation(value = "新增大类", notes = "新增大类")
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public Map<String, Object> add(@RequestBody FirstClassVO firstClassVO) {
    return iFirstClassService.addFirstClass(firstClassVO.getMedicineId(), firstClassVO.getName());
  }

  /**
   * 修改大类名称
   *
   * @param firstClassIdNameVO 大类对象
   * @return 处理结果
   */
  @ApiOperation(value = "修改大类名称", notes = "修改大类名称")
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public Map<String, Object> update(@RequestBody FirstClassIdNameVO firstClassIdNameVO) {
    return iFirstClassService.updateFirstClass(
        firstClassIdNameVO.getMedicineId(),
        firstClassIdNameVO.getId(),
        firstClassIdNameVO.getName());
  }
}
