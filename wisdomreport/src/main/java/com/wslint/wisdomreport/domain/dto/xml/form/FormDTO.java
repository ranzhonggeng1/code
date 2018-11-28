package com.wslint.wisdomreport.domain.dto.xml.form;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 样式集合
 *
 * @author yuxr
 * @since 2018/10/23 16:43
 */
@XmlRootElement(name = "form")
@XmlAccessorType(XmlAccessType.NONE)
public class FormDTO {

  @XmlElementWrapper(name = "styles")
  @XmlElement(name = "style")
  private List<StyleDTO> styles;

  public List<StyleDTO> getStyles() {
    return styles;
  }

  public void setStyles(List<StyleDTO> styles) {
    this.styles = styles;
  }
}
