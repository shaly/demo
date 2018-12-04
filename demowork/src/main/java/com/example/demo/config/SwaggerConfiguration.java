package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.Parameter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxmzv on 2018/11/26.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Value("${swagger.host}")
    private String swaggerHost;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${app.version}")
    private String appVersion;

    @Bean
    public Docket createRestApi(){//设置请带上Token（需要校验Token的接口才校验Token，不需要校验的Token不影响使用）
        ParameterBuilder xxx_token=new ParameterBuilder();
        ParameterBuilder xxxx_token=new ParameterBuilder();
        List<Parameter> pars=new ArrayList<Parameter>();
        //请求时的token
        xxx_token.name("xxx_token").description("xxx描述").modelRef(new ModelRef("string"))
                .parameterType("header").required(false).build();
        xxxx_token.name("xxxx_token").description("xxxxxx描述").modelRef(new ModelRef("string"))
                .parameterType("header").required(false).build();
        pars.add(xxx_token.build());
        pars.add(xxxx_token.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .host(swaggerHost)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                .build()
                .globalOperationParameters(pars)//设置Token参数
                .apiInfo(apiInfo());//应用信息设置
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title(applicationName)
                .description("xxx服务")
                .version(appVersion)
                .build();
    }

}
