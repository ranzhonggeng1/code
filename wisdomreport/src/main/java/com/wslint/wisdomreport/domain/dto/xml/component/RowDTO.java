package com.wslint.wisdomreport.domain.dto.xml.component;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * 行对象
 *
 * @author yxur
 * @since 2018/10/24 15:24
 */
@XmlAccessorType(XmlAccessType.NONE)
public class RowDTO {

  @XmlElement private Long id;

  @XmlElementWrapper(name = "columns")
  @XmlElement(name = "column")
  private List<ColumnDTO> columns;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<ColumnDTO> getColumns() {
    return columns;
  }

  public void setColumns(List<ColumnDTO> columns) {
    this.columns = columns;
  }
}
