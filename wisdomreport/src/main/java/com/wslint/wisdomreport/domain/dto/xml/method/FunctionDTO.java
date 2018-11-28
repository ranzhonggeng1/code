package com.wslint.wisdomreport.domain.dto.xml.method;

import com.wslint.wisdomreport.domain.dto.xml.component.TableDTO;
import com.wslint.wisdomreport.domain.dto.xml.relation.IdDTO;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * 方法对象
 *
 * @author yuxr
 * @since 2018/10/24 12:12
 */
@XmlAccessorType(XmlAccessType.NONE)
public class FunctionDTO {

  @XmlElement private Long id;
  @XmlElement private String type;
  @XmlElement private String name;

  @XmlElementWrapper(name = "formulas")
  @XmlElement(name = "formula")
  private List<IdDTO> formulas;

  @XmlElementWrapper(name = "params")
  @XmlElement(name = "table")
  private List<TableDTO> params;

  @XmlElementWrapper(name = "inputs")
  @XmlElement(name = "table")
  private List<TableDTO> inputs;

  @XmlElementWrapper(name = "outputs")
  @XmlElement(name = "control")
  private List<ControlDTO> outputs;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<IdDTO> getFormulas() {
    return formulas;
  }

  public void setFormulas(List<IdDTO> formulas) {
    this.formulas = formulas;
  }

  public List<TableDTO> getParams() {
    return params;
  }

  public void setParams(List<TableDTO> params) {
    this.params = params;
  }

  public List<TableDTO> getInputs() {
    return inputs;
  }

  public void setInputs(List<TableDTO> inputs) {
    this.inputs = inputs;
  }

  public List<ControlDTO> getOutputs() {
    return outputs;
  }

  public void setOutputs(List<ControlDTO> outputs) {
    this.outputs = outputs;
  }
}
