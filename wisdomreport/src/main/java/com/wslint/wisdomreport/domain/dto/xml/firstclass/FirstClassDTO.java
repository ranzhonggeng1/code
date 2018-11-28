package com.wslint.wisdomreport.domain.dto.xml.firstclass;

import com.wslint.wisdomreport.domain.dto.xml.relation.IdDTO;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 大类对象
 *
 * @author yuxr
 * @since 2018/10/24 14:55
 */
@XmlRootElement(name = "firstClass")
@XmlAccessorType(XmlAccessType.NONE)
public class FirstClassDTO {

  @XmlElement private Long id;
  @XmlElement private String name;

  @XmlElementWrapper(name = "secondClasss")
  @XmlElement(name = "secondClass")
  private List<IdDTO> secondClasss;

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

  public List<IdDTO> getSecondClasss() {
    return secondClasss;
  }

  public void setSecondClasss(List<IdDTO> secondClasss) {
    this.secondClasss = secondClasss;
  }
}
