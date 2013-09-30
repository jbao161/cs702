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
public class hw04 {

    public static double TOL = 1e-1;
    public static int max_iter = (int) 50;

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

        for (int i = 0; i < max_iter; i++) {
            init_params = crude(curve, init_params, init_step, data);
            numutil.MathTools.printdata(init_params);
        }
        double[] plotparams = new double[]{
            435.8354122965868, 0.029848660860493616, 149.70774217847804, 25.089274089348063};
        numutil.Plot.plot_datafit(curve, plotparams, data, 0, 435.7, 436, true);

        //init_params = new double[]{434.83537392666886, 0.03108379107191143, 149.70548215717258, 23.301493572735644};

        //numutil.MathTools.printdata(params);
        //double[][] hessian = newton(curve, init_params, init_step, data);
        //Matrix printer = new Matrix(hessian);
        //printer.print();
        //numutil.Plot.plot_datafit(curve, init_params, data, 0, 435.7, 436, true);
        ///params = LM2.nl_fit(curve, init_params, data); 
    }

    public static double[] crude(FunctionModel curve, double[] prev_params, double[] stepsize, double[][] data) {
        int psize = prev_params.length;
        double[] a_plus = new double[psize], a_minus = new double[psize];
        double sse_p, sse_m, sse;
        sse = LSE(curve, prev_params, data);
        sse_m = sse;
        double[] save = new double[psize];
        double savesse = sse;
        // for each parameter find its next estimate
        for (int i = 0; i < psize; i++) {
            // change only one parameter at a time
            for (int k = 0; k < psize; k++) {
                if (k == i) {
                    a_plus[i] = prev_params[i] + stepsize[i];
                    a_minus[i] = prev_params[i] - stepsize[i];
                } else {
                    a_plus[k] = prev_params[k];
                    a_minus[k] = prev_params[k];
                }
            }
            // evaluate the SSE as one parameter varies
            sse_p = LSE(curve, a_plus, data);
            sse = LSE(curve, prev_params, data);
            sse_m = LSE(curve, a_minus, data);
            // approximate derivative in SSE w\r to parameters with central difference
            save[i] = prev_params[i];
            prev_params[i] = prev_params[i] - 0.5 * stepsize[i] * (sse_p - sse_m) / (sse_p - 2 * sse + sse_m);

            // take smaller steps in blind hope for convergence
            stepsize[i] = 0.5 * stepsize[i];
        }
        double testsse = LSE(curve, prev_params, data);
        if (testsse > savesse) {
            prev_params = save;
            return prev_params;
        }
        System.out.println("sse:" + sse);
        return prev_params;
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

    public static double[] crude2(FunctionModel curve, double[] params, double[] stepsize, double[][] data) {
        int psize = params.length;
        double[] a_plus = new double[psize], a_minus = new double[psize];
        double sse_p, sse_m, sse;
        sse = LSE(curve, params, data);
        sse_m = sse;
        double[] prev_params = numutil.MathTools.Copy(params);
        double[] next_params = numutil.MathTools.Copy(params);
        for (int j = 0; j < max_iter; j++) {

            // for each parameter find its next estimate
            for (int i = 0; i < psize; i++) {
                // change only one parameter at a time
                for (int k = 0; k < psize; k++) {
                    if (k == i) {
                        a_plus[i] = prev_params[i] + stepsize[i];
                        a_minus[i] = prev_params[i] - stepsize[i];
                    } else {
                        a_plus[k] = prev_params[k];
                        a_minus[k] = prev_params[k];
                    }
                }
                // evaluate the SSE as one parameter varies
                sse_p = LSE(curve, a_plus, data);
                sse = LSE(curve, prev_params, data);
                sse_m = LSE(curve, a_minus, data);
                // approximate derivative in SSE w\r to parameters with central difference
                next_params[i] = prev_params[i] - 0.5 * stepsize[i] * (sse_p - sse_m) / (sse_p - 2 * sse + sse_m);
                // take smaller steps in blind hope for convergence
                //stepsize[i] = 0.5 * stepsize[i];
            }
            int flag = 0;
            for (int i = 0; i < psize; i++) {
                if (Math.abs((prev_params[i] - next_params[i]) / prev_params[i]) < TOL) {
                    flag++;
                }
            }
            if (flag == psize) {
                return next_params;
            }
            System.out.println(Arrays.toString(next_params));
            prev_params = numutil.MathTools.Copy(next_params);
        }
        return null;
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
