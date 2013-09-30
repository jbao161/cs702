/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

import numutil.Matrix;

/**
 *
 * @author jbao
 */
public class hw04b {

    static void ex17() {
        int size = 5;
        double[][] ex3_17 = new double[][]{
            {2, -1, 0, 0, 0, 0},
            {-1, 2, -1, 0, 0, 1},
            {0, -1, 2, -1, 0, 2},
            {0, 0, -1, 2, -1, 3},
            {0, 0, 0, 2, -1, 4}
        };

        Matrix m17 = new Matrix(ex3_17);

        double[] sol17 = m17.solve(true);
        Matrix ms17 = new Matrix(sol17);
        double[][] red_17 = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                red_17[i][j] = ex3_17[i][j];
            }
        }
        Matrix mr_17 = new Matrix(red_17);
        mr_17.multiply(ms17).print();
        Matrix lu1 = mr_17.LUdoolittle()[0];
        Matrix lu2 = mr_17.LUdoolittle()[1];
        lu1.print();
        lu2.print();
        lu1.multiply(lu2).print();
        System.out.println(mr_17.det());
    }

    public void ex18() {
        int size = 5;
        double[][] ex3_18 = new double[size + 1][size + 1];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ex3_18[i][j] = 1.0 / (j + i + 1);
            }
            ex3_18[i][size] = 1 + i;
        }
        Matrix m18 = new Matrix(ex3_18);

        double[][] red_18 = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                red_18[i][j] = ex3_18[i][j];
            }
        }
        Matrix mr_18 = new Matrix(red_18);
        Matrix lu1 = mr_18.LUdoolittle()[0];
        Matrix lu2 = mr_18.LUdoolittle()[1];
        lu1.print();
        lu2.print();
        lu1.multiply(lu2).print();
        System.out.println(mr_18.det());
        mr_18.inverse().print();
        double[] xy = new double[size];
        for (int i = 0; i < size; i++) {
            xy[i] = 1 + i;
        }
        Matrix sol = mr_18.solve(xy, true);
        mr_18.multiply(sol).print();
    }

    public static void main(String[] args) {
        int size = 7;
        double[][] ex19 = new double[][]{
            {3, 0, -1, -1, 0, 0, 0},
            {0, 3, 0, -1, -1, 0, 0},
            {2, 0, -3, 0, 0, 1, 0},
            {2, 1, 0, -6, 0, 1, 2},
            {0, 1, 0, 0, -3, 0, 2},
            {0, 0, 1, 1, 0, -3, 0},
            {0, 0, 0, 1, 1, 0, -3}};
        double[] xy = {10,10,0,0,0,0,0};
        Matrix m19 = new Matrix(ex19);
        m19.LUdoolittle()[0].print();
         m19.LUdoolittle()[1].print();
        m19.solve(xy, true);
        m19.det(true);
    }
}
