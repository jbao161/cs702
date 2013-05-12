/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionModel;

/**
 *
 * @author jbao
 */
public class ModelExpGrowth implements FunctionModel {

    public double compute(double birthRate, double[] growthParams) {
        // input variable
        double lambda = birthRate;
        if (lambda == 0) {
            return Double.NaN; // divide by zero error
        }
        // logistic growth parameters
        double n0 = growthParams[0];
        double t = growthParams[1];
        double v = growthParams[2];
        double c0 = growthParams[3];

        // function calculation
        double result = n0 * Math.exp(lambda * t) + v / lambda * (Math.exp(lambda * t) - 1) + c0;

        return result;
    }

    public double dcompute(int derivative,double input, double[] equationParams) {
        return Double.NaN;
    }
}
