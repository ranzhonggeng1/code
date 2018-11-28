package com.wslint.wisdomreport.xmldao.impl;

import com.wslint.wisdomreport.constant.XMLConstant;
import com.wslint.wisdomreport.domain.dto.xml.product.ProductDTO;
import com.wslint.wisdomreport.utils.FileUtils;
import com.wslint.wisdomreport.utils.XMLUtils;
import com.wslint.wisdomreport.xmldao.IProductDao;
import org.springframework.stereotype.Service;

/**
 * 产品xml读写服务
 *
 * @author yuxr
 * @since 2018/10/30 11:30
 */
@Service
public class ProductDao implements IProductDao {

  /**
   * 获取产品信息信息
   *
   * @return 获取产品信息信息
   */
  @Override
  public ProductDTO getProduct() {
    String path = FileUtils.getProductPath();
    return XMLUtils.xmlFileToDto(ProductDTO.class, path, XMLConstant.FILE_PRODUCT);
  }

  /**
   * 更新产品文件
   *
   * @param productDTO 产品对象
   */
  @Override
  public boolean setProduct(ProductDTO productDTO) {
    String path = FileUtils.getProductPath();
    return XMLUtils.dtoToXml(productDTO, path, XMLConstant.FILE_PRODUCT);
  }
}
