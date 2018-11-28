package com.wslint.wisdomreport.dao.user;

import com.wslint.wisdomreport.domain.dto.permission.Permission;
import com.wslint.wisdomreport.domain.dto.user.User;
import java.util.List;
import org.apache.ibatis.annotations.Select;

/**
 * 权限管理Dao
 *
 * @author yuxr
 * @since 2018/9/10 14:26
 */
public interface PermissionDao {

  /**
   * 查询系统全部权限
   *
   * @return 权限结果
   */
  @Select("select * from permission order by id")
  List<Permission> getAllPermissions();

  /**
   * 根据权限码获取对应权限的用户
   *
   * @param permissionCode 权限码
   * @return 查询信息
   */
  @Select(
      "select distinct(id), user_code, user_name from user_permission where id != 0 and permission_code = #{permissionCode} order by id")
  List<User> getUsersByPermissionCode(String permissionCode);
}
