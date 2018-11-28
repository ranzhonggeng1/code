package com.wslint.wisdomreport.domain.dto.data.batch;

import com.wslint.wisdomreport.domain.dto.data.DataWorkflowDTO;

/**
 * 批次数据对象
 *
 * @author yuxr
 * @since 2018/11/23 12:17
 */
public class DataBatchDTO extends DataWorkflowDTO {
  private Long medicineId;
  private String batchNo;

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
}
