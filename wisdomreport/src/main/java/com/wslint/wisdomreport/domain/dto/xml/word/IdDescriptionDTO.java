package com.wslint.wisdomreport.domain.dto.xml.word;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/** 药品版本管理文件中id和描述 @Author: rzg @Date: 2018/11/22 11:47 */
@XmlAccessorType(XmlAccessType.NONE)
public class IdDescriptionDTO {

  @XmlElement private String id;
  @XmlElement private String description;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
