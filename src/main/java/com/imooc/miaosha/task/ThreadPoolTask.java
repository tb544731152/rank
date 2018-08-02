package com.imooc.miaosha.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.miaosha.config.RedisTableConfig;
import com.imooc.miaosha.domain.TerminalRankWinningList;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.service.RankService;
import com.imooc.miaosha.util.SerializeUtils;
import com.imooc.miaosha.util.WxTemplateUtil;

public class ThreadPoolTask implements Runnable {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	private RedisService redisService;
	private RankService rankService;
	private List<byte[]> taskList;

	public ThreadPoolTask(List<byte[]> taskList,RedisService redisService,RankService rankService) {
		this.taskList = taskList;
		this.redisService=redisService;
		this.rankService=rankService;
	}

	@Override
	public void run() {
		
		try {
			for (byte[] task : taskList) {
				try {
					Object obj = SerializeUtils.unserialize(task);
					if (obj instanceof TerminalRankWinningList) {
						TerminalRankWinningList rankAward = (TerminalRankWinningList) obj;
						logger.info("推送模板："+rankAward.getOpenId());
						sendTemplate(rankAward);
					}
				} catch (Exception e) {
					 e.printStackTrace();
					 logger.error("ThreadPoolTask.run异常:" + e.getMessage());
				}
				
			}
		} catch (Exception e) {
			logger.error("ThreadPoolTask.run异常:" + e.getMessage());
		}
	}
	
	public void sendTemplate(TerminalRankWinningList rankAward){
		String token = rankService.getToken("wxfe05835b1793dfe7");
		String desc = "奖品已发放至[我的奖品],奖励金直接发送到我的钱包;实物奖励需填写地址信息领取，以便及时发放，请注意领取查收！";
		JSONObject results =WxTemplateUtil.send_award(rankAward.getOpenId(), "zKEJf7N1ToHtE-lXW29WoLw_dyskrgTcxqcQjEX6JWM", token, rankAward.getPrizeName(), "排行-"+rankAward.getLimitName(),"您此次活动排"+rankAward.getNum()+"名", desc);
		if(!results.get("errcode").equals(0)){
			logger.info("推送模板失败|openId:"+rankAward.getOpenId()+"|limitId:"+rankAward.getLimitId()+"|奖品名称："+rankAward.getPrizeName());
			logger.info("errorcode:"+results.get("errcode")+results.toString());
			rankService.saveRedisListWaitSend(rankAward,  RedisTableConfig.db_redis_rank_send_template);
			
		}
	}

}
