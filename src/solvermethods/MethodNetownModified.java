/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package solvermethods;

import function.FunctionModel;

/**
 *
 * @author jbao
 */
public class MethodNetownModified extends SolverMethod {

    public static final boolean DEBUG = true;

    @Override
    public double solve(Object[] args) {

        FunctionModel function = (FunctionModel) args[0];
        double[] intialGuesses = (double[]) args[1];
        double initialGuess = intialGuesses[0];
        double[] equationParams = (double[]) args[2];

        double nextGuess = Double.NaN;
        double fx = Double.NaN;
        double convergenceCriterion = Double.NaN;
        double derivative = Double.NaN;
        double modifiedTerm = Double.NaN;
        double fd2 = Double.NaN;

        for (int i = 0; i <= maxIter; i++) { // i<= maxIter because results of nth iteration are not checked until the n+1th
            fx = function.compute(initialGuess, equationParams);
            if (fx == 0 || convergenceCriterion < TOL) { // NaN < TOL == false
                if (DEBUG) {
                    System.out.println("after " + i + " iterations"); // if initial guess is correct, then iterations = 0
                    System.out.println("solution: " + initialGuess);
                    System.out.println("f(" + initialGuess + ") = " + function.compute(initialGuess, equationParams));
                }
                return initialGuess;
            }
            derivative = function.dcompute(1,initialGuess, equationParams);
            if (derivative == 0) {
                if (DEBUG) {
                    System.out.println("ERROR: at iteration " + (i + 1) + ", derivative " + derivative + " of f(" + initialGuess + ") became zero. (cannot divide by zero).");
                }
                return Double.NaN;
            }
            fd2 = function.dcompute(2,initialGuess, equationParams); 
            modifiedTerm =Math.pow(derivative,2)-fx*fd2;
            nextGuess = initialGuess - fx *derivative/modifiedTerm;
            convergenceCriterion = Math.abs(nextGuess - initialGuess) / Math.abs(nextGuess);
            initialGuess = nextGuess;

        }
        if (DEBUG) {
            System.out.println("Method failed after " + maxIter + " iterations");
            System.out.println("inadequate solution: " + nextGuess + ", which maps to f(x) = " + function.compute(nextGuess, equationParams));
        }
        return Double.NaN;
    }
    /* comments:
     * 1. we solve f(x) = f(a) + (x-a) d/dx f(a) + error, for f(x) = 0
     * 2. the updated solution uses a subtraction, because f(a)/f'(a) +(x-a) = 0 gives x = a - f(a)/f'(a)
     * 3. linear approximation assumes higher order derivatives are small compared to f'(a)
     * 4. method fails if a derivative equals zero due to divide by zero error
     * 5. initial guess must be 'sufficiently close' to solution for convergence
     * 6. consecutive derivatives decrease to zero, so rate of convergence increases with each iteration
     * 
     */
}
