/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SolutionOfSingleVariableEq;

/**
 *
 * @author jbao
 */
public class FunctionModel {

    public static final boolean DEBUG = true;

    public static double computeFunction(String functionName, double input, double[] equationParams) {
        if (functionName.equalsIgnoreCase("exponential")) {
            return expGrowth(input, equationParams);
        }
        if (functionName.equalsIgnoreCase("polynomial")) {
            return polynomial(input, equationParams);
        }
        if (DEBUG) {
            // if no function exists, return NaN
            System.out.println("ERROR: specified function does not exist!");
        }

        return Double.NaN;
    }

    public static double expGrowth(double birthRate, double[] growthParams) {
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

    public static double polynomial(double x, double[] polyCoefs) {
        double result = 0;
        for (int i = 0; i < polyCoefs.length; i++) {
            result += polyCoefs[i] * Math.pow(x, i);
        }
        return result;
    }
}
