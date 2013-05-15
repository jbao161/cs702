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

    public static double factorial(double value) {
        double result = 1;
        for (double i = value; i > 0; i--) {
            result *= i;
        }
        return result;
    }

    public static double binomNum(double value, int numOfTerms) {
        double result = 1;
        for (double i = value; i > value - numOfTerms; i--) {
            result *= i;
        }
        return result;
    }

    public static double min(double[][] data) {
        double result = Double.MAX_VALUE;
        for (int i = 0; i < data.length; i++) {
            if (data[i][0] < result) {
                result = data[i][0];
            }
        }
        return result;
    }

    public static double max(double[][] data) {
        double result = Double.MIN_VALUE;
        for (int i = 0; i < data.length; i++) {
            if (data[i][0] > result) {
                result = data[i][0];
            }
        }
        return result;
    }

    public static double logBase(double base, double number) {
        if (base == 0) {
            return number;
        } else {
            double result = Math.log(number) / Math.log(base);
            return result;
        }
    }

    public static double binomial(int n, int k) {
        double numerator = factorial(n);
        double denom = factorial(k) * factorial(n - k);
        return numerator / denom;
    }

    /**
     * converts a list of multidimensional points into a list of coordinates grouped by their dimension
     * @param listOfDimensionalPoints input format: {p1,p2, ...}
     * for points p1 = {x1,y1,..}, p2 = {x2,y2,..}
     * @return same dimension coordinates are grouped into arrays {{x1,x2,...},{y1,y2,...},...}
     */
    public static double[][] pointToCoordinateArray(double[][] listOfDimensionalPoints) {
        int numOfPoints = listOfDimensionalPoints.length;
        int numOfDimensions = listOfDimensionalPoints[0].length; // must all be same dimensions!
        double[][] result = new double[numOfDimensions][];
        double[] coordinateArray;
        for (int i = 0; i < numOfDimensions; i++) {
            coordinateArray = new double[numOfPoints];
            for (int j = 0; j < numOfPoints; j++) {
                coordinateArray[j] = listOfDimensionalPoints[i][j];
            }
            result[i] = coordinateArray;
        }
        return result;
    }
}
