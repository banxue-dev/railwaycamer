package com.admin.railway.domain;

import java.io.Serializable;
/**
 * 调度任务
 * @author Administrator
 *
 */
public class OrderDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id; //id 
	private String trainNo; //车厢号
	
	private Long startStationId; //发站id
	private String startStationName; // 发站名称
	
	private Long endStationId; // 到站id
	private String endStationName; // 到站名称
	
	private String consignor; // 托货人
	private String consignee; // 收货人
	
	private String productName; // 品名
	private String trainType; // 车型
	private String projectNo; // 方案编号
	private String loadingLine; // 装车路线
	private String description; // 任务描述
	
	private String personIds; // 拍摄人员id集合,逗号分隔
	private String personNames; // 拍摄人员name集合,逗号分隔
	private Integer continueShot; // 续拍
	
	private String startTime; // 开始时间
	private String endTime; // 结束时间
	private Integer orderState; // 任务状态-已接收:0 已开始:1 待验收:2 已结束:3 超时:4 终止:5
	
	private Integer delState = 0; // 是否删除 0:不删除 1:删除
	private String createTime; // 创建时间
	private String createUser; // 创建用户
	private String modifyTime; // 修改时间
	private String modifyUser; // 修改用户
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTrainNo() {
		return trainNo;
	}
	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}
	public Long getStartStationId() {
		return startStationId;
	}
	public void setStartStationId(Long startStationId) {
		this.startStationId = startStationId;
	}
	public String getStartStationName() {
		return startStationName;
	}
	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}
	public Long getEndStationId() {
		return endStationId;
	}
	public void setEndStationId(Long endStationId) {
		this.endStationId = endStationId;
	}
	public String getEndStationName() {
		return endStationName;
	}
	public void setEndStationName(String endStationName) {
		this.endStationName = endStationName;
	}
	public String getConsignor() {
		return consignor;
	}
	public void setConsignor(String consignor) {
		this.consignor = consignor;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getTrainType() {
		return trainType;
	}
	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public String getLoadingLine() {
		return loadingLine;
	}
	public void setLoadingLine(String loadingLine) {
		this.loadingLine = loadingLine;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPersonIds() {
		return personIds;
	}
	public void setPersonIds(String personIds) {
		this.personIds = personIds;
	}
	public String getPersonNames() {
		return personNames;
	}
	public void setPersonNames(String personNames) {
		this.personNames = personNames;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getOrderState() {
		return orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
	public Integer getDelState() {
		return delState;
	}
	public void setDelState(Integer delState) {
		this.delState = delState;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public Integer getContinueShot() {
		return continueShot;
	}
	public void setContinueShot(Integer continueShot) {
		this.continueShot = continueShot;
	}
	
}
