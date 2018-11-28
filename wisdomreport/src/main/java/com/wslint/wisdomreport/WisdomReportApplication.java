package com.wslint.wisdomreport;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/// @EnableScheduling
/** @author yuxr */
@SpringBootApplication
@MapperScan("com.wslint.wisdomreport.dao")
public class WisdomReportApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {

    SpringApplication springApplication = new SpringApplication(WisdomReportApplication.class);
    // 控制无法通过命令行修改参数
    springApplication.setAddCommandLineProperties(false);
    springApplication.run(args);
    // 另一种加载方式
    // SpringApplication.run(wisdomreportApplication.class, args);

  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    // 注意这里要指向原先用main方法执行的Application启动类
    return builder.sources(WisdomReportApplication.class);
  }
}
