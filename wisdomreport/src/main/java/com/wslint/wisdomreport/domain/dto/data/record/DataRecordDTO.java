package com.wslint.wisdomreport.domain.dto.data.record;

import com.wslint.wisdomreport.domain.dto.data.DataWorkflowWithDataDTO;

/**
 * 记录数据对象
 *
 * @author yuxr
 * @since 2018/11/13 16:15
 */
public class DataRecordDTO extends DataWorkflowWithDataDTO {
  private Long medicineId;
  private String batchNo;
  private Long firstClassId;
  private Long secondClassId;

  private Long batchId;
  private Long classId;

  private Long oldId;

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

  public Long getBatchId() {
    return batchId;
  }

  public void setBatchId(Long batchId) {
    this.batchId = batchId;
  }

  public Long getClassId() {
    return classId;
  }

  public void setClassId(Long classId) {
    this.classId = classId;
  }

  public Long getOldId() {
    return oldId;
  }

  public void setOldId(Long oldId) {
    this.oldId = oldId;
  }
}
