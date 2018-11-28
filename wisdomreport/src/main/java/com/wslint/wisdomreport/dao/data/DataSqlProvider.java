package com.wslint.wisdomreport.dao.data;

import com.wslint.wisdomreport.common.AppConstant;
import java.util.List;
import java.util.Map;

/**
 * data相关sql拼装类
 *
 * @author yuxr
 * @since 2018/8/15 16:00
 */
public class DataSqlProvider {

  /**
   * 拼装药品批次数据是否存在sql * todo 未考虑sql注入
   *
   * @param medicineIdBatchNosMap 药品批次信息
   * @return 拼装好的sql
   */
  public String getHaveDataByMedicineIdBatchNosMapSql(
      Map<Long, List<String>> medicineIdBatchNosMap) {
    StringBuilder sql = new StringBuilder(" select if(count(1) = 0, 0, 1) from data_batch where ");
    if (medicineIdBatchNosMap.isEmpty()) {
      sql.append(" 1=0 ");
    }
    medicineIdBatchNosMap.forEach(
        (key, value) -> {
          sql.append(" (valid = 1 and medicine_id = ").append(key).append(" and batch_no in ( ");
          value.forEach(batchNo -> sql.append("'").append(batchNo).append("',"));
          sql.deleteCharAt(sql.length() - 1).append(" )) or");
        });
    sql.delete(sql.length() - 2, sql.length());
    return sql.toString();
  }

  /**
   * 拼装查询小类对应空格id的sql * 已考虑sql注入
   *
   * @param classOrderIdsMap 空格id对应map
   * @return 拼装好的sql
   */
  public String getHaveDataByClassOrderIdsMapSql(Map<Long, List<Long>> classOrderIdsMap) {
    StringBuilder sql = new StringBuilder(" select if(count(1) = 0, 0, 1) from data_record where ");
    if (classOrderIdsMap.isEmpty()) {
      sql.append(" 1=0 ");
    }
    classOrderIdsMap.forEach(
        (key, value) -> {
          sql.append(" (class_id = ").append(key).append(" and order_id in ( ");
          value.forEach(orderId -> sql.append(orderId).append(","));
          sql.deleteCharAt(sql.length() - 1).append(" )) or");
        });
    sql.delete(sql.length() - 2, sql.length());
    return sql.toString();
  }

  /**
   * 拼装查询批次对应空格id的sql * 已考虑sql注入
   *
   * @param batchOrderIdsMap 空格id对应map
   * @return 拼装好的sql
   */
  public String getHaveDataByBatchOrderIdsMapSql(Map<Long, List<Long>> batchOrderIdsMap) {
    StringBuilder sql = new StringBuilder(" select if(count(1) = 0, 0, 1) from data_report where ");
    if (batchOrderIdsMap.isEmpty()) {
      sql.append(" 1=0 ");
    }
    batchOrderIdsMap.forEach(
        (key, value) -> {
          sql.append(" (batch_id = ").append(key).append(" and order_id in ( ");
          value.forEach(orderId -> sql.append(orderId).append(","));
          sql.deleteCharAt(sql.length() - 1).append(" )) or");
        });
    sql.delete(sql.length() - 2, sql.length());
    return sql.toString();
  }

  /**
   * 拼接需要处理的sql
   *
   * @param param 传入参数
   * @return 返回值
   */
  public String selectDataCheck(Map<String, Object> param) {
    StringBuilder sql =
        new StringBuilder(
            "select wr.id,wr.batch_no,wr.medicine,wr.first_class,wr.second_class,wr.third_class,wr.class1,wr.class2,wr.class3,wr.hold1,wr.hold2,wr.hold3,wr.gmt_create,wr.gmt_modified,wr.approver,wr.version,wr.status,wrd.id as data_id,wrd.report_id as data_report_id,wrd.order_id as data_order_id,wrd.data as data_data,wrd.inputer as data_inputer,wrd.reviewer as data_reviewer,wrd.change_reason as data_change_reason,wrd.gmt_create as data_gmt_create,wrd.gmt_modified as data_gmt_modified,wrd.version as data_version,wrd.status as data_status,wrd.hold1 as data_hold1,wrd.hold2 as data_hold2,wrd.hold3 as data_hold3,wrd.hold4 as data_hold4,wrd.hold5 as data_hold5 from wr_report wr left join ");
    if (!(boolean) param.get(AppConstant.IS_ALL)) {
      sql.append(" vw_wr_report_data wrd on wrd.report_id = wr.id where 1 = 1 ");
    } else {
      sql.append(" wr_report_data wrd on wrd.report_id = wr.id where 1 = 1 ");
    }
    if (null != param.get(AppConstant.BATCH_ID)) {
      sql.append(" and wr.batch_no = ").append(String.valueOf(param.get(AppConstant.BATCH_ID)));
    }
    if (null != param.get(AppConstant.MEDICINE_ID)) {
      sql.append(" and wr.medicine = ").append(String.valueOf(param.get(AppConstant.MEDICINE_ID)));
    }
    if (null != param.get(AppConstant.ACTIVITY_ID)) {
      sql.append(" and wr.first_class = ")
          .append(String.valueOf(param.get(AppConstant.ACTIVITY_ID)));
    }
    if (null != param.get(AppConstant.CLASS_ID)) {
      sql.append(" and wr.second_class = ").append(String.valueOf(param.get(AppConstant.CLASS_ID)));
    }
    if (null != param.get(AppConstant.APPROVER)) {
      sql.append(" and wr.approver = ").append(String.valueOf(param.get(AppConstant.APPROVER)));
    }
    if (null != param.get(AppConstant.STATUS)) {
      sql.append(" and wr.status = ").append(String.valueOf(param.get(AppConstant.STATUS)));
    }
    sql.append(" order by wr.gmt_modified desc");
    return sql.toString();
  }
}
