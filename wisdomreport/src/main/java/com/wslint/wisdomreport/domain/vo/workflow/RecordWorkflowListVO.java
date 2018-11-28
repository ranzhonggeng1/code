package com.wslint.wisdomreport.domain.vo.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 记录数据走工作流参数对象
 *
 * @author yuxr
 * @since 2018/11/13 17:50
 */
@ApiModel(value = "记录走工作流对象")
public class RecordWorkflowListVO {

  @ApiModelProperty(value = "批量执行工作流数据对象", required = true, example = "")
  private List<RecordWorkflowVO> recordWorkflowVOS;

  public List<RecordWorkflowVO> getRecordWorkflowVOS() {
    return recordWorkflowVOS;
  }

  public void setRecordWorkflowVOS(List<RecordWorkflowVO> recordWorkflowVOS) {
    this.recordWorkflowVOS = recordWorkflowVOS;
  }
}
