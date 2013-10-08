/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780.geometry;

/**
 *
 * @author jbao
 */
public class MultiNewtonSafe {

    static int maxIter = 1000;
    static double TOL = 5e-6;

    public static double solve(int atom_index, int coord_index, double[][] atom_positions) {
        int ii = atom_index;
        int jj = coord_index;
        PartialD function = new PartialD();

        double initialGuess = atom_positions[ii][jj];

        double nextGuess = Double.NaN;
        double fx = Double.NaN;
        double convergenceCriterion = Double.NaN;
        double derivative = Double.NaN;

        for (int i = 0; i <= maxIter; i++) { // i<= maxIter because results of nth iteration are not checked until the n+1th

            fx = function.compute(ii, jj, atom_positions);
            //System.out.println(fx);
            if (Math.abs(fx) < TOL || convergenceCriterion < TOL) { // NaN < TOL == false
                return initialGuess;
            }
            derivative = function.dcompute(ii, jj, atom_positions);
            if (derivative == 0) {
                System.out.println("derivative became zero");
                return Double.NaN;
            }
            nextGuess = initialGuess - fx / derivative;

            convergenceCriterion = Math.abs(nextGuess - initialGuess) / Math.abs(initialGuess);
            if (Math.abs(nextGuess - initialGuess) > 10 ){
                return initialGuess;
            }
            initialGuess = nextGuess;
            atom_positions[ii][jj] = initialGuess;
        }

        return Double.NaN;
    }
}
