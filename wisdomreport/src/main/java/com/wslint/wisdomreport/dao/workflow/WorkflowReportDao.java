package com.wslint.wisdomreport.dao.workflow;

import com.wslint.wisdomreport.domain.dto.workflow.WorkflowReportTaskDTO;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * 报告工作流数据处理服务
 *
 * @author yuxr
 * @since 2018/11/13 15:43
 */
public interface WorkflowReportDao {

  /**
   * 批量插入工作流数据
   *
   * @param workflowReportTaskDTOList 工作流数据list
   * @return 是否成功处理
   */
  @Insert(
      "<script>"
          + "insert into wf_report_current_task  "
          + "(id, report_id, operation, operator, next_operator, reason, "
          + " old_data, old_remark, old_remark_time, old_remarker, "
          + " status, next_status, hold1, hold2, hold3, gmt_create, gmt_modified) values "
          + "<foreach collection='list' item='item' index='index' separator=',' >"
          + "(#{item.id}, #{item.reportId}, #{item.operation}, #{item.operator}, #{item.nextOperator}, #{item.reason}, "
          + " #{item.oldData}, #{item.oldRemark}, #{item.oldRemarkTime}, #{item.oldRemarker}, "
          + " #{item.status}, #{item.nextStatus}, #{item.hold1}, #{item.hold2}, #{item.hold3}, #{item.gmtCreate}, #{item.gmtModified})"
          + "</foreach>"
          + "</script>")
  boolean batchInsertWorkflowTask(List<WorkflowReportTaskDTO> workflowReportTaskDTOList);

  /**
   * 根据 reportId 按照操作顺序查询工作流数据
   *
   * @param reportId reportId
   * @return 工作流数据
   */
  @Select(
      "select * from vw_wf_report_current_task where report_id = #{reportId} order by gmt_modified desc")
  List<WorkflowReportTaskDTO> getWorkflowCurrentTasksByReportId(Long reportId);

  /**
   * 根据 reportId 按照操作顺序查询工作流数据
   *
   * @param reportId reportId
   * @return 工作流数据
   */
  @Select(
      "select * from vw_wf_report_complete_task where report_id = #{reportId} order by gmt_modified desc")
  List<WorkflowReportTaskDTO> getWorkflowCompleteTasksByReportId(Long reportId);

  /**
   * 备份报告工作流数据
   *
   * @param reportIds 报告id
   * @return 是否备份成功
   */
  @Insert(
      "<script>"
          + "insert into wf_report_complete_task select * from wf_report_current_task where report_id in "
          + "<foreach collection='list' item='item' index='index' open='(' separator=',' close=')' >"
          + " #{item}"
          + "</foreach> "
          + "</script>")
  boolean insertCompleteFromCurrentByReportId(List<Long> reportIds);

  /**
   * 批量删除工作流数据
   *
   * @param reportIds 报告id
   * @return 是否删除成功
   */
  @Delete(
      "<script>"
          + "delete from wf_report_current_task where report_id in "
          + "<foreach collection='list' item='item' index='index' open='(' separator=',' close=')' >"
          + " #{item}"
          + "</foreach>"
          + "</script>")
  boolean deleteCurrentByReportId(List<Long> reportIds);
}
