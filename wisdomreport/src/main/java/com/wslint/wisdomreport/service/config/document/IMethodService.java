package com.wslint.wisdomreport.service.config.document;

import com.wslint.wisdomreport.domain.dto.xml.component.ColumnDTO;
import com.wslint.wisdomreport.domain.dto.xml.method.MethodDTO;
import com.wslint.wisdomreport.domain.dto.xml.program.FormulaDTO;
import com.wslint.wisdomreport.domain.dto.xml.relation.IdDTO;
import java.util.List;
import java.util.Map;

/**
 * 方法对象服务
 *
 * @author yuxr
 * @since 2018/10/31 10:21
 */
public interface IMethodService {
  /**
   * 根据药品id获取方法信息
   *
   * @return 方法对象
   */
  MethodDTO getMethodByMedicineId();

  /**
   * 获取所有的公式
   *
   * @return 所有的公式信息
   */
  List<FormulaDTO> getFormulas();

  /**
   * 保存方法对应公式信息
   *
   * @param functionId 方法id
   * @param formulas 公式信息
   * @return 处理结果
   */
  Map<String, Object> formulasSave(Long functionId, List<IdDTO> formulas);

  /**
   * 根据药品id和方法id获取公式信息
   *
   * @param functionId 公式id
   * @return 公式信息
   */
  List<FormulaDTO> getFormulasFunctionId(Long functionId);

  /**
   * 新增方法参数
   *
   * @param medicineId 药品id
   * @param secondId 小类id
   * @param functionId 方法id
   * @param tableId 列表id
   * @param columnDTOList 行数据
   * @return 处理结果
   */
  Map<String, Object> addParamValue(
      Long medicineId, Long secondId, Long functionId, Long tableId, List<ColumnDTO> columnDTOList);

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
  Map<String, Object> updateParamValue(
      Long medicineId,
      Long secondId,
      Long functionId,
      Long tableId,
      Long rowId,
      List<ColumnDTO> columnDTOList);

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
  Map<String, Object> deleteParamValue(
      Long medicineId, Long secondId, Long functionId, Long tableId, Long rowId);

  /**
   * 保存方法参数关联关系
   *
   * @param medicineId 药品id
   * @param secondId 小类id
   * @param functionId 方法id
   * @param tableId 列表id
   * @param code 编码
   * @param value 值
   * @return 处理结果
   */
  Map<String, Object> saveRelation(
      Long medicineId, Long secondId, Long functionId, Long tableId, String code, String value);
}
