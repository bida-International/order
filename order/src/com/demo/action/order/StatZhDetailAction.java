package com.demo.action.order;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jfree.data.category.DefaultCategoryDataset;
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

@Controller("order.statZhDetailAction")
@Scope("prototype")
public class StatZhDetailAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private OrderStatZhDao orderStatZhDao;
	@Resource
	private ZhangHaoDao zhangHaoDao;
	
	private Long zhanghaoId;
	private Integer statType = 1;
	private String beginDate;
	private String endDate;
	private Date beginTime;
	private Date endTime;
	
	private List<OrderStatZh> statList;
	private List<ZhangHao> zhangHaoList;
	
	private String totalMoneyChart;
	private String orderAmountChart;
	private String jiufenRateChart;
	
	public String execute() {
		this.initParams();
		zhangHaoList = zhangHaoDao.getAllZhangHao();
		statList = orderStatZhDao.getStatList(zhanghaoId, statType, beginTime, endTime);
		for (OrderStatZh stat : statList) {
			Double jiufenRate = 0.0d;
			if (!stat.getOrderAmount().equals(0)) {
				jiufenRate = Double.parseDouble(stat.getJiufenAmount().toString()) /
						Double.parseDouble(stat.getOrderAmount().toString()) * 100;
			}
			jiufenRate = Double.parseDouble(new DecimalFormat("#0.00").format(jiufenRate));
			stat.setJiufenRate(jiufenRate);
		}
		if (!statList.isEmpty() && statList.size() >= 2) {
			this.createTotalMoneyChart();
			this.createOrderAmountChart();
			this.createJiufenRateChart();
		}
		return SUCCESS;
	}
	
	private void initParams() {
		try {
			if (beginDate == null) {
				beginTime = DateUtils.getAfterDaysDate(new Date(), -30);
				beginTime = DateUtils.getDayStart(beginTime);
				beginDate = new SimpleDateFormat("yyyy-MM-dd").format(beginTime);
			} else {
				beginTime = new SimpleDateFormat("yyyy-MM-dd").parse(beginDate);
				if (statType == 2) {
					beginTime = DateUtils.getFirstDayOfWeek(beginTime);
				} else if (statType == 3) {
					beginTime = DateUtils.getFirstDayOfMonth(beginTime);
				}
				beginTime = DateUtils.getDayStart(beginTime);
			}
			
			if (endDate == null) {
				endTime = DateUtils.getDayEnd(new Date());
				endDate = new SimpleDateFormat("yyyy-MM-dd").format(endTime);
			} else {
				endTime = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
				if (statType == 2) {
					endTime = DateUtils.getLastDayOfWeek(endTime);
				} else if (statType == 3) {
					endTime = DateUtils.getLastDayOfMonth(endTime);
				}
				endTime = DateUtils.getDayEnd(endTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getFormatDate(Date date) {
		return new SimpleDateFormat("yyyy年MM月dd日").format(date);
	}
	
	public String getFormatMonth(Date date) {
		return new SimpleDateFormat("yyyy年MM月").format(date);
	}
	
	private void createOrderAmountChart() {
		String fileName = "oa" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".jpg";

		XYSeriesCollection xySeriesCol = new XYSeriesCollection();
		XYSeries xySeries = new XYSeries("总数");
		for (OrderStatZh orderStatZh : statList) {
			if (statType == 1) {
				xySeries.add(DateUtils.getDay(orderStatZh.getStatDate()), orderStatZh.getOrderAmount());
			} else if (statType == 2) {
				xySeries.add(orderStatZh.getStatWeek(), orderStatZh.getOrderAmount());
			} else if (statType == 3) {
				xySeries.add(orderStatZh.getStatMonth(), orderStatZh.getOrderAmount());
			}
		}
		xySeriesCol.addSeries(xySeries);

		// 设置标题文字
		String headerTitle = "订单总数统计";
		ChartCreator.createXyLineChart(xySeriesCol, headerTitle, fileName);
		orderAmountChart = ChartCreator.getFileDir() + fileName;
	}
	
	private void createTotalMoneyChart() {
		String fileName = "tm" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".jpg";
		
		XYSeriesCollection xySeriesCol = new XYSeriesCollection();
		XYSeries xySeries = new XYSeries("总金额");
		for (OrderStatZh orderStatZh : statList) {
			if (statType == 1) {
				xySeries.add(DateUtils.getDay(orderStatZh.getStatDate()), orderStatZh.getTotalMoney());
			} else if (statType == 2) {
				xySeries.add(orderStatZh.getStatWeek(), orderStatZh.getTotalMoney());
			} else if (statType == 3) {
				xySeries.add(orderStatZh.getStatMonth(), orderStatZh.getTotalMoney());
			}
		}
		xySeriesCol.addSeries(xySeries);

		// 设置标题文字
		String headerTitle = "订单总金额统计";
		ChartCreator.createXyLineChart(xySeriesCol, headerTitle, fileName);
		totalMoneyChart = ChartCreator.getFileDir() + fileName;
	}
	
	private void createJiufenRateChart() {
		String fileName = "jr" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".jpg";

		XYSeriesCollection xySeriesCol = new XYSeriesCollection();
		XYSeries xySeries = new XYSeries("纠纷率");
		for (OrderStatZh orderStatZh : statList) {
			if (statType == 1) {
				xySeries.add(DateUtils.getDay(orderStatZh.getStatDate()), orderStatZh.getJiufenRate());
			} else if (statType == 2) {
				xySeries.add(orderStatZh.getStatWeek(), orderStatZh.getJiufenRate());
			} else if (statType == 3) {
				xySeries.add(orderStatZh.getStatMonth(), orderStatZh.getJiufenRate());
			}
		}
		xySeriesCol.addSeries(xySeries);
		
		// 设置标题文字
		String headerTitle = "订单纠纷率统计";
		ChartCreator.createXyLineChart(xySeriesCol, headerTitle, fileName);
		jiufenRateChart = ChartCreator.getFileDir() + fileName;
	}
	

	public OrderStatZhDao getOrderStatZhDao() {
		return orderStatZhDao;
	}

	public void setOrderStatZhDao(OrderStatZhDao orderStatZhDao) {
		this.orderStatZhDao = orderStatZhDao;
	}

	public Long getZhanghaoId() {
		return zhanghaoId;
	}

	public void setZhanghaoId(Long zhanghaoId) {
		this.zhanghaoId = zhanghaoId;
	}

	public Integer getStatType() {
		return statType;
	}

	public void setStatType(Integer statType) {
		this.statType = statType;
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

	public String getJiufenRateChart() {
		return jiufenRateChart;
	}

	public void setJiufenRateChart(String jiufenRateChart) {
		this.jiufenRateChart = jiufenRateChart;
	}
	
}
