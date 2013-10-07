/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780.geometry;

import function.FunctionModel;
import numutil.Matrix;
import solvermethods.MethodBisection;
import solvermethods.MethodFalsePosition;
import solvermethods.MethodNewton;
import solvermethods.SolverMethod;

/**
 *
 * @author jbao
 */
public class K4_Tetra extends Project01_N4 {

    static double[][] ap_stored;

    public static double[][] t4(double L) {
        double[][] atom_positions = new double[][]{
            {L, 0, -Math.sqrt(2) * 0.5 * L},
            {-L, 0, -Math.sqrt(2) * 0.5 * L},
            {0, L, Math.sqrt(2) * 0.5 * L},
            {0, -L, Math.sqrt(2) * 0.5 * L}
        };
        return atom_positions;
    }

    public static double t4potential(double L) {
        double potential = 0;
        double[][] atom_positions = t4(L);
        potential = get_minized_potential(atom_positions);
        //Matrix printer = new Matrix(atom_positions);
        //printer.print();
        //get_distances(atom_positions);
        return potential;
    }

  

    public static void main(String[] args) {
        // double u = t4potential(5.1);
        // System.out.println(u);
        FunctionModel ft = new FunctionModel() {
            public double compute(double x, double[] params) {
                return t4potential(x);
            }

            public double dcompute(int n, double x, double[] params) {
                return Double.NaN;
            }
        };
        FunctionModel ftd = new FunctionModel() {
            public double compute(double x, double[] params) {
                double step = 0.01;
                return 0.5 * (t4potential(x + step) - t4potential(x - step));
            }

            public double dcompute(int n, double x, double[] params) {
                double step = 0.01;
                return 0.5 * (compute(x + step, null) - compute(x - step, null));
            }
        };
//        numutil.Plot.plotdata(ft, null, 0.1, 14.4, 14.6, true);
//          u = t4potential(14.5);
        SolverMethod mt = new MethodFalsePosition();
        Object[] arg2 = new Object[3];
        arg2[0] = ftd;
        arg2[1] = new double[]{2, 6};
        arg2[2] = null;
        double min = mt.solve(arg2);
        //System.out.println(min); // 27.352040468015804
//        //f(27.352040468015804) = 0.02284208496971729
        double minpotential = t4potential(min);
        //System.out.println(minpotential); //-72.3027274383776
        System.out.println(ft.compute(min, null));
        double[][] coords = t4(min);
        coords = minimize(coords);
        Matrix printer = new Matrix(coords);
        printer.printdata();

        get_distances(coords);
    }
}
