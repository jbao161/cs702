/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SolutionOfSingleVariableEq;

/**
 *
 * @author jbao
 */
public class ModelPolynomial implements FunctionModel {

    public double computeFunction(double x, double[] polyCoefs) {
        double result = 0;
        for (int i = 0; i < polyCoefs.length; i++) {
            result += polyCoefs[i] * Math.pow(x, i);
        }
        return result;
    }
}
