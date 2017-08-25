package com.qx.test.atomikosdemo.config.swagger;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Created by qinxue on 2017/8/25.
 */
@EnableConfigurationProperties(SwaggerProperties.class)
@Configuration
public class SwaggerConfig {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket creatRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .produces(Sets.newHashSet("application/json"))
                .consumes(Sets.newHashSet("application/json"))
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .termsOfServiceUrl(swaggerProperties.getTrrmsOfServiceUrl())
                .contact(swaggerProperties.getContact())
                .version(swaggerProperties.getVersion())
                .build();
    }
}
