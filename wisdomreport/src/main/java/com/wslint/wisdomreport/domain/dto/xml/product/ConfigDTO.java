package com.wslint.wisdomreport.domain.dto.xml.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 系统配置DTO
 *
 * @author yuxr
 * @since 2018/10/30 11:24
 */
@XmlAccessorType(XmlAccessType.NONE)
public class ConfigDTO {
  @XmlElement private Long medicineId;
  @XmlElement private Long firstClassId;
  @XmlElement private Long secondClassId;
  @XmlElement private Long rowId;
  @XmlElement private Long relsId;

  public Long getMedicineId() {
    return medicineId;
  }

  public void setMedicineId(Long medicineId) {
    this.medicineId = medicineId;
  }

  public Long getFirstClassId() {
    return firstClassId;
  }

  public void setFirstClassId(Long firstClassId) {
    this.firstClassId = firstClassId;
  }

  public Long getSecondClassId() {
    return secondClassId;
  }

  public void setSecondClassId(Long secondClassId) {
    this.secondClassId = secondClassId;
  }

  public Long getRowId() {
    return rowId;
  }

  public void setRowId(Long rowId) {
    this.rowId = rowId;
  }

  public Long getRelsId() {
    return relsId;
  }

  public void setRelsId(Long relsId) {
    this.relsId = relsId;
  }

  public Long getNewMedicineId() {
    return ++medicineId;
  }

  public Long getNewFirstClassId() {
    return ++firstClassId;
  }

  public Long getNewSecondClassId() {
    return ++secondClassId;
  }

  public Long getNewRelsId() {
    return ++relsId;
  }

  public Long getNewRowId() {
    return ++rowId;
  }
}
