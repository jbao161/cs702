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
public class K7_square extends Project01{
     public static double[][] p7(double L) {
        // start with the diamond, and then add an upside down tetrahedron attached to the top
        double A = Math.sqrt(3) / 3 * L;
        double B = Math.sqrt(6) / 3 * L;
        double[][] atom_positions = new double[][]{
            {A, 0, 0}, // triangle 1
            {0, 0, -B}, // bottom of diamond
            {L, 0, -B}, // right side of equilateral triangle
            {0.5 * L, Math.sqrt(3) * 0.5 * L, -B}, // middle of equilateral triangle

            {-0.5 * A, 0.5 * L, 0}, // triangle 2
            {-0.5 * A, -0.5 * L, 0},
            {0, 0, B} // top of diamond
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
        int n = 7;
        testgeometry(n);
        testminimize(n);
    }
}
