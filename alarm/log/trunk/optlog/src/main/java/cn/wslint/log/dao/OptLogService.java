package cn.wslint.log.dao;

import cn.wslint.log.resources.OptlogAddResource;
import cn.wslint.log.resources.OptlogResource;
import cn.wslint.log.resources.OptlogSearchResource;
import java.io.IOException;
import java.util.List;

/**
 * 提供日志接口
 *
 * @author ranzhonggeng
 * <p>2018年11月13日
 */
public interface OptLogService {

  /**
   * 获取所有的日志信息
   *
   * @return OptlogConstant List
   */
  List<OptlogResource> getOptlogAll();

  /**
   * 外部调用添加日志
   *
   * @return boolean
   */
  boolean handleOptlog(OptlogAddResource optlogAddResource) throws IOException;

  /**
   * @param optlogSearch
   * @return
   */
  List<OptlogResource> getOptlogSearch(OptlogSearchResource optlogSearch);
}
