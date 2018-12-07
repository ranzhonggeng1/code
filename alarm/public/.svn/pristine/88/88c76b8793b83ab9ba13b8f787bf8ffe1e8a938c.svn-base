package com.wslint.wisdomreport.AlarmAgent;

import cn.hutool.core.bean.BeanUtil;
import java.util.Map;

/**
 * 发送告警实体类
 *
 * @author ranzhonggeng
 *
 * 2018年12月5日
 */
public class AlarmInfo {

  private static final long serialVersionUID = -6201793338937462437L;
  /**
   * 警报id
   */
  private String id;
  /**
   * 警报名称
   */
  private String alarmName;

  /**
   * 告警模块名称
   */
  private String componentName;
  /**
   * 警报级别
   */
  private String level;
  /**
   * 告警的主机
   */
  private String host;
  /**
   * 报警时间
   */
  private String dateTime;
  /**
   * 报警的内容
   */
  private String content;
  /**
   * 报错方法的堆栈，方便开发人员快速定位异常
   */
  private String traceStack;
  /**
   * 异常堆栈
   */
  private String exception;
  /**
   * 状态：0：异常，1：已恢复（未推送），2：历史异常（已解决）
   */
  private String status;

  public String getAlarmName() {
    return alarmName;
  }

  public void setAlarmName(String alarmName) {
    this.alarmName = alarmName;
  }

  public String getComponentName() {
    return componentName;
  }

  public void setComponentName(String componentName) {
    this.componentName = componentName;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getDateTime() {
    return dateTime;
  }

  public void setDateTime(String dateDB) {
    this.dateTime = dateDB;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getTraceStack() {
    return traceStack;
  }

  public void setTraceStack(String traceStack) {
    this.traceStack = traceStack;
  }

  public String getException() {
    return exception;
  }

  public void setException(String exception) {
    this.exception = exception;
  }

  public Map<String, Object> toMap() {
    return BeanUtil.beanToMap(this);
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "{\"alarm_name\":\""
        + alarmName
        + "\",\"component_name\":\""
        + componentName
        + "\",\"level\":\""
        + level
        + "\",\"host\":\""
        + host
        + "\",\"date_time\":\""
        + dateTime
        + "\",\"content\":\""
        + content
        + "\",\"status\":\""
        + status
        + "\",\"trace_stack\":\""
        + traceStack
        + "\",\"exception\":\""
        + exception
        + "\"}";
  }
}
