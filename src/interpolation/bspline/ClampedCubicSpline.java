/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpolation.bspline;

/**
 *
 * @author jbao
 */
public class ClampedCubicSpline {

    public static double[][] computePoly(double[] x, double[] fx, double dx0, double dxn) {
        int n = x.length - 1;
        // polynomial coefficients
        double[] a = new double[n + 1];
        double[] b = new double[n + 1];
        double[] c = new double[n + 1];
        double[] d = new double[n + 1];
        // initialize f'(x) as constant term a
        for (int i = 0; i <= n; i++) {
            a[i] = fx[i];
        }
        // changes in x
        double[] h = new double[n];
        for (int i = 0; i < n; i++) {
            h[i] = x[i + 1] - x[i];
        }
        // system of equations
        double[] alpha = new double[n + 1];
        alpha[0] = 3 * (a[1] - a[0]) / h[0] - 3 * dx0;
        alpha[n] = 3 * (dxn - (a[n] - a[n - 1]) / h[n - 1]);
        for (int i = 1; i < n; i++) {
            alpha[i] = 3 * ((a[i + 1] - a[i]) / h[i] - (a[i] - a[i - 1]) / h[i - 1]);
        }
        // solvers for the system of equations
        double[] l = new double[n + 1];
        double[] z = new double[n + 1];
        double[] mu = new double[n + 1];
        l[0] = 2 * h[0];
        mu[0] = 0.5;
        z[0] = alpha[0] / l[0];
        for (int i = 1; i < n; i++) {
            l[i] = 2 * (x[i + 1] - x[i - 1]) - h[i - 1] * mu[i - 1];
            mu[i] = h[i] / l[i];
            z[i] = (alpha[i] - h[i - 1] * z[i - 1]) / l[i];
        }
        // polynomial coefficients solved
        l[n] = h[n - 1] * (2.0 - mu[n - 1]);
        z[n] = (alpha[n] - h[n - 1] * z[n - 1]) / l[n];
        c[n] = z[n];
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
