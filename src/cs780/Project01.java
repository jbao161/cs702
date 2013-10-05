/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

import function.FunctionModel;

/**
 *
 * @author jbao
 */
public class Project01 {

    static double alpha = 19.30;
    static double epsilon = 1.51;
    static double p = 10.58;
    static double q = 1.34;
    static double r_0 = 8.253;

    public static void equilateral() {
        double length_start = 5.5;
        double length_end = 25;
        int num_points = 100;
        int num_atoms = 3;
        double[] params = new double[1];
        params[0] = num_atoms;
        double increment = (length_end - length_start) / num_points;
        for (int i = 0; i < num_points; i++) {
            double distance = length_start + i * increment;
            double potential = potential(num_atoms, distance);
            System.out.println(potential);
        }
        FunctionModel plotter = new FunctionModel() {
            public double compute(double x, double[] params) {
                return potential((int) params[0], x);
            }

            public double dcompute(int n, double x, double[] params) {
                return Double.NaN;
            }
        };
        numutil.Plot.plot(plotter, params, 0, length_start, length_end, true);
    }

    public static double potential(int num_atoms, double length) {
        double u_rep = U_rep(num_atoms, length);
        double u_el = 0;
        double term1;
        for (int i = 0; i < num_atoms; i++) {
            double term2 = 0;
            for (int j = 0; j < num_atoms; j++) {
                if (j != i) {
                    double expterm = Math.exp(-2 * q * (length / r_0 - 1));
                    term1 = Math.pow(alpha, 2) * expterm;
                    term2 += term1;
                }
            }
            term2 = Math.sqrt(term2);
            u_el += term2;
        }
        return u_rep - u_el;
    }

    public static void test_potential() {
        double result = potential(3, 8.253); // 19.3^2 = 372 * 3 = 745 sqrt = 27.29 * 3 = 81.8. so should be 9-81.8 = 72.8
        System.out.println(result);
    }

// repulsive potential
    public static double U_rep(int num_atoms, double length) {
        double potential = 0;
        for (int i = 0; i < num_atoms; i++) {
            double inner_sum = 0;
            for (int j = 0; j < num_atoms; j++) {
                if (j != i) {
                    inner_sum += Math.exp(-p * ((length / r_0) - 1));
                }
            }
            inner_sum *= epsilon;
            potential += inner_sum;
        }
        return potential;
    }

    public static void test_U_rep() {
        double urep = U_rep(3, 8.253); // should be about 9
        System.out.println(urep);
    }

    public static void tests() {
        test_U_rep();
        test_potential();
    }

    public static void main(String[] args) {
        //tests();
        //equilateral();
    }

    public static double distance(double[] position1, double[] position2) {
        double distance = 0;
        int num_dimensions = position1.length;
        for (int i = 0; i < num_dimensions; i++) {
            distance += Math.sqrt(Math.pow(position1[i] - position2[i], 2));
        }
        return distance;
    }
    
    
}
