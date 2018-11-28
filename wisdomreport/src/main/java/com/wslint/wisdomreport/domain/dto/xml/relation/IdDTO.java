package com.wslint.wisdomreport.domain.dto.xml.relation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 关联关系DTO
 *
 * @author yuxr
 * @since 2018/10/24 14:19
 */
@XmlAccessorType(XmlAccessType.NONE)
public class IdDTO {

  @XmlElement private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
