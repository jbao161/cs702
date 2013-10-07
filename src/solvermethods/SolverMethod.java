/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package solvermethods;

/**
 *
 * @author jbao
 */
public abstract class SolverMethod {

    // tolerance and max iterations
    public double TOL = 5e-8;
    public double maxIter = 1e3;

    public abstract double solve(Object[] args);
}
