/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

import numutil.Matrix;
import function.FunctionModel;
import java.util.Arrays;

/**
 *
 * @author jbao
 */
public class LM2 {

    public static boolean DEBUG = true;
    public static int max_iter = (int) 1e3;
    public static double TOL = 5e-3;

    public static void main(String[] args) {
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
        double[] dx = {0.001, 1, 1, 1};
        int itersub_max = (int) 1e5;
        int iter;
        double[] params_prev = params;
        double[] params_next;
        double lambda = .01;
        double sse_prev, sse_next;
        sse_prev = get_sse(function, params_prev, data);
        System.out.println("sse_prev first:" + sse_prev);

        for (int i = 1; i < max_iter; i++) {
            //System.out.println("sse_prev: " + sse_prev + "\n");
            double[] residuals = get_residuals(function, params_prev, data);
            // 1. approximate the jacobian
            double[][] jacobian = get_jacobian(function, params_prev, dx, data);
            Matrix printer = new Matrix(jacobian);
            printer.print();
            // 2. get the new params
            params_next = get_updatedparams(jacobian, params_prev, lambda, residuals);
            System.out.println("pnext: " + Arrays.toString(params_next));
            sse_next = get_sse(function, params_next, data);
            System.out.println("next jacobian sse_next:" + sse_next);
            iter = 0;
            while (sse_next > sse_prev) {
                iter++;
                lambda *= 10;
                params_next = get_updatedparams(jacobian, params_prev, lambda, residuals);
                numutil.MathTools.print(params_next);
                sse_next = get_sse(function, params_next, data);
                System.out.println("lambda loop sse_next: " + sse_next + "\n");
            }

            lambda /= 10;
            int flag = 0; // if all the parameters match TOL, return the next parameter
            for (int k = 0; k < params.length; k++) {
                if (Math.abs(params_next[k] - params_prev[k]) < TOL) {
                    flag++;
                }
            }
            if (flag == params.length) {
                return params_next;
            }

            System.out.println(i + "," + sse_next + "," + params_next[0] + "," + params_next[1] + "," + params_next[2]);
            sse_prev = sse_next;
            params_prev = params_next;
            numutil.MathTools.print(params_next);
            System.out.println("");
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

    public static double[][] get_jacobian(FunctionModel function, double[] params, double[] step_size, double[][] data) {
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
            double[] a_plus = new double[num_params], a_minus = new double[num_params];
            for (int j = 0; j < num_params; j++) {
                double[] dx = numutil.MathTools.Copy(params);
                dx[j] += params[j] + step_size[j];
                residual_p = Math.abs(data_y - function.compute(data_x, dx));
                diff_y = residual - residual_p; // positive direction is towards minimizing residual
                diff_p = dx[j] - params[j];
                jacobian[i][j] = diff_y / diff_p;
                // prevent divide by zero
                if (jacobian[i][j] == 0) {
                    jacobian[i][j] = 1e-15;
                }
            }
        }

        return jacobian;
    }
}
