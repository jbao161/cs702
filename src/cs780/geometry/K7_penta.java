/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780.geometry;

import numutil.Matrix;

/**
 *
 * @author jbao
 */
public class K7_penta extends Project01 {

    public static double[][] p7(double L) {
        double c1 = L * Math.cos(2 * Math.PI / 5);
        double c2 = L * Math.cos(Math.PI / 5);
        double s1 = L * Math.sin(2 * Math.PI / 5);
        double s2 = L * Math.sin(4 * Math.PI / 5);
        double[][] atom_positions = new double[][]{
            {0, 0, L}, // top
            {0, L, 0}, // 12o clock
            {s1, c1, 0}, // 3
            {s2, -c2, 0}, // 5
            {-s2, -c2, 0}, // 7
            {-s1, c1, 0}, // 10
            {0, 0, -L} // bottom
        };

        return atom_positions;
    }

    public static void testgeometry(int n) {
        double[][] ap = p7(n);
        Matrix printer;
        printer = new Matrix(ap);
        printer.printdata();
        Project01.get_distances(ap);
    }

    public static void testminimize(int n) {
        double[][] ap = p7(n);
        minimize(ap);
        Matrix printer;
        printer = new Matrix(ap);
        printer.printdata();
        Project01.get_distances(ap);
        System.out.println(get_potential(ap));
    }

    public static void main(String[] args) {
        int n = 6;
        testgeometry(n);
        testminimize(n);
    }
}
