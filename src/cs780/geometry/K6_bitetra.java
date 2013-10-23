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
public class K6_bitetra extends Project01safe {

    public static double[][] k7(double L) {

        double[][] atom_positions = new double[][]{
            {17.8130778321, -10.9601743941, 2.7881524681},
            {17.5337615754, -10.1721917375, -0.9293744309},
            {16.9232057939, -7.4452755859, 1.6344147911},
            {19.9529725524, -5.1980600189, 2.0248002665},
            {19.2800989876, -7.9438370302, 4.5795781929},
            {20.4623095148, -8.8338221587, 1.0741821748}
        };
        Matrix apm = new Matrix(atom_positions);

        return apm.multiply(L).array;
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

            String content = num_atoms + "\r\nK7 partial\r\n";

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
        double n = 2.24;
        testgeometry(n);
           long startTime = System.nanoTime();
        testminimize(n);
        long endTime = System.nanoTime();

        double duration = (endTime - startTime)*1E-6;
        System.out.println("duration (ms): "+duration);
        xyz(n);
    }
}
