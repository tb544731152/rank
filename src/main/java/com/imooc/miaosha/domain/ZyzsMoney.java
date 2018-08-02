package com.imooc.miaosha.domain;

import java.util.Date;


public class ZyzsMoney{
	private Integer id;//编号
	private String openId;//openId
	private Integer money;//钱包钱数
	private String isDelete;//是否删除  0是未删除，1是删除
	
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
	
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
}
