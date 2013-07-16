/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package numutil;

import java.util.Arrays;

/**
 *
 * @author jbao
 */
public class Matrix {

    public double[][] array;

    public Matrix() {
    }

    /**
     * Creates a matrix using a copy of a two dimensional array.
     *
     * @param array
     */
    public Matrix(double[][] array) {
        Copy(array);
        Rectify(array);
        this.array = array;
    }

    /**
     * Creates a matrix using a two dimensional array without checking equal row
     * lengths.
     *
     * @param array
     * @param bypass dummy input that does nothing
     */
    private Matrix(double[][] array, boolean bypass) {
        this.array = array;
    }

    /**
     * Creates an n x m matrix with entries equal to zero.
     *
     * @param row n
     * @param col m
     */
    public Matrix(int row, int col) {
        this(row, col, 0.0);
    }

    /**
     * Creates an n x m matrix with entries equal to a constant value.
     *
     * @param row n
     * @param col m
     * @param entry constant value
     */
    public Matrix(int row, int col, double entry) {
        double[][] a = new double[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                a[i][j] = entry;
            }
        }
        this.array = a;
    }

    public void set(double[][] array) {
        Rectify(array);
        this.array = array;
    }

    public void set(int row, int col, double entry) {
        array[row][col] = entry;
    }

    /**
     * Makes a deep copy of an array.
     *
     * @param array
     * @return
     */
    public static double[][] Copy(double[][] array) {
        double[][] result = new double[array.length][];
        for (int i = 0; i < array.length; i++) {
            result[i] = new double[array[i].length];
            for (int j = 0; j < array[i].length; j++) {
                result[i][j] = array[i][j];
            }
        }
        return result;
    }

    /**
     * Makes all rows of a 2D array of equal length by extending the lengths of
     * shorter rows using zeros. The input array changes.
     *
     * @param array
     * @return
     */
    public static double[][] Rectify(double[][] array) {
        int[] lengths = new int[array.length];
        int count = 0;
        int maxSize = array[0].length;
        for (int i = 0; i < array.length; i++) {
            int size = array[i].length;
            lengths[i] = size;
            if (size != maxSize) {
                count++;
                if (size > maxSize) {
                    maxSize = size;
                }
            }
        }
        if (count > 0) {
            for (int i = 0; i < array.length; i++) {
                if (lengths[i] < maxSize) {
                    double[] extend = new double[maxSize];
                    for (int j = 0; j < array[i].length; j++) {
                        extend[j] = array[i][j];
                    }
                    for (int j = array[i].length; j < maxSize; j++) {
                        extend[j] = 0;
                    }
                    array[i] = extend;
                }
            }
        }
        return array;
    }

    public int[] dim() {
        return new int[]{array.length, array[0].length};
    }

    public void print() {
        int[] dim = dim();
        System.out.println(dim[0] + "x" + dim[1] + " matrix");
        for (int i = 0; i < dim[0]; i++) {
            System.out.println(Arrays.toString(array[i]));
        }
    }

    public Matrix add(double constant) {
        double[][] b = this.array;
        int row = b.length;
        int col = b[0].length;
        double[][] c = new double[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                c[i][j] = constant + b[i][j];
            }
        }
        Matrix result = new Matrix(c, true);
        return result;
    }

    public Matrix add(Matrix matrix) {
        int[] dimA = this.dim();
        int[] dimB = matrix.dim();
        if (dimA[0] != dimB[0] || dimA[1] != dimB[1]) {
            throw new ArithmeticException("matrix dimensions do not agree: (" + dimA[0] + "x" + dimA[1] + "),(" + dimB[0] + "x" + dimB[1] + ") ");
        }
        int row = dimA[0];
        int col = dimA[1];
        double[][] a = this.array;
        double[][] b = matrix.array;
        double[][] c = new double[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                c[i][j] = a[i][j] + b[i][j];
            }
        }
        Matrix result = new Matrix(c, true);
        return result;
    }

    public Matrix multiply(double constant) {
        double[][] a = this.array;
        int row = a.length;
        int col = a[0].length;
        double[][] c = new double[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                c[i][j] = constant * a[i][j];
            }
        }
        Matrix result = new Matrix(c, true);
        return result;
    }

    public Matrix multiply(Matrix matrix) {
        int[] dimA = this.dim();
        int[] dimB = matrix.dim();
        if (dimA[1] != dimB[0]) {
            throw new ArithmeticException("matrix dimensions do not agree: (" + dimA[0] + "x" + dimA[1] + "),(" + dimB[0] + "x" + dimB[1] + ") ");
        }
        double[][] a = this.array;
        double[][] b = matrix.array;
        int row = dimA[0];
        int col = dimB[1];
        double[][] c = new double[row][col];
        int multiplications = dimA[1];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                double entry = 0;
                for (int m = 0; m < multiplications; m++) {
                    entry += a[i][m] * b[m][j];
                }
                c[i][j] = entry;
            }
        }
        Matrix result = new Matrix(c, true);
        return result;
    }

    public Matrix reduce() {
        Matrix result = new Matrix(Copy(array));
        linearalgebra.SystemOfLinearEq.reduce(result.array);
        return result;
    }

    private void reduce(boolean nocopy) {
        linearalgebra.SystemOfLinearEq.reduce(array);
    }

    public double[] solve(boolean print) {
        double[] result = linearalgebra.SystemOfLinearEq.Gaussian(Copy(array));
        if (print) {
            System.out.println(Arrays.toString(result));
        }
        return result;
    }

    public double[] solve() {
        return solve(false);
    }

    /**
     * Creates a matrix of a n_x_n square matrix augmented with the n_x_n
     * identity.
     *
     * @return
     */
    private Matrix augment() {
        int[] dim = dim();
        if (dim[0] != dim[1]) {
            throw new ArithmeticException("matrix is not square: (" + dim[0] + "x" + dim[1] + ")");
        }
        int n = dim[0];
        double[][] identity = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                identity[i][j] = 0;
            }
            identity[i][i] = 1;
        }
        double[][] augment = new double[n][2 * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augment[i][j] = array[i][j];
                augment[i][j + n] = identity[i][j];
            }
        }
        Matrix result = new Matrix(augment);
        return result;
    }

    /**
     * Creates a matrix by augmenting column or row vectors.
     *
     * @param matrix
     * @param column true joins the array as column vectors to the right of
     * matrix. false joins the array as row vectors to the bottom of the matrix.
     * @return
     */
    public Matrix augment(double[][] matrix, boolean column) {
        int[] dim = dim();
        int row = dim[0];
        int col = dim[1];
        double[][] addend = Copy(matrix);
        Rectify(addend);
        int addrow = addend.length;
        int addcol = addend[0].length;
        if (column) {
            col += addcol;
            if (row != addrow) {
                throw new ArithmeticException("row lengths do not match: " + row + "," + addrow);
            }
        } else {
            row += addrow;
            if (col != addcol) {
                throw new ArithmeticException("row lengths do not match: " + col + "," + addcol);
            }
        }
        double[][] augment = new double[row][col];
        if (column) {
            for (int i = 0; i < dim[0]; i++) {
                for (int j = 0; j < dim[1]; j++) {
                    augment[i][j] = array[i][j];
                    augment[i][j + addcol] = addend[i][j];
                }
            }
        } else {
            for (int i = 0; i < dim[0]; i++) {
                for (int j = 0; j < dim[1]; j++) {
                    augment[i][j] = array[i][j];
                    augment[i + addrow][j] = addend[i][j];
                }
            }
        }
        Matrix result = new Matrix(augment);
        return result;
    }

    /**
     * Creates a matrix by augmenting another matrix as column or row vectors.
     *
     * @param matrix
     * @param column true joins the input matrix as column vectors to the right
     * of matrix. false joins the input matrix as row vectors to the bottom of
     * the matrix.
     * @return
     */
    public Matrix augment(Matrix matrix, boolean column) {
        return augment(matrix.array, column);
    }

    public static Matrix Identity(int n) {
        Matrix result = new Matrix(Identity(n, true));
        return result;

    }

    private static double[][] Identity(int n, boolean array) {
        double[][] identity = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                identity[i][j] = 0;
            }
            identity[i][i] = 1;
        }
        return identity;
    }

    public Matrix transpose() {
        int[] dim = dim();
        double[][] transpose = new double[dim[1]][dim[0]];
        for (int i = 0; i < dim[1]; i++) {
            for (int j = 0; j < dim[0]; j++) {
                transpose[i][j] = this.array[j][i];
            }
        }
        Matrix result = new Matrix(transpose);
        return result;
    }

    /**
     * Returns the inverse of a square matrix. Original matrix is not modified.
     * @return 
     */
    public Matrix inverse() {
        int[] dim = dim();
        int n = dim[0];
        Matrix result = this.augment();
        result.reduce(true);
        result.crop(new int[]{0, n, n, 2 * n});
        return result;
    }

    private void crop(int[] corners) {
        int rowstart = corners[0];
        int rowend = corners[1];
        int colstart = corners[2];
        int colend = corners[3];

        int row = rowend - rowstart;
        int col = colend - colstart;
        double[][] crop = new double[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                crop[i][j] = array[rowstart + i][colstart + j];
            }
        }
        array = crop;
    }

    public static void main(String[] args) {
        double[][] test = {{1, 2, 3}, {1}, {1, 2, 3, 4, 5}};
        Matrix m1 = new Matrix(test);
        m1.print();

        Matrix m2 = new Matrix(5, 4, 1);
        m1.add(m1).print();
        m1.add(2).print();
        m1.print();
        m1.multiply(m2).print();

        m2 = new Matrix(new double[][]{{1, 2, 3}, {2, 3, 4}, {0, 4, 5}});
        m1 = new Matrix(new double[][]{{1, 4, 6}, {3, 5, 9}});
        m1.multiply(m2).print();
        m1.multiply(2).print();
        System.out.println(Arrays.toString(m1.solve()));
        m1.print();
        m1.transpose().print();
        m1 = new Matrix(4, 4, 5);
        m2.augment(Identity(3), true).print();
        m2.print();
        m2.augment(Identity(3), true).reduce().print();
        m2.inverse().print();
        m2.print();
    }
}
