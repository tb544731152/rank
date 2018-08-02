package com.imooc.miaosha.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Tuple;

import com.imooc.miaosha.config.RedisTableConfig;
import com.imooc.miaosha.dao.RankDao;
import com.imooc.miaosha.domain.TerminalRankTask;
import com.imooc.miaosha.domain.TerminalRankWinningList;
import com.imooc.miaosha.domain.TerminalTaskLimit;
import com.imooc.miaosha.domain.Terminal_draw_record;
import com.imooc.miaosha.domain.ZyzsMoneyRecord;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.util.HttpUtils;
import com.imooc.miaosha.util.SerializeUtils;



@Service
public class RankService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	@Autowired
	RankDao rankDao;
	
	@Autowired
	RedisService redisService;
	
	public CodeMsg generate(String limitId) {
		//查询任务
		TerminalTaskLimit limit = rankDao.sdgetById(limitId);
		if(limit==null){
			return CodeMsg.ACTIVITY_ERROR;
		}
		//奖励
		List<TerminalRankTask> tasks= rankDao.sdselecRankTask(limitId);
		if(tasks==null || tasks.size()==0){
			return CodeMsg.TASK_ERROR;
		}
		int max = 1 ;
		for(TerminalRankTask task : tasks){
			if(task.getEnd() > max ){
				max = task.getEnd();
			}
		}
		//查询排名情况
		Set<Tuple> set= redisService.getZset(RedisTableConfig.db_redis_rank_prex+":"+limitId, 0, max - 1);
		List<Tuple> tuples = new ArrayList<Tuple>(set);
		for(int i =0 ;i < tuples.size() ; i++){  
			Tuple tuple = tuples.get(i);
			String zyzsId = tuple.getElement();
			Double saoma=tuple.getScore();
			TerminalRankWinningList rankRecord=rankDao.sdselecRankRecord("0",limitId, zyzsId);
			if(rankRecord != null && rankRecord.getStatus().equals("0")){
				return CodeMsg.Generate_WAIT;
			}
			if(rankRecord != null && rankRecord.getStatus().equals("1")){
				return CodeMsg.Generate_SENDED;
			}
			TerminalRankWinningList rank = generateRank(zyzsId, saoma, i, tasks, limit);
			//开始生成中奖名单
			int num = rankDao.sdinsertRankRecord(rank);
			//保存中奖记录
			rankDao.sdsaveRecord(rank);
		 }
		return CodeMsg.Generate_SUCCESS;
	}

	
	public TerminalRankWinningList generateRank(String zyzsId,Double saoma,
			int ranking,
			List<TerminalRankTask> tasks,TerminalTaskLimit limit
			){
		TerminalRankWinningList rank=new TerminalRankWinningList();
		rank.setId(UUID.randomUUID().toString());
		rank.setIsDelete("0");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
		rank.setLimitDate(sdf.format(limit.getStartDate())+"至"+sdf.format(limit.getEndDate()));
		rank.setLimitId(limit.getId());
		rank.setLimitName(limit.getName());
		rank.setNum(ranking+1);
		rank.setCreateDate(new Date());
		rank.setOpenId(rankDao.getOpenId(zyzsId).get(0));
		if(saoma!=null){
			rank.setSaoCount(saoma.intValue());
		}else{
			rank.setSaoCount(0);
		}
		rank.setStatus("0");
		TerminalRankTask task = getTaskId(ranking, tasks);
		rank.setType(task.getType());
		rank.setCount(Integer.parseInt(task.getCount()));
		rank.setTaskId(task.getId());
		rank.setZyzsId(zyzsId);
		rank.setPrizeName(task.getPrizeName());
		saveRedisListWaitSend(rank,RedisTableConfig.db_redis_rank_wait_send);
		return rank;
	}
	public boolean saveRedisListWaitSend(TerminalRankWinningList rank,String listname){
		logger.info("存入："+listname);
		List<byte[]> list=new ArrayList<byte[]>();
		list.add(SerializeUtils.serialize(rank));
		try {
			redisService.addByteToList(listname,list);
		} catch (Exception e) {
			return false;
		}
		return true;
		
	}
	public TerminalRankTask getTaskId(int ranking,
			List<TerminalRankTask> tasks){
		for(TerminalRankTask task : tasks){
			if(ranking+1 <= task.getEnd()){
				return task;
			}
		}
		return null;
	}
	//查询粉丝信息
	public List<String> getZyzsId(){
		return  rankDao.getZyzsId();
	}

	public String getToken(String appId){
		return rankDao.getAccessToken(appId);
	}
	
	public List<String> findZyzsId(String openId) {
		return rankDao.findZyzsId(openId);
	}
	
	@Async("myTaskAsyncPool")
	public void rankSendAward(){
		int i= 0;
		while(redisService.existKey(RedisTableConfig.db_redis_rank_wait_send.getBytes())){
			byte[] wait_send_task = redisService.getByteFromList(RedisTableConfig.db_redis_rank_wait_send);
			Object obj = SerializeUtils.unserialize(wait_send_task);
			if (obj instanceof TerminalRankWinningList) {
				TerminalRankWinningList rankAward = (TerminalRankWinningList) obj;
				logger.info("发送奖品："+rankAward.getPrizeName()+"--openId:"+rankAward.getOpenId());
				//发奖
				sendAwar(rankAward);
				saveRedisListWaitSend(rankAward, RedisTableConfig.db_redis_rank_send_template);
			}
			i++;
		}
		logger.info("共发奖---："+i +"名");
	}

	@Transactional
	private void sendAwar(TerminalRankWinningList rankAward) {
		//存放我的奖品
		Integer activityId= rankDao.selectActivityById(Integer.parseInt(rankAward.getTaskId()));
		Terminal_draw_record record =new Terminal_draw_record();
		record.setId(UUID.randomUUID().toString());
		record.setActivityId(activityId);
		record.setCreateDate(new Date());
		record.setStartDate(new Date());
		record.setIsDelete("0");
		//0标识实物1标识虚拟产品 2大额红包 3奖品（钱）
		if(rankAward.getType().equals("0")){
			//0 红包 1 实物 2 烟酒币  
			record.setStatus("1");
			record.setType("3");
			//发红包
			String orderId= "rank_"+UUID.randomUUID();
			ZyzsMoneyRecord zyzsMoneyRecord = new ZyzsMoneyRecord();
			Date createDate = new Date();
			zyzsMoneyRecord.setCreateDate(createDate);
			zyzsMoneyRecord.setMoney(rankAward.getCount());
			zyzsMoneyRecord.setIsDelete("0");
			zyzsMoneyRecord.setType("0");
			zyzsMoneyRecord.setOpenId(rankAward.getOpenId());
			zyzsMoneyRecord.setZsNum(orderId);
			zyzsMoneyRecord.setOrderId(orderId);
			zyzsMoneyRecord.setStatus("8");
			zyzsMoneyRecord.setIsView("0");
			rankDao.saveMoneyRecord(zyzsMoneyRecord);
			rankDao.updateMyMoney(rankAward.getCount(),rankAward.getOpenId());
		}
		if(rankAward.getType().equals("1")){
			//实物
			record.setStatus("0");
			record.setType("0");
		}
		if(rankAward.getType().equals("2")){
			//虚拟产品--烟酒币
			record.setStatus("1");
			record.setType("1");
			//发烟酒币
		   String name = "龙江排行活动";
		   String paramm = "zyzsId=" + rankAward.getZyzsId() + "&yjCoinValue="+rankAward.getCount()+"&name=" + name;
		   String resultJson = HttpUtils.postJsonRequest("http://10.252.0.120:8081/zyzsYj/conDetail/updateSmokeWineCoin?", paramm);
		   logger.info("---------------龙江排行活动:--------" + resultJson);
		}
		record.setProductId(Integer.parseInt(rankAward.getTaskId()));
		record.setNum(1);
		record.setProductName(rankAward.getPrizeName());
		record.setZyzsId(rankAward.getZyzsId());
		rankDao.saveAwardRecord(record);
		
		
	}
	
}
