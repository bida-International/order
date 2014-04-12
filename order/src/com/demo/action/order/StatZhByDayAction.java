package com.demo.action.order;

import java.awt.Font;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.dao.OrderStatZhDao;
import com.demo.dao.ZhangHaoDao;
import com.demo.entity.ZhangHao;
import com.demo.entity.order.OrderStatZh;
import com.demo.utils.ChartCreator;
import com.demo.utils.DateUtils;
import com.demo.utils.Struts2Utils;

@Controller("order.statZhByDayAction")
@Scope("prototype")
public class StatZhByDayAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private OrderStatZhDao orderStatZhDao;
	@Resource
	private ZhangHaoDao zhangHaoDao;
	
	private Long zhanghaoId;
	private String statMonth;
	private Date statBeginTime;
	private Date statEndTime;

	private List<String> dates = new ArrayList<String>();
	private List<OrderStatZh> statList;
	private List<ZhangHao> zhangHaoList;
	
	private String totalMoneyChart;
	private String orderAmountChart;
	
	public String execute() {
		this.initParams();
		this.initDates();
		
		zhangHaoList = zhangHaoDao.getAllZhangHao();
		statList = orderStatZhDao.getStatList(zhanghaoId, 1, statBeginTime, statEndTime);

		if (!statList.isEmpty()) {
			this.createTotalMoneyChart();
			this.createOrderAmountChart();
		}
		
		return SUCCESS;
	}
	
	private void initParams() {
		if (statMonth == null) {
			statMonth = DateUtils.format(new Date(), "yyyyMM");
		}
		Date statDate = DateUtils.parse(statMonth + "01", "yyyyMMdd");
		statBeginTime = DateUtils.getDayStart(
				DateUtils.getFirstDayOfMonth(statDate));
		statEndTime = DateUtils.getDayEnd(
				DateUtils.getLastDayOfMonth(statDate));
	}
	
	private void initDates() {
		Date minDate = orderStatZhDao.findEarliestOrderDate();
		Integer minYear = DateUtils.getYear(minDate);
		Integer minMonth = DateUtils.getMonth(minDate);
		Integer nextMonth = minMonth;
		while (nextMonth <= 12) {
			dates.add(getDateString(minYear, nextMonth));
			nextMonth++;
		}
		
		Date maxDate = orderStatZhDao.findNewestDayStatDate();
		Integer maxYear = DateUtils.getYear(maxDate);
		Integer maxMonth = DateUtils.getMonth(maxDate);
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
	
	private void createOrderAmountChart() {
		TimeSeries series = new TimeSeries("订单数量", Day.class);
		
		for (OrderStatZh stat : statList) {
			series.add(new Day(stat.getStatBeginDate()), stat.getOrderAmount());
		}
		
		TimeSeriesCollection  dataset  = new TimeSeriesCollection();
		dataset.addSeries(series);
		
		int width = 60 + 35 * statList.size();
		int height = 250;
		String headerTitle = "订单数量统计";
		orderAmountChart = ChartCreator.createTimeSeriesChart(dataset, headerTitle, 
				new SimpleDateFormat("MM-dd"), width, height, Struts2Utils.getSession());
	}
	
	private void createTotalMoneyChart() {
		TimeSeries series = new TimeSeries("订单金额", Day.class);
		
		for (OrderStatZh stat : statList) {
			series.add(new Day(stat.getStatBeginDate()), stat.getTotalMoney());
		}
		
		TimeSeriesCollection  dataset  = new TimeSeriesCollection();
		dataset.addSeries(series);
		
		int width = 60 + 35 * statList.size();
		int height = 250;
		String headerTitle = "订单金额统计";
		totalMoneyChart = ChartCreator.createTimeSeriesChart(dataset, headerTitle, 
				new SimpleDateFormat("MM-dd"), width, height, Struts2Utils.getSession());
	}
	
	
	public String getFormatDate(Date date) {
		return DateUtils.format(date, "yyyy年MM月dd日");
	}
	
	public Long getZhanghaoId() {
		return zhanghaoId;
	}

	public void setZhanghaoId(Long zhanghaoId) {
		this.zhanghaoId = zhanghaoId;
	}

	public String getStatMonth() {
		return statMonth;
	}

	public void setStatMonth(String statMonth) {
		this.statMonth = statMonth;
	}

	public List<OrderStatZh> getStatList() {
		return statList;
	}

	public void setStatList(List<OrderStatZh> statList) {
		this.statList = statList;
	}

	public List<ZhangHao> getZhangHaoList() {
		return zhangHaoList;
	}

	public void setZhangHaoList(List<ZhangHao> zhangHaoList) {
		this.zhangHaoList = zhangHaoList;
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

	public List<String> getDates() {
		return dates;
	}

	public void setDates(List<String> dates) {
		this.dates = dates;
	}
}
