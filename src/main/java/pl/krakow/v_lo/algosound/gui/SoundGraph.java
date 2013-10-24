package pl.krakow.v_lo.algosound.gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import pl.krakow.v_lo.algosound.Command;
import pl.krakow.v_lo.algosound.maths.FastFourierTransform;

public class SoundGraph
{

  private ChartPanel chartPanel;
  private String     chartTitle;

  public SoundGraph(Command command, String chartTitle, Dimension dimension)
  {
    this.chartTitle = chartTitle;
    XYDataset dataset = createDataset(command);
    JFreeChart chart = createChart(dataset, chartTitle);
    chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(dimension);
    chartPanel.setMinimumSize(dimension);
    chartPanel.setMaximumSize(dimension);
    chartPanel.setSize(dimension);
    chartPanel.setVisible(true);
  }

  private JFreeChart createChart(XYDataset dataset, String title)
  {
    final JFreeChart chart = ChartFactory.createXYLineChart(title, // chart title
        "time", // x axis label
        "value", // y axis label
        dataset, // data
        PlotOrientation.VERTICAL, false, // include legend
        false, // tooltips
        false // urls
        );

    return chart;
  }

  private XYDataset createDataset(Command command)
  {

    final XYSeries series1 = new XYSeries("First");

    int size = command.getRawData().size();
    int powOf2 = 1;
    System.out.println("Creating");
    while(powOf2 < size)
      powOf2 *= 2;
    powOf2 /= 2;
    System.out.println("Computed");
    
    List<Complex> data = new ArrayList<Complex>();
    data = command.getRawData().subList(0, powOf2);
    FastFourierTransform fft = new FastFourierTransform(data);
    fft.transformForward();
    List<Complex> toShow = fft.getResult();
    
    double resolution = 44100.0F / powOf2;
    
    for (int i = 1; i < toShow.size(); i += 70)
    {
      series1.add(i * resolution, toShow.get(i).abs());
    }

    final XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(series1);
    return dataset;
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

}
