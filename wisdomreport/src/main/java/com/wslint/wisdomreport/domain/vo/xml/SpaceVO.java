package com.wslint.wisdomreport.domain.vo.xml;

import com.wslint.wisdomreport.domain.dto.xml.relation.SpaceRelationDTO;
import com.wslint.wisdomreport.domain.dto.xml.secondclass.PositionDTO;
import java.util.List;

/**
 * 空格信息
 *
 * @author yuxr
 * @since 2018/10/31 11:05
 */
public class SpaceVO {

  private Long medicineId;
  private Long secondClassId;

  private Long id;
  private Long styleId;
  private PositionDTO position;
  private List<SpaceRelationDTO> relations;
  private Boolean isAutoCal;
  private Boolean isAutoInput;
  private Integer type;

  public Long getMedicineId() {
    return medicineId;
  }

  public void setMedicineId(Long medicineId) {
    this.medicineId = medicineId;
  }

  public Long getSecondClassId() {
    return secondClassId;
  }

  public void setSecondClassId(Long secondClassId) {
    this.secondClassId = secondClassId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Boolean getAutoCal() {
    return isAutoCal;
  }

  public void setAutoCal(Boolean autoCal) {
    isAutoCal = autoCal;
  }

  public Boolean getAutoInput() {
    return isAutoInput;
  }

  public void setAutoInput(Boolean autoInput) {
    isAutoInput = autoInput;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }
}
