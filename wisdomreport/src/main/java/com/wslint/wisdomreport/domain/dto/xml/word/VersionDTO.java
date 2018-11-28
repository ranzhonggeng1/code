package com.wslint.wisdomreport.domain.dto.xml.word;

import javax.xml.bind.annotation.*;
import java.util.List;

/** 药品版本管理文件 @Author: rzg @Date: 2018/11/22 11:38 */
@XmlRootElement(name = "version")
@XmlAccessorType(XmlAccessType.NONE)
public class VersionDTO {

  @XmlElementWrapper(name = "wordaround")
  @XmlElement(name = "version")
  private List<IdDescriptionDTO> idDescriptionDTOS;

  @XmlElement private ConfigDTO config;

  public List<IdDescriptionDTO> getIdDescriptionDTOS() {
    return idDescriptionDTOS;
  }

  public void setIdDescriptionDTOS(List<IdDescriptionDTO> idDescriptionDTOS) {
    this.idDescriptionDTOS = idDescriptionDTOS;
  }

  public ConfigDTO getConfig() {
    return config;
  }

  public void setConfig(ConfigDTO config) {
    this.config = config;
  }
}
