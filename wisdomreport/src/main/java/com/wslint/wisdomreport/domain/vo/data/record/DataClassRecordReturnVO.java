package com.wslint.wisdomreport.domain.vo.data.record;

/**
 * 记录数据返回
 *
 * @author yuxr
 * @since 2018/11/16 11:23
 */
public class DataClassRecordReturnVO extends DataRecordReturnVO {

  private Long firstClassId;
  private Long secondClassId;

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
