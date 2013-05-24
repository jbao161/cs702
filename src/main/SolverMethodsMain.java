/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import function.ModelPolynomial;
import function.FunctionModel;
import solvermethods.MethodFixedPoint;
import solvermethods.SolverMethod;
import solvermethods.MethodNewton;
import solvermethods.MethodSecant;
import solvermethods.MethodBisection;
import solvermethods.MethodFalsePosition;
import solvermethods.MethodNetownModified;

/**
 *
 * @author jbao
 */
public class SolverMethodsMain {

    public static void main(String[] args) {
        // iteration method
        MethodBisection bisect = new MethodBisection();
        MethodFixedPoint fixed = new MethodFixedPoint();
        MethodNewton newton = new MethodNewton();
        MethodNetownModified newton2 = new MethodNetownModified();
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

            public double dcompute(int deriv, double x, double[] equationParams) {
                return -Math.sin(x);
            }
        };
        //the search method, function, intial guess, and parameters here:
        SolverMethod solver = newton;
        FunctionModel function = poly;
        double[] functionParams = {-10, 0, 4, 1};
        double[] initialGuess = {0, 2};
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
