/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataApproximation;

/**
 *
 * @author jbao
 */
public class NevilleInterpolation {

    public static double[][] iterate(double x, double[] xArray, double[] yArray) {
        int n = xArray.length;
        double xi;
        double xi_j;
        double[][] result = new double[n][0];

        // create an array size matches number of nonzero elements, set the first element to f(xi)
        for (int i = 0; i < n; i++) {
            result[i] = new double[i + 1];
            result[i][0] = yArray[i];
        }
        // recursively fill in the subsequent elements
        for (int i = 1; i < n; i++) {
            xi = xArray[i];
            for (int j = 1; j <= i; j++) {
                xi_j = xArray[i - j];
                result[i][j] = ((x - xi_j) * result[i][j - 1] - (x - xi) * result[i - 1][j - 1]) / (xi - xi_j);
            }
        }
        return result;
    }
}
