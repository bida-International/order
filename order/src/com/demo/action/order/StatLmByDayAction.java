package com.demo.action.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.dao.OrderStatLmDao;
import com.demo.dao.LeiMuDao;
import com.demo.entity.LeiMuTable;
import com.demo.entity.order.OrderStatLm;
import com.demo.utils.ChartCreator;
import com.demo.utils.DateUtils;
import com.demo.utils.Struts2Utils;

@Controller("order.statLmByDayAction")
@Scope("prototype")
public class StatLmByDayAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private OrderStatLmDao orderStatLmDao;
	@Resource
	private LeiMuDao leiMuDao;
	
	private Long leimuId;
	private String statMonth;
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
		statList = orderStatLmDao.getStatList(leimuId, 1, statBeginTime, statEndTime);

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
		Date minDate = orderStatLmDao.findEarliestOrderDate();
		Integer minYear = DateUtils.getYear(minDate);
		Integer minMonth = DateUtils.getMonth(minDate);
		Integer nextMonth = minMonth;
		while (nextMonth <= 12) {
			dates.add(getDateString(minYear, nextMonth));
			nextMonth++;
		}
		
		Date maxDate = orderStatLmDao.findNewestDayStatDate();
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
		
		for (OrderStatLm stat : statList) {
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
		
		for (OrderStatLm stat : statList) {
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
	
	public Long getLeimuId() {
		return leimuId;
	}

	public void setLeimuId(Long leimuId) {
		this.leimuId = leimuId;
	}

	public String getStatMonth() {
		return statMonth;
	}

	public void setStatMonth(String statMonth) {
		this.statMonth = statMonth;
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
