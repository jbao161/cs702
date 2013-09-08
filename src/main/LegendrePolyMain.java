/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import function.FunctionModel;
import numutil.LegendrePolynomial;
import numutil.Polynomial;

/**
 *
 * @author jbao
 */
public class LegendrePolyMain {

    public static void main(String[] args) {
        Polynomial l8 = LegendrePolynomial.derivDefinition(8);
        l8.root_bisection(-4,4,100,1e-8);
        
        // test two different ways of defining the nth legendre polynomial
        if (true) {
            Polynomial lp;
            int n = 8;
            double TOL = 10e-5;
            boolean test = true;
            for (int i = 0; i <= n; i++) {
                test = test && LegendrePolynomial.derivDefinition(i).equals(LegendrePolynomial.recursiveDefinition(i), TOL);
                
                 lp = LegendrePolynomial.derivDefinition(i);
                 lp.print();
                 lp = LegendrePolynomial.recursiveDefinition(i);
                 lp.print();
                 
            }
            System.out.println(test);
        }
        // approximate an integral with gaussian quadrature method, using roots of the nth legendre polynomial
        function.FunctionModel ex = new function.FunctionModel() {
            @Override
            public double compute(double input, double[] equationParams) {
                return Math.exp(input) * Math.cos(input);
            }

            @Override
            public double dcompute(int derivative, double input, double[] equationParams) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        double appr = LegendrePolynomial.gaussquad(ex, new double[]{}, 3, -1, 1);
        System.out.println(appr);

        // approximate an integral over an interval not from -1 to 1
        System.out.println("\ncomparing integration methods: newton-cotes vs gaussian quadrature\n");
        function.FunctionModel ex2 = new function.FunctionModel() {
            @Override
            public double compute(double x, double[] equationParams) {
                return Math.pow(x, 6) - Math.pow(x, 2) * Math.sin(2 * x);
            }

            @Override
            public double dcompute(int derivative, double input, double[] equationParams) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        double appr2 = LegendrePolynomial.gaussquad(ex2, new double[]{}, 3, 1, 3);
        System.out.println("gaussian quadrature n = 2: " + appr2);
        double appr2c = calculus.Integral.newtoncotes(true, 1, 1, 3, ex2, new double[]{});
        System.out.println("newton cotes, n = 1, closed: " + appr2c);
        double appr2o = calculus.Integral.newtoncotes(false, 1, 1, 3, ex2, new double[]{});
        System.out.println("newton cotes, n = 1, open: " + appr2o);
    }
}
