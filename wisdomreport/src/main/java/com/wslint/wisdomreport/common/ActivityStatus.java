package com.wslint.wisdomreport.common;

/**
 * 工作流状态记录类
 *
 * @author wslint06
 */
public enum ActivityStatus implements ActivityNode {

  /** 状态1，实验中状态 */
  STATUS_PROCESSING(1) {
    @Override
    public int doNext(boolean isPass) {
      return STATUS_APPROVING.value;
    }
  },
  /** 状态2，审批中状态 */
  STATUS_APPROVING(2) {
    @Override
    public int doNext(boolean isPass) {
      if (isPass) {
        return STATUS_COMPLETED.value;
      } else {
        return STATUS_APPROVE_NOT_PASS.value;
      }
    }
  },
  /** 状态3，已完成状态 */
  STATUS_COMPLETED(3) {
    @Override
    public int doNext(boolean isPass) {
      return 0;
    }
  },
  /** 状态4，审批未通过状态 */
  STATUS_APPROVE_NOT_PASS(4) {
    @Override
    public int doNext(boolean isPass) {
      return STATUS_PROCESSING.value;
    }
  };

  private int value;

  ActivityStatus(int value) {
    this.value = value;
  }

  public int getValue() {
    return this.value;
  }
}
