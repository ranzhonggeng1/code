package com.wslint.wisdomreport.common;

/**
 * 工作流节点接口
 *
 * @author wslint06
 */
public interface ActivityNode {
  /**
   * 进行下一步
   *
   * @param isPass 当前是否通过
   * @return 下一步状态
   */
  int doNext(boolean isPass);
}
