package com.wslint.wisdomreport.config;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * session监听
 *
 * @author yuxr
 * @since 2018/8/21 15:56
 */
public class WslShiroSessionListener implements SessionListener {

  private final AtomicInteger sessionCount = new AtomicInteger(0);

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(WslShiroSessionListener.class);

  @Override
  public void onStart(Session session) {
    LOGGER.debug("创建会话:" + session.getId());
    sessionCount.incrementAndGet();
    LOGGER.debug("创建后当前会话数:" + sessionCount);
  }

  @Override
  public void onStop(Session session) {
    LOGGER.debug("会话停止：" + session.getId());
    sessionCount.decrementAndGet();
    LOGGER.debug("停止后当前会话数：" + sessionCount);
  }

  @Override
  public void onExpiration(Session session) {
    LOGGER.debug("会话过期：" + session.getId());
    sessionCount.decrementAndGet();
    LOGGER.debug("过期后当前会话数：" + sessionCount);
  }

  public AtomicInteger getSessionCount() {
    return sessionCount;
  }
}
