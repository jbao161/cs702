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
public class MethodFixedPoint extends SolverMethod {

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
                initialGuess = guess; // update approximation
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
     * 1. assumes a continuous function bounded within a square (f(a) <= a, f(b) <= b) where the initial guess lies in the interval (a,b)
     * 2. the equation is of the form x = f(x), exploiting the fact that the line y = x must intersect the function within the square
     * 3. a solution is unique if the derivative of the function within the square is strictly bounded by the derivative of the intersecting function, i.e. the line y = x; in other words, if |d/dx (f(x)| < 1. 
     * 4. the rate of convergence can be (very conservatively) approximated by (a) |solution - initialGuess| <= maxDerivative^numIterations * |a-b|/2 or (b) |solution - initialGuess| <= maxDerivative^numIterations / ( 1 - maxDerivative) * |firstIterationValue - initialGuess|
     * 5. the rate of convergence is basically using that the max derivative < 1, so repeatedly multiplying it against the max distance within domain will arbitrarily minimize the error. (really slowly.) the second expression comes from a triangle inequality of distance from inital guess to solution and a geometric series of maxDerivative^i
     * 6. because the convergence criterion is really poor, we should choose a function with as small a derivative as possible if we have a choice (for example, if we have x^2 = expression we can use a f(x) that is + or -)
     * 7. we pick one point, not an interval for initial guess
     */
}
