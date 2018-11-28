package com.wslint.wisdomreport.service;

import com.wslint.wisdomreport.domain.dto.data.WrReport;
import com.wslint.wisdomreport.domain.dto.data.WrReportData;
import java.util.List;

/**
 * 数据处理对外提供的接口
 *
 * @author yuxr
 * @since 2018/8/13 16:12
 */
public interface IDataService {

  /**
   * 保存主表和关联从表信息
   *
   * @param wrReport 传入信息
   * @throws Exception 异常信息
   */
  void saveReport(WrReport wrReport) throws Exception;
  /**
   * 创建批次服务
   *
   * @param param 批次信息
   * @throws Exception 异常
   */
  void createBatch(List<WrReport> param) throws Exception;

  /**
   * 查询某个小类下的所有数据
   *
   * @param param 查询条件
   * @return 查询出的数据
   */
  List<WrReportData> getOneClassData(WrReport param);

  /**
   * 查询某个药品某个批次下的所有大类所有小类数据
   *
   * @param param 查询条件
   * @return 查询出的数据
   */
  List<WrReport> getAllClassData(WrReport param);

  /**
   * 根据用户id查询该用户当前的复核任务
   *
   * @param userId 用户id
   * @return 复核任务列表
   */
  List<WrReport> getReviewList(long userId);

  /**
   * 根据用户id查询该用户当前的修改复核任务
   *
   * @param userId 用户id
   * @return 修改复核任务列表
   */
  List<WrReport> getModifyReviewList(long userId);

  /**
   * 根据数据id获取历史数据
   *
   * @param reportId 模板id
   * @param orderId 空格id
   * @return 历史数据
   */
  List<WrReportData> getDataByReportIdAndOrderId(Long reportId, Long orderId);

  /**
   * 根据药品id 和 批次号获取模板信息
   *
   * @param medicine 药品id
   * @param batchNo 批次号
   * @return 模板信息
   */
  WrReport getReportIdByMedicineIdAndBatchNo(Long medicine, Long batchNo);
}
