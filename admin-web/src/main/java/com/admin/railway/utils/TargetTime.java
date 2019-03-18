package com.admin.railway.utils;

/**
作者：fengchase
时间：2019年3月13日
文件：TargetTime.java
项目：admin-web
*/
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 获取最近N天的时间，从昨天开始算起
 * 
 * @author baiyu
 *
 */
public class TargetTime {
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static Calendar calendar = Calendar.getInstance();
	public static void main(String[] args) {
		Date firstDay = getBeforeOrAfterDate(new Date(), -7);
		Date lastDay = getBeforeOrAfterDate(new Date(), 0);
	}

	/**
	 * 根据当前时间，添加或减去指定的时间量。例如，要从当前日历时间减去 5 天，可以通过调用以下方法做到这一点：
	 * add(Calendar.DAY_OF_MONTH, -5)。
	 * 
	 * @param date
	 *            指定时间
	 * @param num
	 *            为时间添加或减去的时间天数
	 * @return
	 */
	public static Date getBeforeOrAfterDate(Date date, int num) {
		
		// 获取日历
		calendar.setTime(date);
		// 当date的值是当前时间，则可以不用写这段代码。
		calendar.add(Calendar.DATE, num);
		Date d = calendar.getTime();
		// 把日历转换为Date
		return d;
	}
	public static String getBeforeOrAfterDateReString(Date date, int num) {
		
		// 获取日历
		calendar.setTime(date);
		// 当date的值是当前时间，则可以不用写这段代码。
		calendar.add(Calendar.DATE, num);
		Date d = calendar.getTime();
		// 把日历转换为Date
		return sdf.format(d);
	}
}
