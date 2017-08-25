package com.qx.test.atomikosdemo.config.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by qinxue on 2017/8/25.
 */
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerProperties {
    private String basePackage;
    private String title = "Atomikos Restful Api";
    private String description = "Atomikos Micro-service Web Api";
    private String trrmsOfServiceUrl;
    private String contact = "christagger";
    private String version = "1.0";
}
