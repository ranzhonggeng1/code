package cn.wslint.alarm.sender;

import cn.hutool.core.lang.Singleton;
import cn.wslint.alarm.sender.impl.EmailSenderImpl;
import cn.wslint.alarm.sender.impl.SmsSenderImpl;
import cn.wslint.alarm.sender.impl.wechat.WeChatSenderImpl;

/**
 * 发送器工厂
 *
 * @author ranzhonggeng
 */
public class SenderFactory {

  public static Sender getSender(String key) {
    System.out.println("SenderFactory :" + key);
    if ("email".equals(key)) {
      return Singleton.get(EmailSenderImpl.class);
    }
    if ("sms".equals(key)) {
      return Singleton.get(SmsSenderImpl.class);
    }
    if ("wechat".equals(key)) {
      return Singleton.get(WeChatSenderImpl.class);
    }
    throw new RuntimeException("not support sender type");
  }
}
