package com.wslint.wisdomreport.service.data;

import com.wslint.wisdomreport.domain.dto.data.batch.DataBatchDTO;
import com.wslint.wisdomreport.domain.dto.data.clazz.DataClassDTO;
import java.util.List;
import java.util.Map;

/**
 * 批次数据服务
 *
 * @author yuxr
 * @since 2018/11/9 11:54
 */
public interface IDataBatchService {

  /**
   * 根据药品id和状态获取批次数据
   *
   * @param medicineId 药品id
   * @param status 状态码
   * @param offset 偏移数
   * @param limit 限制数
   * @return 批次数据
   */
  List<DataBatchDTO> getDataBatchByMedicineIdAndStatus(
      Long medicineId, Integer status, Integer offset, Integer limit);

  /**
   * 获取当前操作人的待办列表
   *
   * @return 批次数据
   */
  List<DataBatchDTO> getDataBatchByNextOperator();

  /**
   * 创建批次数据
   *
   * @param dataClassDTOList 批次数据对象
   * @return 处理结果
   */
  Map<String, Object> save(List<DataClassDTO> dataClassDTOList) throws Exception;

  Long fkGetClassIdByRubbish(Long medicineId, String batchNo, Long firstClassId, Long secondClassId);

  Long fkGetBatchIdByRubbish(Long medicineId, String batchNo);

  Long fkGetClassIdByRubbish(Long batchId, Long firstClassId, Long secondClassId);
}
