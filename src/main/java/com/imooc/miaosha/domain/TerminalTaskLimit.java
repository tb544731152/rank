package com.imooc.miaosha.domain;

import java.util.Date;



/**
 * 零售户活动期数配置表
 * 
 *
 */
public class TerminalTaskLimit {

	private String id;			//主键
	private Date createDate;	//创建日期
	private int limitCount;			//任务总限制次数
	private String isDelete;	//是否删除0 未删除  1 删除
	private String descri;	//描述
	private Date startDate;     //开始时间
	private Date endDate;       //结束时间
	private Date onlineDate;     //上线时间
	private Date downDate;       //下线时间
	private String zsNum;		 //商品排号
	private String name;		 //期活动名称
	private String appId;
	private String type;		//活动类型 0排行  1兑换
	private String status;		//活动状态 0未生成  1已生成
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	public int getLimitCount() {
		return limitCount;
	}
	public void setLimitCount(int limitCount) {
		this.limitCount = limitCount;
	}
	
	
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
	
	public String getDescri() {
		return descri;
	}
	public void setDescri(String descri) {
		this.descri = descri;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getOnlineDate() {
		return onlineDate;
	}
	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}
	public Date getDownDate() {
		return downDate;
	}
	public void setDownDate(Date downDate) {
		this.downDate = downDate;
	}
	
	
	public String getZsNum() {
		return zsNum;
	}
	public void setZsNum(String zsNum) {
		this.zsNum = zsNum;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
