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
            // elminate the leading term of the row below by subtracting a multiple of that row with this row
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
        // backwards substitution for the variable values
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

    /**
     * Reduces a matrix into reduced row-echelon form using the algorithm
     * described here: http://www.csun.edu/~panferov/math262/262_rref.pdf.
     *
     * @param matrix
     */
    public static void reduce(double[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        for (int i = 0, j = 0; i < row && j < col; i++, j++) {
            /* if aij = 0 swap the ith row with some other row below to guarantee that aij != 0.
             * if all entries in the column are zero, increase j by 1.
             */
            if (matrix[i][j] == 0) {
                boolean found = false;
                for (int k = i; k < row; k++) {
                    if (matrix[k][j] != 0) {
                        double[] swap = matrix[k];
                        matrix[k] = matrix[i];
                        matrix[i] = swap;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    j++;
                    if (j >= col) {
                        break;
                    }
                }
            }
            // divide the ith row by aij to make the pivot entry = 1
            double aij = matrix[i][j];
            for (int k = 0; k < col; k++) {
                matrix[i][k] = matrix[i][k] / aij;
            }
            // eliminate all other entries in the jth column by subtracting suitable multiples of the ith row from other rows
            for (int k = 0; k < row; k++) {
                double coef = matrix[k][j];
                for (int m = 0; m < col; m++) {
                    if (k != i) {
                        matrix[k][m] = matrix[k][m] - coef * matrix[i][m];
                    }
                }
            }
        }
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
        matrix2 = new double[][]{
            {0, 0, 1, 8},
            {0, 0, 0, 0},
            {1, 5, 2, 3},
            {1, 2, 4, 11}};
        reduce(matrix2);
        System.out.println(Arrays.deepToString((matrix2)));
    }
}
