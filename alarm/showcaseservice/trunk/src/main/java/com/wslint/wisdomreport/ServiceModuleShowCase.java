package com.wslint.wisdomreport;

import com.wslint.wisdomreport.MsgDefine;
import com.wslint.wisdomreport.TaskBase;

/**
 * 基于模块框架的业务模块样例
 * 1、需要实现为服务加载4个接口：loadService、unloadService、startService、stopService
 * 2、实现消息处理函数handleEvents
 */
public class ServiceModuleShowCase extends TaskBase {

    public ServiceModuleShowCase() {
        //明确本服务ID
        super(MsgDefine.ServiceDefine.SHOWCASE_SERVICE);
    }

    /**
     * 模块加载: 成功返回1,失败返回0
     */
    public int loadService(){
        // 模块加载代码
        return 1;
    }

    /**
     * 模块卸载: 成功返回1,失败返回0
     */
    public int unloadService(){
        // 模块卸载代码
        return 1;
    }

    /**
     * 模块启动: 成功返回1,失败返回0
     */
    public int startService(){
        // 模块启动代码
        return 1;
    }

    /**
     * 模块停止: 成功返回1,失败返回0
     */
    public int stopService(){
        // 模块停止代码
        return 1;
    }

    /**
     * 消息事件处理器
     * @param msg
     */
    protected void handleEvents(MsgDefine msg){
        switch(msg.getCMD()) {
            case REPORT_ALARM:
                // 消息处理函数
                //String msgInfo = new String(msg.getContent());
                //System.out.println("Handle msg: from "+msg.getSrcServiceID()+ ". cmd:"+msg.getCMD()+". content:"+msgInfo);
                // MsgDefine xxmsg = new MsgDefine();
                // 构造msg
                // SendMsg(xxmsg);
                break;
            case REPORT_LOG:
                //String msgInfo = new String(msg.getContent());
                //System.out.println("Handle msg: from "+msg.getSrcServiceID()+ ". cmd:"+msg.getCMD()+". content:"+msgInfo);
                //SendMsg()
                break;
            case BACKUP_FILE:
                String msgInfo = new String(msg.getContent());
                System.out.println("Handle msg: from "+msg.getSrcServiceID()+ ". cmd:"+msg.getCMD()+". content:"+msgInfo);
                break;
            default:
                //String msgInfo = new String(msg.getContent());
                //System.out.println("Unknown msg: from "+msg.getSrcServiceID()+ ". cmd:"+msg.getCMD()+". content:"+msgInfo);
                break;
        }
    }

    /**
     * 消息处理函数
     */
    public void do_backup() {
        MsgDefine msg = new MsgDefine();
        msg.setSrcServiceID(serviceID);
        msg.setDstServiceID(MsgDefine.ServiceDefine.SHOWCASE_SERVICE);
        msg.setCMD(MsgDefine.CMD.BACKUP_FILE);
        msg.setPriority(MsgDefine.Priority.MIDDLE);
        String msgInfo = "\\opt\\WisdomReport\\xxx\\a.jpg";
        msg.setMsgLen(msgInfo.length());
        msg.setContent(msgInfo.getBytes());
        SendMsg(msg);
    }

    /**
     * 业务入口函数
     */
    public void xxxFunc() {

        // 业务代码
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        do_backup();

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){

        //创建并运行模块框架
        ServiceModuleShowCase showCaseModule= new ServiceModuleShowCase();
        Thread workThread = new Thread(showCaseModule);
        workThread.start();

        //执行框架之外的业务代码
        showCaseModule.xxxFunc();
    }
}