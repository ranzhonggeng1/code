package com.wslint.wisdomreport.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.alibaba.fastjson.JSON;
import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.domain.dto.user.User;
import com.wslint.wisdomreport.util.TestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginControllerTest {

  @Autowired private SecurityManager securityManager;
  @Autowired private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    // 登录必须要添加安全管理
    SecurityUtils.setSecurityManager(securityManager);
    //    TestUtils.login(STR_ADMIN, Constant.STR_DEFULAT_AP, webApplicationContext,
    // securityManager);
  }

  @Test
  public void login() throws Exception {

    ResultActions resultActions;
    // 测试成功登录
    resultActions = getLoingResultActions(Constant.STR_ADMIN, Constant.SIR_DEFAULT_R_AP);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("code").value(200));
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("msg").value("登录成功!"));
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("data").isMap());
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("data.id").isNotEmpty());
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("data.userName").isNotEmpty());
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("data.complete").isNotEmpty());
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("data.Authorization").isNotEmpty());
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("data.permissionCodeList ").isArray());

    // 退出
    TestUtils.logout(mockMvc);

    // 测试密码错误
    resultActions = getLoingResultActions("admin", "1");
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("code").value(-1));
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("msg").value("用户名/密码错误"));

    // 退出
    TestUtils.logout(mockMvc);

    // 测试没有用户
    resultActions = getLoingResultActions("***", "1");
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("code").value(-1));
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("msg").value("无此用户"));

    // 退出
    TestUtils.logout(mockMvc);
  }

  @Test
  public void TemporaryTestLogin() throws Exception {

    ResultActions resultActions;
    // 测试成功登录
    resultActions = getLoingResultActions(Constant.STR_ADMIN, Constant.SIR_DEFAULT_R_AP);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("code").value(200));
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("msg").value("登录成功!"));

    // 退出
    TestUtils.logout(mockMvc);
  }

  /**
   * 拼接登录用户参数的json串
   *
   * @param userCode 用户编码
   * @param password 用户密码
   * @return 拼接json
   */
  private ResultActions getLoingResultActions(String userCode, String password) throws Exception {
    User user = new User();
    user.setUserCode(userCode);
    user.setPassword(password);

    // 测试登录
    String jsonStr = JSON.toJSONString(user);
    // post提交一个data
    RequestBuilder request =
        post("/login/login").contentType(MediaType.APPLICATION_JSON).content(jsonStr.getBytes());
    // 待验证结果
    return mockMvc.perform(request);
  }
}
