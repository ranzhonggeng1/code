'''
消息队列
作者：吴昌议
日期：20181204
说明： 保证同一个多线程安全
'''
# -*- coding: utf-8 -*-
import queue
import threading


class MsgQueue:
    def __init__(self):
        self.msgQueue = queue.Queue()  #默认提供无限制队列
        self.msgQueueLock = threading.Lock()

    '''
    消息队列入队    
    成功：返回True
    失败：返回False
    '''
    def enqueue(self, msg):
        self.msgQueueLock.acquire()
        try:
            self.msgQueue.put(msg)
            self.msgQueueLock.release()
            return True
        except queue.Full:
            self.msgQueueLock.release()
            print("message queue is full, enqueue failed.")
            return False


    '''
    消息队列出队
    成功：返回消息
    失败：返回None
    '''
    def dequeuc(self):
        self.msgQueueLock.acquire()
        if self.msgQueue.qsize() > 0 :
            try:
                msg =  self.msgQueue.get()
                self.msgQueueLock.release()
                return msg
            except queue.Empty:
                self.msgQueueLock.release()
                return None
        else:
            self.msgQueueLock.release()
            return None
