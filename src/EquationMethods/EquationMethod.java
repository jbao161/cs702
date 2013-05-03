/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EquationMethods;

import FunctionModel.FunctionModel;

/**
 *
 * @author jbao
 */
public abstract class EquationMethod {

    // tolerance and max iterations
    public double TOL = 1e-15;
    public double maxIter = 1e3;

    public abstract double solve(FunctionModel function, double lowGuess, double highGuess, double[] equationParams);
}
