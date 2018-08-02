package com.imooc.miaosha.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Tuple;

import com.imooc.miaosha.config.RedisTableConfig;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.UserKey;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Rank;
import com.imooc.miaosha.result.RankAll;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.RankService;
import com.imooc.miaosha.service.UserService;

@Controller
@RequestMapping("/rank")
public class RankController {
	@Autowired
	UserService userService;

	@Autowired
	RedisService redisService;
	
	@Autowired
	RankService rankService;
	
	
	//查询我的排行
    @RequestMapping("/myRank")
    @ResponseBody
    public Result<Rank> redisGet(HttpServletRequest request,
			HttpServletResponse response) {
    	String member=request.getParameter("zyzsId");
    	String limitId=request.getParameter("limitId");
        return Result.success(redisService.queryRank(RedisTableConfig.db_redis_rank_prex+":"+limitId, member));
    }
    //保存更新我的排名
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Result<Boolean> rankAdd(HttpServletRequest request,
			HttpServletResponse response) {
    	String openId =request.getParameter("openId");
    	List<String> zyzsid = rankService.findZyzsId(openId);
    	String member = zyzsid.get(0);
    	String limitId=request.getParameter("limitId");
    	Rank rank=redisService.queryRank(RedisTableConfig.db_redis_rank_prex+":"+limitId, member);
    	if(rank.getScore()==null){
    		redisService.zSetAdd(RedisTableConfig.db_redis_rank_prex+":"+limitId, member, 1);
    	}else{
    		redisService.updateRank(RedisTableConfig.db_redis_rank_prex+":"+limitId, member, 1);
    	}
        return Result.success(true);
    }
    
   
    //获取指定排名
    @RequestMapping("/getAllRank")
    @ResponseBody
    public Result<List<RankAll>> rankAll(HttpServletRequest request,
			HttpServletResponse response) {
    	long startOffset =Long.parseLong(request.getParameter("start"));
		long endOffset =Long.parseLong(request.getParameter("end"));
		String limitId=request.getParameter("limitId");
		Set<Tuple> set= redisService.getZset(RedisTableConfig.db_redis_rank_prex+":"+limitId, startOffset, endOffset);
		List<RankAll> result=new ArrayList<RankAll>();
		int i=1;
		for (Tuple temp : set) {
				String zyzsId = temp.getElement();
				Double score=temp.getScore();
				//获取微信
				String nickName= userService.getNickName(zyzsId);
				try {
					nickName=URLDecoder.decode(nickName, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				RankAll all=new RankAll(i,nickName , zyzsId, score);
				i++;
				result.add(all);
		}
		return Result.success(result);
    }
    //获取指定排名
    @RequestMapping("/test")
    @ResponseBody
    public Result<Integer> test(HttpServletRequest request,
			HttpServletResponse response) {
    	int  i=userService.getArea();
        return Result.success(i);
    }
  //查询我的排行
    @RequestMapping("/test2")
    @ResponseBody
    public Result<User> test2(HttpServletRequest request,
			HttpServletResponse response) {
    	User  i=userService.getById(1);
        return Result.success(i);
    }
    
    //获取指定排名
    @RequestMapping("/test3")
    @ResponseBody
    public Result<Integer> test3(HttpServletRequest request,
			HttpServletResponse response) {
    	int  i=userService.getfans();
        return Result.success(i);
    }
}
