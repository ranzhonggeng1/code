package com.wslint.wisdomreport.domain.dto.data.clazz;

import java.util.List;

/**
 * 大类对象
 *
 * @author yuxr
 * @since 2018/11/9 14:05
 */
public class FirstClassDTO {

  private Long id;

  private List<SecondClassDTO> secondClassDTOS;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<SecondClassDTO> getSecondClassDTOS() {
    return secondClassDTOS;
  }

  public void setSecondClassDTOS(List<SecondClassDTO> secondClassDTOS) {
    this.secondClassDTOS = secondClassDTOS;
  }
}
