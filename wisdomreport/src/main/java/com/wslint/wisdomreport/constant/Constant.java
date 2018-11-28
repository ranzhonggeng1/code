package com.wslint.wisdomreport.constant;

/**
 * 系统常量
 *
 * @author yuxr
 * @since 2018/8/14 12:03
 */
public final class Constant {

  /** 返回成功msg */
  public static final String RETURN_MESSAGE_SUCCESS = "操作成功";

  /** 返回失败msg */
  public static final String RETURN_MESSAGE_FAILURE = "操作失败";

  /** 返回失败msg */
  public static final String RETURN_MESSAGE_ERROR = "系统异常";

  /** 返回信息code */
  public static final int RETURN_CODE_WARN = 100;

  /** 返回成功code */
  public static final int RETURN_CODE_SUCCESS = 200;

  /** 返回失败code */
  public static final int RETURN_CODE_FAILURE = -1;

  /** 返回失败code */
  public static final int RETURN_CODE_ERROR = 500;

  /** 返回登录失效code */
  public static final int RETURN_CODE_LOGIN_INVALID = -2;

  /** 字符串code */
  public static final String STR_CODE = "code";

  /** 字符串msg */
  public static final String STR_MSG = "msg";

  /** 字符串msg */
  public static final String STR_ERROR_MSG = "errorMsg";

  /** 字符串data */
  public static final String STR_DATA = "data";

  /** 数字0 */
  public static final int NUM_0 = 0;
  /** 数字1 */
  public static final int NUM_1 = 1;
  /** 数字2 */
  public static final int NUM_2 = 2;
  /** 数字3 */
  public static final int NUM_3 = 3;
  /** 数字4 */
  public static final int NUM_4 = 4;
  /** 数字5 */
  public static final int NUM_5 = 5;
  /** 数字6 */
  public static final int NUM_6 = 6;
  /** 数字7 */
  public static final int NUM_7 = 7;
  /** 数字12 */
  public static final int NUM_12 = 12;
  /** 数字16 */
  public static final int NUM_16 = 16;
  /** 数字100 */
  public static final int NUM_100 = 100;

  /** 常量 SIR_DEFAULT_R_AP */
  public static final String SIR_DEFAULT_R_AP = "wsladmin";
  /** 常量 SIR_DEFAULT_W_AP */
  public static final String SIR_DEFAULT_W_AP = "***";
  /** 常量 STR_ADMIN */
  public static final String STR_ADMIN = "admin";
  /** 常量 STR_ADMIN_NAME */
  public static final String STR_ADMIN_NAME = "系统管理员";

  public static final Long ID_0 = 0L;
  public static final Long ID_NEGATIVE_1 = -1L;
  public static final String STR_EMPTY = "";

  /** 工作流全部数据 */
  public static final int WORK_FLOW_STATUS_ALL = 0;
  /** 工作流实验节点 */
  public static final int WORK_FLOW_STATUS_EXPERIMENT = 1;
  /** 工作流审核节点 */
  public static final int WORK_FLOW_STATUS_REVIEW = 2;
  /** 工作流审核未通过节点 */
  public static final int WORK_FLOW_STATUS_REVIEW_FAILED = 3;
  /** 工作流结束节点 */
  public static final int WORK_FLOW_STATUS_END = 4;

  /** 字符串分割符 */
  public static final String STR_SPLIT = "-";

  public static final String STR_0 = "0";
  public static final String STR_ID = "id";
  public static final String STR_UPDATE = "update";
  public static final String STR_VERSION = "version";
  public static final String STR_MEDICINE_WORD_ID_MAX = "idmax";
  /** 初始化权限个数 */
  public static final int INIT_PERMISSION = 16;

  public static final int ADMIN_PERMISSION = 1;

  public static final String FILE_EX_HTML = ".html";
  public static final String STR_RETURN_IMG_PATH = "returnImgPath";
  public static final String STR_FILE_NAME = "fileName";
  public static final String PARAM_ROOT_PATH = "file_root_path";
  public static final String PARAM_DEFAULT_PASSWORD = "default_password";
  public static final int NUM_FILE_NAME_LENGTH = 15;
  public static final String STR_PATH = "path";
  public static final String STR_URL = "url";
  public static final String PROPERTY_ID = "id";
  public static final String FIELD_GET = "get";
  public static final Long ADMIN_ID = 0L;
  public static final String STR_FIRST_CLASS_ID = "firstClassId";
  public static final String STR_SECOND_CLASS_ID = "secondClassId";
  public static final String STR_FIRST_CLASS_LIST = "firstClassList";
  public static final String STR_SECOND_CLASS_LIST = "secondClassList";
  public static final String FILE_WORD_VERSION_FIRST_ID = "1";
}
