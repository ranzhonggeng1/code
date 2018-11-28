package com.wslint.wisdomreport.domain.vo.xml;

/**
 * 大类对象
 *
 * @author yuxr
 * @since 2018/10/30 17:53
 */
public class FirstClassIdNameVO {
  private Long medicineId;
  private Long id;
  private String name;

  public Long getMedicineId() {

    return medicineId;
  }

  public void setMedicineId(Long medicineId) {
    this.medicineId = medicineId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
