package com.wslint.wisdomreport.dao.data;

import com.wslint.wisdomreport.domain.dto.data.record.DataRecordDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowBatchTaskDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowRecordTaskDTO;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

/**
 * 记录数据服务
 *
 * @author yuxr
 * @since 2018/11/13 16:14
 */
public interface DataRecordDao {

  /**
   * 根据recordId获取业务数据
   *
   * @param workflowRecordTaskDTOList 包含orderId的对象
   * @return 业务数据
   */
  @Select(
      "<script>"
          + "select * from data_record where id in ("
          + "<foreach collection='list' item='item' index='index' separator=',' >"
          + "#{item.recordId}"
          + "</foreach> )"
          + "</script>")
  List<DataRecordDTO> getRecordsByRecordIds(List<WorkflowRecordTaskDTO> workflowRecordTaskDTOList);

  /**
   * 批量更新业务数据
   *
   * @param dataRecordDTOList 待更新业务数据
   * @return 是否更新成功
   */
  @Update({
    "<script>"
        + "<foreach collection='list' item='item' index='index' open='' close='' separator=';'>"
        + " update data_record set "
        + " data = #{item.data}, remark = #{item.remark}, remark_time = #{item.remarkTime}, remarker = #{item.remarker}, "
        + " status = #{item.status}, next_operator = #{item.nextOperator}, "
        + " modify_user = #{item.modifyUser}, gmt_modified = #{item.gmtModified} "
        + " where id = #{item.id} "
        + "</foreach>"
        + "</script>"
  })
  boolean batchUpdateRecordData(List<DataRecordDTO> dataRecordDTOList);

  /**
   * 根据类型id获取记录数据
   *
   * @param classId 类型id
   * @return 记录数据
   */
  @Select("select * from data_record where class_id = #{classId}")
  List<DataRecordDTO> getRecordsByClassId(Long classId);

  /**
   * 根据记录id获取记录数据
   *
   * @param recordId 记录id
   * @return 记录数据
   */
  @Select("select * from data_record where id = #{recordId}")
  DataRecordDTO getRecordByRecordId(Long recordId);

  /**
   * 获取小类对应的记录数据
   *
   * @param medicineId 药品id
   * @param batchNo 批次号
   * @param firstClassId 大类id
   * @param secondClassId 小类id
   * @return 记录数据
   */
  @Select(
      "select * from vw_data_record_detail "
          + " where medicine_id = #{medicineId} and batch_no = #{batchNo} and first_class_id = #{firstClassId} and second_class_id = #{secondClassId} "
          + " order by gmt_modified desc")
  List<DataRecordDTO> getRecordsByClass(
      @Param("medicineId") Long medicineId,
      @Param("batchNo") String batchNo,
      @Param("firstClassId") Long firstClassId,
      @Param("secondClassId") Long secondClassId);

  /**
   * 获取批次对应的记录数据
   *
   * @param medicineId 药品id
   * @param batchNo 批次号
   * @return 记录数据
   */
  @Select(
      "select * from vw_data_record_detail "
          + " where medicine_id = #{medicineId} and batch_no = #{batchNo} "
          + " order by gmt_modified desc")
  List<DataRecordDTO> getRecordsByBatch(
      @Param("medicineId") Long medicineId, @Param("batchNo") String batchNo);

  /**
   * 根据状态获取记录数据
   *
   * @param status 状态
   * @param userId 用户id
   * @return 记录数据
   */
  @Select(
      "select * from vw_data_record_detail "
          + " where status = #{status} and next_operator = #{userId} "
          + " order by gmt_modified desc")
  List<DataRecordDTO> getRecordsByStatus(
      @Param("status") Integer status, @Param("userId") Long userId);

  /**
   * 批量新增记录数据
   *
   * @param dataRecordDTOList 待新增数据
   * @return 是否新增成功
   */
  @Insert(
      "<script>"
          + "insert into data_record "
          + "(id, order_id, class_id, batch_id, data, img_url, status, next_operator, gmt_create, gmt_modified, create_user, modify_user) values "
          + "<foreach collection='list' item='item' index='index' separator=',' >"
          + " (#{item.id}, #{item.orderId}, #{item.classId}, #{item.batchId}, #{item.data}, #{item.imgUrl}, #{item.status}, #{item.nextOperator},"
          + " #{item.gmtCreate}, #{item.gmtModified}, #{item.createUser}, #{item.modifyUser})"
          + "</foreach>"
          + "</script>")
  boolean insertDataRecords(List<DataRecordDTO> dataRecordDTOList);

  /**
   * 检查对应空格id是否已经有数据
   *
   * @param classOrderIdsMap 空格id
   * @return 是否有数据
   */
  @SelectProvider(type = DataSqlProvider.class, method = "getHaveDataByClassOrderIdsMapSql")
  boolean haveDataByClassOrderIdsMap(Map<Long, List<Long>> classOrderIdsMap);

  /**
   * 根据批次id 查询记录数据
   *
   * @param batchId 批次id
   * @return 记录数据
   */
  @Select("select * from data_record where batch_id = #{batchId}")
  List<DataRecordDTO> getRecordsByBatchId(Long batchId);

  /**
   * 根据id集合获取所有的记录数据
   *
   * @param dataRecordDTOS id集合
   * @return 记录数据
   */
  @Select(
      "<script>"
          + "select * from vw_data_record_detail where id in "
          + "<foreach collection='list' item='item' index='index' open='(' separator=',' close=')'>"
          + " #{item.id}"
          + "</foreach>"
          + " order by gmt_modified desc"
          + "</script>")
  List<DataRecordDTO> getDataRecordsByIds(List<DataRecordDTO> dataRecordDTOS);

  /**
   * 通过流程信息和批次id获取发起工作流信息
   *
   * @param batchTaskDTOS 批次流程信息
   * @return 发起工作流信息
   */
  @Select(
      "<script>"
          + "<foreach collection='list' item='item' index='index' open='' close='' separator='union all'>"
          + " (select id as record_id, #{item.operation} as operation, #{item.reason} as reason from data_record "
          + " where batch_id = #{item.batchId}) "
          + "</foreach>"
          + "</script>")
  List<WorkflowRecordTaskDTO> getRecordTasksByBatchTasks(List<WorkflowBatchTaskDTO> batchTaskDTOS);

  /**
   * 根据classId查询记录数据
   *
   * @param copyClassIds 待复制classId
   * @return 记录数据
   */
  @Select(
      "<script>"
          + "select * from data_record where class_id in "
          + "<foreach collection='list' item='item' index='index' open='(' separator=',' close=')'>"
          + " #{item}"
          + "</foreach>"
          + "</script>")
  List<DataRecordDTO> getCopyRecordDatasByClassIds(List<Long> copyClassIds);

  /**
   * 根据类型和空格id更新备注
   *
   * @param dataRecordDTOList 数据对象
   * @return 是否更新成功
   */
  @Update(
      "<script>"
          + "<foreach collection='list' item='item' index='index' open='' separator=';' close=''>"
          + "update data_record set remark = #{item.remark}, remark_time = #{item.remarkTime}, remarker = #{item.remarker} "
          + " where class_id = #{item.classId} and order_id = #{item.orderId} "
          + "</foreach>"
          + "</script>")
  boolean updateRemarksByClassOrderIds(List<DataRecordDTO> dataRecordDTOList);
}
