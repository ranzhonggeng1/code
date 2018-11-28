package com.wslint.wisdomreport.domain.vo.xml;

import com.wslint.wisdomreport.domain.dto.xml.component.ColumnDTO;
import java.util.List;

/**
 * 方法参数
 *
 * @author yuxr
 * @since 2018/11/1 10:58
 */
public class MethodAddVO {

  private Long medicineId;
  private Long secondId;
  private Long functionId;
  private Long tableId;
  private List<ColumnDTO> columnDTOList;

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

  public List<ColumnDTO> getColumnDTOList() {
    return columnDTOList;
  }

  public void setColumnDTOList(List<ColumnDTO> columnDTOList) {
    this.columnDTOList = columnDTOList;
  }
}
