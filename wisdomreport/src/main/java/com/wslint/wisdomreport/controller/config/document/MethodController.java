package com.wslint.wisdomreport.controller.config.document;

import com.wslint.wisdomreport.domain.dto.xml.program.FormulaDTO;
import com.wslint.wisdomreport.domain.vo.xml.FormulaSaveVO;
import com.wslint.wisdomreport.domain.vo.xml.MethodAddVO;
import com.wslint.wisdomreport.domain.vo.xml.MethodDeleteVO;
import com.wslint.wisdomreport.domain.vo.xml.MethodUpdateVO;
import com.wslint.wisdomreport.domain.vo.xml.RelationVO;
import com.wslint.wisdomreport.service.config.document.IMethodService;
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
 * 方法管理接口
 *
 * @author yuxr
 * @since 2018/11/1 10:55
 */
@Api(tags = "5 文档配置接口", description = "提供文档配置的相关接口")
@RestController
@RequestMapping(value = "/config/document/method")
public class MethodController {

  @Autowired IMethodService iMethodService;

  /**
   * 公式查询
   *
   * @param functionId 方法id
   * @return 处理结果
   */
  @ApiOperation(value = "公式查询接口", notes = "公式查询接口")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "functionId", value = "方法id", required = true, defaultValue = "1")
  })
  @RequestMapping(value = "/formulas/get", method = RequestMethod.GET)
  public Map<String, Object> formulasGet(@RequestParam Long functionId) {
    List<FormulaDTO> formulaDTOList = iMethodService.getFormulasFunctionId(functionId);
    if (formulaDTOList == null) {
      return ReturnUtils.failureMap("查询失败，未找到公式信息！");
    }
    return ReturnUtils.successMap(formulaDTOList, "查询公式信息成功！");
  }

  /**
   * 获取所有公式接口
   *
   * @return 处理结果
   */
  @ApiOperation(value = "获取所有公式接口", notes = "获取所有公式接口")
  @RequestMapping(value = "/formulas/all", method = RequestMethod.GET)
  public Map<String, Object> formulasAll() {
    List<FormulaDTO> formulaDTOList = iMethodService.getFormulas();
    if (formulaDTOList == null) {
      return ReturnUtils.failureMap("查询失败，未找到公式信息！");
    }
    return ReturnUtils.successMap(formulaDTOList, "查询公式信息成功！");
  }

  /**
   * 方法添加公式接口
   *
   * @param formulaSaveVO 新增公式对象
   * @return 处理结果
   */
  @ApiOperation(value = "方法添加公式接口", notes = "方法添加公式接口")
  @RequestMapping(value = "/formulas/save", method = RequestMethod.POST)
  public Map<String, Object> formulasSave(@RequestBody FormulaSaveVO formulaSaveVO) {
    return iMethodService.formulasSave(formulaSaveVO.getFunctionId(), formulaSaveVO.getFormulas());
  }

  /**
   * 方法参数添加接口
   *
   * @param methodAddVO 添加参数对象
   * @return 处理结果
   */
  @ApiOperation(value = "方法参数添加接口", notes = "方法参数添加接口")
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public Map<String, Object> add(@RequestBody MethodAddVO methodAddVO) {
    return iMethodService.addParamValue(
        methodAddVO.getMedicineId(),
        methodAddVO.getSecondId(),
        methodAddVO.getFunctionId(),
        methodAddVO.getTableId(),
        methodAddVO.getColumnDTOList());
  }
  /**
   * 方法参数修改接口
   *
   * @param methodUpdateVO 添加参数对象
   * @return 处理结果
   */
  @ApiOperation(value = "方法参数修改接口", notes = "方法参数修改接口")
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public Map<String, Object> update(@RequestBody MethodUpdateVO methodUpdateVO) {
    return iMethodService.updateParamValue(
        methodUpdateVO.getMedicineId(),
        methodUpdateVO.getSecondId(),
        methodUpdateVO.getFunctionId(),
        methodUpdateVO.getTableId(),
        methodUpdateVO.getRowId(),
        methodUpdateVO.getColumnDTOList());
  }
  /**
   * 方法参数删除接口
   *
   * @param methodDeleteVO 删除参数对象
   * @return 处理结果
   */
  @ApiOperation(value = "方法参数删除接口", notes = "方法参数删除接口")
  @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
  public Map<String, Object> delete(@RequestBody MethodDeleteVO methodDeleteVO) {
    return iMethodService.deleteParamValue(
        methodDeleteVO.getMedicineId(),
        methodDeleteVO.getSecondId(),
        methodDeleteVO.getFunctionId(),
        methodDeleteVO.getTableId(),
        methodDeleteVO.getRowId());
  }

  /**
   * 方法参数关联关系保存接口
   *
   * @param relationVO 添加参数对象
   * @return 处理结果
   */
  @ApiOperation(value = "方法参数关联关系保存接口", notes = "方法参数关联关系保存接口")
  @RequestMapping(value = "/relation/save", method = RequestMethod.POST)
  public Map<String, Object> saveRelation(@RequestBody RelationVO relationVO) {
    return iMethodService.saveRelation(
        relationVO.getMedicineId(),
        relationVO.getSecondId(),
        relationVO.getFunctionId(),
        relationVO.getTableId(),
        relationVO.getCode(),
        relationVO.getValue());
  }
}
