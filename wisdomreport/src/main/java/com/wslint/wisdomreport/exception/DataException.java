package com.wslint.wisdomreport.exception;

/**
 * 数据异常
 *
 * @author yuxr
 * @since 2018/11/27 15:23
 */
public class DataException extends Exception {
  public DataException() {
    super();
  }

  public DataException(String message) {
    super(message);
  }

  public DataException(String message, Throwable cause) {
    super(message, cause);
  }

  public DataException(Throwable cause) {
    super(cause);
  }
}
