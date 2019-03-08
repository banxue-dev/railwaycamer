package com.admin.railway.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 照片表
 * 
 * @author luojing
 * @email lg932740579@163.com
 * @date 2019-03-08 11:39:53
 */
public class PictureDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增长id
	private Integer id;
	//任务id
	private Integer orderId;
	//略缩图路径地址
	private String thumUrl;
	//原图地址
	private String url;
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
	 * 设置：任务id
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：任务id
	 */
	public Integer getOrderId() {
		return orderId;
	}
	/**
	 * 设置：略缩图路径地址
	 */
	public void setThumUrl(String thumUrl) {
		this.thumUrl = thumUrl;
	}
	/**
	 * 获取：略缩图路径地址
	 */
	public String getThumUrl() {
		return thumUrl;
	}
	/**
	 * 设置：原图地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：原图地址
	 */
	public String getUrl() {
		return url;
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
