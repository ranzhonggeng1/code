package com.wslint.wisdomreport.service.user;

import java.util.Map;

/**
 * 权限管理
 *
 * @author yuxr
 * @since 2018/9/10 10:14
 */
public interface IPermissionService {
  /**
   * 获取系统全部权限
   *
   * @return 处理结果
   */
  Map<String, Object> getAllPermissions();

  /**
   * 根据权限码获取对应权限的用户
   *
   * @param permissionCode 权限码
   * @return 查询信息
   */
  Map<String, Object> getUsersByPermissionCode(String permissionCode);
}
