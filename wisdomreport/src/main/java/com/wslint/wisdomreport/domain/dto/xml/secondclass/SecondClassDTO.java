package com.wslint.wisdomreport.domain.dto.xml.secondclass;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 小类对象
 *
 * @author yuxr
 * @since 2018/10/24 15:04
 */
@XmlRootElement(name = "secondClass")
@XmlAccessorType(XmlAccessType.NONE)
public class SecondClassDTO {

  @XmlElement private Long id;
  @XmlElement private String name;
  @XmlElement private String htmlURL;

  @XmlElementWrapper(name = "spaces")
  @XmlElement(name = "space")
  private List<SpaceDTO> spaces;

  @XmlElementWrapper(name = "paramValues")
  @XmlElement(name = "paramValue")
  private List<ParamValueDTO> paramValues;

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

  public String getHtmlURL() {
    return htmlURL;
  }

  public void setHtmlURL(String htmlURL) {
    this.htmlURL = htmlURL;
  }

  public List<SpaceDTO> getSpaces() {
    return spaces;
  }

  public void setSpaces(List<SpaceDTO> spaces) {
    this.spaces = spaces;
  }

  public List<ParamValueDTO> getParamValues() {
    return paramValues;
  }

  public void setParamValues(List<ParamValueDTO> paramValues) {
    this.paramValues = paramValues;
  }
}
