'''
任务框架
作者：吴昌议
日期：20181204
说明：子类继承TaskBase获得消息驱动的框架能力， 子类必须实现微服务、事件处理接口
'''
# -*- coding: utf-8 -*-
import struct
import threading
from time import sleep

from msgDefine import MsgDefine
from msgQueue import MsgQueue
from socketHandler import SocketHandler


class TaskBase:
    def __init__(self, serviceID):
        self.msgQueue = MsgQueue()
        self.serviceID = serviceID
        self.sockHandler = SocketHandler("127.0.0.1", 10023, self.msgQueue)

        # 向MSG_CENTER发送注册信息
        registerMsg = MsgDefine()
        registerMsg.srcServiceID = serviceID
        registerMsg.dstServiceID = MsgDefine.ServiceDefine.MSG_SERVICE
        registerMsg.cmd = MsgDefine.CMD.REGISTER
        registerMsg.priority = MsgDefine.Priority.HEIGHT
        registerMsg.content = "request register service."
        registerMsg.msgLen = len(registerMsg.content)
        self.sockHandler.SendMsg(registerMsg)

        #接收MSG_CENTER的注册应答消息
        answerMsg = self.sockHandler.extractMsg()
        print("service:{} receive register answer, CMD:{}. message:{}".
              format(serviceID, answerMsg.cmd, answerMsg.content))

        # 启动消息接收线程
        self.sockHandler.run()

    '''
    启动消息处理线程
    '''
    def run(self):
        eht = threading.Thread(target=self.eventsHandleThread)
        eht.setDaemon(True)
        eht.start()

    '''
    消息处理线程
    '''
    def eventsHandleThread(self):
        while True:
            msg = self.msgQueue.dequeuc()
            if None == msg:
                sleep(0.01)  # 如果没有消息， 休眠10ms
            else:
                eventFunc = self.handleEvents(msg.cmd)
                if None != eventFunc:
                    eventFunc(msg)
                else:
                    print("handle error message, srcServiceID:{} cmd:{}, content:{}". \
                          format(msg.srcServiceID, msg.cmd, msg.content))

    '''
    消息处理事件
    '''
    def handleEvents(self, msg):
        raise NotImplementedError

    '''
    定时器事件
    '''
    def OnTimer(self):
        pass

    '''
    加载服务
    '''
    def loadService(self):
        raise NotImplementedError

    '''
    卸载服务
    '''
    def unloadService(self):
        raise NotImplementedError

    '''
    启动服务
    '''
    def startService(self):
        raise NotImplementedError

    '''
    停止服务
    '''
    def stopService(self):
        raise NotImplementedError

    '''
    发送消息
    '''
    def SendMsg(self, msg):
        self.sockHandler.SendMsg(msg)