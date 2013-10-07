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
public class K5_diamond extends Project01 {

    public static double[][] d5(double L) {
        double T = Math.sqrt(2) * 0.5 * L;
        double[][] atom_positions = new double[][]{
            {L, 0, -T},
            {-L, 0, -T},
            {0, L, T},
            {0, -L, T},
            {0, -2 * T, -L},};
        return atom_positions;
    }

    public static void testgeometry() {
        double[][] ap = d5(10);
        Matrix printer;
        printer = new Matrix(ap);
        printer.printdata();
        Project01.get_distances(ap);
    }

    public static void testminimize() {
        double[][] ap = d5(4);
        minimize(ap);
        Matrix printer;
        printer = new Matrix(ap);
        printer.printdata();
        Project01.get_distances(ap);
        System.out.println(get_potential(ap));
    }

    public static void main(String[] args) {
        //testgeometry();
        testminimize();
    }
}
