package com.imooc.miaosha.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Multiple DataSource Context Holder
 *
 * @author zhaokui
 * @date 2017 -08-15 14:26
 * @Email hellowoodes @gmail.com
 */
public class DynamicDataSourceContextHolder {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);


    /**
     * Maintain variable for every thread, to avoid effect other thread
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = ThreadLocal.withInitial(DataSourceKey.hljDataSource::name);
    


    /**
     * All DataSource List
     */
    public static List<Object> dataSourceKeys = new ArrayList<Object>();

    /**
     * The constant slaveDataSourceKeys.
     */
    public static List<Object> slaveDataSourceKeys = new ArrayList<Object>();

    /**
     * To switch DataSource
     *
     * @param key the key
     */
    public static void setDataSourceKey(String key) {
        CONTEXT_HOLDER.set(key);
    }

    /**
     * Use master data source.
     */
    public static void useSDMasterDataSource() {
        CONTEXT_HOLDER.set(DataSourceKey.sdDataSourceOne.name());
    }
    /**
     * Use master data source.
     */
    public static void useHLJMasterDataSource() {
        CONTEXT_HOLDER.set(DataSourceKey.hljDataSource.name());
    }
    /**
     * Get current DataSource
     *
     * @return data source key
     */
    public static String getDataSourceKey() {
        return CONTEXT_HOLDER.get();
    }
    /**
     * To set DataSource as default
     */
    public static void clearSDDataSourceKey() {
        CONTEXT_HOLDER.remove();
    }
    /**
     * Check if give DataSource is in current DataSource list
     *
     * @param key the key
     * @return boolean boolean
     */
    public static boolean containDataSourceKey(String key) {
        return dataSourceKeys.contains(key);
    }
}

