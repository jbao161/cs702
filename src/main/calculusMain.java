/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import numutil.Polynomial;

/**
 *
 * @author jbao
 */
public class calculusMain {
    /*
     * numerical integration: lagrange iterpolating polynomials, composite lagrange,
     */

    public static void main(String args[]) {

        Polynomial p = new Polynomial(new double[]{0, 1.0});
        Polynomial ap = p.antiderivative(1);
        ap.print();
        System.out.println(p.integrate(0.0, 2.0));
        function.FunctionModel ex = new function.FunctionModel() {
            @Override
            public double compute(double input, double[] equationParams) {
                return 100 / input / input * Math.sin(10 / input);
            }

            @Override
            public double dcompute(int derivative, double input, double[] equationParams) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        double[] equationParams = new double[0];
        double r1 = calculus.Integral.newtoncn(2, 0.0, 4.0, ex, equationParams);
        double r2 = calculus.Integral.newtonc2(0.0, 4.0, ex, equationParams);
        double r3 = calculus.Integral.newtonon(2, 0.0, 4.0, ex, equationParams);
        double r4 = calculus.Integral.newtono2(0.0, 4.0, ex, equationParams);
        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);
        System.out.println(r4);

        double r5 = calculus.AdaptiveQuadrature.aq2(1.0, 3.0, ex, equationParams, 0.0001, 300);
        System.out.println("aq: " + r5);

    }
}
