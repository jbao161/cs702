/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

/**
 *
 * @author jbao
 */
public class Gamma {

    public double factorial(double input) {
        int result = 0;
        for (int i = 1; i <= input; i++) {
            result *= i;
        }
        return result;
    }

    public double compute(double input, double[] equationParams) {
        return factorial(input-1);
    }

    public double dcompute(int derivative, double input, double[] equationParams) {
        return Double.NaN;
    }
}
