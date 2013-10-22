package pl.krakow.v_lo.algosound.gui;

import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import pl.krakow.v_lo.algosound.Command;

public class SoundGraph {

	private ChartPanel chartPanel;

	public SoundGraph(Command command, String chartTitle, Dimension dimension) {
		XYDataset dataset = createDataset(command);
		JFreeChart chart = createChart(dataset, chartTitle);
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(dimension);
		chartPanel.setMinimumSize(dimension);
		chartPanel.setMaximumSize(dimension);
		chartPanel.setSize(dimension);
		chartPanel.setVisible(true);
	}

	private JFreeChart createChart(XYDataset dataset, String title) {
		final JFreeChart chart = ChartFactory.createXYLineChart(
	            title,      // chart title
	            "time",                      // x axis label
	            "value",                      // y axis label
	            dataset,                  // data
	            PlotOrientation.VERTICAL,
	            false,                     // include legend
	            false,                     // tooltips
	            false                     // urls
	        );

		return chart;
	}

	private XYDataset createDataset(Command command) {
		
		final XYSeries series1 = new XYSeries("First");
		
		int size = command.getRawData().size();

		for (int i = 1; i < size; i+=80) {
			series1.add(i, command.getRawData().get(i).getReal());
		}
	
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		return dataset;
	}

	public ChartPanel getChartPanel() {
		return chartPanel;
	}

}
