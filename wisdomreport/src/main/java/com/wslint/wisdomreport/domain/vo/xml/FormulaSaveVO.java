package com.wslint.wisdomreport.domain.vo.xml;

import com.wslint.wisdomreport.domain.dto.xml.relation.IdDTO;
import java.util.List;

/**
 * 公式保存对象
 *
 * @author yuxr
 * @since 2018/11/5 20:09
 */
public class FormulaSaveVO {

  private Long functionId;
  private List<IdDTO> formulas;

  public Long getFunctionId() {
    return functionId;
  }

  public void setFunctionId(Long functionId) {
    this.functionId = functionId;
  }

  public List<IdDTO> getFormulas() {
    return formulas;
  }

  public void setFormulas(List<IdDTO> formulas) {
    this.formulas = formulas;
  }
}
