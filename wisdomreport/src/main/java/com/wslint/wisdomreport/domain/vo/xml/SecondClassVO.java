package com.wslint.wisdomreport.domain.vo.xml;

/**
 * 小类对象
 *
 * @author yuxr
 * @since 2018/10/30 17:20
 */
public class SecondClassVO {
  private Long medicineId;
  private Long firstClassId;
  private String name;

  public Long getMedicineId() {
    return medicineId;
  }

  public void setMedicineId(Long medicineId) {
    this.medicineId = medicineId;
  }

  public Long getFirstClassId() {
    return firstClassId;
  }

  public void setFirstClassId(Long firstClassId) {
    this.firstClassId = firstClassId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
