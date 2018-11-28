package com.wslint.wisdomreport.domain.dto.xml.program;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 参数
 *
 * @author yuxr
 * @since 2018/10/23 18:09
 */
@XmlAccessorType(XmlAccessType.NONE)
public class ParamDTO {

  @XmlElement private Long id;
  @XmlElement private String type;
  @XmlElement private String name;
  @XmlElement private String code;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() { return code; }

  public void setCode(String code) { this.code = code; }
}
