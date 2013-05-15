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
     * @param controlPoints input format: {p1,p2, ...} for control points p1 =
     * {x1,y1,..}, p2 = {x2,y2,..}
     */
    public BezierCurve(double[][] controlPoints) {
        double[][] coordinateArrays = NumUtil.MathTools.pointToCoordinateArray(controlPoints);
        int numOfDimensions = coordinateArrays.length;
        for (int i = 0; i < numOfDimensions; i++) {
            BezierPolynomial bp = new BezierPolynomial(coordinateArrays[i]);
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

    /**
     * Creates a set of x,y points for plotting a 2D bezier curve
     *
     * @param curveSeries add to an existing XYSeries, or use null to generate a
     * new series
     * @return an updated XYSeries for plotting a composite bezier curve, or a
     * new XYSeries
     */
    public XYSeries create2DPlotData(XYSeries curveSeries) {
        double numOfPoints = 10e3;
        double increment = 1.0 / numOfPoints;
        double xMarker;
        double yMarker;
        BezierPolynomial bpx = this.get(0);
        BezierPolynomial bpy = this.get(1);
        if (curveSeries == null) {
            curveSeries = new XYSeries("Bezier Curve");
        }
        // the bezier curve extends over the parameter t range from t = 0 to t = 1
        for (double i = 0; i < 1; i += increment) {
            xMarker = bpx.evaluate(i);
            yMarker = bpy.evaluate(i);
            curveSeries.add(xMarker, yMarker);
        }
        return curveSeries;
    }

    public ChartPanel plot2D(boolean visible) {
        String title = get(0).toText("t") + "\n" + get(1).toText("t");
        ArrayList<XYSeries> dataSets = new ArrayList<XYSeries>();
        dataSets.add(create2DPlotData(null));
        return NumUtil.Plot.plot(title, dataSets, visible);
    }
    /* comments:
     * 1. the curve is parameterized by t, which starts at t=0 and ends at t=1. x and y depend on the polynomials
     * 2. the curve is continuous and has derivatives of all orders on t:[0,1]
     * 3. the curve starts at the first control point and ends at the last control point
     * 4. the interior control points do not (necessarily) lie on the curve
     * 5. the end control points of the curve B have slopes B'(p_0) = n * (p_0-_p1) and p'(t=1) = n * (p_n - p_(n-1)), where n = # of points -1
     * 6. the curve lies in the intersection of all convex sets containing the set of its control points.
     */
}
