package com.imooc.miaosha.domain;

import java.util.Date;

/**
 * 零售终端抽奖记录
 * @author@ooyanjing.com
 *
 */
public class Terminal_draw_record {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String zyzsId;//唯一标识
	private Date createDate;//中奖时间
	private Integer activityId;//活动Id
	private String address;//店铺名称
	private int productId;//中奖产品Id
	private String productName;//中奖产品名称
	private String status;//领取状态 0 未领取，1，已经领取 2 有有效期的优惠券 3审核中
	private Date startDate;//有效期-开始时间
	private Date endDate;//有效期-结束时间
	private Integer num;//数量
	private String type;//0标识实物1标识虚拟产品 2大额红包 3奖品（钱）
	private String isDelete = "0";//是否删除
	private String orderId;//订单Id
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getZyzsId() {
		return zyzsId;
	}
	public void setZyzsId(String zyzsId) {
		this.zyzsId = zyzsId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
}
