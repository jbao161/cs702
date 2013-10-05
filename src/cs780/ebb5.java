/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

import java.util.Random;

/**
 *
 * @author jbao
 */
public class ebb5 {

    public static double random(double min, double max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        double randomNum = rand.nextDouble() * (max - min) + min;

        return randomNum;
    }

    public static double[] polar() {
        double s = 1;
        double v1 = Double.NaN;
        double v2 = Double.NaN;
        // step1
        while (s >= 1) {
            double u1 = random(0, 1);
            double u2 = random(0, 1);
            v1 = 2 * u1 - 1;
            v2 = 2 * u2 - 1;
            s = v1 * v1 + v2 * v2;
        }
        double x1 = v1 * Math.sqrt(-2 * Math.log(s) / s);
        double x2 = v2 * Math.sqrt(-2 * Math.log(s) / s);
        double[] result = new double[2];
        result[0] = x1;
        result[1] = x2;
        return result;
    }

    public static double[] boxmuller() {
        double s = 1;
        double v1 = Double.NaN;
        double v2 = Double.NaN;
        // step1
        double u1 = random(0, 1);
        double u2 = random(0, 1);

        double x1 = Math.sqrt(-2 * Math.log(u1)) * Math.cos(2 * Math.PI * u2);
        double x2 = Math.sqrt(-2 * Math.log(u1)) * Math.sin(2 * Math.PI * u2);
        double[] result = new double[2];
        result[0] = x1;
        result[1] = x2;
        return result;
    }

    public static void testbox() {
        double[] histogramx = new double[]{0, 0, 0, 0, 0, 0, 0};
        double[] histogramy = new double[]{0, 0, 0, 0, 0, 0, 0};
        double[] bin = new double[]{3, 2, 1, 0, -1, -2, -3};
        int n = (int) (0.5 * Math.pow(10, 6));
        for (int i = 0; i < n; i++) {
            double[] xy = boxmuller();

            double x = xy[0];

            double y = xy[1];
            for (int k = 0; k < histogramx.length; k++) {
                double bin_edge = bin[k];
                if (x > bin_edge) {
                    histogramx[k]++;
                    break;
                }
            }
            for (int k = 0; k < histogramx.length; k++) {
                double bin_edge = bin[k];
                if (y > bin_edge) {
                    histogramy[k]++;
                    break;
                }
            }

        }
        numutil.MathTools.printdata(histogramx);
        numutil.MathTools.printdata(histogramy);
    }

    public static void normal(){
        
    }
    public static void testpolar() {
        double[] histogramx = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] histogramy = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] bin = new double[]{3.5, 3, 2.5, 2, 1.5, 1, .5, 0, -0.5, -1.0, -1.5, -2, -2.5, -3, -3.5, -0.6, -0.7, -0.8, -0.9, -1};
        int n = (int) (0.5 * Math.pow(10, 6));
        for (int i = 0; i < n; i++) {
            double[] xy = polar();

            double x = xy[0];

            double y = xy[1];
            for (int k = 0; k < histogramx.length; k++) {
                double bin_edge = bin[k];
                if (x > bin_edge) {
                    histogramx[k]++;
                    break;
                }
            }
            for (int k = 0; k < histogramx.length; k++) {
                double bin_edge = bin[k];
                if (y > bin_edge) {
                    histogramy[k]++;
                    break;
                }
            }

        }
        numutil.MathTools.printdata(histogramx);
        numutil.MathTools.printdata(histogramy);
    }

    public static void main(String[] args) {
        testbox();
    }
}
