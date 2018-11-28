package com.wslint.wisdomreport.domain.dto.xml.method;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 方法集合对象
 *
 * @author yuxr
 * @since 2018/10/24 12:09
 */
@XmlRootElement(name = "method")
@XmlAccessorType(XmlAccessType.NONE)
public class MethodDTO {

  @XmlElementWrapper(name = "functions")
  @XmlElement(name = "function")
  private List<FunctionDTO> functions;

  public List<FunctionDTO> getFunctions() {
    return functions;
  }

  public void setFunctions(List<FunctionDTO> functions) {
    this.functions = functions;
  }
}
