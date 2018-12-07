package cn.wslint.alarm.provider;

import cn.wslint.alarm.resources.Receiver;
import java.text.ParseException;

/**
 * 定义一个联系人提供者的接口，因为本系统不仅仅支持配置文件定义发送者 也支持将联系人存放在数据库中，因此，定义一个接口，方便拓展。
 *
 * @author ranzhonggeng
 */
public interface ContactProvider {

  Receiver getReceiver() throws ParseException;
}
