package com.wslint.wisdomreport.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2配置信息
 *
 * @author yuxr
 * @since 2018/10/11 10:56
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

  @Bean
  public Docket createRestApi() {
    ParameterBuilder ticketPar = new ParameterBuilder();
    List<Parameter> pars = new ArrayList<Parameter>();
    ticketPar
        .name("Authorization")
        .description("鉴权信息")
        .modelRef(new ModelRef("string"))
        .parameterType("header")
        .required(false)
        .build();
    pars.add(ticketPar.build());

    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.wslint.wisdomreport.controller"))
        .paths(PathSelectors.any())
        .build()
        .globalOperationParameters(pars);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("智慧报告项目 APIs")
        .description("智慧报告项目后台api接口文档")
        .version("1.0")
        .build();
  }
}
