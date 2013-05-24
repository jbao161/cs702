/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beziercurve;

import numutil.Polynomial;

/**
 *
 * @author jbao
 */
public class BezierPolynomial extends numutil.Polynomial {

    /**
     * Sum(i=0 to n) {x_i * B(i,n)(t)}
     * where x_i is the x coordinate of a control point for the Bezier Curve, B(i,n)(t) is a Bernstein polynomial, and n equals the number of points - 1
     * this polynomial gives the function x(t) for the x coordinate of a Bezier curve parameterized with t:[0,1]
     * @param controlCoordinates 
     */
    public BezierPolynomial(double[] controlCoordinates) {
        Polynomial result = new Polynomial();
        int n = controlCoordinates.length - 1;
        BernsteinPolynomial bp;
        for (int i = 0; i <= n; i++) {
            bp = new BernsteinPolynomial(i, n);
            result = result.add(bp.times(controlCoordinates[i]));
        }
        become(result);
    }
}
