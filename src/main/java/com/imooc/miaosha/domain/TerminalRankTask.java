package com.imooc.miaosha.domain;

import java.util.Date;

/**
 * 零售户排名活动表
 * @author MR.Chen
 *
 */
public class TerminalRankTask {

	private String id;			//主键
	private String taskNum;		//任务期数
	private String prizeName;	//奖品名称
	private Date createDate;	//创建日期
	private int start;		//开始名次
	private int end;	//结束名次	假如只配单名次，开始结束名次写成一样	
	private String isDelete;	//是否删除 		0 未删除，1	已删除
	private String type;		//奖励类型0虚拟，1实物
	private String count;		//奖品数量
	private String imgUrl; //奖品图片
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getTaskNum() {
		return taskNum;
	}
	public void setTaskNum(String taskNum) {
		this.taskNum = taskNum;
	}
	
	
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	
	
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	
	
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
	
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
	
}
