package com.wslint.wisdomreport.controller.file;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.wslint.wisdomreport.adpter.IRCGServerAdpater;
import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.constant.FileConstant;
import com.wslint.wisdomreport.constant.UrlConstant;
import com.wslint.wisdomreport.domain.dto.img.ImgInfo;
import com.wslint.wisdomreport.domain.dto.img.ProofInfo;
import com.wslint.wisdomreport.service.config.system.ISysConfigService;
import com.wslint.wisdomreport.service.file.IFileService;
import com.wslint.wisdomreport.utils.CommonUtils;
import com.wslint.wisdomreport.utils.FileUtils;
import com.wslint.wisdomreport.utils.ReturnUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传接口
 *
 * @author yuxr
 * @since 2018/9/18 13:56
 */
@Api(tags = "4 文件接口", description = "提供文件管理的相关接口")
@RestController
@RequestMapping(value = "/file/upload")
public class UploadController {

  @Autowired private IFileService iFileService;
  @Autowired private ISysConfigService iSysConfigService;
  @Autowired private IRCGServerAdpater ircgServerAdpater;

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

  /**
   * 记录文件上传
   *
   * @param uploadFile 文件
   * @return 处理结果
   */
  @ApiOperation(value = "原始记录文件上传接口", notes = "原始记录文件上传")
  @RequestMapping(value = "/record", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> record(@RequestParam("file") MultipartFile uploadFile) {
    if (uploadFile == null || uploadFile.isEmpty()) {
      return ReturnUtils.failureMap("空文件，上传失败!");
    }
    // 获取文件名
    String fileName = uploadFile.getOriginalFilename();
    LOGGER.info("上传的文件名为:{}", fileName);
    // 文件上传根路径
    String path = iFileService.upload(FileConstant.PATH_RECORD, fileName, uploadFile);
    if (!path.isEmpty()) {
      return ReturnUtils.successMap("上传成功!");
    }
    return ReturnUtils.failureMap("上传失败!");
  }

  /**
   * 报告文件上传
   *
   * @param uploadFile 文件
   * @return 处理结果
   */
  @ApiOperation(value = "报告记录文件上传接口", notes = "报告记录文件上传")
  @RequestMapping(value = "/report", method = RequestMethod.POST)
  public Map<String, Object> report(@RequestParam("file") MultipartFile uploadFile) {
    if (uploadFile == null || uploadFile.isEmpty()) {
      return ReturnUtils.failureMap("空文件，上传失败!");
    }
    // 获取文件名
    String fileName = uploadFile.getOriginalFilename();
    LOGGER.info("上传的文件名为:{}", fileName);
    // 文件上传根路径
    String path = iFileService.upload(FileConstant.PATH_REPORT, fileName, uploadFile);
    if (!path.isEmpty()) {
      return ReturnUtils.successMap("上传成功!");
    }
    return ReturnUtils.failureMap("上传失败!");
  }

  /**
   * 图片文件上传
   *
   * @param uploadFile 文件
   * @return 处理结果
   */
  @ApiOperation(value = "图像记录文件上传接口", notes = "图像记录文件上传")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "info",
        value = "图片存储信息",
        required = true,
        defaultValue =
            "{\"medicine\":1,\"batchNo\":20181010,\"firstClass\":3,\"secondClass\":12,\"deviceCode\":\"32321312\",\"deviceType\":1,\"substance\":\"1\",\"operatingPersonnel\":\"2312\",\"fileName\":\"123.pdf\"}"),
  })
  @RequestMapping(value = "/img", method = RequestMethod.POST)
  public Map<String, Object> img(
      @RequestParam("img") MultipartFile uploadFile, @RequestParam("info") String info) {
    if (uploadFile == null || uploadFile.isEmpty()) {
      return ReturnUtils.failureMap("空文件，上传失败!");
    }
    ImgInfo imgInfo = JSON.parseObject(info, ImgInfo.class);
    if (imgInfo == null
        || imgInfo.getBatchNo() == null
        || imgInfo.getDeviceCode() == null
        || imgInfo.getDeviceType() == null
        || imgInfo.getFileName() == null
        || imgInfo.getFirstClass() == null
        || imgInfo.getMedicine() == null
        || imgInfo.getSecondClass() == null
        || imgInfo.getSubstance() == null) {
      return ReturnUtils.failureMap("图像信息缺失，上传失败!");
    }
    // 获取文件名
    String fileName = uploadFile.getOriginalFilename();
    LOGGER.info("上传的文件名为:{}", fileName);
    String filePath =
        FileUtils.getBusinessPath(
            imgInfo.getMedicine(),
            imgInfo.getBatchNo(),
            imgInfo.getFirstClass(),
            imgInfo.getSecondClass());
    // 文件上传根路径
    LOGGER.debug("filePath: {} fileName: {} ", FileConstant.PATH_IMG + filePath, fileName);
    String path = iFileService.upload(FileConstant.PATH_IMG + filePath, fileName, uploadFile);
    LOGGER.debug("path: {} ", path);
    if (!path.isEmpty()) {
      Map<String, Object> result = ircgServerAdpater.recognitionImage(info, path);
      LOGGER.debug("img result: ", new Gson().toJson(result));
      result.put(Constant.STR_RETURN_IMG_PATH, filePath + result.get(Constant.STR_RETURN_IMG_PATH));
      result.put(Constant.STR_FILE_NAME, filePath + result.get(Constant.STR_FILE_NAME));
      LOGGER.debug("img result: ", new Gson().toJson(result));
      return ReturnUtils.successMap(result, "上传成功!");
    }
    return ReturnUtils.failureMap("上传失败!");
  }
  /**
   * 取证图片上传
   *
   * @param uploadFile 文件
   * @return 处理结果
   */
  @ApiOperation(value = "取证图片上传接口", notes = "取证图片上传")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "info",
        value = "图片存储信息",
        required = true,
        defaultValue = "{\"medicine\":1,\"batchNo\":20181010,\"firstClass\":3,\"secondClass\":12}"),
  })
  @ResponseBody
  @RequestMapping(value = "/proof", method = RequestMethod.POST)
  public Map<String, Object> proof(
      @RequestParam("proof") MultipartFile uploadFile, @RequestParam("info") String info) {
    if (uploadFile == null || uploadFile.isEmpty() || uploadFile.getOriginalFilename() == null) {
      return ReturnUtils.failureMap("空文件，上传失败!");
    }
    ProofInfo proofInfo = JSON.parseObject(info, ProofInfo.class);
    if (proofInfo == null
        || proofInfo.getMedicine() == null
        || proofInfo.getBatchNo() == null
        || proofInfo.getFirstClass() == null
        || proofInfo.getSecondClass() == null) {
      return ReturnUtils.failureMap("图像信息缺失，上传失败!");
    }
    // 获取文件名
    String oldName = uploadFile.getOriginalFilename();
    String prefix = oldName.substring(oldName.lastIndexOf("."));
    String fileName = CommonUtils.getNextId() + prefix;
    LOGGER.info("上传的文件名为:{}", fileName);
    String filePath =
        FileUtils.getProofPath(
            proofInfo.getMedicine(),
            proofInfo.getBatchNo(),
            proofInfo.getFirstClass(),
            proofInfo.getSecondClass());
    // 文件上传根路径
    LOGGER.debug("filePath: {} fileName: {} ", filePath, fileName);
    String path = iFileService.upload(filePath, fileName, uploadFile);
    LOGGER.debug("path: {} ", path);
    Map<String, Object> rtnMap = new HashMap<>();
    String url = UrlConstant.FILE_DOWNLOAD_FILE + filePath + fileName;
    LOGGER.debug("url: {} ", url);
    rtnMap.put(Constant.STR_URL, url);
    return ReturnUtils.successMap(rtnMap, "上传成功!");
  }
}
