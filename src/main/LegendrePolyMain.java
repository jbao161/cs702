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
        int n = 10;
        for (int i = 0; i < n; i++) {
            System.out.println(LegendrePolynomial.derivDefinition(i).equals(LegendrePolynomial.recursiveDefinition(i)));
            /*
            lp = LegendrePolynomial.derivDefinition(i);
            lp.print();
            lp = LegendrePolynomial.recursiveDefinition(i);
            lp.print();
            */
        }
    }
}
