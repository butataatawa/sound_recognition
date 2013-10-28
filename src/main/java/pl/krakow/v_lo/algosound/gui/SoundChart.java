package pl.krakow.v_lo.algosound.gui;

import java.awt.Dimension;
import org.apache.commons.math3.complex.Complex;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import pl.krakow.v_lo.algosound.Command;

public class SoundChart
{
  private ChartPanel chartPanel;
  private String     chartTitle;
  
  public SoundChart(Command command, String chartTitle, Dimension dimension)
  {
    this.chartTitle = chartTitle;
    XYDataset dataset = createDataset(command);
    chartPanel = new ChartPanel(createChart(dataset, chartTitle));
    chartPanel.setPreferredSize(dimension);
    chartPanel.setMinimumSize(dimension);
    chartPanel.setMaximumSize(dimension);
    chartPanel.setSize(dimension);
    chartPanel.setVisible(true);
  }

  private JFreeChart createChart(XYDataset dataset, String title)
  {
    final JFreeChart chart = ChartFactory.createXYLineChart(
        title,                           // chart title
        "Sample",                        // x axis label
        "Amplitude",                     // y axis label
        dataset,                         // data
        PlotOrientation.VERTICAL, false, // include legend
        false,                           // tooltips
        false                            // urls
    );

    return chart;
  }

  private XYDataset createDataset(Command command)
  {
    final XYSeries series = new XYSeries("Amplitude - time");

    int xValue = 0;
    for (Complex yValue : command.getRawData())
      series.add(xValue++, yValue.getReal());

    return new XYSeriesCollection(series);
  }

  public ChartPanel getChartPanel()
  {
    return chartPanel;
  }

  public void updateChart(Command command)
  {
    XYDataset dataset = createDataset(command);
    JFreeChart chart = createChart(dataset, chartTitle);
    chartPanel.setChart(chart);
  }
  
  public void hide()
  {
    chartPanel.setVisible(false);
  }
  
  public void unhide()
  {
    chartPanel.setVisible(true);
  }
}
