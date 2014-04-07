package com.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	/**
	 * ��ȡ����ʱ�伸��֮���ʱ��
	 * @param date
	 * @param n ȡ֮ǰ��ʹ�ø���
	 * @return
	 */
	public static Date getAfterDaysDate(Date date, int n) {
		Calendar  cal =  Calendar.getInstance();
		cal.setTime(date);
	    cal.add(Calendar.DATE, n);
	    return cal.getTime();
	}
	
	/**
	 * ȡ������������
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int getIntervalDays(Date fDate, Date oDate) {
		long intervalMilli = oDate.getTime() - fDate.getTime();
		return (int) (intervalMilli / (24 * 60 * 60 * 1000));
	}
	
	/**
	 * ��ȡһ��Ŀ�ʼʱ���
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
	 * ��ȡһ��Ľ���ʱ���
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
	 * ����date�����
	 * @param date
	 * @return
	 */
	public static Integer getYear(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * ����date��һ���еĵڼ���
	 * @param date
	 * @return
	 */
	public static Integer getWeek(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * ����date���·�
	 * @param date
	 * @return
	 */
	public static Integer getMonth(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * ����date��һ���еļ���
	 * @param date
	 * @return
	 */
	public static Integer getDay(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * ����date�����ܵĵ�һ��
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, 1); // �ϸ�������
		return getAfterDaysDate(cal.getTime(), 1);
	}
	
	/**
	 * ����date�����ܵĵ�һ��
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, 7); // ������
		return getAfterDaysDate(cal.getTime(), 1);
	}

	/**
	 * ����date�����µĵ�һ��
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
	 * ����date�����µĵ�һ��
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
