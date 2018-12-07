package cn.wslint.log.servlet;

import cn.wslint.log.dao.impl.OptLogServiceImpl;
import cn.wslint.log.resources.OptlogResource;
import cn.wslint.log.resources.OptlogSearchResource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
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
 * 实现日志查询
 *
 * @author ranzhonggeng
 * <p>2018年11月12日
 */
public class OptlogServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = LoggerFactory.getLogger(OptlogServlet.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {

      logger.debug("********** optlog get request *************");
      OptLogServiceImpl optLogServiceImpl = new OptLogServiceImpl();
      List<OptlogResource> optlogResource = new ArrayList<OptlogResource>();

      StringBuffer url = req.getRequestURL();
      //    		//查看是否存在名称参数
      OptlogSearchResource optlogSearch = new OptlogSearchResource();
      if (req.getQueryString() != null && req.getQueryString().length() != 0) {
        // 存在条件查询
        optlogSearch.setName(req.getParameter("name"));
        optlogSearch.setBeginTime(req.getParameter("beginTime"));
        optlogSearch.setEndTime(req.getParameter("endTime"));
        optlogResource = optLogServiceImpl.getOptlogSearch(optlogSearch);
      } else {
        // 查询所有
        optlogResource = optLogServiceImpl.getOptlogAll();
      }

      // 创建JSON对象
      JSONObject jsonObject = new JSONObject();

      jsonObject.put("code", 200);
      jsonObject.put("log", optlogResource);
      resp.setContentType("text/html;charset=UTF-8");
      PrintWriter out = resp.getWriter();
      out.write(JSONArray.toJSON(jsonObject).toString());
      out.flush();
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
