package com.demo.biz;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.LeiMuDao;
import com.demo.dao.OrderStatLmDao;
import com.demo.entity.LeiMuTable;
import com.demo.entity.order.OrderStatLm;
import com.demo.utils.DateUtils;

/**
 * ��Ŀ����ͳ�����ݷ���
 *
 */
@Service
@Transactional
public class OrderStatLmBiz {

	private static int statActionflag = 0; // 1.��ʾ����ִ��ͳ������
	
	@Resource
	private OrderStatLmDao orderStatLmDao;
	@Resource
	private LeiMuDao leiMuDao;
	
	private Double totalMoney;
	private Integer orderAmount;
	private Integer jiufenAmount;
	private Double jiufenRate;
	
	/**
	 * ����������Ŀͳ������
	 */
	public void doStatistic() {
		if (statActionflag == 1) {
			return;
		}
		
		Date curDate = new Date();

		/**
		 * ͳ����ʼ����
		 * ��ʼ�Ӷ�����������һ���������ϴ����ڿ�ʼ,
		 * ֮���ͳ�Ʊ������һ������ͳ�Ƶ����ڿ�ʼ
		 */
		Date statDate = null;
		statDate = orderStatLmDao.findNewestDayStatDate();
		if (statDate == null) {
			statDate = orderStatLmDao.findEarliestOrderDate();
		}
		
		Date endDate = curDate;
		
		System.out.println("----ִ����Ŀ��������ͳ��----");
		statActionflag = 1;
		try {
			List<LeiMuTable> leimus = leiMuDao.getAllLeiMu();
			while (DateUtils.getIntervalDays(statDate, endDate) >= 0
					&& statDate.getTime() <= endDate.getTime()) {
				System.out.println("����ͳ��" + new SimpleDateFormat("yyyy-MM-dd").format(statDate)
						+ "����Ŀ��������...");
				for (LeiMuTable leimu : leimus) {
					this.updateStatData(statDate, leimu);
				}
				statDate = DateUtils.getAfterDaysDate(statDate, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			statActionflag = 0;
		}
	}
	
	/**
	 * ����һ����Ŀĳһ��(���������)�Ķ���ͳ������
	 * @param statDate
	 * @param leiMuTable
	 */
	public void updateStatData(Date statDate, LeiMuTable leiMuTable) {
		this.statByDay(statDate, leiMuTable);
		this.statByWeek(statDate, leiMuTable);
		this.statByMonth(statDate, leiMuTable);
	}
	
	/**
	 * ͳ��һ����Ŀһ��Ķ�������
	 * @param statDate
	 * @param leiMuTable
	 */
	private void statByDay(Date statDate, LeiMuTable leiMuTable) {
		Date statBeginTime = DateUtils.getDayStart(statDate);
		Date statEndTime = DateUtils.getDayEnd(statDate);
		
		// ͳ������
		this.initStatParams();
		this.setStatResult(leiMuTable, statBeginTime, statEndTime);
		
		// �������
		OrderStatLm dayStat = orderStatLmDao.findDayStat(leiMuTable.getId(), statBeginTime);
		if (dayStat == null) {
			dayStat = new OrderStatLm();
			dayStat.setStatType(1);
			dayStat.setLeimuId(leiMuTable.getId());
			dayStat.setLeimuName(leiMuTable.getLeimu());
			dayStat.setStatYear(DateUtils.getYear(statDate));
			dayStat.setStatDate(statBeginTime);
			dayStat.setStatBeginDate(statBeginTime);
			dayStat.setStatEndDate(statEndTime);
		}
		dayStat.setTotalMoney(totalMoney);
		dayStat.setOrderAmount(orderAmount);
		dayStat.setJiufenAmount(jiufenAmount);
		dayStat.setJiufenRate(jiufenRate);
		orderStatLmDao.merge(dayStat);
	}
	
	/**
	 * ͳ��һ����Ŀһ�ܵĶ�������
	 * @param statDate
	 * @param leiMuTable
	 */
	private void statByWeek(Date statDate, LeiMuTable leiMuTable) {
		Date statBeginTime = DateUtils.getDayStart(DateUtils.getFirstDayOfWeek(statDate));
		Date statEndTime = DateUtils.getDayEnd(DateUtils.getLastDayOfWeek(statDate));
		
		// ͳ������
		this.initStatParams();
		this.setStatResult(leiMuTable, statBeginTime, statEndTime);
		
		// ��������
		Integer year = DateUtils.getYear(statDate);
		Integer week = DateUtils.getWeek(statDate);
		OrderStatLm weekStat = orderStatLmDao.findWeekStat(leiMuTable.getId(), year, week);
		if (weekStat == null) {
			weekStat = new OrderStatLm();
			weekStat.setStatType(2);
			weekStat.setLeimuId(leiMuTable.getId());
			weekStat.setLeimuName(leiMuTable.getLeimu());
			weekStat.setStatYear(year);
			weekStat.setStatWeek(week);
			weekStat.setStatBeginDate(statBeginTime);
			weekStat.setStatEndDate(statEndTime);
		}
		weekStat.setTotalMoney(totalMoney);
		weekStat.setOrderAmount(orderAmount);
		weekStat.setJiufenAmount(jiufenAmount);
		weekStat.setJiufenRate(jiufenRate);
		orderStatLmDao.merge(weekStat);
	}
	
	/**
	 * ͳ��һ����Ŀһ�µĶ�������
	 * @param statDate
	 * @param leiMuTable
	 */
	private void statByMonth(Date statDate, LeiMuTable leiMuTable) {
		Date statBeginTime = DateUtils.getDayStart(DateUtils.getFirstDayOfMonth(statDate));
		Date statEndTime = DateUtils.getDayEnd(DateUtils.getLastDayOfMonth(statDate));

		// ͳ������
		this.initStatParams();
		this.setStatResult(leiMuTable, statBeginTime, statEndTime);
		
		// ��������
		Integer year = DateUtils.getYear(statDate);
		Integer month = DateUtils.getMonth(statDate);
		Integer statMonth = null;
		if (month < 10) {
			statMonth = Integer.parseInt(year.toString() + "0" + month.toString());
		} else {
			statMonth = Integer.parseInt(year.toString() + month.toString());
		}
		OrderStatLm monthStat = orderStatLmDao.findMonthStat(leiMuTable.getId(), year, statMonth);
		if (monthStat == null) {
			monthStat = new OrderStatLm();
			monthStat.setStatType(3);
			monthStat.setLeimuId(leiMuTable.getId());
			monthStat.setLeimuName(leiMuTable.getLeimu());
			monthStat.setStatYear(year);
			monthStat.setStatMonth(statMonth);
			monthStat.setStatBeginDate(statBeginTime);
			monthStat.setStatEndDate(statEndTime);
		}
		monthStat.setTotalMoney(totalMoney);
		monthStat.setOrderAmount(orderAmount);
		monthStat.setJiufenAmount(jiufenAmount);
		monthStat.setJiufenRate(jiufenRate);
		orderStatLmDao.merge(monthStat);
	}
	
	private void initStatParams() {
		totalMoney = 0d;
		orderAmount = 0;
		jiufenAmount = 0;
		jiufenRate = 0d;
	}
	
	/**
	 * ��ѯͳ�ƽ��������
	 * @param leiMuTable
	 * @param statBeginTime
	 * @param statEndTime
	 * @param totalMoney
	 * @param orderAmount
	 * @param jiufenAmount
	 * @param jiufenRate
	 */
	private void setStatResult(LeiMuTable leiMuTable, Date statBeginTime, 
			Date statEndTime) {
		orderAmount = orderStatLmDao.getOrderAmount(leiMuTable.getId(), statBeginTime, statEndTime);
		if (orderAmount == 0) {
			return;
		}
		
		totalMoney = orderStatLmDao.getTotalMoney(leiMuTable.getId(), statBeginTime, statEndTime);
		totalMoney = Double.parseDouble(new DecimalFormat("#0.00").format(totalMoney));
		jiufenAmount = orderStatLmDao.getJiufenAmount(leiMuTable.getId(), statBeginTime, statEndTime);
		jiufenRate = Double.parseDouble(jiufenAmount.toString()) /
				Double.parseDouble(orderAmount.toString()) * 100;
		jiufenRate = Double.parseDouble(new DecimalFormat("#0.00").format(jiufenRate));
	}
}
