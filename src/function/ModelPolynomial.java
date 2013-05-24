/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

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
    public double dcompute(int derivative, double x, double[] polyCoefs) {
        double result = 0;
        for (int i = 0; i < polyCoefs.length; i++) {
            result += polyCoefs[i] * numutil1.MathTools.factorial(i) * Math.pow(x, Math.max(0, i - derivative));
        }
        return result;
    }
}
