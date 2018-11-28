package com.wslint.wisdomreport.controller.common;

import com.wslint.wisdomreport.utils.ReturnUtils;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author yuxr
 * @date 2018/08/31
 */
@ApiIgnore
@RestController
@RequestMapping("/html")
public class HtmlController {

  private Logger log = LoggerFactory.getLogger(HtmlController.class);

  @RequestMapping("/login")
  public Map<String, Object> login() {
    return ReturnUtils.loginInvalidMap("登录失效！");
  }

  @RequestMapping("/index")
  public Map<String, Object> index() {
    return ReturnUtils.loginInvalidMap("未登录！");
  }

  @RequestMapping("/403")
  public Map<String, Object> html403() {
    return ReturnUtils.loginInvalidMap("未登录！");
  }
}
