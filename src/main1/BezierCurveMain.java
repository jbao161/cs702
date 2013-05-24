/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main1;

/**
 *
 * @author jbao
 */
import beziercurve1.BernsteinPolynomial;
import beziercurve1.BezierCurve;
import beziercurve1.BezierPolynomial;
import numutil1.Polynomial;
import java.util.ArrayList;
import org.jfree.data.xy.XYSeries;

public class BezierCurveMain {

    public static void main(String args[]) {

        double[] x = {-9.0, -8.0, -8.0, -4.0};
        double[] y = {0.0, 1.0, 2.5, 2.5};
        double[][] data = {
            {-9.0, 0.0},
            {-8.0, 1.0},
            {-8.0, 2.5},
            {-4.0, 2.5}
        };
        BezierCurve bc = new BezierCurve(data);
        bc.print();
        XYSeries xy1 = bc.create2DPlotData(null);
        data = new double[][]{
            {-4.0, 2.5},
            {-3.0, 3.5},
            {-1.0, 4.0},
            {-0.0, 4.}
        };
        bc = new BezierCurve(data);
        bc.print();
         XYSeries xy2 = bc.create2DPlotData(xy1);
        data = new double[][]{
            {-0.0, 4.0},
            {2.0, 4.0},
            {3.0, 4.0},
            {5.0, 2.0}
        };
             bc = new BezierCurve(data);
        bc.print();
        XYSeries xy3 = bc.create2DPlotData(xy2);
        data = new double[][]{
            {5.0, 2.0},
            {6, 2},
            {20, 3.0},
            {18.0, 0.0}
        };
             bc = new BezierCurve(data);
        bc.print();
        XYSeries xy4 = bc.create2DPlotData(xy3);
        ArrayList<XYSeries> datasets = new ArrayList<XYSeries>();
        datasets.add(xy4);
        numutil1.Plot.plot("composite bezier curve", datasets, true);

    }
}
