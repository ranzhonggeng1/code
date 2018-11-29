package com.wslint.wisdomreport.controller.user;

import com.wslint.wisdomreport.domain.dto.permission.Permission;
import com.wslint.wisdomreport.domain.vo.user.GetRightUserVO;
import com.wslint.wisdomreport.service.user.IPermissionService;
import com.wslint.wisdomreport.utils.ReturnUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限信息处理类
 *
 * @author yuxr
 * @since 2018/9/10 10:11
 */
@Api(tags = "1 用户权限接口", description = "提供用户权限管理的相关接口")
@RestController
@RequestMapping(value = "/permission")
public class PermissionContreller {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(PermissionContreller.class);

  @Autowired private IPermissionService iPermissionService;

  /**
   * 查询所有权限
   *
   * @return 处理结果
   */
  @ApiOperation(value = "查询所有权限接口", notes = "查询所有权限")
  @RequestMapping(value = "/getAllPermissions", method = RequestMethod.POST)
  public Map<String, Object> getAllPermissions() {
    return iPermissionService.getAllPermissions();
  }

  /**
   * 根据权限码查看用户
   *
   * @return 处理结果
   */
  @ApiOperation(value = "根据权限码查看用户接口", notes = "根据权限码查看用户")
  @ApiImplicitParam(name = "permission", value = "权限信息", required = true, dataType = "Permission")
  @RequestMapping(value = "/getUsersByPermissionCode", method = RequestMethod.POST)
  public Map<String, Object> getUsersByPermissionCode(@RequestBody Permission permission) {
    if (permission.getPermissionCode() == null) {
      LOGGER.info("------------------ 权限码为空！------------------");
      return ReturnUtils.failureMap("权限码为空！");
    }
    return iPermissionService.getUsersByPermissionCode(permission.getPermissionCode());
  }

  /**
   * 根据权限码查看用户
   *
   * @return 处理结果
   */
  @ApiOperation(value = "根据权限码查看用户接口", notes = "根据权限码查看用户")
  @RequestMapping(value = "/getFilterUsersByPermissionCode", method = RequestMethod.POST)
  public Map<String, Object> getFilterUsersByPermissionCode(
      @RequestBody GetRightUserVO getRightUserVO) {
    return iPermissionService.getFilterUsersByPermissionCode(
        getRightUserVO.getPermissionCode(), getRightUserVO.getUsers());
  }
}
