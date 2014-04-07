package com.demo.utils;

import java.awt.Font;
import java.io.FileOutputStream;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeriesCollection;
import org.slf4j.LoggerFactory;

public class ChartCreator {

	/**
	 * 生成曲线图
	 */
	public static void createXyLineChart(XYSeriesCollection xySeriesCol,
			String headerTitle, String fileName) {
		JFreeChart chart = ChartFactory.createXYLineChart("", "", "",
				xySeriesCol, PlotOrientation.VERTICAL, true, true, false);
		Font font = new Font("宋体", Font.BOLD, 16);
		TextTitle title = new TextTitle(headerTitle, font);
		chart.setTitle(title);
		
		chart.getLegend().setItemFont(new Font("宋体", Font.ITALIC, 12));
		chart.setNotify(false);
		
		try {
			String filePath = getFilePath();
			if (!FileUtils.isExistFile(filePath + fileName)) {
				FileUtils.createFile(filePath, fileName);
			}
			FileOutputStream fos = new FileOutputStream(filePath + fileName);
			ChartUtilities.writeChartAsJPEG(fos, chart, 1100, 350);
			fos.close();
		} catch (Exception e) {
			LoggerFactory.getLogger(ChartCreator.class).error(e.toString());
		}
	}

	/**
	 * 生成柱状图
	 */
	public static void createBarChart3D(DefaultCategoryDataset categoryDataset, 
			String headerTitle, String fileName, boolean isPercentType) {
		JFreeChart chart = ChartFactory.createBarChart3D("", "", "", 
				categoryDataset, PlotOrientation.VERTICAL, false, false, false);
		Font font = new Font("宋体", Font.BOLD, 16);
		TextTitle title = new TextTitle(headerTitle, font);
		chart.setTitle(title);
		
		BarRenderer3D renderer = new BarRenderer3D();
		if (isPercentType) {
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator(
					"{2}%", NumberFormat.getInstance(), NumberFormat.getPercentInstance()));
		} else {
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		}
		renderer.setBaseItemLabelsVisible(true);
		chart.getCategoryPlot().setRenderer(renderer);

		try {
			String filePath = getFilePath();
			if (!FileUtils.isExistFile(filePath + fileName)) {
				FileUtils.createFile(filePath, fileName);
			}
			FileOutputStream fos = new FileOutputStream(filePath + fileName);
			ChartUtilities.writeChartAsJPEG(fos, chart, 1100, 350);
			fos.close();
		} catch (Exception e) {
			LoggerFactory.getLogger(ChartCreator.class).error(e.toString());
		}
	}

	/**
	 * 生成柄状图
	 */
	public static void createPieChart(DefaultPieDataset pieDataset,
			String headerTitle, String fileName) {
		// 用工厂类创建饼图
		JFreeChart chart = ChartFactory.createPieChart("", pieDataset, true,
				true, false);
		Font font = new Font("宋体", Font.BOLD, 16);
		TextTitle title = new TextTitle(headerTitle, font);
		chart.setTitle(title);

		try {
			String filePath = getFilePath();
			if (!FileUtils.isExistFile(filePath + fileName)) {
				FileUtils.createFile(filePath, fileName);
			}
			FileOutputStream fos = new FileOutputStream(filePath + fileName);
			ChartUtilities.writeChartAsJPEG(fos, chart, 1100, 350);
			fos.close();
		} catch (Exception e) {
			LoggerFactory.getLogger(ChartCreator.class).error(e.toString());
		}
	}

	/**
	 * 获取图片文件目录本地地址
	 */
	private static String getFilePath() {
		return ApplicationUtils.getPrjRootDir()
				+ getFileDir();
	}
	
	public static String getFileDir() {
		return "/tmp/chart/";
	}
}
