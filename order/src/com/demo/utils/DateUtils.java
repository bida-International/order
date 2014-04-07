package com.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	/**
	 * 获取一天的开始时间点
	 * @param date
	 * @return
	 */
	public static Date getDayStart(Date date) {
		String strDate = new SimpleDateFormat("yyyy-MM-dd").format(date) + " 00:00:00";
		try {
			return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取一天的结束时间点
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		String strDate = new SimpleDateFormat("yyyy-MM-dd").format(date) + " 23:59:59";
		try {
			return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 计算date的年份
	 * @param date
	 * @return
	 */
	public static Integer getYear(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * 计算date是一年中的第几周
	 * @param date
	 * @return
	 */
	public static Integer getWeek(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * 计算date的月份
	 * @param date
	 * @return
	 */
	public static Integer getMonth(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 计算date是一月中的几号
	 * @param date
	 * @return
	 */
	public static Integer getDay(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 计算date所在周的第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, 1); // 上个星期日
		return getAfterDaysDate(cal.getTime(), 1);
	}
	
	/**
	 * 计算date所在周的第一天
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, 7); // 星期六
		return getAfterDaysDate(cal.getTime(), 1);
	}

	/**
	 * 计算date所在月的第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	
	/**
	 * 计算date所在月的第一天
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}
}
