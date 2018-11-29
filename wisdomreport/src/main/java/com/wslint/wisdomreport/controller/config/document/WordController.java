package com.wslint.wisdomreport.controller.config.document;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.constant.XMLConstant;
import com.wslint.wisdomreport.domain.dto.xml.word.ConfigDTO;
import com.wslint.wisdomreport.domain.dto.xml.word.IdDescriptionDTO;
import com.wslint.wisdomreport.domain.dto.xml.word.VersionDTO;
import com.wslint.wisdomreport.service.config.document.ISecondClassService;
import com.wslint.wisdomreport.service.config.document.IWordService;
import com.wslint.wisdomreport.service.file.IFileService;
import com.wslint.wisdomreport.utils.FileUtils;
import com.wslint.wisdomreport.utils.ReturnUtils;
import com.wslint.wisdomreport.xmldao.IWordDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/** 药品word管理接口类 @Author: rzg @Date: 2018/11/22 11:19 */
@Api(tags = "5 文档配置接口", description = "提供文档配置的相关接口")
@RestController
@RequestMapping(value = "/config/document/word")
public class WordController {

  /** 日志记录 */
  private static final Logger logger = LoggerFactory.getLogger(WordController.class);

  @Autowired private IFileService iFileService;
  @Autowired private ISecondClassService iSecondClassService;
  @Autowired private IWordDao iWordDao;
  @Autowired private IWordService iWordService;

  /**
   * 小类word下载 根据medicineId、firstClassId、secondClassId获取小类word路径及文件名称
   *
   * @param response 返回结果
   * @param medicineId 药品id
   * @param firstClassId 大类id
   * @param secondClassId 小类id
   */
  @ApiOperation(value = "小类word下载", notes = "小类word下载")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "medicineId", value = "药品ID", required = true),
    @ApiImplicitParam(name = "firstClassId", value = "大类ID", required = true),
    @ApiImplicitParam(name = "secondClassId", value = "小类ID", required = true)
  })
  @RequestMapping(value = "/download/secondclass", method = RequestMethod.GET)
  public void downloadWord(
      HttpServletResponse response,
      @RequestParam Long medicineId,
      @RequestParam Long firstClassId,
      @RequestParam Long secondClassId) {
    // word模板位置
    String filePath = FileUtils.getSecondClassWordPath(medicineId, firstClassId);
    // word模板名称
    String fileName = secondClassId + XMLConstant.WORD_SUFFIX;
    // 下载word
    iFileService.download(response, filePath, fileName);
  }

  /**
   * 小类word上传
   *
   * @param uploadFile 上传文件
   * @param medicineId 药品id
   * @param firstClassId 大类id
   * @param secondClassId 小类id
   * @return 处理结果
   */
  @ApiOperation(value = "小类word文档上传", notes = "小类word文档上传")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "medicineId", value = "药品ID", required = true),
    @ApiImplicitParam(name = "firstClassId", value = "大类ID", required = true),
    @ApiImplicitParam(name = "secondClassId", value = "小类ID", required = true)
  })
  @RequestMapping(value = "/upload/secondclass", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> uploadWord(
      @RequestParam("file") MultipartFile uploadFile,
      @RequestParam Long medicineId,
      @RequestParam Long firstClassId,
      @RequestParam Long secondClassId) {
    if (uploadFile == null || uploadFile.isEmpty()) {
      return ReturnUtils.failureMap("空文件，上传失败");
    }
    String secondClassWordPath = FileUtils.getSecondClassWordPath(medicineId, firstClassId);
    String fileName = secondClassId + XMLConstant.WORD_SUFFIX;
    String path = iFileService.upload(secondClassWordPath, fileName, uploadFile);
    if (!path.isEmpty()) {
      return ReturnUtils.successMap("上传成功");
    }
    return ReturnUtils.failureMap("上传失败");
  }

  /**
   * 药品word上传至工作区，生成历史版本
   *
   * @param uploadFile 上传文件
   * @param medicineId 药品ID
   * @param description 描述修改信息
   * @param idMax id最大值
   * @return 处理结果
   */
  @ApiOperation(value = "药品word文档上传工作区", notes = "药品文档上传")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "medicineId", value = "药品ID", required = true),
    @ApiImplicitParam(name = "description", value = "药品word修改原因", required = true),
    @ApiImplicitParam(name = "idMax", value = "ID最大值", required = true)
  })
  @RequestMapping(value = "/upload/medicine", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> uploadMedicineWord(
      @RequestParam("file") MultipartFile uploadFile,
      @RequestParam Long medicineId,
      @RequestParam String description,
      @RequestParam Long idMax) {
    if (uploadFile == null || uploadFile.isEmpty()) {
      return ReturnUtils.failureMap("药品word为空，上传失败");
    }
    return iWordService.setMedicineWordVersion(idMax, description, medicineId, uploadFile);
  }

  /**
   * 从工作区下载药品word模板
   *
   * @param response 返回结果
   * @param medicineId 药品id
   * @param versionId 版本号
   */
  @ApiOperation(value = "药品word模板下载", notes = "药品word模板下载")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "medicineId", value = "药品ID", required = true),
    @ApiImplicitParam(name = "versionId", value = "药品word模板版本号", required = true)
  })
  @RequestMapping(value = "/download/medicine", method = RequestMethod.GET)
  public void downloadMedicineWord(
      HttpServletResponse response, @RequestParam Long medicineId, @RequestParam String versionId) {
    // word模板位置-相对路径
    String medicineWordVersionPath = FileUtils.getMedicineWordVersionPath(medicineId) + versionId;
    logger.info("下载药品word模板位置----" + medicineWordVersionPath);
    // word模板名称
    String fileName = medicineId + XMLConstant.WORD_SUFFIX;
    // 下载word
    iFileService.download(response, medicineWordVersionPath, fileName);
  }

  /**
   * 查询药品模板版本号
   *
   * @param medicineId 药品id
   * @return 处理结果
   */
  @ApiOperation(value = "查询药品word版本号", notes = "查询药品word版本列表")
  @ApiImplicitParams({@ApiImplicitParam(name = "medicineId", value = "药品ID", required = true)})
  @RequestMapping(value = "/version", method = RequestMethod.GET)
  @ResponseBody
  public Map<String, Object> getMedicineVersion(@RequestParam Long medicineId) {
    VersionDTO versionDTO = iWordDao.getWordVersionInfoById(medicineId);
    ConfigDTO configDTO = versionDTO.getConfig();
    List<IdDescriptionDTO> idDescriptionDTOS = versionDTO.getIdDescriptionDTOS();
    return ReturnUtils.successMap(idDescriptionDTOS, "查询药品版本列表成功");
  }

  /**
   * 查询药品word最大id
   *
   * @param medicineId 药品ID
   * @return 处理结果
   */
  @ApiOperation(value = "查询药品word最大ID值", notes = "查询药品word最大ID值")
  @ApiImplicitParams({@ApiImplicitParam(name = "medicineId", value = "药品ID", required = true)})
  @RequestMapping(value = "/getId", method = RequestMethod.GET)
  @ResponseBody
  public Map<String, Object> getMedicineMaxId(@RequestParam Long medicineId) {
    VersionDTO versionDTO = iWordDao.getWordVersionInfoById(medicineId);
    ConfigDTO configDTO = versionDTO.getConfig();
    Map<String, Object> rtnMap = new HashMap<>();
    logger.info(configDTO.getIdMax().toString());
    rtnMap.put(Constant.STR_MEDICINE_WORD_ID_MAX, configDTO.getIdMax());
    return ReturnUtils.successMap(rtnMap, "查询药品word最大ID成功");
  }

  /**
   * 推送药品当前word版本至生效区，需要先生成历史版本，推送生效区
   *
   * @param uploadFile 上传文件
   * @param medicineId 药品id
   * @param description 描述修改信息
   * @param idMax 空格id最大值
   * @return 处理结果
   */
  @ApiOperation(value = "设置药品word模板至生效区", notes = "设置药品word模板生效")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "medicineId", value = "药品ID", required = true),
    @ApiImplicitParam(name = "description", value = "药品word修改原因", required = true),
    @ApiImplicitParam(name = "idMax", value = "ID最大值", required = true)
  })
  @RequestMapping(value = "/effect/medicine", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> effectMedicineWordToOperation(
      @RequestParam("file") MultipartFile uploadFile,
      @RequestParam Long medicineId,
      @RequestParam String description,
      @RequestParam Long idMax) {
    if (uploadFile == null || uploadFile.isEmpty()) {
      return ReturnUtils.failureMap("药品word为空，上传失败");
    }
    return iWordService.setMedicineWordToOperation(idMax, description, medicineId, uploadFile);
  }
}
