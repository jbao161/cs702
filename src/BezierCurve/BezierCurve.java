/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BezierCurve;

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
public class BezierCurve extends ArrayList<BezierPolynomial> {

    /**
     *
     * @param controlPoints input format: {p1,p2, ...}
     * for control points p1 = {x1,y1,..}, p2 = {x2,y2,..}
     */
    public BezierCurve(double[][] controlPoints) {
        double[][] coordinateArrays = Util.MathTools.pointToCoordinateArray(controlPoints);
        int numOfDimensions = coordinateArrays.length;
        for (int i = 0; i < numOfDimensions; i++) {
            BezierPolynomial bp = new BezierPolynomial(controlPoints[i]);
            add(bp);
        }
    }

    public BezierCurve(double[] x, double[] y) {
        BezierPolynomial bp = new BezierPolynomial(x);
        add(bp);
        bp = new BezierPolynomial(y);
        add(bp);
    }

    public void print() {
        for (int i = 0; i < this.size(); i++) {
            this.get(i).print();
        }
    }
    public XYSeries create2DPlotData(){
        double numOfPoints = 10e3;
        double increment = 1.0 / numOfPoints;
        double xMarker;
        double yMarker;
        BezierPolynomial bpx = this.get(0);
        BezierPolynomial bpy = this.get(1);
        XYSeries curveSeries = new XYSeries("Bezier Curve");
        // the bezier curve extends over the parameter t range from t = 0 to t = 1
        for ( double i = 0; i < 1; i += increment ){
            xMarker = bpx.evaluate(i);
            yMarker = bpy.evaluate(i);
            curveSeries.add( xMarker, yMarker);
        }
        return curveSeries;
    }
    public void plot2D() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(create2DPlotData());
        JFreeChart chart = ChartFactory.createXYLineChart(
                get(0).toText("t")+"\n"+get(1).toText("t"), "x", "y", dataset, PlotOrientation.VERTICAL, true, true, false);
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
            ApplicationFrame frame = new ApplicationFrame("polynomial interpolation");
        frame.setContentPane(chartPanel);
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
    
}
