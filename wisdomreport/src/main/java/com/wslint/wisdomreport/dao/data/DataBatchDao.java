package com.wslint.wisdomreport.dao.data;

import com.wslint.wisdomreport.domain.dto.data.batch.DataBatchDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowBatchTaskDTO;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

/**
 * 数据处理接口
 *
 * @author yuxr
 * @since 2018/11/9 12:27
 */
public interface DataBatchDao {

  /**
   * 插入批次数据
   *
   * @param dataBatchDTO 批次数据
   * @return 是否插入成功
   */
  @Insert(
      "insert into data_batch "
          + "(id, batch_no, medicine_id, status, next_operator, gmt_create, gmt_modified, create_user, modify_user) values "
          + "(#{id}, #{batchNo}, #{medicineId}, #{status}, #{nextOperator}, #{gmtCreate}, #{gmtModified}, #{createUser}, #{modifyUser})")
  boolean insertDataBatch(DataBatchDTO dataBatchDTO);

  /**
   * 数据记录置状态
   *
   * @param dataBatchDTO 待修改数据
   * @return 是否修改成功
   */
  @Update(
      "update data_batch set status = #{status}, gmt_modified = #{gmtModified}, modify_user = #{modifyUser}, next_operator = #{nextOperator} "
          + " where id = #{id}")
  boolean updateBatchDataStatus(DataBatchDTO dataBatchDTO);

  /**
   * 当前药品批次是否存在
   *
   * @param medicineId 药品id
   * @param batchNo 批次号
   * @return 是否存在
   */
  @Select(
      "select if(count(1) = 0 ,0, 1) from data_batch where medicine_id = #{medicineId} and batch_no = #{batchNo}")
  boolean isBatchDataExist(@Param("medicineId") Long medicineId, @Param("batchNo") String batchNo);

  /**
   * 检查状态是否满足参数条件
   *
   * @param batchId 批次数据id
   * @param status 批次数据状态
   * @param currentUserId 当前用户id
   * @return 是否满足条件
   */
  @Select(
      "select if(count(1) = 0 ,0, 1) from data_batch where id = #{batchId} and status = #{status} and (next_operator is null || next_operator = #{currentUserId})")
  boolean checkWorkflowRight(
      @Param("batchId") Long batchId,
      @Param("status") int status,
      @Param("currentUserId") Long currentUserId);

  /**
   * 根据药品id和状态获取批次数据
   *
   * @param medicineId 药品id
   * @param status 状态码
   * @param offset 偏移数
   * @param limit 限制数
   * @return 批次数据
   */
  @Select(
      "<script>"
          + "select * from data_batch where medicine_id = #{medicineId}"
          + "<when test='status != -1'> and status = #{status} </when>"
          + " order by gmt_modified desc "
          + " limit #{offset},#{limit} "
          + "</script>")
  List<DataBatchDTO> getDataBatchByMedicineIdAndStatus(
      @Param("medicineId") Long medicineId,
      @Param("status") Integer status,
      @Param("offset") Integer offset,
      @Param("limit") Integer limit);

  /**
   * 根据待操作人查询批次数据
   *
   * @param nextOperator 待操作人id
   * @return 批次数据
   */
  @Select(
      "select * from data_batch where next_operator = #{nextOperator} order by gmt_modified desc")
  List<DataBatchDTO> getDataBatchByNextOperator(Long nextOperator);

  /**
   * 根据id获取批次数据
   *
   * @param id 批次id
   * @return 批次数据
   */
  @Select("select * from data_batch where id = #{id}")
  DataBatchDTO getDataBatchById(Long id);

  /**
   * 根据药品id和批次号获取批次id
   *
   * @param medicineId 药品id
   * @param batchNo 批次号
   * @return 批次id
   */
  @Select(
      "select id from data_batch where medicine_id = #{medicineId} and batch_no = #{batchNo} and valid = 1")
  Long getIdByMedicineIdAndBatchNo(
      @Param("medicineId") Long medicineId, @Param("batchNo") String batchNo);

  /**
   * 根据批次id设定数据无效
   *
   * @param batchId 批次id
   * @return 是否成功
   */
  @Update("update data_batch set valid = 0 where id = #{batchId}")
  boolean setIsValidFalseByBatchId(Long batchId);

  // todo 重构分界线====================================================

  /**
   * 根据批次ids获取批次信息
   *
   * @param workflowBatchTaskDTOList 待走工作流的批次信息
   * @return 批次数据信息
   */
  @Select(
      "<script>"
          + "select * from data_batch where id in ("
          + "<foreach collection='list' item='item' index='index' separator=',' >"
          + "#{item.batchId}"
          + "</foreach> )"
          + "</script>")
  List<DataBatchDTO> getBatchsByBatchIds(List<WorkflowBatchTaskDTO> workflowBatchTaskDTOList);

  /**
   * 批量更新批次数据
   *
   * @param dataBatchDTOList 待更新数据
   * @return 是否更新成功
   */
  @Update({
    "<script>"
        + "<foreach collection='list' item='item' index='index' open='' close='' separator=';'>"
        + " update data_batch set "
        + " status = #{item.status}, next_operator = #{item.nextOperator}, "
        + " modify_user = #{item.modifyUser}, gmt_modified = #{item.gmtModified} "
        + " where id = #{item.id} "
        + "</foreach>"
        + "</script>"
  })
  boolean batchUpdateBatchData(List<DataBatchDTO> dataBatchDTOList);

  /**
   * 新增批次数据
   *
   * @param dataBatchDTOList 新增数据
   * @return 是否新增成功
   */
  @Insert(
      "<script>"
          + " insert into data_batch "
          + " (id, batch_no, medicine_id, status, next_operator, hold1, hold2, hold3, gmt_create, gmt_modified, create_user, modify_user) values "
          + "<foreach collection='list' item='item' index='index' separator=',' >"
          + " (#{item.id}, #{item.batchNo}, #{item.medicineId}, #{item.status}, #{item.nextOperator}, #{item.hold1}, #{item.hold2}, #{item.hold3},"
          + " #{item.gmtCreate}, #{item.gmtModified}, #{item.createUser}, #{item.modifyUser})"
          + "</foreach>"
          + "</script>")
  boolean insertDataBatchs(List<DataBatchDTO> dataBatchDTOList);

  /**
   * 批次数据是否存在
   *
   * @param medicineIdBatchNosMap 药品批次号对象
   * @return 是否存在数据
   */
  @SelectProvider(type = DataSqlProvider.class, method = "getHaveDataByMedicineIdBatchNosMapSql")
  boolean haveDataByMedicineIdBatchNosMap(Map<Long, List<String>> medicineIdBatchNosMap);
}
