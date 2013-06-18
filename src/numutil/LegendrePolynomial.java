/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package numutil;

import function.FunctionModel;

/**
 *
 * @author jbao
 */
public class LegendrePolynomial {
    /*
     * solution to Legendre's differential equation: 0 = d/dx[(1-x^2)d/dx[P_n(x)]+ n*(n+1)*P_n(x)
     * converges for -1<x<1. for integer n, the solution is a polynomial.
     * P_n(x) = 1/(2^n*n!)*(d/dx)^n[(x^2-1)^n]
     * They are also coefficients in the taylor expansion of (1-2*x*t+t^2)^(-1/2) = Sum[P_n(x)*t^n], over n from 0 to infinity.
     * recursive definition: (n+1)*P_n+1(x) = (2*n+1)*x*P_n(x) - n*P_(n-1)(x)
     * binomial definition: Sum[ n_C_k * (-n-1)_C_k * ((1-x) / 2)^k], for k from 0 to n
     * = 2^n*Sum[x^k* n_C_k * ((n+k-1)/2)_C_n
     */

    /*
     * okay for n <= 100. then 1. calculation is too slow and 2. numbers approach machine infinity
     */
    public static Polynomial derivDefinition(int n) {
        Polynomial pair = new Polynomial(new double[]{-1, 0, 1}); // (x^2-1)
        Polynomial result = new Polynomial(new double[]{1}); // initial value equals 1
        for (int i = 0; i < n; i++) {
            result = result.times(pair); // after iterations result is (x^2-1)^n
        }
        result = result.differentiate(n); // (d/dx)^n[(x^2-1)^n]
        double coef = 1.0 / (Math.pow(2, n) * MathTools.factorial(n));
        result = result.times(coef);
        return result;
    }

    /*
     * ok for about n = 30 before calculation takes too long
     */
    public static Polynomial recursiveDefinition(int n) {
        if (n < 0) {
            return null;
        }
        Polynomial result = new Polynomial(new double[]{1});
        if (n == 0) {
            return result; // 1
        }
        if (n == 1) {
            return result.antiderivative(1); // x
        }
        Polynomial[] p = new Polynomial[2];
        //(n+1)*P_n+1(x) = (2*n+1)*x*P_n(x) - n*P_(n-1)(x)
        p[0] = recursiveDefinition(n - 1).times(new Polynomial(new double[][]{{1, 1}})).times(2 * n - 1.0);
        p[1] = recursiveDefinition(n - 2).times(n - 1.0);
        return (p[0].subtract(p[1])).divide(n);
    }

    /*
     * not finished
     */
    public static Polynomial binomialDefinition(int n) {
        /* binomial definition: Sum[ n_C_k * (-n-1)_C_k * ((1-x) / 2)^k], for k from 0 to n
         = 2^n*Sum[x^k* n_C_k * ((n+k-1)/2)_C_n
         */
        Polynomial result = new Polynomial();
        double[] binom = new double[2];
        for (int k = 0; k <= n; k++) {
            binom[0] = MathTools.binomial(n, k);
            binom[1] = MathTools.binomial(-n - 1, k);
        }
        return result;
    }
    // starting with n = 2, the root of the legendre polynomial and the corresponding coef = Integral[Product[(x-xj)/(xi-xj)]dx], over x from -1 to 1 where xn are the roots of the nth legendre polynomial
    public static double[][][] rootCoef = new double[][][]{
        {{0.5773502692, 1.0}, {-0.5773502692, 1.0}},
        {{0.7745966692, 0.5555555556}, {0.0, 0.8888888889}, {-0.7745966692, 0.5555555556}},
        {{0.8611363116, 0.3478548451}, {0.3399810436, 0.6521451549}, {-0.3399810436, 0.6521451549}, {-0.8611363116, 0.3478548451}},
        {{0.9061798459, 0.2369268850}, {0.5384693101, 0.4786286705}, {0.0, 0.5688888889}, {-0.5384693101, 0.4786286705}, {-0.9061798459, 0.2369268850}}
    };

    public static double gaussquad(FunctionModel fx, double[] equationParams, int n, double x1, double x2) {
        if (n < 2 || n >= rootCoef.length + 2) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        double[][] tablelookup = rootCoef[n - 2];
        double result = 0;
        double root, coef, function;
        for (int i = 0; i < tablelookup.length; i++) {
            root = tablelookup[i][0];
            coef = tablelookup[i][1];
            root = ((x2 - x1) * root + (x2 + x1)) / 2.0; // transform integral from x2 to x1 into integral from -1 to 1
            function = fx.compute(root, equationParams);
            function *= (x2 - x1) / 2.0; // transform integral from x2 to x1 into integral from -1 to 1
            result += coef * function;
        }
        return result;
    }
}
