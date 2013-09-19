/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import numutil.Matrix;

/**
 *
 * @author jbao
 */
public class ebb4 {
    /* consider the molecule C,Be. This is a diatomic molecule.
     * assume that the interatomic potential to model this molecule is the Morse potential:
     * U(r) = alpha * (e^-beta*(r/gamma - 1) - 1)^2 - alpha
     * where alpha, beta, and gamma are parameters and r is the distance between C and Be
     */

    /* define the error S for a nonlinear fitting of the 3 parameters,
     * where target points are provided in data.
     */
    public static void main(String[] args) {
        double[][] data = read_in("ebb4data.csv");
        System.out.println(Arrays.deepToString(data));
        Matrix printer = new Matrix(data);
        printer.printdata();
    }

    public static double[][] read_in(String filepath) {
        double[][] result = new double[0][];
        ArrayList<Double[]> resultal = new ArrayList<Double[]>();
        try {
            int k = 0;
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line = null;
            while ((line = reader.readLine()) != null) {

                String[] parsedline = line.split(",");
                Double[] data_row = new Double[parsedline.length];
                for (int i = 0; i < parsedline.length; i++) {
                    data_row[i] = Double.parseDouble(parsedline[i]);
                }
                resultal.add(data_row);
                k++;

            }
            result = new double[k][3];
            for (int i = 0; i < k; i++) {
                result[i] = new double[3];
                for (int j = 0; j < 3; j++) {
                    result[i][j] = resultal.get(i)[j].doubleValue();
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }
}
