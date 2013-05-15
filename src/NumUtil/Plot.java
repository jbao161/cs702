/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NumUtil;

import java.awt.Color;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author jbao
 */
public class Plot {

    public static ChartPanel plot(String title, ArrayList<XYSeries> dataSets, boolean visible) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (int i =0; i < dataSets.size(); i ++){
             dataset.addSeries(dataSets.get(i));
        }
        JFreeChart chart = ChartFactory.createXYLineChart(
                title, "x", "y", dataset, PlotOrientation.VERTICAL, true, true, false);
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        if (visible){
        ApplicationFrame frame = new ApplicationFrame(title);
        frame.setContentPane(chartPanel);
        frame.setSize(600, 800);
        frame.setVisible(true);
        }
        return chartPanel;
    }
}
