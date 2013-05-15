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
import BezierCurve.BezierCurve;
import BezierCurve.BezierPolynomial;

public class BezierCurveMain {

    public static void main(String args[]) {
        BernsteinPolynomial bp = new BernsteinPolynomial(2, 3);
        bp.print("t");
        bp.become(bp.differentiate(3));
        bp.print("t");
        
        double[] x = {2,1,3.5,4};
        double[] y = {2,1.5,0,1};
        BezierCurve bc = new BezierCurve(x,y);
        bc.print();
        bc.plot2D();
    }
}
