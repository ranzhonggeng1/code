package com.wslint.wisdomreport.controller.user;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.constant.UserConstant;
import com.wslint.wisdomreport.domain.dto.user.User;
import com.wslint.wisdomreport.domain.vo.user.PasswordRestVO;
import com.wslint.wisdomreport.domain.vo.user.PasswordUpdateVO;
import com.wslint.wisdomreport.domain.vo.user.UserAddVO;
import com.wslint.wisdomreport.service.user.IUserService;
import com.wslint.wisdomreport.utils.ReturnUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 用户信息处理类
 *
 * @author yuxr
 */
@Api(tags = "1 用户权限接口", description = "提供用户权限管理的相关接口")
@RestController
@RequestMapping(value = "/user")
public class UserController {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @Autowired private IUserService iUserService;

  /**
   * 新增用户接口
   *
   * @param userAddVO 用户信息
   * @return 处理结果
   */
  @ApiOperation(value = "新增用户接口", notes = "新增用户")
  @ApiImplicitParam(name = "userAddVO", value = "新增用户信息", required = true, dataType = "UserAddVO")
  @RequestMapping(value = "/addUser", method = RequestMethod.POST)
  public Map<String, Object> addUser(@RequestBody UserAddVO userAddVO) {
    User user = new User();
    user.setUserCode(userAddVO.getUserCode());
    user.setUserName(userAddVO.getUserName());
    user.setPassword(userAddVO.getPassword());
    if (user.getUserCode() == null || user.getUserCode().isEmpty()) {
      LOGGER.info("------------------ 用户帐号为空！------------------");
      return ReturnUtils.failureMap("用户帐号为空！");
    }
    if (user.getPassword() == null || user.getPassword().isEmpty()) {
      LOGGER.info("------------------ 密码为空！------------------");
      return ReturnUtils.failureMap("密码为空！");
    }
    return iUserService.addUser(user);
  }

  /**
   * todo 删除用户接口
   *
   * @param user 用户信息
   * @return 处理结果
   */
  @ApiIgnore
  @ApiOperation(value = "删除用户接口", notes = "删除用户")
  @ApiImplicitParam(name = "user", value = "用户信息", required = true, dataType = "User")
  @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> delUser(@RequestBody User user) {
    if (user.getId().equals(Constant.ID_0)) {
      LOGGER.info("------------------ 系统管理员不可删除！------------------");
      return ReturnUtils.failureMap("系统管理员不可删除！");
    }
    User userInfo = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    if (userInfo.getId().equals(user.getId())) {
      LOGGER.info("------------------ 您不能删除自己的账户！------------------");
      return ReturnUtils.failureMap("您不能删除自己的账户！");
    }
    return iUserService.deleteUserById(user);
  }

  /**
   * 密码修改接口
   *
   * @param passwordUpdateVO 用户信息
   * @return 处理结果
   */
  @ApiOperation(value = "密码修改接口", notes = "修改密码，成功后退出当前用户。")
  @ApiImplicitParam(
      name = "passwordUpdateVO",
      value = "用户信息",
      required = true,
      dataType = "PasswordUpdateVO")
  @RequestMapping(value = "/password/update", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> passwordUpdate(@RequestBody PasswordUpdateVO passwordUpdateVO) {
    User user = new User();
    user.setId(passwordUpdateVO.getId());
    user.setPassword(passwordUpdateVO.getPassword());
    user.setOldPassword(passwordUpdateVO.getOldPassword());
    return iUserService.passwordUpdate(user);
  }
  /**
   * 密码重置接口
   *
   * @param passwordRestVO 被重置密码的用户id
   * @return 处理结果
   */
  @ApiOperation(value = "密码重置接口", notes = "重置密码，只有系统管理员拥有此接口权限")
  @ApiImplicitParam(
      name = "passwordRestVO",
      value = "被重置密码的用户id",
      required = true,
      dataType = "PasswordRestVO")
  @RequestMapping(value = "/password/reset", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> passwordReset(@RequestBody PasswordRestVO passwordRestVO) {
    return iUserService.passwordReset(passwordRestVO.getUserId());
  }

  /**
   * 完善用户信息
   *
   * @param user 用户信息
   * @return 处理结果
   */
  @ApiOperation(value = "完善用户信息接口", notes = "完善用户信息")
  @ApiImplicitParam(name = "user", value = "用户信息", required = true, dataType = "User")
  @RequestMapping(value = "/completeUserInfo", method = RequestMethod.POST)
  public Map<String, Object> completeUserInfo(@RequestBody User user) {
    if (user.getId() == null) {
      LOGGER.info("------------------ 找不到用户！------------------");
      return ReturnUtils.failureMap("找不到用户！");
    }
    if (user.getOldPassword() == null || user.getOldPassword().isEmpty()) {
      LOGGER.info("------------------ 验证密码为空！------------------");
      return ReturnUtils.failureMap("验证密码为空！");
    }
    if (user.getSign() == null || user.getSign().isEmpty()) {
      LOGGER.info("------------------ 签名信息为空！------------------");
      return ReturnUtils.failureMap("签名信息为空！");
    }
    if (user.getSign().length() >= Constant.NUM_100) {
      LOGGER.info("------------------ 签名信息过长！------------------");
      return ReturnUtils.failureMap("签名信息过长！");
    }
    user.setPassword(null);
    user.setComplete(UserConstant.STATUS_COMPLETED);
    return iUserService.updateUser(user);
  }

  /**
   * 修改密码
   *
   * @param user 用户信息
   * @return 处理结果
   */
  @ApiIgnore
  @ApiOperation(value = "修改用户密码接口", notes = "修改用户密码")
  @ApiImplicitParam(name = "user", value = "用户信息", required = true, dataType = "User")
  @RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
  public Map<String, Object> modifyPassword(@RequestBody User user) {
    if (user.getId() == null) {
      LOGGER.info("------------------ 找不到用户！------------------");
      return ReturnUtils.failureMap("找不到用户！");
    }
    if (user.getOldPassword() == null || user.getOldPassword().isEmpty()) {
      LOGGER.info("------------------ 验证密码为空！------------------");
      return ReturnUtils.failureMap("验证密码为空！");
    }
    if (user.getPassword() == null || user.getPassword().isEmpty()) {
      LOGGER.info("------------------ 新密码为空！------------------");
      return ReturnUtils.failureMap("新密码为空！");
    }
    user.setComplete(null);
    return iUserService.passwordUpdate(user);
  }

  /**
   * 查看所有用户
   *
   * @return 查询结果
   */
  @ApiOperation(value = "查询所有用户接口", notes = "查询所有用户接口")
  @RequestMapping(value = "/getAllUsers", method = RequestMethod.POST)
  public Map<String, Object> getAllUsers() {
    return iUserService.getAllUsers();
  }
}
