/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import DataApproximation.NewtonDividedDifference;
import java.util.Arrays;

/**
 *
 * @author jbao
 */
public class DataApproximationMain {

    public static void main(String args[]) {
        double[] x = {8.1, 8.3, 8.6, 8.7};
        double[] y = {16.94410, 17.56492, 18.50515, 18.82091};
        System.out.println(Arrays.deepToString(DataApproximation.NevilleInterpolation.iterate(8.4, x, y)));
        x = new double[]{1, 1.3, 1.6, 1.9, 2.2};
        y = new double[]{0.7651977, 0.6200860, 0.4554022, 0.2818186, 0.1103623};
        System.out.println(Arrays.deepToString(NewtonDividedDifference.findDifference(x, y)));
    }
}
