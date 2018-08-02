package com.imooc.miaosha.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * 零售排行中奖名单表
 * @author MR.Chen
 *
 */
public class TerminalRankWinningList  implements Serializable{
	private static final long serialVersionUID = 8146936570947548961L;
	private String id;			//主键
	private int num;		//排名
	private int saoCount;	//扫条数
	private Date createDate;	//创建日期
	private String limitId;		//排名期数ID
	private String limitName;	//活动名称
	private String limitDate;	//活动时间：开始时间-结束时间
	private String isDelete;	//是否删除 		0 未删除，1	已删除
	private String taskId;		//奖励Id（rankTask表Id）
	private String prizeName;   //奖品名称
	private String status;		//状态0待发奖，1已发奖
	private String openId;		
	private String zyzsId;	
	private String type; // 0 红包 1 实物 2 烟酒币  
	private int count; // 数量 钱或者烟酒币或者实物
	
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	
	public int getSaoCount() {
		return saoCount;
	}
	public void setSaoCount(int saoCount) {
		this.saoCount = saoCount;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	public String getLimitId() {
		return limitId;
	}
	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}
	
	
	public String getLimitName() {
		return limitName;
	}
	public void setLimitName(String limitName) {
		this.limitName = limitName;
	}
	
	
	public String getLimitDate() {
		return limitDate;
	}
	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate;
	}
	
	
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getZyzsId() {
		return zyzsId;
	}
	public void setZyzsId(String zyzsId) {
		this.zyzsId = zyzsId;
	}
	
	
}
