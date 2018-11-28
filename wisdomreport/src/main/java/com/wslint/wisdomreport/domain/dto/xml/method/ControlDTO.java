package com.wslint.wisdomreport.domain.dto.xml.method;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * 定位信息对象
 *
 * @author yuxr
 * @since 2018/10/24 17:02
 */
@XmlAccessorType(XmlAccessType.NONE)
public class ControlDTO {

  @XmlElement private Long inId;
  @XmlElement private Long paramId;

  @XmlElementWrapper(name = "selects")
  @XmlElement(name = "select")
  List<SelectDTO> selects;

  @XmlElementWrapper(name = "subSelects")
  @XmlElement(name = "select")
  List<SelectDTO> subSelects;

  public Long getInId() {
    return inId;
  }

  public void setInId(Long inId) {
    this.inId = inId;
  }

  public Long getParamId() {
    return paramId;
  }

  public void setParamId(Long paramId) {
    this.paramId = paramId;
  }

  public List<SelectDTO> getSelects() {
    return selects;
  }

  public void setSelects(List<SelectDTO> selects) {
    this.selects = selects;
  }

  public List<SelectDTO> getSubSelects() {
    return subSelects;
  }

  public void setSubSelects(List<SelectDTO> subSelects) {
    this.subSelects = subSelects;
  }
}
