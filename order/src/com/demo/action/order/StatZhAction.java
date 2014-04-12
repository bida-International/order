package com.demo.action.order;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.biz.OrderStatZhBiz;
import com.demo.dao.OrderStatZhDao;
import com.demo.dao.ZhangHaoDao;
import com.demo.entity.ZhangHao;
import com.demo.entity.order.OrderStatZh;
import com.demo.utils.ChartCreator;
import com.demo.utils.DateUtils;
import com.demo.utils.Struts2Utils;

@Controller("order.statZhAction")
@Scope("prototype")
public class StatZhAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private OrderStatZhDao orderStatZhDao;
	@Resource
	private ZhangHaoDao zhangHaoDao;
	@Resource
	private OrderStatZhBiz orderStatZhBiz;
	
	private List<OrderStatZh> orderStatZhs;
	private List<String> dates = new ArrayList<String>();
	private String beginDate;
	private String endDate;
	private String totalMoneyChart;
	private String orderAmountChart;
	private String jiufenRateChart;
	
	public String execute() {
		initDates();
		
		@SuppressWarnings("rawtypes")
		List statObjs = orderStatZhDao.getGroupListByZhangHao(
				Integer.parseInt(beginDate), 
				Integer.parseInt(endDate));
		if (statObjs != null && !statObjs.isEmpty()) {
			orderStatZhs = new ArrayList<OrderStatZh>();
		}
		
		Integer sumOrderAmount = 0; // 合计订单数
		Integer sumJiufenAmount = 0; // 合计纠纷数
		Double sumTotalMoney = 0.00d; // 合计金额
		for (int i = 0; i < statObjs.size(); i++) {
			Object[] objArray = (Object[]) statObjs.get(i);
			Integer orderAmount = ((Long)objArray[0]).intValue();
			Double totalMoney = (Double) objArray[1];
			Integer jiufenAmount = ((Long) objArray[2]).intValue();
			// 合计
			sumOrderAmount += orderAmount;
			sumJiufenAmount += jiufenAmount;
			sumTotalMoney += totalMoney;
			
			OrderStatZh stat = new OrderStatZh();
			stat.setOrderAmount(orderAmount);
			stat.setTotalMoney(totalMoney);
			stat.setTotalMoney(Double.parseDouble(new DecimalFormat("#0.00").format(stat.getTotalMoney())));
			stat.setJiufenAmount(jiufenAmount);
			stat.setZhanghaoId((Long) objArray[3]);
			stat.setZhanghaoAccount((String) objArray[4]);
			Double jiufenRate = Double.parseDouble(stat.getJiufenAmount().toString()) /
					Double.parseDouble(stat.getOrderAmount().toString()) * 100;
			jiufenRate = Double.parseDouble(new DecimalFormat("#0.00").format(jiufenRate));
			stat.setJiufenRate(jiufenRate);
			orderStatZhs.add(stat);
		}
		
		if (!orderStatZhs.isEmpty()) {
			this.createTotalMoneyChart();
			this.createOrderAmountChart();
			this.createJiufenRateChart();
			
			// 合计
			OrderStatZh stat = new OrderStatZh();
			stat.setOrderAmount(sumOrderAmount);
			stat.setTotalMoney(sumTotalMoney);
			stat.setTotalMoney(Double.parseDouble(new DecimalFormat("#0.00").format(stat.getTotalMoney())));
			stat.setJiufenAmount(sumJiufenAmount);
			stat.setZhanghaoId(0l);
			Double jiufenRate = Double.parseDouble(stat.getJiufenAmount().toString()) /
					Double.parseDouble(stat.getOrderAmount().toString()) * 100;
			jiufenRate = Double.parseDouble(new DecimalFormat("#0.00").format(jiufenRate));
			stat.setJiufenRate(jiufenRate);
			stat.setZhanghaoAccount("合计");
			orderStatZhs.add(stat);
		}
		return SUCCESS;
	}
	
	private void initDates() {
		Date minDate = orderStatZhDao.findEarliestOrderDate();
		Integer minYear = DateUtils.getYear(minDate);
		Integer minMonth = DateUtils.getMonth(minDate);
		if (beginDate == null) {
			beginDate = getDateString(minYear, minMonth);
		}
		Integer nextMonth = minMonth;
		while (nextMonth <= 12) {
			dates.add(getDateString(minYear, nextMonth));
			nextMonth++;
		}
		
		Date maxDate = orderStatZhDao.findNewestDayStatDate();
		Integer maxYear = DateUtils.getYear(maxDate);
		Integer maxMonth = DateUtils.getMonth(maxDate);
		if (endDate == null) {
			endDate = getDateString(maxYear, maxMonth);
		}
		Integer nextYear = minYear + 1;
		while (nextYear <= maxYear) {
			Integer lastMonth = 12;
			if (nextYear.equals(maxYear)) {
				lastMonth = maxMonth;
			}
			for (int month = 1; month <= lastMonth; month++) {
				dates.add(getDateString(nextYear, month));
			}
			nextYear++;
		}
	}
	
	private String getDateString(Integer year, Integer month) {
		if (month < 10) {
			return year.toString() + "0" + month.toString();
		} else {
			return year.toString() + month.toString();
		}
	}
	
	// 生成订单数据统计图表
	private void createOrderAmountChart() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (OrderStatZh orderStatZh : orderStatZhs) {
			dataset.setValue(orderStatZh.getOrderAmount(), "总数", orderStatZh.getZhanghaoAccount());
		}
		// 设置标题文字
		String headerTitle = "订单数量统计";
		int width = 350;
		int height = 60 + 25 * orderStatZhs.size(); 
		orderAmountChart = ChartCreator.createBarChart(dataset, headerTitle, PlotOrientation.HORIZONTAL, 
				width, height, false, Struts2Utils.getSession());
	}
	
	// 生成订单金额统计图表
	private void createTotalMoneyChart() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (OrderStatZh orderStatZh : orderStatZhs) {
			dataset.setValue(orderStatZh.getTotalMoney(), "总金额", orderStatZh.getZhanghaoAccount());
		}
		
		// 设置标题文字
		String headerTitle = "订单总金额统计";
		int width = 350;
		int height = 60 + 25 * orderStatZhs.size(); 
		totalMoneyChart = ChartCreator.createBarChart(dataset, headerTitle, PlotOrientation.HORIZONTAL, 
				width, height, false, Struts2Utils.getSession());
	}

	// 生成订单纠纷率统计图表
	private void createJiufenRateChart() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (OrderStatZh orderStatZh : orderStatZhs) {
			dataset.setValue(orderStatZh.getJiufenRate() / 100, "纠纷率", orderStatZh.getZhanghaoAccount());
		}

		// 设置标题文字
		String headerTitle = "订单纠纷率统计";
		int width = 350;
		int height = 60 + 25 * orderStatZhs.size(); 
		jiufenRateChart = ChartCreator.createBarChart(dataset, headerTitle, PlotOrientation.HORIZONTAL, 
				width, height, true, Struts2Utils.getSession());
	}
	
	public String doStatistic() {
		Date curDate = new Date();

		/**
		 * 统计起始日期
		 * 初始从订单表里最早一条订单的上传日期开始,
		 * 之后从统计表的最新一条数据统计的日期开始
		 */
		Date statDate = null;
		statDate = orderStatZhDao.findNewestDayStatDate();
		if (statDate == null) {
			statDate = orderStatZhDao.findEarliestOrderDate();
		}
		
		Date endDate = curDate;
		
		System.out.println("----执行订单数据统计----");
		try {
			List<ZhangHao> zhangHaos = zhangHaoDao.getAllZhangHao();
			while (DateUtils.getIntervalDays(statDate, endDate) >= 0
					&& statDate.getTime() <= endDate.getTime()) {
				System.out.println("正在统计" + new SimpleDateFormat("yyyy-MM-dd").format(statDate)
						+ "的订单数据...");
				for (ZhangHao zhangHao : zhangHaos) {
					orderStatZhBiz.updateStatData(statDate, zhangHao);
				}
				statDate = DateUtils.getAfterDaysDate(statDate, 1);
			}
			Struts2Utils.renderJson("数据统计完成", true);
		} catch (Exception e) {
			e.printStackTrace();
			Struts2Utils.renderJson("发生未知错误", false);
		}
		
		return null;
	}
	
	public List<OrderStatZh> getOrderStatZhs() {
		return orderStatZhs;
	}

	public void setOrderStatZhs(List<OrderStatZh> orderStatZhs) {
		this.orderStatZhs = orderStatZhs;
	}

	public List<String> getDates() {
		return dates;
	}

	public void setDates(List<String> dates) {
		this.dates = dates;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTotalMoneyChart() {
		return totalMoneyChart;
	}

	public void setTotalMoneyChart(String totalMoneyChart) {
		this.totalMoneyChart = totalMoneyChart;
	}

	public String getOrderAmountChart() {
		return orderAmountChart;
	}

	public void setOrderAmountChart(String orderAmountChart) {
		this.orderAmountChart = orderAmountChart;
	}

	public String getJiufenRateChart() {
		return jiufenRateChart;
	}

	public void setJiufenRateChart(String jiufenRateChart) {
		this.jiufenRateChart = jiufenRateChart;
	}
}
