package cn.wslint.log;

import cn.wslint.log.common.OptlogConstant;
import cn.wslint.log.dao.impl.OptLogServiceImpl;
import cn.wslint.log.resources.OptlogAddResource;
import com.alibaba.fastjson.JSONObject;
import com.wslint.wisdomreport.MsgDefine;
import com.wslint.wisdomreport.TaskBase;
import java.util.UUID;


/**
 * 基于模块框架的业务实现 1、需要实现为服务加载4个接口：loadService、unloadService、startService、stopService
 * 2、实现消息处理函数handleEvents
 *
 * Author: rzg
 * Date: 2018/12/6 9:40
 */
public class OptlogCenter extends TaskBase {

  public OptlogCenter() {
    //明确本服务ID
    super(MsgDefine.ServiceDefine.LOG_SERVICE);

  }

  /**
   * 模块加载: 成功返回1,失败返回0
   */
  public int loadService() {
    // 模块加载代码
    return 1;
  }

  /**
   * 模块卸载: 成功返回1,失败返回0
   */
  public int unloadService() {
    // 模块卸载代码
    return 1;
  }

  /**
   * 模块启动: 成功返回1,失败返回0
   */
  public int startService() {
    // 模块启动代码
    return 1;
  }

  /**
   * 模块停止: 成功返回1,失败返回0
   */
  public int stopService() {
    // 模块停止代码
    return 1;
  }

  /**
   * 消息事件处理器
   */
  protected void handleEvents(MsgDefine msg) {
    switch (msg.getCMD()) {
      case REPORT_LOG:
        //日志消息体
        String logInfo = new String(msg.getContent());
        //处理日志消息
        msgDeal(logInfo);
        break;
      default:
        //String msgInfo = new String(msg.getContent());
        //System.out.println("Unknown msg: from "+msg.getSrcServiceID()+ ". cmd:"+msg.getCMD()+". content:"+msgInfo);
        break;
    }
  }

  private void msgDeal(String logInfo) {
    //DB实体
    OptLogServiceImpl optLogService = new OptLogServiceImpl();
    OptlogAddResource optlogAddResource = new OptlogAddResource();

    //实体拼装
    JSONObject logParams = JSONObject.parseObject(logInfo); // 转化成json对象

    // 设置告警ID，UUID方式生成
    String optlogId = UUID.randomUUID().toString().replace("-", "").toLowerCase();

    optlogAddResource.setDateTime((String) logParams.get(OptlogConstant.OPTLOG_DAYE_TIME));
    optlogAddResource.setId(optlogId);
    optlogAddResource.setName((String) logParams.get(OptlogConstant.OPTLOG_NAME));
    optlogAddResource.setDeviceHost(
        (String) logParams.get(OptlogConstant.OPTLOG_DEVICE_HOST));
    optlogAddResource.setServiceHost((String) logParams.get(OptlogConstant.OPTLOG_SERVICE_HOST));
    optlogAddResource.setType((String) logParams.get(OptlogConstant.OPTLOG_TYPE));
    optlogAddResource.setUserName((String) logParams.get(OptlogConstant.OPTLOG_USER_NAME));
    optlogAddResource.setUserId((String) logParams.get(OptlogConstant.OPTLOG_USER_ID));
    optlogAddResource.setObjectId((String) logParams.get(OptlogConstant.OPTLOG_OBJECT_ID));
    optlogAddResource.setObjectName(
        (String) logParams.get(OptlogConstant.OPTLOG_OBJECT_NAME));
    optlogAddResource.setResult((String) logParams.get(OptlogConstant.OPTLOG_RESULT));
    optlogAddResource.setContent((String) logParams.get(OptlogConstant.OPTLOG_CONTENT));

    optLogService.handleOptlog(optlogAddResource);
  }

  public static void main(String[] args) {
    //创建并运行模块框架
    OptlogCenter optlogAgent = new OptlogCenter();
    Thread workThread = new Thread(optlogAgent);
    workThread.start();

    //执行框架之外的业务代码
//    showCaseModule.alarmFunc();
  }
}
