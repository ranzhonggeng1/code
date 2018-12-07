package cn.wslint.log.dao.impl;

import cn.wslint.log.DB.DBconn;
import cn.wslint.log.common.OptlogConstant;
import cn.wslint.log.dao.OptLogService;
import cn.wslint.log.resources.OptlogAddResource;
import cn.wslint.log.resources.OptlogResource;
import cn.wslint.log.resources.OptlogSearchResource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志接口实现
 *
 * @author ranzhonggeng
 * <p>2018年11月13日
 */
public class OptLogServiceImpl implements OptLogService {

  private static final Logger logger = LoggerFactory.getLogger(OptLogServiceImpl.class);

  private PreparedStatement ptmt = null;
  private ResultSet rs = null;

  // 获取所有日志
  @Override
  public List<OptlogResource> getOptlogAll() {
    // TODO Auto-generated method stub
    List<OptlogResource> list = new ArrayList<OptlogResource>();
    try {
      DBconn.init();
      ResultSet rs = DBconn.selectSql("select * from optlog");
      //            System.out.println("111111111111" + System.getProperty("user.name"));
      while (rs.next()) {
        OptlogResource optlogConstant = new OptlogResource();
        optlogConstant.setId(rs.getString(OptlogConstant.OPTLOG_ID));
        optlogConstant.setName(rs.getString(OptlogConstant.OPTLOG_NAME));
        optlogConstant.setDeviceHost(rs.getString(OptlogConstant.OPTLOG_DEVICE_HOST));
        optlogConstant.setServiceHost(rs.getString(OptlogConstant.OPTLOG_SERVICE_HOST));
        optlogConstant.setType(rs.getString(OptlogConstant.OPTLOG_TYPE));
        optlogConstant.setObjectId(rs.getString(OptlogConstant.OPTLOG_OBJECT_ID));
        optlogConstant.setObjectName(rs.getString(OptlogConstant.OPTLOG_OBJECT_NAME));
        optlogConstant.setDateTime(rs.getString(OptlogConstant.OPTLOG_DAYE_TIME));
        optlogConstant.setUserName(rs.getString(OptlogConstant.OPTLOG_USER_NAME));
        optlogConstant.setUserId(rs.getString(OptlogConstant.OPTLOG_USER_ID));
        optlogConstant.setResult(rs.getString(OptlogConstant.OPTLOG_RESULT));
        optlogConstant.setContent(rs.getString(OptlogConstant.OPTLOG_CONTENT));

        list.add(optlogConstant);
      }
      DBconn.closeConn();
      return list;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 日志条件查询
   *
   * @return OptlogResource List
   */
  @Override
  public List<OptlogResource> getOptlogSearch(OptlogSearchResource optlogSearch) {
    // TODO Auto-generated method stub
    List<OptlogResource> list = new ArrayList<OptlogResource>();
    try {
      DBconn.init();
      String sql = "select * from optlog";
      String searchInfo = "";
      if (optlogSearch.getName() != null && optlogSearch.getName().length() != 0) {
        searchInfo += " name like \"%" + optlogSearch.getName() + "%\"";
      }
      if (optlogSearch.getBeginTime() != null
          && optlogSearch.getBeginTime().length() != 0
          && optlogSearch.getEndTime() != null
          && optlogSearch.getEndTime().length() != 0) {
        searchInfo +=
            " date_time between '"
                + optlogSearch.getBeginTime()
                + "' and '"
                + optlogSearch.getEndTime()
                + "'";
      }

      if (searchInfo.length() != 0) {
        sql += " where" + searchInfo;
      }
      logger.debug(sql);
      ResultSet rs = DBconn.selectSql(sql);
      while (rs.next()) {
        OptlogResource optlogConstant = new OptlogResource();
        optlogConstant.setId(rs.getString(OptlogConstant.OPTLOG_ID));
        optlogConstant.setName(rs.getString(OptlogConstant.OPTLOG_NAME));
        optlogConstant.setDeviceHost(rs.getString(OptlogConstant.OPTLOG_DEVICE_HOST));
        optlogConstant.setServiceHost(rs.getString(OptlogConstant.OPTLOG_SERVICE_HOST));
        optlogConstant.setType(rs.getString(OptlogConstant.OPTLOG_TYPE));
        optlogConstant.setObjectId(rs.getString(OptlogConstant.OPTLOG_OBJECT_ID));
        optlogConstant.setObjectName(rs.getString(OptlogConstant.OPTLOG_OBJECT_NAME));
        optlogConstant.setDateTime(rs.getString(OptlogConstant.OPTLOG_DAYE_TIME));
        optlogConstant.setUserName(rs.getString(OptlogConstant.OPTLOG_USER_NAME));
        optlogConstant.setUserId(rs.getString(OptlogConstant.OPTLOG_USER_ID));
        optlogConstant.setResult(rs.getString(OptlogConstant.OPTLOG_RESULT));
        optlogConstant.setContent(rs.getString(OptlogConstant.OPTLOG_CONTENT));

        list.add(optlogConstant);
      }
      DBconn.closeConn();
      return list;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  // 添加日志DB
  public boolean addOptlog(OptlogResource optlogResource) {
    // TODO Auto-generated method stub

    /** 获取当前用户id */
    boolean flag = false;
    DBconn.init();
    int i =
        DBconn.addUpdDel(
            "insert into optlog(id,name,device_host,service_host,type,user_name,user_id,date_time,object_name,object_id,result,content) "
                + "values('"
                + optlogResource.getId()
                + "','"
                + optlogResource.getName()
                + "','"
                + optlogResource.getDeviceHost()
                + "','"
                + optlogResource.getServiceHost()
                + "','"
                + optlogResource.getType()
                + "','"
                + optlogResource.getUserName()
                + "','"
                + optlogResource.getUserId()
                + "','"
                + optlogResource.getDateTime()
                + "','"
                + optlogResource.getObjectName()
                + "','"
                + optlogResource.getObjectId()
                + "','"
                + optlogResource.getResult()
                + "','"
                + optlogResource.getContent()
                + "')");
    if (i > 0) {
      flag = true;
    }
    DBconn.closeConn();
    return flag;
  }

  // 处理外部的添加日志

  /**
   * @return 添加成功
   */
  @Override
  public boolean handleOptlog(OptlogAddResource optlogAddResource) {
    // TODO Auto-generated method stub
    System.out.println("1111111111");
    boolean flag = false;
    DBconn.init();
    int i =
        DBconn.addUpdDel(
            "insert into optlog(id,name,device_host,service_host,type,user_name,user_id,date_time,object_name,object_id,result,content) "
                + "values('"
                + optlogAddResource.getId()
                + "','"
                + optlogAddResource.getName()
                + "','"
                + optlogAddResource.getDeviceHost()
                + "','"
                + optlogAddResource.getServiceHost()
                + "','"
                + optlogAddResource.getType()
                + "','"
                + optlogAddResource.getUserName()
                + "','"
                + optlogAddResource.getUserId()
                + "','"
                + optlogAddResource.getDateTime()
                + "','"
                + optlogAddResource.getObjectName()
                + "','"
                + optlogAddResource.getObjectId()
                + "','"
                + optlogAddResource.getResult()
                + "','"
                + optlogAddResource.getContent()
                + "')");
    if (i > 0) {
      flag = true;
    }
    DBconn.closeConn();
    return flag;
  }
}
