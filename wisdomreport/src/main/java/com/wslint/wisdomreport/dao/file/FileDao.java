package com.wslint.wisdomreport.dao.file;

import com.wslint.wisdomreport.domain.dto.file.CfgfileVersion;
import org.apache.ibatis.annotations.Select;

/**
 * 文件管理dao
 *
 * @author yuxr
 * @since 2018/9/18 15:03
 */
public interface FileDao {

  /**
   * 根据药品ID获取对应配置数据
   *
   * @param medicineId 版本信息
   * @return 配置数据
   */
  @Select("select * from cfgfile_version where medicine_id = #{medicineId}")
  CfgfileVersion getCfgfileVersionByMedicineId(Long medicineId);

  /**
   * 根据药品ID和版本号获取对应配置文件路径和文件名
   *
   * @param cfgfileVersion 版本信息
   * @return 配置数据
   */
  @Select(
      "select path, file_name from cfgfile_version where medicine_id = #{medicineId} and version = #{version}")
  CfgfileVersion getCfgfileVersionByMedicineIdAndVersion(CfgfileVersion cfgfileVersion);
}
