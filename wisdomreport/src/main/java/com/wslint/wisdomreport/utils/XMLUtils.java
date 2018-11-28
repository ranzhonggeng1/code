package com.wslint.wisdomreport.utils;

import com.wslint.wisdomreport.constant.XMLConstant;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 封装了XML转换成object，object转换成XML的代码
 *
 * @author yuxr
 * @since 2018/10/23
 */
public class XMLUtils {

  /** logger 日志记录 */
  private static final Logger LOGGER = LoggerFactory.getLogger(XMLUtils.class);

  /**
   * 将对象直接转换成String类型的XML输出
   *
   * @param obj 对象
   * @return xml string
   */
  public static String dtoToXml(Object obj) {
    // 创建输出流
    StringWriter sw = new StringWriter();
    try {
      // 利用jdk中自带的转换类实现
      JAXBContext context = JAXBContext.newInstance(obj.getClass());

      Marshaller marshaller = context.createMarshaller();
      // 格式化xml输出的格式
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      // 将对象转换成输出流形式的xml
      marshaller.marshal(obj, sw);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return sw.toString();
  }

  /**
   * 将对象根据路径转换成xml文件
   *
   * @param obj 对象
   * @param path xml存储路径
   * @param name xml文件名
   */
  public static boolean dtoToXml(Object obj, String path, String name) {
    String filePath = path + name + XMLConstant.XML_SUFFIX;
    try {
      // 利用jdk中自带的转换类实现
      JAXBContext context = JAXBContext.newInstance(obj.getClass());
      Marshaller marshaller = context.createMarshaller();
      // 格式化xml输出的格式
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      // 创建路径
      if (!FileUtils.createPath(path)) {
        return false;
      }
      // 将对象转换成输出流形式的xml
      // 创建输出流
      FileWriter fw = new FileWriter(filePath);
      marshaller.marshal(obj, fw);
      fw.close();
    } catch (JAXBException e) {
      LOGGER.info("{} 文件数据转换失败！", filePath);
      LOGGER.error(e.getMessage());
      e.printStackTrace();
      return false;
    } catch (IOException e) {
      LOGGER.info("{} 文件读写失败！", filePath);
      LOGGER.error(e.getMessage());
      e.printStackTrace();
      return false;
    }
    LOGGER.info("{} 文件转换存储成功！", filePath);
    return true;
  }

  /**
   * 将String类型的xml转换成对象
   *
   * @param clazz class
   * @param xmlStr xml String
   * @return xml对象
   */
  @SuppressWarnings("unchecked")
  public static Object xmlStrToDto(Class clazz, String xmlStr) {
    Object xmlObject = null;
    try {
      JAXBContext context = JAXBContext.newInstance(clazz);
      // 进行将Xml转成对象的核心接口
      Unmarshaller unmarshaller = context.createUnmarshaller();
      StringReader sr = new StringReader(xmlStr);
      xmlObject = unmarshaller.unmarshal(sr);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return xmlObject;
  }

  /**
   * 将file类型的xml转换成对象
   *
   * @param clazz class
   * @param path xml path
   * @param name xml name
   * @return xml对象
   */
  @SuppressWarnings("unchecked")
  public static <T> T xmlFileToDto(Class<T> clazz, String path, String name) {
    String xmlPath = path + name + XMLConstant.XML_SUFFIX;
    T t = null;
    try {
      t = clazz.newInstance();
      JAXBContext context = JAXBContext.newInstance(clazz);
      Unmarshaller unmarshaller = context.createUnmarshaller();
      FileReader fr = null;
      try {
        fr = new FileReader(xmlPath);
      } catch (FileNotFoundException e) {
        LOGGER.info("{} 文件不存在！", xmlPath);
        LOGGER.error(e.getMessage());
        e.printStackTrace();
        return t;
      }
      t = (T) unmarshaller.unmarshal(fr);
    } catch (JAXBException e) {
      LOGGER.info("{} 文件数据转换错误！", xmlPath);
      LOGGER.error(e.getMessage());
      e.printStackTrace();
    } catch (IllegalAccessException | InstantiationException e) {
      LOGGER.info("{} 对象创建错误！", clazz.toString());
      LOGGER.error(e.getMessage());
      e.printStackTrace();
    }
    LOGGER.info("{} 文件读取转换成功！", xmlPath);
    return t;
  }
}
