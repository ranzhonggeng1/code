package com.wslint.wisdomreport.constant;

import java.io.File;

/**
 * xml文件常量
 *
 * @author yuxr
 * @since 2018/10/23 14:34
 */
public class XMLConstant {

  public static final String FILE_PRODUCT = "product";
  public static final String FILE_MEDICINE = "medicine";
  public static final String FILE_METHOD = "method";
  public static final String FILE_FORM = "form";
  public static final String FILE_PROGRAM = "program";
  public static final String FILE_WORD_VERSION = "version";
  public static final String XML_SUFFIX = ".xml";
  // html模板文件名后缀
  public static final String HTML_SUFFIX = ".html";
  // word模板文件名后缀
  public static final String WORD_SUFFIX = ".docx";
  // html模板头文件名后缀（由word页眉生成）
  public static final String HTML_HEADER_SUFFIX = "_header.html";

  public static final String PATH_PRODUCT = "product" + File.separator;
  public static final String PATH_DOCUMENT = File.separator + "document" + File.separator;
  public static final String PATH_XML = PATH_DOCUMENT + "xml" + File.separator;
  // html模板地址
  public static final String PATH_HTML = PATH_DOCUMENT + "html" + File.separator;
  // word模板地址
  public static final String PATH_WORD = PATH_DOCUMENT + "word" + File.separator;
  public static final String PATH_FIRST_CLASS = "firstClass" + File.separator;
  public static final String PATH_SECOND_CLASS = "secondClass" + File.separator;
  public static final String PATH_MEDICINE_WORD_VERSION = "version" + File.separator;
  public static final String PATH_MEDICINE_WORD_OPERATION = "operation" + File.separator;

  public static final String STR_MAP = "map";
  public static final String STR_LIST = "List";
  public static final String STR_DEFAULT = "default";
  public static final String NUM_1 = "1";
  public static final String STR_HTML_URL = "htmlUrl";
  public static final String STR_IDS = "ids";
  public static final String STR_METHOD = "method";
  public static final String STR_ROOT_PATH = "rootPath";
  public static final String STR_PARAM = "param";
  public static final String STR_HTML_FILEPATH = "filePath";
  public static final String STR_HTML_FILENAME = "fileName";
}
