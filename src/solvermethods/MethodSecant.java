/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package solvermethods1;

import function.FunctionModel;

/**
 *
 * @author jbao
 */
public class MethodSecant extends SolverMethod {

    public static final boolean DEBUG = true;

    @Override
    public double solve(Object[] args) {

        FunctionModel function = (FunctionModel) args[0];
        double[] intialGuesses = (double[]) args[1];
        double x0 = intialGuesses[0];
        double x1 = intialGuesses[1];
        double[] equationParams = (double[]) args[2];

        double fx0 = function.compute(x0, equationParams);
        double fx1 = function.compute(x1, equationParams);
        double derivative = Double.NaN;
        double convergenceCriterion = Double.NaN;
        double solution = Double.NaN;
        if (fx0 == fx1) {
            if (DEBUG) {
                System.out.println("ERROR: invalid input. Initial guesses must produce different outputs: f(x0) = " + fx0 + " f(x1) = " + fx1);
                return Double.NaN;
            }
        }
        for (int i = 0; i < maxIter; i++) {
            derivative = fx1 - fx0 / x1 - x0; // numerical approximation to f'(x)
            solution = x0 - fx0 / derivative;
            convergenceCriterion = Math.abs(solution - x0) / Math.abs(solution);
            if (solution == 0 || convergenceCriterion < TOL) {
                if (DEBUG) {
                    System.out.println("after " + (i + 1) + " iterations");
                    System.out.println("solution: " + solution);
                    System.out.println("f(" + solution + ") = " + function.compute(solution, equationParams));
                }
                return solution;
            }
            // update the four point approximators
            x1 = solution;
            x0 = x1;
            fx0 = fx1;
            fx1 = function.compute(solution, equationParams);

        }
        if (DEBUG) {
            System.out.println("Method failed after " + maxIter + " iterations");
            System.out.println("inadequate solution: " + solution + ", which maps to f(x) = " + function.compute(solution, equationParams));
        }
        return Double.NaN;
    }
    /* comments:
     * 1. this is just newton's method with an numerically computed derivative
     * 2. derivative never has a divide by zero error, because the 
     */
}
