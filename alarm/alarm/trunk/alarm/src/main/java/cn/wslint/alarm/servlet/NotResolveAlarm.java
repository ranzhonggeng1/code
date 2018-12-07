package cn.wslint.alarm.servlet;

import cn.wslint.alarm.common.Constant;
import cn.wslint.alarm.dao.impl.AlarmReceiverDaoImpl;
import cn.wslint.alarm.resources.AlarmResource;
import cn.wslint.alarm.resources.AlarmSearchResource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 1、未解决告警查询 2、告警恢复
 *
 * @author ranzhonggeng
 * <p>2018年11月14日
 */
public class NotResolveAlarm extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = LoggerFactory.getLogger(NotResolveAlarm.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {

      logger.debug("********** alarm not resolve get request *************");
      AlarmReceiverDaoImpl alarmReceiverDaoImpl = new AlarmReceiverDaoImpl();
      List<AlarmResource> alarmResource = new ArrayList<AlarmResource>();
      //    		//查看是否存在名称参数
      AlarmSearchResource alarmSearch = new AlarmSearchResource();
      if (req.getQueryString() != null && req.getQueryString().length() != 0) {
        // 存在条件查询
        alarmSearch.setAlarmName(req.getParameter("name"));
        alarmSearch.setBeginTime(req.getParameter("beginTime"));
        alarmSearch.setEndTime(req.getParameter("endTime"));
        alarmResource = alarmReceiverDaoImpl.getAlarmSearch(alarmSearch);
      } else {
        // 查询所有
        alarmResource = alarmReceiverDaoImpl.getAlarmResource();
      }

      // 创建JSON对象
      JSONObject jsonObject = new JSONObject();

      jsonObject.put("code", 200);
      jsonObject.put("log", alarmResource);
      resp.setContentType("text/html;charset=UTF-8");
      PrintWriter out = resp.getWriter();
      out.write(JSONArray.toJSON(jsonObject).toString());
      out.flush();
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      AlarmResource alarmResource = new AlarmResource();

      BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));

      String str = "";
      String wholeStr = "";
      while ((str = reader.readLine()) != null) { // 一行一行的读取body体里面的内容；
        wholeStr += str;
      }
      JSONObject alarmJson = JSONObject.parseObject(wholeStr); // 转化成json对象

      // 增加
      if (req.getMethod().equalsIgnoreCase("post")) {
        AlarmReceiverDaoImpl alarmReceiverDaoImpl = new AlarmReceiverDaoImpl();
        // 解析post请求
        alarmResource.setHost((String) alarmJson.get(Constant.FIELD_ALARM_HOST));
        alarmResource.setComponentName((String) alarmJson.get(Constant.FIELD_ALARM_COMPONENT_NAME));
        alarmResource.setTraceStack((String) alarmJson.get(Constant.FIELD_ALARM_TRACE_STACK));

        boolean isClearAlarm = alarmReceiverDaoImpl.clearAlarm(alarmResource);

        // 创建JSON对象
        JSONObject jsonObject = new JSONObject();

        if (isClearAlarm) {
          jsonObject.put("code", 200);
          jsonObject.put("msg", "clear alarm success");
        } else {
          jsonObject.put("code", 500);
          jsonObject.put("msg", "clear alarm failed");
        }
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(JSONArray.toJSON(jsonObject).toString());
        out.flush();
        out.close();
      }

    } catch (Exception e) {

    }
  }
}
