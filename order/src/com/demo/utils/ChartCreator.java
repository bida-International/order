package com.demo.utils;

import java.awt.Font;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

public class ChartCreator {

	public static String createTimeSeriesChart(TimeSeriesCollection dataset, 
			String headerTitle, SimpleDateFormat dateFormat, int width, int height, HttpSession session) {
		JFreeChart  chart  = ChartFactory.createTimeSeriesChart("",  "", "", dataset,  false,  false,  false);	
		// 设置标题文字
		Font font = new Font("黑体", Font.HANGING_BASELINE, 14);
		TextTitle title = new TextTitle(headerTitle, font);
		chart.setTitle(title);
		chart.setAntiAlias(true);
		
		XYPlot plot = (XYPlot) chart.getPlot();
		//设置网格背景颜色
//		plot.setBackgroundPaint(Color.white);
//		//设置网格竖线颜色
//		plot.setDomainGridlinePaint(Color.pink);
//		//设置网格横线颜色
//		plot.setRangeGridlinePaint(Color.pink);
		//设置曲线图与 xy 轴的距离
		plot.setAxisOffset(new  RectangleInsets(0D,  0D, 0D,  10D));
		
		DateAxis dateAxis = (DateAxis) plot.getDomainAxis();
		dateAxis.setDateFormatOverride(dateFormat);
//		dateAxis.setLowerMargin(0.15D);
//		dateAxis.setUpperMargin(0.15D);
		
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLowerMargin(0.15D);
        rangeAxis.setUpperMargin(0.15D);
        NumberFormat numFormat = new DecimalFormat("#");
    	rangeAxis.setNumberFormatOverride(numFormat);

		XYLineAndShapeRenderer  xylineandshaperenderer  = (XYLineAndShapeRenderer)plot.getRenderer();
		
		//设置曲线是否显示数据点
		xylineandshaperenderer.setBaseShapesVisible(false);
		//设置曲线显示各数据点的值
		XYItemRenderer  renderer  = plot.getRenderer();     
		renderer.setBaseItemLabelsVisible(true);    
		renderer.setBasePositiveItemLabelPosition( new  ItemLabelPosition
		(ItemLabelAnchor.OUTSIDE12,   TextAnchor.BASELINE_LEFT));
		renderer.setBaseItemLabelGenerator( new  StandardXYItemLabelGenerator());
		renderer.setBaseItemLabelFont(new Font(Font.SERIF, Font.HANGING_BASELINE, 11));
		plot.setRenderer(renderer);
		
		String filename = null;
		try {
			filename = ServletUtilities.saveChartAsPNG(chart, width, height, session);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filename;
	}
	
	public static String createBarChart(DefaultCategoryDataset dataset, 
			String headerTitle, PlotOrientation plotOrientation, int width, int height, 
			boolean isPercentType, HttpSession session) {
		JFreeChart chart = ChartFactory.createBarChart("", "", "", 
				dataset, plotOrientation, false, false, false);
		// 设置标题文字
		Font font = new Font("黑体", Font.HANGING_BASELINE, 14);
		TextTitle title = new TextTitle(headerTitle, font);
		chart.setTitle(title);
		
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		//设置网格背景颜色
//		plot.setBackgroundPaint(Color.white);
//		//设置网格竖线颜色
//		plot.setDomainGridlinePaint(Color.pink);
//		//设置网格横线颜色
//		plot.setRangeGridlinePaint(Color.pink);
		//设置曲线图与 xy 轴的距离
		plot.setAxisOffset(new  RectangleInsets(0D,  0D, 0D,  10D));
		
		// set the range axis to display integers only...
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLowerMargin(0.15D);
        rangeAxis.setUpperMargin(0.15D);
        if (isPercentType) {
        	rangeAxis.setNumberFormatOverride(NumberFormat.getPercentInstance());
        } else {
        	NumberFormat numFormat = new DecimalFormat("#");
        	rangeAxis.setNumberFormatOverride(numFormat);
        }
        
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLowerMargin(0.1D);
		domainAxis.setUpperMargin(0.1D);
		
        // disable bar outlines...
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBaseItemLabelFont(new Font(Font.SERIF, Font.HANGING_BASELINE, 11));
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE3, TextAnchor.HALF_ASCENT_RIGHT));
		if (isPercentType) {
			NumberFormat percentFormat = NumberFormat.getPercentInstance();
			percentFormat.setMinimumFractionDigits(2);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", percentFormat));
		} else {
        	NumberFormat numFormat = new DecimalFormat("#");
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", numFormat));
		}
		plot.setRenderer(renderer);
		
		String filename = null;
		try {
			filename = ServletUtilities.saveChartAsPNG(chart, width, height, session);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filename;
	}
	
}
