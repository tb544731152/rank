package com.imooc.miaosha.config;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.service.RankService;
import com.imooc.miaosha.task.SendTemplateTask;
@Service
public class InitTaskConfig  implements InitializingBean{
	protected final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	@Autowired
	private RedisService redisService;
	@Autowired
	private RankService rankService;
	@Override
	public void afterPropertiesSet() throws Exception {
		// 处理同步客户任务的线程池
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4,
								5, 10, TimeUnit.SECONDS,
								new LinkedBlockingQueue<Runnable>(1),
								new ThreadFactory() {
									final AtomicInteger threadNumber = new AtomicInteger(1);
									public Thread newThread(Runnable r) {
										Thread t = new Thread(Thread.currentThread()
												.getThreadGroup(), r, "ThreadPoolExecutor中线程"
												+ (threadNumber.getAndIncrement()));
										t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
													public void uncaughtException(Thread t,
															Throwable e) {
														logger.error("ThreadPoolExecutor异常:"+e.getMessage()+ "\r\n");
																		
													}
												});
										return t;
									}
								}, new ThreadPoolExecutor.CallerRunsPolicy());

		// 从服务器获取任务的 计划任务线程
		ScheduledExecutorService scheduledService = Executors
								.newScheduledThreadPool(2);

		// 执行计划
		scheduledService.scheduleWithFixedDelay(new SendTemplateTask(threadPool,redisService,rankService),
						2, 2, TimeUnit.MICROSECONDS);
		
	}

	
}
