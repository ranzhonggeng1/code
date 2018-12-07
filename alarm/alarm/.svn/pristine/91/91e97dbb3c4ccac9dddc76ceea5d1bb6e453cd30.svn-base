package cn.wslint.alarm.provider;

import cn.hutool.core.lang.Singleton;
import cn.wslint.alarm.config.ConfigManager;
import cn.wslint.alarm.config.ProviderConfig;
import cn.wslint.alarm.provider.impl.JdbcContactProviderImpl;
import cn.wslint.alarm.provider.impl.JsonContactProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 联系人获取工厂类（jdbc或者json）
 *
 * @author ranzhonggeng
 */
public class ContactProviderFactory {

  private static final Logger logger = LoggerFactory.getLogger(ContactProviderFactory.class);
  private static ProviderConfig providerConfig = ConfigManager.getConfig(ProviderConfig.class);
  private static final String JSON_TYPE = "json";
  private static final String JDBC_TYPE = "jdbc";

  public static ContactProvider getContactProvider() {
    String type = providerConfig.getType();
    logger.info(type);
    switch (type) {
      case JSON_TYPE:
        logger.debug("contact type is {}", JSON_TYPE);
        return Singleton.get(JsonContactProviderImpl.class);
      case JDBC_TYPE:
        logger.debug("contact type is {}", JDBC_TYPE);
        return Singleton.get(JdbcContactProviderImpl.class);
      default:
        logger.error("Type mismatch, please type json or jdbc");
        throw new RuntimeException("Type mismatch, please type json or jdbc");
    }
  }
}
