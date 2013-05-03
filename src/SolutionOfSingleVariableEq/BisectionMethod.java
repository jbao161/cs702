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
        double functionMidpoint = Double.NaN;

        // step 1. initialize comparison
        double functionBot = FunctionModel.computeFunction(functionName, lowGuess, equationParams);
        double functionTop = FunctionModel.computeFunction(functionName, highGuess, equationParams);

        if (DEBUG) {
            System.out.println("f(low guess) = " + functionBot);
            System.out.println("f(high guess) = " + functionTop);
        }

        // some mild input checking
        boolean breakout = false;
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
            // optional: if there is a singularity at the midpoint, try to find a different point to split the interval into
            for (int j = 3; (Double.isNaN(functionMidpoint) || Double.isInfinite(functionMidpoint)); j++) {
                // if 1/2 point is invalid, try using 1/3 point, then 1/4 point, 1/5 etc until point is valid or until 1/j approaches zero.
                functionMidpoint = FunctionModel.computeFunction(functionName, intervalBot + (intervalTop - intervalBot) / j, equationParams);
                if (j > 1e100) {
                    System.out.println("Error: the function may be undefined in the interval specified because a suitable midpoint could not be found");
                    return Double.NaN;
                }
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
// comments: 1. "step 5. increment iteration step" is included in the body to be consistent the textbook for academic purposes only. otherwise it can go in the 'for' statment.

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double[] eqParams = {34543, 1, 435000, -1564000};
        double solution = bisect("exponential", -600, 600, eqParams);
        System.out.println("solution: " + solution);
        System.out.println("f(" + solution + ") = " + FunctionModel.computeFunction("exponential", solution, eqParams));


    }
}
