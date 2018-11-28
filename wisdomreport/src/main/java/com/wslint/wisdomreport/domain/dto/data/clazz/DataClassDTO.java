package com.wslint.wisdomreport.domain.dto.data.clazz;

import com.wslint.wisdomreport.domain.dto.data.DataDTO;

/**
 * 类别对象
 *
 * @author yuxr
 * @since 2018/11/9 10:21
 */
public class DataClassDTO extends DataDTO {

  private Long batchId;
  private Long medicineId;
  private String batchNo;
  private Long firstClassId;
  private Long secondClassId;
  private Long oldBatchId;
  private Long oldClassId;

  public Long getBatchId() {
    return batchId;
  }

  public void setBatchId(Long batchId) {
    this.batchId = batchId;
  }

  public Long getMedicineId() {
    return medicineId;
  }

  public void setMedicineId(Long medicineId) {
    this.medicineId = medicineId;
  }

  public String getBatchNo() {
    return batchNo;
  }

  public void setBatchNo(String batchNo) {
    this.batchNo = batchNo;
  }

  public Long getFirstClassId() {
    return firstClassId;
  }

  public void setFirstClassId(Long firstClassId) {
    this.firstClassId = firstClassId;
  }

  public Long getSecondClassId() {
    return secondClassId;
  }

  public void setSecondClassId(Long secondClassId) {
    this.secondClassId = secondClassId;
  }

  public Long getOldBatchId() {
    return oldBatchId;
  }

  public void setOldBatchId(Long oldBatchId) {
    this.oldBatchId = oldBatchId;
  }

  public Long getOldClassId() {
    return oldClassId;
  }

  public void setOldClassId(Long oldClassId) {
    this.oldClassId = oldClassId;
  }
}
