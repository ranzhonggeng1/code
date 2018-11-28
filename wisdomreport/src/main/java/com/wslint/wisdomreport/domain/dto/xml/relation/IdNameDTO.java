package com.wslint.wisdomreport.domain.dto.xml.relation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * id name 对应dto
 *
 * @author yuxr
 * @since 2018/10/29 12:26
 */
@XmlAccessorType(XmlAccessType.NONE)
public class IdNameDTO {

  @XmlElement private Long id;
  @XmlElement private String name;

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
}
