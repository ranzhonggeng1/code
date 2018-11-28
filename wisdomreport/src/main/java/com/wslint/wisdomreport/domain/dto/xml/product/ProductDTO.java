package com.wslint.wisdomreport.domain.dto.xml.product;

import com.wslint.wisdomreport.domain.dto.xml.relation.IdNameDTO;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 产品对象
 *
 * @author yuxr
 * @since 2018/10/29 12:19
 */
@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.NONE)
public class ProductDTO {

  @XmlElementWrapper(name = "medicines")
  @XmlElement(name = "medicine")
  private List<IdNameDTO> medicines;

  @XmlElement private ConfigDTO config;

  public List<IdNameDTO> getMedicines() {
    return medicines;
  }

  public void setMedicines(List<IdNameDTO> medicines) {
    this.medicines = medicines;
  }

  public ConfigDTO getConfig() {
    return config;
  }

  public void setConfig(ConfigDTO config) {
    this.config = config;
  }
}
