package com.wslint.wisdomreport.domain.dto.data;

/** @author yuxr */
public class WrReportData {

  private Long id;
  private Long reportId;
  private Long orderId;
  private String data;
  private Long inputer;
  private Long reviewer;
  private String changeReason;
  private java.sql.Timestamp gmtCreate;
  private java.sql.Timestamp gmtModified;
  private Long version;
  private Long status;
  private String hold1;
  private String hold2;
  private String hold3;
  private Long hold4;
  private Long hold5;
  private Long modifyReviewer;
  private String imageUrl;
  private String inputerName;
  private String reviewerName;
  private String modifyReviewerName;

  private Long batchNo;
  private Long medicine;

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public Long getModifyReviewer() {
    return modifyReviewer;
  }

  public void setModifyReviewer(Long modifyReviewer) {
    this.modifyReviewer = modifyReviewer;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getReportId() {
    return reportId;
  }

  public void setReportId(Long reportId) {
    this.reportId = reportId;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public Long getInputer() {
    return inputer;
  }

  public void setInputer(Long inputer) {
    this.inputer = inputer;
  }

  public Long getReviewer() {
    return reviewer;
  }

  public void setReviewer(Long reviewer) {
    this.reviewer = reviewer;
  }

  public String getChangeReason() {
    return changeReason;
  }

  public void setChangeReason(String changeReason) {
    this.changeReason = changeReason;
  }

  public java.sql.Timestamp getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(java.sql.Timestamp gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  public java.sql.Timestamp getGmtModified() {
    return gmtModified;
  }

  public void setGmtModified(java.sql.Timestamp gmtModified) {
    this.gmtModified = gmtModified;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

  public String getHold1() {
    return hold1;
  }

  public void setHold1(String hold1) {
    this.hold1 = hold1;
  }

  public String getHold2() {
    return hold2;
  }

  public void setHold2(String hold2) {
    this.hold2 = hold2;
  }

  public String getHold3() {
    return hold3;
  }

  public void setHold3(String hold3) {
    this.hold3 = hold3;
  }

  public Long getHold4() {
    return hold4;
  }

  public void setHold4(Long hold4) {
    this.hold4 = hold4;
  }

  public Long getHold5() {
    return hold5;
  }

  public void setHold5(Long hold5) {
    this.hold5 = hold5;
  }

  public Long getMedicine() {
    return medicine;
  }

  public void setMedicine(Long medicine) {
    this.medicine = medicine;
  }

  public Long getBatchNo() {
    return batchNo;
  }

  public void setBatchNo(Long batchNo) {
    this.batchNo = batchNo;
  }

  public String getInputerName() {
    return inputerName;
  }

  public void setInputerName(String inputerName) {
    this.inputerName = inputerName;
  }

  public String getReviewerName() {
    return reviewerName;
  }

  public void setReviewerName(String reviewerName) {
    this.reviewerName = reviewerName;
  }

  public String getModifyReviewerName() {
    return modifyReviewerName;
  }

  public void setModifyReviewerName(String modifyReviewerName) {
    this.modifyReviewerName = modifyReviewerName;
  }
}
