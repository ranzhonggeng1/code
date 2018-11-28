package com.wslint.wisdomreport.domain.dto.data.report;

import com.wslint.wisdomreport.domain.dto.data.DataWorkflowWithDataDTO;

/**
 * 记录数据对象
 *
 * @author yuxr
 * @since 2018/11/13 16:15
 */
public class DataReportDTO extends DataWorkflowWithDataDTO {
  private Long medicineId;
  private String batchNo;

  private Long batchId;

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

  public Long getBatchId() {
    return batchId;
  }

  public void setBatchId(Long batchId) {
    this.batchId = batchId;
  }
}
