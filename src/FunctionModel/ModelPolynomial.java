/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionModel;

/**
 *
 * @author jbao
 */
public class ModelPolynomial implements FunctionModel {

    @Override
    public double compute(double x, double[] polyCoefs) {
        double result = 0;
        for (int i = 0; i < polyCoefs.length; i++) {
            result += polyCoefs[i] * Math.pow(x, i);
        }
        return result;
    }

    @Override
    public double dcompute(double x, double[] polyCoefs) {
        double result = 0;
        for (int i = 1; i < polyCoefs.length; i++) {
            result += polyCoefs[i] * Math.pow(x, i - 1);
        }
        return result;
    }
}
