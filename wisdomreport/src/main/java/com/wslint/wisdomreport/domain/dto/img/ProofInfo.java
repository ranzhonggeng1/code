package com.wslint.wisdomreport.domain.dto.img;

/**
 * 取证图片传入参数
 *
 * @author yuxr
 * @since 2018/11/6 17:31
 */
public class ProofInfo {

  private Long medicine;
  private Long batchNo;
  private Long firstClass;
  private Long secondClass;

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
}
