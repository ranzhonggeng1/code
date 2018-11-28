package com.wslint.wisdomreport.dao;

import com.wslint.wisdomreport.dao.data.DataSqlProvider;
import com.wslint.wisdomreport.domain.dto.data.WrReport;
import com.wslint.wisdomreport.domain.dto.data.WrReportAllData;
import com.wslint.wisdomreport.domain.dto.data.WrReportData;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * 业务数据持久层处理
 *
 * @author yuxr
 * @since 2018/8/13 16:21
 */
public interface IDataDao {

  /**
   * 根据传入参数查询所有数据信息
   *
   * @param param 传入数据封装
   * @return 返回所有符合要求的数据
   */
  @SelectProvider(type = DataSqlProvider.class, method = "selectDataCheck")
  @Results({
    @Result(property = "id", column = "id"),
    @Result(property = "batchNo", column = "batch_no"),
    @Result(property = "medicine", column = "medicine"),
    @Result(property = "firstClass", column = "first_class"),
    @Result(property = "secondClass", column = "second_class"),
    @Result(property = "thirdClass", column = "third_class"),
    @Result(property = "class1", column = "class1"),
    @Result(property = "class2", column = "class2"),
    @Result(property = "class3", column = "class3"),
    @Result(property = "hold1", column = "hold1"),
    @Result(property = "hold2", column = "hold2"),
    @Result(property = "hold3", column = "hold3"),
    @Result(property = "gmtCreate", column = "gmt_create"),
    @Result(property = "gmtModified", column = "gmt_modified"),
    @Result(property = "version", column = "version"),
    @Result(property = "status", column = "status"),
    @Result(property = "approver", column = "approver"),
    @Result(property = "dataId", column = "data_id"),
    @Result(property = "dataReportId", column = "data_report_id"),
    @Result(property = "dataOrderId", column = "data_order_id"),
    @Result(property = "dataData", column = "data_data"),
    @Result(property = "dataInputer", column = "data_inputer"),
    @Result(property = "dataReviewer", column = "data_reviewer"),
    @Result(property = "dataChangeReason", column = "data_change_reason"),
    @Result(property = "dataGmtCreate", column = "data_gmt_create"),
    @Result(property = "dataGmtModified", column = "data_gmt_modified"),
    @Result(property = "dataVersion", column = "data_version"),
    @Result(property = "dataStatus", column = "data_status"),
    @Result(property = "dataHold1", column = "data_hold1"),
    @Result(property = "dataHold2", column = "data_hold2"),
    @Result(property = "dataHold3", column = "data_hold3"),
    @Result(property = "dataHold4", column = "data_hold4"),
    @Result(property = "dataHold5", column = "data_hold5"),
  })
  List<WrReportAllData> getWrReportAllDatas(Map<String, Object> param);

  /**
   * 插入report数据
   *
   * @param wrReport 传入数据
   * @return 成功插入的条数
   */
  @Insert(
      "insert into wr_report(id,batch_no,medicine,first_class,second_class,third_class,class1,class2,class3,hold1,hold2,hold3,gmt_create,gmt_modified) "
          + "values(#{id},#{batchNo},#{medicine},#{firstClass},#{secondClass},#{thirdClass},#{class1},#{class2},#{class3},#{hold1},#{hold2},#{hold3},#{gmtCreate},#{gmtModified})")
  int insertWrReport(WrReport wrReport);

  /**
   * 批量插入 report 数据
   *
   * @param wrReportList 传入数据
   * @return 成功插入的条数
   */
  @Insert(
      "<script>"
          + "insert into wr_report(id,batch_no,medicine,first_class,second_class,third_class,class1,class2,class3,hold1,hold2,hold3,gmt_create,gmt_modified) values"
          + "<foreach collection='list' item='item' index='index'  separator=','>"
          + "(#{item.id},#{item.batchNo},#{item.medicine},#{item.firstClass},#{item.secondClass},#{item.thirdClass},#{item.class1},#{item.class2},#{item.class3},#{item.hold1},#{item.hold2},#{item.hold3},#{item.gmtCreate},#{item.gmtModified})"
          + "</foreach>"
          + "</script>")
  int batchInserWrReport(List<WrReport> wrReportList);

  /**
   * 批量插入reportdata数据
   *
   * @param wrReportDataList reportdata的数据
   * @return 返回成功插入的条数
   */
  @Insert(
      "<script>"
          + "insert into wr_report_data(id,report_id,order_id,data,inputer,reviewer,change_reason,gmt_create,gmt_modified,version,status,hold1,hold2,hold3,hold4,hold5,modify_reviewer,imageurl) values"
          + "<foreach collection='list' item='item' index='index'  separator=','>"
          + "(#{item.id},#{item.reportId},#{item.orderId},#{item.data},#{item.inputer},#{item.reviewer},#{item.changeReason},#{item.gmtCreate},#{item.gmtModified},#{item.version},#{item.status},#{item.hold1},#{item.hold2},#{item.hold3},#{item.hold4},#{item.hold5},#{item.modifyReviewer},#{item.imageUrl})"
          + "</foreach>"
          + "</script>")
  int batchInsertWrReportData(List<WrReportData> wrReportDataList);
  /**
   * 创建批次
   *
   * @param param 创建批次的信息
   * @return 数据表中插入数据的条数
   */
  @Insert(
      "<script>"
          + "insert into wr_report (id,batch_no,medicine,first_class,second_class,third_class,class1,class2,class3,hold1,hold2,hold3,gmt_create,gmt_modified) values"
          + "<foreach collection='list' item='item' index='index'  separator=','>"
          + "(#{item.id},#{item.batchNo},#{item.medicine},#{item.firstClass},#{item.secondClass},#{item.thirdClass},#{item.class1},#{item.class2},#{item.class3},#{item.hold1},#{item.hold2},#{item.hold3},#{item.gmtCreate},#{item.gmtModified})"
          + "</foreach>"
          + "</script>")
  int createBatch(List<WrReport> param);

  /**
   * 查询某个小类的数据
   *
   * @param param 查询条件
   * @return 查询结果
   */
  @Select(
      "select wrd.*, "
          + " (select user_name from user where id = wrd.inputer) as inputer_name, "
          + " (select user_name from user where id = wrd.reviewer) as reviewer_name, "
          + " (select user_name from user where id = wrd.modify_reviewer) as modify_reviewer_name "
          + " from wr_report_data wrd, "
          + " wr_report wr "
          + " where wrd.report_id = wr.id "
          + " and wr.medicine = #{medicine} "
          + " and wr.batch_no = #{batchNo} "
          + " and wr.first_class = #{firstClass} "
          + " and wr.second_class = #{secondClass} "
          + " order by wrd.version desc")
  List<WrReportData> getOneClassData(WrReport param);

  /**
   * 根据传入参数的药品名和批次号查询出所有类别
   *
   * @param param 查询信息
   * @return 查询到的结果
   */
  @Select(
      "select wr.*,"
          + " wrd.id              as data_id, "
          + " wrd.report_id       as data_report_id, "
          + " wrd.order_id        as data_order_id, "
          + " wrd.data            as data_data, "
          + " wrd.inputer         as data_inputer, "
          + " wrd.reviewer        as data_reviewer, "
          + " wrd.modify_reviewer as data_modify_reviewer, "
          + " wrd.change_reason   as data_change_reason, "
          + " wrd.gmt_create      as data_gmt_create, "
          + " wrd.gmt_modified    as data_gmt_modified, "
          + " wrd.imageurl        as data_imageurl, "
          + " wrd.status          as data_status, "
          + " wrd.version         as data_version, "
          + " wrd.hold1           as data_hold1, "
          + " wrd.hold2           as data_hold2, "
          + " wrd.hold3           as data_hold3, "
          + " wrd.hold4           as data_hold4, "
          + " wrd.hold5           as data_hold5, "
          + " (select user_name from user where id = wrd.inputer)         as data_inputer_name, "
          + " (select user_name from user where id = wrd.reviewer)        as data_reviewer_name, "
          + " (select user_name from user where id = wrd.modify_reviewer) as data_modify_reviewer_name "
          + " from wr_report wr"
          + " right join wr_report_data wrd on wr.id = wrd.report_id"
          + " where batch_no = #{batchNo} "
          + " and medicine = #{medicine} ")
  List<WrReportAllData> getAllClassData(WrReport param);

  /**
   * 根据相关信息查询数据库中是否存在相关数据
   *
   * @param wrReport 查询信息
   * @return 查询到的主表id，为0表示没查到
   */
  @Select(
      "<script>select id from wr_report where medicine=#{medicine} and batch_no=#{batchNo} and first_class=#{firstClass} and second_class=#{secondClass} limit 0,1</script>")
  Long getWrReportId(WrReport wrReport);

  /**
   * 根据用户id查询复核任务
   *
   * @param reviewerId 复核人Id
   * @return 数据列表
   */
  @Select(
      "<script>select medicine,batch_no,first_class,second_class from wr_report_data as data left join wr_report on data.report_id=wr_report.id where  not exists "
          + "(select 1 from wr_report_data "
          + "where <![CDATA[data.version < wr_report_data.version]]> and data.report_id=wr_report_data.report_id and data.order_id=wr_report_data.order_id ) and reviewer=${reviewerId} and data.status=2</script>")
  List<WrReport> getReviewList(@Param("reviewerId") long reviewerId);

  /**
   * 根据用户id查询修改复核任务
   *
   * @param userId
   * @return
   */
  @Select(
      "<script>select medicine,batch_no,first_class,second_class from wr_report_data as data left join wr_report on data.report_id=wr_report.id where  not exists "
          + "(select 1 from wr_report_data "
          + "where <![CDATA[data.version < wr_report_data.version]]> and data.report_id=wr_report_data.report_id and data.order_id=wr_report_data.order_id) and modify_reviewer=${userId} and data.status=4</script>")
  List<WrReport> getModifyReviewList(@Param("userId") long userId);

  /**
   * 根据数据id获取历史数据
   *
   * @param wrReportData 查询数据
   * @return 历史数据
   */
  @Select(
      "select *, (select user_name from user where id = inputer) as inputer_name,(select user_name from user where id = reviewer) as reviewer_name, (select user_name from user where id = modify_reviewer) as modify_reviewer_name from wr_report_data where report_id = #{reportId} and order_id = #{orderId} order by version desc")
  List<WrReportData> getDataByReportIdAndOrderId(WrReportData wrReportData);

  /**
   * 根据药品id 和 批次号获取模板信息
   *
   * @param wrReport 查询信息
   * @return 模板信息
   */
  @Select(
      "select * from wr_report where medicine = #{medicine} and batch_no = #{batchNo} and first_class = 3 and second_class = 12 limit 1")
  WrReport getReportIdByMedicineIdAndBatchNo(WrReport wrReport);
}
