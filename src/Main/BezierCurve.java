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
import java.util.ArrayList;

public class BezierCurve {

    public static void main(String args[]) {
        BernsteinPolynomial bp = new BernsteinPolynomial(2, 3);
        bp.print("t");
        bp.become(bp.differentiate(3));
        bp.print("t");
        ArrayList<Double> ad = new ArrayList<Double>();
    }
}
