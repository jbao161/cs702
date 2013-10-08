/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780.geometry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import numutil.Matrix;

/**
 *
 * @author jbao
 */
public class K7_partial extends Project01 {

    public static double[][] p7(double L) {
        // start with the diamond, and then add an upside down tetrahedron attached to the top
        double A = Math.sqrt(3) / 3 * L;
        double B = Math.sqrt(6) / 3 * L;
        double[][] atom_positions = new double[][]{
            {-0.5 * A, 0.5 * L, 0}, // triangle 1
            {0, 0, -B}, // bottom of diamond
            {-A, -0.5 * L, -B}, // lower outcrop
            {-0.5 * A, -0.5 * L, 0}, //triangle 3

            {0, 0, B}, // top of diamond
            {A, 0, B}, // top outcrop
            {A, 0, 0} // triangle 2
        };
        atom_positions = new double[][]{
            {-1.6840236246, 1.0684294406, -0.7116624148},
            {0.9085473330, -0.9868619425, -3.0525511272},
            {-3.0480218864, -2.1601542407, -2.4227185692},
            {-0.0788396616, -2.0510202515, 0.1632678720},
            {-0.7874706794, 0.4530580514, 3.0710994550},
            {2.7956298090, -1.6506873466, 2.8881018952},
            {1.8641787099, 1.1562362891, 0.2244628890}
        };
        Matrix apm = new Matrix(atom_positions);

        return apm.multiply(L).array;
    }

    public static void testgeometry(double n) {
        double[][] ap = p7(n);
        Matrix printer;
        printer = new Matrix(ap);
        printer.printdata();
        Project01.get_distances(ap);
    }

    public static void testminimize(double n) {
        double[][] ap = p7(n);
        ap =minimize(ap);
        Matrix printer;
        printer = new Matrix(ap);
        printer.printdata();
        Project01.get_distances(ap);
        System.out.println(get_potential(ap));
    }

    public static void to_file(String filename, double[][] atom_positions) {
        int num_atoms = atom_positions.length;
        try {

            String content = "7\r\nK7 partial\r\n";

            File file = new File("/users/jbao/" + filename + ".xyz");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            for (int i = 0; i < num_atoms; i++) {
                int num_coordinates = atom_positions[i].length;
                bw.write("K ");
                for (int j = 0; j < num_coordinates; j++) {
                    bw.write(atom_positions[i][j] + " ");
                }
                bw.write("\r\n");
            }
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void xyz(double n) {
        double[][] ap = p7(n);
        to_file("k7 initial", ap);
        minimize(ap);
        to_file("k7 optimal", ap);

    }

    public static void main(String[] args) {
        double n = 2;
        testgeometry(n);
        testminimize(n);
        xyz(n);
    }
}
