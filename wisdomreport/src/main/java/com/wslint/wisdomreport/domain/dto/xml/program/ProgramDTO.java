package com.wslint.wisdomreport.domain.dto.xml.program;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 公式集合
 *
 * @author yxur
 * @since 2018/10/23 18:08
 */
@XmlRootElement(name = "program")
@XmlAccessorType(XmlAccessType.NONE)
public class ProgramDTO {
  @XmlElementWrapper(name = "formulas")
  @XmlElement(name = "formula")
  private List<FormulaDTO> formulas;

  public List<FormulaDTO> getFormulas() {
    return formulas;
  }

  public void setFormulas(List<FormulaDTO> formulas) {
    this.formulas = formulas;
  }
}
