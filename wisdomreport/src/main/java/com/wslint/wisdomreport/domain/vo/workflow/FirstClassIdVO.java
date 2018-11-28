package com.wslint.wisdomreport.domain.vo.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 大类id对象
 *
 * @author yuxr
 * @since 2018/11/9 13:59
 */
@ApiModel(value = "大类对象")
public class FirstClassIdVO {

  @ApiModelProperty(value = "主键id", required = true, example = "3")
  private Long id;

  @ApiModelProperty(value = "小类对象", required = true)
  private List<SecondClassIdVO> secondClassIdVOS;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<SecondClassIdVO> getSecondClassIdVOS() {
    return secondClassIdVOS;
  }

  public void setSecondClassIdVOS(List<SecondClassIdVO> secondClassIdVOS) {
    this.secondClassIdVOS = secondClassIdVOS;
  }
}
