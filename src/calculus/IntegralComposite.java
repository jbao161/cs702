/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus;

import function.FunctionModel;

/**
 *
 * @author jbao
 */
public class IntegralComposite {

    public static double c1(int n, double x1, double x2, FunctionModel function, double[] equationParams) {
        // length of each interval
        double h = (x2 - x1) / n;
        // f(x1) + f(x2)
        double xi0 = function.compute(x1, equationParams) + function.compute(x2, equationParams);
        // sum of f(x)
        double xi1 = 0;
        // interval subdivision point
        double x;
        for (int i = 1; i < n; i++) {
            x = x1 + i * h;
            xi1 += function.compute(x, equationParams);
        }
        double result = h * (xi0 + 2 * xi1) / 2.0;
        return result;
    }// composite trapezoidal rule

    public static double c2(int n, double x1, double x2, FunctionModel function, double[] equationParams) {
        if (n % 2 != 0) {
            // the number of subintervals, n, is required to be even
            return Double.NaN;
        }
        // length of each interval
        double h = (x2 - x1) / n;
        // f(x1) + f(x2)
        double xi0 = function.compute(x1, equationParams) + function.compute(x2, equationParams);
        // sum of f(x_odd)
        double xi1 = 0;
        // sum of f(x_even)
        double xi2 = 0;
        // interval subdivision point
        double x;
        for (int i = 1; i < n; i++) {
            x = x1 + i * h;
            if (i % 2 == 0) {
                xi2 = xi2 + function.compute(x, equationParams);
            } else {
                xi1 = xi1 + function.compute(x, equationParams);
            }
        }
        double result = h * (xi0 + 2 * xi2 + 4 * xi1) / 3.0;
        return result;
    } // composite simpson's rule

    public static double c3(int n, double x1, double x2, FunctionModel function, double[] equationParams) {
        // n is the number of subintegral rectangles
        // length of each interval
        double h = (x2 - x1) / n;
        // sum of f(x_midpoint)
        double sumfx = 0;
        // midpoint
        double x;
        // sum f(x) of midpoints in each subinterval
        for (int i = 0; i < n; i++) {
            // midpoint
            x = (x1 + h / 2) + i * h;
            sumfx += function.compute(x, equationParams);
        }
        double result = 2 * h * sumfx;
        return result;
    } // composite midpoint rule

    public static double[][] rombergc1(int n, double x1, double x2, FunctionModel function, double[] equationParams) {
        // n is the number of levels of extrapolation kMax, jMax = n
        // approximations for extrapolation
        double[][] rkj = new double[n][];
        for (int j = 0; j < n; j++) {
            // subsequent row needs one less term
            rkj[j] = new double[n - j];
        }
        // first row is approximations using n = 2^i interval points
        for (int k = 0; k < n; k++) {
            rkj[0][k] = c1((int) Math.pow(2, k), x1, x2, function, equationParams);
        }
        for (int j = 1; j < n; j++) {
            // subsequent row needs one less term
            for (int k = 0; k < n - j; k++) {
                double r1 = rkj[j - 1][k + 1];
                double r2 = rkj[j - 1][k];
                double kjValue = r1 + 1.0 / (Math.pow(4.0, j) - 1.0) * (r1 - r2);
                rkj[j][k] = kjValue;
            }
        }
        return rkj;
    }
}
