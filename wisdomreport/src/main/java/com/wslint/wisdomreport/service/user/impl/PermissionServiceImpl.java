package com.wslint.wisdomreport.service.user.impl;

import com.google.gson.Gson;
import com.wslint.wisdomreport.dao.user.PermissionDao;
import com.wslint.wisdomreport.domain.dto.permission.Permission;
import com.wslint.wisdomreport.domain.dto.user.User;
import com.wslint.wisdomreport.service.user.IPermissionService;
import com.wslint.wisdomreport.utils.ReturnUtils;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统权限管理
 *
 * @author yuxr
 * @since 2018/9/10 10:14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl implements IPermissionService {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(PermissionServiceImpl.class);

  @Autowired PermissionDao permissionDao;

  /**
   * 获取系统全部权限
   *
   * @return 处理结果
   */
  @Override
  public Map<String, Object> getAllPermissions() {
    List<Permission> permissions = permissionDao.getAllPermissions();
    LOGGER.info("------------------ 查询所有权限成功！------------------");
    LOGGER.debug("------------------ 查询所有权限:{} ------------------", new Gson().toJson(permissions));
    return ReturnUtils.successMap(permissions, "查询所有权限成功！");
  }

  /**
   * 根据权限码获取对应权限的用户
   *
   * @param permissionCode 权限码
   * @return 查询信息
   */
  @Override
  public Map<String, Object> getUsersByPermissionCode(String permissionCode) {
    List<User> users = permissionDao.getUsersByPermissionCode(permissionCode);
    LOGGER.info("------------------  根据权限码获取用户成功！------------------");
    LOGGER.debug("------------------  根据权限码获取用户:{} ------------------", new Gson().toJson(users));
    return ReturnUtils.successMap(users, "根据权限码获取用户成功！");
  }

  /**
   * 根据权限码和待过滤用户获取用户列表
   *
   * @param permissionCode 权限码
   * @param users 待过滤用户
   * @return 查询信息
   */
  @Override
  public Map<String, Object> getFilterUsersByPermissionCode(String permissionCode,
      List<Long> users) {
    List<User> rightUsers = permissionDao.getFilterUsersByPermissionCode(permissionCode,users);
    return ReturnUtils.successMap(rightUsers, "根据权限码获取用户成功！");
  }
}
