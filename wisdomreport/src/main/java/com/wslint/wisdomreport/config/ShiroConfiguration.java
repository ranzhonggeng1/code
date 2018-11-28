package com.wslint.wisdomreport.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * Shiro 配置类
 *
 * @author yuxr
 * @since 2018/8/21 15:37
 */
@Configuration
public class ShiroConfiguration {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(ShiroConfiguration.class);

  /**
   * 异常管理
   *
   * @return 异常管理
   */
  @Bean
  public HandlerExceptionResolver solver() {
    return new WslExceptionResolver();
  }

  /**
   * 生命周期管理
   *
   * @return 生命周期管理
   */
  @Bean
  public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

  /**
   * 将定制的验证方式加入容器
   *
   * @return 定制的验证方式
   */
  @Bean
  public WslShiroRealm wslShiroRealm() {
    LOGGER.debug("------------------ wslShiroRealm() ------------------");
    return new WslShiroRealm();
  }

  /**
   * session管理器
   *
   * @return session管理
   */
  @Bean
  public SessionManager wslSessionManager() {
    LOGGER.debug("------------------ SessionManager() ------------------");
    WslSessionManager wslSessionManager = new WslSessionManager();
    Collection<SessionListener> listeners = new ArrayList<>();
    listeners.add(new WslShiroSessionListener());
    wslSessionManager.setSessionListeners(listeners);
    wslSessionManager.setGlobalSessionTimeout(1800000);
    // 去掉第一次登录时url后跟随jsessionid
    wslSessionManager.setSessionIdUrlRewritingEnabled(false);
    return wslSessionManager;
  }

  /**
   * 权限管理，配置主要是Realm的管理认证
   *
   * @return 安全管理器
   */
  @Bean
  public DefaultWebSecurityManager securityManager() {
    LOGGER.debug("------------------ securityManager() ------------------");
    DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
    defaultWebSecurityManager.setRealm(wslShiroRealm());
    defaultWebSecurityManager.setSessionManager(wslSessionManager());
    return defaultWebSecurityManager;
  }

  /**
   * Filter工厂，设置对应的过滤条件和跳转条件
   *
   * @param defaultWebSecurityManager 默认安全管理器
   * @return Shiro拦截器
   */
  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(
      DefaultWebSecurityManager defaultWebSecurityManager) {
    LOGGER.debug("------------------ shiroFilterFactoryBean() ------------------");
    LOGGER.debug("------------------ 加载url过滤器 start ------------------");
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
    // 拦截器，主意有序的map
    Map<String, String> map = new LinkedHashMap<>();
    // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->

    // todo 暂时先不鉴权
    map.put("/config/**", "anon");

    map.put("/swagger-ui.html", "anon");
    map.put("/swagger-resources/**", "anon");
    map.put("/v2/api-docs/**", "anon");
    map.put("/webjars/springfox-swagger-ui/**", "anon");
    map.put("/login/login", "anon");
    map.put("/login/testIP", "anon");
    map.put("/user/addUser", "anon");
    map.put("/static/**", "anon");

    map.put("/**", "authc");
    // 对所有用户认证
    // 登录
    shiroFilterFactoryBean.setLoginUrl("/html/login");
    // 首页
    //    shiroFilterFactoryBean.setSuccessUrl("/html/index");
    // 错误页面，认证不通过跳转
    shiroFilterFactoryBean.setUnauthorizedUrl("/html/403");

    shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
    LOGGER.debug("------------------ 加载url过滤器 end ------------------");

    return shiroFilterFactoryBean;
  }

  /**
   * 生命周期管理
   *
   * @return 生命周期管理器
   */
  @DependsOn("lifecycleBeanPostProcessor")
  @Bean
  public DefaultAdvisorAutoProxyCreator autoProxyCreator() {
    DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
    advisorAutoProxyCreator.setProxyTargetClass(true);
    return advisorAutoProxyCreator;
  }

  /**
   * 加入注解的使用，不加入这个注解不生效
   *
   * @param defaultWebSecurityManager 默认安全管理器
   * @return 注解服务
   */
  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
      DefaultWebSecurityManager defaultWebSecurityManager) {
    LOGGER.debug("------------------ authorizationAttributeSourceAdvisor() ------------------");
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
        new AuthorizationAttributeSourceAdvisor();
    authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
    return authorizationAttributeSourceAdvisor;
  }
}
