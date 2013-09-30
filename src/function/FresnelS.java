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
public class FresnelS implements FunctionModel {

    //public static boolean has_pi = false; // is our function sin(t^2) when the table is sin(pi/2*t^2)?
    public class sinhelper implements FunctionModel {

        public double compute(double input, double[] equationParams) {
            return Math.sin(Math.PI * 0.5 * input * input);
        }

        public double dcompute(int derivative, double input, double[] equationParams) {
            return Math.PI * input * Math.cos(input * input);
        }
    }

    public double dcompute(int derivative, double input, double[] equationParams) {
        double TOL = 1e-15;
        int max_subdivision = 1000;
        FunctionModel sh = new sinhelper();
        double result = AdaptiveQuadrature.aq2(0.0, input, sh, equationParams, TOL, max_subdivision);
        System.out.println(result * Math.sqrt(2 / Math.PI));

        return Double.NaN;
    }

    public double unstablecompute(double input, double[] equationParams) {
        double TOL = 1e-15;
        int max_subdivision = 1000;
        FunctionModel sh = new sinhelper();
        double result = Double.NaN;
        for (int i = 8; i < 30; i++) {
            result = Integral.newtoncotes(true, i, 0.0, input, sh, equationParams);
            System.out.println(result * Math.sqrt(2 / Math.PI));
        }
        return result;
    }

    public double compute(double input, double[] equationParams) {
        double TOL = 1e-15;
        int max_subdivision = 1000;
        FunctionModel sh = new sinhelper();
        double result = AdaptiveQuadrature.aq2(0.0, input, sh, equationParams, TOL, max_subdivision);
        //System.out.println(result);
        return result;
    }

    public static void main(String[] args) {
        FresnelS fs = new FresnelS();
        fs.compute(2, new double[]{});
    }
}
