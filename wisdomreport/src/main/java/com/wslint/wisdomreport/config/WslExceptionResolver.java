package com.wslint.wisdomreport.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理
 *
 * @author yuxr
 * @date 2018/09/03
 */
public class WslExceptionResolver implements HandlerExceptionResolver {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(WslExceptionResolver.class);

  @Override
  public ModelAndView resolveException(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      Object o,
      Exception ex) {
    LOGGER.debug("------------------ 鉴权异常 start ------------------");
    if (ex instanceof UnauthenticatedException) {
      return new ModelAndView("/html/403");
    } else if (ex instanceof UnauthorizedException) {
      return new ModelAndView("/html/login");
    }
    return new ModelAndView("/html/403");
  }
}
