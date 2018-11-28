package com.wslint.wisdomreport.domain.dto.xml.component;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 列表关联参数数据
 *
 * @author yuxr
 * @since 2018/10/26 11:29
 */
@XmlAccessorType(XmlAccessType.NONE)
public class RelationDTO {

  @XmlElement private String code;

  @XmlElement private String value;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
