package com.wslint.wisdomreport.domain.dto.workflow;

/**
 * 记录工作流对象
 *
 * @author yuxr
 * @since 2018/11/13 14:36
 */
public class WorkflowReportTaskDTO extends WorkflowTaskDTO {

  private Long reportId;
  private String oldData;
  private String newData;
  private String imgUrl;

  public Long getReportId() {
    return reportId;
  }

  public void setReportId(Long reportId) {
    this.reportId = reportId;
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
