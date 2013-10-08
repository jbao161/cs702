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
public class K4_Tetra extends Project01 {

    public static double[][] k7(double L) {

         double[][] atom_positions = new double[][]{
            {L, 0, -Math.sqrt(2) * 0.5 * L},
            {-L, 0, -Math.sqrt(2) * 0.5 * L},
            {0, L, Math.sqrt(2) * 0.5 * L},
            {0, -L, Math.sqrt(2) * 0.5 * L}
        };

        return atom_positions;
    }

    public static void testgeometry(double n) {
        double[][] ap = k7(n);
        Matrix printer;
        printer = new Matrix(ap);
        printer.printdata();
        Project01.get_distances(ap);
    }

    public static void testminimize(double n) {
        double[][] ap = k7(n);
        minimize(ap);
        Matrix printer;
        printer = new Matrix(ap);
        printer.printdata();
        Project01.get_distances(ap);
        System.out.println(get_potential(ap));
    }

    public static void to_file(String filename, double[][] atom_positions) {
        int num_atoms = atom_positions.length;
        try {

            String content = num_atoms+"\r\nK7 partial\r\n";

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
        double[][] ap = k7(n);
        to_file("k7 initial", ap);
        minimize(ap);
        to_file("k7 optimal", ap);

    }

    public static void main(String[] args) {
        double n = 4;
        testgeometry(n);
        testminimize(n);
        xyz(n);
    }
}
