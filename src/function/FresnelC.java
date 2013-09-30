/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

import calculus.AdaptiveQuadrature;
import calculus.Integral;

/**
 *
 * @author jbao
 */
public class FresnelC implements FunctionModel {

    public class coshelper implements FunctionModel {

        public double compute(double input, double[] equationParams) {
            return Math.cos(Math.PI * 0.5 * input * input);
        }

        public double dcompute(int derivative, double input, double[] equationParams) {
            return -2 * input * Math.sin(input * input);
        }
    }

    public double compute(double input, double[] equationParams) {
        double TOL = 1e-15;
        int max_subdivision = 1000;
        FunctionModel sh = new coshelper();
        double result = AdaptiveQuadrature.aq2(0.0, input, sh, equationParams, TOL, max_subdivision);
      //  System.out.println(result);
        return result;
    }

    public double dcompute(int derivative, double input, double[] equationParams) {
        return Double.NaN;
    }

    public static void main(String[] args) {
        FresnelC fc = new FresnelC();
        fc.compute(2, new double[]{});
    }
}
