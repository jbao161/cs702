/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SolutionOfSingleVariableEq;

import Util.Polynomial;

/**
 *
 * @author jbao
 */
public class LagrangePoly {

    public static void main(String[] args) {
        double[][] py1 = {{0, -1}, {3, 1}};
        double[][] py2 = {{0, 1}, {4, 2}};
        Polynomial p1 = new Polynomial(py1);
        Polynomial p2 = new Polynomial(py2);
        Polynomial p3 = p1.times(p2);
          System.out.println(p3.toString());
        System.out.println(p3.toText());

        Polynomial n = new Polynomial();

        System.out.println(p1.add(p2).toString());
        System.out.println(p2.add(p1).toString());
        System.out.println(n.add(p1).toString());
    }
}
