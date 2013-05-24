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
import org.jfree.chart.axis.NumberTickUnit;
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
        for (int i = 0; i < dataSets.size(); i++) {
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
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);
        plot.setRenderer(renderer);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 500));
        if (visible) {
            ApplicationFrame frame = new ApplicationFrame(title);
            frame.setContentPane(chartPanel);
            frame.setSize(600, 800);
            frame.setVisible(true);
        }
        return chartPanel;
    }

    public static XYSeries create2DPlotData(double[][] dataPoints, XYSeries curveSeries) {
        double numOfPoints = dataPoints.length;
        if (curveSeries == null) {
            curveSeries = new XYSeries("");
        }
        for (int i = 0; i < numOfPoints; i++) {
            curveSeries.add(dataPoints[i][0], dataPoints[i][1]);
        }
        return curveSeries;
    }

    /**
     * creates a 2D plot using {x,y} data pairs as inputs
     *
     * @param title title for the ChartPanel generated
     * @param dataPoints {{x1,y1},{x2,y2},...{x_n,y_n}}
     * @param visible true = display the plot and return the ChartPanel, false =
     * just return the ChartPanel
     * @return
     */
    public static ChartPanel plot(String title, double[][] dataPoints, boolean visible) {
        XYSeries xy = create2DPlotData(dataPoints, new XYSeries("data", false));

        ArrayList<XYSeries> oneXYSet = new ArrayList<XYSeries>();
        oneXYSet.add(xy);


        return plot(title, oneXYSet, visible);
    }
}
