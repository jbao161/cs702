/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

import calculus.AdaptiveQuadrature;
import calculus.Integral;
import function.FresnelC;
import function.FresnelS;
import function.FunctionModel;
import numutil.Polynomial;

/**
 *
 * @author jbao
 */
public class hw05 {

    static class Integrand implements FunctionModel {

        public double compute(double x, double[] equationParams) {
            double k = equationParams[0];
            return 1.0 / Math.sqrt(1 - Math.pow(k, 2) * Math.pow(Math.sin(x), 2));
        }

        public double dcompute(int derivative, double input, double[] equationParams) {
            return Double.NaN;
        }
    }
    public static double TOL = 1e-5;

    public static void main(String[] args) {

        if (false) {// exercise 4.4
            fresnel();
        }
        if (false) { // exercise 4.6
            pendulum();
            double[] equationParams = new double[]{1.0, 1.0, 1.0};
            Integrand ig = new Integrand();
            double kterm = Integral.newtoncotes(false, 19, 0.0, Math.PI / 2, ig, equationParams);
            System.out.println(kterm);
//            for (int i = 0; i < 5; i++) {
//                kterm = ig.compute(i, equationParams);
//                       System.out.println(kterm);
//            }
//            kterm = ig.compute(Math.PI/2-0.001, equationParams);
//                       System.out.println(kterm);
        }
        if (false) { // exercise 4.12
            legendre();
        }
        if (true) { // exercise 4.18
            double[][] xpoints = new double[4][];
            xpoints[0] = new double[]{0.58579, 3.4142};
            xpoints[1] = new double[]{0.32255, 1.7458, 4.5366, 9.3951};
            xpoints[2] = new double[]{0.22285, 1.1889, 2.9927, 5.37751, 9.8375, 1.5983};
            xpoints[3] = new double[]{0.17028, 9.0370, 2.2511, 4.2667, 7.0459, 1.0759, 1.5741, 2.2863};
            double[][] wpoints = new double[4][];
            wpoints[0] = new double[]{0.853355, 0.14645};
            wpoints[1] = new double[]{0.60315, 0.35742, 0.03889, 0.0005393};
            wpoints[2] = new double[]{0.45896, 0.417, 0.113, 0.104, 0.000261, 0.000000899};
            wpoints[3] = new double[]{0.369, 0.419, 0.1176, 0.0333, 0.00279, 0.00009, 0.00000085, 0.000000001};
        }
    }

    public static void legendre() {
        Polynomial pos = numutil.LegendrePolynomial.derivDefinition(3);
        pos.print();
        double norm = (pos.times(pos)).integrate(-1, 1);
        System.out.println("norm: " + norm);
        System.out.println("normcoef: " + 1 / Math.sqrt(norm));
        Polynomial pos2 = pos.times(1 / Math.sqrt(norm));
        pos2.print();
        System.out.println((pos2.times(pos2)).integrate(-1, 1));

        // gramschmidt
//            double[] equationParams = new double[]{};
//            double bknum = AdaptiveQuadrature.aq2(-1.0, 1.0, bnum, equationParams, TOL, 1000);
//            double bkdenom = AdaptiveQuadrature.aq2(-1.0, 1.0, bdenom, equationParams, TOL, 1000);
//            double cknum = AdaptiveQuadrature.aq2(-1.0, 1.0, cnum, equationParams, TOL, 1000);
//            double ckdenom = AdaptiveQuadrature.aq2(-1.0, 1.0, cdenom, equationParams, TOL, 1000);

        //System.out.println(bknum / bkdenom);
        Polynomial legendre01 = new Polynomial(new double[]{0, Math.sqrt(1.5)});
        //legendre01.print();
        Polynomial legendre02 = new Polynomial(new double[]{-0.5 * Math.sqrt(2.5), 0, 1.5 * Math.sqrt(2.5)});
        //legendre02.print();
        double legendre01I = legendre01.times(legendre01).times(new Polynomial(new double[]{0, 1})).integrate(-1, 1);
        Polynomial Bk = legendre01.times(legendre01).times(new Polynomial(new double[]{0, 1}));
        double legendre02I = legendre02.times(legendre02).integrate(-1, 1);
        double coef1a = legendre01I / legendre02I;
        System.out.println(coef1a);
        //legendre02.times(legendre02).print();
        Polynomial lhs = legendre02.times((new Polynomial(new double[]{0, 1})).subtract(Bk)); // left term
        lhs.print();
        // find c3
        Polynomial rhs = new Polynomial(new double[]{0, 1});
        rhs = rhs.times(legendre01).times(legendre02); // this is the top integrand of c3
        double coefrhsnum = rhs.integrate(-1, 1);
        double coefrhsdenom = (legendre01.times(legendre01)).integrate(-1, 1);
        double c3 = coefrhsnum / coefrhsdenom;
        Polynomial phi = lhs.subtract(legendre01.times(c3));
        System.out.println("phi");
        phi.print();

        double norm2 = phi.times(phi).integrate(-1, 1);
        System.out.println("norm2: " + norm2);
        System.out.println(1 / Math.sqrt(norm2));
        phi.times(1 / Math.sqrt(norm2)).print();

//            Polynomial coef1 = new Polynomial(new double[]{-bknum / bkdenom, 1});
//            double coef2 = cknum / ckdenom;
//            Polynomial result = coef1.times(legendre01).subtract(legendre02.times(coef2));
//            result.print();
    }
    static FunctionModel phi02 = new FunctionModel() {
        public double compute(double x, double[] equationParams) {
            return Math.sqrt(2.5) * (3 * Math.pow(x, 2) - 1) * 0.5;
        }

        public double dcompute(int derivative, double input, double[] equationParams) {
            return Double.NaN;
        }
    };
    static FunctionModel phi01 = new FunctionModel() {
        public double compute(double x, double[] equationParams) {
            return Math.sqrt(1.5) * x;
        }

        public double dcompute(int derivative, double input, double[] equationParams) {
            return Double.NaN;
        }
    };
    static FunctionModel bnum = new FunctionModel() {
        public double compute(double x, double[] equationParams) {
            return x * Math.pow(phi01.compute(x, equationParams), 2);
        }

        public double dcompute(int derivative, double input, double[] equationParams) {
            return Double.NaN;
        }
    };
    static FunctionModel bdenom = new FunctionModel() {
        public double compute(double x, double[] equationParams) {
            return Math.pow(phi02.compute(x, equationParams), 2);
        }

        public double dcompute(int derivative, double input, double[] equationParams) {
            return Double.NaN;
        }
    };
    static FunctionModel cnum = new FunctionModel() {
        public double compute(double x, double[] equationParams) {
            return x * phi02.compute(x, equationParams) * phi01.compute(x, equationParams);
        }

        public double dcompute(int derivative, double input, double[] equationParams) {
            return Double.NaN;
        }
    };
    static FunctionModel cdenom = new FunctionModel() {
        public double compute(double x, double[] equationParams) {
            return Math.pow(phi01.compute(x, equationParams), 2);
        }

        public double dcompute(int derivative, double input, double[] equationParams) {
            return Double.NaN;
        }
    };

    public static void pendulum() {


        FunctionModel period_function = new FunctionModel() {
            public double compute(double theta, double[] equationParams) {
                double result = Double.NaN;
                double start = 0.0;
                double end = Math.PI * 0.49;
                double k = equationParams[0];
                double length = equationParams[1];
                double gravity = equationParams[2];
                Integrand integrate_me = new Integrand();
                double kterm = AdaptiveQuadrature.aq2(start, end, integrate_me, equationParams, TOL, 10000);
                double deriv = (integrate_me.compute(Math.PI * 0.49, equationParams) - integrate_me.compute(Math.PI * 0.40 - 0.01, equationParams));
                kterm += deriv;
                System.out.println(kterm);
                kterm = 1;
                result = 4 * Math.sqrt(length / gravity) * kterm * Math.sin(theta / 2);
                return result;
            }

            public double dcompute(int derivative, double input, double[] equationParams) {
                return Double.NaN;
            }
        };
        double[] period_params = {1, 1, 10};
        double period = period_function.compute(Math.PI / 2, period_params);
        System.out.println(period);


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
