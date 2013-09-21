/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

import function.FunctionModel;
import java.util.Arrays;

/**
 * using the assigned matlab style algorithm as presented by Devries Hasbun.
 * had to add modifications on top of original functions to make a least squares fit
 * comments explain what Devries Hasbun did and do not reflect author's choice for coding!
 * @author jbao
 */
public class hw04 {

    public static double TOL = 1e-9;
    public static int max_iter = (int) 1e1;

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

        double[] params = crude(curve, init_params, init_step, data);
        System.out.println(Arrays.toString(params));
    }

    public static double[] crude(FunctionModel curve, double[] params, double[] stepsize, double[][] data) {
        int psize = params.length;
        double[] a_plus = new double[psize], a_minus = new double[psize];
        double sse_p, sse_m, sse;
        sse = LSE(curve, params, data);
        sse_m = sse;
        for (int j = 0; j < max_iter; j++) {
            // for each parameter find its next estimate
            for (int i = 0; i < psize; i++) {
                // change only one parameter at a time
                for (int k = 0; k < psize; k++) {
                    if (k == i) {
                        a_plus[i] = params[i] + stepsize[i];
                        a_minus[i] = params[i] - stepsize[i];
                    } else {
                        a_plus[k] = params[k];
                        a_minus[k] = params[k];
                    }
                }
                // evaluate the SSE as one parameter varies
                sse_p = LSE(curve, a_plus, data);
                sse = LSE(curve, params, data);
                sse_m = LSE(curve, a_minus, data);
                // approximate derivative in SSE w\r to parameters with central difference
                params[i] = params[i] - 0.5 * stepsize[i] * (sse_p - sse_m) / (sse_p - 2 * sse + sse_m);
                // take smaller steps in blind hope for convergence
                stepsize[i] = 0.5 * stepsize[i];
            }
        }
        return params;
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
