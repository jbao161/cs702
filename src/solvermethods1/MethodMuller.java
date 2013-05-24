/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package solvermethods1;

import function.ModelPolynomial;

/**
 *
 * @author jbao
 */
public class MethodMuller extends SolverMethod {

    public double solve(Object[] args) {
        ModelPolynomial poly = new ModelPolynomial();
        double[] inputs = (double[]) args[0];
        double p0 = inputs[0];
        double p1 = inputs[1];
        double p2 = inputs[2];
        double[] polyCoefs = (double[]) args[1];

        double h1 = p1 - p0;
        double h2 = p2 - p1;
        double delta1 = (poly.compute(p1, polyCoefs) - poly.compute(p0, polyCoefs)) / h1;
        double delta2 = (poly.compute(p2, polyCoefs) - poly.compute(p1, polyCoefs) / h2);
        double d = (delta2 - delta1) / (h2 + h1);
        for (int i = 3; i < maxIter; i++) {
            double b = delta2 + h2 * d;
            double sqrt = Math.sqrt(Math.pow(b, 2) - 4 * poly.compute(p2, polyCoefs) * d);
            double e = Double.NaN;
            if (Math.abs(b - sqrt) < Math.abs(b + sqrt)) {
                e = b + sqrt;
            } else {
                e = b - sqrt;
            }
            double h = -2 * poly.compute(p2, polyCoefs) / e;
            double p = p2 + h;
            if (Math.abs(h) < TOL) {
                return p;
            }
            p0 = p1;
            p1 = p2;
            p2 = p;
            h1 = p1 - p0;
            h2 = p2 - p1;
            delta1 = (poly.compute(p1, polyCoefs) - poly.compute(p0, polyCoefs)) / h1;
            delta2 = (poly.compute(p2, polyCoefs) - poly.compute(p1, polyCoefs)) / h2;
            d = (delta2 - delta1) / (h2 + h1);
        }
        return Double.NaN;
    }
}
