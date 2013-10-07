/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780.geometry;

import function.FunctionModel;
import function.MultiFunction;
import numutil.Matrix;
import solvermethods.MethodFalsePosition;
import solvermethods.MethodNewton;
import solvermethods.SolverMethod;

/**
 *
 * @author jbao
 */
public class K4_plane extends Project01 {

    static FunctionModel ft = new FunctionModel() {
        public double compute(double x, double[] params) {
            return t4potential(x);
        }

        public double dcompute(int n, double x, double[] params) {
            return Double.NaN;
        }
    };
    static FunctionModel ftd = new FunctionModel() {
        public double compute(double x, double[] params) {
            double step = 0.01;
            return 0.5 * (t4potential(x + step) - t4potential(x - step));
        }

        public double dcompute(int n, double x, double[] params) {
            double step = 0.01;
            return 0.5 * (compute(x + step, null) - compute(x - step, null));
        }
    };

    public static double[][] t4(double L) {
        double[][] atom_positions = new double[][]{
            {0, 0, 0},
            {L, 0, 0},
            {L * (1 + 1 / Math.sqrt(2)), L / Math.sqrt(2), 0},
            {L / Math.sqrt(2), L / Math.sqrt(2), 0}
        };
        return atom_positions;
    }

    public static double t4potential(double L) {
        double potential = 0;
        double[][] atom_positions = t4(L);
        //potential = get_minized_potential(atom_positions);
        //Matrix printer = new Matrix(atom_positions);
        //printer.print();
        //get_distances(atom_positions);
        potential = get_potential(atom_positions);
        return potential;
    }

    public static double[][] minimize(double[][] atom_positions) {

        /*
         * do minimization here
         */
        int num_iterations = 100;

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
                for (int j = 0; j < num_coordinates; j++) {
                    double x = atom_positions[i][j];
                    double optimalx = MultiNewton.solve(i, j, atom_positions);
                    //System.out.println(optimalx);
                    atom_positions[i][j] = optimalx;
                    if (Math.abs(x - optimalx) > TOL) {
                        good_enough = false;
                    }
                }
            }
            if (good_enough) {
                System.out.println("iterations: " + k);
                break;
            }
        }
        /*
         * 
         */
        return atom_positions;
    }

    public static double get_minized_potential(double[][] atom_positions) {
        double initial_potential = get_potential(atom_positions);
        minimize(atom_positions);
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

    public static void findglobal() {
//        numutil.Plot.plotdata(ft, null, 0.1, 14.4, 14.6, true);
//          u = t4potential(14.5);
        SolverMethod mt = new MethodFalsePosition();
        Object[] arg2 = new Object[3];
        arg2[0] = ftd;
        arg2[1] = new double[]{6, 12};
        arg2[2] = null;
        double min = mt.solve(arg2);
        //System.out.println(min); // 27.352040468015804
//        //f(27.352040468015804) = 0.02284208496971729
        double minpotential = t4potential(min);
        //System.out.println(minpotential); //-72.3027274383776
        System.out.println(ft.compute(min, null));
        double[][] coords = t4(min);
        Matrix printer = new Matrix(coords);
        printer.printdata();
        coords = minimize(coords);
        printer = new Matrix(coords);
        printer.printdata();

        get_distances(coords);

    }

    public static void findglobal2() {
        double[][] ap = t4(6);
        Matrix printer = new Matrix(ap);
        printer.printdata();
        minimize(ap);
        printer = new Matrix(ap);
        printer.printdata();
        get_distances(ap);
        System.out.println(get_potential(ap));
    }

    public static double findglobalsilent(double length) {
        double[][] ap = t4(length);
        minimize(ap);
        return get_potential(ap);
    }

    public static double fgd(double length) {
        double step = 0.1;
        double fplus = findglobalsilent(length + step);
        double fminus = findglobalsilent(length - step);
        double deriv = 0.5 / step * (fplus - fminus);
        return deriv;
    }

    public static void manualsearch() {
        double[][] ap = t4(11);
        Matrix printer = new Matrix();
        printer = new Matrix(ap);
//        printer.printdata();
//        get_distances(ap);
        double[][] ap2 = minimize(ap);
        printer.array = ap2;
        printer.printdata();
        get_distances(ap2);
        double ip = get_potential(ap);
        double fp = get_potential(ap2);
        System.out.println("initial potential: " + ip + "\r\nfinal potential: " + fp);
    }

    public static void main(String[] args) {
        findglobal2();
        //manualsearch();
       
    }
}
