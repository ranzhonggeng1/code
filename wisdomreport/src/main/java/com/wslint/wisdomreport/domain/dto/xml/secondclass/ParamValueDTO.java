package com.wslint.wisdomreport.domain.dto.xml.secondclass;

import com.wslint.wisdomreport.domain.dto.xml.component.TableDTO;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * 预设参数值对象
 *
 * @author yuxr
 * @since 2018/10/24 15:18
 */
@XmlAccessorType(XmlAccessType.NONE)
public class ParamValueDTO {

  @XmlElement private Long functionId;

  @XmlElementWrapper(name = "tables")
  @XmlElement(name = "table")
  private List<TableDTO> tables;

  public Long getFunctionId() {
    return functionId;
  }

  public void setFunctionId(Long functionId) {
    this.functionId = functionId;
  }

  public List<TableDTO> getTables() {
    return tables;
  }

  public void setTables(List<TableDTO> tables) {
    this.tables = tables;
  }
}
