/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package numutil;

/**
 *
 * @author jbao
 */
public class LegendrePolynomial extends Polynomial {
    /*
     * solution to Legendre's differential equation: 0 = d/dx[(1-x^2)d/dx[P_n(x)]+ n*(n+1)*P_n(x)
     * converges for -1<x<1. for integer n, the solution is a polynomial.
     * P_n(x) = 1/(2^n*n!)*(d/dx)^n[(x^2-1)^n]
     * They are also coefficients in the taylor expansion of (1-2*x*t+t^2)^(-1/2) = Sum[P_n(x)*t^n], over n from 0 to infinity.
     * recursive definition: (n+1)*P_n+1(x) = (2*n+1)*x*P_n(x) - n*P_(n-1)(x)
     * binomial definition: Sum[ n_C_k * (-n-1)_C_k * (1-x) / 2]
     */
    /*
     * okay for n<=100. then 1. calculation is too slow and 2. numbers approach machine infinity
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
        p[1] = recursiveDefinition(n - 2).times(n-1.0);
        return (p[0].subtract(p[1])).divide(n);
    }
}
