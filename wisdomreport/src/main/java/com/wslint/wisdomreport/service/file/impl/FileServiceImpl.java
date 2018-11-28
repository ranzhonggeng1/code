package com.wslint.wisdomreport.service.file.impl;

import com.wslint.wisdomreport.adpter.IRCGServerAdpater;
import com.wslint.wisdomreport.constant.UrlConstant;
import com.wslint.wisdomreport.dao.file.FileDao;
import com.wslint.wisdomreport.domain.dto.file.CfgfileVersion;
import com.wslint.wisdomreport.domain.dto.file.FileBatch;
import com.wslint.wisdomreport.service.file.IFileService;
import com.wslint.wisdomreport.utils.FileUtils;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件管理服务
 *
 * @author yuxr
 * @since 2018/9/18 14:37
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FileServiceImpl implements IFileService {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

  @Autowired private FileDao fileDao;
  @Autowired private IRCGServerAdpater ircgServerAdpater;

  /**
   * 根据药品ID获取对应配置数据
   *
   * @param medicineId 药品id
   * @return 配置数据
   */
  @Override
  public CfgfileVersion getCfgfileVersionByMedicineId(Long medicineId) {
    return fileDao.getCfgfileVersionByMedicineId(medicineId);
  }

  /**
   * 根据药品ID和版本号获取对应下载路径和文件名
   *
   * @param medicineId 药品id
   * @param version 版本号
   * @return 配置数据
   */
  @Override
  public CfgfileVersion getCfgfileVersionByMedicineIdAndVersion(Long medicineId, String version) {
    CfgfileVersion cfgfileVersion = new CfgfileVersion();
    cfgfileVersion.setMedicineId(medicineId);
    cfgfileVersion.setVersion(version);
    return fileDao.getCfgfileVersionByMedicineIdAndVersion(cfgfileVersion);
  }

  /**
   * 提供文件名和文件路径下载文件
   *
   * @param response 请求回应
   * @param fileName 文件名
   * @param filePath 文件路径-相对根目录
   */
  @Override
  public void download(HttpServletResponse response, String filePath, String fileName) {
    if (fileName != null) {
      String realPath = FileUtils.getFileRootPath() + filePath;
      // 设置文件路径
      File file = new File(realPath, fileName);

      if (file.exists()) {
        // 设置强制下载不打开
        response.setContentType("application/force-download");
        // 设置文件名
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        response.setContentLengthLong(file.length());
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        int length;
        try {
          fis = new FileInputStream(file);
          bis = new BufferedInputStream(fis);
          OutputStream os = response.getOutputStream();
          while ((length = bis.read(buffer)) != -1) {
            os.write(buffer, 0, length);
          }
          LOGGER.info("success download file {}!", file.getPath());
        } catch (Exception e) {
          LOGGER.error(e.getMessage());
        } finally {
          if (bis != null) {
            try {
              bis.close();
            } catch (IOException e) {
              LOGGER.error(e.getMessage());
            }
          }
          if (fis != null) {
            try {
              fis.close();
            } catch (IOException e) {
              LOGGER.error(e.getMessage());
            }
          }
        }
      } else {
        LOGGER.error("file {} not exist !", file.getPath());
      }
    }
  }

  /**
   * 文件上传
   *
   * @param filePath 存储路径
   * @param fileName 存储名称
   * @param uploadFile 上传文件
   */
  @Override
  public String upload(String filePath, String fileName, MultipartFile uploadFile) {
    String realPath = FileUtils.getFileRootPath() + filePath;
    FileUtils.createPath(realPath);
    File file = new File(realPath, fileName);
    try {
      uploadFile.transferTo(file);
      LOGGER.info("上传文件 {} 成功！", fileName);
      return realPath + fileName;
    } catch (IllegalStateException | IOException e) {
      LOGGER.error(e.getMessage());
      return "";
    }
  }

  /**
   * 文件批量上传
   * @param fileBatchList 上传文件对象
   */
  @Override
  public void uploadBatch(List<FileBatch> fileBatchList) {
    for(FileBatch fileBatch : fileBatchList) {
      upload(fileBatch.getFilePath(), fileBatch.getFileName(), fileBatch.getUploadFile());
    }
  }

  /**
   * 根据路径获取所有图片的url
   *
   * @param medicineId 药品id
   * @param batchNo 批次号
   * @param firstClassId 大类id
   * @param secondClassId 小类id
   * @return url集合
   */
  @Override
  public List<String> getImgUrls(
      Long medicineId, Long batchNo, Long firstClassId, Long secondClassId) {
    String path = FileUtils.getImgPath(medicineId, batchNo, firstClassId, secondClassId);
    String contain = ircgServerAdpater.getImageFilterKey();
    LOGGER.info("img-urls-condition : {} ", contain);
    return getFileUrlsByPath(path, contain);
  }

  /**
   * 根据路径参数获取所有取证图片的url
   *
   * @param medicineId 药品id
   * @param batchNo 批次号
   * @param firstClassId 大类id
   * @param secondClassId 小类id
   * @return url集合
   */
  @Override
  public List<String> getProofUrls(
      Long medicineId, Long batchNo, Long firstClassId, Long secondClassId) {
    String path = FileUtils.getProofPath(medicineId, batchNo, firstClassId, secondClassId);
    return getFileUrlsByPath(path, "");
  }

  /**
   * 根据路径获取路径下所有请求路由
   *
   * @param path 路径
   * @return 所有文件名
   */
  private List<String> getFileUrlsByPath(String path, String condition) {
    List<String> fileNames = getFileNamesByPath(path);
    List<String> fileUrls = new ArrayList<>();
    for (String fileName : fileNames) {
      if (condition.isEmpty() || fileName.contains(condition)) {
        String fileUrl = UrlConstant.FILE_DOWNLOAD_FILE + path + fileName;
        fileUrls.add(fileUrl);
      }
    }
    return fileUrls;
  }

  /**
   * 根据路径获取路径下所有文件名
   *
   * @param path 路径
   * @return 所有文件名
   */
  private List<String> getFileNamesByPath(String path) {
    String filePath = FileUtils.getFileRootPath() + path;
    File file = new File(filePath);
    String[] fileNames = file.list();
    List<String> fileNameList = new ArrayList<>();
    if (fileNames != null) {
      fileNameList = Arrays.asList(fileNames);
    }
    return fileNameList;
  }
}
