package com.admin.railway.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 * 拍照人员表
 * 
 * @author luojing
 * @email lg932740579@163.com
 * @date 2019-03-10 09:22:12
 */
@Data
public class PersonDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增长id
	private Long id;
	//登录名
	private String loginName;
	//登录密码
	private String password;
	//姓名
	private String name;
	//电话
	private String phone;
	//站点id
	private Long stationId;
	//站点名称
	private String stationName;
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

}
