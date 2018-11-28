package com.wslint.wisdomreport.domain.dto.data;

import java.util.List;

/** @author yuxr */
public class WrReport {

  private Long id;
  private Long batchNo;
  private Long medicine;
  private Long firstClass;
  private Long secondClass;
  private Long thirdClass;
  private Long class1;
  private Long class2;
  private Long class3;
  private String hold1;
  private String hold2;
  private String hold3;
  private java.sql.Timestamp gmtCreate;
  private java.sql.Timestamp gmtModified;
  private List<WrReportData> wrReportDataList;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getBatchNo() {
    return batchNo;
  }

  public void setBatchNo(Long batchNo) {
    this.batchNo = batchNo;
  }

  public Long getMedicine() {
    return medicine;
  }

  public void setMedicine(Long medicine) {
    this.medicine = medicine;
  }

  public Long getFirstClass() {
    return firstClass;
  }

  public void setFirstClass(Long firstClass) {
    this.firstClass = firstClass;
  }

  public Long getSecondClass() {
    return secondClass;
  }

  public void setSecondClass(Long secondClass) {
    this.secondClass = secondClass;
  }

  public Long getThirdClass() {
    return thirdClass;
  }

  public void setThirdClass(Long thirdClass) {
    this.thirdClass = thirdClass;
  }

  public Long getClass1() {
    return class1;
  }

  public void setClass1(Long class1) {
    this.class1 = class1;
  }

  public Long getClass2() {
    return class2;
  }

  public void setClass2(Long class2) {
    this.class2 = class2;
  }

  public Long getClass3() {
    return class3;
  }

  public void setClass3(Long class3) {
    this.class3 = class3;
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

  public List<WrReportData> getWrReportDataList() {
    return wrReportDataList;
  }

  public void setWrReportDataList(List<WrReportData> wrReportDataList) {
    this.wrReportDataList = wrReportDataList;
  }
}
