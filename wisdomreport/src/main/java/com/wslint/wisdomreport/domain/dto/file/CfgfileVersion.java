package com.wslint.wisdomreport.domain.dto.file;

public class CfgfileVersion {

  private Long id;
  private Long medicineId;
  private String version;
  private String path;
  private String fileName;
  private java.sql.Timestamp gmtCreate;
  private java.sql.Timestamp gmtModified;
  private String remark;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getMedicineId() {
    return medicineId;
  }

  public void setMedicineId(Long medicineId) {
    this.medicineId = medicineId;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String verion) {
    this.version = verion;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
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

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
