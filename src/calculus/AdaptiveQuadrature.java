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
public class AdaptiveQuadrature {

    /* adaptive quadrature method using simpson's rule:
     * to reduce the error term in an approximate integration using polynomials,
     * we can predict the sufficiently small step size required.
     * 
     * using a smaller step size reduces the coefficient of the error term.
     * assuming that the f(x1) and f(x2) of the total interval approximation and reduced step size approximation are approximately equal,
     * for simpson's rule, if we reduce the step size by two, the error coeffficient h^n is reduced to h^n / 2^(n-1).
     * 
     * this method 
     */
    public static double aq(double x1, double x2, FunctionModel f, double[] equationParams, double TOL, int limit) {
        double result = 0;
        int n = (int)20000;
        double[] tol = new double[n];
        double[] a = new double[n];
        double[] h = new double[n];
        double[] fa = new double[n];
        double[] fc = new double[n];
        double[] fb = new double[n];
        double[] s = new double[n];
        double[] l = new double[n];


        int i = 1; // approximation from simpsons's method for entire interval
        while (i > 0) {
            tol[i] = 10 * TOL;
            a[i] = x1;
            h[i] = (x2 - x1) / 2;
            fa[i] = f.compute(x1, equationParams);
            fc[i] = f.compute(x1 + h[i], equationParams);
            fb[i] = f.compute(x2, equationParams);
            s[i] = h[i] * (fa[i] + 4 * fc[i] + fb[i]) / 3;
            l[i] = 1; // limit level

            double fd = f.compute(x1 + h[i] / 2, equationParams);
            double fe = f.compute(x1 + 3 * h[i] / 2, equationParams);
            double s1 = h[i] * (fa[i] + 4 * fd + fc[i]) / 6; // approximations from simpson's method for half subinterval
            double s2 = h[i] * (fc[i] + 4 * fd + fc[i]) / 6;
            // store the f(x) values for the ith halfing of subintervals
            double v1 = a[i];
            double v2 = fc[i];
            double v3 = fc[i];
            double v4 = fb[i];
            double v5 = h[i];
            double v6 = tol[i];
            double v7 = s[i];
            double v8 = l[i];

            // if each part of the subinterval meets the subdivided tolerance, sum all parts and return the value
            i--;
            if (Math.abs(s1 + s2 - v7) < v6) {
                result += (s1 + s2);
            } else {// if one of the parts exceeds tolerance, divide it in half and repeat
                if (v8 >= limit) {
                    return Double.NaN; // limit level exceeded. procedure fails.
                } else { // add one level
                    // right half subinterval
                    i++;
                    a[i] = v1 + v5;
                    fa[i] = v3;
                    fc[i] = fe;
                    fb[i] = v4;
                    h[i] = v5 / 2;
                    tol[i] = v6 / 2;
                    s[i] = s2;
                    l[i] = v8 + 1;
                    // left half subinterval
                    i++;
                    a[i] = v1;
                    fa[i] = v2;
                    fc[i] = fd;
                    fb[i] = v3;
                    h[i] = h[i - 1];
                    tol[i] = tol[i - 1];
                    s[i] = s1;
                    l[i] = l[i - 1];
                }
            }
        }
        // approximates integral to within TOL
        return result;
    }
}
