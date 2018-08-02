package com.imooc.miaosha.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.miaosha.config.RedisTableConfig;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.RankService;
//排行奖品生成及发送
@Controller
@RequestMapping("/rankAward")
public class RankAwardController {


	@Autowired
	RankService rankService;
	@Autowired
	RedisService redisService;
	
	//generate
    @RequestMapping("/init")
    @ResponseBody
    public Result<CodeMsg> init(HttpServletRequest request,
			HttpServletResponse response) {
    	String limitId=request.getParameter("limitId");
    	List<String> zyzsIds=  rankService.getZyzsId();
    	for(String zyzsid : zyzsIds){
    		int i=(int)(Math.random()*100); 
    		redisService.zSetAdd(RedisTableConfig.db_redis_rank_prex+":"+limitId, zyzsid, i);
    	}
		return Result.success(CodeMsg.SUCCESS);
    }
	
    
    
    //generate
    @RequestMapping("/generate")
    @ResponseBody
    public Result<CodeMsg> rankAll(HttpServletRequest request,
			HttpServletResponse response) {
		String limitId=request.getParameter("limitId");
		return Result.success(rankService.generate(limitId));
    }
    //发奖
    @RequestMapping("/sendAward")
    @ResponseBody
    public Result<CodeMsg> rankAwardSend(HttpServletRequest request,
			HttpServletResponse response) {
    	rankService.rankSendAward();
		return Result.success(CodeMsg.SUCCESS);
    }
    
}
