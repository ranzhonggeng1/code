package cn.wslint.alarm.dao;

import cn.hutool.json.JSONException;
import cn.wslint.alarm.emailTemplate.TemplateModel;
import cn.wslint.alarm.resources.AlarmResource;
import cn.wslint.alarm.resources.AlarmSearchResource;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 * 告警对外接口
 *
 * @author ranzhonggeng
 * <p>2018年11月9日
 */
public interface AlarmReceiverDao {

  /**
   * 告警添加数据库
   *
   * @return boolean
   */
  boolean addAlarmResource(AlarmResource contact) throws ParseException;

  /**
   * 发送告警信息到wechat和email
   */
  void sendAlarm(TemplateModel model);

  /**
   * 告警获取
   *
   * @return Alarm String
   */
  List<AlarmResource> getAlarmResource() throws JSONException, SQLException, ParseException;

  /**
   * 1、条件查询 2、查询出未解决的告警
   *
   * @return AlarmResource List
   */
  List<AlarmResource> getAlarmSearch(AlarmSearchResource alarmSearch)
      throws JSONException, SQLException, ParseException;

  /**
   * 告警清除，根据component_name、host和trace_stack判定相同的未解决告警信息是否已经存在，存在则更新状态为已解决
   *
   * @return boolean
   */
  boolean clearAlarm(AlarmResource alarm);
}
