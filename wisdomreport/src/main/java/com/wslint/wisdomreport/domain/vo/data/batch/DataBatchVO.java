package com.wslint.wisdomreport.domain.vo.data.batch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 批次对象
 *
 * @author yuxr
 * @since 2018/11/16 10:51
 */
@ApiModel(value = "批次对象")
public class DataBatchVO {

  @ApiModelProperty(value = "药品id", required = true, example = "1")
  private Long medicineId;

  @ApiModelProperty(value = "批次号", required = true, example = "20181120xx")
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
