package com.imooc.miaosha.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ConditionalOnClass({EnableTransactionManagement.class})
@Import({ DataBaseConfiguration.class})
@MapperScan(basePackages={"com.imooc.miaosha.dao"})
public class TxxsbatisConfiguration {
 
 

    @Resource(name = "sdDataSourceOne")
    private DataSource sdDataSources;
    
    @Resource(name = "hljDataSource")
    private DataSource hljdataSource;
 
    
 
 
    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(roundRobinDataSouceProxy());
        sqlSessionFactoryBean.setTypeAliasesPackage("com.imooc.miaosha.domain");
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:com/imooc/miaosha/dao/*.xml"));
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }
    /**
     * 有多少个数据源就要配置多少个bean
     * @return
     */
    @Bean
    public AbstractRoutingDataSource roundRobinDataSouceProxy() {
    	DynamicRoutingDataSource proxy = new DynamicRoutingDataSource();
    	
    	Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
    	targetDataSources.put(DataSourceKey.hljDataSource.name(), hljdataSource);
    	targetDataSources.put(DataSourceKey.sdDataSourceOne.name(), sdDataSources);
    	proxy.setDefaultTargetDataSource(hljdataSource);
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }
}
