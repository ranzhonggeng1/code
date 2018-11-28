package com.wslint.wisdomreport.dao;

import com.wslint.wisdomreport.domain.dto.workflow.WrWfTask;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * 工作流dao
 *
 * @author yuxr
 * @since 2018/9/12 13:57
 */
public interface WorkFlowDao {

  /**
   * 新增工作流待办表
   *
   * @param wrWfTask 工作流数据
   */
  @Insert(
      "INSERT INTO WR_WF_CURRENT_TASK (TASK_ID, MEDICINE_ID, BATCH_NO, REVIEWER_ID, REVIEW_COMMENT, GMT_CREATE, GMT_MODIFIED, OPERATOR_ID, OPERATOR_NAME, STATUS, HOLD1) VALUES (#{taskId}, #{medicineId}, #{batchNo}, #{reviewerId}, #{reviewComment}, #{gmtCreate}, #{gmtModified}, #{operatorId}, #{operatorName}, #{status}, #{hold1})")
  void insertWrWfCurrentTask(WrWfTask wrWfTask);

  /**
   * 新增工作流已完成表
   *
   * @param wrWfTask 工作流数据
   */
  @Insert(
      "INSERT INTO WR_WF_COMPLETE_TASK (TASK_ID, MEDICINE_ID, BATCH_NO, REVIEWER_ID, REVIEW_COMMENT, GMT_CREATE, GMT_MODIFIED, OPERATOR_ID, OPERATOR_NAME, STATUS, HOLD1) VALUES (#{taskId}, #{medicineId}, #{batchNo}, #{reviewerId}, #{reviewComment}, #{gmtCreate}, #{gmtModified}, #{operatorId}, #{operatorName}, #{status}, #{hold1})")
  void insertWrWfCompleteTask(WrWfTask wrWfTask);

  /**
   * 根据任务ID删除当前工作流待办表
   *
   * @param taskId 任务id
   */
  @Delete("DELETE  FROM WR_WF_CURRENT_TASK WHERE TASK_ID = #{taskId}")
  void deleteWrWfCurrentTaskByTaskId(Long taskId);

  /**
   * 根据任务ID获取当前工作流待办表
   *
   * @param taskId 任务id
   * @return 工作流数据
   */
  @Select("SELECT * FROM WR_WF_CURRENT_TASK WHERE TASK_ID = #{taskId}")
  WrWfTask getWrWfCurrentTaskByTaskId(Long taskId);

  /**
   * 根据药品id和状态获取未完成数据
   *
   * @param wrWfTask 工作流数据
   * @return 未完成工作流数据列表
   */
  @Select(
      "<script>"
          + " SELECT * FROM WR_WF_CURRENT_TASK WHERE MEDICINE_ID = #{medicineId} "
          + "<when test='status != 0'> AND STATUS = #{status} </when>"
          + "ORDER BY GMT_MODIFIED, STATUS"
          + "</script>")
  List<WrWfTask> getWrWfCurrentTasksByMedicineIdAndStatus(WrWfTask wrWfTask);

  /**
   * 根据药品id和状态获取已完成数据
   *
   * @param wrWfTask 工作流数据
   * @return 未完成工作流数据列表
   */
  @Select(
      "SELECT * FROM WR_WF_COMPLETE_TASK WHERE MEDICINE_ID = #{medicineId} AND STATUS = #{status} ORDER BY GMT_MODIFIED, STATUS")
  List<WrWfTask> getWrWfCompleteTasksByMedicineIdAndStatus(WrWfTask wrWfTask);

  /**
   * 根据药品id获取已完成数据
   *
   * @param wrWfTask 工作流数据
   * @return 已完成工作流数据列表
   */
  @Select(
      "SELECT * FROM WR_WF_COMPLETE_TASK WHERE MEDICINE_ID = #{medicineId} ORDER BY GMT_MODIFIED, STATUS")
  List<WrWfTask> getWrWfCompleteTasksByMedicineId(WrWfTask wrWfTask);

  /**
   * 根据批次号，获取所有工作流历史记录
   *
   * @param wrWfTask 工作流数据
   * @return 工作流数据
   */
  @Select(
      "(SELECT * FROM WR_WF_CURRENT_TASK WHERE BATCH_NO = #{batchNo} ) UNION ALL "
          + "(SELECT * FROM WR_WF_COMPLETE_TASK WHERE BATCH_NO = #{batchNo})  ORDER BY GMT_MODIFIED, STATUS")
  List<WrWfTask> getWrWfAllTasksByMedicineIdAndBatchNo(WrWfTask wrWfTask);

  /**
   * 根据审核人查看待处理数据
   *
   * @param wrWfTask 工作流数据
   * @return 工作流数据
   */
  @Select(
      "SELECT * FROM WR_WF_CURRENT_TASK WHERE REVIEWER_ID = #{reviewerId} ORDER BY GMT_MODIFIED, STATUS")
  List<WrWfTask> getWrWfCurrentTasksByReviewerId(WrWfTask wrWfTask);

  /**
   * 根据任务id查询所有未完成数据
   *
   * @param testTaskId 任务id
   * @return 工作流数据
   */
  @Select(
      "SELECT * FROM WR_WF_CURRENT_TASK WHERE TASK_ID = #{taskId} ORDER BY GMT_MODIFIED, STATUS")
  List<WrWfTask> getWrWfCurrentTasksByTaskId(Long testTaskId);

  /**
   * 根据任务id查询所有已完成数据
   *
   * @param testTaskId 任务id
   * @return 工作流数据
   */
  @Select(
      "SELECT * FROM WR_WF_COMPLETE_TASK WHERE TASK_ID = #{taskId} ORDER BY GMT_MODIFIED, STATUS")
  List<WrWfTask> getWrWfCompleteTasksByTaskId(Long testTaskId);

  /**
   * 判断任务id对应的待办任务是否存在
   *
   * @param taskId 任务id
   * @return 是否存在数据
   */
  @Select("SELECT IF(COUNT(1) = 0 ,0, 1) FROM WR_WF_CURRENT_TASK WHERE TASK_ID = #{taskId} ")
  boolean isWrWFCurrentTaskExist(Long taskId);
}
