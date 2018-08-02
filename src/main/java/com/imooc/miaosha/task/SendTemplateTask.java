package com.imooc.miaosha.task;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imooc.miaosha.config.RedisTableConfig;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.service.RankService;



public class SendTemplateTask implements Runnable{
	protected final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	private ThreadPoolExecutor threadPool;
	private RedisService redisService;
	private RankService rankService;

	public SendTemplateTask(ThreadPoolExecutor threadPool,RedisService redisService,RankService rankService) {
		this.threadPool = threadPool;
		this.redisService=redisService;
		this.rankService=rankService;
	}

	@Override
	public void run() {
		try {
			int taskNum = 5;
			List<byte[]> taskList = redisService.getByteFromList(
					RedisTableConfig.db_redis_rank_send_template, taskNum);
			if (taskList == null) {
				return;
			}

			logger.info("从"
					+ RedisTableConfig.db_redis_rank_send_template
					+ "队列中获取" + taskList.size() + "个任务");

			threadPool.execute(new ThreadPoolTask(taskList,redisService,rankService));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AcquireTask.run()异常:" + e.getMessage());
		}
	}
}
