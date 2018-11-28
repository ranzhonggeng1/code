package com.wslint.wisdomreport.controller.user;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.domain.dto.user.User;
import com.wslint.wisdomreport.domain.vo.user.LoginVO;
import com.wslint.wisdomreport.service.user.ILoginService;
import com.wslint.wisdomreport.service.user.IUserService;
import com.wslint.wisdomreport.utils.CommonUtils;
import com.wslint.wisdomreport.utils.ReturnUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统登录鉴权
 *
 * @author yuxr
 * @since 2018/8/21 15:56
 */
@RestController
@RequestMapping("/login")
@Api(tags = "1 用户权限接口", description = "提供用户权限管理的相关接口")
public class LoginController {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

  @Autowired private IUserService iUserService;
  @Autowired private ILoginService iLoginService;

  /**
   * 用户登录
   *
   * @param loginVO 用户信息
   * @return 登录信息
   */
  @ApiOperation(value = "用户登录接口", notes = "系统用户登录入口，返回记录用户信息的session")
  @ApiImplicitParam(
      name = "loginVO",
      value = "用户信息(包含用户编码和密码)",
      required = true,
      dataType = "LoginVO")
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> login(@RequestBody LoginVO loginVO) {

    User user = new User();
    user.setUserCode(loginVO.getUserCode());
    user.setPassword(loginVO.getPassword());

    if (user.getUserCode() == null || user.getPassword() == null) {
      return ReturnUtils.failureMap("用户名/密码为空！");
    }
    Map<String, Object> dataMap = new HashMap<>(Constant.NUM_16);
    // 添加用户认证信息
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken usernamePasswordToken =
        new UsernamePasswordToken(CommonUtils.getShiroCodeByUser(user), user.getPassword());
    String message = "登录成功!";
    try {
      // 进行验证，这里可以捕获异常，然后返回对应信息
      subject.login(usernamePasswordToken);
      // 查询用户信息
      User currentUser = iUserService.getUserByCode(user.getUserCode());

      dataMap.put("id", currentUser.getId());
      dataMap.put("userName", currentUser.getUserName());
      dataMap.put("permissionCodeList", currentUser.getPermissionCodeList());
      dataMap.put("complete", currentUser.getComplete());
      dataMap.put("Authorization", subject.getSession().getId());
      LOGGER.debug(
          "------------------ SessionId is : {} ------------------ ", subject.getSession().getId());
    } catch (UnknownAccountException e) {
      message = "无此用户";
    } catch (IncorrectCredentialsException e) {
      message = "用户名/密码错误";
    } catch (ExcessiveAttemptsException e) {
      message = "登录失败多次，账户锁定十分钟";
    } catch (AuthenticationException e) {
      message = "系统超时!";
      LOGGER.error("------------------ error is : {} ------------------", e.getMessage());
    } finally {
      LOGGER.info("------------------ Message is : {} ------------------", message);
    }
    boolean authenticated = subject.isAuthenticated();
    if (authenticated) {
      CommonUtils.getCurrentUser();
      return ReturnUtils.successMap(dataMap, message);
    }
    return ReturnUtils.failureMap(message);
  }

  /**
   * 人员验证
   *
   * @param user 用户信息
   * @return 登录信息
   */
  @ApiOperation(value = "用户验证接口", notes = "验证用户密码是否正确")
  @ApiImplicitParam(name = "user", value = "用户信息(包含用户编码和密码)", required = true, dataType = "User")
  @RequestMapping(value = "/check", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> check(@RequestBody User user) {
    if (user.getUserCode() == null || user.getPassword() == null) {
      LOGGER.info("------------------ 用户名/密码为空！------------------");
      return ReturnUtils.failureMap("用户名/密码为空！");
    }
    User checkUser = iLoginService.checkUserInfo(user.getUserCode(), user.getPassword());
    if (null != checkUser) {
      Map<String, Object> dataMap = new HashMap<>();
      dataMap.put(Constant.STR_ID, checkUser.getId());
      return ReturnUtils.successMap(dataMap, "验证通过！");
    }
    return ReturnUtils.failureMap("验证不通过！");
  }

  /**
   * 测试IP连接
   *
   * @return 测试通过信息
   */
  @ApiOperation(value = "IP连接测试接口", notes = "测试IP的连接是否成功")
  @RequestMapping(value = "/testIP", method = RequestMethod.POST)
  public Map<String, Object> testIP() {
    return ReturnUtils.successMap("测试IP通过！");
  }

  /**
   * 用户退出
   *
   * @return 处理结果
   */
  @ApiOperation(value = "用户安全退出接口", notes = "用于用户安全退出")
  @RequestMapping(value = "/logout", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> logout() {
    Subject subject = SecurityUtils.getSubject();
    LOGGER.info("------------------ {} 用户退出 ------------------", subject.getPrincipal());
    subject.logout();
    return ReturnUtils.successMap("用户已安全退出!");
  }
}
