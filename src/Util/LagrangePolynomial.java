/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import FunctionModel.FunctionModel;

/**
 *
 * @author jbao
 */
public class LagrangePolynomial {

    public double[] interpolate(FunctionModel function, double[][] data) {
        for (int i = 0; i < data.length; i++) {
            double y = data[i][1];
            for (int j = 0; j < i; j++) {
                double x = data[i][0];
                Pair pair = new Pair(x);
            }
        }

        return new double[0];
    }

    public double[] polyCoef(Object[][] grouping) {
        int numOfGroups = grouping.length;
        int numOfTerms = (int) Math.pow(2, numOfGroups);
        for (int i = 0; i < numOfGroups; i++) {
            double coef = 1;

        }

        if (numOfGroups == 2) {
            for (int j = 0; j < grouping[0].length; j++) {
            }
        }
        return new double[0];
    }
}
