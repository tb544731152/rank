package com.imooc.miaosha.domain;

import java.util.Date;


public class ZyzsMoneyRecord{
	private Integer id;//编号
	private String openId;//openId
	private String zsNum;//追溯码，提现的记录
	private Integer money;//钱数
	private Date createDate;//创建时间
	private String type;//类型 0 标识收入，1 标识支出
	private String orderId;//微信提现的订单ID
	private String status;//如果是微信提现，0 标识提现成功，1.标识 提现失败（24 小时后再次提现），
	//2.标识扫码收益,3.烟酒币消耗,4.老虎机抽奖消耗,5.老虎机抽奖得到,6.买世界杯竞猜卡消耗,7.世界杯获得奖励
	private String isView;//是否查看
	private String isDelete;//是否删除
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public String getZsNum() {
		return zsNum;
	}
	public void setZsNum(String zsNum) {
		this.zsNum = zsNum;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsView() {
		return isView;
	}
	public void setIsView(String isView) {
		this.isView = isView;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
	
	
}
