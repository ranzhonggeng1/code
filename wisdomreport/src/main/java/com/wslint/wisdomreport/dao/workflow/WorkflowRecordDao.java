package com.wslint.wisdomreport.dao.workflow;

import com.wslint.wisdomreport.domain.dto.workflow.WorkflowRecordTaskDTO;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * 记录工作流数据处理服务
 *
 * @author yuxr
 * @since 2018/11/13 15:43
 */
public interface WorkflowRecordDao {

  /**
   * 批量插入工作流数据
   *
   * @param workflowRecordTaskDTOList 工作流数据list
   * @return 是否成功处理
   */
  @Insert(
      "<script>"
          + "insert into wf_record_current_task  "
          + "(id, record_id, operation, operator, next_operator, reason, old_data, old_remark, old_remark_time, old_remarker, "
          + " status, next_status, hold1, hold2, hold3, gmt_create, gmt_modified) values "
          + "<foreach collection='list' item='item' index='index' separator=',' >"
          + "(#{item.id}, #{item.recordId}, #{item.operation}, #{item.operator}, #{item.nextOperator}, #{item.reason}, "
          + " #{item.oldData}, #{item.oldRemark}, #{item.oldRemarkTime}, #{item.oldRemarker}, "
          + " #{item.status}, #{item.nextStatus}, #{item.hold1}, #{item.hold2}, #{item.hold3}, #{item.gmtCreate}, #{item.gmtModified})"
          + "</foreach>"
          + "</script>")
  boolean batchInsertWorkflowTask(List<WorkflowRecordTaskDTO> workflowRecordTaskDTOList);

  /**
   * 根据 recordId 按照操作顺序查询工作流数据
   *
   * @param recordId recordId
   * @return 工作流数据
   */
  @Select(
      "select * from vw_wf_record_current_task where record_id = #{recordId} order by gmt_modified desc")
  List<WorkflowRecordTaskDTO> getWorkflowCurrentTasksByRecordId(Long recordId);

  /**
   * 根据 recordId 按照操作顺序查询工作流数据
   *
   * @param recordId recordId
   * @return 工作流数据
   */
  @Select(
      "select * from vw_wf_record_complete_task where record_id = #{recordId} order by gmt_modified desc")
  List<WorkflowRecordTaskDTO> getWorkflowCompleteTasksByRecordId(Long recordId);

  /**
   * 备份记录工作流数据
   *
   * @param recordIds 记录id
   * @return 是否备份成功
   */
  @Insert(
      "<script>"
          + "insert into wf_record_complete_task select * from wf_record_current_task where record_id in "
          + "<foreach collection='list' item='item' index='index' open='(' separator=',' close=')' >"
          + " #{item}"
          + "</foreach> "
          + "</script>")
  boolean insertCompleteFromCurrentByRecordIds(List<Long> recordIds);

  /**
   * 批量删除工作流数据
   *
   * @param recordIds 记录id
   * @return 是否删除成功
   */
  @Delete(
      "<script>"
          + "delete from wf_record_current_task where record_id in "
          + "<foreach collection='list' item='item' index='index' open='(' separator=',' close=')' >"
          + " #{item}"
          + "</foreach>"
          + "</script>")
  boolean deleteCurrentByRecordIds(List<Long> recordIds);

  /**
   * 根据记录ids查询记录对应的工作流数据
   *
   * @param copyRecordIds 待复制记录数据id
   * @return 查询结果
   */
  @Select(
      "<script>"
          + "select * from wf_record_current_task where record_id in "
          + "<foreach collection='list' item='item' index='index' open='(' separator=',' close=')' >"
          + " #{item}"
          + "</foreach> "
          + "</script>")
  List<WorkflowRecordTaskDTO> getCopyRecordTasksByRecordIds(List<Long> copyRecordIds);
}
