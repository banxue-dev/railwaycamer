package com.admin.railway.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 拍照人员表
 * 
 * @author luojing
 * @email lg932740579@163.com
 * @date 2019-03-08 11:39:53
 */
public class PersonDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增长id
	private Integer id;
	//登录名
	private String loginName;
	//登录密码
	private String password;
	//姓名
	private String name;
	//电话
	private String phone;
	//站点id
	private Integer stationId;
	//站点名称
	private String stationName;
	//照片上报账号
	private String account;
	//是否删除
	private Integer delState;
	//创建时间
	private Date createTime;
	//创建用户
	private String createUser;
	//修改时间
	private Date modifyTime;
	//修改用户
	private String modifyUser;

	/**
	 * 设置：自增长id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：自增长id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：登录名
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * 获取：登录名
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * 设置：登录密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：登录密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：电话
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：站点id
	 */
	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}
	/**
	 * 获取：站点id
	 */
	public Integer getStationId() {
		return stationId;
	}
	/**
	 * 设置：站点名称
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	/**
	 * 获取：站点名称
	 */
	public String getStationName() {
		return stationName;
	}
	/**
	 * 设置：照片上报账号
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取：照片上报账号
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置：是否删除
	 */
	public void setDelState(Integer delState) {
		this.delState = delState;
	}
	/**
	 * 获取：是否删除
	 */
	public Integer getDelState() {
		return delState;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：创建用户
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取：创建用户
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * 设置：修改时间
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * 设置：修改用户
	 */
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	/**
	 * 获取：修改用户
	 */
	public String getModifyUser() {
		return modifyUser;
	}
}
