package com.wslint.wisdomreport;

/**
 * 作者：吴昌议
 * 日期：2018-12-1
 * 1.消息头文件定义
 *      MsgDefine: 消息定义
 *      ServiceDefine： 服务定义
 *      CMD：命令定义
 *      Priority：消息优先级定义
 * 2.通过最原始流结构传输数据，以便跨平台、跨语言可以交互
 * 3.提供流结构和JSON结构互转功能，便于使用
 */
public class MsgDefine {

    public MsgDefine() {
        srcServiceID = ServiceDefine.UNKNOWN_SERVICE;
        dstServiceID = ServiceDefine.UNKNOWN_SERVICE;
        cmd          = CMD.UNKNOWN_CMD;
        priority     = Priority.MIDDLE;
        msgLen       = 0;
    }
    /**
     *  定义消息结构
     */
    private ServiceDefine   srcServiceID;
    private ServiceDefine   dstServiceID;
    private CMD             cmd;
    private Priority        priority;
    private int             msgLen;
    private byte[]          content;

    /**
     * 定义服务ID
     */
    public enum ServiceDefine {
        MSG_SERVICE(0),             // 消息中心
        CHECK_RECORD_DB_SERVICE(1), // 检验记录数据保存到数据库服务
        BACKUP_SERVICE(2),          // 备份服务
        ALARM_SERVICE(3),           // 告警服务
        LOG_SERVICE(4),             // 日志服务
        SHOWCASE_SERVICE(99),       // 示例服务
        UNKNOWN_SERVICE(-1);        // 未知模块

        private int value = 0;
        private ServiceDefine(int value){
            this.value = value;
        }
        public static ServiceDefine valueOf(int value) {
            switch(value) {
                case 0:
                    return MSG_SERVICE;
                case 1:
                    return CHECK_RECORD_DB_SERVICE;
                case 2:
                    return BACKUP_SERVICE;
                case 3:
                    return ALARM_SERVICE;
                case 4:
                    return LOG_SERVICE;
                case 99:
                    return SHOWCASE_SERVICE;
                default:
                    return UNKNOWN_SERVICE;
            }
        }

        public static int index(ServiceDefine serviceID) {
            switch(serviceID) {
                case MSG_SERVICE:
                    return 0;
                case CHECK_RECORD_DB_SERVICE:
                    return 1;
                case BACKUP_SERVICE:
                    return 2;
                case ALARM_SERVICE:
                    return 3;
                case LOG_SERVICE:
                    return 4;
                case SHOWCASE_SERVICE:
                    return 99;
                case UNKNOWN_SERVICE:
                    return -1;
                default:
                    return -1;
            }
        }
    }

    /**
     * 定义命令字
     */
    public enum CMD {
        REGISTER(0),        // 注册服务
        HAND_SHAKE(1),      // 握手命令
        LOAD_SERVICE(2),    // 服务加载
        START_SERVICE(3),   // 服务启动
        STOP_SERVICE(4),    // 服务停止
        UNLOAD_SERVICE(5),  // 服务卸载
        REPORT_LOG(6),      // 记录日志
        REPORT_ALARM(7),    // 上报告警
        BACKUP_FILE(8),     // 备份文件


        REGISTER_ANSWER(1000),        // 注册服务
        HAND_SHAKE_ANSWER(1001),      // 握手命令
        LOAD_SERVICE_ANSWER(1002),    // 服务加载
        START_SERVICE_ANSWER(1003),   // 服务启动
        STOP_SERVICE_ANSWER(1004),    // 服务停止
        UNLOAD_SERVICE_ANSWER(1005),  // 服务卸载
        REPORT_LOG_ANSWER(1006),      // 记录日志
        REPORT_ALARM_ANSWER(1007),    // 上报告警
        BACKUP_FILE_ANSWER(1008),     // 备份文件

        UNKNOWN_CMD(-1);    // 未知命令

        private int value = 0;
        private CMD(int value){
            this.value = value;
        }

        public static CMD valueOf(int value) {
            switch(value) {
                case 0:
                    return REGISTER;
                case 1:
                    return HAND_SHAKE;
                case 2:
                    return LOAD_SERVICE;
                case 3:
                    return START_SERVICE;
                case 4:
                    return STOP_SERVICE;
                case 5:
                    return UNLOAD_SERVICE;
                case 6:
                    return REPORT_LOG;
                case 7:
                    return REPORT_ALARM;
                case 8:
                    return BACKUP_FILE;

                case 1000:
                    return REGISTER_ANSWER;
                case 1001:
                    return HAND_SHAKE_ANSWER;
                case 1002:
                    return LOAD_SERVICE_ANSWER;
                case 1003:
                    return START_SERVICE_ANSWER;
                case 1004:
                    return STOP_SERVICE_ANSWER;
                case 1005:
                    return UNLOAD_SERVICE_ANSWER;
                case 1006:
                    return REPORT_LOG_ANSWER;
                case 1007:
                    return REPORT_ALARM_ANSWER;
                case 1008:
                    return BACKUP_FILE_ANSWER;

                default:
                    return UNKNOWN_CMD;
            }
        }

        public static int index(CMD cmd) {
            switch (cmd) {
                case REGISTER:
                    return 0;
                case HAND_SHAKE:
                    return 1;
                case LOAD_SERVICE:
                    return 2;
                case START_SERVICE:
                    return 3;
                case STOP_SERVICE:
                    return 4;
                case UNLOAD_SERVICE:
                    return 5;
                case REPORT_LOG:
                    return 6;
                case REPORT_ALARM:
                    return 7;
                case BACKUP_FILE:
                    return 8;

                case REGISTER_ANSWER:
                    return 1000;
                case HAND_SHAKE_ANSWER:
                    return 1001;
                case LOAD_SERVICE_ANSWER:
                    return 1002;
                case START_SERVICE_ANSWER:
                    return 1003;
                case STOP_SERVICE_ANSWER:
                    return 1004;
                case UNLOAD_SERVICE_ANSWER:
                    return 1005;
                case REPORT_LOG_ANSWER:
                    return 1006;
                case REPORT_ALARM_ANSWER:
                    return 1007;
                case BACKUP_FILE_ANSWER:
                    return 1008;

                case UNKNOWN_CMD:
                    return -1;
                default:
                    return -1;
            }
        }
    }

    /**
     *  定义消息体优先级
     */
    public enum Priority {
        HEIGHT(0),  //高优先级消息
        MIDDLE(1),  //中优先级消息，默认均应为中
        LOW(2),     //低优先级消息
        UNKNOWN_PRIORITY(-1); // 未知优先级

        private int value = 0;
        private Priority(int value){
            this.value = value;
        }
        public static Priority valueOf(int value) {
            switch(value) {
                case 0:
                    return HEIGHT;
                case 1:
                    return MIDDLE;
                case 2:
                    return LOW;
                default:
                    return UNKNOWN_PRIORITY;
            }
        }

        public static int index(Priority priority) {
            switch (priority) {
                case HEIGHT:
                    return 0;
                case MIDDLE:
                    return 1;
                case LOW:
                    return 2;
                case UNKNOWN_PRIORITY:
                    return -1;
                default:
                    return -1;
            }
        }
    }

    /**
     * 得到各个裸数据大小，以便socket接收固定大小的数据
     */
    //private static Instrumentation inst;
    public int getSrcServiceIDObjSize(){
        return 4; //(int)inst.getObjectSize(srcServiceID);
    }
    public int getDstServiceIDObjSize(){
        return 4; //(int)inst.getObjectSize(dstServiceID);
    }
    public int getCMDObjSize(){
        return 4; //(int)inst.getObjectSize(cmd);
    }
    public int getPriorityObjSize(){
        return 4; //(int)inst.getObjectSize(priority);
    }
    public int getMsgLenObjSize(){
        return 4; //(int)inst.getObjectSize(msgLen);
    }

    /**
     * 设置裸数据消息体
     * 设置消息源服务ID
     */
    public void setSrcServiceID(ServiceDefine srcServiceID){
        this.srcServiceID = srcServiceID;
    }

    /**
     * 设置裸数据消息体
     * 设置消息目标服务ID
     */
    public void setDstServiceID(ServiceDefine dstServiceID){
        this.dstServiceID = dstServiceID;
    }

    /**
     * 设置裸数据消息体
     * 设置命令ID
     */
    public void setCMD(CMD cmd){
        this.cmd = cmd;
    }

    /**
     * 设置裸数据消息体
     * 设置优先级
     */
    public void setPriority(Priority priority){
        this.priority = priority;
    }
    /**
     * 设置裸数据消息体
     * 设置消息长度
     */
    public void setMsgLen(int msgLen){
        this.msgLen = msgLen;
    }

    /**
     * 设置裸数据消息体
     * 设置消息内容
     */
    public void setContent(byte[] content){
        this.content = content;
    }

    /**
     * 得到各个裸数据
     */
    public ServiceDefine getSrcServiceID(){
        return srcServiceID;
    }
    public ServiceDefine getDstServiceID(){
        return dstServiceID;
    }
    public CMD getCMD(){
        return cmd;
    }
    public Priority getPriority(){
        return priority;
    }
    public int getMsgLen(){
        return msgLen;
    }
    public byte[] getContent(){
        return content;
    }


    /**
     * 通过json格式设在消息体
     * 由于只能通过裸数据发送，将json转换为裸数据消息体
     */


    /**
     * 将裸数据转为后json结构，以便以json形式使用
     */
}
