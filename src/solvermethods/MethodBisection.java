/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package solvermethods1;

import function.FunctionModel;
import numutil1.MathTools;

/**
 *
 * @author jbao
 */
public class MethodBisection extends SolverMethod {

    public static final boolean DEBUG = true;

    @Override
    public double solve(Object[] args) {

        FunctionModel function = (FunctionModel) args[0];
        double[] intialGuesses = (double[]) args[1];
        double lowGuess = intialGuesses[0];
        double highGuess = intialGuesses[1];
        double[] equationParams = (double[]) args[2];

        String[] convergenceMethods = {"Midpoint Ratio", "Half Range"};
        String convergenceMethod = convergenceMethods[0];
        double convergenceCriterion = Double.NaN;
        double precision = Double.NaN;
        double solution = Double.NaN;
        double intervalBot = lowGuess;
        double intervalTop = highGuess;

        double halfRange = Double.NaN;
        double midpoint = Double.NaN;
        double prevMidpoint = midpoint;

        // step 1. initialize comparison
        double functionMidpoint = Double.NaN;
        double functionBot = function.compute(lowGuess, equationParams);
        double functionTop = function.compute(highGuess, equationParams);

        // some mild input checking
        boolean breakout = false;
        if (DEBUG) {
            System.out.println("f(low guess) = " + functionBot);
            System.out.println("f(high guess) = " + functionTop);
        }
        if (Double.isInfinite(functionBot) || Double.isNaN(functionBot)) {
            System.out.println("Invalid Input: " + functionBot + ". f(low guess) is not a valid number!");
            breakout = true;
        }
        if (Double.isInfinite(functionTop) || Double.isNaN(functionTop)) {
            System.out.println("Invalid Input: " + functionTop + ". f(high guess) is not a valid number!");
            breakout = true;
        }
        if (functionBot * functionTop >= 0) {
            System.out.println("Invalid Inputs: " + functionBot + ", " + functionTop + "f(low guess) and f(high guess) must have opposite sign!");
            breakout = true;
        }
        if (breakout) {
            return Double.NaN;
        }

        // step 2. iterate on steps 3-6
        for (int i = 0; i < maxIter;) {
            // step 3. compute midpoint and f(midpoint)
            halfRange = (intervalTop - intervalBot) / 2;
            midpoint = intervalBot + halfRange;
            functionMidpoint = function.compute(midpoint, equationParams);

            // optional: check if there is a singularity at the midpoint
            boolean functionUndefined; // check if the function of the midpoint is undefined
            functionUndefined = Double.isNaN(functionMidpoint) || Double.isInfinite(functionMidpoint);
            // while the function of the point at which the interval splits is undefined, try to use a different point
            for (int j = 3; functionUndefined; j++) {
                if (j > 100) { // give up if a valid point cannot be found after some reasonable number of tries
                    System.out.println("Error: a suitable midpoint could not be found. The function may be undefined in the interval specified. ");
                    return Double.NaN;
                }
                // try using 1/3 -of-the-way point, then 1/4, 1/5 etc
                double splitPoint = intervalBot + (intervalTop - intervalBot) / j;
                // check to see if the new value is valid
                functionMidpoint = function.compute(splitPoint, equationParams);
                functionUndefined = Double.isNaN(functionMidpoint) || Double.isInfinite(functionMidpoint);
            }

            // step 4. evaluate accuracy of midpoint
            if (convergenceMethod.equals(convergenceMethods[0])) {
                convergenceCriterion = Math.abs(midpoint - prevMidpoint) / Math.abs(midpoint);
            }
            if (convergenceMethod.equals(convergenceMethods[1])) {
                convergenceCriterion = halfRange;
            }
            precision = Math.abs(intervalBot - intervalTop);
            if (functionMidpoint == 0 || (!Double.isNaN(convergenceCriterion) & convergenceCriterion < TOL)) {

                solution = midpoint;// found a suitable solution

                if (DEBUG) {
                    System.out.println("after " + (i + 1) + " iterations");
                    System.out.println("solution: " + solution + " within an interval of length " + precision);
                    System.out.println("f(" + solution + ") = " + function.compute(solution, equationParams));
                }
                return solution;
            }
            // step 5. increment iteration step
            i++;
            prevMidpoint = midpoint;
            // step 6. determine new location to search
            if (MathTools.sign(functionBot) == MathTools.sign(functionMidpoint)) {
                // use the right half of the original interval
                intervalBot = midpoint;
                functionBot = functionMidpoint;
            } else {
                // use the left half of the original interval
                intervalTop = midpoint;
            }
        }
        // unsuccessful search
        if (DEBUG) {
            System.out.println("Method failed after " + maxIter + " iterations");
            System.out.println("inadequate solution: " + midpoint + " within an interval of length " + precision);
        }
        return Double.NaN;
    }

    /*
     * comments: 
     * 1. "step 5. increment iteration step" is included in the body to be consistent the textbook for academic purposes only. otherwise it can go in the 'for' statment.
     * 2. this is basically an application of the mean value theorem using some continuous function and exploiting the fact that a solution to f = 0 will exist between a and b, provided that f(a) < 0 and f(b) > 0 both exist.
     * 3. we need to solve f=0, because the algorithm relies on positive/negative sign checking in step 6 to determine which interval contains the solution
     * 4. we need to maintain the lower bound of the interval to map to a negative and the upper bound of the interval to map to a positive to ensure a zero exists within the interval
     * 5. it doesn't really matter where we split the interval, we use the midpoint in the hope that halving the interval will find a solution the fastest, but the algorithm works for any point in the interval
     * 6. if we wanted to allow f = constant instead of f = 0, we could simply use (functionBot - constant)* (functionMidpoint - constant) in step 6
     * 7. if there are multiple solutions within the interval, the algorithm still works. it will output whichever solution it stumbles upon and won't know that other solutions exist
     * 8. if you find the solution in one iteration, the reported error interval will be equal to the distance between the two guesses you provided. That error interval is too large and can be misleading. However, you cannot be sure of the true margin of error, because f() might not be exactly zero due to truncation errors within the function computation.
     */
}
