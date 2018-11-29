package com.wslint.wisdomreport.service.data.impl;

import com.wslint.wisdomreport.constant.ReturnConstant;
import com.wslint.wisdomreport.constant.WorkflowConstant;
import com.wslint.wisdomreport.dao.data.DataBatchDao;
import com.wslint.wisdomreport.dao.data.DataClassDao;
import com.wslint.wisdomreport.dao.data.DataRecordDao;
import com.wslint.wisdomreport.domain.dto.data.clazz.DataClassDTO;
import com.wslint.wisdomreport.domain.dto.data.record.DataRecordDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowRecordTaskDTO;
import com.wslint.wisdomreport.domain.vo.data.record.DataClassRecordReturnVO;
import com.wslint.wisdomreport.exception.DataException;
import com.wslint.wisdomreport.service.data.IDataRecordService;
import com.wslint.wisdomreport.service.workflow.IWorkflowRecordService;
import com.wslint.wisdomreport.utils.BeanCopyUtil;
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
 * 记录数据服务实现
 *
 * @author yuxr
 * @since 2018/11/16 11:29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DataRecordServiceImpl implements IDataRecordService {

  @Autowired private DataRecordDao dataRecordDao;
  @Autowired private DataBatchDao dataBatchDao;
  @Autowired private DataClassDao dataClassDao;
  @Autowired private IWorkflowRecordService iWorkflowRecordService;

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(DataRecordServiceImpl.class);

  /**
   * 保存批次对应的记录数据
   *
   * @param dataRecordDTOList 待保存批次数据
   * @return 处理结果
   */
  @Override
  public Map<String, Object> save(List<DataRecordDTO> dataRecordDTOList) throws Exception {
    LOGGER.info("记录数据保存 - {} 条！", dataRecordDTOList.size());
    List<WorkflowRecordTaskDTO> workflowRecordTaskDTOList = new ArrayList<>();
    // 查处所有类别id对应的数据，并处理出classId和batchId关联的map
    List<DataClassDTO> dataClassDTOList = dataClassDao.getClasssByClassIds(dataRecordDTOList);
    Map<Long, Long> classBatchIdMap = new HashMap<>();
    dataClassDTOList.forEach(data -> classBatchIdMap.put(data.getId(), data.getBatchId()));
    // 判断是否有重复保存的数据，已保存数据走工作流修改
    Map<Long, List<Long>> classOrderIdsMap = new HashMap<>();
    for (DataRecordDTO dataRecordDTO : dataRecordDTOList) {
      completeDataRecordDTO(dataRecordDTO, classBatchIdMap);
      workflowRecordTaskDTOList.add(completeWorkflowRecordDTO(dataRecordDTO.getId()));
      List<Long> orderIds =
          classOrderIdsMap.computeIfAbsent(dataRecordDTO.getClassId(), k -> new ArrayList<>());
      orderIds.add(dataRecordDTO.getOrderId());
    }
    if (dataRecordDao.haveDataByClassOrderIdsMap(classOrderIdsMap)) {
      LOGGER.error("对应空格已有数据！");
      throw new DataException("数据保存失败!");
    }
    if (!dataRecordDao.insertDataRecords(dataRecordDTOList)) {
      LOGGER.error("记录数据保存失败！");
      throw new DataException("数据保存失败!");
    }
    int result = iWorkflowRecordService.doWorkflow(workflowRecordTaskDTOList);
    if (result == ReturnConstant.WORKFLOW_SUCCESS) {
      LOGGER.info("记录数据新增成功！");
      List<DataClassRecordReturnVO> dataClassRecordReturnVOS =
          BeanCopyUtil.copyList(
              getDataRecordsByIds(dataRecordDTOList), DataClassRecordReturnVO.class);
      return ReturnUtils.successMap(dataClassRecordReturnVOS, "记录数据新增成功！");
    }
    LOGGER.error("记录数据新增失败！");
    throw new DataException("数据保存失败!");
  }

  /**
   * 保存记录备注
   *
   * @param dataRecordDTOList 记录对象
   * @return 处理结果
   */
  @Override
  public Map<String, Object> saveRemark(List<DataRecordDTO> dataRecordDTOList)
      throws Exception {
    dataRecordDTOList.forEach(
        data -> {
          data.setRemarker(CommonUtils.getCurrentUserId());
          data.setRemarkTime(CommonUtils.getNowTime());
        });
    if (!dataRecordDao.updateRemarksByClassOrderIds(dataRecordDTOList)) {
      LOGGER.error("更新备注失败！");
      throw new DataException("保存备注失败！");
    }
    LOGGER.info("更新备注成功！");
    return ReturnUtils.successMap("更新备注成功！");
  }

  /**
   * 获取小类对应的记录数据
   *
   * @param medicineId 药品id
   * @param batchNo 批次号
   * @param firstClassId 大类id
   * @param secondClassId 小类id
   * @return 记录数据
   */
  @Override
  public List<DataRecordDTO> getRecordsByClass(
      Long medicineId, String batchNo, Long firstClassId, Long secondClassId) {
    return dataRecordDao.getRecordsByClass(medicineId, batchNo, firstClassId, secondClassId);
  }

  /**
   * 获取批次对应的记录数据
   *
   * @param medicineId 药品id
   * @param batchNo 批次号
   * @return 记录数据
   */
  @Override
  public List<DataRecordDTO> getRecordsByBatch(Long medicineId, String batchNo) {
    return dataRecordDao.getRecordsByBatch(medicineId, batchNo);
  }

  /**
   * 根据状态获取记录数据
   *
   * @param status 状态
   * @return 记录数据
   */
  @Override
  public List<DataRecordDTO> getRecordsByStatus(Integer status) {
    Long userId = CommonUtils.getCurrentUserId();
    return dataRecordDao.getRecordsByStatus(status, userId);
  }

  /**
   * 根据数据id获取记录数据
   *
   * @param dataRecordDTOS 存放记录id的对象
   * @return 查询到的id信息
   */
  private List<DataRecordDTO> getDataRecordsByIds(List<DataRecordDTO> dataRecordDTOS) {
    return dataRecordDao.getDataRecordsByIds(dataRecordDTOS);
  }

  /**
   * 完善记录数据对象
   *
   * @param dataRecordDTO 记录对象
   * @param classBatchIdMap 类别和批次关联id
   */
  private void completeDataRecordDTO(DataRecordDTO dataRecordDTO, Map<Long, Long> classBatchIdMap) {
    Long classId = dataRecordDTO.getClassId();
    dataRecordDTO.setId(CommonUtils.getNextId());
    dataRecordDTO.setBatchId(classBatchIdMap.get(classId));
    dataRecordDTO.setClassId(classId);
    dataRecordDTO.setStatus(WorkflowConstant.STATUS_START);
    dataRecordDTO.setGmtCreate(CommonUtils.getNowTime());
    dataRecordDTO.setGmtModified(CommonUtils.getNowTime());
    dataRecordDTO.setCreateUser(CommonUtils.getCurrentUserId());
    dataRecordDTO.setModifyUser(CommonUtils.getCurrentUserId());
  }

  /**
   * 完善工作流对象数据
   *
   * @param id 数据id
   * @return 工作流对象
   */
  private WorkflowRecordTaskDTO completeWorkflowRecordDTO(Long id) {
    WorkflowRecordTaskDTO workflowRecordTaskDTO = new WorkflowRecordTaskDTO();
    workflowRecordTaskDTO.setRecordId(id);
    workflowRecordTaskDTO.setOperation(WorkflowConstant.RECORD_OPERATION_SAVE);
    return workflowRecordTaskDTO;
  }
}
