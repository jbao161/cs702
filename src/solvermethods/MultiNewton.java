/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package solvermethods;

import function.MultiFunction;

/**
 *
 * @author jbao
 */
public class MultiNewton extends SolverMethod {

    public static final boolean DEBUG = true;

    /**
     *
     * @param args {MultiFunction function, int index of the variable to
     * optimize, double step size, double[] initial variables, double []
     * equation parameters}
     * @return
     */
    @Override
    public double solve(Object[] args) {

        MultiFunction function = (MultiFunction) args[0];
        int var_index = (Integer) args[1];
        double step_size = (Double) args[2];
        double[] init_variables = (double[]) args[3];
        double initialGuess = init_variables[var_index];
        double[] equationParams = (double[]) args[4];

        double nextGuess = Double.NaN;
        double fx = Double.NaN;
        double convergenceCriterion = Double.NaN;
        double derivative = Double.NaN;

        for (int i = 0; i <= maxIter; i++) { // i<= maxIter because results of nth iteration are not checked until the n+1th

            fx = function.compute(init_variables, equationParams);
            if (fx == 0 || convergenceCriterion < TOL) { // NaN < TOL == false
                return initialGuess;
            }
            derivative = function.dcompute(var_index, step_size, init_variables, equationParams);
            if (derivative == 0) {

                return Double.NaN;
            }
            nextGuess = initialGuess - fx / derivative;
            convergenceCriterion = Math.abs(nextGuess - initialGuess) / Math.abs(nextGuess);
            initialGuess = nextGuess;
            init_variables[var_index] = initialGuess;
        }

        return Double.NaN;
    }
    /* comments:
     * 1. we solve f(x) = f(a) + (x-a) d/dx f(a) + error, for f(x) = 0
     * 2. the updated solution uses a subtraction, because f(a)/f'(a) +(x-a) = 0 gives x = a - f(a)/f'(a)
     * 3. linear approximation assumes higher order derivatives are small compared to f'(a)
     * 4. method fails if a derivative equals zero due to divide by zero error
     * 5. initial guess must be 'sufficiently close' to solution for convergence
     * 6. consecutive derivatives decrease to zero, so rate of convergence increases with each iteration
     * 
     */
}