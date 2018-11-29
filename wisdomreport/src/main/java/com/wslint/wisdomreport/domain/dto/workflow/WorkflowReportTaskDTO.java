package com.wslint.wisdomreport.domain.dto.workflow;

import java.sql.Timestamp;

/**
 * 记录工作流对象
 *
 * @author yuxr
 * @since 2018/11/13 14:36
 */
public class WorkflowReportTaskDTO extends WorkflowTaskDTO {

  private Long reportId;
  private String oldData;
  private String oldRemark;
  private Timestamp oldRemarkTime;
  private Long oldRemarker;
  private String oldRemarkerName;
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

  public String getOldRemark() {
    return oldRemark;
  }

  public void setOldRemark(String oldRemark) {
    this.oldRemark = oldRemark;
  }

  public Timestamp getOldRemarkTime() {
    return oldRemarkTime;
  }

  public void setOldRemarkTime(Timestamp oldRemarkTime) {
    this.oldRemarkTime = oldRemarkTime;
  }

  public Long getOldRemarker() {
    return oldRemarker;
  }

  public void setOldRemarker(Long oldRemarker) {
    this.oldRemarker = oldRemarker;
  }

  public String getOldRemarkerName() {
    return oldRemarkerName;
  }

  public void setOldRemarkerName(String oldRemarkerName) {
    this.oldRemarkerName = oldRemarkerName;
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
