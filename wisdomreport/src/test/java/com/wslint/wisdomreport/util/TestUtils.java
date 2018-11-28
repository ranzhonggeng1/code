package com.wslint.wisdomreport.util;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

/**
 * 测试工具类
 *
 * @author yuxr
 * @since 2018/9/6 15:15
 */
public class TestUtils {

  /**
   * 模拟登录
   *
   * @param username 用户名
   * @param password 密码
   * @param webApplicationContext webApplicationContext
   * @param securityManager securityManager
   */
  public static void login(
      String username,
      String password,
      WebApplicationContext webApplicationContext,
      SecurityManager securityManager) {
    MockHttpServletRequest mockHttpServletRequest =
        new MockHttpServletRequest(webApplicationContext.getServletContext());
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
    MockHttpSession mockHttpSession =
        new MockHttpSession(webApplicationContext.getServletContext());
    mockHttpServletRequest.setSession(mockHttpSession);
    SecurityUtils.setSecurityManager(securityManager);
    Subject subject =
        new WebSubject.Builder(mockHttpServletRequest, mockHttpServletResponse).buildWebSubject();
    UsernamePasswordToken token = new UsernamePasswordToken(username, password, true);
    subject.login(token);
    ThreadContext.bind(subject);
  }

  /**
   * 模拟用户退出
   *
   * @param mockMvc mock模拟
   * @throws Exception 异常信息
   */
  public static void logout(MockMvc mockMvc) throws Exception {
    // post提交一个data
    RequestBuilder request = post("/login/logout").contentType(MediaType.APPLICATION_JSON);
    // 验证
    ResultActions resultActions = mockMvc.perform(request);
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("code").value(200));
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("msg").value("用户已安全退出!"));
  }
}
