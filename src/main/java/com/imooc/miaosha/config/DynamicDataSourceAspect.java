package com.imooc.miaosha.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Multiple DataSource Aspect
 *
 * @author HelloWood
 * @date 2017-08-15 11:37
 * @email hellowoodes@gmail.com
 */
@Aspect
@Component
public class DynamicDataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    private final String[] SD_QUERY_PREFIX = {"sd"};

    /**
     * Dao aspect.
     */
    @Pointcut("execution( * com.imooc.miaosha.dao.*.*(..))")
    public void daoAspect() {
    }

    /**
     * Switch DataSource
     *
     * @param point the point
     */
    @Before("daoAspect()")
    public void switchDataSource(JoinPoint point) {
        Boolean isQueryMethod = isSDQueryMethod(point.getSignature().getName());
        if (isQueryMethod) {
            DynamicDataSourceContextHolder.useSDMasterDataSource();
            logger.info("Switch DataSource to [{}] in Method [{}]",
                    DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
        }else{
        	DynamicDataSourceContextHolder.useHLJMasterDataSource();
        }
    }

    /**
     * Restore DataSource
     *
     * @param point the point
     */
    @After("daoAspect())")
    public void restoreDataSource(JoinPoint point) {
        DynamicDataSourceContextHolder.clearSDDataSourceKey();
        logger.debug("Restore DataSource to [{}] in Method [{}]",
                DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
    }


    /**
     * Judge if method start with query prefix
     *
     * @param methodName
     * @return
     */
    private Boolean isSDQueryMethod(String methodName) {
        for (String prefix : SD_QUERY_PREFIX) {
            if (methodName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

}
