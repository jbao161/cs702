/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780.geometry;

import function.FunctionModel;
import function.MultiFunction;
import numutil.Matrix;
import solvermethods.MethodNewton;
import solvermethods.SolverMethod;

/**
 *
 * @author jbao
 */
public class Project01_N4 {

    static double alpha = 19.30;
    static double epsilon = 1.51;
    static double p = 10.58;
    static double q = 1.34;
    static double r_0 = 8.253;
    static double step_size = 0.01; // in the xyz coordianate for computing derivatives
    static double TOL = 0.0005;

    public static double get_distance(double[] position1, double[] position2) {
        double distance = 0;
        int num_dimensions = position1.length;
        for (int i = 0; i < num_dimensions; i++) {
            distance += Math.pow(position1[i] - position2[i], 2);
        }
        return Math.sqrt(distance);
    }

    public static double get_repulsion(int num_atoms, double[][] atom_positions) {
        double potential = 0;
        for (int i = 0; i < num_atoms; i++) {
            double inner_sum = 0;
            for (int j = 0; j < num_atoms; j++) {
                if (j != i) {
                    double distance = get_distance(atom_positions[i], atom_positions[j]);
                    inner_sum += Math.exp(-p * ((distance / r_0) - 1));
                }
            }
            inner_sum *= epsilon;
            potential += inner_sum;
        }
        return potential;
    }

    public static double get_potential(double[][] atom_positions) {
        int num_atoms = atom_positions.length;
        double u_rep = get_repulsion(num_atoms, atom_positions);
        double u_el = 0;
        double term1;
        for (int i = 0; i < num_atoms; i++) {
            double term2 = 0;
            for (int j = 0; j < num_atoms; j++) {
                if (j != i) {
                    double distance = get_distance(atom_positions[i], atom_positions[j]);
                    double expterm = Math.exp(-2 * q * (distance / r_0 - 1));
                    term1 = Math.pow(alpha, 2) * expterm;
                    term2 += term1;
                }
            }
            term2 = Math.sqrt(term2);
            u_el += term2;
        }
        return u_rep - u_el;
    }

    public static double[][] get_jacobian(MultiFunction fxyz, double[][] atom_positions) {
        int num_atoms = atom_positions.length;
        double[][] result = new double[num_atoms][num_atoms];
        for (int i = 0; i < num_atoms; i++) {
            int num_coordinates = atom_positions[i].length;
            double[] variables = atom_positions[i];
            double[] params = null;
            for (int j = 0; j < num_coordinates; j++) {

                double dfdj = fxyz.dcompute(i, j, step_size, atom_positions);
                result[i][j] = dfdj;
            }
        }
        return result;
    }

   

    public static void get_distances(double[][] atom_positions) {
        int num_atoms = atom_positions.length;
        System.out.println("distances:");
        for (int i = 0; i < num_atoms - 1; i++) {
            double distance = get_distance(atom_positions[i], atom_positions[i + 1]);
            System.out.println(distance);
        }
        double distance = get_distance(atom_positions[num_atoms - 1], atom_positions[0]);
        System.out.println(distance);
    }
}
