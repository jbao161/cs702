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
public class K7_shield extends Project01 {

    public static double[][] p7(double L) {

        double[][] atom_positions = new double[][]{
            {1.7417974527, 2.8359118057, 0.1049058614},
            {-2.5740215969, 1.2019931524, 5.5096168429},
            {1.6439185815, -3.2898577650, 3.7065730746},
            {1.0206535344, -0.2628855654, 6.1378882921},
            {-0.1391826428, 0.1226919186, 2.4640456906},
            {3.2012087895, 0.4102477046, 2.9613649524},
            {0.7654820886, 3.0041448973, 4.0025500037}};
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
        double n = 2;
        testgeometry(n);
        testminimize(n);
        xyz(n);
    }
}
