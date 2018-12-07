package com.wslint.wisdomreport.comm;

import com.wslint.wisdomreport.MsgDefine;

import java.util.HashMap;
import java.util.Map;

public class RouteTable {

    Map<MsgDefine.ServiceDefine, SocketHandler> routeMap;

    public RouteTable() {
        routeMap = new HashMap<MsgDefine.ServiceDefine, SocketHandler>();
    }

    /**
     * 注册路由，多个channel、MsgCenter都可能访问路由表，必须对路由表的访问加锁
     */
    public void registerRoute(MsgDefine.ServiceDefine serviceDefineID, SocketHandler socketHandler ) {
        synchronized (routeMap) {
            routeMap.put(serviceDefineID,socketHandler);
        }
    }

    /*
    注销路由，多个channel、MsgCenter都可能访问路由表，必须对路由表的访问加锁
     */
    public void unregisterRoute(MsgDefine.ServiceDefine serviceDefineID) {
        synchronized (routeMap) {
            routeMap.remove(serviceDefineID);
        }
    }

    public SocketHandler lookupSocketHandler(MsgDefine.ServiceDefine serviceDefineID) {
        synchronized (routeMap) {
            return routeMap.get(serviceDefineID);
        }
    }
}

