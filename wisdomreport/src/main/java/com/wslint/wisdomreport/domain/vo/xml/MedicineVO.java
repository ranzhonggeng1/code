package com.wslint.wisdomreport.domain.vo.xml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加药品对象
 *
 * @author yuxr
 * @since 2018/10/30 11:00
 */
@ApiModel
public class MedicineVO {

  @ApiModelProperty(value = "主键id", hidden = true)
  private Long id;

  @ApiModelProperty(value = "用户名称")
  private String name;

  @ApiModelProperty(value = "工厂名")
  private String factory;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFactory() {
    return factory;
  }

  public void setFactory(String factory) {
    this.factory = factory;
  }
}
