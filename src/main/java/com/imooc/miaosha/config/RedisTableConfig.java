package com.imooc.miaosha.config;

public class RedisTableConfig {
	public static final String db_redis_rank_prex = "zset:rank";
	
	public static final String db_redis_rank_wait_send = "list:rank:award:wait:send";
	
	public static final String db_redis_rank_send_template = "list:rank:award:send:template";
	
	//定义模板
	
	

}
