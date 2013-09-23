/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

import function.FunctionModel;
import java.util.Arrays;
import numutil.Matrix;

/**
 * using the assigned matlab style algorithm as presented by Devries Hasbun. had
 * to add modifications on top of original functions to make a least squares fit
 * comments explain what Devries Hasbun did and do not reflect author's choice
 * for coding!
 *
 * @author jbao
 */
public class hw04a {

    public static double TOL = 1e-1;
    public static int max_iter = (int) 1000;

    public static void main(String[] args) {
        function.FunctionModel curve = new function.FunctionModel() {
            @Override
            public double compute(double input, double[] params) {
                double A = params[0];
                double B = params[1];
                double C = params[2];
                double D = params[3];
                double denom = 1 + 4 * Math.pow(input - A, 2) / Math.pow(B, 2);
                double result = D + C / denom;
                return result;
            }

            @Override
            public double dcompute(int derivative, double input, double[] params) {
                return Double.NaN;
            }
        };

        double[][] data = new double[][]{
            {435.784, 40.0},
            {435.789, 44.0},
            {435.789, 41.0},
            {435.799, 46.0},
            {435.804, 47.0},
            {435.809, 54.0},
            {435.814, 66.0},
            {435.819, 97.0},
            {435.824, 129.0},
            {435.829, 153.0},
            {435.834, 165.0},
            {435.839, 168.0},
            {435.844, 143.0},
            {435.849, 110.0},
            {435.854, 79.0},
            {435.859, 64.0},
            {435.864, 52.0},
            {435.869, 51.0},
            {435.874, 44.0},
            {435.879, 46.0},
            {435.884, 41.0}};

        double[] init_params = new double[]{435.84, 0.03, 120.0, 40.0};
        double[] init_step = new double[]{0.01, 0.005, 4.0, 2.0};
        double[] params;

    }


    public static double[][] newton(FunctionModel function, double[] params, double[] step, double[][] data) {
        int psize = params.length;
        double[] ap = new double[psize];
        double[] am = new double[psize];
        double[] app = new double[psize];
        double[] apm = new double[psize];
        double[] amp = new double[psize];
        double[] amm = new double[psize];
        double[][] hessian = new double[psize][psize];
        // compute Hessian
        for (int i = 0; i < psize; i++) {
            for (int j = 0; j < psize; j++) {
                if (i == j) {
                    for (int k = 0; k < psize; k++) {
                        ap[k] = params[k];
                        am[k] = params[k];
                    }
                    ap[i] = params[i] + step[i];
                    am[i] = params[i] - step[i];
                    hessian[i][i] = (LSE(function, ap, data) - 2 * LSE(function, params, data) * LSE(function, am, data)) / Math.pow(step[i], 2);
                } else {
                    for (int k = 0; k < psize; k++) {
                        app[k] = params[k];
                        apm[k] = params[k];
                        amp[k] = params[k];
                        amm[k] = params[k];
                    }
                    app[i] = params[i] + step[i];
                    app[j] = params[j] + step[j];
                    apm[i] = params[i] + step[i];
                    apm[j] = params[j] - step[j];
                    amp[i] = params[i] - step[i];
                    amp[j] = params[j] + step[j];
                    amm[i] = params[i] - step[i];
                    amm[j] = params[j] - step[j];
                    double term1 = (LSE(function, app, data) - LSE(function, apm, data)) / (2 * step[j]);
                    double term2 = (LSE(function, amp, data) - LSE(function, amm, data)) / (2 * step[j]);
                    hessian[i][j] = term1 - term2 / (2 * step[i]);
                    hessian[j][i] = hessian[i][j];
                }
            }
        }
        Matrix h = new Matrix(hessian);
        hessian = h.inverse().array;
        return hessian;
    }


    public static double LSE(FunctionModel curve, double[] params, double[][] data) {
        double SSE = 0;
        int datasize = data.length;
        double x, fx;
        for (int i = 0; i < datasize; i++) {
            x = data[i][0];
            fx = curve.compute(x, params);
            SSE += Math.pow(data[i][1] - fx, 2);
        }
        return SSE;
    }
}

