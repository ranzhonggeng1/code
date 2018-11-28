package com.wslint.wisdomreport.constant;

/**
 * 工作流常量
 *
 * @author yuxr
 * @since 2018/11/9 10:55
 */
public class WorkflowConstant {

  /** 未知状态 */
  public static int UNKNOWN_STATUS = -1;

  public static final String UNKNOWN_STATUS_NAME = "未知状态";
  public static final String UNKNOWN_OPERATION_NAME = "未知操作";

  /** 开始状态 */
  public static int STATUS_START = 0;
  /** 结束状态 */
  public static int STATUS_END = 9;
  /** 开始状态 */
  public static String STATUS_NAME_START = "开始";
  /** 结束状态 */
  public static String STATUS_NAME_END = "结束";

  /** 记录完成操作 */
  public static int OPERATION_COMPLETE = 0;
  /** 记录完成操作 */
  public static String OPERATION_NAME_COMPLETE = "审批完成";

  // 批次工作流定义

  // todo delete start
  /** 批次实验中 */
  public static int BATCH_STATUS_EXPERIMENTING_BAK = 1;
  /** 批次审核中 */
  public static int BATCH_STATUS_APPROVING_BAK = 2;
  /** 批次未通过 */
  public static int BATCH_STATUS_NOT_PASS_BAK = 3;
  /** 批次创建 */
  public static int BATCH_OPERATION_CREATE_BAK = 1;
  /** 批次提交 */
  public static int BATCH_OPERATION_COMMIT_BAK = 2;
  /** 批次通过 */
  public static int BATCH_OPERATION_PASS_BAK = 3;
  /** 批次驳回 */
  public static int BATCH_OPERATION_REJECT_BAK = 4;
  /** 批次重做实验 */
  public static int BATCH_OPERATION_UNDO_BAK = 5;
  /** 默认创建批次数据原因 */
  public static String BATCH_REASON_CRATE = "创建批次数据";
  /** 默认提交批次审核原因 */
  public static String BATCH_REASON_COMMIT = "提交批次审核";
  /** 默认审核批次通过原因 */
  public static String BATCH_REASON_PASS = "审核批次通过";
  /** 默认审核批次驳回原因 */
  public static String BATCH_REASON_REJECT = "审核批次驳回";
  /** 默认重做实验原因 */
  public static String BATCH_REASON_UNDO = "发起重做实验";
  // todo delete end

  /** 批次就绪状态 */
  public static int BATCH_STATUS_EXPERIMENT = 3;
  /** 批次审批中状态 */
  public static int BATCH_STATUS_APPROVE = 5;

  /** 批次就绪状态名称 */
  public static String BATCH_STATUS_NAME_EXPERIMENT = "实验";
  /** 批次审批中状态名称 */
  public static String BATCH_STATUS_NAME_APPROVE = "审批";

  /** 批次创建 */
  public static int BATCH_OPERATION_CREATE = 1;
  /** 批次提交审批操作 */
  public static int BATCH_OPERATION_COMMIT = 5;
  /** 批次审批通过操作 */
  public static int BATCH_OPERATION_PASS = 8;
  /** 批次审批驳回操作 */
  public static int BATCH_OPERATION_REJECT = 9;
  /** 批次审批不通过操作 */
  public static int BATCH_OPERATION_FAIL = 10;
  /** 继续审批 */
  public static int BATCH_OPERATION_APPROVE = 6;
  /** 重做实验 */
  public static int BATCH_OPERATION_REDO = 11;

  /** 批次创建操作名称 */
  public static String BATCH_OPERATION__NAME_CREATE = "批次创建";
  /** 批次提交审批操作名称 */
  public static String BATCH_OPERATION_NAME_COMMIT = "提交审批";
  /** 批次审批通过操作名称 */
  public static String BATCH_OPERATION_NAME_PASS = "审批通过";
  /** 批次审批驳回操作名称 */
  public static String BATCH_OPERATION_NAME_REJECT = "审批驳回";
  /** 批次审批不通过操作名称 */
  public static String BATCH_OPERATION_NAME_FAIL = "审批不通过";
  /** 继续审批 */
  public static String BATCH_OPERATION_NAME_APPROVE = "继续审批";
  /** 重做实验 */
  public static String BATCH_OPERATION_NAME_REDO = "复测";

  // 记录工作流定义

  /** 记录草稿状态 */
  public static int RECORD_STATUS_DRAFT = 101;
  /** 记录复核状态 */
  public static int RECORD_STATUS_REVIEW = 102;
  /** 记录生效状态 */
  public static int RECORD_STATUS_EFFECTIVE = 103;
  /** 记录审批状态 */
  public static int RECORD_STATUS_APPROVE = 105;

  /** 记录草稿状态名称 */
  public static String RECORD_STATUS_NAME_DRAFT = "草稿";
  /** 记录复核状态名称 */
  public static String RECORD_STATUS_NAME_REVIEW = "复核";
  /** 记录生效状态名称 */
  public static String RECORD_STATUS_NAME_EFFECTIVE = "生效";
  /** 记录审批状态名称 */
  public static String RECORD_STATUS_NAME_APPROVE = "审批";

  /** 记录保存操作 */
  public static int RECORD_OPERATION_SAVE = 101;
  /** 记录发起复核操作 */
  public static int RECORD_OPERATION_REVIEW_START = 102;
  /** 记录现场复核操作 */
  public static int RECORD_OPERATION_REVIEW_DIRECT = 103;
  /** 记录复核通过操作 */
  public static int RECORD_OPERATION_REVIEW_PASS = 104;
  /** 记录复核驳回操作 */
  public static int RECORD_OPERATION_REVIEW_REJECT = 106;
  /** 记录草稿修改操作 */
  public static int RECORD_OPERATION_EDIT_DRAFT = 107;
  /** 记录生效修改操作 */
  public static int RECORD_OPERATION_EDIT_EFFECTIVE = 108;


  /** 记录保存操作 */
  public static String RECORD_OPERATION_NAME_SAVE = "保存记录";
  /** 记录发起复核操作 */
  public static String RECORD_OPERATION_NAME_REVIEW_START = "发起复核";
  /** 记录现场复核操作 */
  public static String RECORD_OPERATION_NAME_REVIEW_DIRECT = "现场复核";
  /** 记录复核通过操作 */
  public static String RECORD_OPERATION_NAME_REVIEW_PASS = "复核通过";
  /** 记录复核驳回操作 */
  public static String RECORD_OPERATION_NAME_REVIEW_REJECT = "复核驳回";
  /** 记录草稿修改操作 */
  public static String RECORD_OPERATION_NAME_EDIT_DRAFT = "修改";
  /** 记录生效修改操作 */
  public static String RECORD_OPERATION_NAME_EDIT_EFFECTIVE = "修改";


  // 报告工作流定义

  /** 报告草稿状态 */
  public static int REPORT_STATUS_DRAFT = 201;
  /** 报告复核状态 */
  public static int REPORT_STATUS_REVIEW = 202;
  /** 报告生效状态 */
  public static int REPORT_STATUS_EFFECTIVE = 203;
  /** 报告审批状态 */
  public static int REPORT_STATUS_APPROVE = 205;

  /** 报告草稿状态名称 */
  public static String REPORT_STATUS_NAME_DRAFT = "草稿";
  /** 报告复核状态名称 */
  public static String REPORT_STATUS_NAME_REVIEW = "复核";
  /** 报告生效状态名称 */
  public static String REPORT_STATUS_NAME_EFFECTIVE = "生效";
  /** 报告审批状态名称 */
  public static String REPORT_STATUS_NAME_APPROVE = "审批";

  /** 报告保存操作 */
  public static int REPORT_OPERATION_SAVE = 201;
  /** 报告发起复核操作 */
  public static int REPORT_OPERATION_REVIEW_START = 202;
  /** 报告复核通过操作 */
  public static int REPORT_OPERATION_REVIEW_PASS = 204;
  /** 报告复核驳回操作 */
  public static int REPORT_OPERATION_REVIEW_REJECT = 206;
  /** 报告草稿修改操作 */
  public static int REPORT_OPERATION_EDIT_DRAFT = 207;
  /** 报告生效修改操作 */
  public static int REPORT_OPERATION_EDIT_EFFECTIVE = 208;

  /** 报告保存操作 */
  public static String REPORT_OPERATION_NAME_SAVE = "保存报告";
  /** 报告发起复核操作 */
  public static String REPORT_OPERATION_NAME_REVIEW_START = "发起复核";
  /** 报告复核通过操作 */
  public static String REPORT_OPERATION_NAME_REVIEW_PASS = "复核通过";
  /** 报告复核驳回操作 */
  public static String REPORT_OPERATION_NAME_REVIEW_REJECT = "复核驳回";
  /** 报告草稿修改操作 */
  public static String REPORT_OPERATION_NAME_EDIT_DRAFT = "修改报告";
  /** 报告生效修改操作 */
  public static String REPORT_OPERATION_NAME_EDIT_EFFECTIVE = "修改报告";
}
