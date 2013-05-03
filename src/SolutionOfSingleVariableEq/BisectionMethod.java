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
        System.out.println(functionBot);
        System.out.println(functionTop);
        boolean breakout = false;
        if (Double.isInfinite(functionBot)  || Double.isNaN(functionBot)) {
            System.out.println("Invalid Input: f(low guess) is not a number! (" + functionBot + ")");
            breakout = true;
        }
        if (Double.isInfinite(functionTop)  || Double.isNaN(functionTop)) {
            System.out.println("Invalid Input: f(high guess) is not a number! (" + functionTop + ")");
            breakout = true;
        }
        if (functionBot * functionTop > 0) {
            System.out.println("Invalid Input: f(low guess) and f(high guess) must have opposite sign! (" + functionBot + ", " + functionTop + ")");
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double[] eqParams = {34543, 1, 435000, -1564000};
        double solution = bisect("exponential", -100, 90, eqParams);
        System.out.println(solution);
    }
}
