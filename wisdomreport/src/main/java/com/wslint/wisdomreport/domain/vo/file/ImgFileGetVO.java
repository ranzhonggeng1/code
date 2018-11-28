package com.wslint.wisdomreport.domain.vo.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 图像文件获取对象
 *
 * @author yuxr
 * @since 2018/11/7 15:06
 */
@ApiModel
public class ImgFileGetVO {
  @ApiModelProperty(value = "药品id", required = true, example = "1")
  private Long medicineId;

  @ApiModelProperty(value = "批次号", required = true, example = "20181010")
  private Long batchNo;

  @ApiModelProperty(value = "大类id", required = true, example = "3")
  private Long firstClassId;

  @ApiModelProperty(value = "小类id", required = true, example = "12")
  private Long secondClassId;

  public Long getMedicineId() {
    return medicineId;
  }

  public void setMedicineId(Long medicineId) {
    this.medicineId = medicineId;
  }

  public Long getBatchNo() {
    return batchNo;
  }

  public void setBatchNo(Long batchNo) {
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
}
