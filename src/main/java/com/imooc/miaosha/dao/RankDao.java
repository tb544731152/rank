package com.imooc.miaosha.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imooc.miaosha.domain.TerminalRankTask;
import com.imooc.miaosha.domain.TerminalRankWinningList;
import com.imooc.miaosha.domain.TerminalTaskLimit;
import com.imooc.miaosha.domain.Terminal_draw_record;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.domain.ZyzsMoneyRecord;

@Mapper
public interface RankDao {
	/**
	 * 查询任务
	 * @param id
	 * @return
	 */
	
	@Select("select * from zyzs_terminal_task_limit where id = #{id}")
	public TerminalTaskLimit sdgetById(@Param("id")String id);
	
	/**
	 * 查询生成记录
	 * @param limitId
	 * @param zyzsId
	 * @return
	 */
	@Select("select * from zyzs_terminal_rank_winninglist where isDelete=#{isDelete} and limitId = #{limitId} and zyzsId = #{zyzsId}")
	public TerminalRankWinningList sdselecRankRecord(@Param("isDelete")String isDelete,@Param("limitId")String limitId,@Param("zyzsId")String zyzsId);
	/**
	 * 查询奖励
	 * @param limitId
	 * @return
	 */
	@Select("select * from zyzs_terminal_rank_task where taskNum = #{limitId}")
	public List<TerminalRankTask> sdselecRankTask(@Param("limitId")String limitId);
	/**
	 * 查询粉丝信息
	 * @param zyzsId
	 * @return
	 */
	@Select("select openId from oo_fans where zyzsId = #{zyzsId}")
	public List<String> getOpenId(@Param("zyzsId")String zyzsId);
	
	/**
	 * 查询粉丝信息
	 * @param openId
	 * @return
	 */
	@Select("select zyzsId from oo_fans where openId = #{openId}")
	public List<String> findZyzsId(@Param("openId")String openId);
	
	/**
	 * 查询Token
	 * @param zyzsId
	 * @return
	 */
	@Select("select accessToken from oo_public_num where appId = #{appId} ")
	public String getAccessToken(@Param("appId")String appId);
	/**
	 * 生成中奖名单
	 * @param rank
	 * @return
	 */
	@Insert("insert into zyzs_terminal_rank_winninglist"
			+ "(`id`, `createDate`, `isDelete`, `limitDate`, `limitId`, `limitName`, `num`, `openId`, `prizeName`, `saoCount`, `status`, `taskId`, `zyzsId`, `type`, `count`) "
			+ "VALUES (#{id}, #{createDate},#{isDelete},#{limitDate},#{limitId},#{limitName},#{num},#{openId},#{prizeName},#{saoCount},#{status},#{taskId},#{zyzsId},#{type},#{count})")
	public int sdinsertRankRecord(TerminalRankWinningList rank);
	
	/**
	 * 获取zyzzId
	 * @return
	 */
	@Select("select zyzsId from oo_fans")
	public List<String> getZyzsId();

	/**
	 * 保存中奖记录
	 * @param rank
	 */
	@Insert("INSERT INTO `zyzs_terminal_task_use_record`"
			+ "(`id`,`count`,`createDate`,`isDelete`,`name`,`openId`,`taskId`,`zsNum`,`zyzsId`,`taskNum`,`prizeName`)VALUES"
			+ "(UUID(),#{count},NOW(),'0',#{limitName},#{openId},"
			+ "#{taskId},'0',#{zyzsId},#{limitId},#{prizeName});")
	public void sdsaveRecord(TerminalRankWinningList rank);
	/**
	 * 保存中奖记录
	 * @param record
	 * @return
	 */
	@Insert("INSERT INTO `terminal_draw_record` "
			+ "(`id`, `activityId`,`createDate`, `endDate`, `isDelete`, `num`, `orderId`, `productId`, `productName`, `startDate`, `status`, `type`, `zyzsId`) "
			+ "VALUES "
			+ "(#{id}, #{activityId}, #{createDate}, '2099-09-08 09:00:00', '0', '1', NULL,#{productId},#{productName} ,now(), #{status}, #{type},#{zyzsId})")
	public int saveAwardRecord(Terminal_draw_record record);
	
	//查询活动Id
	@Select("select activityId from terminal_draw_activ_product where id = #{id} ")
	public int selectActivityById(@Param("id")int id);
	
	//保存钱包记录
	@Insert("INSERT INTO `zyzs_money_record` (`orderId`, `createDate`, `isDelete`, `isView`, `money`, `openId`, `status`, `type`, `zsNum`, `id`) "
			+ "VALUES "
			+ "(#{orderId},#{createDate}, '0', '0',#{money}, #{openId}, #{status}, #{type},#{zsNum}, NULL)")
	public int saveMoneyRecord(ZyzsMoneyRecord moneyRecord);
	
	//更新钱包记录
	@Update("UPDATE zyzs_money set money=money+#{money} where openId=#{openId}")
	public boolean updateMyMoney(@Param("money")int money,@Param("openId")String openId);
	
}
