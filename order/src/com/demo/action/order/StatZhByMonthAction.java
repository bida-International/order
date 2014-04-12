package com.demo.action.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
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


@Controller("order.statZhByMonthAction")
@Scope("prototype")
public class StatZhByMonthAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	@Resource
	private OrderStatZhDao orderStatZhDao;
	@Resource
	private ZhangHaoDao zhangHaoDao;
	
	private Long zhanghaoId;
	private String statYear;
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
		statList = orderStatZhDao.getStatList(zhanghaoId, 3, statBeginTime, statEndTime);

		if (!statList.isEmpty()) {
			this.createTotalMoneyChart();
			this.createOrderAmountChart();
		}
		
		return SUCCESS;
	}
	
	private void initParams() {
		if (statYear == null) {
			statYear = DateUtils.format(new Date(), "yyyy");
		}
		statBeginTime = DateUtils.getDayStart(
				DateUtils.getFirstDayOfMonth(DateUtils.parse(statYear + "0101", "yyyyMMdd")));
		statEndTime = DateUtils.getDayEnd(
				DateUtils.getLastDayOfMonth(DateUtils.parse(statYear + "1231", "yyyyMMdd")));
	}
	
	private void initDates() {
		Date minDate = orderStatZhDao.findEarliestOrderDate();
		Integer minYear = DateUtils.getYear(minDate);
		
		Date maxDate = orderStatZhDao.findNewestDayStatDate();
		Integer maxYear = DateUtils.getYear(maxDate);
		Integer nextYear = minYear;
		while (nextYear <= maxYear) {
			dates.add(nextYear.toString());
			nextYear++;
		}
	}
	
	private void createOrderAmountChart() {
		TimeSeries series = new TimeSeries("订单数量", Month.class);
		
		for (OrderStatZh stat : statList) {
			series.add(new Month(stat.getStatBeginDate()), stat.getOrderAmount());
		}
		
		TimeSeriesCollection  dataset  = new TimeSeriesCollection();
		dataset.addSeries(series);
		
		int width = 60 + 35 * statList.size();
		int height = 250;
		String headerTitle = "订单数量统计";
		orderAmountChart = ChartCreator.createTimeSeriesChart(dataset, headerTitle, 
				new SimpleDateFormat("yyyy-MM"), width, height, Struts2Utils.getSession());
	}
	
	private void createTotalMoneyChart() {
		TimeSeries series = new TimeSeries("订单金额", Month.class);
		
		for (OrderStatZh stat : statList) {
			series.add(new Month(stat.getStatBeginDate()), stat.getTotalMoney());
		}
		
		TimeSeriesCollection  dataset  = new TimeSeriesCollection();
		dataset.addSeries(series);
		
		int width = 60 + 35 * statList.size();
		int height = 250;
		String headerTitle = "订单金额统计";
		totalMoneyChart = ChartCreator.createTimeSeriesChart(dataset, headerTitle, 
				new SimpleDateFormat("yyyy-MM"), width, height, Struts2Utils.getSession());
	}
	
	public String getFormatMonth(Date date) {
		return new SimpleDateFormat("yyyy年MM月").format(date);
	}
	
	public Long getZhanghaoId() {
		return zhanghaoId;
	}

	public void setZhanghaoId(Long zhanghaoId) {
		this.zhanghaoId = zhanghaoId;
	}

	public String getStatYear() {
		return statYear;
	}

	public void setStatYear(String statYear) {
		this.statYear = statYear;
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
