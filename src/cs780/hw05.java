/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

import calculus.AdaptiveQuadrature;
import function.FresnelC;
import function.FresnelS;
import function.FunctionModel;

/**
 *
 * @author jbao
 */
public class hw05 {

    public static double TOL = 1e-15;

    public static void main(String[] args) {

        if (false) {// exercise 4.4
            fresnel();
        }
        if (true) { // exercise 4.6
            pendulum();
        }
    }

    public static void pendulum() {

        FunctionModel period_function = new FunctionModel() {
            class Integrand implements FunctionModel {

                public double compute(double x, double[] equationParams) {
                    double k = equationParams[0];
                    return Math.sqrt(1 - Math.pow(k, 2) * Math.pow(Math.sin(x), 2));
                }

                public double dcompute(int derivative, double input, double[] equationParams) {
                    return Double.NaN;
                }
            }

            public double compute(double input, double[] equationParams) {
                double result = Double.NaN;
                double start = 0.0;
                double end = Math.PI * 0.5;
                Integrand integrate_me = new Integrand();
                equationParams[0] = input;
                result = AdaptiveQuadrature.aq2(start, end, integrate_me, equationParams, TOL, 10000);
                return result;
            }

            public double dcompute(int derivative, double input, double[] equationParams) {
                return Double.NaN;
            }
        };
        double[] period_params = {1,1,1};
      period_function.compute(0.0, period_params);
    }

    public static void fresnel() {
        FunctionModel intensity = new FunctionModel() {
            public double compute(double input, double[] equationParams) {
                FresnelC fc = new FresnelC();
                FresnelS fs = new FresnelS();
                return 0.5 * (Math.pow(fc.compute(input, equationParams) + 0.5, 2) + Math.pow(fs.compute(input, equationParams) + 0.5, 2));
            }

            public double dcompute(int derivative, double input, double[] equationParams) {
                return Double.NaN;
            }
        };
        double plotmin = 0;
        double plotmax = 10;
        double result = Double.NaN;
        double increment = .1;
        for (double i = plotmin; i < plotmax; i += increment) {
            result = intensity.compute(i, new double[]{});
            System.out.println(i + "," + result);
        }


        // numutil.Plot.plot(intensity, new double[]{},0,-5,5, true);
    }
}
