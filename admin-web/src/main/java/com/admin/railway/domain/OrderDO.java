package com.admin.railway.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 * 任务表
 * 
 * @author luojing
 * @email lg932740579@163.com
 * @date 2019-03-10 09:22:12
 */
@Data
public class OrderDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增长id
	private Long id;
	//车厢号
	private String trainNo;
	//发站id
	private Long startStationId;
	//发站名称
	private String startStationName;
	//到站id
	private Long endStationId;
	//到站名称
	private String endStationName;
	//托货人
	private String consignor;
	//收货人
	private String consignee;
	//品名
	private String productName;
	//车型
	private String trainType;
	//方案编号
	private String projectNo;
	//装车路线
	private String loadingLine;
	//任务描述
	private String description;
	//拍摄人员id集合,逗号分隔
	private String personIds;
	//拍摄人员name集合,逗号分隔
	private String personNames;
	//为0时不是续拍，不为0时为续拍，存放上次任务ID
	private Long continueShot;
	//开始时间
	private Date startTime;
	//结束时间
	private Date endTime;
	//任务状态:0已拍照，1未拍照，
	private Integer orderState;
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

	public final static String DEL_STATE = "delState";

	public final static String ID = "id";
}
