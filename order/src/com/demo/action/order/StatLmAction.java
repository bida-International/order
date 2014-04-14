package com.demo.action.order;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.biz.OrderStatLmBiz;
import com.demo.dao.OrderStatLmDao;
import com.demo.entity.order.OrderStatLm;
import com.demo.utils.ChartCreator;
import com.demo.utils.DateUtils;
import com.demo.utils.Struts2Utils;

@Controller("order.statLmAction")
@Scope("prototype")
public class StatLmAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private OrderStatLmDao orderStatLmDao;
	@Resource
	private OrderStatLmBiz orderStatLmBiz;
	
	private List<OrderStatLm> orderStatLms;
	private List<String> dates = new ArrayList<String>();
	private String beginDate;
	private String endDate;
	private String totalMoneyChart;
	private String orderAmountChart;
	private String jiufenRateChart;
	
	public String execute() {
		initDates();
		
		@SuppressWarnings("rawtypes")
		List statObjs = orderStatLmDao.getGroupListByLeimu(
				Integer.parseInt(beginDate), 
				Integer.parseInt(endDate));
		if (statObjs != null && !statObjs.isEmpty()) {
			orderStatLms = new ArrayList<OrderStatLm>();
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
			
			OrderStatLm stat = new OrderStatLm();
			stat.setOrderAmount(orderAmount);
			stat.setTotalMoney(totalMoney);
			stat.setTotalMoney(Double.parseDouble(new DecimalFormat("#0.00").format(stat.getTotalMoney())));
			stat.setJiufenAmount(jiufenAmount);
			stat.setLeimuId((Long) objArray[3]);
			stat.setLeimuName((String) objArray[4]);
			
			Double jiufenRate = 0.0d;
			if (stat.getOrderAmount() > 0) {
				jiufenRate = Double.parseDouble(stat.getJiufenAmount().toString()) /
						Double.parseDouble(stat.getOrderAmount().toString()) * 100;
			}
			jiufenRate = Double.parseDouble(new DecimalFormat("#0.00").format(jiufenRate));
			stat.setJiufenRate(jiufenRate);
			orderStatLms.add(stat);
		}
		
		if (!orderStatLms.isEmpty()) {
			this.createTotalMoneyChart();
			this.createOrderAmountChart();
//			this.createJiufenRateChart();
			
			// 合计
			OrderStatLm stat = new OrderStatLm();
			stat.setOrderAmount(sumOrderAmount);
			stat.setTotalMoney(sumTotalMoney);
			stat.setTotalMoney(Double.parseDouble(new DecimalFormat("#0.00").format(stat.getTotalMoney())));
			stat.setJiufenAmount(sumJiufenAmount);
			stat.setLeimuId(0l);
			Double jiufenRate = 0.0d;
			if (stat.getOrderAmount() > 0) {
				jiufenRate = Double.parseDouble(stat.getJiufenAmount().toString()) /
						Double.parseDouble(stat.getOrderAmount().toString()) * 100;
			}
			jiufenRate = Double.parseDouble(new DecimalFormat("#0.00").format(jiufenRate));
			stat.setJiufenRate(jiufenRate);
			stat.setLeimuName("合计");
			orderStatLms.add(stat);
		}
		return SUCCESS;
	}
	
	private void initDates() {
		Date minDate = orderStatLmDao.findEarliestOrderDate();
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
		
		Date maxDate = orderStatLmDao.findNewestDayStatDate();
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
		for (OrderStatLm orderStatLm : orderStatLms) {
			dataset.setValue(orderStatLm.getOrderAmount(), "总数", getLeiMuEnName(orderStatLm.getLeimuName()));
		}
		// 设置标题文字
		String headerTitle = "订单数量统计";
		int width = 520;
		int height = 60 + 25 * orderStatLms.size(); 
		orderAmountChart = ChartCreator.createBarChart(dataset, headerTitle, PlotOrientation.HORIZONTAL, 
				width, height, false, Struts2Utils.getSession());
	}
	
	// 生成订单金额统计图表
	private void createTotalMoneyChart() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (OrderStatLm orderStatLm : orderStatLms) {
			dataset.setValue(orderStatLm.getTotalMoney(), "总金额", getLeiMuEnName(orderStatLm.getLeimuName()));
		}
		
		// 设置标题文字
		String headerTitle = "订单总金额统计";
		int width = 530;
		int height = 60 + 25 * orderStatLms.size(); 
		totalMoneyChart = ChartCreator.createBarChart(dataset, headerTitle, PlotOrientation.HORIZONTAL, 
				width, height, false, Struts2Utils.getSession());
	}

	// 生成订单纠纷率统计图表
	private void createJiufenRateChart() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (OrderStatLm orderStatLm : orderStatLms) {
			dataset.setValue(orderStatLm.getJiufenRate() / 100, "纠纷率", getLeiMuEnName(orderStatLm.getLeimuName()));
		}

		// 设置标题文字
		String headerTitle = "订单纠纷率统计";
		int width = 530;
		int height = 60 + 25 * orderStatLms.size(); 
		jiufenRateChart = ChartCreator.createBarChart(dataset, headerTitle, PlotOrientation.HORIZONTAL, 
				width, height, true, Struts2Utils.getSession());
	}
	
	private String getLeiMuEnName(String leimuName) {
		if (leimuName.contains("(")) {
			return leimuName.substring(leimuName.indexOf("(") + 1, leimuName.length() - 1);
		}
		return leimuName;
	}
	
	public String doStatistic() {
		orderStatLmBiz.doStatistic();
		Struts2Utils.renderJson("数据统计请求发送成功", true);
		
		return null;
	}
	
	public List<OrderStatLm> getOrderStatLms() {
		return orderStatLms;
	}

	public void setOrderStatLms(List<OrderStatLm> orderStatLms) {
		this.orderStatLms = orderStatLms;
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
