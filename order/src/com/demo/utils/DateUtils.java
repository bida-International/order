package com.demo.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	/**
	 * 获取给定时间几天之后的时间
	 * @param date
	 * @param n 取之前的使用负数
	 * @return
	 */
	public static Date getAfterDaysDate(Date date, int n) {
		Calendar  cal =  Calendar.getInstance();
		cal.setTime(date);
	    cal.add(Calendar.DATE, n);
	    return cal.getTime();
	}
	
	/**
	 * 取两个日期相差几天
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int getIntervalDays(Date fDate, Date oDate) {
		long intervalMilli = oDate.getTime() - fDate.getTime();
		return (int) (intervalMilli / (24 * 60 * 60 * 1000));
	}
	
}
