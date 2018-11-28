package com.wslint.wisdomreport.xmldao;

import com.wslint.wisdomreport.domain.dto.xml.method.MethodDTO;

/**
 * 方法对象服务
 *
 * @author yuxr
 * @since 2018/10/31 10:26
 */
public interface IMethodDao {

  /**
   * 根据药品id获取方法信息
   *
   * @return 方法对象
   */
  MethodDTO getMethods();

  /**
   * 更新方法文件
   *
   * @param methodDTO 方法对象
   * @return 是否成功更新
   */
  boolean setMethod(MethodDTO methodDTO);
}
