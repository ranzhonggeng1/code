package com.wslint.wisdomreport.controller.common;

import com.wslint.wisdomreport.utils.CommonUtils;
import com.wslint.wisdomreport.utils.ReturnUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.sql.Timestamp;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提供给外部的server工具接口
 *
 * @author yuxr
 * @since 2018/10/11 9:42
 */
@Api(tags = "9 系统工具接口", description = "提供系统工具的相关接口")
@RestController
@RequestMapping(value = "/util")
public class UtilController {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(UtilController.class);

  /**
   * 查看系统时间
   *
   * @return 处理结果
   */
  @ApiOperation(value = "获取服务器当前时间接口", notes = "获取服务器当前时间")
  @RequestMapping(value = "/time", method = RequestMethod.GET)
  public Map<String, Object> time() {
    Timestamp timestamp = CommonUtils.getNowTime();
    LOGGER.info("系统当前时间 {} ", timestamp.toString());
    return ReturnUtils.successMap(timestamp, "获取系统当前时间成功！");
  }
}
