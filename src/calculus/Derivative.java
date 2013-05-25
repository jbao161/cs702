/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus;

import function.FunctionModel;

/**
 *
 * @author jbao
 */
public class Derivative {

    public static final double STEP = 10e-3;

    public static double endpoint3(double x, FunctionModel f, double[] equationParams, double stepSize) {
        double fx0 = f.compute(x, equationParams);
        double fx1 = f.compute(x + stepSize, equationParams);
        double fx2 = f.compute(x + 2 * stepSize, equationParams);
        return (-3 * fx0 + 4 * fx1 - fx2) / (2 * stepSize);
    }

    public static double midpoint3(double x, FunctionModel f, double[] equationParams, double stepSize) {
        double fx1 = f.compute(x + stepSize, equationParams);
        double fx2 = f.compute(x - stepSize, equationParams);
        return (fx2 - fx1) / (2 * stepSize);
    }

    public static double midpoint5(double x, FunctionModel f, double[] equationParams, double stepSize) {
        double fx1 = f.compute(x - 2 * stepSize, equationParams);
        double fx2 = f.compute(x - stepSize, equationParams);
        double fx3 = f.compute(x + stepSize, equationParams);
        double fx4 = f.compute(x + 2 * stepSize, equationParams);
        return (fx1 - 8 * fx2 + 8 * fx3 - fx4) / (12 * stepSize);
    }

    public static double endpoint5(double x, FunctionModel f, double[] equationParams, double stepSize) {
        double fx1 = f.compute(x, equationParams);
        double fx2 = f.compute(x + stepSize, equationParams);
        double fx3 = f.compute(x + 2 * stepSize, equationParams);
        double fx4 = f.compute(x + 3 * stepSize, equationParams);
        double fx5 = f.compute(x + 4 * stepSize, equationParams);
        return (-25 * fx1 + 48 * fx2 - 36 * fx3 + 16 * fx4 - 3 * fx4) / (12 * stepSize);
    }
}
