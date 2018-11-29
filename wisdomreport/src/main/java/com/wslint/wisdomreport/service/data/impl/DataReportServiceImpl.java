package com.wslint.wisdomreport.service.data.impl;

import com.wslint.wisdomreport.constant.ReturnConstant;
import com.wslint.wisdomreport.constant.WorkflowConstant;
import com.wslint.wisdomreport.dao.data.DataReportDao;
import com.wslint.wisdomreport.domain.dto.data.report.DataReportDTO;
import com.wslint.wisdomreport.domain.dto.workflow.WorkflowReportTaskDTO;
import com.wslint.wisdomreport.domain.vo.data.report.DataBatchReportReturnVO;
import com.wslint.wisdomreport.exception.DataException;
import com.wslint.wisdomreport.service.data.IDataReportService;
import com.wslint.wisdomreport.service.workflow.IWorkflowReportService;
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
 * 报告数据服务实现
 *
 * @author yuxr
 * @since 2018/11/16 11:29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DataReportServiceImpl implements IDataReportService {

  @Autowired private DataReportDao dataReportDao;
  @Autowired private IWorkflowReportService iWorkflowReportService;

  /** logger 日志报告 */
  private static final Logger LOGGER = LoggerFactory.getLogger(DataReportServiceImpl.class);

  /**
   * 保存批次对应的报告数据
   *
   * @param dataReportDTOList 待保存批次数据
   * @return 处理结果
   */
  @Override
  public Map<String, Object> save(List<DataReportDTO> dataReportDTOList) throws Exception {
    LOGGER.info("报告数据保存 - {} 条！", dataReportDTOList.size());
    List<WorkflowReportTaskDTO> workflowReportTaskDTOList = new ArrayList<>();
    // 判断是否有重复保存的数据，已保存数据走工作流修改
    Map<Long, List<Long>> batchOrderIdsMap = new HashMap<>();
    for (DataReportDTO dataReportDTO : dataReportDTOList) {
      completeDataReportDTO(dataReportDTO);
      workflowReportTaskDTOList.add(completeWorkflowReportDTO(dataReportDTO.getId()));
      List<Long> orderIds =
          batchOrderIdsMap.computeIfAbsent(dataReportDTO.getBatchId(), k -> new ArrayList<>());
      orderIds.add(dataReportDTO.getOrderId());
    }
    if (dataReportDao.haveDataByBatchOrderIdsMap(batchOrderIdsMap)) {
      LOGGER.error("对应空格已有数据！");
      throw new DataException("数据保存失败!");
    }
    if (!dataReportDao.insertDataReports(dataReportDTOList)) {
      LOGGER.error("报告数据保存失败！");
      throw new DataException("数据保存失败!");
    }
    int result = iWorkflowReportService.doWorkflow(workflowReportTaskDTOList);
    if (result == ReturnConstant.WORKFLOW_SUCCESS) {
      LOGGER.info("报告数据新增成功！");
      List<DataBatchReportReturnVO> dataClassReportReturnVOS =
          BeanCopyUtil.copyList(
              getDataReportsByIds(dataReportDTOList), DataBatchReportReturnVO.class);
      return ReturnUtils.successMap(dataClassReportReturnVOS, "报告数据新增成功！");
    }
    LOGGER.error("报告数据新增失败！");
    throw new DataException("数据保存失败!");
  }

  /**
   * 保存报告备注
   *
   * @param dataReportDTOList 数据对象
   * @return 处理结果
   */
  @Override
  public Map<String, Object> saveRemark(List<DataReportDTO> dataReportDTOList) throws Exception {
    dataReportDTOList.forEach(
        data -> {
          data.setRemarker(CommonUtils.getCurrentUserId());
          data.setRemarkTime(CommonUtils.getNowTime());
        });
      if (!dataReportDao.updateRemarksByClassOrderIds(dataReportDTOList)) {
        LOGGER.error("更新备注失败！");
        throw new DataException("保存备注失败！");
      }
      LOGGER.info("更新备注成功！");
      return ReturnUtils.successMap("更新备注成功！");
    }

  /**
   * 获取批次对应的报告数据
   *
   * @param medicineId 药品id
   * @param batchNo 批次号
   * @return 报告数据
   */
  @Override
  public List<DataReportDTO> getReportsByBatch(Long medicineId, String batchNo) {
    return dataReportDao.getReportsByBatch(medicineId, batchNo);
  }

  /**
   * 根据状态获取报告数据
   *
   * @param status 状态
   * @return 报告数据
   */
  @Override
  public List<DataReportDTO> getReportsByStatus(Integer status) {
    Long userId = CommonUtils.getCurrentUserId();
    return dataReportDao.getReportsByStatus(status, userId);
  }

  /**
   * 根据数据id获取报告数据
   *
   * @param dataReportDTOS 存放报告id的对象
   * @return 查询到的id信息
   */
  private List<DataReportDTO> getDataReportsByIds(List<DataReportDTO> dataReportDTOS) {
    return dataReportDao.getDataReportsByIds(dataReportDTOS);
  }

  /**
   * 完善报告数据对象
   *
   * @param dataReportDTO 报告对象
   */
  private void completeDataReportDTO(DataReportDTO dataReportDTO) {
    dataReportDTO.setId(CommonUtils.getNextId());
    dataReportDTO.setBatchId(dataReportDTO.getBatchId());
    dataReportDTO.setStatus(WorkflowConstant.STATUS_START);
    dataReportDTO.setGmtCreate(CommonUtils.getNowTime());
    dataReportDTO.setGmtModified(CommonUtils.getNowTime());
    dataReportDTO.setCreateUser(CommonUtils.getCurrentUserId());
    dataReportDTO.setModifyUser(CommonUtils.getCurrentUserId());
  }

  /**
   * 完善工作流对象数据
   *
   * @param id 数据id
   * @return 工作流对象
   */
  private WorkflowReportTaskDTO completeWorkflowReportDTO(Long id) {
    WorkflowReportTaskDTO workflowReportTaskDTO = new WorkflowReportTaskDTO();
    workflowReportTaskDTO.setReportId(id);
    workflowReportTaskDTO.setOperation(WorkflowConstant.REPORT_OPERATION_SAVE);
    return workflowReportTaskDTO;
  }
}
