package com.wslint.wisdomreport.domain.vo.data.batch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 批次数据获取对象
 *
 * @author yuxr
 * @since 2018/11/12 10:43
 */
@ApiModel(value = "批次数据获取对象")
public class DataBatchGetVO {

  @ApiModelProperty(value = "药品id", required = true, example = "1")
  private Long medicineId;

  @ApiModelProperty(value = "工作流状态", required = true, example = "3")
  private Integer status;

  @ApiModelProperty(value = "第几页", example = "1")
  private Integer page;

  @ApiModelProperty(value = "每页显示条数", example = "5")
  private Integer limit;

  @ApiModelProperty(hidden = true)
  private Integer offset;

  public Long getMedicineId() {
    return medicineId;
  }

  public void setMedicineId(Long medicineId) {
    this.medicineId = medicineId;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public Integer getOffset() {
    return (page - 1) * limit;
  }
}
