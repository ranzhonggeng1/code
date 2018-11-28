package com.wslint.wisdomreport.common;

import com.wslint.wisdomreport.constant.WorkflowConstant;

/**
 * 工作流工具类
 *
 * @author yuxr
 * @since 2018/11/13 16:52
 */
public class WorkflowControl {

  // 批次工作流

  /**
   * 根据状态码获取批次操作前状态码
   *
   * @param operation 操作码
   * @return 状态码
   */
  public static int getBatchStatusByOperation(Integer operation) {
    if (operation == WorkflowConstant.BATCH_OPERATION_CREATE) {
      return WorkflowConstant.STATUS_START;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_COMMIT) {
      return WorkflowConstant.BATCH_STATUS_EXPERIMENT;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_PASS) {
      return WorkflowConstant.BATCH_STATUS_APPROVE;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_REJECT) {
      return WorkflowConstant.BATCH_STATUS_APPROVE;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_FAIL) {
      return WorkflowConstant.BATCH_STATUS_APPROVE;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_APPROVE) {
      return WorkflowConstant.BATCH_STATUS_APPROVE;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_REDO) {
      return WorkflowConstant.BATCH_STATUS_APPROVE;
    }
    return WorkflowConstant.UNKNOWN_STATUS;
  }

  /**
   * 根据状态码获取批次操作后状态码
   *
   * @param operation 操作码
   * @return 状态码
   */
  public static int getBatchNextStatusByOperation(Integer operation) {
    if (operation == WorkflowConstant.BATCH_OPERATION_CREATE) {
      return WorkflowConstant.BATCH_STATUS_EXPERIMENT;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_COMMIT) {
      return WorkflowConstant.BATCH_STATUS_APPROVE;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_PASS) {
      return WorkflowConstant.STATUS_END;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_REJECT) {
      return WorkflowConstant.BATCH_STATUS_EXPERIMENT;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_FAIL) {
      return WorkflowConstant.STATUS_END;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_APPROVE) {
      return WorkflowConstant.BATCH_STATUS_APPROVE;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_REDO) {
      return WorkflowConstant.STATUS_END;
    }
    return WorkflowConstant.UNKNOWN_STATUS;
  }

  /**
   * 根据状态码获取状态名
   *
   * @param status 状态码
   * @return 状态名
   */
  public static String getBatchStatusNameByCode(Integer status) {
    if (status == WorkflowConstant.STATUS_START) {
      return WorkflowConstant.STATUS_NAME_START;
    }
    if (status == WorkflowConstant.STATUS_END) {
      return WorkflowConstant.STATUS_NAME_END;
    }
    if (status == WorkflowConstant.BATCH_STATUS_EXPERIMENT) {
      return WorkflowConstant.BATCH_STATUS_NAME_EXPERIMENT;
    }
    if (status == WorkflowConstant.BATCH_STATUS_APPROVE) {
      return WorkflowConstant.BATCH_STATUS_NAME_APPROVE;
    }
    return WorkflowConstant.UNKNOWN_STATUS_NAME;
  }

  /**
   * 根据操作码获取操作名称
   *
   * @param operation 操作码
   * @return 操作名
   */
  public static String getBatchOperationNameByCode(Integer operation) {
    if (operation == WorkflowConstant.BATCH_OPERATION_CREATE) {
      return WorkflowConstant.BATCH_OPERATION__NAME_CREATE;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_COMMIT) {
      return WorkflowConstant.BATCH_OPERATION_NAME_COMMIT;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_PASS) {
      return WorkflowConstant.BATCH_OPERATION_NAME_PASS;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_REJECT) {
      return WorkflowConstant.BATCH_OPERATION_NAME_REJECT;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_FAIL) {
      return WorkflowConstant.BATCH_OPERATION_NAME_FAIL;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_APPROVE) {
      return WorkflowConstant.BATCH_OPERATION_NAME_APPROVE;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_REDO) {
      return WorkflowConstant.BATCH_OPERATION_NAME_REDO;
    }
    return WorkflowConstant.UNKNOWN_OPERATION_NAME;
  }

  // 记录工作流

  /**
   * 根据操作码获取记录操作前状态码
   *
   * @param operation 操作码
   * @return 状态码
   */
  public static int getRecordStatusByOperation(Integer operation) {
    if (operation == WorkflowConstant.RECORD_OPERATION_SAVE) {
      return WorkflowConstant.STATUS_START;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_REVIEW_START) {
      return WorkflowConstant.RECORD_STATUS_DRAFT;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_REVIEW_DIRECT) {
      return WorkflowConstant.RECORD_STATUS_DRAFT;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_REVIEW_PASS) {
      return WorkflowConstant.RECORD_STATUS_REVIEW;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_REVIEW_REJECT) {
      return WorkflowConstant.RECORD_STATUS_REVIEW;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_EDIT_DRAFT) {
      return WorkflowConstant.RECORD_STATUS_DRAFT;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_EDIT_EFFECTIVE) {
      return WorkflowConstant.RECORD_STATUS_EFFECTIVE;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_COMMIT) {
      return WorkflowConstant.RECORD_STATUS_EFFECTIVE;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_REJECT) {
      return WorkflowConstant.RECORD_STATUS_APPROVE;
    }
    if (operation == WorkflowConstant.OPERATION_COMPLETE) {
      return WorkflowConstant.RECORD_STATUS_APPROVE;
    }
    return WorkflowConstant.UNKNOWN_STATUS;
  }

  /**
   * 根据操作码获取记录操作后状态码
   *
   * @param operation 操作码
   * @return 状态码
   */
  public static int getRecordNextStatusByOperation(Integer operation) {
    if (operation == WorkflowConstant.RECORD_OPERATION_SAVE) {
      return WorkflowConstant.RECORD_STATUS_DRAFT;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_REVIEW_START) {
      return WorkflowConstant.RECORD_STATUS_REVIEW;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_REVIEW_DIRECT) {
      return WorkflowConstant.RECORD_STATUS_EFFECTIVE;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_REVIEW_PASS) {
      return WorkflowConstant.RECORD_STATUS_EFFECTIVE;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_REVIEW_REJECT) {
      return WorkflowConstant.RECORD_STATUS_DRAFT;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_EDIT_DRAFT) {
      return WorkflowConstant.RECORD_STATUS_DRAFT;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_EDIT_EFFECTIVE) {
      return WorkflowConstant.RECORD_STATUS_EFFECTIVE;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_COMMIT) {
      return WorkflowConstant.RECORD_STATUS_APPROVE;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_REJECT) {
      return WorkflowConstant.RECORD_STATUS_EFFECTIVE;
    }
    if (operation == WorkflowConstant.OPERATION_COMPLETE) {
      return WorkflowConstant.STATUS_END;
    }
    return WorkflowConstant.UNKNOWN_STATUS;
  }

  /**
   * 根据状态码获取当前状态名称
   *
   * @param status 状态码
   * @return 状态名称
   */
  public static String getRecordStatusNameByCode(Integer status) {
    String batchStatusName = getBatchStatusNameByCode(status);
    if (!batchStatusName.equals(WorkflowConstant.UNKNOWN_STATUS_NAME)) {
      return batchStatusName;
    }
    if (status == WorkflowConstant.RECORD_STATUS_DRAFT) {
      return WorkflowConstant.RECORD_STATUS_NAME_DRAFT;
    }
    if (status == WorkflowConstant.RECORD_STATUS_REVIEW) {
      return WorkflowConstant.RECORD_STATUS_NAME_REVIEW;
    }
    if (status == WorkflowConstant.RECORD_STATUS_EFFECTIVE) {
      return WorkflowConstant.RECORD_STATUS_NAME_EFFECTIVE;
    }
    if (status == WorkflowConstant.RECORD_STATUS_APPROVE) {
      return WorkflowConstant.RECORD_STATUS_NAME_APPROVE;
    }
    return WorkflowConstant.UNKNOWN_STATUS_NAME;
  }

  /**
   * 根据操作码获取操作名
   *
   * @param operation 操作码
   * @return 操作名
   */
  public static String getRecordOperationNameByCode(Integer operation) {
    String batchOperationName = getBatchOperationNameByCode(operation);
    if (!batchOperationName.equals(WorkflowConstant.UNKNOWN_OPERATION_NAME)) {
      return batchOperationName;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_SAVE) {
      return WorkflowConstant.RECORD_OPERATION_NAME_SAVE;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_REVIEW_START) {
      return WorkflowConstant.RECORD_OPERATION_NAME_REVIEW_START;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_REVIEW_DIRECT) {
      return WorkflowConstant.RECORD_OPERATION_NAME_REVIEW_DIRECT;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_REVIEW_PASS) {
      return WorkflowConstant.RECORD_OPERATION_NAME_REVIEW_PASS;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_REVIEW_REJECT) {
      return WorkflowConstant.RECORD_OPERATION_NAME_REVIEW_REJECT;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_EDIT_DRAFT) {
      return WorkflowConstant.RECORD_OPERATION_NAME_EDIT_DRAFT;
    }
    if (operation == WorkflowConstant.RECORD_OPERATION_EDIT_EFFECTIVE) {
      return WorkflowConstant.RECORD_OPERATION_NAME_EDIT_EFFECTIVE;
    }
    if (operation == WorkflowConstant.OPERATION_COMPLETE) {
      return WorkflowConstant.OPERATION_NAME_COMPLETE;
    }
    return WorkflowConstant.UNKNOWN_OPERATION_NAME;
  }

  /**
   * 是否会编辑数据
   *
   * @param operation 记录操作码
   * @return 是否可以编辑数据
   */
  public static boolean isEditRecordData(int operation) {
    return operation == WorkflowConstant.RECORD_OPERATION_EDIT_DRAFT
        || operation == WorkflowConstant.RECORD_OPERATION_EDIT_EFFECTIVE;
  }

  // 报告工作流

  /**
   * 根据操作码获取报告操作前状态码
   *
   * @param operation 操作码
   * @return 状态码
   */
  public static int getReportStatusByOperation(Integer operation) {
    if (operation == WorkflowConstant.REPORT_OPERATION_SAVE) {
      return WorkflowConstant.STATUS_START;
    }
    if (operation == WorkflowConstant.REPORT_OPERATION_REVIEW_START) {
      return WorkflowConstant.REPORT_STATUS_DRAFT;
    }
    if (operation == WorkflowConstant.REPORT_OPERATION_REVIEW_PASS) {
      return WorkflowConstant.REPORT_STATUS_REVIEW;
    }
    if (operation == WorkflowConstant.REPORT_OPERATION_REVIEW_REJECT) {
      return WorkflowConstant.REPORT_STATUS_REVIEW;
    }
    if (operation == WorkflowConstant.REPORT_OPERATION_EDIT_DRAFT) {
      return WorkflowConstant.REPORT_STATUS_DRAFT;
    }
    if (operation == WorkflowConstant.REPORT_OPERATION_EDIT_EFFECTIVE) {
      return WorkflowConstant.REPORT_STATUS_EFFECTIVE;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_COMMIT) {
      return WorkflowConstant.REPORT_STATUS_EFFECTIVE;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_REJECT) {
      return WorkflowConstant.REPORT_STATUS_APPROVE;
    }
    if (operation == WorkflowConstant.OPERATION_COMPLETE) {
      return WorkflowConstant.REPORT_STATUS_APPROVE;
    }
    return WorkflowConstant.UNKNOWN_STATUS;
  }

  /**
   * 根据操作码获取报告操作后状态码
   *
   * @param operation 操作码
   * @return 状态码
   */
  public static int getReportNextStatusByOperation(Integer operation) {
    if (operation == WorkflowConstant.REPORT_OPERATION_SAVE) {
      return WorkflowConstant.REPORT_STATUS_DRAFT;
    }
    if (operation == WorkflowConstant.REPORT_OPERATION_REVIEW_START) {
      return WorkflowConstant.REPORT_STATUS_REVIEW;
    }
    if (operation == WorkflowConstant.REPORT_OPERATION_REVIEW_PASS) {
      return WorkflowConstant.REPORT_STATUS_EFFECTIVE;
    }
    if (operation == WorkflowConstant.REPORT_OPERATION_REVIEW_REJECT) {
      return WorkflowConstant.REPORT_STATUS_DRAFT;
    }
    if (operation == WorkflowConstant.REPORT_OPERATION_EDIT_DRAFT) {
      return WorkflowConstant.REPORT_STATUS_DRAFT;
    }
    if (operation == WorkflowConstant.REPORT_OPERATION_EDIT_EFFECTIVE) {
      return WorkflowConstant.REPORT_STATUS_EFFECTIVE;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_COMMIT) {
      return WorkflowConstant.REPORT_STATUS_APPROVE;
    }
    if (operation == WorkflowConstant.BATCH_OPERATION_REJECT) {
      return WorkflowConstant.REPORT_STATUS_EFFECTIVE;
    }
    if (operation == WorkflowConstant.OPERATION_COMPLETE) {
      return WorkflowConstant.STATUS_END;
    }
    return WorkflowConstant.UNKNOWN_STATUS;
  }

  /**
   * 根据状态码获取当前状态名称
   *
   * @param status 状态码
   * @return 状态名称
   */
  public static String getReportStatusNameByCode(Integer status) {
    String batchStatusName = getBatchStatusNameByCode(status);
    if (!batchStatusName.equals(WorkflowConstant.UNKNOWN_STATUS_NAME)) {
      return batchStatusName;
    }
    if (status == WorkflowConstant.REPORT_STATUS_DRAFT) {
      return WorkflowConstant.REPORT_STATUS_NAME_DRAFT;
    }
    if (status == WorkflowConstant.REPORT_STATUS_REVIEW) {
      return WorkflowConstant.REPORT_STATUS_NAME_REVIEW;
    }
    if (status == WorkflowConstant.REPORT_STATUS_EFFECTIVE) {
      return WorkflowConstant.REPORT_STATUS_NAME_EFFECTIVE;
    }
    if (status == WorkflowConstant.REPORT_STATUS_APPROVE) {
      return WorkflowConstant.REPORT_STATUS_NAME_APPROVE;
    }
    return WorkflowConstant.UNKNOWN_STATUS_NAME;
  }

  /**
   * 根据操作码获取操作名
   *
   * @param operation 操作码
   * @return 操作名
   */
  public static String getReportOperationNameByCode(Integer operation) {
    String batchOperationName = getBatchOperationNameByCode(operation);
    if (!batchOperationName.equals(WorkflowConstant.UNKNOWN_OPERATION_NAME)) {
      return batchOperationName;
    }
    if (operation == WorkflowConstant.REPORT_OPERATION_SAVE) {
      return WorkflowConstant.REPORT_OPERATION_NAME_SAVE;
    }
    if (operation == WorkflowConstant.REPORT_OPERATION_REVIEW_START) {
      return WorkflowConstant.REPORT_OPERATION_NAME_REVIEW_START;
    }
    if (operation == WorkflowConstant.REPORT_OPERATION_REVIEW_PASS) {
      return WorkflowConstant.REPORT_OPERATION_NAME_REVIEW_PASS;
    }
    if (operation == WorkflowConstant.REPORT_OPERATION_REVIEW_REJECT) {
      return WorkflowConstant.REPORT_OPERATION_NAME_REVIEW_REJECT;
    }
    if (operation == WorkflowConstant.REPORT_OPERATION_EDIT_DRAFT) {
      return WorkflowConstant.REPORT_OPERATION_NAME_EDIT_DRAFT;
    }
    if (operation == WorkflowConstant.REPORT_OPERATION_EDIT_EFFECTIVE) {
      return WorkflowConstant.REPORT_OPERATION_NAME_EDIT_EFFECTIVE;
    }
    if (operation == WorkflowConstant.OPERATION_COMPLETE) {
      return WorkflowConstant.OPERATION_NAME_COMPLETE;
    }
    return WorkflowConstant.UNKNOWN_OPERATION_NAME;
  }

  /**
   * 是否会编辑数据
   *
   * @param operation 报告操作码
   * @return 是否可以编辑数据
   */
  public static boolean isEditReportData(int operation) {
    return operation == WorkflowConstant.REPORT_OPERATION_EDIT_DRAFT
        || operation == WorkflowConstant.REPORT_OPERATION_EDIT_EFFECTIVE;
  }
}
