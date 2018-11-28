package com.wslint.wisdomreport.config;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.exception.DataException;
import com.wslint.wisdomreport.exception.WorkflowException;
import com.wslint.wisdomreport.utils.ReturnUtils;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 *
 * @author yuxr
 * @date 2018/9/24
 */
@RestControllerAdvice
public class WslGlobalExceptionHandler {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(WslGlobalExceptionHandler.class);

  @ExceptionHandler(value = WorkflowException.class)
  public Map<String, Object> WorkflowErrorHandler(HttpServletRequest req, Exception e) {
    LOGGER.error("------------------ {}！ ------------------", e.getMessage());
    return ReturnUtils.failureMap(e.getMessage());
  }

  @ExceptionHandler(value = DataException.class)
  public Map<String, Object> DataErrorHandler(HttpServletRequest req, Exception e) {
    LOGGER.error("------------------ {}！ ------------------", e.getMessage());
    return ReturnUtils.failureMap(e.getMessage());
  }

  @ExceptionHandler(value = Exception.class)
  public Map<String, Object> defaultErrorHandler(HttpServletRequest req, Exception e) {
    LOGGER.error("------------------ 系统异常！ - {} ------------------", e.getMessage());
    return ReturnUtils.errorMap(Constant.RETURN_MESSAGE_ERROR, e.getMessage());
  }
}
