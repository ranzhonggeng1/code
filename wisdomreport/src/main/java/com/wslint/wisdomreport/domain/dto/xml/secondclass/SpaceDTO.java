package com.wslint.wisdomreport.domain.dto.xml.secondclass;

import com.wslint.wisdomreport.domain.dto.xml.relation.SpaceRelationDTO;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * 空格对象
 *
 * @author yuxr
 * @since 2018/10/24 15:13
 */
@XmlAccessorType(XmlAccessType.NONE)
public class SpaceDTO {
  @XmlElement private Long id;
  @XmlElement private Boolean isAutoInput;
  @XmlElement private Boolean isNULL;
  @XmlElement private Boolean isAutoCal;
  @XmlElement private Integer type;
  @XmlElement private Integer dataSourceType;
  @XmlElement private Long styleId;

  @XmlElement private PositionDTO position;

  @XmlElementWrapper(name = "relations")
  @XmlElement(name = "relation")
  private List<SpaceRelationDTO> relations;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getAutoInput() {
    return isAutoInput;
  }

  public void setAutoInput(Boolean autoInput) {
    isAutoInput = autoInput;
  }

  public Boolean getNULL() {
    return isNULL;
  }

  public void setNULL(Boolean NULL) {
    isNULL = NULL;
  }

  public Boolean getAutoCal() {
    return isAutoCal;
  }

  public void setAutoCal(Boolean autoCal) {
    isAutoCal = autoCal;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Integer getDataSourceType() {
    return dataSourceType;
  }

  public void setDataSourceType(Integer dataSourceType) {
    this.dataSourceType = dataSourceType;
  }

  public Long getStyleId() {
    return styleId;
  }

  public void setStyleId(Long styleId) {
    this.styleId = styleId;
  }

  public PositionDTO getPosition() {
    return position;
  }

  public void setPosition(PositionDTO position) {
    this.position = position;
  }

  public List<SpaceRelationDTO> getRelations() {
    return relations;
  }

  public void setRelations(List<SpaceRelationDTO> relations) {
    this.relations = relations;
  }
}
