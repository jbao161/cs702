/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package solvermethods;

import function.FunctionModel;
import function.ModelPolynomial;
import static solvermethods.MethodSteffensen.DEBUG;

/**
 *
 * @author jbao
 */
public class MethodHorner {

    public static final boolean DEBUG = true;

    public double[] solve(Object[] args) {
        FunctionModel function = new ModelPolynomial();
        double x = (Double) args[1];

        double[] polyCoefs = (double[]) args[1];
        int degree = polyCoefs.length;

        double y = polyCoefs[polyCoefs.length];
        double z = Double.valueOf(y);

        for (int i = degree - 1; i > 0; i--) {
            y = x * y + polyCoefs[i];
            z = x * z + y;
        }
        y = x * y + polyCoefs[0];


        return new double[]{y, z};
    }
    /* comments:
     * 1. used for polynomial function only!
     */
}
