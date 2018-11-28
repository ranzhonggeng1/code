package com.wslint.wisdomreport.domain.dto.workflow;

/**
 * 记录工作流对象
 *
 * @author yuxr
 * @since 2018/11/13 14:36
 */
public class WorkflowRecordTaskDTO extends WorkflowTaskDTO {

  private Long recordId;
  private String oldData;
  private String newData;
  private String imgUrl;

  public Long getRecordId() {
    return recordId;
  }

  public void setRecordId(Long recordId) {
    this.recordId = recordId;
  }

  public String getOldData() {
    return oldData;
  }

  public void setOldData(String oldData) {
    this.oldData = oldData;
  }

  public String getNewData() {
    return newData;
  }

  public void setNewData(String newData) {
    this.newData = newData;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }
}
