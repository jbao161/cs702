/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus;

import function.FunctionModel;
import interpolatelagrange.LagrangePolynomial;
import numutil.Polynomial;

/**
 *
 * @author jbao
 */
public class Integral {
    /**
     * Newton-Cotes integration formula for (n+1) points. uses Lagrange
     * interpolating polynomial to approximate the integral. uses the end points
     * of the specified domain interval.
     *
     * @param useEndPoints true = closed formula, includes end points. false = open formula, use only interior points.
     * @param n the order of the formula
     * @param x1 lower bound of integration domain
     * @param x2 upper bound of integration domain
     * @param function the function to be integrated
     * @param equationParams the parameters, if applicable, of the function (ex:
     * coefficients of a polynomial)
     * @return a numerical value for the definite integral of the function from
     * x1 to x2
     */
    public static double newtoncotes(boolean useEndpoints, int n, double x1, double x2, FunctionModel function, double[] equationParams) {
        double xi;
        double start = x1;
        double stepSize = (x2 - x1) / n;;
        // use only interior points for open formula
        if (useEndpoints == false) {
            stepSize = (x2 - x1) / (n + 2);
            start = x1 + stepSize;
        }
        // find the x points and compute the fx values
        double[][] xfx = new double[n + 1][2];
        for (int i = 0; i < n + 1; i++) {
            xi = start + stepSize * i;
            xfx[i][0] = xi;
            xfx[i][1] = function.compute(xi, equationParams);
        }
        // construct the lagrange interpolating polynomial
        Polynomial p = LagrangePolynomial.interpolate(xfx);
        // integrate the lagrange polynomial from x1 to x2
        return p.integrate(x1, x2);
    }

    /**
     * closed Newton-Cotes integration formula for (n+1) points. uses Lagrange
     * interpolating polynomial to approximate the integral. uses the end points
     * of the specified domain interval.
     *
     * @param n the order of the formula
     * @param x1 lower bound of integration domain
     * @param x2 upper bound of integration domain
     * @param function the function to be integrated
     * @param equationParams the parameters, if applicable, of the function (ex:
     * coefficients of a polynomial)
     * @return a numerical value for the definite integral of the function from
     * x1 to x2
     */
    public static double newtoncn(int n, double x1, double x2, FunctionModel function, double[] equationParams) {
        double xi;
        double stepSize = (x2 - x1) / n;
        // find the x points and compute the fx values
        double[][] xfx = new double[n + 1][2];
        for (int i = 0; i < n + 1; i++) {
            xi = x1 + stepSize * i;
            xfx[i][0] = xi;
            xfx[i][1] = function.compute(xi, equationParams);
        }
        // construct the lagrange interpolating polynomial
        Polynomial p = LagrangePolynomial.interpolate(xfx);
        // integrate the lagrange polynomial from x1 to x2
        return p.integrate(x1, x2);
    }

    /**
     * open Newton-Cotes integration formula for (n+1) points. uses Lagrange
     * interpolating polynomial to approximate the integral. does not use the
     * end points of the specified domain interval, only points of its interior.
     *
     * @param n the order of the formula
     * @param x1 lower bound of integration domain
     * @param x2 upper bound of integration domain
     * @param function the function to be integrated
     * @param equationParams the parameters, if applicable, of the function (ex:
     * coefficients of a polynomial)
     * @return a numerical value for the definite integral of the function from
     * x1 to x2
     */
    public static double newtonon(int n, double x1, double x2, FunctionModel function, double[] equationParams) {
        double xi;
        double stepSize = (x2 - x1) / (n + 2);
        // find the x points and compute the fx values
        double[][] xfx = new double[n + 1][2];
        for (int i = 0; i < n + 1; i++) {
            xi = x1 + stepSize * (i + 1);
            xfx[i][0] = xi;
            xfx[i][1] = function.compute(xi, equationParams);
        }
        // construct the lagrange interpolating polynomial
        Polynomial p = LagrangePolynomial.interpolate(xfx);
        // integrate the lagrange polynomial from x1 to x2
        return p.integrate(x1, x2);
    }

    /*
     * manually defined low order Newton-Cortes formulas used to double check the general formula
     */
    public static double newtonc1(double x1, double x2, FunctionModel fx, double[] equationParams) {
        double stepSize = x2 - x1;
        return stepSize / 2.0 * (fx.compute(x2, equationParams) + fx.compute(x1, equationParams));
    }    // trapezoidal rule: exact for linear function

    public static double newtonc2(double x1, double x2, FunctionModel fx, double[] equationParams) {
        double stepSize = (x2 - x1) / 2.0;
        double fx1 = fx.compute(x1, equationParams);
        double fx2 = fx.compute(x1 + stepSize, equationParams);
        double fx3 = fx.compute(x2, equationParams);
        return stepSize / 3.0 * (fx1 + 4.0 * fx2 + fx3);
    }    // simpson's rule: exact for three degree polynomials

    public static double newtonc3(double x1, double x2, FunctionModel fx, double[] equationParams) {
        double stepSize = (x2 - x1) / 3.0;
        double fx1 = fx.compute(x1, equationParams);
        double fx2 = fx.compute(x1 + stepSize, equationParams);
        double fx3 = fx.compute(x1 + stepSize * 2.0, equationParams);
        double fx4 = fx.compute(x2, equationParams);
        return 3.0 * stepSize / 8.0 * (fx1 + 3.0 * fx2 + 3.0 * fx3 + fx4);
    }

    public static double newtonc4(double x1, double x2, FunctionModel fx, double[] equationParams) {
        double stepSize = (x2 - x1) / 4.0;
        double fx1 = fx.compute(x1, equationParams);
        double fx2 = fx.compute(x1 + stepSize, equationParams);
        double fx3 = fx.compute(x1 + stepSize * 2.0, equationParams);
        double fx4 = fx.compute(x1 + stepSize * 3.0, equationParams);
        double fx5 = fx.compute(x1 + stepSize * 4.0, equationParams);
        return 2.0 * stepSize / 45.0 * (7.0 * fx1 + 32.0 * fx2 + 12.0 * fx3 + 32.0 * fx4 + 7.0 * fx5);
    }

    public static double newtono0(double x1, double x2, FunctionModel fx, double[] equationParams) {
        double stepSize = (x2 - x1) / (2.0);
        double fx1 = fx.compute(x1 + stepSize, equationParams);
        return 2.0 * stepSize * fx1;
    }

    public static double newtono1(double x1, double x2, FunctionModel fx, double[] equationParams) {
        double stepSize = (x2 - x1) / 3.0;
        double fx1 = fx.compute(x1 + stepSize, equationParams);
        double fx2 = fx.compute(x1 + 2.0 * stepSize, equationParams);
        return 3.0 * stepSize / 2.0 * (fx1 + fx2);
    }

    public static double newtono2(double x1, double x2, FunctionModel fx, double[] equationParams) {
        double stepSize = (x2 - x1) / 4.0;
        double fx1 = fx.compute(x1 + stepSize, equationParams);
        double fx2 = fx.compute(x1 + 2.0 * stepSize, equationParams);
        double fx3 = fx.compute(x1 + 3.0 * stepSize, equationParams);
        return 4.0 * stepSize / 3.0 * (2.0 * fx1 - fx2 + 2.0 * fx3);
    }

    public static double newtono3(double x1, double x2, FunctionModel fx, double[] equationParams) {
        double stepSize = (x2 - x1) / 5.0;
        double fx1 = fx.compute(x1 + stepSize, equationParams);
        double fx2 = fx.compute(x1 + 2.0 * stepSize, equationParams);
        double fx3 = fx.compute(x1 + 3.0 * stepSize, equationParams);
        double fx4 = fx.compute(x1 + 4.0 * stepSize, equationParams);
        return 5.0 * stepSize / 24.0 * (11.0 * fx1 + fx2 + fx3 + 11.0 * fx4);

    }

    public static double compositec(int n, double x1, double x2, FunctionModel fx, double[] equationParams) {
        double stepSize = (x2 - x1) / n;

        return Double.NaN;
    }
}
