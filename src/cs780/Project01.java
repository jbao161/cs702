/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

import function.FunctionModel;
import function.MultiFunction;
import numutil.Matrix;
import solvermethods.MethodBisection;
import solvermethods.MethodNewton;
import solvermethods.SolverMethod;

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
    static double step_size = 0.01; // in the xyz coordianate for computing derivatives
    static double TOL = 0.0005;

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
            double potential = get_trianglepotential(num_atoms, distance);
            System.out.println(potential);
        }
        FunctionModel plotter = new FunctionModel() {
            public double compute(double x, double[] params) {
                return get_trianglepotential((int) params[0], x);
            }

            public double dcompute(int n, double x, double[] params) {
                return Double.NaN;
            }
        };
        numutil.Plot.plot(plotter, params, 0, length_start, length_end, true);
    }

    public static double get_trianglepotential(int num_atoms, double length) {
        double u_rep = U_trianglerep(num_atoms, length);
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

// repulsive potential
    public static double U_trianglerep(int num_atoms, double length) {
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

    public static void tests() {
        test_U_rep();
        test_potential();
    }

    public static void test_U_rep() {
        double urep = U_trianglerep(3, 8.253); // should be about 9
        System.out.println(urep);
    }

    public static void test_potential() {
        double result = get_trianglepotential(3, 8.253); // 19.3^2 = 372 * 3 = 745 sqrt = 27.29 * 3 = 81.8. so should be 9-81.8 = -72.8
        System.out.println(result);
    }

    public static void test_getpotential() {
        double distance = 8.253;
        distance = 8.13;
        double L = Math.sqrt(Math.pow(distance, 2) * 0.5);
        double[] atom01 = new double[]{L, 0, 0};
        double[] atom02 = new double[]{0, L, 0};
        double[] atom03 = new double[]{0, 0, L};
        double[][] atom_positions = new double[3][];
        atom_positions[0] = atom01;
        atom_positions[1] = atom02;
        atom_positions[2] = atom03;
        double result = get_potential(atom_positions);
        System.out.println(result);
    }

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

    public static void test_multideriv() {
        double distance = 8.253;
        double lambda = 0.01;
        double L = Math.sqrt(Math.pow(distance, 2) * 0.5); //5.835752265132577
        double[] atom01 = new double[]{L + 1, 0, 1};
        double[] atom02 = new double[]{0, L - 1, 1};
        double[] atom03 = new double[]{0, 1, L + 1};
        double[][] atom_positions = new double[3][];
        atom_positions[0] = atom01;
        atom_positions[1] = atom02;
        atom_positions[2] = atom03;
        double pt = get_potential(atom_positions); // trying to get to -72.8229652614022
        System.out.println(pt);
        MultiFunction potential = new MultiFunction() {
            public double compute(double[][] atom_positions) {
                return get_potential(atom_positions);
            }
        };
        int num_atoms = atom_positions.length;
        int num_iterations = 10;
        for (int k = 0; k < num_iterations; k++) {
            for (int i = 0; i < num_atoms; i++) {
                int num_coordinates = atom_positions[i].length;
                double[] variables = atom_positions[i];
                double[] params = null;
                for (int j = 0; j < num_coordinates; j++) {
                    double[][] jacobian = get_jacobian(potential, atom_positions);
                    Matrix jM = new Matrix(jacobian);
                    jM.print();
                    double coordinate = variables[j];
                    double dfdj = potential.dcompute(i, j, step_size, atom_positions);
                    System.out.println(dfdj);
                    atom_positions[i][j] = coordinate - lambda * dfdj;
                    System.out.println("atom_positions:");
                    Matrix printer = new Matrix(atom_positions);
                    printer.print();

                    double next_pt = get_potential(atom_positions);
                    System.out.println(next_pt);
                    if (next_pt > pt) {
                        atom_positions[i][j] = coordinate;
                        lambda /= 10;
                    } else {
                        lambda *= 10;
                        pt = next_pt;
                    }
                }
            }
        }
        System.out.println("final atom_positions:");
        Matrix printer = new Matrix(atom_positions);
        printer.print();
        double next_pt = get_potential(atom_positions);
        System.out.println(next_pt);
        double dt = get_distance(atom_positions[0], atom_positions[1]);
        System.out.println(dt);
        dt = get_distance(atom_positions[1], atom_positions[2]);
        System.out.println(dt);
        dt = get_distance(atom_positions[2], atom_positions[0]);
        System.out.println(dt);
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

    public static double minimize(double[][] atom_positions) {
        double initial_potential = get_potential(atom_positions);
        /*
         * do minimization here
         */
        int num_iterations = 100;
        double lambda = 0.01;

        double pt = get_potential(atom_positions); // trying to get to -72.8229652614022

        MultiFunction potential = new MultiFunction() {
            public double compute(double[][] atom_positions) {
                return get_potential(atom_positions);
            }
        };
        int num_atoms = atom_positions.length;

        for (int k = 0; k < num_iterations; k++) {
            boolean good_enough = true;
            for (int i = 0; i < num_atoms; i++) {
                int num_coordinates = atom_positions[i].length;
                double[] variables = atom_positions[i];
                double[] params = null;
                for (int j = 0; j < num_coordinates; j++) {
                    double[][] jacobian = get_jacobian(potential, atom_positions);
                    Matrix jM = new Matrix(jacobian);
                    // jM.print();
                    double coordinate = variables[j];
                    double dfdj = potential.dcompute(i, j, step_size, atom_positions);
                    // System.out.println(dfdj);
                    atom_positions[i][j] = coordinate - lambda * dfdj;
                    // System.out.println("atom_positions:");

                    double next_pt = get_potential(atom_positions);
                    // System.out.println(next_pt);
                    if (Math.abs(pt - next_pt) > TOL) {
                        good_enough = false;
                    }
                    if (next_pt > pt) {
                        atom_positions[i][j] = coordinate;
                        lambda /= 10;
                    } else {
                        lambda *= 10;
                        pt = next_pt;
                    }
                }
            }
            if (good_enough) {
                //System.out.println("iterations: " + k);
                break;
            }
        }
        /*
         * 
         */
        double final_potential = get_potential(atom_positions);
        Object[] result = new Object[3];
        result[0] = initial_potential;
        result[1] = final_potential;
        result[2] = atom_positions;
        //System.out.println(initial_potential + "," + final_potential + ",");
        Matrix ap = new Matrix(atom_positions);
        //ap.printdata();
        return final_potential;
    }

    public static void n3() {
        double start = 6;
        double end = 12;
        double increment = 1;
        for (double d = start; d < end; d += increment) {
            double distance = d;
            double lambda = 0.01;
            double L = Math.sqrt(Math.pow(distance, 2) * 0.5); //5.835752265132577
            L = d;
            System.out.println("L: " + L);
            double[] atom01 = new double[]{0, 0, 0};
            double[] atom02 = new double[]{L, 0, 0};
            double[] atom03 = new double[]{2 * L, 0, 0};
            double[][] atom_positions = new double[3][];
            atom_positions[0] = atom01;
            atom_positions[1] = atom02;
            atom_positions[2] = atom03;
            double pt = get_potential(atom_positions); // trying to get to -72.8229652614022
            //System.out.println("initial potential: " + pt);
            System.out.println(pt + ",");
            MultiFunction potential = new MultiFunction() {
                public double compute(double[][] atom_positions) {
                    return get_potential(atom_positions);
                }
            };
            int num_atoms = atom_positions.length;
            int num_iterations = 100;
            for (int k = 0; k < num_iterations; k++) {

                for (int i = 0; i < num_atoms; i++) {
                    int num_coordinates = atom_positions[i].length;
                    double[] variables = atom_positions[i];
                    double[] params = null;
                    for (int j = 0; j < num_coordinates; j++) {
                        double[][] jacobian = get_jacobian(potential, atom_positions);
                        Matrix jM = new Matrix(jacobian);
                        // jM.print();
                        double coordinate = variables[j];
                        double dfdj = potential.dcompute(i, j, step_size, atom_positions);
                        // System.out.println(dfdj);
                        atom_positions[i][j] = coordinate - lambda * dfdj;
                        // System.out.println("atom_positions:");
                        Matrix printer = new Matrix(atom_positions);
                        // printer.print();

                        double next_pt = get_potential(atom_positions);
                        // System.out.println(next_pt);
                        if (next_pt > pt) {
                            atom_positions[i][j] = coordinate;
                            lambda /= 10;
                        } else {
                            lambda *= 10;
                            pt = next_pt;
                        }
                    }
                }
            }
            //System.out.println("final potential: " + next_pt);
            double next_pt = get_potential(atom_positions);
            System.out.println(next_pt + ",");
            System.out.println("final atom_positions:");
            Matrix printer = new Matrix(atom_positions);
            printer.printdata();


            System.out.println("distances:");
            double dt = get_distance(atom_positions[0], atom_positions[1]);
            System.out.println(dt);
            dt = get_distance(atom_positions[1], atom_positions[2]);
            System.out.println(dt);
            dt = get_distance(atom_positions[2], atom_positions[0]);
            System.out.println(dt + "\r\n");
        }
    }

    public static void multimethod() {
        double distance = 8.253;
        double L = Math.sqrt(Math.pow(distance, 2) * 0.5); //5.835752265132577
        double[] atom01 = new double[]{L + 1, 0, 1};
        double[] atom02 = new double[]{0, L - 1, 1};
        double[] atom03 = new double[]{0, 1, L + 1};
        double[][] atom_positions = new double[3][];
        atom_positions[0] = atom01;
        atom_positions[1] = atom02;
        atom_positions[2] = atom03;
        minimize(atom_positions);
    }

    public static double l3potential(double L) {
        double potential = 0;
        double[] atom01 = new double[]{0, 0, 0};
        double[] atom02 = new double[]{L, 0, 0};
        double[] atom03 = new double[]{2 * L, 0, 0};
        double[][] atom_positions = new double[3][];
        atom_positions[0] = atom01;
        atom_positions[1] = atom02;
        atom_positions[2] = atom03;
        potential = minimize(atom_positions);
        return potential;
    }

    public static void min_n3() {
        SolverMethod newton = new MethodBisection();
        FunctionModel potential = new FunctionModel() {
            public double compute(double input, double[] p) {
                double step = 0.01;
                return 0.5 * (l3potential(input + step) - l3potential(input - step)) / step;

            }

            public double dcompute(int n, double input, double[] p) {
                return Double.NaN;
            }
        };
        double me = potential.compute(7, null);
        System.out.println(me);
        me = potential.dcompute(1, 7, null);
        System.out.println(me);
        me = potential.compute(8, null);
        System.out.println(me);
        me = potential.dcompute(1, 8, null);
        System.out.println(me);
        me = potential.compute(9, null);
        System.out.println(me);
        me = potential.dcompute(1, 9, null);
        System.out.println(me);
        Object[] args = new Object[3];
        args[0] = potential;
        args[1] = new double[]{7, 9};
        args[2] = null;
        double min = newton.solve(args);
        System.out.println(min);
        double minpotential = l3potential(min);
        System.out.println(minpotential);
    }

    public static void main(String[] args) {
        //tests();
        //equilateral();
        //test_getpotential();
        //test_multideriv(); // trying to get to -72.8229652614022
        n3();
        //multimethod();
        //min_n3();
    }
    // junk code:
    /*
     *   num_coordinates = atom_positions[atom].length;
     for (int coordinate = 0; coordinate < num_coordinates; coordinate++) {
                
     }
     * for (int atom = 0; atom < num_atoms; atom++) { // for each atom
     for (int i = 0; i < num_atoms; i++) { // get distance to other atoms
     if (i != atom) {
     distance = get_distance(atom_positions[atom], atom_positions[i]);
                    
     }
     }

     }
     * 
     *  for (int i = 0; i < 10; i++) {
     double[][] jacobian = get_jacobian(potential, atom_positions);
     Matrix jM = new Matrix(jacobian);
     jM.print();
     Matrix apM = new Matrix(atom_positions);
     Matrix new_apM = apM.subtract(jM.multiply(lambda));
     atom_positions = new_apM.array;
     Matrix printer = new_apM;
     printer.print();
     }
     */
}
