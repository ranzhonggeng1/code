package com.wslint.wisdomreport.domain.dto.xml.component;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 列DTO
 *
 * @author yuxr
 * @since 2018/10/24 12:26
 */
@XmlAccessorType(XmlAccessType.NONE)
public class ColumnDTO {

  @XmlElement private String code;
  @XmlElement private String value;
  @XmlElement private String isRef;

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

  public String getIsRef() {
    return isRef;
  }

  public void setIsRef(String isRef) {
    this.isRef = isRef;
  }
}
