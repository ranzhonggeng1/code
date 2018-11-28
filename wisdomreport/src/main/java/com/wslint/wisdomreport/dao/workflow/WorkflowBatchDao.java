package com.wslint.wisdomreport.dao.workflow;

import com.wslint.wisdomreport.domain.dto.workflow.WorkflowBatchTaskDTO;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * 工作流数据接口
 *
 * @author yuxr
 * @since 2018/11/9 12:26
 */
public interface WorkflowBatchDao {

  /**
   * 插入一条current工作流数据
   *
   * @param workflowBatchTaskDTO 工作流数据
   * @return 是否成功
   */
  @Insert(
      "insert into wf_batch_current_task "
          + "(id, batch_id, operation, operator, next_operator,  reason, status, next_status,  hold1, hold2, hold3, gmt_create, gmt_modified) values "
          + "(#{id}, #{batchId}, #{operation}, #{operator}, #{nextOperator}, #{reason}, #{status}, #{nextStatus}, #{hold1}, #{hold2}, #{hold3}, #{gmtCreate}, #{gmtModified} )")
  boolean insertCurrent(WorkflowBatchTaskDTO workflowBatchTaskDTO);

  /**
   * 插入complete数据
   *
   * @param batchId 批次id
   * @return 是否成功
   */
  @Insert(
      "insert into wf_batch_complete_task select * from wf_batch_current_task where batch_id = #{batchId}")
  boolean insertCompleteFromCurrentByBatchId(Long batchId);

  /**
   * 删除已备份数据
   *
   * @param batchId 批次id
   * @return 是否成功
   */
  @Delete("delete from wf_batch_current_task where batch_id = #{batchId}")
  boolean deleteCurrentByBatchId(Long batchId);

  /**
   * 根据批次id获取历史工作流数据
   *
   * @param batchId 批次id
   * @return 工作流数据
   */
  @Select(
      "select * from vw_wf_batch_current_task where batch_id = #{batchId} order by gmt_modified desc")
  List<WorkflowBatchTaskDTO> getWfCurrentTasksByBatchId(Long batchId);

  /**
   * 根据批次id获取历史工作流数据
   *
   * @param batchId 批次id
   * @return 工作流数据
   */
  @Select(
      "select * from vw_wf_batch_complete_task where batch_id = #{batchId} order by gmt_modified desc")
  List<WorkflowBatchTaskDTO> getWfCompleteTasksByBatchId(Long batchId);

  // todo 重构分界线 ======================================
  /**
   * 批量插入工作流信息
   *
   * @param workflowBatchTaskDTOList 工作流数据
   * @return 是否插入成功
   */
  @Insert(
      "<script>"
          + "insert into wf_batch_current_task  "
          + "(id, batch_id, operation, operator, next_operator, reason, status, next_status, hold1, hold2, hold3, gmt_create, gmt_modified) values "
          + "<foreach collection='list' item='item' index='index' separator=',' >"
          + "(#{item.id}, #{item.batchId}, #{item.operation}, #{item.operator}, #{item.nextOperator}, #{item.reason}, "
          + " #{item.status}, #{item.nextStatus}, #{item.hold1}, #{item.hold2}, #{item.hold3}, #{item.gmtCreate}, #{item.gmtModified})"
          + "</foreach>"
          + "</script>")
  boolean batchInsertWorkflowTask(List<WorkflowBatchTaskDTO> workflowBatchTaskDTOList);
  /**
   * 插入complete数据
   *
   * @param batchIds 批次id
   * @return 是否成功
   */
  @Insert(
      "<script>"
          + "insert into wf_batch_complete_task select * from wf_batch_current_task where batch_id in"
          + "<foreach collection='list' item='item' index='index' open='(' separator=',' close=')' >"
          + " #{item} "
          + "</foreach>"
          + "</script>")
  boolean insertCompleteFromCurrentByBatchIds(List<Long> batchIds);
  /**
   * 删除已备份数据
   *
   * @param batchIds 批次id
   * @return 是否成功
   */
  @Delete(
      "<script>"
          + "delete from wf_batch_current_task where batch_id in"
          + "<foreach collection='list' item='item' index='index' open='(' separator=',' close=')' >"
          + " #{item} "
          + "</foreach>"
          + "</script>")
  boolean deleteCurrentByBatchIds(List<Long> batchIds);
}
