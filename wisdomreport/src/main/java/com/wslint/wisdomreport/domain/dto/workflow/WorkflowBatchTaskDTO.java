package com.wslint.wisdomreport.domain.dto.workflow;

/**
 * 批次工作流对象
 *
 * @author yuxr
 * @since 2018/11/9 14:35
 */
public class WorkflowBatchTaskDTO extends WorkflowTaskDTO {

  private Long batchId;

  public Long getBatchId() {
    return batchId;
  }

  public void setBatchId(Long batchId) {
    this.batchId = batchId;
  }
}
