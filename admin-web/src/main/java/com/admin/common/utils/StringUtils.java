package com.admin.common.utils;

/**
 * @author Admin
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{
	/**
	 * 是否是空已经空字符串
	 * 
	 * @param str
	 * @return 2018年8月1日 作者：fengchase
	 */
	public static boolean isNullString(String str) {
		if (str == null || str == "" || "".equals(str)) {
			return true;
		}
		return false;
	}
	/**
	 * 校验string列表
	 * 
	 * @param str
	 * @return 2018年8月1日 作者：fengchase
	 */
	public static boolean isNullString(String... strs) {
		for (String str : strs) {
			if(isNullString(str)) {
				return true;
			}
		}
		return false;
	}
}
