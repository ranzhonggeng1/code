package com.wslint.wisdomreport.controller.config.document;

import com.wslint.wisdomreport.constant.XMLConstant;
import com.wslint.wisdomreport.domain.dto.xml.method.MethodDTO;
import com.wslint.wisdomreport.domain.dto.xml.secondclass.SecondClassDTO;
import com.wslint.wisdomreport.domain.dto.xml.secondclass.SpaceDTO;
import com.wslint.wisdomreport.service.config.document.IMethodService;
import com.wslint.wisdomreport.service.config.document.ISecondClassService;
import com.wslint.wisdomreport.service.file.IFileService;
import com.wslint.wisdomreport.utils.FileUtils;
import com.wslint.wisdomreport.utils.ReturnUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.multi.MultiInternalFrameUI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 模板接口
 *
 * @author yuxr
 * @since 2018/10/31 10:41
 */
@Api(tags = "5 文档配置接口", description = "提供文档配置的相关接口")
@RestController
@RequestMapping(value = "/config/document/template")
public class TemplateController {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(TemplateController.class);

  @Autowired private IFileService iFileService;
  @Autowired private ISecondClassService iSecondClassService;
  @Autowired private IMethodService iMethodService;

  /**
   * 根据药品id、大类id和小类id获取模板信息
   *
   * @return 小类信息
   */
  @ApiOperation(value = "获取模板信息", notes = "获取模板信息")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "medicineId", value = "药品id", required = true, defaultValue = "1"),
    @ApiImplicitParam(name = "firstClassId", value = "大类id", required = true, defaultValue = "3"),
    @ApiImplicitParam(name = "secondClassId", value = "小类id", required = true, defaultValue = "12")
  })
  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public Map<String, Object> get(
      HttpServletResponse response,
      @RequestParam Long medicineId,
      @RequestParam Long firstClassId,
      @RequestParam Long secondClassId) {
    // 小类信息
    SecondClassDTO secondClassDTO =
        iSecondClassService.getSecondClassByMedicineSecondClassId(medicineId, secondClassId);
    List<Long> ids = new ArrayList<>();
    List<SpaceDTO> spaceDTOList = secondClassDTO.getSpaces();
    if (spaceDTOList == null) {
      return ReturnUtils.successMap("模板信息不存在！");
    }
    for (SpaceDTO spaceDTO : spaceDTOList) {
      if (spaceDTO.getPosition() != null) {
        ids.add(spaceDTO.getId());
      }
    }
    // 方法信息
    MethodDTO methodDTO = iMethodService.getMethodByMedicineId();

    Map<String, Object> rtnMap = new HashMap<>();
    rtnMap.put(XMLConstant.STR_HTML_FILEPATH, FileUtils.getHtmlPath(medicineId, firstClassId));
    rtnMap.put(XMLConstant.STR_HTML_FILENAME, secondClassId + XMLConstant.HTML_SUFFIX);
    rtnMap.put(XMLConstant.STR_IDS, ids);
    rtnMap.put(XMLConstant.STR_METHOD, methodDTO);
    rtnMap.put(XMLConstant.STR_PARAM, secondClassDTO.getParamValues());

    return ReturnUtils.successMap(rtnMap, "获取模板信息成功！");
  }

  /**
   * 模板下载
   *
   * @param response 回应
   * @param filePath 文件路径
   * @param fileName 文件名
   */
  @ApiOperation(value = "模板下载接口", notes = "模板下载")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "filePath",
        value = "文件路径",
        required = true,
        defaultValue = "product/1/document/html/3/"),
    @ApiImplicitParam(name = "fileName", value = "文件名", required = true, defaultValue = "12.html"),
  })
  @RequestMapping(value = "/download", method = RequestMethod.GET)
  public void download(
      HttpServletResponse response, @RequestParam String filePath, @RequestParam String fileName) {
    // 调用下载服务
    iFileService.download(response, filePath, fileName);
  }

  /**
   * 模板上传
   *
   * @param uploadFile 文件
   * @return 处理结果
   */
  @ApiOperation(value = "模板上传接口", notes = "模板上传")
  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> upload(
      @RequestParam("file") MultipartFile uploadFile,
      @RequestParam Long medicineId,
      @RequestParam Long firstClassId,
      @RequestParam Long secondClassId)
      throws Exception {
    if (uploadFile == null || uploadFile.isEmpty()) {
      return ReturnUtils.failureMap("空文件，上传失败!");
    }
    List<SpaceDTO> spaceDTOList = getHtmlIds(uploadFile);
    String htmlPath = FileUtils.getHtmlPath(medicineId, firstClassId);
    String fileName = secondClassId + XMLConstant.HTML_SUFFIX;
    String path = iFileService.upload(htmlPath, fileName, uploadFile);
    if (!path.isEmpty()) {
      String htmlUrl = FileUtils.getHtmlUrl(firstClassId, secondClassId);
      return iSecondClassService.uploadHtml(medicineId, secondClassId, htmlUrl, spaceDTOList);
    }
    return ReturnUtils.failureMap("上传失败!");
  }

  @ApiOperation(value = "模板页眉上传接口", notes = "header上传")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "medicineId", value = "药品id", required = true, defaultValue = "1023")
  })
  @RequestMapping(value = "/header/upload", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> uploadHeader(
      @RequestParam("file") MultipartFile uploadFile, @RequestParam Long medicineId) {
    if (uploadFile == null || uploadFile.isEmpty()) {
      return ReturnUtils.failureMap("空文件，上传失败!");
    }
    String htmlPath = FileUtils.getMedicineHeaderPath(medicineId);
    // 生成的模板html模板头文件名样例：1023_header.html
    String fileName = medicineId + XMLConstant.HTML_HEADER_SUFFIX;
    String path = iFileService.upload(htmlPath, fileName, uploadFile);
    if (!path.isEmpty()) {
      return ReturnUtils.successMap(path, "上传成功!");
    }
    return ReturnUtils.failureMap("上传失败!");
  }

  @ApiOperation(value = "模板页眉下载接口", notes = "header下载")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "medicineId", value = "药品id", required = true, defaultValue = "1023")
  })
  @RequestMapping(value = "/header/download", method = RequestMethod.GET)
  @ResponseBody
  public void downloadHeader(HttpServletResponse response, @RequestParam Long medicineId) {
    String filePath = FileUtils.getMedicineHeaderPath(medicineId);
    // 生成的模板html模板头文件名样例：1023_header.html
    String fileName = medicineId + XMLConstant.HTML_HEADER_SUFFIX;
    // 调用下载服务
    iFileService.download(response, filePath, fileName);
  }

  /**
   * 提取html中的id信息
   *
   * @param uploadFile 上传文件
   * @return 封装好id信息的list
   * @throws Exception 异常信息
   */
  private List<SpaceDTO> getHtmlIds(MultipartFile uploadFile) throws Exception {
    List<SpaceDTO> spaceDTOList = new ArrayList<>();
    try {
      Document document = Jsoup.parse(uploadFile.getInputStream(), "utf-8", "");
      Elements input = document.select("input");
      for (Element element : input) {
        SpaceDTO spaceDTO = new SpaceDTO();
        spaceDTO.setId(Long.valueOf(element.attr("id")));
        spaceDTOList.add(spaceDTO);
      }
    } catch (IOException e) {
      LOGGER.error("模板id转换失败！");
      e.printStackTrace();
      throw new Exception("模板id转换失败");
    }
    return spaceDTOList;
  }
}
