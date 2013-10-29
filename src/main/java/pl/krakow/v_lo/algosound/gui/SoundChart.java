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

public class SoundChart extends ChartPanel
{
  private String     chartTitle;
  
  public SoundChart(Command command, String chartTitle, Dimension dimension)
  {
    super(null);
    this.chartTitle = chartTitle;
    setChart(createChart(command));
    setPreferredSize(dimension);
    setMinimumSize(dimension);
    setMaximumSize(dimension);
    setSize(dimension);
    setVisible(true);
  }

  private JFreeChart createChart(Command command)
  {
    final JFreeChart chart = ChartFactory.createXYLineChart(
        chartTitle,                      // chart title
        "Sample",                        // x axis label
        "Amplitude",                     // y axis label
        createDataset(command),          // data
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

  public void updateChart(Command command)
  {
    setChart(createChart(command));
  }
  
  public void hideIt()
  {
    setVisible(false);
  }
  
  public void unhideIt()
  {
    setVisible(true);
  }
}
