package com.admin.railway.domain;

import java.io.Serializable;
/**
 * 站点
 * @author Administrator
 *
 */
public class StationDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id; //id 
	private String name; //站点名称
	private Long parentId; // 父级id, 顶级parent_id:0
	
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
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
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
