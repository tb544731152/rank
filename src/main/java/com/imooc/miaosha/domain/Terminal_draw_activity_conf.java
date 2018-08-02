package com.imooc.miaosha.domain;

import java.util.Date;


/**
 * 零售终端参加活动配置表
 * @author@zk
 *
 */
public class Terminal_draw_activity_conf {
	
	private int id;
	private Date createDate;//创建时间
	private Date startDate;//活动开始时间
	private Date endDate;//活动结束时间
	private int star;	//商铺星级
	private int jifen;   //积分
	private int fans;    //粉丝数量
	private String name;//活动的名称
	private String zhName; //活动名称（中文）
	private String isDelete = "0";// 是否删除
	private String basic;    
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public int getJifen() {
		return jifen;
	}
	public void setJifen(int jifen) {
		this.jifen = jifen;
	}
	public int getFans() {
		return fans;
	}
	public void setFans(int fans) {
		this.fans = fans;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getZhName() {
		return zhName;
	}
	public void setZhName(String zhName) {
		this.zhName = zhName;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getBasic() {
		return basic;
	}
	public void setBasic(String basic) {
		this.basic = basic;
	}
	

}
