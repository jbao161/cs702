/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import LagrangeInterpolation.LagrangePlot;
import NumUtil.Polynomial;
import javax.swing.JFrame;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author jbao
 */
public class LagrangeInterpolationMain {

    public static void main(String[] args) {
        Polynomial n = new Polynomial();
        double[][] py1 = {{0, -1}, {3, 1}};
        double[][] py2 = {{0, 1}, {4, 2}};
        Polynomial p1 = new Polynomial(py1);
        Polynomial p2 = new Polynomial(py2);
        Polynomial p3 = n.add(p2).times(1.0);

        double[][] data = {{2.0, 0.5}, {2.75, 4.0 / 11.0}, {4.0, 0.25}};
        double[] intensity = {
            201702.5,
            214761.3,
            103005.3,
            49700.7};

        double[] concentration = {
            50000.0,
            25000.0,
            6250.0,
            1562.5
        };
        double[] intensity2 = {
            14995.3,
            4202.9,
            3185.3};
        double[] concentration2 = {
            390.6,
            97.7,
            48.8
        };
        int numDataPts = intensity.length;
        data = new double[numDataPts][2];
        for (int i = 0; i < numDataPts; i++) {
            data[i][0] = concentration[i];
            data[i][1] = intensity[i];
        }
        Polynomial result = LagrangeInterpolation.LagrangePolynomial.interpolate(data);
        result.print();
        System.out.println(result.toString());
        System.out.println(result.evaluate(1.0));
        LagrangePlot lp = new LagrangePlot(10, data, result);
        ApplicationFrame frame = new ApplicationFrame("polynomial interpolation");
        frame.setContentPane(lp.chartPanel);
        frame.setSize(600, 800);
        frame.setVisible(true);


        // second part

        numDataPts = intensity2.length;
        data = new double[numDataPts][2];
        for (int i = 0; i < numDataPts; i++) {
            data[i][0] = concentration2[i];
            data[i][1] = intensity2[i];
        }
        result = LagrangeInterpolation.LagrangePolynomial.interpolate(data);
        lp = new LagrangePlot(10, data, result);
        frame = new ApplicationFrame("polynomial interpolation");
        frame.setContentPane(lp.chartPanel);
        frame.setSize(600, 800);
        frame.setLocation(600,0);
        frame.setVisible(true);
    }
}
