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
        Polynomial n = new Polynomial();
        double[][] py1 = {{0, -1}, {3, 1}};
        double[][] py2 = {{0, 1}, {4, 2}};
        Polynomial p1 = new Polynomial(py1);
        Polynomial p2 = new Polynomial(py2);
        Polynomial p3 = n.add(p2).times(1.0);

        double[][] data = {{2.0,0.5},{2.75,4.0/11.0},{4.0,0.25}};
        Polynomial result =Util.LagrangePolynomial.interpolate(data);
        result.print();
        System.out.println(result.toString());
        

    }
}
