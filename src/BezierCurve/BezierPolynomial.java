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
public class BezierPolynomial extends Util.Polynomial {

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
