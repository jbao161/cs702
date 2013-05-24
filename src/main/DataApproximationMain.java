/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import dataapproximation.NewtonDividedDifference;
import interpolation.hermite.HermitePolynomial;
import java.util.Arrays;

/**
 *
 * @author jbao
 */
public class DataApproximationMain {
    
    public static void main(String args[]) {
        double[] x = {8.1, 8.3, 8.6, 8.7};
        double[] y = {16.94410, 17.56492, 18.50515, 18.82091};
        System.out.println(Arrays.deepToString(dataapproximation.NevilleInterpolation.iterate(8.4, x, y)));
        x = new double[]{1, 1.3, 1.6, 1.9, 2.2};
        y = new double[]{0.7651977, 0.6200860, 0.4554022, 0.2818186, 0.1103623};
        System.out.println(Arrays.deepToString(NewtonDividedDifference.findDifference(x, y)));
        
        double[][] xy = {{1.3, 0.6200860, -0.5220232}, {1.6, 0.4554022, -0.5698959},
            {1.9, 0.2818186, -0.5811571}};
        double[][] result = interpolation.hermite.HermitePolynomial.computeCoef(xy);
        System.out.println(Arrays.deepToString(result));
        
        HermitePolynomial hp = new HermitePolynomial(xy);
        hp.print();
        
        double[] x1 = {0, 1, 2, 3};
        double[] y1 = {1, Math.exp(1.0), Math.exp(2.0), Math.exp(3.0)};
        
        double[][] answer = interpolation.bspline.NaturalCubicSpline.computePoly(x1, y1);
        System.out.println(Arrays.deepToString(answer));
    }
}
