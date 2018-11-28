package com.wslint.wisdomreport.common.scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务测试类
 *
 * @author yuxr
 * @since 2018/8/9 11:53
 */
@Component
public class ScheduledTasks {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

  @Scheduled(fixedRate = 50000)
  public void reportCurrentTime() {
    System.out.println("现在时间：" + DATE_FORMAT.format(new Date()));
  }
}
