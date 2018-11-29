package com.wslint.wisdomreport.service.config.document.impl;

import com.wslint.wisdomreport.constant.Constant;
import com.wslint.wisdomreport.constant.XMLConstant;
import com.wslint.wisdomreport.domain.dto.xml.word.ConfigDTO;
import com.wslint.wisdomreport.domain.dto.xml.word.IdDescriptionDTO;
import com.wslint.wisdomreport.domain.dto.xml.word.VersionDTO;
import com.wslint.wisdomreport.service.config.document.IWordService;
import com.wslint.wisdomreport.service.file.IFileService;
import com.wslint.wisdomreport.utils.FileUtils;
import com.wslint.wisdomreport.utils.ReturnUtils;
import com.wslint.wisdomreport.xmldao.IWordDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/** 药品word接口实现 @Author: rzg @Date: 2018/11/22 12:02 */
@Service
public class WordServiceImpl implements IWordService {

  @Autowired private IWordDao iWordDao;
  @Autowired private IFileService iFileService;

  private static final Logger logger = LoggerFactory.getLogger(WordServiceImpl.class);

  /**
   * 新增药品word版本文件至工作区
   *
   * @param description 药品word模板修改原因
   * @param medicineId 药品ID
   * @param uploadFile 药品word文件
   * @return 处理结果
   */
  @Override
  public Map<String, Object> setMedicineWordVersion(
      Long idMax, String description, Long medicineId, MultipartFile uploadFile) {
    String medicineWordVersionPath = FileUtils.getMedicineWordVersionXmlPath(medicineId);
    logger.info("药品word版本管理文件存储位置：------" + medicineWordVersionPath);
    // 先生成版本管理文件，再上传文件
    // 检查药品word的version.xml是否存在
    if (!FileUtils.createPath(medicineWordVersionPath)) {
      // 不存在，首次创建
      if (!addMedicineWordVersionXml(idMax, medicineId, description)) {
        return ReturnUtils.failureMap("药品模板管理文件创建失败！");
      }
      logger.info("-----创建药品版本管理文件成功-----");
    } else {
      // 版本管理文件已存在
      if (!updateMedicineWordVersionXml(idMax, medicineId, description)) {
        return ReturnUtils.successMap("药品word版本文件更新失败");
      }
    }

    VersionDTO versionDTO = iWordDao.getWordVersionInfoById(medicineId);
    ConfigDTO configDTO = versionDTO.getConfig();
    String uploadPath =
        FileUtils.getMedicineWordVersionPath(medicineId) + configDTO.getVersionMax();
    logger.info("药品word历史版本存储位置：------" + uploadPath);
    // 生成
    String fileName = medicineId + XMLConstant.WORD_SUFFIX;
    String path = iFileService.upload(uploadPath, fileName, uploadFile);
    logger.info("工作区path---------" + path);
    if (!path.isEmpty()) {
      return ReturnUtils.successMap("药品word上传工作区成功");
    }
    return ReturnUtils.failureMap("药品word上传工作区失败！");
  }

  /**
   * 推送药品当前word版本至生效区，先保存到历史版本再推送
   *
   * @param medicineId 药品ID
   * @param description 版本id
   * @param uploadFile 药品word文件
   * @return 处理结果
   */
  @Override
  public Map<String, Object> setMedicineWordToOperation(
      Long idMax, String description, Long medicineId, MultipartFile uploadFile) {
    // 先保存历史版本
    setMedicineWordVersion(idMax, description, medicineId, uploadFile);
    String medicineWordOperationPath = FileUtils.getMedicineWordOperationPath(medicineId);

    // 获取药品历史版本存储位置
    VersionDTO versionDTO = iWordDao.getWordVersionInfoById(medicineId);
    ConfigDTO configDTO = versionDTO.getConfig();
    String medicineVersionPath =
        FileUtils.getMedicineWordVersionPath(medicineId) + configDTO.getVersionMax();
    iFileService.copyFolder(medicineVersionPath, medicineWordOperationPath);
    return ReturnUtils.successMap("药品word上传生效区成功！");
  }

  /**
   * 新增药品版本文件
   *
   * @param idMax id最大值
   * @param medicineId 药品id
   * @param description 描述信息
   * @return boolean 创建成功
   */
  private boolean addMedicineWordVersionXml(Long idMax, Long medicineId, String description) {
    VersionDTO versionDTO = new VersionDTO();
    ConfigDTO configDTO = new ConfigDTO();
    IdDescriptionDTO idDescriptionDTO = new IdDescriptionDTO();

    configDTO.setOperation(Constant.FILE_WORD_VERSION_FIRST_ID);
    configDTO.setVersionMax(Constant.FILE_WORD_VERSION_FIRST_ID);
    configDTO.setIdMax(idMax);
    // 必须输入修改原因
    idDescriptionDTO.setId(Constant.FILE_WORD_VERSION_FIRST_ID);
    idDescriptionDTO.setDescription(description);

    List<IdDescriptionDTO> idDescriptionDTOArrayList = new ArrayList<>();
    idDescriptionDTOArrayList.add(idDescriptionDTO);

    versionDTO.setIdDescriptionDTOS(idDescriptionDTOArrayList);
    versionDTO.setConfig(configDTO);

    // 创建xml
    return iWordDao.setWordVersionInfoById(medicineId, versionDTO);
  }

  /** 上传之后，更新version.xml */
  private boolean updateMedicineWordVersionXml(Long idMax, Long medicineId, String description) {
    VersionDTO versionDTO = iWordDao.getWordVersionInfoById(medicineId);
    ConfigDTO configDTO = versionDTO.getConfig();

    // 设置版本号，叠加
    String maxVersion = configDTO.getVersionMax();
    int versionId = Integer.parseInt(maxVersion);
    String currentVersion = String.valueOf(++versionId);

    // 保存原来的id list，并且新加一条数据
    List<IdDescriptionDTO> idDescriptionDTOList = versionDTO.getIdDescriptionDTOS();
    IdDescriptionDTO idDescriptionDTO = new IdDescriptionDTO();
    idDescriptionDTO.setId(currentVersion);
    idDescriptionDTO.setDescription(description);
    idDescriptionDTOList.add(idDescriptionDTO);

    ConfigDTO newConfigDTO = new ConfigDTO();
    // 生效区的永远是最新版本，也就是最大版本
    newConfigDTO.setOperation(currentVersion);
    newConfigDTO.setIdMax(idMax);
    newConfigDTO.setVersionMax(currentVersion);

    versionDTO.setConfig(newConfigDTO);
    versionDTO.setIdDescriptionDTOS(idDescriptionDTOList);

    return iWordDao.setWordVersionInfoById(medicineId, versionDTO);
  }
}
