'''
任务框架样例
作者：吴昌议
日期：20181204
说明：
1.业务模块从TaskBase继承，获得消息发送、消息自动驱动消息处理函数的能力
2.业务模块需要实现微服务接口: 实
  (1) 在eventToFunc中登记消息与消息处理函数的关系
  (2) 实现消息处理函数
'''
# -*- coding: utf-8 -*-
import threading
from time import sleep

from msgDefine import MsgDefine
from taskBase import TaskBase


class ShowCaseService(TaskBase):
    def __init__(self):
        super(ShowCaseService, self).__init__(MsgDefine.ServiceDefine.SHOWCASE_SERVICE)

    '''
    定义消息处理函数
    '''
    def handleEvents(self, cmdID):
        eventFuncs = {
            MsgDefine.CMD.BACKUP_FILE           : self.doBackup,
            MsgDefine.CMD.STOP_SERVICE_ANSWER   : self.stopService,
            MsgDefine.CMD.REPORT_ALARM          : self.handleAlarm
        }
        return eventFuncs.get(cmdID, None)

    '''
    定时器事件
    '''
    def OnTimer(self):
        pass

    '''
    加载服务
    '''
    def loadService(self):
        pass

    '''
    卸载服务
    '''
    def unloadService(self):
        pass

    '''
    启动服务
    '''
    def startService(self):
        pass

    '''
    停止服务
    '''
    def stopService(self):
        pass

    '''
    消息处理函数
    '''
    def doBackup(self, msg):
        print("handle CMD: {}, message info: {}".format(msg.cmd, msg.content))


    '''
    消息处理函数
    '''
    def handleAlarm(self, msg):
        print("handle CMD: {}, message info: {}".format(msg.cmd, msg.content))

    '''
    业务函数
    '''
    def xxFunc(self):

        while True:

            sleep(1)

            # 测试，向自己发送告警信息
            testMsg = MsgDefine()
            testMsg.srcServiceID = self.serviceID
            testMsg.dstServiceID = self.serviceID
            testMsg.cmd = MsgDefine.CMD.REPORT_ALARM
            testMsg.content = "test Alarm."
            testMsg.msgLen = len(testMsg.content)
            self.SendMsg(testMsg)



if __name__ == "__main__":
    scs = ShowCaseService()
    scs.run()

    # 执行业务任意其他函数
    scs.xxFunc()


