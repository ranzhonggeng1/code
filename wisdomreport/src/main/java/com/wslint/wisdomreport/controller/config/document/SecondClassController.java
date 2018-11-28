package com.wslint.wisdomreport.controller.config.document;

import com.wslint.wisdomreport.domain.dto.xml.relation.IdNameDTO;
import com.wslint.wisdomreport.domain.dto.xml.secondclass.SecondClassDTO;
import com.wslint.wisdomreport.domain.vo.xml.SecondClassIdNameVO;
import com.wslint.wisdomreport.domain.vo.xml.SecondClassVO;
import com.wslint.wisdomreport.service.config.document.ISecondClassService;
import com.wslint.wisdomreport.utils.BeanCopyUtil;
import com.wslint.wisdomreport.utils.ReturnUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
@RequestMapping(value = "/config/document/secondclass")
public class SecondClassController {

  @Autowired private ISecondClassService iSecondClassService;

  /**
   * 根据药品id和大类id获取对应小类信息
   *
   * @return 小类信息
   */
  @ApiOperation(value = "获取小类信息", notes = "获取小类信息")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "medicineId", value = "药品id", required = true, defaultValue = "1"),
    @ApiImplicitParam(name = "firstClassId", value = "大类id", required = true, defaultValue = "3")
  })
  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public Map<String, Object> get(@RequestParam Long medicineId, @RequestParam Long firstClassId)
      throws Exception {
    List<SecondClassDTO> secondClassDTOList =
        iSecondClassService.getSecondClasssByMedicineFirstClassId(medicineId, firstClassId);
    List<IdNameDTO> idNameDTOList = BeanCopyUtil.copyList(secondClassDTOList, IdNameDTO.class);
    return ReturnUtils.successMap(idNameDTOList, "获取小类成功！");
  }

  /**
   * 新增小类
   *
   * @param secondClassVO 类对象
   * @return 处理结果
   */
  @ApiOperation(value = "新增小类", notes = "新增小类")
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public Map<String, Object> add(@RequestBody SecondClassVO secondClassVO) {
    return iSecondClassService.addSecondClass(
        secondClassVO.getMedicineId(), secondClassVO.getFirstClassId(), secondClassVO.getName());
  }

  /**
   * 修改小类名称
   *
   * @param secondClassIdNameVO 小类对象
   * @return 处理结果
   */
  @ApiOperation(value = "修改小类名称", notes = "修改小类名称")
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public Map<String, Object> update(@RequestBody SecondClassIdNameVO secondClassIdNameVO) {
    return iSecondClassService.updateFirstClass(
        secondClassIdNameVO.getMedicineId(),
        secondClassIdNameVO.getId(),
        secondClassIdNameVO.getName());
  }
}
