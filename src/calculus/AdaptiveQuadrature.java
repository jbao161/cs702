/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus;

import function.FunctionModel;
import java.util.ArrayList;

/**
 *
 * @author jbao
 */
public class AdaptiveQuadrature {

    /* adaptive quadrature method using simpson's rule:
     * to reduce the error term in an approximate integration using polynomials,
     * we can predict the sufficiently small step size required.
     * 
     * using a smaller step size reduces the coefficient of the error term.
     * assuming that the f(x1) and f(x2) of the total interval approximation and reduced step size approximation are approximately equal,
     * for simpson's rule, if we reduce the step size by two, the error coeffficient h^n is reduced to h^n / 2^(n-1).
     * 
     * ATTENTION: this method not working, because upon dividing an interval in half, the simpson's rule must be evaluated for each subinterval
     * however, following the way the algorithm is explained in the book, it is very cumbersome to introduce a method call within the book's algorithm
     * i have written an algorithm easier to understand in the method below.
     */
    public static double aq(double x1, double x2, FunctionModel f, double[] equationParams, double TOL, int limit) {
        double result = 0;
        int n = 2;
        double tol;
        double a;
        double e;
        double b;
        double h;
        double fa;
        double fd;
        double fc;
        double fe;
        double fb;
        double s;
        double l;


        int i = 2; // number of subintervals yet to be evaluated
        l = 1; // subdivision level
        tol = 15 * TOL; // criterion for determining when to stop subdividing an interval
        a = x1; // left bound of interval
        e = x2; // right bound of interval
        h = (e - a) / 2; // half length of interval
        fa = f.compute(a, equationParams);
        fc = f.compute(a + h, equationParams);
        fe = f.compute(e, equationParams);
        s = h / 3 * (fa + 4 * fc + fe); // approximation from simpson's method for entire interval

        while (i >= 0) {
            // midway points of left and right subintervals, respectively
            fb = f.compute(a + h / 2, equationParams);
            fd = f.compute(a + 3 * h / 2, equationParams);
            double s1 = h / 6 * (fa + 4 * fb + fc); // approximation from simpsons's method for left subinterval
            double s2 = h / 6 * (fc + 4 * fd + fe); // approximation from simpsons's method for right subinterval

            // store the f(x) values for the ith halfing of subintervals
            double v1 = a; // left bound of subinterval
            double v2 = fa; // f(x) at left bound of subinterval
            double v3 = fc; // f(x) at midpoint of subinterval
            double v4 = fe; // f(x) at right bound of subinterval
            double v5 = h; // half length of subinterval
            double v6 = tol; // tolerance of subinterval
            double v7 = s; // approximation from simpson's method for subinterval
            double v8 = l; // subdivision level

            // if each part of the subinterval meets the subdivided tolerance, sum all parts and return the value
            i--;
            if (Math.abs(s1 + s2 - v7) < v6) {
                result += (s1 + s2);
            } else {// if one of the parts exceeds tolerance, divide it in half and repeat
                if (v8 >= limit) {
                    return Double.NaN; // limit level exceeded. procedure fails.
                } else { // add one level
                    // right half subinterval
                    i++;
                    a = v1 + v5;
                    fa = v3;
                    fc = fe;
                    fb = v4;
                    h = v5 / 2;
                    tol = v6 / 2;
                    s = s2;
                    l = v8 + 1;
                    // left half subinterval
                    i++;
                    a = v1;
                    fa = v2;
                    fc = fb;
                    fb = v3;
                    h = v5 / 2;
                    tol = v6 / 2;
                    s = s1;
                    l = v8 + 1;
                }
            }
        }
        // approximates integral to within TOL
        return result;
    }

    /* adaptive quadrature algorithm using simpson's method for approximating an integral.
     * this algorithm divides the domain of integration into sufficiently halved subintervals until the approximation is within prescribed tolerance.
     * each interval is stored in an ArrayList, and the method iterates until the ArrayList is empty
     * the method removes intervals one at a time and calculates the approximation for that interval.
     * if the error is within tolerance, that approximation term is added to the total integral.
     * otherwise, that interval is split evenly into two subintervals, their tolerances are halved from the parent, and each subinterval is added to the ArrayList.
     */
   
    public static double aq2(double x1, double x2, FunctionModel f, double[] equationParams, double TOL, double maxSubdivision) {
        double result = 0; // the numerical estimation of the integral
        ArrayList<Double[]> intervals = new ArrayList<Double[]>(); // stores unprocessed intervals
        intervals.add(new Double[]{x1, x2, 0.0}); // initialize the algorithm with the interval between the bounds of integration
        TOL = TOL * 15; // a criterion for accepting a simpson's approximation for an interval. the factor of 15 is due to a coefficient specific to simpson's method.
        while (!intervals.isEmpty()) {
            // remove the most recently added interval for processing
            Double[] interval = intervals.remove(intervals.size() - 1);
            double level = interval[2]; // the subdivision level of the interval: i.e. how many times it has been halved from the length of original interval.
            double tol = TOL / Math.pow(2, level); // higher level subdivisions require stricter tolerance levels
            double a1 = interval[0];
            double a2 = interval[1];
            double[] intervalApproximation = aqhelper(a1, a2, f, equationParams, tol); // processes the simpson's approximation to an interval
            if (intervalApproximation[0] > 0) { // if error of the interval approximation is acceptable
                result += intervalApproximation[1]; // add the approximation term to the integral
            } else {
                if (level > maxSubdivision) {
                    return Double.NaN; // prevent infinite subdivision
                }
                // if the error is too large, divide the interval into two and add both subintervals to the ArrayList for further processing
                double midpoint = (a1 + a2) / 2;
                intervals.add(new Double[]{a1, midpoint, level + 1});
                intervals.add(new Double[]{midpoint, a2, level + 1});
            }
        }
        // when all subdivision intervals have passed tolerance, their terms have been added to the total integral
        return result;
    }
    /*
     * computes the simpson's method integration over the entire interval, the left half of the interval, and the right half of the interval.
     * then calculates the sum of left and right approximation terms.
     * if the error from the entire interval approximation is less than tolerance, a positive value is returned as the first number.
     * else a negative number is returned.
     * the second number returned is the sum of left and right approximation terms.
     */
    public static double[] aqhelper(double x1, double x2, FunctionModel f, double[] equationParams, double TOL) {
        double h = (x2 - x1) / 4;
        double a1 = x1;
        double a2 = x1 + h;
        double a3 = x1 + 2 * h;
        double a4 = x1 + 3 * h;
        double a5 = x2;
        double fa1 = f.compute(a1, equationParams);
        double fa2 = f.compute(a2, equationParams);
        double fa3 = f.compute(a3, equationParams);
        double fa4 = f.compute(a4, equationParams);
        double fa5 = f.compute(a5, equationParams);

        double totalIntegral = 2 * h / 3 * (fa1 + 4 * fa3 + fa5);
        double leftIntegral = h / 3 * (fa1 + 4 * fa2 + fa3);
        double rightIntegral = h / 3 * (fa3 + 4 * fa4 + fa5);

        double sum = leftIntegral + rightIntegral;
        double error = Math.abs(totalIntegral - sum);
        double passfail = -1; // error is too high
        if (error < TOL) {
            passfail = 1; // error is acceptable
        }
        return new double[]{passfail, sum};
    }
}
