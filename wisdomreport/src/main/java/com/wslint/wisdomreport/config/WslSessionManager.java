package com.wslint.wisdomreport.config;

import java.io.Serializable;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * session管理器
 *
 * @author yuxr
 * @since 2018/8/21 15:56
 */
public class WslSessionManager extends DefaultWebSessionManager {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(WslSessionManager.class);

  private static final String AUTHORIZATION = "Authorization";

  private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

  public WslSessionManager() {
    super();
  }

  @Override
  protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
    LOGGER.debug("------------------ 获取sessionId start ------------------");
    String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
    // 如果请求头中有 Authorization 则其值为sessionId
    if (!StringUtils.isEmpty(id)) {
      request.setAttribute(
          ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
      request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
      request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
      LOGGER.info("------------------ sessionId = {} ------------------", id);
      LOGGER.debug("------------------ 获取sessionId end ------------------");
      return id;
    } else {
      LOGGER.debug("------------------ 获取sessionId end ------------------");
      // 否则按默认规则从cookie取sessionId
      return super.getSessionId(request, response);
    }
  }
}
