/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780.geometry;

/**
 *
 * @author jbao
 */
public class LengthFinder {

    static int maxIter = 1000;
    static double TOL = 5e-5;

    public static double solve(double length) {

        double initialGuess = length;
        double nextGuess = Double.NaN;
        double fx = Double.NaN;
        double convergenceCriterion = Double.NaN;
        double derivative = Double.NaN;

        for (int i = 0; i <= maxIter; i++) { // i<= maxIter because results of nth iteration are not checked until the n+1th

            fx = K4_plane.findglobalsilent(length);
            //System.out.println(fx);
            if (Math.abs(fx) < TOL || convergenceCriterion < TOL) { // NaN < TOL == false
                return initialGuess;
            }
            derivative = K4_plane.fgd(length);
            if (derivative == 0) {
                System.out.println("derivative became zero");
                return Double.NaN;
            }
            nextGuess = initialGuess - fx / derivative;
            convergenceCriterion = Math.abs(nextGuess - initialGuess) / Math.abs(nextGuess);
            initialGuess = nextGuess;
        }

        return Double.NaN;
    }
}
