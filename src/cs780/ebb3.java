/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

import numutil.Matrix;
import function.FunctionModel;

/**
 *
 * @author jbao
 */
public class ebb3 {

    public static boolean DEBUG = true;
    public static int max_iter = (int) 1e3;
    public static double TOL = 5e-9;

    public static void main(String[] args) {
        function.FunctionModel ex = new function.FunctionModel() {
            @Override
            public double compute(double input, double[] params) {
                return params[0] + params[1] * Math.pow(input, params[2]);
            }

            @Override
            public double dcompute(int derivative, double input, double[] params) {
                return params[1] * params[2] * Math.pow(input, params[2]);
            }
        };
        double[] intensity = {
            0.001,
            0.003,
            0.0055,
            0.01,
            0.03,
            0.072,
            0.139,
            0.1,
            0.24,
            0.2,
            0.3,
            0.469,
            0.4,
            0.5
        };
        double[] concentration = {
            1.81945517,
            1.84768883,
            1.85922616,
            1.87857847,
            1.94678403,
            2.04153268,
            2.11669564,
            2.10190186,
            2.20970561,
            2.18723323,
            2.40119457,
            2.3130484,
            2.34998155,
            2.38172234
        };
        int numDataPts = intensity.length;
        double[][] data = new double[numDataPts][2];
        for (int i = 0; i < numDataPts; i++) {
            data[i][1] = concentration[i];
            data[i][0] = intensity[i];
        }
        double[] params = {1.7, 0.8, 0.2};
        get_sse(ex, params, data);
        params = new double[]{1.5, 0.8, 0.2};
        double sse_next = get_sse(ex, params, data);
        double[] params_next = params;
        System.out.println("0,"+sse_next+","+params_next[0]+","+params_next[1]+","+params_next[2]);
        double[] params_fitted = nl_fit(ex, params, data);
        numutil.MathTools.print(params_fitted);
    }

    public static double get_sse(FunctionModel function, double[] params, double[][] data) {
        double sse = 0;
        double x, y, fx;
        for (int i = 0; i < data.length; i++) {
            x = data[i][0];
            //System.out.println("x:"+x);
            y = data[i][1];
            //System.out.println("y:"+y);
            fx = function.compute(x, params);
            //System.out.println("fx:"+fx);
            //System.out.println("fx minus y:"+(fx -y));
            sse += Math.pow(fx - y, 2);
        }
        //System.out.println("get_sse:");
        //numutil.MathTools.print(params);
        //System.out.println("sse: " + sse + "\n");
        return sse;
    }

    public static double[] nl_fit(FunctionModel function, double[] params, double[][] data) {
        double dx = 0.01;
        int itersub_max = 1000;
        int iter;
        double[] params_prev = params;
        double[] params_next;
        double lambda = .001;
        double sse_prev, sse_next;
        sse_prev = get_sse(function, params_prev, data);

        for (int i = 1; i < max_iter; i++) {
            //System.out.println("sse_prev: " + sse_prev + "\n");
            double[] residuals = get_residuals(function, params_prev, data);
            // 1. approximate the jacobian
            double[][] jacobian = get_jacobian(function, params_prev, dx, data);
            //Matrix printer = new Matrix(jacobian);
            // printer.print();
            // 2. get the new params
            params_next = get_updatedparams(jacobian, params_prev, lambda, residuals);
            // printer = new Matrix(dx);
            //printer.print();
            sse_next = get_sse(function, params_next, data);
            
            iter = 0;
            while (sse_next >= sse_prev) {
                iter++;
                if (iter >= itersub_max) {
                    return null;
                }
                lambda *= 10;

                params_next = get_updatedparams(jacobian, params_prev, lambda, residuals);
                //numutil.MathTools.print(params_next);
                sse_next = get_sse(function, params_next, data);
                //System.out.println("sse_next: "+ sse_next + "\n");
            }

            lambda /= 10;
            int flag = 0;
            for (int k = 0; k < params.length; k++) {
                if (Math.abs(params_next[k] - params_prev[k]) / params_prev[k] < TOL) {
                    flag++;
                }
            }
            if (flag == params.length) {
                return params_next;
            }
            sse_prev = sse_next;
            params_prev = params_next;
            System.out.println(i+","+sse_next+","+params_next[0]+","+params_next[1]+","+params_next[2]);
        }
        return null;
    }

    public static double[] get_residuals(FunctionModel function, double[] params, double[][] data) {
        double[] result = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = Math.abs(data[i][1] - function.compute(data[i][0], params));
        }
        return result;
    }

    public static double[] get_updatedparams(double[][] jacobian, double[] params, double lambda, double[] residuals) {
        int num_params = params.length;
        int num_datapts = jacobian.length;
        Matrix jacobiM = new Matrix(jacobian);
        Matrix jacobiT = jacobiM.transpose();
        Matrix H = jacobiT.multiply(jacobiM);
        Matrix w = new Matrix(residuals);
        Matrix gradient = jacobiT.multiply(w);
        Matrix m1 = new Matrix(Matrix.Diagonal(H.array));
        m1 = m1.multiply(lambda);
        m1 = m1.add(H);
        m1 = m1.inverse();
        m1 = m1.multiply(gradient);
        double[] result = new double[params.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = params[i] + m1.array[i][0];
        }
        return result;
    }

    public static double[][] get_jacobian(FunctionModel function, double[] params, double step_size, double[][] data) {
        int num_datapts = data.length;
        int num_params = params.length;
        double[][] jacobian = new double[num_datapts][num_params];
        double data_y, data_x, residual, residual_p, diff_y, diff_p;

        // the jacobian are the first derivatives of the components of the [y - f(x, params)) vector for each data point y
        for (int i = 0; i < num_datapts; i++) {

            // dd1/da. dd1/db, etc
            data_x = data[i][0];
            data_y = data[i][1];
            residual = Math.abs(data_y - function.compute(data_x, params));
            for (int j = 0; j < num_params; j++) {
                double[] dx = numutil.MathTools.Copy(params);
                dx[j] += params[j] * step_size;
                residual_p = Math.abs(data_y - function.compute(data_x, dx));
                diff_y = residual - residual_p; // positive direction is towards minimizing residual
                diff_p = dx[j] - params[j];
                jacobian[i][j] = diff_y / diff_p;
            }
        }

        return jacobian;
    }
}
