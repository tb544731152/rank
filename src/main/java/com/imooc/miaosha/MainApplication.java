package com.imooc.miaosha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

import com.imooc.miaosha.config.threadpool.TaskThreadPoolConfig;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties({TaskThreadPoolConfig.class} ) // 开启配置属性支持
public class MainApplication {
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
}
