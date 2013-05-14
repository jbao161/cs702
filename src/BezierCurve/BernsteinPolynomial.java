/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BezierCurve;

import Util.Polynomial;

/**
 *
 * @author jbao
 */
public class BernsteinPolynomial {

    public static Polynomial bernstein(int i, int n) {
        Polynomial result = new Polynomial(new double[]{1, 0});// start the bernstein polynomial at constant = 1
        // (1-t) ^ n - i 
        Polynomial singlePair = new Polynomial(new double[]{1.0, -1.0}); // set the polynomial (1-t)
        for (int k = 0; k < n-i; k++) {
            result = result.times(singlePair);
        }
        // binomial coefficient
        double binomCoef = Util.MathTools.binomial(n, i);
        // nCi * t^i
        Polynomial tPow = new Polynomial(new double[][]{{i, binomCoef}});
        result = result.times(tPow);
        return result;
    }
}
