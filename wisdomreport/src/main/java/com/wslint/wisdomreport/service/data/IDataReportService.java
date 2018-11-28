package com.wslint.wisdomreport.service.data;

import com.wslint.wisdomreport.domain.dto.data.report.DataReportDTO;
import java.util.List;
import java.util.Map;

/**
 * 报告数据服务接口
 *
 * @author yuxr
 * @since 2018/11/22 11:03
 */
public interface IDataReportService {

  /**
   * 获取批次对应的报告数据
   *
   * @param medicineId 药品id
   * @param batchNo 批次号
   * @return 报告数据
   */
  List<DataReportDTO> getReportsByBatch(Long medicineId, String batchNo);

  /**
   * 保存批次对应的报告数据
   *
   * @param dataReportDTOList 待保存批次数据
   * @return 处理结果
   */
  Map<String, Object> save(List<DataReportDTO> dataReportDTOList) throws Exception;

  /**
   * 根据状态获取报告数据
   *
   * @param status 状态
   * @return 报告数据
   */
  List<DataReportDTO> getReportsByStatus(Integer status);
}
