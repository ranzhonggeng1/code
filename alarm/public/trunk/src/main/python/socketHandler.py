'''
socket通信客户端，实时提取消息并将消息入队，并提供消息发送功能
作者：吴昌议
日期：20181204
说明：
'''
# -*- coding: utf-8 -*-
import select
import socket
import threading

from msgDefine import MsgDefine

class SocketHandler:
    def __init__(self, serverIP, port, msgQueue):
        self.socketHandler = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.socketHandler.connect((serverIP, port))
        self.socketHandler.setblocking(True) # 设置为阻塞模式
        self.socketLock = threading.Lock()
        self.msgQueue = msgQueue

    '''
    创建一个线程接收消息
    '''
    def run(self):
        readThread = threading.Thread(target=self.socketRecvThread)
        readThread.setDaemon(True)
        readThread.start()

    '''
    消息接收线程
    '''
    def socketRecvThread(self):
        while True:
            if not self.socketHandler: # 如果socket关闭，退出
                 break
            '''
            使用select监听客户端，消息中心之外服务都是客户端，所以监听客户端，因为是客户端，我们只监听创建的一个socket就ok 
            第一个参数：要监听读事件列表
            第二个参数：要监听写事件列表 
            第三个参数：要监听异常事件列表 
            第四个参数：监听超时时间，默认永不超时。如果设置了超时时间，过了超时时间线程就不会阻塞在select方法上，会继续向下执行 
            返回参数：分别对应监听到的读事件列表，写事件列表，异常事件列表 
            '''
            rs, _, _ = select.select([self.socketHandler], [], [], 10)
            for r in rs: # 只监听读事件，所以只管读的返回句柄数组
                self.handleRequest()

    '''
    处理退出事件：清空资源
    '''
    def  handleClose(self):
        self.socketLock.acquire()
        self.socketHandler.close()
        self.socketHandler = None
        self.socketLock.release()


    '''
    提取1条消息，并将消息放入消息队列
    '''
    def handleRequest(self):
        self.msgQueue.enqueue(self.extractMsg())

    '''
    提取1条消息
    成功：返回消息
    失败：返回None，释放socket相关资源
    '''
    def extractMsg(self):
        self.socketLock.acquire()
        if not self.socketHandler:  # 这里需要判断下，因为有可能在select后到加锁之间socket被关闭了
            self.socketLock.release()
            return None

        try:

            msg = MsgDefine()

            # 提取消息头
            msgHeadBuf = b''
            msgHeadLen = msg.getMsgHeadLen()
            while (len(msgHeadBuf) < msgHeadLen):
                data = self.socketHandler.recv(msgHeadLen-len(msgHeadBuf))
                if not data:
                    break  #continue
                else:
                    msgHeadBuf += data

            # 提取消息体长度
            msgLen = int.from_bytes(msgHeadBuf[-4:], byteorder='big')

            # 提取消息体
            msgPayLoad = b''
            while (len(msgPayLoad) < msgLen):
                data = self.socketHandler.recv(msgLen-len(msgPayLoad))
                if not data:
                    break #continue
                else:
                    msgPayLoad += data

            # 将二进制流转换为python对象
            msg.bytes2Obj(msgHeadBuf, msgPayLoad)

            self.socketLock.release()

            return msg

        except socket.error as e:
            self.socketLock.release()
            self.handleClose()
            print("socket except: {}".format(e))
            return None

    '''
    发送1条消息
    成功：返回消息大小
    失败：返回-1, 释放socket相关资源
    '''
    def SendMsg(self, msg):

        try:
            bytesData = msg.obj2Bytes(msg)
            self.socketLock.acquire()
            self.socketHandler.sendall(bytesData) # 先将python对象转换为二进制流再发送
            self.socketLock.release()
            return len(bytesData)
        except socket.error as e:
            self.socketLock.release()
            self.handleClose()
            print("socket except: {}".format(e))
            return -1
