/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus;

import function.FunctionModel;
import numutil.LegendrePolynomial;

/**
 *
 * @author jbao
 */
public class MultipleIntegral {

    public static double compositeSimpson(double x1, double x2, double y1, double y2, int xsteps, int ysteps, FunctionModel fx, double[] equationParams) {

        return Double.NaN;
    }

    public static double simpsonsDouble(double a, double b, int m, int n, FunctionModel bounds1, FunctionModel bounds2, FunctionModel fx, double[] equationParams) {
        double h = (b - 1) / n;
        double sum1 = 0;
        double sum2 = 0;
        double sum3 = 0;

        for (int i = 0; i <= n; i++) {
            double x = a + i * h;
            double dx = bounds2.compute(x, null);
            double cx = bounds1.compute(x, null);
            double hx = (dx - cx) / m;
            double k1 = fx.compute(Double.NaN, new double[]{x, cx});
            double k2 = 0;
            double k3 = 0;
            for (int j = 0; j < m; j++) {
                double y = cx + j * hx;
                double q = fx.compute(Double.NaN, new double[]{x, y});
                if (j % 2 == 0) {
                    k2 += q;
                } else {
                    k3 += q;
                }
            }
            double l = (k1 + 2 * k2 + 4 * k3) * hx / 3;
            if (i == 0 || i == n) {
                sum1 += l;
            } else if (i % 2 == 0) {
                sum2 += l;
            } else {
                sum3 += l;
            }
        }
        double result = h * (sum1 + 2 * sum2 + 4 * sum3) / 3;
        return result;
    }
    
     public static double gaussianDouble (double a, double b, int m, int n, FunctionModel bounds1, FunctionModel bounds2, FunctionModel fx, double[] equationParams) {
        double result =0;
        double h1 = (b-a)/2;
        double h2 = (b+1)/2;
        for (int i=0; i <=m; i ++){
            double jx = 0;
             double x = h1 * LegendrePolynomial.rootCoef[m][i][0] + h2;
        }
         return result;
     }
}
