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
        int n = 6;
        testgeometry(n);
        testminimize(n);
         xyz(n);
    }
}
