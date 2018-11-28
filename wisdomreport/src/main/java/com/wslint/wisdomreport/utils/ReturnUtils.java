package com.wslint.wisdomreport.utils;

import com.wslint.wisdomreport.constant.Constant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装返回数据的工具类
 *
 * @author yxur
 * @since 2018/9/10 11:52
 */
public class ReturnUtils {

  /**
   * 返回操作成功的数据集
   *
   * @param data 传入处理结果
   * @param message 返回信息描述
   * @return 操作结果
   */
  public static Map<String, Object> successMap(Object data, String message) {
    Map<String, Object> rtnMap = successMap(message);
    rtnMap.put(Constant.STR_DATA, data == null ? new ArrayList<>() : data);
    return rtnMap;
  }

  /**
   * 返回操作成功的数据集
   *
   * @param message 返回信息描述
   * @return 操作结果
   */
  public static Map<String, Object> successMap(String message) {
    return map(Constant.RETURN_CODE_SUCCESS, message);
  }

  /**
   * 返回操作失败的数据集
   *
   * @param message 返回信息描述
   * @return 操作结果
   */
  public static Map<String, Object> failureMap(String message) {
    return map(Constant.RETURN_CODE_FAILURE, message);
  }

  /**
   * 返回操作信息的数据集
   *
   * @param message 返回信息描述
   * @return 操作结果
   */
  public static Map<String, Object> wrongMap(String message) {
    return map(Constant.RETURN_CODE_WARN, message);
  }

  /**
   * 返回异常信息的数据集
   *
   * @param message 返回信息描述
   * @return 操作结果
   */
  public static Map<String, Object> errorMap(String message, String errorMsg) {
    Map<String, Object> rtnMap = errorMap(message);
    rtnMap.put(Constant.STR_ERROR_MSG, errorMsg);
    return rtnMap;
  }

  /**
   * 返回操作信息的数据集
   *
   * @param message 返回信息描述
   * @return 操作结果
   */
  public static Map<String, Object> errorMap(String message) {
    return map(Constant.RETURN_CODE_ERROR, message);
  }

  /**
   * 返回登录失效数据集
   *
   * @param message 返回信息描述
   * @return 登录失效数据
   */
  public static Map<String, Object> loginInvalidMap(String message) {
    return map(Constant.RETURN_CODE_LOGIN_INVALID, message);
  }
  /**
   * 返回数据集
   *
   * @param code 返回状态码
   * @param message 返回信息描述
   * @return 操作结果
   */
  private static Map<String, Object> map(int code, String message) {
    Map<String, Object> rtnMap = new HashMap<>(Constant.NUM_16);
    rtnMap.put(Constant.STR_CODE, code);
    rtnMap.put(Constant.STR_MSG, message);
    return rtnMap;
  }
}
