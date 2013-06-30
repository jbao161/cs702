/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package approximation;

import java.util.Arrays;
import linearalgebra.SystemOfLinearEq;
import numutil.Polynomial;

/**
 *
 * @author jbao
 */
public class LinearLeastSquares {

    public static Polynomial LinearLeastSquares(double[] x, double[] fx) {
        double c1, c2;
        double sumx2 = 0;
        double sumx = 0;
        double sumy = 0;
        double sumxy = 0;
        double m = x.length;
        for (int i = 0; i < x.length; i++) {
            sumx += x[i];
            sumx2 += Math.pow(x[i], 2);
            sumy += fx[i];
            sumxy += x[i] * fx[i];
        }
        c1 = (sumx2 * sumy - sumxy * sumx) / (m * sumx2 - Math.pow(sumx, 2));
        c2 = (m * sumxy - sumx * sumy) / (m * sumx2 - Math.pow(sumx, 2));
        Polynomial result = new Polynomial(new double[]{c1, c2});
        return result;
    }

    public static Polynomial PolynomialLeastSquares(int n, double[] x, double[] fx) {
        n++; // input is power of the polynomial, so number of coefs should be one more due to the constant term
        int m = x.length;
        double[] sumxpow = new double[2*n];
        for ( int i =0; i < sumxpow.length; i ++){
            double sum = 0;
            for (int j =0; j <m; j++){
                sum+= Math.pow(x[j],i);
            }
            sumxpow[i] = sum;
        }
        System.out.println(Arrays.toString(sumxpow));
        double[][] matrix = new double[n][n+1];
        for (int i =0; i < n; i++){
            for (int j=0; j < n; j++){
                matrix[i][j] = sumxpow[i+j];
            }
            double yx = 0;
            for (int j =0; j  < m; j++){
                yx += fx[j] * Math.pow(x[j],i);
            }
            matrix[i][n] = yx;
        }
        System.out.println(Arrays.deepToString(matrix));
        double[] coef = SystemOfLinearEq.Gaussian(matrix);
        Polynomial result = new Polynomial(coef);
        return result;
    }

    public static void main(String[] args) {
        double[] x = new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        double[] y = new double[]{1.3, 3.5, 4.2, 5.0, 7.0, 8.8, 10.1, 12.5, 13.0, 15.6};
        LinearLeastSquares(x, y).print();
        double[] x2 = new double[]{0,.25,.5,.75,1};
        double[] y2 = new double[]{1,1.284,1.6487,2.117,2.7183};
        PolynomialLeastSquares(3,x2,y2).print();
    }
}
