package com.wslint.wisdomreport.constant;

/**
 * 返回常量
 *
 * @author yuxr
 * @since 2018/11/9 15:26
 */
public class ReturnConstant {

  public static final int WORKFLOW_SUCCESS = 1;
  public static final int WORKFLOW_FAILED = -1;
  public static final int WORKFLOW_FAILED_RIGHT_ERROR = -2;
  public static final int WORKFLOW_FAILED_INSERT = -3;
  public static final int DATA_UPDATE_FAILED = -4;
  public static final int WORKFLOW_FAILED_BACKUP = -5;
  public static final int WORKFLOW_FAILED_UNION = -6;

  public static Long DATA_FAILED_EXISTS = -2L;
  public static final Long DATA_FAILED_BATCH_INSERT = -3L;
  public static final Long DATA_FAILED_CLASS_INSERT = -4L;
}
