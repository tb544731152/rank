package com.imooc.miaosha.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataBaseConfiguration{
 
    /**
     * 主库--山东
     * @return
     */
    @Bean(name = "sdDataSourceOne")
    @ConfigurationProperties(prefix = "spring.datasource.druid.master.sd")
    public DataSource sdDataSource(){
    	
        return DataSourceBuilder.create().build();
    }
    /**
     * 主库 --黑龙江
     * @return
     */
    @Bean(name="hljDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.master.hlj")
    public DataSource hljDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    /**
     * Dynamic data source.
     *
     * @return the data source
     */
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<Object, Object>(4);
        dataSourceMap.put(DataSourceKey.hljDataSource.name(), hljDataSource());
        dataSourceMap.put(DataSourceKey.sdDataSourceOne.name(), sdDataSource());

        // Set master datasource as default
        dynamicRoutingDataSource.setDefaultTargetDataSource(hljDataSource());
        // Set master and slave datasource as target datasource
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
        // To put datasource keys into DataSourceContextHolder to judge if the datasource is exist
        DynamicDataSourceContextHolder.dataSourceKeys.addAll(dataSourceMap.keySet());
        return dynamicRoutingDataSource;
    }
    
}

