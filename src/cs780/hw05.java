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

    static class Integrand implements FunctionModel {

        public double compute(double input, double[] equationParams) {
            double x = input;
            double k = equationParams[0];
            return 1.0 / Math.sqrt(1 - Math.pow(k, 2) * Math.pow(Math.sin(x), 2));
        }

        public double dcompute(int derivative, double input, double[] equationParams) {
            return Double.NaN;
        }
    }
    public static double TOL = 1e-15;

    public static void main(String[] args) {

        if (false) {// exercise 4.4
            fresnel();
        }
        if (true) { // exercise 4.6
            // testK
            pendulum();
        }
    }

    public static void testK() {
        for (int i = 0; i < 9; i++) {
            double[] equationParams = new double[]{Math.sin(i * Math.PI / 18), 1.0, 1.0};
            Integrand ig = new Integrand();
            double kterm;
            kterm = AdaptiveQuadrature.aq2(0.0, Math.PI / 2.0, ig, equationParams, TOL, 1000);
            System.out.println(kterm);
        }
    }

    public static void pendulum() {
        double[] equationParams = new double[]{Double.NaN, 1.0, 9.8};
        FunctionModel period_function = new FunctionModel() {
            public double compute(double theta, double[] equationParams) {
                double result = Double.NaN;
                double start = 0.0;
                double end = Math.PI * 0.5;
                double length = equationParams[1];
                double gravity = equationParams[2];
                Integrand integrate_me = new Integrand();
                double kterm;
                equationParams[0] = Math.sin(theta * 0.5);
                kterm = AdaptiveQuadrature.aq2(0.0, Math.PI / 2.0, integrate_me, equationParams, TOL, 1000);
                // System.out.println("kterm:"+kterm);
                result = 4 * Math.sqrt(length / gravity) * kterm;
                return result;
            }

            public double dcompute(int derivative, double input, double[] equationParams) {
                return Double.NaN;
            }
        };

        numutil.Plot.plotdata(period_function, equationParams, Math.PI/180, 0, Math.PI, true);
//        double period = period_function.compute(3 * Math.PI / 18, equationParams);
//        System.out.println(period);
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
