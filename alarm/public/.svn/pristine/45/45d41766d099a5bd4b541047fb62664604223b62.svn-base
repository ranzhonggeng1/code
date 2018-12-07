package com.wslint.wisdomreport.comm;

import com.wslint.wisdomreport.MsgDefine;
import com.wslint.wisdomreport.queue.LockFreeQueue;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;


public class SocketHandler implements Runnable{

    // socket数据收发对象
    private SocketChannel socketChannel;
    // 消息队列，用户保存socket受到的消息
    private final LockFreeQueue msgQueue;
    // reactor句柄，用于channel结束时清理环境
    SelectionKey selectionKey;

    // 路由表，如果本channel属于消息中心，用于注册和注销channel和本channel对端服务ID的关系
    RouteTable routeTable;

    // 本channel对端服务ID
    MsgDefine.ServiceDefine oppositeServiceID;

//    private static Instrumentation inst;
//    public static void premain(String agentArgs, Instrumentation instP) {
//        inst = instP;
//    }

    public SocketHandler(Selector selector, SocketChannel socketChannel, LockFreeQueue msgQueue, RouteTable routeTable) throws IOException {
        this.msgQueue = msgQueue;
        this.routeTable = routeTable;
        oppositeServiceID = MsgDefine.ServiceDefine.MSG_SERVICE;
        this.socketChannel=socketChannel;
        socketChannel.configureBlocking(false);

        selectionKey=socketChannel.register(selector, 0);

        //将SelectionKey绑定为本Handler 下一步有事件触发时，将调用本类的run方法。
        //参看dispatch(SelectionKey key)
        selectionKey.attach(this);

        //同时将SelectionKey标记为可读，以便读取。
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    /**
     * 处理读取数据
     */
    @Override
    public void run() {
        handleRequect();
    }

    /**
     *  处理socket关闭事件，释放资源，注销channel
     */
    private void handleClose() {

        // 如果本端属于消息中心，则需要注销路由
        // 条件1: 对端服务ID不是MSG_SERVICE
        // 条件2：路由表非空，
        if ((oppositeServiceID != MsgDefine.ServiceDefine.MSG_SERVICE) && (null!=routeTable)) {
            routeTable.unregisterRoute(oppositeServiceID);
        }
        // 通知reactor释放注册信息
        selectionKey.cancel();
        System.out.println("service:" +oppositeServiceID+" disconnected, release resource.");
    }

    /**
     * 处理消息：提取并将消息放入路由队列，如果对端管道关闭，释放本端管道
     */
    private void handleRequect(){

        //提取一条消息并处理
        MsgDefine msg = extractMsg();
        if (null == msg) {
            handleClose();
        } else {
            // 如果是消息中心，且收到服务注册请求（只有消息中心能受到注册请求），将本channel对象和对端服务ID绑定，
            // 并向MsgCenter注册，以便后续MsgCenter能路由消息
            if (MsgDefine.CMD.REGISTER == msg.getCMD()) {
                // 得到对端服务ID
                oppositeServiceID = msg.getSrcServiceID();
                // 将本channel和对端服务ID绑定并注册到路由表
                routeTable.registerRoute(oppositeServiceID, this);
                String requestInfo = new String(msg.getContent());
                System.out.println("service:"+oppositeServiceID+" registed. request message: "+requestInfo);

                //向对端服务发送服务注册成功消息
                MsgDefine anserMsg = new MsgDefine();
                anserMsg.setSrcServiceID(MsgDefine.ServiceDefine.MSG_SERVICE);
                anserMsg.setDstServiceID(oppositeServiceID);
                anserMsg.setCMD(MsgDefine.CMD.REGISTER_ANSWER);
                anserMsg.setPriority(MsgDefine.Priority.HEIGHT);
                String msgInfo = "register service success.";
                anserMsg.setMsgLen(msgInfo.length());
                anserMsg.setContent(msgInfo.getBytes());
                SendMsg(anserMsg);
            } else {
                // 将消息放入路由队列
                msgQueue.enqueue(msg);
            }
        }
    }


    /**
     * 从网络流提取1条消息
     * @return
     */
    public MsgDefine extractMsg() {
        /**
         * 获取一条消息, 按消息裸数据结构，依次获取数据
         */
        try {
            MsgDefine msg = new MsgDefine();
            ByteBuffer srcServiceIDBuf = ByteBuffer.allocate(msg.getSrcServiceIDObjSize());
            extractField(srcServiceIDBuf);
            msg.setSrcServiceID(MsgDefine.ServiceDefine.valueOf(srcServiceIDBuf.getInt()));

            ByteBuffer dstServiceIDBuf = ByteBuffer.allocate(msg.getDstServiceIDObjSize());
            extractField(dstServiceIDBuf);
            msg.setDstServiceID(MsgDefine.ServiceDefine.valueOf(dstServiceIDBuf.getInt()));

            ByteBuffer cmdBuf = ByteBuffer.allocate(msg.getCMDObjSize());
            extractField(cmdBuf);
            msg.setCMD(MsgDefine.CMD.valueOf(cmdBuf.getInt()));

            ByteBuffer priorityBuf = ByteBuffer.allocate(msg.getPriorityObjSize());
            extractField(priorityBuf);
            msg.setPriority(MsgDefine.Priority.valueOf(priorityBuf.getInt()));

            ByteBuffer msgLenBuf = ByteBuffer.allocate(msg.getMsgLenObjSize());
            extractField(msgLenBuf);
            msg.setMsgLen(msgLenBuf.getInt());

            ByteBuffer contentBuf = ByteBuffer.allocate(msg.getMsgLen());
            extractField(contentBuf);
            msg.setContent(contentBuf.array());

            return msg;
        } catch (IOException e) {
            return null;
        }


    }

    /**
     * 从字节流buffer获取1个域
     * @param inputBuffer: socket缓冲区
     */
    private void extractField(ByteBuffer inputBuffer) throws IOException {

        try {
            inputBuffer.clear();
            while (inputBuffer.hasRemaining()){
                if (-1 == socketChannel.read(inputBuffer)){
                    //"socket is disconnected";
                    throw new IOException("opposite channel is closed!");
                }
            }
            inputBuffer.flip();
        } catch (IOException e) {
            // 管道异常
            //e.printStackTrace();
            throw new IOException("opposite channel is closed!");
        }
    }

    /**
     * 发送消息
     * @param msg
     * @return -1：失败; 或实际发送的数据长度
     */
    public int SendMsg(MsgDefine msg) {

        //int msgLen = (int)inst.getObjectSize(msg);
        int msgLen = msg.getMsgLen()+20; // 对象大小获取方法还没搞清楚，此行代码需要重写
        ByteBuffer buffer = ByteBuffer.allocate(msgLen);
        buffer.clear();
        buffer.putInt(MsgDefine.ServiceDefine.index(msg.getSrcServiceID()));
        buffer.putInt(MsgDefine.ServiceDefine.index(msg.getDstServiceID()));
        buffer.putInt(MsgDefine.CMD.index(msg.getCMD()));
        buffer.putInt(MsgDefine.Priority.index(msg.getPriority()));
        buffer.putInt(msg.getMsgLen());
        buffer.put(msg.getContent());

        try {
            buffer.flip();
            while (buffer.hasRemaining()){
                if (-1 == socketChannel.write(buffer)){
                    //管道被关闭
                    return -1;
                }
            }
        } catch (IOException e) {
            // 管道异常
            //e.printStackTrace();
            handleClose();
        }

        return msgLen;
    }
}

