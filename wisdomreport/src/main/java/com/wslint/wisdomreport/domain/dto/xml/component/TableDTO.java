package com.wslint.wisdomreport.domain.dto.xml.component;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * 列表对象
 *
 * @author yuxr
 * @since 2018/10/24 12:25
 */
@XmlAccessorType(XmlAccessType.NONE)
public class TableDTO {

  @XmlElement private Long id;

  @XmlElementWrapper(name = "relations")
  @XmlElement(name = "relation")
  private List<RelationDTO> relations;

  @XmlElement private RowDTO head;

  @XmlElementWrapper(name = "rows")
  @XmlElement(name = "row")
  private List<RowDTO> rows;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<RelationDTO> getRelations() {
    return relations;
  }

  public void setRelations(List<RelationDTO> relations) {
    this.relations = relations;
  }

  public RowDTO getHead() {
    return head;
  }

  public void setHead(RowDTO head) {
    this.head = head;
  }

  public List<RowDTO> getRows() {
    return rows;
  }

  public void setRows(List<RowDTO> rows) {
    this.rows = rows;
  }
}
