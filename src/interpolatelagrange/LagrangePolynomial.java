/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpolatelagrange;

import numutil.Pair;
import numutil.Polynomial;
import function.FunctionModel;

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

    /* comments:
     * 1. each x value must be distinct; there cannot be replicate entries in the data because of divide by zero error. can be modified to screen out replicate pairs, but then there is a loss of terms which I don't know whether will affect the outcome of the polynomial. seems it would be okay, may need to account for the end power of the polynomial. 
     * 2. the error term = f(n+1 th derivative) (x0)/ (n+1)! * Product(i=0 to n)(x-xi), where n+1 is the number of points
     */
}
