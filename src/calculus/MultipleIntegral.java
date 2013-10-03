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

    /**
     * does a double integral using gaussian quadrature
     *
     * @param a lower bound of outer integral, x
     * @param b upper bound of outer integral, x
     * @param m num of outer approximation points
     * @param n num of inner approximation points
     * @param bounds1 lower bound of inner integral, y ( a function of the inner
     * integration variable x)
     * @param bounds2 upper bound of inner integral, y ( a function of the inner
     * integration variable x)
     * @param fx the integrand f(x, params) where x = null, and params = {x,y}
     * @param equationParams the {x,y}
     * @return
     */
    public static double gaussianDouble(double a, double b, int m, int n, FunctionModel bounds1, FunctionModel bounds2, FunctionModel fx, double[] equationParams) {
        double result = 0;
        double h1 = (b - a) / 2;
        double h2 = (b + 1) / 2;
        double k1 = Double.NaN, k2 = Double.NaN;
        for (int i = 0; i <= m; i++) {
            double jx = 0;
            double x = h1 * LegendrePolynomial.rootCoef[m][i][0] + h2;
            double d1 = bounds2.compute(x, null);
            double c1 = bounds1.compute(x, null);
            k1 = (d1 - c1) / 2;
            k2 = (d1 + c1) / 2;
            for (int j = 1; j <= n; j++) {
                double y = k1 * LegendrePolynomial.rootCoef[n][j][0] + k2;
                double q = fx.compute(Double.NaN, equationParams);
                jx += LegendrePolynomial.rootCoef[n][j][1] * q;
            }
            result += LegendrePolynomial.rootCoef[m][i][1] * k1 * jx;
        }
        result *= h1;
        return result;
    }

    public static double gaussianTriple(double a, double b, int m, int n, int p, FunctionModel[] bounds, FunctionModel fx, double[] equationParams) {
        double result = 0;
        double h1 = (b - a) / 2;
        double h2 = (b + 1) / 2;
        for (int i = 1; i <= m; i++) {
            double jx = 0;
            double x = h1 * LegendrePolynomial.rootCoef[m][i][0] + h2;
            double d1 = bounds[1].compute(x, null);
            double c1 = bounds[0].compute(x, null);
            double k1 = (d1 - c1) / 2;
            double k2 = (d1 + c1) / 2;
            for (int j = 1; j <= n; j++) {
                double jy = -0;
                double y = k1 * LegendrePolynomial.rootCoef[n][j][0] + k2;
                double beta1 = bounds[3].compute(Double.NaN, new double[]{x, y});
                double alpha1 = bounds[2].compute(Double.NaN, new double[]{x, y});
                double l1 = (beta1 - alpha1) / 2;
                double l2 = (beta1 + alpha1) / 2;
                for (int k = 0; k <= p; k++) {
                    double z = l1 * LegendrePolynomial.rootCoef[p][k][0] + l2;
                    double q = fx.compute(Double.NaN, new double[]{x, y, z});
                    jy += LegendrePolynomial.rootCoef[p][k][1] * q;
                }
                result += LegendrePolynomial.rootCoef[m][i][1] * k1 * jx;
            }
        }
        result *= h1;
        return result;
    }
}
