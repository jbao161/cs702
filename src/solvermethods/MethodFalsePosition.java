/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package solvermethods;

import function.FunctionModel;
import numutil.MathTools;
/**
 *
 * @author jbao
 */
public class MethodFalsePosition extends SolverMethod {

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
        // some mild input checking
        boolean breakout = false;
        if (DEBUG) {
            System.out.println("f("+x0+") = " + fx0);
            System.out.println("f("+x1+") = " + fx1);
        }
        if (Double.isInfinite(fx0) || Double.isNaN(fx0)) {
            System.out.println("Invalid Input: " + x0 + ". f(low guess) is not a valid number!");
            breakout = true;
        }
        if (Double.isInfinite(fx1) || Double.isNaN(fx1)) {
            System.out.println("Invalid Input: " + x1 + ". f(high guess) is not a valid number!");
            breakout = true;
        }
        if (MathTools.sign(fx0) * MathTools.sign(fx1) >= 0) {
            System.out.println("Invalid Inputs: " + x0 + ", " + x1 + ". f(low guess) and f(high guess) must have opposite sign!");
            breakout = true;
        }
        if (breakout) {
            return Double.NaN;
        }


        double solution = Double.NaN;
        double fsolution = Double.NaN;
        for (int i = 1; i < maxIter; i++) {
            solution = x1 - fx1 * (x1 - x0) / (fx1 - fx0);
            if (Math.abs(solution - x1) < TOL) {
                if (DEBUG) {
                    System.out.println("after " + i + " iterations"); // if initial guess is correct, then iterations = 0
                    System.out.println("solution: " + solution);
                    System.out.println("f(" + solution + ") = " + function.compute(solution, equationParams));
                }
                return solution;
            }
            fsolution = function.compute(solution, equationParams);
            if (MathTools.sign(fsolution) * MathTools.sign(fx1) < 0) {
                x0 = solution;
                fx0 = fsolution;
                
            } else{
            x1 = solution;
            fx1 = fsolution;}
        }
        if (DEBUG) {
            System.out.println("Method failed after " + maxIter + " iterations");
            System.out.println("inadequate solution: " + solution + ", which maps to f(x) = " + function.compute(solution, equationParams));
        }
        return Double.NaN;
    }
    /* comments:
     * 1. the approximation will lie between the two initial guesses, because we have set up them to straddle the zero that we are trying to find
     * 2. if fsolution and fx1 have same sign, then the solution lies to the left of the approximation
     *    we change the end boundary of the interval to the approximation point
     * 3. if fsolution and fx1 have opposite sign, then the solution lies to the right of the approximation
     *    we reverse the direction of the interval and set the start boundary equal to the end boundary and the end boundary equal to the approximation point
     * 4. why did the algorithm in the textbook reverse the interval? personal preference of the textbook author. setting the start or end bounds like in the bisection method is just as good.
     *    reversing the interval is just another way that works, but it doesn't do anything special.
     * 5. this is just the bisection method using a secant method approximation point instead of the midpoint
     * 6. not an efficient algorithm
     */
}
