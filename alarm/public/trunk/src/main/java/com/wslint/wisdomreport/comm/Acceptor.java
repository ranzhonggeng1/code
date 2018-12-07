package com.wslint.wisdomreport.comm;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class Acceptor implements Runnable{
    private com.wslint.wisdomreport.comm.ReactorServer reactor;
    public Acceptor(com.wslint.wisdomreport.comm.ReactorServer reactor){
        this.reactor=reactor;
    }
    @Override
    public void run() {
        try {
            SocketChannel socketChannel=reactor.serverSocketChannel.accept();
            if(socketChannel!=null){
                //调用Handler来处理channel
                new SocketHandler(reactor.selector, socketChannel, reactor.msgQueue, reactor.routeTable);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
