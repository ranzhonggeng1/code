package com.wslint.wisdomreport.domain.vo.xml;

/**
 * 删除方法参数
 *
 * @author yuxr
 * @since 2018/11/1 16:25
 */
public class MethodDeleteVO {
  private Long medicineId;
  private Long secondId;
  private Long functionId;
  private Long tableId;
  private Long rowId;

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

  public Long getRowId() {
    return rowId;
  }

  public void setRowId(Long rowId) {
    this.rowId = rowId;
  }
}
