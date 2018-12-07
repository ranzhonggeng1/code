package com.wslint.wisdomreport.comm;

import com.wslint.wisdomreport.queue.LockFreeQueue;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 反应器模式
 * 用于解决多用户访问并发问题
 *
 * @author wuchangyi
 * time 2018-11-29
 */
public class ReactorServer implements Runnable {
    protected final Selector selector;
    protected final ServerSocketChannel serverSocketChannel;
    protected final LockFreeQueue msgQueue;
    protected RouteTable routeTable;

    public ReactorServer(int port, LockFreeQueue msgQueue, RouteTable routeTable) throws IOException{

        // 创建侦听channel
        selector=Selector.open();
        serverSocketChannel=ServerSocketChannel.open();
        //InetSocketAddress inetSocketAddress=new InetSocketAddress(InetAddress.getLocalHost(),port);
        InetSocketAddress inetSocketAddress=new InetSocketAddress("127.0.0.1",port);
        serverSocketChannel.socket().bind(inetSocketAddress);
        serverSocketChannel.configureBlocking(false);

        // 注册消息队列
        this.msgQueue = msgQueue;

        // 注册路由表
        this.routeTable = routeTable;

        //向selector注册该channel
        SelectionKey selectionKey=serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //利用selectionKey的attache功能绑定Acceptor 如果有事情，触发Acceptor
        selectionKey.attach(new Acceptor(this));

        System.out.println("server listen:"+InetAddress.getLocalHost()+"  prot:"+port);
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                selector.select();
                Set<SelectionKey> selectionKeys= selector.selectedKeys();
                Iterator<SelectionKey> it=selectionKeys.iterator();
                //Selector如果发现channel有OP_ACCEPT或READ事件发生，下列遍历就会进行。
                while(it.hasNext()){
                    //来一个事件 第一次触发一个accepter线程
                    //以后触发SocketReadHandler
                    SelectionKey selectionKey=it.next();

                    dispatch(selectionKey);
                    it.remove();
                    //selectionKeys.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 运行Acceptor或SocketReadHandler
     * @param key
     */
    void dispatch(SelectionKey key) {
        Runnable r = (Runnable)(key.attachment());
        if (r != null){
            r.run();
        }
    }
}
