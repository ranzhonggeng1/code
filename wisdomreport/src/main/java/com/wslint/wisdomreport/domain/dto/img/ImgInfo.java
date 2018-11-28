package com.wslint.wisdomreport.domain.dto.img;

/**
 * 图像相关信息
 *
 * @author yuxr
 * @since 2018/10/10 10:26
 */
public class ImgInfo {

  private Long medicine;
  private Long batchNo;
  private Long firstClass;
  private Long secondClass;
  private String deviceCode;
  private Integer deviceType;
  private String substance;
  private String fileName;

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

  public String getDeviceCode() {
    return deviceCode;
  }

  public void setDeviceCode(String deviceCode) {
    this.deviceCode = deviceCode;
  }

  public Integer getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(Integer deviceType) {
    this.deviceType = deviceType;
  }

  public String getSubstance() {
    return substance;
  }

  public void setSubstance(String substance) {
    this.substance = substance;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
