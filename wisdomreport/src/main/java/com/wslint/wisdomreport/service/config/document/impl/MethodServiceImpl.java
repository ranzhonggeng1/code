package com.wslint.wisdomreport.service.config.document.impl;

import com.wslint.wisdomreport.domain.dto.xml.component.ColumnDTO;
import com.wslint.wisdomreport.domain.dto.xml.component.RelationDTO;
import com.wslint.wisdomreport.domain.dto.xml.component.RowDTO;
import com.wslint.wisdomreport.domain.dto.xml.component.TableDTO;
import com.wslint.wisdomreport.domain.dto.xml.method.FunctionDTO;
import com.wslint.wisdomreport.domain.dto.xml.method.MethodDTO;
import com.wslint.wisdomreport.domain.dto.xml.product.ProductDTO;
import com.wslint.wisdomreport.domain.dto.xml.program.FormulaDTO;
import com.wslint.wisdomreport.domain.dto.xml.program.ProgramDTO;
import com.wslint.wisdomreport.domain.dto.xml.relation.IdDTO;
import com.wslint.wisdomreport.domain.dto.xml.secondclass.ParamValueDTO;
import com.wslint.wisdomreport.domain.dto.xml.secondclass.SecondClassDTO;
import com.wslint.wisdomreport.service.config.document.IMethodService;
import com.wslint.wisdomreport.utils.ReturnUtils;
import com.wslint.wisdomreport.xmldao.IMethodDao;
import com.wslint.wisdomreport.xmldao.IProductDao;
import com.wslint.wisdomreport.xmldao.IProgramDao;
import com.wslint.wisdomreport.xmldao.ISecondClassDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 方法对象服务
 *
 * @author yuxr
 * @since 2018/10/31 10:21
 */
@Service
public class MethodServiceImpl implements IMethodService {

  @Autowired IProductDao iProductDao;
  @Autowired IMethodDao iMethodDao;
  @Autowired ISecondClassDao iSecondClassDao;
  @Autowired IProgramDao IProgramDao;

  /**
   * 根据药品id获取方法信息
   *
   * @return 方法对象
   */
  @Override
  public MethodDTO getMethodByMedicineId() {
    return iMethodDao.getMethods();
  }

  /**
   * 获取所有的公式
   *
   * @return 所有的公式信息
   */
  @Override
  public List<FormulaDTO> getFormulas() {
    ProgramDTO programDTO = IProgramDao.getPrograms();
    return programDTO.getFormulas();
  }

  /**
   * 保存方法对应公式信息
   *
   * @param functionId 方法id
   * @param formulas 公式信息
   * @return 处理结果
   */
  @Override
  public Map<String, Object> formulasSave(Long functionId, List<IdDTO> formulas) {
    MethodDTO methodDTO = iMethodDao.getMethods();
    List<FunctionDTO> functionDTOList = methodDTO.getFunctions();
    for (FunctionDTO functionDTO : functionDTOList) {
      if (functionId.equals(functionDTO.getId())) {
        functionDTO.setFormulas(formulas);
        break;
      }
    }
    if (!iMethodDao.setMethod(methodDTO)) {
      return ReturnUtils.failureMap("方法文件存储失败，请联系管理员！");
    }
    return ReturnUtils.successMap("新增方法公式成功！");
  }

  /**
   * 根据药品id和方法id获取公式信息
   *
   * @param functionId 公式id
   * @return 公式信息
   */
  @Override
  public List<FormulaDTO> getFormulasFunctionId(Long functionId) {
    MethodDTO methodDTO = iMethodDao.getMethods();
    ProgramDTO programDTO = IProgramDao.getPrograms();
    Map<Long, FormulaDTO> formulaMap = new HashMap<>();
    List<FormulaDTO> formulaDTOList = programDTO.getFormulas();
    for (FormulaDTO formulaDTO : formulaDTOList) {
      formulaMap.put(formulaDTO.getId(), formulaDTO);
    }
    List<FunctionDTO> functionDTOList = methodDTO.getFunctions();
    for (FunctionDTO functionDTO : functionDTOList) {
      if (functionId.equals(functionDTO.getId())) {
        List<IdDTO> formulaIds = functionDTO.getFormulas();
        List<FormulaDTO> formulas = new ArrayList<>();
        for (IdDTO idDTO : formulaIds) {
          formulas.add(formulaMap.get(idDTO.getId()));
        }
        return formulas;
      }
    }
    return null;
  }

  /**
   * 新增方法参数 todo 哪天脑子清楚了优化
   *
   * @param medicineId 药品id
   * @param secondId 小类id
   * @param functionId 方法id
   * @param tableId 列表id
   * @param columnDTOList 行数据
   * @return 处理结果
   */
  @Override
  public Map<String, Object> addParamValue(
      Long medicineId,
      Long secondId,
      Long functionId,
      Long tableId,
      List<ColumnDTO> columnDTOList) {

    SecondClassDTO secondClassDTO =
        iSecondClassDao.getSecondClassByMedicineSecondClassId(medicineId, secondId);
    ProductDTO productDTO = iProductDao.getProduct();

    // 初始化行信息
    RowDTO rowDTO = new RowDTO();
    rowDTO.setId(productDTO.getConfig().getNewRowId());
    rowDTO.setColumns(columnDTOList);

    // cnt: 0 paramValue 1 table
    //    int cnt = 0;
    boolean containParamValue = false;
    boolean containTable = false;

    List<ParamValueDTO> paramValueDTOList = secondClassDTO.getParamValues();
    if (paramValueDTOList == null) {
      paramValueDTOList = new ArrayList<>();
      secondClassDTO.setParamValues(paramValueDTOList);
    }
    for (ParamValueDTO paramValueDTO : paramValueDTOList) {
      if (functionId.equals(paramValueDTO.getFunctionId())) {
        containParamValue = true;
        List<TableDTO> tableDTOList = paramValueDTO.getTables();
        if (tableDTOList == null) {
          tableDTOList = new ArrayList<>();
          paramValueDTO.setTables(tableDTOList);
        }
        for (TableDTO tableDTO : tableDTOList) {
          if (tableId.equals(tableDTO.getId())) {
            containTable = true;
            tableDTO.getRows().add(rowDTO);
            break;
          }
        }
        if (!containTable) {
          List<RowDTO> rowDTOList = new ArrayList<>();
          rowDTOList.add(rowDTO);
          TableDTO tableDTO = new TableDTO();
          tableDTO.setId(tableId);
          tableDTO.setRows(rowDTOList);
          tableDTOList.add(tableDTO);
        }
        break;
      }
    }
    if (!containParamValue) {
      List<RowDTO> rowDTOList = new ArrayList<>();
      rowDTOList.add(rowDTO);
      TableDTO tableDTO = new TableDTO();
      tableDTO.setId(tableId);
      tableDTO.setRows(rowDTOList);
      List<TableDTO> tableDTOList = new ArrayList<>();
      tableDTOList.add(tableDTO);
      ParamValueDTO paramValueDTO = new ParamValueDTO();
      paramValueDTO.setFunctionId(functionId);
      paramValueDTO.setTables(tableDTOList);
      paramValueDTOList.add(paramValueDTO);
    }

    if (!iProductDao.setProduct(productDTO)) {
      return ReturnUtils.failureMap("产品文件存储失败，请联系管理员！");
    }
    if (!iSecondClassDao.setSecondClass(medicineId, secondClassDTO)) {
      return ReturnUtils.failureMap("小类文件存储失败，请联系系统管理员！");
    }
    return ReturnUtils.successMap("新增方法参数成功！");
  }

  /**
   * 修改方法参数
   *
   * @param medicineId 药品id
   * @param secondId 小类id
   * @param functionId 方法id
   * @param tableId 列表id
   * @param rowId 行id
   * @param columnDTOList 行数据
   * @return 处理结果
   */
  @Override
  public Map<String, Object> updateParamValue(
      Long medicineId,
      Long secondId,
      Long functionId,
      Long tableId,
      Long rowId,
      List<ColumnDTO> columnDTOList) {
    SecondClassDTO secondClassDTO =
        iSecondClassDao.getSecondClassByMedicineSecondClassId(medicineId, secondId);

    List<ParamValueDTO> paramValueDTOList = secondClassDTO.getParamValues();
    for (ParamValueDTO paramValueDTO : paramValueDTOList) {
      if (functionId.equals(paramValueDTO.getFunctionId())) {
        List<TableDTO> tableDTOList = paramValueDTO.getTables();
        for (TableDTO tableDTO : tableDTOList) {
          if (tableId.equals(tableDTO.getId())) {
            List<RowDTO> rowDTOList = tableDTO.getRows();
            for (RowDTO rowDTO : rowDTOList) {
              if (rowId.equals(rowDTO.getId())) {
                rowDTO.setColumns(columnDTOList);
                break;
              }
            }
            break;
          }
        }
        break;
      }
    }
    if (!iSecondClassDao.setSecondClass(medicineId, secondClassDTO)) {
      return ReturnUtils.failureMap("小类文件存储失败，请联系系统管理员！");
    }
    return ReturnUtils.successMap("修改方法参数成功！");
  }

  /**
   * 删除方法参数
   *
   * @param medicineId 药品id
   * @param secondId 小类id
   * @param functionId 方法id
   * @param tableId 列表id
   * @param rowId 行id
   * @return 处理结果
   */
  @Override
  public Map<String, Object> deleteParamValue(
      Long medicineId, Long secondId, Long functionId, Long tableId, Long rowId) {
    SecondClassDTO secondClassDTO =
        iSecondClassDao.getSecondClassByMedicineSecondClassId(medicineId, secondId);

    List<ParamValueDTO> paramValueDTOList = secondClassDTO.getParamValues();
    for (ParamValueDTO paramValueDTO : paramValueDTOList) {
      if (functionId.equals(paramValueDTO.getFunctionId())) {
        List<TableDTO> tableDTOList = paramValueDTO.getTables();
        for (TableDTO tableDTO : tableDTOList) {
          if (tableId.equals(tableDTO.getId())) {
            List<RowDTO> rowDTOList = tableDTO.getRows();
            for (RowDTO rowDTO : rowDTOList) {
              if (rowId.equals(rowDTO.getId())) {
                rowDTOList.remove(rowDTO);
                break;
              }
            }
            break;
          }
        }
        break;
      }
    }
    if (!iSecondClassDao.setSecondClass(medicineId, secondClassDTO)) {
      return ReturnUtils.failureMap("小类文件存储失败，请联系系统管理员！");
    }
    return ReturnUtils.successMap("删除方法参数成功！");
  }

  /**
   * 保存方法参数关联关系 todo 2333
   *
   * @param medicineId 药品id
   * @param secondId 小类id
   * @param functionId 方法id
   * @param tableId 列表id
   * @param code 编码
   * @param value 值
   * @return 处理结果
   */
  @Override
  public Map<String, Object> saveRelation(
      Long medicineId, Long secondId, Long functionId, Long tableId, String code, String value) {

    SecondClassDTO secondClassDTO =
        iSecondClassDao.getSecondClassByMedicineSecondClassId(medicineId, secondId);
    ProductDTO productDTO = iProductDao.getProduct();

    // cnt: 0 paramValue 1 table
    //    int cnt = 0;
    boolean containParamValue = false;
    boolean containTable = false;
    boolean containRelation = false;

    List<ParamValueDTO> paramValueDTOList = secondClassDTO.getParamValues();
    if (paramValueDTOList == null) {
      paramValueDTOList = new ArrayList<>();
      secondClassDTO.setParamValues(paramValueDTOList);
    }
    for (ParamValueDTO paramValueDTO : paramValueDTOList) {
      if (functionId.equals(paramValueDTO.getFunctionId())) {
        containParamValue = true;
        List<TableDTO> tableDTOList = paramValueDTO.getTables();
        if (tableDTOList == null) {
          tableDTOList = new ArrayList<>();
          paramValueDTO.setTables(tableDTOList);
        }
        for (TableDTO tableDTO : tableDTOList) {
          if (tableId.equals(tableDTO.getId())) {
            containTable = true;
            List<RelationDTO> relationDTOList = tableDTO.getRelations();
            if (relationDTOList == null) {
              relationDTOList = new ArrayList<>();
              tableDTO.setRelations(relationDTOList);
            }
            for (RelationDTO relationDTO : relationDTOList) {
              if (code.equals(relationDTO.getCode())) {
                containRelation = true;
                relationDTO.setValue(value);
                break;
              }
            }
            if (!containRelation) {
              RelationDTO relationDTO = new RelationDTO();
              relationDTO.setCode(code);
              relationDTO.setValue(value);
              relationDTOList.add(relationDTO);
            }
            break;
          }
        }
        if (!containTable) {
          RelationDTO relationDTO = new RelationDTO();
          relationDTO.setCode(code);
          relationDTO.setValue(value);
          List<RelationDTO> relationDTOList = new ArrayList<>();
          relationDTOList.add(relationDTO);
          TableDTO tableDTO = new TableDTO();
          tableDTO.setId(tableId);
          tableDTO.setRelations(relationDTOList);
          tableDTOList.add(tableDTO);
        }
        break;
      }
    }
    if (!containParamValue) {
      RelationDTO relationDTO = new RelationDTO();
      relationDTO.setCode(code);
      relationDTO.setValue(value);
      List<RelationDTO> relationDTOList = new ArrayList<>();
      relationDTOList.add(relationDTO);
      TableDTO tableDTO = new TableDTO();
      tableDTO.setId(tableId);
      tableDTO.setRelations(relationDTOList);
      List<TableDTO> tableDTOList = new ArrayList<>();
      tableDTOList.add(tableDTO);
      ParamValueDTO paramValueDTO = new ParamValueDTO();
      paramValueDTO.setFunctionId(functionId);
      paramValueDTO.setTables(tableDTOList);
      paramValueDTOList.add(paramValueDTO);
    }

    if (!iProductDao.setProduct(productDTO)) {
      return ReturnUtils.failureMap("产品文件存储失败，请联系管理员！");
    }
    if (!iSecondClassDao.setSecondClass(medicineId, secondClassDTO)) {
      return ReturnUtils.failureMap("小类文件存储失败，请联系系统管理员！");
    }
    return ReturnUtils.successMap("保存方法参数关联关系！");
  }
}
