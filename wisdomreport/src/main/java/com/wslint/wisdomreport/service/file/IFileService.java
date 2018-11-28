package com.wslint.wisdomreport.service.file;

import com.wslint.wisdomreport.domain.dto.file.CfgfileVersion;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.wslint.wisdomreport.domain.dto.file.FileBatch;
import org.springframework.web.multipart.MultipartFile;

/**
 * 下载服务
 *
 * @author yuxr
 * @since 2018/9/18 13:59
 */
public interface IFileService {

  /**
   * 根据药品ID获取对应配置数据
   *
   * @param medicineId 药品id
   * @return 配置数据
   */
  CfgfileVersion getCfgfileVersionByMedicineId(Long medicineId);

  /**
   * 根据药品ID和版本号获取对应下载路径和文件名
   *
   * @param medicineId 药品id
   * @param version 版本号
   * @return 配置数据
   */
  CfgfileVersion getCfgfileVersionByMedicineIdAndVersion(Long medicineId, String version);

  /**
   * 提供文件名和文件路径下载文件
   *
   * @param response 请求回应
   * @param fileName 文件名
   * @param filePath 文件路径-相对根目录
   */
  void download(HttpServletResponse response, String filePath, String fileName);

  /**
   * 文件上传
   *
   * @param filePath 存储路径
   * @param fileName 存储名称
   * @param uploadFile 上传文件
   */
  String upload(String filePath, String fileName, MultipartFile uploadFile);

  /**
   * 文件批量上传
   * @param fileBatchList 上传文件对象
   */
  void  uploadBatch(List<FileBatch> fileBatchList);

  /**
   * 根据路径获取所有图片的url
   *
   * @param medicineId 药品id
   * @param batchNo 批次号
   * @param firstClassId 大类id
   * @param secondClassId 小类id
   * @return url集合
   */
  List<String> getImgUrls(Long medicineId, Long batchNo, Long firstClassId, Long secondClassId);

  /**
   * 根据路径参数获取所有取证图片的url
   *
   * @param medicineId 药品id
   * @param batchNo 批次号
   * @param firstClassId 大类id
   * @param secondClassId 小类id
   * @return url集合
   */
  List<String> getProofUrls(Long medicineId, Long batchNo, Long firstClassId, Long secondClassId);
}
