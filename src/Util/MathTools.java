/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.lang.ArithmeticException;

/**
 *
 * @author jbao
 */
public class MathTools {

    public static int sign(double value) {
        if (Double.isNaN(value)) {
            throw new ArithmeticException("invalid input: " + value + " is not a number");
        }
        if (value > 0) { // allows positive infinity
            return 1;
        }
        if (value < 0) { // allows negative infinity
            return -1;
        }
        return 0;
    }

    public static double factorial(int value) {
        double result = 1;
        for (int i = 1; i <= value; i++) {
            result *= i;
        }
        return result;
    }
}
