package com.wslint.wisdomreport.exception;

/**
 * 工作流异常
 *
 * @author yuxr
 * @since 2018/11/27 15:09
 */
public class WorkflowException extends Exception {
  public WorkflowException() {
    super();
  }

  public WorkflowException(String message) {
    super(message);
  }

  public WorkflowException(String message, Throwable cause) {
    super(message, cause);
  }

  public WorkflowException(Throwable cause) {
    super(cause);
  }
}
