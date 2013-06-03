/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpolationhermite;

import numutil.Pair;
import numutil.Polynomial;

/**
 *
 * @author jbao
 */
public class HermitePolynomial extends Polynomial {

    public HermitePolynomial() {
    }

    public HermitePolynomial(double[] xArray, double[] fxArray, double[] dxArray) {
        Polynomial hermitePoly = new Polynomial(new double[]{0});
        Polynomial singleTerm;
        Pair xz;
        int n = xArray.length - 1;
        int numOfRows = 2 * (n + 1);
        double[][] result = new double[numOfRows][];
        double[] z = new double[numOfRows];
        result[0] = new double[numOfRows];
        result[1] = new double[numOfRows - 1];
        double numerator;
        double denominator;
        // set up the first row from f(x) inputs
        for (int i = 0; i <= n; i++) {
            // doubles up the x values
            z[2 * i] = xArray[i];
            z[2 * i + 1] = xArray[i];
            // doubles up the f(x) values in the first row
            result[0][2 * i] = fxArray[i];
            result[0][2 * i + 1] = fxArray[i];
        }
        // set up the second row using the derivative or the approximation
        for (int i = 0; i < n; i++) {
            result[1][2 * i] = dxArray[i];
            result[1][2 * i + 1] = (result[0][2 * (i + 1)] - result[0][2 * i + 1]) / (z[2 * (i + 1)] - z[2 * i + 1]);
        }
        // the last entry of the second row contains a derivative, there is no approximation entry
        result[1][result[1].length - 1] = dxArray[(result[1].length - 1) / 2];

        // the next row is computed using the in-between values from the previous row
        double f_ij;
        for (int i = 2; i < numOfRows; i++) {
            // subsequent row needs one less term
            result[i] = new double[numOfRows - i];
            for (int j = 0; j < numOfRows - i; j++) {
                // use adjacent f(x) difference from the previous row
                numerator = result[i - 1][j + 1] - result[i - 1][j];
                // trace x back to their separation in the original row
                denominator = z[j + i] - z[j];
                // set the value
                f_ij = numerator / denominator;
                result[i][j] = f_ij;
            }
        }
        // the hermite polynomial
        for (int i = 0; i <= 2 * n + 1; i++) {
            // the first term f[x0,x1,x2,...,xi] of each row in the coefficient array is used as the coefficient of the polynomial term
            double fz = result[i][0];
            // the polynomial term is a product of (x-xj), where j = 0 to i-1
            singleTerm = new Polynomial(new double[]{1});
            for (int j = 0; j < i; j++) {
                xz = new Pair(z[j]);
                singleTerm = singleTerm.times(xz);
            }
            singleTerm = singleTerm.times(fz);
            hermitePoly = hermitePoly.add(singleTerm);
         
        }
        become(hermitePoly);
    }

    public HermitePolynomial(double[][] xfxdx) {
        int numOfTerms = xfxdx.length;
        double[] xArray = new double[numOfTerms];
        double[] fxArray = new double[numOfTerms];
        double[] dxArray = new double[numOfTerms];
        for (int i = 0; i < numOfTerms; i++) {
            xArray[i] = xfxdx[i][0];
            fxArray[i] = xfxdx[i][1];
            dxArray[i] = xfxdx[i][2];
        }
        HermitePolynomial hp = new HermitePolynomial(xArray, fxArray, dxArray);
        become(hp);
    }

    public static double[][] computeCoef(double[] xArray, double[] fxArray, double[] dxArray) {
        int n = xArray.length - 1;
        int numOfRows = 2 * (n + 1);
        double[][] result = new double[numOfRows][];
        double[] z = new double[numOfRows];
        result[0] = new double[numOfRows];
        result[1] = new double[numOfRows - 1];
        double numerator;
        double denominator;
        // set up the first row from f(x) inputs
        for (int i = 0; i <= n; i++) {
            // doubles up the x values
            z[2 * i] = xArray[i];
            z[2 * i + 1] = xArray[i];
            // doubles up the f(x) values in the first row
            result[0][2 * i] = fxArray[i];
            result[0][2 * i + 1] = fxArray[i];
        }
        // set up the second row using the derivative or the approximation
        for (int i = 0; i < n; i++) {
            result[1][2 * i] = dxArray[i];
            result[1][2 * i + 1] = (result[0][2 * (i + 1)] - result[0][2 * i + 1]) / (z[2 * (i + 1)] - z[2 * i + 1]);
        }
        // the last entry of the second row contains a derivative, there is no approximation entry
        result[1][result[1].length - 1] = dxArray[(result[1].length - 1) / 2];

        // the next row is computed using the in-between values from the previous row
        double f_ij;
        for (int i = 2; i < numOfRows; i++) {
            // subsequent row needs one less term
            result[i] = new double[numOfRows - i];
            for (int j = 0; j < numOfRows - i; j++) {
                // use adjacent f(x) difference from the previous row
                numerator = result[i - 1][j + 1] - result[i - 1][j];
                // trace x back to their separation in the original row
                denominator = z[j + i] - z[j];
                // set the value
                f_ij = numerator / denominator;
                result[i][j] = f_ij;
            }
        }

        return result;
    }

    public static double[][] computeCoef(double[][] xfxdx) {
        int numOfTerms = xfxdx.length;
        double[] xArray = new double[numOfTerms];
        double[] fxArray = new double[numOfTerms];
        double[] dxArray = new double[numOfTerms];
        for (int i = 0; i < numOfTerms; i++) {
            xArray[i] = xfxdx[i][0];
            fxArray[i] = xfxdx[i][1];
            dxArray[i] = xfxdx[i][2];
        }
        return computeCoef(xArray, fxArray, dxArray);
    }
    
    
}
