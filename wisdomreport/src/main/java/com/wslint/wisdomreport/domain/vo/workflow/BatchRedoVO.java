package com.wslint.wisdomreport.domain.vo.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 批次复测对象
 *
 * @author yuxr
 * @since 2018/11/27 16:17
 */
@ApiModel(value = "批次复测对象")
public class BatchRedoVO {

  @ApiModelProperty(value = "批次数据id", required = true, example = "")
  private Long batchId;

  @ApiModelProperty(value = "复测原因", required = true, example = "测试复测")
  private String reason;

  @ApiModelProperty(value = "类型对象", required = true, hidden = true)
  private List<Long> classIds;

  @ApiModelProperty(value = "大小类对象", required = true)
  private List<ClassIdVO> classIdVOS;

  public Long getBatchId() {
    return batchId;
  }

  public void setBatchId(Long batchId) {
    this.batchId = batchId;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public List<Long> getClassIds() {
    return classIds;
  }

  public void setClassIds(List<Long> classIds) {
    this.classIds = classIds;
  }

  public List<ClassIdVO> getClassIdVOS() {
    return classIdVOS;
  }

  public void setClassIdVOS(
      List<ClassIdVO> classIdVOS) {
    this.classIdVOS = classIdVOS;
  }
}
