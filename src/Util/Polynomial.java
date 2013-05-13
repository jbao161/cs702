/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.util.HashMap;
import java.util.Map;

/**
 * key = power of variable x. value = coefficient of the variable term
 *
 * @author jbao
 */
public class Polynomial extends HashMap<Double, Double> {

    Polynomial() {
    }

    /**
     * starting at the constant term, going up in power: the coefficients of the
     * variable of nth power
     *
     * @param coefs example: x^2 + 3x^4 + 2x^7 = {0,0,1,0,3,0,0,2};
     */
    Polynomial(double[] coefs) {
        for (double i = 0; i < coefs.length; i++) {
            this.put(i, coefs[(int) i]);
        }
    }

    /**
     * pairs of [power, coefficient] in any arbitrary order
     *
     * @param coefs {[power, coefficient], [power, coefficient],...}
     */
    Polynomial(double[][] coefs) {
        for (int i = 0; i < coefs.length; i++) {
            this.put(coefs[i][0], coefs[i][1]);
        }
    }

    /**
     * evaluates the numerical value of the polynomial with input x
     *
     * @param x
     * @return
     */
    public double evaluate(double x) {
        double result = 0;
        for (Map.Entry<Double, Double> e : this.entrySet()) {
            result += Math.pow(x, e.getKey()) * e.getValue();
        }
        return result;
    }

    public Polynomial add(Polynomial px) {
        Polynomial result = new Polynomial();
        double coef;
        double power;
        for (Map.Entry<Double, Double> e : this.entrySet()) {
            power = e.getKey();
            coef = e.getValue();
            if (px.containsKey(power)) {
                coef += px.get(power);
            }
            result.put(power, coef);
        }
        return result;
    }

    public Polynomial times(Polynomial px) {
        double coefThis;
        double coefPx;
        double powerThis;
        double powerPx;
        Polynomial result = new Polynomial();
        Polynomial subProduct;
        for (Map.Entry<Double, Double> entryThis : this.entrySet()) {
            // get the values for the term a1x^n1
            powerThis = entryThis.getKey();
            coefThis = entryThis.getValue();
            // create a new term to store the product of a number with a polynomial
            subProduct = new Polynomial();
            // proceed line by line, a1x^n1 *(Px) + a2x^n2 *(Px) + ...
            for (Map.Entry<Double, Double> entryPx : px.entrySet()) {
                // add the powers, and multiply the coefficients
                powerPx = entryPx.getKey() + powerThis;
                coefPx = entryPx.getValue() * coefThis;
                // combine like terms
                if (subProduct.containsKey(powerPx)) {
                    coefPx += subProduct.get(powerPx);
                }
                // update the coefficient at the nth power
                subProduct.put(powerPx, coefPx);
            }
            // combine the single line product into the total sum
            result.add(subProduct);
        }
        return new Polynomial();
    }
}
