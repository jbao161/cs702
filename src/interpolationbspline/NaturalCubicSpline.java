/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpolationbspline;

import java.util.Arrays;

/**
 *
 * @author jbao
 */
public class NaturalCubicSpline {

    /**
     *
     *
     * P(x) = a + b(x-xi) + c(x-xi)^2 + d(x-xi)^3
     *
     * @param x
     * @param fx
     */
    public static double[][] computePoly(double[] x, double[] fx) {
        int n = x.length - 1;
        // polynomial coefficients
        double[] a = new double[n + 1];
        double[] b = new double[n + 1];
        double[] c = new double[n + 1];
        double[] d = new double[n + 1];
        // initialize fx to constant term a
        for (int i = 0; i <= n; i++) {
            a[i] = fx[i];
        }
        // changes in x
        double[] h = new double[n];
        for (int i = 0; i < n; i++) {
            h[i] = x[i + 1] - x[i];
        }
        // system of equations
        // hj−1cj−1+2(hj−1+hj)cj +hjcj+1 = 3/hj*(aj+1−aj)−3/(hj−1)*(aj −aj−1),
        double[] alpha = new double[n + 1];
        for (int i = 1; i < n; i++) {
            alpha[i] = 3 / h[i] * (a[i + 1] - a[i]) - 3 / h[i - 1] * (a[i] - a[i - 1]);
        }
        // solvers for the system of equations
        double[] l = new double[n + 1];
        double[] z = new double[n + 1];
        double[] mu = new double[n + 1];
        l[0] = 1;
        l[n] = 1;
        mu[0] = 0;
        z[0] = 0;
        z[n] = 0;
        for (int i = 1; i < n; i++) {
            l[i] = 2 * (x[i + 1] - x[i - 1]) - h[i - 1] * mu[i - 1];
            mu[i] = h[i] / l[i];
            z[i] = (alpha[i] - h[i - 1] * z[i - 1]) / l[i];
        }
        // polynomial coefficients solved
        c[n] = 0;
        for (int j = n - 1; j >= 0; j--) {
            c[j] = z[j] - mu[j] * c[j + 1];
            b[j] = (a[j + 1] - a[j]) / h[j] - h[j] * (c[j + 1] + 2 * c[j]) / 3;
            d[j] = (c[j + 1] - c[j]) / (3 * h[j]);
        }
        // return coefficients as {a[],b[],c[],d[]}
        double[][] result = new double[4][n];
        double[][] coefs = {a, b, c, d};
        // the nth value in the coef array is used for calculations only, it is not reported
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = coefs[i][j];
            }
        }
        return result;
    }
}
