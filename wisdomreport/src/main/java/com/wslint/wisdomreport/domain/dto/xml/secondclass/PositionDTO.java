package com.wslint.wisdomreport.domain.dto.xml.secondclass;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 定位对象
 *
 * @author yuxr
 * @since 2018/10/24 16:18
 */
@XmlAccessorType(XmlAccessType.NONE)
public class PositionDTO {

  @XmlElement private Long functionId;
  @XmlElement private String code;
  @XmlElement private String value;
  @XmlElement private String subSelect;

  public Long getFunctionId() {
    return functionId;
  }

  public void setFunctionId(Long functionId) {
    this.functionId = functionId;
  }

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

  public String getSubSelect() {
    return subSelect;
  }

  public void setSubSelect(String subSelect) {
    this.subSelect = subSelect;
  }
}
