/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.util.Map;
import java.util.TreeMap;

/**
 * key = power of variable x. value = coefficient of the variable term
 *
 * @author jbao
 */
public class Polynomial extends TreeMap<Double, Double> {

    public Polynomial() {
    }

    /**
     * starting at the constant term, going up in power: the coefficients of the
     * variable of nth power
     *
     * @param coefs example: x^2 + 3x^4 + 2x^7 = {0,0,1,0,3,0,0,2};
     */
    public Polynomial(double[] coefs) {
        for (double i = 0; i < coefs.length; i++) {
            this.put(i, coefs[(int) i]);
        }
    }

    /**
     * pairs of [power, coefficient] in any arbitrary order
     *
     * @param coefs {[power, coefficient], [power, coefficient],...}
     */
    public Polynomial(double[][] coefs) {
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
        Polynomial[] list = {this, px};
        for (int i = 0; i < list.length; i++) {
            for (Map.Entry<Double, Double> e : list[i].entrySet()) {
                power = e.getKey();
                coef = e.getValue();
                if (result.containsKey(power)) {
                    coef += result.get(power);
                }
                result.put(power, coef);
            }
        }
        return result;
    }

    public Polynomial times(Polynomial px) {
        double coefThis;
        double coefPx;
        double powerThis;
        double powerPx;
        Polynomial result = new Polynomial();
        Polynomial singleTerm;
        for (Map.Entry<Double, Double> entryThis : this.entrySet()) {
            // get the values for the term a1x^n1
            powerThis = entryThis.getKey();
            coefThis = entryThis.getValue();
            // proceed entry by entry, a1x^n1 *(b1x^n1) + a1x^n1 *(b2x^n2) + ... + a2x^n2 * (b1x^n1) + a2x^n2 *(b2x^n2) + ...
            for (Map.Entry<Double, Double> entryPx : px.entrySet()) {
                // create a new term to store the product of a two numbers
                singleTerm = new Polynomial();
                // add the powers, and multiply the coefficients
                powerPx = entryPx.getKey() + powerThis;
                coefPx = entryPx.getValue() * coefThis;
                // set the coefficient and the power
                singleTerm.put(powerPx, coefPx);
                // combine the single term product into the total sum
                result = result.add(singleTerm);
            }
        }
        return result;
    }

    public double[][] toPrimitive() {

        Double[][] objResult = this.entrySet().toArray(new Double[this.entrySet().size()][]);
        double[][] result = new double[objResult.length][];
        for (int i = 0; i < objResult.length; i++) {
            for (int j = 0; j < objResult[i].length; j++) {
                result[i][j] = objResult[i][j].doubleValue();
            }
        }
        return result;
    }

    public String toText() {
        String result = "";
        String add = " + ";
        String subtract = " - ";
        String var = "x";
        String exp = "^";
        String operator;
        for (Map.Entry<Double, Double> e : this.entrySet()) {
            double coef = e.getValue();
            double exponent = e.getKey();
            // if the coefficient is negative, and the term is not a constant, use subtract String and positive coefficient
            if (coef < 0 & exponent!=0) {
                operator = subtract;
                coef = -coef;
            } else {
                operator = add;
            }

            // don't print the term if its coefficient equals zero
            if (coef != 0) {
                result += operator;
                // print coefficient if term is constant, or coefficient does not equal one
                if (exponent == 0 || coef != 1) {
                    result += String.valueOf(coef);
                }
                // print the variable name if term is not a constant
                if (exponent != 0) {
                    result += var;
                }
                // if the term is not a constant and its exponent is not one, we need to show the exponent
                if (exponent !=0 & exponent != 1) {
                    result += exp + e.getKey();
                }
            }
        }
        // removes the leading add string
        result = result.substring(add.length());
        return result;
    }
}
