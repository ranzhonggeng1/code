'''
消息定义文件
作者：吴昌议
日期：20181204
说明：必须保证本文件定义内容和java版本的消息头文件保持一致
'''
# -*- coding: utf-8 -*-
import struct
from enum import Enum

class MsgDefine:
    def __init__(self):
        #定义消息结构
        self.srcServiceID = MsgDefine.ServiceDefine.UNKNOWN_SERVICE
        self.dstServiceID = MsgDefine.ServiceDefine.UNKNOWN_SERVICE
        self.cmd          = MsgDefine.CMD.UNKNOWN_CMD
        self.priority     = MsgDefine.Priority.MIDDLE
        self.msgLen       = 0
        self.content      = None

    '''
    定义服务ID
    '''
    class ServiceDefine(Enum):
        MSG_SERVICE             = 0     # 消息中心
        CHECK_RECORD_DB_SERVICE = 1     # 检验记录数据保存到数据库服务
        BACKUP_SERVICE          = 2     # 备份服务
        ALARM_SERVICE           = 3     # 告警服务
        LOG_SERVICE             = 4     # 日志服务
        SHOWCASE_SERVICE        = 99    # 示例服务
        UNKNOWN_SERVICE         = -1    # 未知模块


    '''
    将服务枚举值转换为整数值
    '''
    def serviceIndex(self, fieldName):
        indexDict = {
            self.ServiceDefine.MSG_SERVICE.name             : 0,  # 消息中心
            self.ServiceDefine.CHECK_RECORD_DB_SERVICE.name : 1,  # 检验记录数据保存到数据库服务
            self.ServiceDefine.BACKUP_SERVICE.name          : 2,  # 备份服务
            self.ServiceDefine.ALARM_SERVICE.name           : 3,  # 告警服务
            self.ServiceDefine.LOG_SERVICE.name             : 4,  # 日志服务
            self.ServiceDefine.SHOWCASE_SERVICE.name        : 99,  # 示例服务
            self.ServiceDefine.UNKNOWN_SERVICE.name         : -1  # 未知模块
        }
        return indexDict.get(fieldName.name, None)

    '''
    定义命令字
    '''
    class CMD(Enum):
        REGISTER                = 0      # 注册服务
        HAND_SHAKE              = 1      # 握手命令
        LOAD_SERVICE            = 2      # 服务加载
        START_SERVICE           = 3      # 服务启动
        STOP_SERVICE            = 4      # 服务停止
        UNLOAD_SERVICE          = 5      # 服务卸载
        REPORT_LOG              = 6      # 记录日志
        REPORT_ALARM            = 7      # 上报告警
        BACKUP_FILE             = 8      # 备份文件

        REGISTER_ANSWER         = 1000   # 注册服务
        HAND_SHAKE_ANSWER       = 1001   # 握手命令
        LOAD_SERVICE_ANSWER     = 1002   # 服务加载
        START_SERVICE_ANSWER    = 1003   # 服务启动
        STOP_SERVICE_ANSWER     = 1004   # 服务停止
        UNLOAD_SERVICE_ANSWER   = 1005   # 服务卸载
        REPORT_LOG_ANSWER       = 1006   # 记录日志
        REPORT_ALARM_ANSWER     = 1007   # 上报告警
        BACKUP_FILE_ANSWER      = 1008   # 备份文件

        UNKNOWN_CMD             = -1     # 未知命令

    '''
    将命令字枚举值转换为整数值
    '''
    def cmdIndex(self, fieldName):
        indexDict = {
            self.CMD.REGISTER.name              : 0,      # 注册服务
            self.CMD.HAND_SHAKE.name            : 1,       # 握手命令
            self.CMD.LOAD_SERVICE.name          : 2,      # 服务加载
            self.CMD.START_SERVICE.name         : 3,      # 服务启动
            self.CMD.STOP_SERVICE.name          : 4,      # 服务停止
            self.CMD.UNLOAD_SERVICE.name        : 5,      # 服务卸载
            self.CMD.REPORT_LOG.name            : 6,      # 记录日志
            self.CMD.REPORT_ALARM.name          : 7,      # 上报告警
            self.CMD.BACKUP_FILE.name           : 8,      # 备份文件

            self.CMD.REGISTER_ANSWER.name       : 1000,   # 注册服务
            self.CMD.HAND_SHAKE_ANSWER.name     : 1001,   # 握手命令
            self.CMD.LOAD_SERVICE_ANSWER.name   : 1002,   # 服务加载
            self.CMD.START_SERVICE_ANSWER.name  : 1003,   # 服务启动
            self.CMD.STOP_SERVICE_ANSWER.name   : 1004,   # 服务停止
            self.CMD.UNLOAD_SERVICE_ANSWER.name : 1005,   # 服务卸载
            self.CMD.REPORT_LOG_ANSWER.name     : 1006,   # 记录日志
            self.CMD.REPORT_ALARM_ANSWER.name   : 1007,   # 上报告警
            self.CMD.BACKUP_FILE_ANSWER.name    : 1008,   # 备份文件

            self.CMD.UNKNOWN_CMD.name           : -1     # 未知命令
        }
        return indexDict.get(fieldName.name, None)

    '''
    定义消息体优先级
    '''
    class Priority(Enum):
        HEIGHT                  = 0      # 高优先级消息
        MIDDLE                  = 1      # 中优先级消息，默认均应为中
        LOW                     = 2      # 低优先级消息
        UNKNOWN_PRIORITY        = -1     # 未知优先级
    '''
    将消息优先级枚举值转换为整数值
    '''
    def priorityIndex(self, fieldName):
        indexDict = {
            self.Priority.HEIGHT.name : 0,  # 高优先级消息
            self.Priority.MIDDLE.name : 1,  # 中优先级消息，默认均应为中
            self.Priority.LOW.name    : 2,  # 低优先级消息
            self.Priority.UNKNOWN_PRIORITY.name : -1  # 未知优先级
        }
        return indexDict.get(fieldName.name, None)

    '''
    定义消息定长字段大小，便于网络收发时明确缓冲区大小
    '''
    def FieldObjSize(self, fieldName):
        fieldSizeDict = {
            self.srcServiceID.name : 4,
            self.dstServiceID.name : 4,
            self.cmd.name          : 4,
            self.priority.name     : 4
        }
        return fieldSizeDict.get(fieldName.name, None)

    '''
    获得消息头长度
    '''
    def getMsgHeadLen(self):
        msgHeadLen = self.FieldObjSize(self.srcServiceID) \
                     + self.FieldObjSize(self.dstServiceID) \
                     + self.FieldObjSize(self.cmd) \
                     + self.FieldObjSize(self.priority) \
                     + 4                                     # msgLen 长度
        return msgHeadLen

    '''
    将MsgDefine对象转换为2进制流，以便通过socket发送到远端
    '''
    def obj2Bytes(self, msg):
        formatStr = '!iiiii{}s'.format(msg.msgLen)   # 消息头序列化描述字符串
        bytesData = struct.pack(formatStr, \
                           self.serviceIndex(msg.srcServiceID), \
                           self.serviceIndex(msg.dstServiceID), \
                           self.cmdIndex(msg.cmd), \
                           self.priorityIndex(msg.priority), \
                           msg.msgLen, \
                           msg.content.encode("utf-8"))
        return bytesData

    '''
    将2进制流转化为MsgDefine对象，以便python程序使用
    msgHead: 二进制的消息头，为网络收到的数据
    msgPayload： 二进制消息体，为网络收到的数据
    '''
    def bytes2Obj(self, msgHead, msgPayload):
        formatStr = '!iiiii'    # 消息头序列化描述字符串
        srcServiceID, dstServiceID, cmd, priority, msgLen = struct.unpack(formatStr, msgHead)
        self.srcServiceID = MsgDefine.ServiceDefine(srcServiceID)
        self.dstServiceID = MsgDefine.ServiceDefine(dstServiceID)
        self.cmd = MsgDefine.CMD(cmd)
        self.priority = MsgDefine.Priority(priority)
        self.msgLen = msgLen
        self.content = ''.join(map(chr, msgPayload))
