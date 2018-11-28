package com.wslint.wisdomreport.domain.dto.workflow;

import com.wslint.wisdomreport.domain.dto.data.clazz.DataClassDTO;
import java.util.List;

/**
 * 批次数据复测对象
 *
 * @author yuxr
 * @since 2018/11/27 16:36
 */
public class WorkflowBatchRedoDTO extends WorkflowBatchTaskDTO {

  private List<DataClassDTO> dataClassDTOS;

  public List<DataClassDTO> getDataClassDTOS() {
    return dataClassDTOS;
  }

  public void setDataClassDTOS(List<DataClassDTO> dataClassDTOS) {
    this.dataClassDTOS = dataClassDTOS;
  }
}
