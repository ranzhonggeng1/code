package com.wslint.wisdomreport.domain.dto.xml.form;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 样式
 *
 * @author yuxr
 * @since 2018/10/23 16:44
 */
@XmlAccessorType(XmlAccessType.NONE)
public class StyleDTO {

  @XmlElement private Long id;
  @XmlElement private Integer decimalNumber;
  @XmlElement private Integer font;
  @XmlElement private Integer fontSize;
  @XmlElement private String color;
  @XmlElement private Boolean isEdit;
  @XmlElement private Boolean isShowFormula;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getDecimalNumber() {
    return decimalNumber;
  }

  public void setDecimalNumber(Integer decimalNumber) {
    this.decimalNumber = decimalNumber;
  }

  public Integer getFont() {
    return font;
  }

  public void setFont(Integer font) {
    this.font = font;
  }

  public Integer getFontSize() {
    return fontSize;
  }

  public void setFontSize(Integer fontSize) {
    this.fontSize = fontSize;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public Boolean getEdit() {
    return isEdit;
  }

  public void setEdit(Boolean edit) {
    isEdit = edit;
  }

  public Boolean getShowFormula() {
    return isShowFormula;
  }

  public void setShowFormula(Boolean showFormula) {
    isShowFormula = showFormula;
  }
}
