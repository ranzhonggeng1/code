package com.wslint.wisdomreport.xmldao.impl;

import com.wslint.wisdomreport.constant.XMLConstant;
import com.wslint.wisdomreport.domain.dto.xml.method.MethodDTO;
import com.wslint.wisdomreport.utils.FileUtils;
import com.wslint.wisdomreport.utils.XMLUtils;
import com.wslint.wisdomreport.xmldao.IMethodDao;
import org.springframework.stereotype.Service;

/**
 * 方法对象服务
 *
 * @author yuxr
 * @since 2018/10/31 10:26
 */
@Service
public class MethodDao implements IMethodDao {
  /**
   * 根据药品id获取方法信息
   *
   * @return 方法对象
   */
  @Override
  public MethodDTO getMethods() {
    String path = FileUtils.getProductPath();
    return XMLUtils.xmlFileToDto(MethodDTO.class, path, XMLConstant.FILE_METHOD);
  }

  /**
   * 更新方法文件
   *
   * @param methodDTO 方法对象
   * @return 是否成功更新
   */
  @Override
  public boolean setMethod(MethodDTO methodDTO) {
    String path = FileUtils.getProductPath();
    return XMLUtils.dtoToXml(methodDTO, path, XMLConstant.FILE_METHOD);
  }
}
