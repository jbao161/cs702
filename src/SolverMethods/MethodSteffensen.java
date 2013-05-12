/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SolverMethods;

import FunctionModel.FunctionModel;

/**
 *
 * @author jbao
 */
public class MethodSteffensen extends SolverMethod {

    public static final boolean DEBUG = true;

    public double solve(Object[] args) {
        FunctionModel function = (FunctionModel) args[0];
        double[] intialGuesses = (double[]) args[1];
        double initialGuess = intialGuesses[0];
        double[] equationParams = (double[]) args[2];

        double guess = Double.NaN;

        // steps 1 and 2: set up main iteration; step 5: increment iteration
        for (int i = 0; i < maxIter; i++) {
            // step 3: compute approximation to solution
            guess = function.compute(initialGuess, equationParams);
            // step 4: evaluate accuracy of approximation
            if (Math.abs(guess - initialGuess) < TOL) {
                if (DEBUG) {
                    System.out.println("after " + (i + 1) + " iterations");
                    System.out.println("solution: " + guess + ", which is the function of " + initialGuess);
                    System.out.println("f(" + guess + ") = " + function.compute(guess, equationParams));
                }
                return guess; // found a suitable solution
            } else {
                double p1 = guess;
                double delta1 = guess - initialGuess;
                double p2 = function.compute(guess, equationParams);
                double delta2 = p2 - 2 * p1 + initialGuess;
                initialGuess = initialGuess - Math.pow(delta1, 2) / p2; // update approximation
            }
        }
        // unsuccessful search
        if (DEBUG) {
            System.out.println("Method failed after " + maxIter + " iterations");
            System.out.println("inadequate solution: " + guess + ", which is the function of " + initialGuess);
        }
        return Double.NaN;
    }
    /* comments:
     * 1. this is a variant of the fixed point method
     * 2. algorithm specified in text looks same as what I wrote for aitkens. ...
     */
}
