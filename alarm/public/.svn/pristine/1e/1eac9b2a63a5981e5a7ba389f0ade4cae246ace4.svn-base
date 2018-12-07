package com.wslint.wisdomreport;

import com.wslint.wisdomreport.AlarmAgent.AlarmInfo;
import com.wslint.wisdomreport.BackupAgent.BackupInfo;
import com.wslint.wisdomreport.MsgDefine.CMD;
import com.wslint.wisdomreport.MsgDefine.Priority;
import com.wslint.wisdomreport.MsgDefine.ServiceDefine;
import com.wslint.wisdomreport.OptlogAgent.OptlogInfo;
import com.wslint.wisdomreport.comm.ReactorClient;
import com.wslint.wisdomreport.queue.LockFreeQueue;

import java.io.IOException;

/**
 * 模块框架，所有消息中心之外模块（业务模块）从此类继承 封装到消息中心的通信过错 提供高性能面锁消息队列，子类通过个实现 handleEvents处理消息 通过SendMsg函数发送消息
 */
public abstract class TaskBase implements Runnable {

  private static LockFreeQueue msgQueue;
  protected MsgDefine.ServiceDefine serviceID;
  private ReactorClient clientChannel;
  private Thread ccThread;

  public TaskBase(MsgDefine.ServiceDefine serviceID) {
    try {
      this.serviceID = serviceID;

      // 创建消息队列
      msgQueue = new LockFreeQueue(1000000);
      //clientChannel = new ReactorClient(InetAddress.getLocalHost().toString(), 10023, msgQueue);
      clientChannel = new ReactorClient("127.0.0.1", 10023, serviceID, msgQueue);
      ccThread = new Thread(clientChannel);
      ccThread.start();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 循环处理队列消息
   */
  public void run() {

    while (true) {
      //msgQueue.printQueue();

      //出队1条消息
      MsgDefine msg = msgQueue.dequeuc();
      if (null == msg) {
        //如果没有消息，休眠10ms
        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      } else {
        // 处理消息
        handleEvents(msg);
      }

      // 触发定时器
      OnTimer();
    }
  }

  /**
   * 消息处理函数,强制子类处理各条消息
   */
  protected abstract void handleEvents(MsgDefine msg);

  /**
   * 定时器函数
   */
  protected void OnTimer() {
    //
  }

  /**
   * 服务加载
   */
  public abstract int loadService();

  /**
   * 服务卸载
   */
  public abstract int unloadService();

  /**
   * 服务启动
   */
  public abstract int startService();

  /**
   * 服务停止
   */
  public abstract int stopService();

  /**
   * 发送数据
   */
  public int SendMsg(MsgDefine msg) {
    return clientChannel.SendMsg(msg);
  }

  /**
   * 消息处理函数
   */
  public void sendAlarmMsg(AlarmInfo alarmInfo) {
    MsgDefine msg = new MsgDefine();
    msg.setSrcServiceID(serviceID);
    msg.setDstServiceID(ServiceDefine.ALARM_SERVICE);
    msg.setCMD(CMD.REPORT_ALARM);
    msg.setPriority(Priority.MIDDLE);

    String msgInfo = alarmInfo.toString();
    msg.setMsgLen(msgInfo.length());
    msg.setContent(msgInfo.getBytes());
    SendMsg(msg);
  }

  /**
   * 日志消息处理
   */
  public void sendLogMsg(OptlogInfo optlogInfo) {
    MsgDefine msg = new MsgDefine();
    msg.setSrcServiceID(serviceID);
    msg.setDstServiceID(ServiceDefine.LOG_SERVICE);
    msg.setCMD(CMD.REPORT_LOG);
    msg.setPriority(Priority.MIDDLE);

    String msgInfo = optlogInfo.toString();
    msg.setMsgLen(msgInfo.length());
    msg.setContent(msgInfo.getBytes());
    SendMsg(msg);
  }
  
  /**
   * 备份消息处理
   */
  public void sendBackupMsg(BackupInfo backupInfo) {
    MsgDefine msg = new MsgDefine();
    msg.setSrcServiceID(serviceID);
    msg.setDstServiceID(ServiceDefine.BACKUP_SERVICE);
    msg.setCMD(CMD.BACKUP_FILE);
    msg.setPriority(Priority.MIDDLE);
    String msgInfo = backupInfo.toString();
    msg.setMsgLen(msgInfo.length());
    msg.setContent(msgInfo.getBytes());
    SendMsg(msg);
  }
}
