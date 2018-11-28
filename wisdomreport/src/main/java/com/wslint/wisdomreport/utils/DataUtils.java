package com.wslint.wisdomreport.utils;

import com.wslint.wisdomreport.domain.dto.data.DataWorkflowDTO;
import com.wslint.wisdomreport.domain.dto.user.User;
import com.wslint.wisdomreport.service.user.IUserService;
import com.wslint.wisdomreport.service.user.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据工具类
 *
 * @author yuxr
 * @since 2018/11/21 15:35
 */
public class DataUtils {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(DataUtils.class);

  private static IUserService iUserService = new UserServiceImpl();

  /**
   * 走工作流的数据做权限校验
   *
   * @param dataWorkflowDTO 走工作流的数据
   * @return 是否校验通过
   */
  public static boolean checkWorkflowRight(DataWorkflowDTO dataWorkflowDTO) {
    if (dataWorkflowDTO.getNextOperator() != null
        && !dataWorkflowDTO.getNextOperator().equals(CommonUtils.getCurrentUserId())) {
      User dataUser = iUserService.getUserById(dataWorkflowDTO.getNextOperator());
      User currentUser = CommonUtils.getCurrentUser();
      LOGGER.error(
          "工作流权限校验失败!当前数据待审核人为:{}-{}, 当前用户为:{}-{}",
          dataUser.getUserCode(),
          dataUser.getUserName(),
          currentUser.getUserCode(),
          currentUser.getUserName());
      return false;
    }
    return true;
  }
}
