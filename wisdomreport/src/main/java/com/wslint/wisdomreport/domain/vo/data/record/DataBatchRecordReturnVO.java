package com.wslint.wisdomreport.domain.vo.data.record;

/**
 * 类别记录数据查询返回
 *
 * @author yuxr
 * @since 2018/11/16 11:46
 */
public class DataBatchRecordReturnVO extends DataClassRecordReturnVO {

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
