package com.imooc.miaosha.domain;

import java.util.Date;


/**
 * 零售户抽奖产品配置表
 * @author@ooyanjing.com
 *
 */
public class Terminal_draw_activ_product {
	private static final long serialVersionUID = 1L;
	private int id;//主键
	private String productName;//产品名称
	/**产品数量**/
	private Integer num;
	/**次数**/
	private String chance;
	/**概率**/
	private String gailv;
	/**图片地址**/
	private String imgUrl;
	/**活动的ID 零售户活动表关联**/
	private int activityId;
	private Date createDate;//创建时间
	/**是否删除 **/
	private String isDelete = "0";
	private int angle; //转动的角度
	/**
	 * 产品有效期
	 */
	private Date startDate;
	private Date endDate;
	private Integer awardNum;//每次中多少
	private String type;//产品的类型 0标识实物，1标识 虚拟产品 2大额奖金 3奖品（钱）
	private Date hitDate; //手动修改允许中奖的时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getChance() {
		return chance;
	}
	public void setChance(String chance) {
		this.chance = chance;
	}
	public String getGailv() {
		return gailv;
	}
	public void setGailv(String gailv) {
		this.gailv = gailv;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public int getAngle() {
		return angle;
	}
	public void setAngle(int angle) {
		this.angle = angle;
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
	public Integer getAwardNum() {
		return awardNum;
	}
	public void setAwardNum(Integer awardNum) {
		this.awardNum = awardNum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getHitDate() {
		return hitDate;
	}
	public void setHitDate(Date hitDate) {
		this.hitDate = hitDate;
	}
}
