package com.wslint.wisdomreport.domain.vo.data.batch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 待办类表查询结果对象
 *
 * @author yuxr
 * @since 2018/11/12 14:35
 */
@ApiModel(value = "待办列表数据")
public class DataBatchTodoReturnVO {

  @ApiModelProperty(value = "批次列表数据id")
  private Long id;

  @ApiModelProperty(value = "药品id")
  private Long medicineId;

  @ApiModelProperty(value = "批次号")
  private String batchNo;

  @ApiModelProperty(value = "批次数据状态")
  private Integer status;

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

  public String getBatchNo() {
    return batchNo;
  }

  public void setBatchNo(String batchNo) {
    this.batchNo = batchNo;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
