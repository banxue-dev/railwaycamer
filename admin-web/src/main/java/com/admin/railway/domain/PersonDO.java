package com.admin.railway.domain;

import java.io.Serializable;
/**
 * 拍照人员
 * @author Administrator
 *
 */
public class PersonDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id; //id 
	private String name; //姓名
	private String phone; // 电话
	private Long stationId; // 站点id
	private String stationName; // 站点名称
	private String account; // 照片上报账号
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getStationId() {
		return stationId;
	}
	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
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
	
}
