/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package linearalgebra;

import java.util.Arrays;

/**
 *
 * @author jbao
 */
public class SystemOfLinearEq {

    public static double[] Gaussian(double[][] matrix) {
        double numOfEq = matrix.length;
        int numOfVar = matrix[0].length - 1; // all rows assumed to be equal length
        for (int i = 0; i < numOfEq; i++) {
            double[] currentRow = matrix[i];
            double leadingTerm = 0;
            int leadingIndex = 0;
            for (int j = i; j <= numOfVar; j++) {
                if (currentRow[j] != 0) {
                    leadingTerm = currentRow[j];
                    leadingIndex = j;
                    break;
                }
            }
            if (leadingTerm == 0) {
                return null; // no unique solution exists
            }
            if (leadingIndex != i) {
                // reorder the rows to diagonalize the matrix if not already done
                matrix[i] = matrix[leadingIndex];
                matrix[leadingIndex] = currentRow;
            }
            for (int j = i + 1; j < numOfVar; j++) {
                double coef = matrix[j][i] / matrix[i][i];
                for (int k = 0; k < matrix[j].length; k++) {
                    matrix[j][k] = matrix[j][k] - coef * matrix[i][k];
                }
            }

        }
        if (matrix[numOfVar - 1][numOfVar - 1] == 0) {
            return null; // no unique solution exists
        }
        double[] result = new double[numOfVar];
        result[numOfVar - 1] = matrix[numOfVar - 1][ numOfVar] / matrix[numOfVar - 1][numOfVar - 1];
        for (int i = numOfVar - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < numOfVar; j++) {
                sum += matrix[i][j] * result[j];
            }
            result[i] = (matrix[i][numOfVar] - sum) / matrix[i][i];
        }
        return result;
    }

    public static void main(String[] args) {
        double[][] matrix = new double[][]{
            {1, -1, 2, -1, -8},
            {2, -2, 3, -3, -20},
            {1, 1, 1, 0, -2},
            {1, -1, 4, 3, 4}};

        double[][] matrix2 = new double[][]{
            {4, -1, 1, 8},
            {2, 5, 2, 3},
            {1, 2, 4, 11}};

        System.out.println(Arrays.toString(Gaussian(matrix2)));
    }
}
