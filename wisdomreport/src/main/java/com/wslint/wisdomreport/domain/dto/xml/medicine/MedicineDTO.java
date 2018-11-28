package com.wslint.wisdomreport.domain.dto.xml.medicine;

import com.wslint.wisdomreport.domain.dto.xml.relation.IdDTO;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 药品
 *
 * @author yuxr
 * @since 2018/10/23 15:48
 */
@XmlRootElement(name = "medicine")
@XmlAccessorType(XmlAccessType.NONE)
public class MedicineDTO {

  @XmlElement private Long id;
  @XmlElement private String name;
  @XmlElement private String factoryName;

  @XmlElementWrapper(name = "firstClasss")
  @XmlElement(name = "firstClass")
  private List<IdDTO> firstClasss;

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

  public String getFactoryName() {
    return factoryName;
  }

  public void setFactoryName(String factoryName) {
    this.factoryName = factoryName;
  }

  public List<IdDTO> getFirstClasss() {
    return firstClasss;
  }

  public void setFirstClasss(List<IdDTO> firstClasss) {
    this.firstClasss = firstClasss;
  }
}
