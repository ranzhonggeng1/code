package com.wslint.wisdomreport.utils;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.wslint.wisdomreport.config.SnowflakeIdWorker;
import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.domain.dto.user.User;
import jodd.bean.BeanUtil;
import org.apache.shiro.SecurityUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 公共工具类
 *
 * @author yuxr
 * @since 2018/8/13 17:11
 */
public final class CommonUtils {

  // todo 之后通过配置的方式配置workerID和datacenterId，做到不同服务生成不同主键id，便于分布式服务部署中数据库的数据同步

  private static SnowflakeIdWorker SNOWFLAKE_ID_WORKER =
      new SnowflakeIdWorker(Constant.NUM_1, Constant.NUM_1);

  public static Long getNextId() {
    return SNOWFLAKE_ID_WORKER.nextId();
  }

  /**
   * 生成app端要求的返回数据
   *
   * @param code 返回编码
   * @param msg 返回信息
   * @param data 返回数据
   * @return 返回数据体map{code:xxx,msg:xxx,data:{}}
   */
  public static Map<String, Object> getReturnMessage(int code, String msg, List<Map> data) {
    Map<String, Object> rtnMap = new HashMap<>(Constant.NUM_16);
    rtnMap.put(Constant.STR_CODE, code);
    rtnMap.put(Constant.STR_MSG, msg);
    rtnMap.put(Constant.STR_DATA, data);
    return rtnMap;
  }

  /**
   * 判断字符串是否为null
   *
   * @param string 字符串
   * @return 返回是否为null
   */
  public static boolean isNullOrEmpty(String string) {
    return string == null || "".equals(string);
  }

  /**
   * 判断列表是否为null
   *
   * @param list 列表
   * @return 返回是否为null
   */
  public static boolean isNullOrEmpty(List list) {
    return list == null || list.isEmpty();
  }

  /**
   * 判断Map是否为null
   *
   * @param map 列表
   * @return 返回是否为null
   */
  public static boolean isNullOrEmpty(Map map) {
    return map == null || map.isEmpty();
  }

  /** 用于获取结果集的映射关系 */
  public static String getResultsStr(Class origin) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("@Results({\n");
    for (Field field : origin.getDeclaredFields()) {
      String property = field.getName();
      // 映射关系：对象属性(驼峰)->数据库字段(下划线)
      String column =
          new PropertyNamingStrategy.SnakeCaseStrategy().translate(field.getName()).toUpperCase();
      stringBuilder.append(
          String.format("@Result(property = \"%s\", column = \"%s\"),\n", property, column));
    }
    stringBuilder.append("})");
    return stringBuilder.toString();
  }

  /**
   * 将bena转换为Map
   *
   * @param bean bean
   * @return 返回一个转换好的map
   */
  public static Map<String, Object> beanToMap(Object bean) {
    if (bean == null) {
      return null;
    }
    Map<String, Object> map = new HashMap<>(Constant.NUM_16);
    try {
      BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
      PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
      for (PropertyDescriptor property : propertyDescriptors) {
        String key = property.getName();

        // 过滤class属性
        if (!"class".equals(key)) {
          // 得到property对应的getter方法
          Method getter = property.getReadMethod();
          Object value = getter.invoke(bean);
          map.put(key, value);
        }
      }
    } catch (Exception e) {
      System.out.println("transBean2Map Error " + e);
    }
    return map;
  }
  /**
   * 生成随机的数字密码
   *
   * @param length 字符串长度
   * @return 随机位数的数字
   */
  public static String getRandomNumber(int length) {
    int tmp = (int) Math.pow(10, length - 1);
    return String.valueOf((int) ((Math.random() * 9 + 1) * tmp));
  }
  /**
   * 生成随机的字符串
   *
   * @param length 字符串长度
   * @return 随机字符串
   */
  public static String getRandomString(int length) {
    String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      int number = random.nextInt(62);
      sb.append(str.charAt(number));
    }
    return sb.toString();
  }

  /**
   * 获取当前登录用户的id
   *
   * @return 当前用户id
   */
  public static Long getCurrentUserId() {
    User currentUser = getCurrentUser();
    return currentUser.getId();
  }

  /**
   * 获取当前登录用户
   *
   * @return 当前用户id
   */
  public static User getCurrentUser() {
    String shiroCode = (String) SecurityUtils.getSubject().getPrincipal();
    return getCurrentUserByShiroCode(shiroCode);
  }

  /**
   * 获取权限验证的用户code，携带用户id和name的信息
   *
   * @param user 用户信息
   * @return 用户信息字符串
   */
  public static String getShiroCodeByUser(User user) {
    return user.getUserCode()
        + Constant.STR_SPLIT
        + (user.getId())
        + Constant.STR_SPLIT
        + user.getUserName();
  }

  /**
   * 获取权限验证的用户code，携带用户id和name的信息
   *
   * @param userCode 用户信息
   * @return 用户信息对象
   */
  public static String getUserCodeByShiroCode(String userCode) {
    String[] userString = userCode.split(Constant.STR_SPLIT);
    return userString[0];
  }

  /**
   * 获取权限验证的用户code，携带用户id和name的信息
   *
   * @param userCode 用户信息
   * @return 用户信息对象
   */
  private static User getCurrentUserByShiroCode(String userCode) {
    String[] userString = userCode.split(Constant.STR_SPLIT);
    User user = new User();
    user.setUserCode(userString[0]);
    user.setId(Long.valueOf(userString[1]));
    user.setUserName(userString[2]);
    return user;
  }

  /**
   * 获取系统当前时间
   *
   * @return 当前时间
   */
  public static Timestamp getNowTime() {
    return new Timestamp(System.currentTimeMillis());
  }

  /**
   * 根据List和key获取对应Map
   *
   * @param list 列表
   * @param property 属性名
   * @return 对应map
   */
  public static Map getMapByKeyFromList(String property, List list) {
    Map rtnMap = new HashMap();
    for (Object object : list) {
      Object obj = BeanUtil.pojo.getProperty(object, property);
      if (obj != null) {
        rtnMap.put(obj, object);
      }
    }
    return rtnMap;
  }

  /**
   * 根据key1和key2获取key
   *
   * @param key1 key1
   * @param key2 key2
   * @return 合成key
   */
  public static String getKey(Object key1, Object key2) {
    return key1.toString() + Constant.STR_SPLIT + key2.toString();
  }
}
