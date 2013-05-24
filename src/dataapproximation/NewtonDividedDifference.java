/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataapproximation;

/**
 *
 * @author jbao
 */
public class NewtonDividedDifference {

    public static double[][] findDifference(double[] xArray, double[] yArray) {
        int numOfRows = xArray.length;
        double numerator;
        double denominator;
        double[][] result = new double[numOfRows][];
        // set the first row as the f(x) terms from input
        result[0] = new double[numOfRows];
        for (int i = 0; i < numOfRows; i++) {
            result[0][i] = yArray[i];
        }
        // the next row is computed using the in-between values from the previous row
        double f_ij;
        for (int i = 1; i < numOfRows; i++) {
            // subsequent row needs one less term
            result[i] = new double[numOfRows - i];
            for (int j = 0; j < numOfRows - i; j++) {
                // use adjacent f(x) difference from the previous row
                numerator = result[i - 1][j + 1] - result[i - 1][j];
                // trace x back to their separation in the original row
                denominator = xArray[j + i] - xArray[j];
                // set the value
                f_ij = numerator / denominator;
                result[i][j] = f_ij;
            }
        }
        return result;
    }
    /* comments:
     * 1. the structure of the method is an upside down triangle \/
     * 2. the top row is the input f(x) values
     * 3. each successive row is put below its predecessor and contains one less element
     * 4. each successive row is built by taking the difference of two entries from the previous row, then dividing by the total difference in x using the input values of x
     * 5. the total difference in x is not of the adjacent positions in the previous row, but of the farthest endpoints of the tree traced up to the topmost row
     */
}
