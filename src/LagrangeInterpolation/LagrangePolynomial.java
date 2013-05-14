/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LagrangeInterpolation;

import FunctionModel.FunctionModel;

/**
 *
 * @author jbao
 */
public class LagrangePolynomial {

    public static final boolean DEBUG = false;

    public static Polynomial interpolate(double[][] data) {
        Polynomial singleTerm = null;
        Polynomial result = new Polynomial();
        Polynomial singleTermPoly = null;

        // the numerator is a polynomial term
        for (int i = 0; i < data.length; i++) {
            // f(x)
            double y = data[i][1];
            double denom = 1.0;
            // polynomial term
            singleTermPoly = new Polynomial(new double[]{1}); // constant = 1
            for (int k = 0; k < data.length; k++) {
                if (k != i) {
                    // Ln,k(x) polynomial associated with a single f(x) term
                    double a = data[k][0];
                    Pair pair = new Pair(a);
                    singleTermPoly = singleTermPoly.times(pair);
                    // the denominator is a product of constants
                    denom *= data[i][0] - a;
                }
            }
            // divide the numerator by denominator to get the joint term 
            singleTermPoly = singleTermPoly.times(y / denom);
            result = result.add(singleTermPoly);
        }
        if (DEBUG) {
            singleTermPoly.print();
        }
        return result;
    }
}
