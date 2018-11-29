package com.wslint.wisdomreport.dao.data;

import com.wslint.wisdomreport.domain.dto.data.report.DataReportDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowBatchTaskDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowReportTaskDTO;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

/**
 * 报告数据服务
 *
 * @author yuxr
 * @since 2018/11/13 16:14
 */
public interface DataReportDao {

  /**
   * 根据reportId获取业务数据
   *
   * @param workflowReportTaskDTOList 包含orderId的对象
   * @return 业务数据
   */
  @Select(
      "<script>"
          + "select * from data_report where id in ("
          + "<foreach collection='list' item='item' index='index' separator=',' >"
          + "#{item.reportId}"
          + "</foreach> )"
          + "</script>")
  List<DataReportDTO> getReportsByReportIds(List<WorkflowReportTaskDTO> workflowReportTaskDTOList);

  /**
   * 批量更新业务数据
   *
   * @param dataReportDTOList 待更新业务数据
   * @return 是否更新成功
   */
  @Update({
    "<script>"
        + "<foreach collection='list' item='item' index='index' open='' close='' separator=';'>"
        + " update data_report set "
        + " data = #{item.data}, remark = #{item.remark}, remark_time = #{item.remarkTime}, remarker = #{item.remarker}, "
        + " status = #{item.status}, next_operator = #{item.nextOperator}, "
        + " modify_user = #{item.modifyUser}, gmt_modified = #{item.gmtModified} "
        + " where id = #{item.id} "
        + "</foreach>"
        + "</script>"
  })
  boolean batchUpdateReportData(List<DataReportDTO> dataReportDTOList);

  /**
   * 根据报告id获取报告数据
   *
   * @param reportId 报告id
   * @return 报告数据
   */
  @Select("select * from data_report where id = #{reportId}")
  DataReportDTO getReportByReportId(Long reportId);

  /**
   * 获取批次对应的报告数据
   *
   * @param medicineId 药品id
   * @param batchNo 批次号
   * @return 报告数据
   */
  @Select(
      "select * from vw_data_report_detail "
          + " where medicine_id = #{medicineId} and batch_no = #{batchNo} "
          + " order by gmt_modified desc")
  List<DataReportDTO> getReportsByBatch(
      @Param("medicineId") Long medicineId, @Param("batchNo") String batchNo);

  /**
   * 根据状态获取报告数据
   *
   * @param status 状态
   * @param userId 用户id
   * @return 报告数据
   */
  @Select(
      "select * from vw_data_report_detail "
          + " where status = #{status} and next_operator = #{userId} "
          + " order by gmt_modified desc")
  List<DataReportDTO> getReportsByStatus(
      @Param("status") Integer status, @Param("userId") Long userId);

  /**
   * 批量新增报告数据
   *
   * @param dataReportDTOList 待新增数据
   * @return 是否新增成功
   */
  @Insert(
      "<script>"
          + "insert into data_report "
          + "(id, order_id, batch_id, data, img_url, status, next_operator, gmt_create, gmt_modified, create_user, modify_user) values "
          + "<foreach collection='list' item='item' index='index' separator=',' >"
          + " (#{item.id}, #{item.orderId}, #{item.batchId}, #{item.data}, #{item.imgUrl}, #{item.status}, #{item.nextOperator},"
          + " #{item.gmtCreate}, #{item.gmtModified}, #{item.createUser}, #{item.modifyUser})"
          + "</foreach>"
          + "</script>")
  boolean insertDataReports(List<DataReportDTO> dataReportDTOList);

  /**
   * 检查对应空格id是否已经有数据
   *
   * @param classOrderIdsMap 空格id
   * @return 是否有数据
   */
  @SelectProvider(type = DataSqlProvider.class, method = "getHaveDataByClassOrderIdsMapSql")
  boolean haveDataByBatchOrderIdsMap(Map<Long, List<Long>> classOrderIdsMap);

  /**
   * 根据批次id 查询报告数据
   *
   * @param batchId 批次id
   * @return 报告数据
   */
  @Select("select * from data_report where batch_id = #{batchId}")
  List<DataReportDTO> getReportsByBatchId(Long batchId);

  /**
   * 根据id集合获取所有的报告数据
   *
   * @param dataReportDTOS id集合
   * @return 报告数据
   */
  @Select(
      "<script>"
          + "select * from vw_data_report_detail where id in "
          + "<foreach collection='list' item='item' index='index' open='(' separator=',' close=')'>"
          + " #{item.id}"
          + "</foreach>"
          + " order by gmt_modified desc"
          + "</script>")
  List<DataReportDTO> getDataReportsByIds(List<DataReportDTO> dataReportDTOS);

  /**
   * 通过流程信息和批次id获取发起工作流信息
   *
   * @param batchTaskDTOS 批次流程信息
   * @return 发起工作流信息
   */
  @Select(
      "<script>"
          + "<foreach collection='list' item='item' index='index' open='' close='' separator='union all'>"
          + " (select id as report_id, #{item.operation} as operation, #{item.reason} as reason from data_report "
          + " where batch_id = #{item.batchId}) "
          + "</foreach>"
          + "</script>")
  List<WorkflowReportTaskDTO> getReportTasksByBatchTasks(List<WorkflowBatchTaskDTO> batchTaskDTOS);

  /**
   * 保存备注信息
   *
   * @param dataReportDTOList 保存备注信息
   * @return 是否保存成功
   */
  @Update(
      "<script>"
          + "<foreach collection='list' item='item' index='index' open='' separator=';' close='' >"
          + "update data_report set remark = #{item.remark}, remark_time = #{item.remarkTime}, remarker = #{item.remarker} "
          + " where batch_id = #{item.batchId} and order_id = #{item.orderId}"
          + "</foreach>"
          + "</script>")
  boolean updateRemarksByClassOrderIds(List<DataReportDTO> dataReportDTOList);
}
