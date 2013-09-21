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
import numutil.Polynomial;

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
        // First we find the interatomic length between the C and Be atoms and plot with the potential.
        data = clean(data);
        //System.out.println(Arrays.deepToString(data));
        //Matrix printer = new Matrix(data);
        //printer.printdata();
        function.FunctionModel morse = new function.FunctionModel() {
            @Override
            public double compute(double input, double[] params) {
                double alpha = params[0];
                double beta = params[1];
                double gamma = params[2];
                double eterm = Math.exp(-1 * beta * (input / gamma - 1));
                return alpha * Math.pow(eterm - 1, 2) - alpha;
            }

            @Override
            public double dcompute(int derivative, double input, double[] params) {
                return Double.NaN;
            }
        };
        // Using visual inspection we can approximate initial parameters to be A = 2.5, C = 1.6. B = ?
        double[] params = {2.5943737821283808, 3, 1.65};
        params = new double[]{2, 5, 2};
        // We run a LM curve fit for the mores potential. 
        params = cs780.LM.nl_fit(morse, params, data);
        numutil.Plot.plot_datafit(morse, params, data, 0, 1, 3, true);
        // Then compare the data points with the fitted function.
        double fittedpt, x;
        System.out.println("fitted params:" + Arrays.toString(params));
        for (int i = 0; i < data.length; i++) {
            x = data[i][0];
            fittedpt = morse.compute(x, params);
            System.out.println(x + "," + data[i][1] + "," + fittedpt);
        }

    }

    public static double[][] clean(double[][] data) {
        double[][] result = new double[data.length][2];
        for (int i = 0; i < data.length; i++) {
            result[i] = new double[2];
            result[i][0] = data[i][1] - data[i][0];
            result[i][1] = data[i][2];
        }
        return result;
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
