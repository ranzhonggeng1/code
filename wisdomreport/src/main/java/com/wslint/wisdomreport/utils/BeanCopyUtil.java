package com.wslint.wisdomreport.utils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.cglib.beans.BeanCopier;

/**
 * 对象转换工具
 *
 * @author yuxr
 * @since 2018/10/29 15:37
 */
public class BeanCopyUtil {

  @SuppressWarnings("unchecked")
  public static <S, T> List<T> copyList(List<S> sourceList, Class<T> targetClass) throws Exception {
    List dataList = new ArrayList();
    for (S s : sourceList) {
      T t = targetClass.newInstance();
      BeanCopier copier = BeanCopier.create(s.getClass(), t.getClass(), false);
      copier.copy(s, t, null);
      dataList.add(t);
    }
    return dataList;
  }

  public static <T> T copyBean(Object obj, Class<T> targetClass) throws Exception {
    T t = targetClass.newInstance();
    BeanCopier copier = BeanCopier.create(obj.getClass(), t.getClass(), false);
    copier.copy(obj, t, null);
    return t;
  }
}
