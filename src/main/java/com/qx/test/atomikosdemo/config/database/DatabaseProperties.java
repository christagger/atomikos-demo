package com.qx.test.atomikosdemo.config.database;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qinxue on 2017/8/24.
 */
@ConfigurationProperties(prefix = "database")
@Data
public class DatabaseProperties {
    private Map<String, String> prop = new HashMap<>();
    private List<Map<String, String>> dataSources = new ArrayList<>();
}
