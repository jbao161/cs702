/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author jbao
 */
import BezierCurve.BernsteinPolynomial;
public class BezierCurve {
       public static void main(String args[]) {
           BernsteinPolynomial.bernstein(2,3).print("t");
           System.out.println(Util.MathTools.binomial(5, 3));
       }
}
