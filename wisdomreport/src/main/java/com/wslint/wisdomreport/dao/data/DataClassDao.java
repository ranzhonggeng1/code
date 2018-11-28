package com.wslint.wisdomreport.dao.data;

import com.wslint.wisdomreport.domain.dto.data.clazz.DataClassDTO;
import com.wslint.wisdomreport.domain.dto.data.record.DataRecordDTO;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * 类别数据管理
 *
 * @author yuxr
 * @since 2018/11/16 14:41
 */
public interface DataClassDao {

  /**
   * 插入类别数据
   *
   * @param dataClassDTOList 类别数据
   * @return 是否插入成功
   */
  @Insert(
      "<script>"
          + "insert into data_class "
          + "(id, batch_id, first_class_id, second_class_id, gmt_create, gmt_modified, create_user, modify_user) values "
          + "<foreach collection='list' item='item' index='index' separator=',' >"
          + "(#{item.id}, #{item.batchId}, #{item.firstClassId}, #{item.secondClassId}, #{item.gmtCreate}, #{item.gmtModified}, #{item.createUser}, #{item.modifyUser})"
          + "</foreach>"
          + "</script>")
  boolean insertDataClasss(List<DataClassDTO> dataClassDTOList);

  /**
   * 根据批次id获取所有检查项信息
   *
   * @param batchId 批次id
   * @return 检查项信息
   */
  @Select("select * from vw_data_class_detail where batch_id = #{batchId}")
  List<DataClassDTO> getClasssByBatchId(Long batchId);

  /**
   * 根据类别id获取类别信息
   * @param dataRecordDTOList 存储类别信息的对象
   * @return 查询结果
   */
  @Select(
      "<script>"
          + "select * from data_class where id in "
          + "<foreach collection='list' item='item' index='index' open='(' separator=',' close=')'>"
          + "#{item.classId}"
          + "</foreach>"
          + "</script>")
  List<DataClassDTO> getClasssByClassIds(List<DataRecordDTO> dataRecordDTOList);
}
