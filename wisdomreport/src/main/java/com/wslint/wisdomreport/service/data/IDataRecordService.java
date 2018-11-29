package com.wslint.wisdomreport.service.data;

import com.wslint.wisdomreport.domain.dto.data.record.DataRecordDTO;
import java.util.List;
import java.util.Map;

/**
 * 记录数据服务
 *
 * @author yuxr
 * @since 2018/11/16 11:28
 */
public interface IDataRecordService {

  /**
   * 获取小类对应的记录数据
   *
   * @param medicineId 药品id
   * @param batchNo 批次号
   * @param firstClassId 大类id
   * @param secondClassId 小类id
   * @return 记录数据
   */
  List<DataRecordDTO> getRecordsByClass(
      Long medicineId, String batchNo, Long firstClassId, Long secondClassId);

  /**
   * 获取批次对应的记录数据
   *
   * @param medicineId 药品id
   * @param batchNo 批次号
   * @return 记录数据
   */
  List<DataRecordDTO> getRecordsByBatch(Long medicineId, String batchNo);

  /**
   * 保存批次对应的记录数据
   *
   * @param dataRecordDTOList 待保存批次数据
   * @return 处理结果
   */
  Map<String, Object> save(List<DataRecordDTO> dataRecordDTOList) throws Exception;

  /**
   * 保存记录备注
   * @param dataRecordDTOList 记录对象
   * @return 处理结果
   */
  Map<String, Object> saveRemark(List<DataRecordDTO> dataRecordDTOList) throws Exception;

  /**
   * 根据状态获取记录数据
   *
   * @param status 状态
   * @return 记录数据
   */
  List<DataRecordDTO> getRecordsByStatus(Integer status);
}
