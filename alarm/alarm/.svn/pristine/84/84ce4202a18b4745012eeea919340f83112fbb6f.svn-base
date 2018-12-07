package cn.wslint.alarm;

import cn.wslint.alarm.common.Constant;
import cn.wslint.alarm.dao.impl.AlarmReceiverDaoImpl;
import cn.wslint.alarm.emailTemplate.TemplateModel;
import cn.wslint.alarm.resources.AlarmResource;
import cn.wslint.alarm.resources.Level;
import com.alibaba.fastjson.JSONObject;
import com.wslint.wisdomreport.MsgDefine;
import com.wslint.wisdomreport.TaskBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *  基于模块框架的业务实现
 *  1、需要实现为服务加载4个接口：loadService、unloadService、startService、stopService
 *  2、实现消息处理函数handleEvents
 *
 * @Author: rzg
 * @Date: 2018/12/5 10:17
 */
public class AlarmCenter extends TaskBase {

  public AlarmCenter() {
    //明确本服务ID
    super(MsgDefine.ServiceDefine.ALARM_SERVICE);
    
  }

  /**
   * 模块加载: 成功返回1,失败返回0
   */
  public int loadService(){
    // 模块加载代码
    return 1;
  }

  /**
   * 模块卸载: 成功返回1,失败返回0
   */
  public int unloadService(){
    // 模块卸载代码
    return 1;
  }

  /**
   * 模块启动: 成功返回1,失败返回0
   */
  public int startService(){
    // 模块启动代码
    return 1;
  }

  /**
   * 模块停止: 成功返回1,失败返回0
   */
  public int stopService(){
    // 模块停止代码
    return 1;
  }

  /**
   * 消息事件处理器
   * @param msg
   */
  protected void handleEvents(MsgDefine msg){
	System.out.println("222");
    switch(msg.getCMD()) {
      case REPORT_ALARM:
        //告警消息体
        String alarmInfo = new String(msg.getContent());
        //处理告警消息
        msgDeal(alarmInfo);
        break;
      default:
        //String msgInfo = new String(msg.getContent());
        //System.out.println("Unknown msg: from "+msg.getSrcServiceID()+ ". cmd:"+msg.getCMD()+". content:"+msgInfo);
        break;
    }
  }

  private void msgDeal(String alarmInfo) {
    try {
      //DB实体
      AlarmResource alarmResource = new AlarmResource();
      //发送实体
      TemplateModel model = new TemplateModel();

      //实体拼装
      JSONObject alarmParams = JSONObject.parseObject(alarmInfo); // 转化成json对象
      // 时间格式化
      String dateStr = (String)alarmParams.get(Constant.FIELD_ALARM_DATE_TIME);
      SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date date = formate.parse(dateStr);
      
      // 设置告警ID，UUID方式生成
      String alarmId = UUID.randomUUID().toString().replace("-", "").toLowerCase();

      // 解析
      alarmResource.setId(alarmId);
      alarmResource.setAlarmName((String) alarmParams.get(Constant.FIELD_ALARM_NAME));
      alarmResource.setComponentName((String) alarmParams.get(Constant.FIELD_ALARM_COMPONENT_NAME));
      alarmResource.setLevel((String) alarmParams.get(Constant.FIELD_ALARM_LEVEL));
      alarmResource.setHost((String) alarmParams.get(Constant.FIELD_ALARM_HOST));
      alarmResource.setDateTime(date);
      alarmResource.setContent((String) alarmParams.get(Constant.FIELD_ALARM_CONTENT));
      alarmResource.setTraceStack((String) alarmParams.get(Constant.FIELD_ALARM_TRACE_STACK));
      alarmResource.setException((String) alarmParams.get(Constant.FIELD_ALARM_EXCEPTION));
      alarmResource.setStatus((String) alarmParams.get(Constant.FIELD_ALARM_STATUS));

      model.setAlarmName((String) alarmParams.get(Constant.FIELD_ALARM_NAME));
      model.setComponentName((String) alarmParams.get(Constant.FIELD_ALARM_COMPONENT_NAME));
      model.setHost((String) alarmParams.get(Constant.FIELD_ALARM_HOST));
      switch ((String) alarmParams.get(Constant.FIELD_ALARM_LEVEL)) {
        case "debug":
          model.setLevel(Level.DEBUG);
          break;
        case "info":
          model.setLevel(Level.INFO);
          break;
        case "warn":
          model.setLevel(Level.WARN);
          break;
        case "error":
          model.setLevel(Level.ERROR);
          break;
      }
      model.setDateTime(date);
      model.setContent((String) alarmParams.get(Constant.FIELD_ALARM_CONTENT));
      model.setTraceStack((String) alarmParams.get(Constant.FIELD_ALARM_TRACE_STACK));
      model.setContent((String) alarmParams.get(Constant.FIELD_ALARM_CONTENT));

      //发送接口类
      AlarmReceiverDaoImpl alarmReceiver = new AlarmReceiverDaoImpl();
      //存储
      alarmReceiver.addAlarmResource(alarmResource);
//      // 发送
      alarmReceiver.sendAlarm(model);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args){

    //创建并运行模块框架
    AlarmCenter alarmCenter= new AlarmCenter();
    Thread workThread = new Thread(alarmCenter);
    workThread.start();
  }
}
