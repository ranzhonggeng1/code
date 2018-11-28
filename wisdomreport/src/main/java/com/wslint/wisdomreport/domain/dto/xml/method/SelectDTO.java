package com.wslint.wisdomreport.domain.dto.xml.method;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 选择框数据对象
 *
 * @author yuxr
 * @since 2018/10/24 18:07
 */
@XmlAccessorType(XmlAccessType.NONE)
public class SelectDTO {

  @XmlElement private String code;
  @XmlElement private Boolean isLeaf;
  @XmlElement private String value;
  @XmlElement private String match;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Boolean getLeaf() {
    return isLeaf;
  }

  public void setLeaf(Boolean leaf) {
    isLeaf = leaf;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getMatch() {
    return match;
  }

  public void setMatch(String match) {
    this.match = match;
  }
}
