package com.wslint.wisdomreport.xmldao;

import com.wslint.wisdomreport.domain.dto.xml.product.ProductDTO;

/**
 * 产品xml读写服务
 *
 * @author yuxr
 * @since 2018/10/30 11:30
 */
public interface IProductDao {
  /**
   * 获取产品文件
   *
   * @return 产品文件
   */
  ProductDTO getProduct();

  /**
   * 更新产品文件
   *
   * @param productDTO 产品对象
   */
  boolean setProduct(ProductDTO productDTO);
}
