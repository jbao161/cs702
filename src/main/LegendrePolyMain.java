/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import numutil.LegendrePolynomial;
import numutil.Polynomial;

/**
 *
 * @author jbao
 */
public class LegendrePolyMain {

    public static void main(String[] args) {
        Polynomial lp;
        int n = 30;
        double TOL = 10e-5;
        boolean test = true;
        for (int i = 0; i <= n; i++) {
            test = test && LegendrePolynomial.derivDefinition(i).equals(LegendrePolynomial.recursiveDefinition(i), TOL);
            /*
             lp = LegendrePolynomial.derivDefinition(i);
             lp.print();
             lp = LegendrePolynomial.recursiveDefinition(i);
             lp.print();
             */
        }
        System.out.println(test);
    }
}
