import com.wslint.wisdomreport.MsgDefine;
import com.wslint.wisdomreport.comm.ReactorServer;
import com.wslint.wisdomreport.comm.RouteTable;
import com.wslint.wisdomreport.comm.SocketHandler;
import com.wslint.wisdomreport.queue.LockFreeQueue;

import java.io.IOException;

public class MsgCenter implements  Runnable {

    private static LockFreeQueue msgQueue;
    private ReactorServer reactorServer;
    private Thread scThread;
    public RouteTable routeTable;

    public MsgCenter(){
        // 创建消息队列
        msgQueue = new LockFreeQueue(1000000);
        // 创建路由表
        routeTable = new RouteTable();

        try {

            reactorServer = new ReactorServer(10023, msgQueue, routeTable);
            scThread = new Thread(reactorServer);
            scThread.start();

            //reactorServer.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 循环处理队列消息
     */
    public void run(){

        while (true){
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
     * 消息处理函数
     * @param msg
     */
    private void handleEvents(MsgDefine msg){
        if (null == msg) {
            return;
        }

        if (MsgDefine.ServiceDefine.MSG_SERVICE != msg.getDstServiceID()) {
            // 将消息路由到对应的服务
            MsgDefine.ServiceDefine dstServiceID = msg.getDstServiceID();
            SocketHandler dstSocketHandler = routeTable.lookupSocketHandler(dstServiceID);
            if (null != dstSocketHandler) {
                dstSocketHandler.SendMsg(msg);
            }
            else {
                // 目的服务未在消息中心注册，丢弃该消息
                // 不提供保留该消息，待注册后再发送功能，避免消息队列溢出
                //msgQueue.enqueue(msg);
            }
        }
        else {
            String msgInfo = new String(msg.getContent());
            System.out.println("receive error message, from " + msg.getSrcServiceID() + " CMD:" + msg.getCMD() + "content:" + msgInfo);
        }

    }

    /**
     * 定时器函数
     */
    private void OnTimer() {

    }

    /**
     * 发送数据
     * @param msg
     * @return
     */
    private int SendMsg(MsgDefine msg) {
        return -1;
    }



    public static void main(String[] args){
        MsgCenter msgCenter= new MsgCenter();
        msgCenter.run();
    }

}
