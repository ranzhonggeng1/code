package com.wslint.wisdomreport.service.config.document;

import com.wslint.wisdomreport.domain.dto.xml.word.VersionDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 药品word服务
 *
 * @Author: rzg
 * @Date: 2018/11/22 12:02
 */
public interface IWordService {

  /**
   * 新增药品word版本文件

   *
   * @param description 描述信息
   * @param medicineId 药品ID
   * @param idMax word文档中空格最大id
   * @param uploadFile 药品word文件
   * @return 新增版本信息
   */
  Map<String, Object> setMedicineWordVersion(Long idMax, String description, Long medicineId,
      MultipartFile uploadFile);

  /**
   * 设置当前word版本文件至生效区
   *
   * @param description 描述信息
   * @param medicineId 药品ID
   * @param idMax word文档中空格最大id
   * @param uploadFile 药品word文件
   */
  Map<String, Object> setMedicineWordToOperation(Long idMax, String description, Long medicineId,
      MultipartFile uploadFile);
}
