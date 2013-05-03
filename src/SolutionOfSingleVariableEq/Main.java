/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SolutionOfSingleVariableEq;

import static SolutionOfSingleVariableEq.BisectionMethod.TOL;
import static SolutionOfSingleVariableEq.BisectionMethod.bisect;
import static SolutionOfSingleVariableEq.BisectionMethod.maxIter;

/**
 *
 * @author jbao
 */
public class Main {
        public static void main(String[] args) {
        FunctionModel eq = new FunctionModel() {
            public double computeFunction(double x, double[] p) {
                return Math.exp(x) - Math.pow(x, 2) + 3 * x - 2;
            }
        };
        TOL = 1e-5;
        maxIter = 17;
        double solution = bisect(eq, 0, 1, null);


    }
}
