package com.qx.test.atomikosdemo.config.datasource;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import com.qx.test.atomikosdemo.config.datasource.DynamicDataSourceContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qinxue on 2017/8/24.
 */
@Component
@DependsOn(value = {"prop", "dataSources"})
public class DynamicDataSourceRegister extends AbstractRoutingDataSource {

    @Autowired
    @Qualifier(value = "prop")
    private Map<String, String> prop;

    @Autowired
    @Qualifier(value = "dataSources")
    private List<Map<String, String>> dataSources;

    private Map<Object, Object> dataSourcesMap = new HashMap<>();

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.get();
    }

    @PostConstruct
    public void init() {
        try {
            String defaultDataSourceUniqueRersourceName = prop.get("defaultDataSourceUniqueRersourceName");
            for (Map<String, String> map : dataSources) {
                String uniqueResourceName = map.get("uniqueResourceName");
                String url = map.get("url");
                String username = map.get("username");
                String password = map.get("password");
                DataSource dataSource = this.getXADataSource(uniqueResourceName, url, username, password);
                dataSourcesMap.put(uniqueResourceName, dataSource);
                if (defaultDataSourceUniqueRersourceName.equals(uniqueResourceName)) {
                    this.setDefaultTargetDataSource(dataSource);
                }
            }
            this.setTargetDataSources(dataSourcesMap);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DataSource getXADataSource(String uniqueResourceName, String url,
                                      String username, String password) throws SQLException {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(url);
        mysqlXADataSource.setUser(username);
        mysqlXADataSource.setPassword(password);
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(mysqlXADataSource);
        atomikosDataSourceBean.setUniqueResourceName(uniqueResourceName);
        atomikosDataSourceBean.setMinPoolSize(Integer.valueOf(prop.get("minPoolSize")));
        atomikosDataSourceBean.setMaxPoolSize(Integer.valueOf(prop.get("maxPoolSize")));
        atomikosDataSourceBean.setMaxLifetime(Integer.valueOf(prop.get("maxLifetime")));
        atomikosDataSourceBean.setBorrowConnectionTimeout(Integer.valueOf(prop.get("borrowConnectionTimeout")));
        atomikosDataSourceBean.setLoginTimeout(Integer.valueOf(prop.get("loginTimeout")));
        atomikosDataSourceBean.setMaintenanceInterval(Integer.valueOf(prop.get("maintenanceInterval")));
        atomikosDataSourceBean.setMaxIdleTime(Integer.valueOf(prop.get("maxIdleTime")));
        atomikosDataSourceBean.setTestQuery(prop.get("testQuery"));
        return atomikosDataSourceBean;
    }
}
