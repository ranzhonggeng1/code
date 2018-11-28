package com.wslint.wisdomreport.domain.vo.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 批次创建对象数据
 *
 * @author yuxr
 * @since 2018/11/9 11:40
 */
@ApiModel(value = "创建批次参数")
public class BatchCreateVO {
  @ApiModelProperty(value = "药品id", required = true, example = "1")
  private Long medicineId;

  @ApiModelProperty(value = "批次号", required = true, example = "20181120xx")
  private String batchNo;

  @ApiModelProperty(value = "创建原因", required = false, example = "测试创建批次数据")
  private String reason;

  @ApiModelProperty(value = "大类对象", required = true)
  private List<FirstClassIdVO> firstClassIdVOS;

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

  public List<FirstClassIdVO> getFirstClassIdVOS() {
    return firstClassIdVOS;
  }

  public void setFirstClassIdVOS(List<FirstClassIdVO> firstClassIdVOS) {
    this.firstClassIdVOS = firstClassIdVOS;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }
}
