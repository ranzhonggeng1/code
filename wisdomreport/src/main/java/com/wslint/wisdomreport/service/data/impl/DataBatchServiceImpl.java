package com.wslint.wisdomreport.service.data.impl;

import com.wslint.wisdomreport.constant.ReturnConstant;
import com.wslint.wisdomreport.constant.WorkflowConstant;
import com.wslint.wisdomreport.dao.data.DataBatchDao;
import com.wslint.wisdomreport.dao.data.DataClassDao;
import com.wslint.wisdomreport.domain.dto.data.batch.DataBatchDTO;
import com.wslint.wisdomreport.domain.dto.data.clazz.DataClassDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowBatchTaskDTO;
import com.wslint.wisdomreport.exception.DataException;
import com.wslint.wisdomreport.service.data.IDataBatchService;
import com.wslint.wisdomreport.service.workflow.IWorkflowBatchService;
import com.wslint.wisdomreport.utils.CommonUtils;
import com.wslint.wisdomreport.utils.ReturnUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 批次数据处理服务实现
 *
 * @author yuxr
 * @since 2018/11/9 11:54
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DataBatchServiceImpl implements IDataBatchService {

  /** logger 日志批次 */
  private static final Logger LOGGER = LoggerFactory.getLogger(DataBatchServiceImpl.class);

  @Autowired private DataBatchDao dataBatchDao;
  @Autowired private DataClassDao dataClassDao;
  @Autowired private IWorkflowBatchService iWorkflowBatchService;

  /**
   * 创建批次数据
   *
   * @param dataClassDTOList 批次数据对象
   * @return 处理结果
   */
  @Override
  public Map<String, Object> save(List<DataClassDTO> dataClassDTOList) throws Exception {
    List<WorkflowBatchTaskDTO> workflowBatchTaskDTOList = new ArrayList<>();
    List<DataBatchDTO> dataBatchDTOList = new ArrayList<>();
    Map<Long, List<String>> medicineIdBatchNosMap = new HashMap<>();
    Map<String, Long> batchIdMap = new HashMap<>();
    for (DataClassDTO dataClassDTO : dataClassDTOList) {
      Long medicineId = dataClassDTO.getMedicineId();
      String batchNo = dataClassDTO.getBatchNo();
      List<String> batchNos =
          medicineIdBatchNosMap.computeIfAbsent(medicineId, k -> new ArrayList<>());
      batchNos.add(batchNo);
      Long batchId =
          batchIdMap.computeIfAbsent(
              CommonUtils.getKey(medicineId, batchNo),
              k -> {
                DataBatchDTO dataBatchDTO = completeDataBatchDTO(dataClassDTO);
                dataBatchDTOList.add(dataBatchDTO);
                workflowBatchTaskDTOList.add(completeWorkflowBatchDTO(dataBatchDTO.getId()));
                return dataBatchDTO.getId();
              });
      dataClassDTO.setOldBatchId(dataClassDTO.getBatchId());
      dataClassDTO.setBatchId(batchId);
      completeDataClassDTO(dataClassDTO);
    }
    if (dataBatchDao.haveDataByMedicineIdBatchNosMap(medicineIdBatchNosMap)) {
      LOGGER.error("当前药品批次已存在！");
      throw new DataException("数据保存失败!");
    }
    if (!dataBatchDao.insertDataBatchs(dataBatchDTOList)) {
      LOGGER.error("批次数据保存失败！");
      throw new DataException("数据保存失败!");
    }
    if (!dataClassDao.insertDataClasss(dataClassDTOList)) {
      LOGGER.error("分类数据保存失败！");
      throw new DataException("数据保存失败!");
    }
    int result = iWorkflowBatchService.doWorkflow(workflowBatchTaskDTOList);
    if (result == ReturnConstant.WORKFLOW_SUCCESS) {
      LOGGER.info("创建批次成功！");
      return ReturnUtils.successMap("创建批次成功！");
    }
    LOGGER.error("创建批次失败！");
    throw new DataException("数据保存失败!");
  }

  /**
   * 完善类型数据对象
   *
   * @param dataClassDTO 类型对象
   */
  private void completeDataClassDTO(DataClassDTO dataClassDTO) {
    // 留存原id
    dataClassDTO.setOldClassId(dataClassDTO.getId());
    dataClassDTO.setId(CommonUtils.getNextId());
    dataClassDTO.setGmtCreate(CommonUtils.getNowTime());
    dataClassDTO.setGmtModified(CommonUtils.getNowTime());
    dataClassDTO.setCreateUser(CommonUtils.getCurrentUserId());
    dataClassDTO.setModifyUser(CommonUtils.getCurrentUserId());
  }

  /**
   * 完善批次数据对象
   *
   * @param dataClassDTO 批次对象
   */
  private DataBatchDTO completeDataBatchDTO(DataClassDTO dataClassDTO) {
    DataBatchDTO dataBatchDTO = new DataBatchDTO();
    dataBatchDTO.setId(CommonUtils.getNextId());
    dataBatchDTO.setMedicineId(dataClassDTO.getMedicineId());
    dataBatchDTO.setBatchNo(dataClassDTO.getBatchNo());
    dataBatchDTO.setStatus(WorkflowConstant.STATUS_START);
    dataBatchDTO.setGmtCreate(CommonUtils.getNowTime());
    dataBatchDTO.setGmtModified(CommonUtils.getNowTime());
    dataBatchDTO.setCreateUser(CommonUtils.getCurrentUserId());
    dataBatchDTO.setModifyUser(CommonUtils.getCurrentUserId());
    return dataBatchDTO;
  }

  /**
   * 完善工作流对象数据
   *
   * @param id 数据id
   * @return 工作流对象
   */
  private WorkflowBatchTaskDTO completeWorkflowBatchDTO(Long id) {
    WorkflowBatchTaskDTO workflowBatchTaskDTO = new WorkflowBatchTaskDTO();
    workflowBatchTaskDTO.setBatchId(id);
    workflowBatchTaskDTO.setOperation(WorkflowConstant.BATCH_OPERATION_CREATE);
    return workflowBatchTaskDTO;
  }

  /**
   * 根据药品id和状态获取批次数据
   *
   * @param medicineId 药品id
   * @param status 状态码
   * @param offset 偏移量
   * @param limit 限制数
   * @return 批次数据
   */
  @Override
  public List<DataBatchDTO> getDataBatchByMedicineIdAndStatus(
      Long medicineId, Integer status, Integer offset, Integer limit) {
    return dataBatchDao.getDataBatchByMedicineIdAndStatus(medicineId, status, offset, limit);
  }

  /**
   * 获取当前操作人的待办列表
   *
   * @return 批次数据
   */
  @Override
  public List<DataBatchDTO> getDataBatchByNextOperator() {
    Long nextOperator = CommonUtils.getCurrentUserId();
    return dataBatchDao.getDataBatchByNextOperator(nextOperator);
  }
}
