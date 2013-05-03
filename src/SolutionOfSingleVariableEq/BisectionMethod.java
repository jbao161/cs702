/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SolutionOfSingleVariableEq;

/**
 *
 * @author jbao
 */
public class BisectionMethod {

    public static final boolean DEBUG = true;
    // tolerance and max iterations
    public static double TOL = 2e-30;
    public static int maxIter = 10000;

    public static double bisect(String functionName, double lowGuess, double highGuess, double[] equationParams) {

        double solution;
        double intervalBot = lowGuess;
        double intervalTop = highGuess;

        double halfRange = Double.NaN;
        double midpoint = Double.NaN;

        // step 1. initialize comparison
        double functionMidpoint = Double.NaN;
        double functionBot = FunctionModel.computeFunction(functionName, lowGuess, equationParams);
        double functionTop = FunctionModel.computeFunction(functionName, highGuess, equationParams);

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
        if (functionBot * functionTop > 0) {
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
            functionMidpoint = FunctionModel.computeFunction(functionName, midpoint, equationParams);

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
                functionMidpoint = FunctionModel.computeFunction(functionName, splitPoint, equationParams);
                functionUndefined = Double.isNaN(functionMidpoint) || Double.isInfinite(functionMidpoint);
            }

            // step 4. evaluate accuracy of midpoint
            if (functionMidpoint == 0 || halfRange < TOL) {
                solution = midpoint;// found a suitable solution
                return solution;
            }
            // step 5. increment iteration step
            i++;
            // step 6. determine new location to search
            if (functionBot * functionMidpoint > 0) {
                // use the right half of the original interval
                intervalBot = midpoint;
                functionBot = functionMidpoint;
            } else {
                // use the left half of the original interval
                intervalTop = midpoint;
            }
        }
        // unsuccessful search
        System.out.println("Method failed after " + maxIter + " iterations");
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
     */ 

    public static void main(String[] args) {
        double[] eqParams = {34543, 1, 435000, -1564000};
        double solution = bisect("exponential", -600, 600, eqParams);
        System.out.println("solution: " + solution);
        System.out.println("f(" + solution + ") = " + FunctionModel.computeFunction("exponential", solution, eqParams));


    }
}
