/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpolatelagrange;

/**
 *
 * @author jbao
 */
import numutil.Polynomial;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import numutil.MathTools;

public class LagrangePlot {

    public ChartPanel chartPanel;

    public LagrangePlot(double logBase, double[][] data, Polynomial px) {

        XYSeriesCollection dataset = new XYSeriesCollection();

        // data pts
        XYSeries dataSeries = new XYSeries("Data");
        for (int i = 0; i < data.length; i++) {
            dataSeries.add(MathTools.logBase(logBase, data[i][0]), MathTools.logBase(logBase, data[i][1]));
        }
        dataset.addSeries(dataSeries);

        // curve pts
        double xmin = MathTools.logBase(logBase, MathTools.min(data));
        double xmax = MathTools.logBase(logBase, numutil.MathTools.max(data));
        double numCurvePts = 10e2;
        double increment = (xmax - xmin) / numCurvePts;
        double xMarker;
        double yMarker;
        XYSeries curveSeries = new XYSeries("Curve");
        for (int i = 0; i < numCurvePts; i++) {
            xMarker = Math.pow(logBase, xmin + increment * i);
            yMarker = px.evaluate(xMarker);
            curveSeries.add(MathTools.logBase(logBase, xMarker), MathTools.logBase(logBase, yMarker));
        }
        dataset.addSeries(curveSeries);

        // chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Lagrange Interpolation", "x", "f(x)", dataset, PlotOrientation.VERTICAL, true, true, false);
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
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
    }
}
