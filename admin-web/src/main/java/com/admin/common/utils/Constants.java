package com.admin.common.utils;

/**
 * 业务常量
 * @author Administrator
 *
 */
public class Constants {
	public static final int NO = 0;
	public static final int YES = 1;
	
	//任务状态-已接收:0 已开始:1 待验收:2 已结束:3 超时:4  终止:5
	public static final int ORDER_STATE_YJS0 = 0; // 已接收
	public static final int ORDER_STATE_YKS = 1; // 已开始
	public static final int ORDER_STATE_DYS = 2; // 待验收
	public static final int ORDER_STATE_YJS3 = 3; // 已结束
	public static final int ORDER_STATE_CS = 4; // 超时
	public static final int ORDER_STATE_ZZ = 5; //终止
}