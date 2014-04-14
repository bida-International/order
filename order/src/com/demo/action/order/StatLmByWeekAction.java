package com.demo.action.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Week;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.dao.LeiMuDao;
import com.demo.dao.OrderStatLmDao;
import com.demo.entity.LeiMuTable;
import com.demo.entity.order.OrderStatLm;
import com.demo.utils.ChartCreator;
import com.demo.utils.DateUtils;
import com.demo.utils.Struts2Utils;

@Controller("order.statLmByWeekAction")
@Scope("prototype")
public class StatLmByWeekAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Resource
	private OrderStatLmDao orderStatLmDao;
	@Resource
	private LeiMuDao leiMuDao;
	
	private Long leimuId;
	private String statYear;
	private Date statBeginTime;
	private Date statEndTime;

	private List<String> dates = new ArrayList<String>();
	private List<OrderStatLm> statList;
	private List<LeiMuTable> leimuList;
	
	private String totalMoneyChart;
	private String orderAmountChart;
	
	public String execute() {
		this.initParams();
		this.initDates();
		
		leimuList = leiMuDao.getAllLeiMu();
		statList = orderStatLmDao.getStatList(leimuId, 2, statBeginTime, statEndTime);

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
		Date minDate = orderStatLmDao.findEarliestOrderDate();
		Integer minYear = DateUtils.getYear(minDate);
		
		Date maxDate = orderStatLmDao.findNewestDayStatDate();
		Integer maxYear = DateUtils.getYear(maxDate);
		Integer nextYear = minYear;
		while (nextYear <= maxYear) {
			dates.add(nextYear.toString());
			nextYear++;
		}
	}
	
	private void createOrderAmountChart() {
		TimeSeries series = new TimeSeries("��������", Week.class);
		
		for (OrderStatLm stat : statList) {
			series.add(new Week(stat.getStatBeginDate()), stat.getOrderAmount());
		}
		
		TimeSeriesCollection  dataset  = new TimeSeriesCollection();
		dataset.addSeries(series);
		
		int width = 60 + 35 * statList.size();
		int height = 250;
		String headerTitle = "��������ͳ��";
		orderAmountChart = ChartCreator.createTimeSeriesChart(dataset, headerTitle, 
				new SimpleDateFormat("yyyy-ww"), width, height, Struts2Utils.getSession());
	}
	
	private void createTotalMoneyChart() {
		TimeSeries series = new TimeSeries("�������", Week.class);
		
		for (OrderStatLm stat : statList) {
			series.add(new Week(stat.getStatBeginDate()), stat.getTotalMoney());
		}
		
		TimeSeriesCollection  dataset  = new TimeSeriesCollection();
		dataset.addSeries(series);
		
		int width = 60 + 35 * statList.size();
		int height = 250;
		String headerTitle = "�������ͳ��";
		totalMoneyChart = ChartCreator.createTimeSeriesChart(dataset, headerTitle, 
				new SimpleDateFormat("yyyy-ww"), width, height, Struts2Utils.getSession());
	}
	
	public Long getLeimuId() {
		return leimuId;
	}

	public void setLeimuId(Long leimuId) {
		this.leimuId = leimuId;
	}

	public String getStatYear() {
		return statYear;
	}

	public void setStatYear(String statYear) {
		this.statYear = statYear;
	}

	public List<OrderStatLm> getStatList() {
		return statList;
	}

	public void setStatList(List<OrderStatLm> statList) {
		this.statList = statList;
	}

	public List<LeiMuTable> getLeimuList() {
		return leimuList;
	}

	public void setLeimuList(List<LeiMuTable> leimuList) {
		this.leimuList = leimuList;
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
