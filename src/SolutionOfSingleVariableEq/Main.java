/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SolutionOfSingleVariableEq;

import EquationMethods.MethodBisection;
import FunctionModel.FunctionModel;

/**
 *
 * @author jbao
 */
public class Main {

    public static void main(String[] args) {
        FunctionModel eq = new FunctionModel() {
            public double computeFunction(double x, double[] p) {
                return Math.sin(2 * x);
            }
        };
        MethodBisection b = new MethodBisection();
        b.TOL = 1e-5;
        b.maxIter = 1000;
        double solution = b.solve(eq, -1, 1, null);


    }
}
