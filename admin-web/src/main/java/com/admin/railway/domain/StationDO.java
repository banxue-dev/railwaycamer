package com.admin.railway.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 * 站点表
 * 
 * @author luojing
 * @email lg932740579@163.com
 * @date 2019-03-10 09:22:12
 */
@Data
public class StationDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增长id
	private Long id;
	//站点名称
	private String name;
	//父级id, 顶级parent_id:0
	private Long parentId;
	//是否删除
	private Integer delState;
	//是否底层节点0不是，1是
	private Integer isBottom;
	//创建时间
	private Date createTime;
	//创建用户
	private String createUser;
	//修改时间
	private Date modifyTime;
	//修改用户
	private String modifyUser;
	private String staPys;
	
	//1:发站站点，2:到站站点
	private Integer type;
	//站点CODE唯一
	private String stationCode;

	public final static String PARENT_ID = "parentId";

	public final static String DEL_STATE = "delState";

	public final static String MODIFY_TIME = "modifyTime";

	public final static String MODIFY_USER = "modifyUser";

}
