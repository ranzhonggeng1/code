package com.wslint.wisdomreport.domain.dto.xml.program;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * 单个公式
 *
 * @author yuxr
 * @since 2018/10/23 18:08
 */
@XmlAccessorType(XmlAccessType.NONE)
public class FormulaDTO {

  @XmlElement private Long id;
  @XmlElement private String name;
  @XmlElement private Integer paramCount;

  @XmlElementWrapper(name = "params")
  @XmlElement(name = "param")
  private List<ParamDTO> params;

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

  public Integer getParamCount() {
    return paramCount;
  }

  public void setParamCount(Integer paramCount) {
    this.paramCount = paramCount;
  }

  public List<ParamDTO> getParams() {
    return params;
  }

  public void setParams(List<ParamDTO> params) {
    this.params = params;
  }
}
