package com.wslint.wisdomreport;

import com.wslint.wisdomreport.MsgDefine.ServiceDefine;
import com.wslint.wisdomreport.resource.OptlogInfo;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志添加演示
 *
 * @Author: rzg
 * @Date: 2018/12/6 10:13
 */

public class OptlogTaskCase extends TaskBase {

  public OptlogTaskCase(ServiceDefine serviceID) {
    super(serviceID);
  }

  @Override
  protected void handleEvents(MsgDefine msgDefine) {

  }

  @Override
  public int loadService() {
    return 0;
  }

  @Override
  public int unloadService() {
    return 0;
  }

  @Override
  public int startService() {
    return 0;
  }

  @Override
  public int stopService() {
    return 0;
  }

  /**
   * test
   *
   * @param args 入口
   */
  public static void main(String args[]) {
    OptlogTaskCase optlogTaskCase = new OptlogTaskCase(ServiceDefine.SHOWCASE_SERVICE);

    SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String date = formate.format(new Date());

    //数据测试
    OptlogInfo optlogInfo = new OptlogInfo();
    optlogInfo.setName("test optlog");
    optlogInfo.setDeviceHost("192.168.1.1");
    optlogInfo.setServiceHost("127.0.0.1");
    optlogInfo.setContent("test");
    optlogInfo.setObjectId("1232321");
    optlogInfo.setObjectName("xxxxxxxx");
    optlogInfo.setDateTime(date);
    optlogInfo.setUserId("fr411");
    optlogInfo.setUserName("admin");
    optlogInfo.setResult("success");
    optlogInfo.setType("add");

    optlogTaskCase.sendLogMsg(optlogInfo);
  }
}

