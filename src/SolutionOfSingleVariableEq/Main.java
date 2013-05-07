/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SolutionOfSingleVariableEq;

import SolverMethods.*;
import FunctionModel.*;

/**
 *
 * @author jbao
 */
public class Main {

    public static void main(String[] args) {
        // iteration method
        MethodBisection bisect = new MethodBisection();
        MethodFixedPoint fixed = new MethodFixedPoint();
        MethodNewton newton = new MethodNewton();
        MethodSecant secant = new MethodSecant();
        MethodFalsePosition falseposition = new MethodFalsePosition();

        // default functions
        ModelPolynomial poly = new ModelPolynomial();

/// assign your variables here!
        // custom defined function (optional)
        FunctionModel custom = new FunctionModel() {
            public double compute(double x, double[] p) {
                return Math.cos(x);
            }

            public double dcompute(double x, double[] equationParams) {
                return -Math.sin(x);
            }
        };
        //the search method, function, intial guess, and parameters here:
        SolverMethod solver = falseposition;
        FunctionModel function = custom;
        double[] functionParams = {0, 3, 4, -4};
        double[] initialGuess = {0, -2};
        // convergence criterion
        solver.TOL = 1e-8;
        solver.maxIter = 10e8;
///
        // solution
        double solution;
        Object[] solveInputs = {function, initialGuess, functionParams};
        //solution = solver.solve(solveInputs);
        solution = falseposition.solve(new Object[]{function, initialGuess, functionParams});
    }
}
