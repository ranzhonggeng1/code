package com.wslint.wisdomreport.controller.file;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.constant.FileConstant;
import com.wslint.wisdomreport.domain.dto.file.CfgfileVersion;
import com.wslint.wisdomreport.service.config.system.ISysConfigService;
import com.wslint.wisdomreport.service.file.IFileService;
import com.wslint.wisdomreport.utils.ReturnUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 下载接口
 *
 * @author yuxr
 * @since 2018/9/18 13:56
 */
@Api(tags = "4 文件接口", description = "提供文件管理的相关接口")
@RestController
@RequestMapping(value = "/file/download")
public class DownloadController {

  @Autowired private IFileService iFileService;
  @Autowired private ISysConfigService iSysConfigService;

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(DownloadController.class);

  /**
   * 校验配置文件
   *
   * @param param 校验参数
   * @return 校验结果
   */
  @ApiOperation(value = "检查药品文件更新接口", notes = "检查药品文件更新")
  @ApiImplicitParam(
      name = "cfgfileVersion",
      value = "配置信息",
      required = true,
      dataType = "CfgfileVersion")
  @RequestMapping(value = "/check", method = RequestMethod.POST)
  public Map<String, Object> check(@RequestBody CfgfileVersion param) {
    if (param.getMedicineId() == null) {
      LOGGER.info("药品ID为空！");
      return ReturnUtils.failureMap("药品ID为空！");
    }
    // 获取配置信息
    CfgfileVersion cfgfileVersion =
        iFileService.getCfgfileVersionByMedicineId(param.getMedicineId());
    Map<String, Object> checkMap = new HashMap<>(Constant.NUM_2);
    checkMap.put(Constant.STR_VERSION, cfgfileVersion.getVersion());
    // 判断是否需要更新
    if (param.getVersion() == null || !param.getVersion().equals(cfgfileVersion.getVersion())) {
      checkMap.put(Constant.STR_UPDATE, true);
      LOGGER.info("App版本号为：{} 系统版本号为：{} 需要更新！", param.getVersion(), cfgfileVersion.getVersion());
      return ReturnUtils.successMap(checkMap, "校验成功！");
    }
    checkMap.put(Constant.STR_UPDATE, false);
    return ReturnUtils.successMap(checkMap, "校验成功！");
  }

  /**
   * 下载配置文件
   *
   * @param request 请求
   * @param response 回应
   * @throws Exception 文件异常
   */
  @ApiOperation(value = "下载系统配置文件接口", notes = "下载药品配置文件")
  @RequestMapping(value = "/config", method = RequestMethod.GET)
  public void config(HttpServletRequest request, HttpServletResponse response) throws Exception {
    // 获取下载路径
    CfgfileVersion cfgfileVersion =
        getCfgfileVersionByMedicineIdAndVersion(Constant.ID_0, Constant.STR_0);
    // 调用下载服务
    iFileService.download(response, cfgfileVersion.getPath(), cfgfileVersion.getFileName());
  }

  /**
   * 药品资源文件下载
   *
   * @param request 请求
   * @param response 回应
   * @param medicineId 药品ID
   * @param version 版本号
   * @throws Exception 文件异常
   */
  @ApiOperation(value = "下载药品配置文件接口", notes = "下载药品配置文件")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "medicineId", value = "药品ID", required = true, paramType = "query"),
    @ApiImplicitParam(name = "version", value = "文件版本", required = true, paramType = "query"),
  })
  @RequestMapping(value = "/medicine", method = RequestMethod.GET)
  public void medicine(
      HttpServletRequest request,
      HttpServletResponse response,
      @RequestParam Long medicineId,
      @RequestParam String version)
      throws Exception {
    if (medicineId == null || version.isEmpty()) {
      LOGGER.info("药品ID或文件版本号为空！");
      throw new Exception("药品ID或文件版本号为空！");
    }
    CfgfileVersion cfgfileVersion = getCfgfileVersionByMedicineIdAndVersion(medicineId, version);
    // 调用下载服务
    iFileService.download(response, cfgfileVersion.getPath(), cfgfileVersion.getFileName());
  }

  /**
   * 原始记录文件下载
   *
   * @param request 请求
   * @param response 回应
   * @param medicineId 药品ID
   * @param batchNo 批次号
   * @throws Exception 文件异常
   */
  @ApiOperation(value = "下载原始记录文件接口", notes = "下载原始记录文件")
  @RequestMapping(value = "/record", method = RequestMethod.GET)
  public void record(
      HttpServletRequest request,
      HttpServletResponse response,
      @RequestParam Long medicineId,
      @RequestParam Long batchNo)
      throws Exception {
    if (medicineId == null || batchNo == null) {
      LOGGER.info("药品ID或批次号为空！");
      throw new Exception("药品ID或批次号为空！");
    }
    String fileName = medicineId + Constant.STR_SPLIT + batchNo + Constant.FILE_EX_HTML;
    // 调用下载服务
    iFileService.download(response, FileConstant.PATH_RECORD, fileName);
  }

  /**
   * 下载报告记录文件
   *
   * @param request 请求
   * @param response 回应
   * @param medicineId 药品ID
   * @param batchNo 批次号
   * @throws Exception 文件异常
   */
  @ApiOperation(value = "下载报告记录文件接口", notes = "下载报告记录文件")
  @RequestMapping(value = "/report", method = RequestMethod.GET)
  public void report(
      HttpServletRequest request,
      HttpServletResponse response,
      @RequestParam Long medicineId,
      @RequestParam Long batchNo)
      throws Exception {
    if (medicineId == null || batchNo == null) {
      LOGGER.info("药品ID或批次号为空！");
      throw new Exception("药品ID或批次号为空！");
    }
    String fileName = medicineId + Constant.STR_SPLIT + batchNo + Constant.FILE_EX_HTML;
    // 调用下载服务
    iFileService.download(response, FileConstant.PATH_REPORT, fileName);
  }

  /**
   * 下载图像文件
   *
   * @param request 请求
   * @param response 回应
   * @param url 图像地址
   * @throws Exception 文件异常
   */
  @ApiOperation(value = "下载图像文件接口", notes = "下载图像文件")
  @RequestMapping(value = "/img", method = RequestMethod.GET)
  public void img(
      HttpServletRequest request, HttpServletResponse response, @RequestParam String url)
      throws Exception {
    if (url == null || url.isEmpty()) {
      LOGGER.info("图片地址为空！");
      throw new Exception("图片地址为空，下载失败！");
    }
    File imgFile = new File(url.trim());
    String fileName = imgFile.getName();
    String filePath = imgFile.getParent();
    // 调用下载服务
    iFileService.download(response, FileConstant.PATH_IMG + filePath, fileName);
  }
  /**
   * 下载取证图像
   *
   * @param request 请求
   * @param response 回应
   * @param url 取证地址
   * @throws Exception 文件异常
   */
  @ApiOperation(value = "下载取证图像接口", notes = "下载取证图像")
  @RequestMapping(value = "/proof", method = RequestMethod.GET)
  public void proof(
      HttpServletRequest request, HttpServletResponse response, @RequestParam String url)
      throws Exception {
    if (url == null || url.isEmpty()) {
      LOGGER.info("取证地址为空！");
      throw new Exception("取证地址为空，下载失败！");
    }
    File imgFile = new File(url.trim());
    String fileName = imgFile.getName();
    String filePath = imgFile.getParent();
    // 调用下载服务
    iFileService.download(response, FileConstant.PATH_PROOF + filePath, fileName);
  }
  /**
   * 下载文件
   *
   * @param request 请求
   * @param response 回应
   * @param url 文件地址
   * @throws Exception 文件异常
   */
  @ApiOperation(value = "下载文件接口", notes = "下载文件")
  @RequestMapping(value = "file", method = RequestMethod.GET)
  public void file(
      HttpServletRequest request, HttpServletResponse response, @RequestParam String url)
      throws Exception {
    if (url == null || url.isEmpty()) {
      LOGGER.info("文件地址为空！");
      throw new Exception("文件地址为空，下载失败！");
    }
    File imgFile = new File(url.trim());
    String fileName = imgFile.getName();
    String filePath = imgFile.getParent();
    // 调用下载服务
    iFileService.download(response, filePath, fileName);
  }

  /**
   * @param medicineId 药品id
   * @param version 药品版本号
   * @return 文件信息
   * @throws Exception 是否成功获取文件
   */
  private CfgfileVersion getCfgfileVersionByMedicineIdAndVersion(Long medicineId, String version)
      throws Exception {
    // 获取下载路径
    CfgfileVersion cfgfileVersion =
        iFileService.getCfgfileVersionByMedicineIdAndVersion(medicineId, version);
    if (cfgfileVersion == null
        || cfgfileVersion.getPath() == null
        || cfgfileVersion.getFileName() == null) {
      LOGGER.info("当前文件不存在！");
      throw new Exception("当前文件不存在！");
    }
    return cfgfileVersion;
  }
}
