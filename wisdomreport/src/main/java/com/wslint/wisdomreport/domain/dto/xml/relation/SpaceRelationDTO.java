package com.wslint.wisdomreport.domain.dto.xml.relation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/** 空格关联关系中，设置code和value关联 @Author: rzg @Date: 2018/11/21 11:42 */
@XmlAccessorType(XmlAccessType.NONE)
public class SpaceRelationDTO {
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
