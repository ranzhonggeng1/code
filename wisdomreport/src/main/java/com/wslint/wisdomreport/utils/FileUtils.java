package com.wslint.wisdomreport.utils;

import com.wslint.wisdomreport.constant.FileConstant;
import com.wslint.wisdomreport.constant.XMLConstant;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件工具类
 *
 * @author yuxr
 * @since 2018/10/31 14:00
 */
public class FileUtils {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

  /** 文件根路径 */
  private static String FILE_ROOT_PATH;

  public static String getFileRootPath() {
    return FILE_ROOT_PATH;
  }

  public static void setFileRootPath(String fileRootPath) {
    FILE_ROOT_PATH = fileRootPath;
  }

  /**
   * 创建路径
   *
   * @param path 路径
   * @return 是否成功创建
   */
  public static boolean createPath(String path) {
    File dir = new File(path);
    if (dir.exists()) {
      return true;
    }
    LOGGER.info("create file : {}", path);
    return dir.mkdirs();
  }

  /**
   * 获取 product 目录
   *
   * @return xml配置文件根目录
   */
  public static String getProductPath() {
    return FILE_ROOT_PATH + XMLConstant.PATH_PRODUCT;
  }

  /**
   * 获取药品路径
   *
   * @param medicineId 药品id
   * @return 药品路径
   */
  public static String getMedicinePath(Long medicineId) {
    return getProductPath() + medicineId + XMLConstant.PATH_XML;
  }

  /**
   * 获取大类路径
   *
   * @param medicineId 药品id
   * @return 大类路径
   */
  public static String getFirstClassPath(Long medicineId) {
    return getMedicinePath(medicineId) + XMLConstant.PATH_FIRST_CLASS;
  }

  /**
   * 获取小类路径
   *
   * @param medicineId 药品id
   * @return 大类路径
   */
  public static String getSecondClassPath(Long medicineId) {
    return getMedicinePath(medicineId) + XMLConstant.PATH_SECOND_CLASS;
  }

  /**
   * 获取html文件存储路径
   *
   * @param medicineId 药品id
   * @param firstClassId 大类id
   * @return 存储路径
   */
  public static String getHtmlPath(Long medicineId, Long firstClassId) {
    return XMLConstant.PATH_PRODUCT
        + medicineId
        + XMLConstant.PATH_HTML
        + firstClassId
        + File.separator;
  }

  /**
   * 获取小类word文件存储路径
   */
  public static String getSecondClassWordPath(Long medicineId, Long firstClassId) {
    return XMLConstant.PATH_PRODUCT
            + medicineId
            + XMLConstant.PATH_WORD
            + firstClassId
            + File.separator;
  }

  /**
   * 获取html的相对url
   *
   * @param firstClassId 大类id
   * @param secondClassId 小类id
   * @return 相对url
   */
  public static String getHtmlUrl(Long firstClassId, Long secondClassId) {
    return XMLConstant.PATH_HTML
        + firstClassId
        + File.separator
        + secondClassId
        + XMLConstant.HTML_SUFFIX;
  }

  /**
   * 获取业务路径 ： 药品id/批次号/大类id/小类id
   *
   * @param medicine 药品id
   * @param batchNo 批次号
   * @param firstClass 大类
   * @param secondClass 小类
   * @return 业务路径
   */
  public static String getBusinessPath(
      Long medicine, Long batchNo, Long firstClass, Long secondClass) {
    return medicine
        + File.separator
        + batchNo
        + File.separator
        + firstClass
        + File.separator
        + secondClass
        + File.separator;
  }

  /**
   * 获取取证图片的存储目录
   *
   * @param medicine 药品id
   * @param batchNo 批次号
   * @param firstClass 大类id
   * @param secondClass 小类id
   * @return 路径
   */
  public static String getProofPath(
      Long medicine, Long batchNo, Long firstClass, Long secondClass) {
    return FileConstant.PATH_PROOF + getBusinessPath(medicine, batchNo, firstClass, secondClass);
  }
  /**
   * 获取图片文件的存储目录
   *
   * @param medicine 药品id
   * @param batchNo 批次号
   * @param firstClass 大类id
   * @param secondClass 小类id
   * @return 路径
   */
  public static String getImgPath(Long medicine, Long batchNo, Long firstClass, Long secondClass) {
    return FileConstant.PATH_IMG + getBusinessPath(medicine, batchNo, firstClass, secondClass);
  }

  /**
   * 获取药品word版本路径
   *
   * @param medicineId 药品id
   * @return 药品word版本路径
   */
  public static String getMedicineWordVersionXmlPath(Long medicineId) {
    return getProductPath() + medicineId + XMLConstant.PATH_WORD;
  }

  /**
   * 获取药品word版本历史模板保存的相对位置
   * @param medicineId 药品ID
   * @return 版本路径
   */
  public static String getMedicineWordVersionPath(Long medicineId) {
    return XMLConstant.FILE_PRODUCT
            + File.separator
            + medicineId
            + XMLConstant.PATH_WORD
            + XMLConstant.PATH_MEDICINE_WORD_VERSION;
  }

  /**
   * 获取药品word版本生效模板保存的相对位置
   * @param medicineId 药品ID
   * @return 返回生效区路径
   */
  public static String getMedicineWordOperationPath(Long medicineId) {
    return XMLConstant.FILE_PRODUCT
            + File.separator
            + medicineId
            + XMLConstant.PATH_WORD
            + XMLConstant.PATH_MEDICINE_WORD_OPERATION;
  }
}
