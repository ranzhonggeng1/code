package com.wslint.wisdomreport.domain.dto.xml.word;

import javax.print.DocFlavor;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/** 版本管理文件中config段 @Author: rzg @Date: 2018/11/22 11:41 */
@XmlAccessorType(XmlAccessType.NONE)
public class ConfigDTO {

  @XmlElement private String operation;
  @XmlElement private Long idMax;
  @XmlElement private String versionMax;

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public Long getIdMax() {
    return idMax;
  }

  public void setIdMax(Long idMax) {
    this.idMax = idMax;
  }

  public String getVersionMax() {
    return versionMax;
  }

  public void setVersionMax(String versionMax) {
    this.versionMax = versionMax;
  }
}
