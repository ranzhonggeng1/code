package com.wslint.wisdomreport.domain.vo.xml;

/**
 * 方法参数关联关系保存数据对象
 *
 * @author yuxr
 * @since 2018/11/1 16:43
 */
public class RelationVO {

  private Long medicineId;
  private Long secondId;
  private Long functionId;
  private Long tableId;
  private String code;
  private String value;

  public Long getMedicineId() {
    return medicineId;
  }

  public void setMedicineId(Long medicineId) {
    this.medicineId = medicineId;
  }

  public Long getSecondId() {
    return secondId;
  }

  public void setSecondId(Long secondId) {
    this.secondId = secondId;
  }

  public Long getFunctionId() {
    return functionId;
  }

  public void setFunctionId(Long functionId) {
    this.functionId = functionId;
  }

  public Long getTableId() {
    return tableId;
  }

  public void setTableId(Long tableId) {
    this.tableId = tableId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
