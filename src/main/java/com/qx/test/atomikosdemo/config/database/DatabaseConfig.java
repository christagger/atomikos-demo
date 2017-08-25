package com.qx.test.atomikosdemo.config.database;

import com.qx.test.atomikosdemo.config.database.DatabaseProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * Created by qinxue on 2017/8/24.
 */
@EnableConfigurationProperties(DatabaseProperties.class)
@Configuration
public class DatabaseConfig {

    @Autowired
    private DatabaseProperties databaseProperties;

    @Bean(name = "prop")
    public Map<String, String> prop() {
        return databaseProperties.getProp();
    }

    @Bean(name = "dataSources")
    public List<Map<String, String>> dataSources() {
        return databaseProperties.getDataSources();
    }
}
