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

    static double[] null_params = new double[]{};

    static class Integrand implements FunctionModel {

        public double compute(double x, double[] equationParams) {
            double k = equationParams[0];
            return 1.0 / Math.sqrt(1 - Math.pow(k, 2) * Math.pow(Math.sin(x), 2));
        }

        public double dcompute(int derivative, double input, double[] equationParams) {
            return Double.NaN;
        }
    }

    static class physicsfunc implements FunctionModel {

        public double compute(double x, double[] equationParams) {
            double y = equationParams[0];
            double xp = equationParams[1];
            double yp = equationParams[2];
            double term1 = Math.pow(x - xp, 2);
            double term2 = Math.pow(y - yp, 2);
            double denom = Math.sqrt(term1 + term2);

            return 1 / denom;
        }

        public double dcompute(int derivative, double input, double[] equationParams) {
            return Double.NaN;
        }
    }

    public static double compositeSimpson(double x1, double x2, double y1, double y2, int x_steps, int y_steps, final FunctionModel integrand, double[] params) {
        double result = 0;
        int n = x_steps;
        int m = y_steps;
        double h = (x2 - x1) / n;
        double k = (y2 - y1) / m;
        double[] inner_params = new double[8];
        inner_params[0] = x1;
        inner_params[1] = x2;
        inner_params[2] = y1;
        inner_params[3] = y2;
        inner_params[4] = x_steps;
        inner_params[5] = y_steps;
        inner_params[6] = params[0]; //xp
        inner_params[7] = params[1]; //yp

        FunctionModel inner_integral = new FunctionModel() {
            public double compute(double input, double[] params) {
                double x = input;
                double x1 = params[0];
                double x2 = params[1];
                double y1 = params[2];
                double y2 = params[3];

                double x_steps = params[4];
                double y_steps = params[5];
                double xp = params[6];
                double yp = params[7];

                double y3 = 0.5 * (y1 + y2);
                double n = x_steps;
                double m = y_steps;
                double h = (x2 - x1) / n;
                double k = (y2 - y1) / m;
                double[] inner_params = new double[3];
                inner_params[1] = xp;
                inner_params[2] = yp;
                inner_params[0] = y1;
                double f1 = integrand.compute(input, inner_params);
                inner_params[0] = y2;
                double f2 = integrand.compute(input, inner_params);
                inner_params[0] = y3;
                double f3 = integrand.compute(input, inner_params);
                double result = 0.5 * k * (f1 + f2 + 2 * f3);
                return result;
            }

            public double dcompute(int derivative, double input, double[] equationParams) {
                return Double.NaN;
            }
        };

        result = AdaptiveQuadrature.aq2(x1, x2, inner_integral, inner_params, TOL, 100);



        return result;
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
        if (false) { // exercise 4.18
            blackbody();
        }
        if (true) { // exercise 4.22
            doubleintegral2();
        }
    }

    public static void doubleintegral2() {
        for (int i =1;i <11; i++){
        double[] params = new double[]{2*i,2*i};
        FunctionModel pyfunc = new physicsfunc();
        double result = compositeSimpson(-1, 1, -1, 1, 5, 5, pyfunc, params);
           System.out.println(result);
        }
    }

    public static void doubleintegral() {
        FunctionModel y1 = new FunctionModel() {
            public double compute(double input, double[] equationParams) {
                return 0;
            }

            public double dcompute(int derivative, double input, double[] equationParams) {
                return Double.NaN;
            }
        };
        FunctionModel y2 = new FunctionModel() {
            public double compute(double input, double[] equationParams) {
                return 1;
            }

            public double dcompute(int derivative, double input, double[] equationParams) {
                return Double.NaN;
            }
        };
        FunctionModel integrand = new FunctionModel() {
            public double compute(double input, double[] params) {
                double x = input;
                double y1 = params[0];
                double y2 = params[1];
                double y3 = 0.5 * (y1 + y2);
                return 4;
            }

            public double dcompute(int derivative, double input, double[] equationParams) {
                return Double.NaN;
            }
        };
        double integral = calculus.MultipleIntegral.simpsonsDouble(0, 1, 2, 2, y1, y2, integrand, null_params);
        // I[I[4]] = 4
        System.out.println(integral);
    }

    public static void blackbody() {
        int num_points = 4;
        double[][] xpoints = new double[num_points][];
        xpoints[0] = new double[]{0.58579, 3.4142};
        xpoints[1] = new double[]{0.322547689619, 1.74576110116, 4.53662029692, 9.3950709123};
        xpoints[2] = new double[]{0.222846604179, 1.18893210167, 2.99273632606, 5.7751435691, 9.83746741838, 15.9828739806};
        xpoints[3] = new double[]{0.170279632305, 0.903701776799, 2.25108662987, 4.26670017029, 7.04590540239, 10.7585160102, 15.7406786413, 22.8631317369};
        double[][] wpoints = new double[num_points][];
        wpoints[0] = new double[]{0.853355, 0.14645};
        wpoints[1] = new double[]{0.603154104342, 0.357418692438, 0.038887908515, 0.00053929470556};
        wpoints[2] = new double[]{0.45896467395, 0.417000830772, 0.113373382074, 0.0103991974531, 0.000261017202815, 8.9854790643 * Math.pow(10, -7)};
        wpoints[3] = new double[]{0.369188589342, 0.418786780814, 0.175794986637, 0.0333434922612, 0.00279453623523, 9.07650877338 * Math.pow(10.0, -5.0), 8.48574671626 * Math.pow(10.0, -7.0), 1.04800117487 * Math.pow(10.0, -9.0)};

        FunctionModel integrand = new FunctionModel() {
            public double compute(double input, double[] equationParams) {
                return Math.pow(input, 3) / (Math.exp(input) - 1);
            }

            public double dcompute(int derivative, double input, double[] equationParams) {
                return Double.NaN;
            }
        };

        for (int i = 0; i < num_points; i++) {
            double integral = 0;
            for (int j = 0; j < xpoints[i].length; j++) {
                System.out.println(Math.exp(xpoints[i][j]) * wpoints[i][j]); // total weight
                integral += Math.exp(xpoints[i][j]) * wpoints[i][j] * integrand.compute(xpoints[i][j], null_params);
            }
            System.out.println(integral);
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
