package com.wslint.wisdomreport.comm;

import com.wslint.wisdomreport.MsgDefine;
import com.wslint.wisdomreport.queue.LockFreeQueue;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


public class ReactorClient implements Runnable{
    private final Selector selector;
    private SocketHandler socketHandler;
    MsgDefine.ServiceDefine serviceID;
    private LockFreeQueue msgQueue;

    public ReactorClient(String serverIP, int serverPort, MsgDefine.ServiceDefine serviceID, LockFreeQueue msgQueue) throws IOException {

        this.serviceID = serviceID;
        this.msgQueue = msgQueue;

        //获取socket通道
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        //获得通道管理器
        selector=Selector.open();
        System.out.println("connect server:"+serverIP);
        channel.connect(new InetSocketAddress(serverIP, serverPort));
        //为该通道注册SelectionKey.OP_CONNECT事件
        channel.register(selector, SelectionKey.OP_CONNECT);
    }


    /**
     * 发送数据
     * @param msg
     * @return
     */
    public int SendMsg(MsgDefine msg) {
        return socketHandler.SendMsg(msg);
    }

    public void run(){
        try {

            while (true) {
                //选择注册过的io操作的事件(第一次为SelectionKey.OP_CONNECT)
                selector.select();

                Set<SelectionKey> selectionKeys= selector.selectedKeys();
                Iterator<SelectionKey> it=selectionKeys.iterator();
                //Selector如果发现channel有OP_CONNECT事件发生，下列遍历就会进行。
                while(it.hasNext()){
                    SelectionKey key=it.next();
                    if (key.isConnectable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        if (channel.isConnectionPending()) {
                            //如果正在连接，则完成连接
                            //是否可能finishConnect也不能保证完成链接的情况？？？
                            channel.finishConnect();
                        }

                        //注册数据收发的sockethandler
                        socketHandler = new SocketHandler(selector, channel, msgQueue, null);
                        //向消息中心发送服务注册消息
                        MsgDefine msg = new MsgDefine();
                        msg.setSrcServiceID(serviceID);
                        msg.setDstServiceID(MsgDefine.ServiceDefine.MSG_SERVICE);
                        msg.setCMD(MsgDefine.CMD.REGISTER);
                        msg.setPriority(MsgDefine.Priority.HEIGHT);
                        String msgInfo = "request register service.";
                        msg.setMsgLen(msgInfo.length());
                        msg.setContent(msgInfo.getBytes());
                        SendMsg(msg);

                        MsgDefine answerMsg = socketHandler.extractMsg();
                        String answerInfo = new String(answerMsg.getContent());
                        System.out.println("service:"+serviceID+" receive register answer, CMD:"+answerMsg.getCMD()+ ".  message: "+answerInfo);

                    }else if(key.isReadable()){
                        socketHandler.run();
                    }
                    it.remove();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
