/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package numutil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.XYSeries;

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
        Iterator<Map.Entry<Double, Double>> iter = this.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Double, Double> e = iter.next();
            result += Math.pow(x, e.getKey()) * e.getValue();
        }
        return result;
    }

    public void clean() {
        Iterator<Map.Entry<Double, Double>> iter = this.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Double, Double> e = iter.next();
            if (e.getValue() == 0) {
                iter.remove();
            }
        }
    }

    public Polynomial differentiate(int order) {
        double coef;
        double power;
        Polynomial result = new Polynomial();
        Polynomial derivTerm;
        for (Map.Entry<Double, Double> e : this.entrySet()) {
            coef = e.getValue();
            power = e.getKey();
            if (order <= power) {
                derivTerm = new Polynomial(new double[][]{{power - order, coef * numutil.MathTools.binomNum(power, order)}});
                result = result.add(derivTerm);
            }
        }
        return result;
    }

    public Polynomial antiderivative(int order) {
        double coef;
        double power;
        Polynomial result = new Polynomial();
        Polynomial antiderivTerm;
        // for each term, raise its power by order amount and divide coefficient by (order+power)!/power!
        for (Map.Entry<Double, Double> e : this.entrySet()) {
            coef = e.getValue();
            power = e.getKey();
            antiderivTerm = new Polynomial(new double[][]{{power + order, coef / numutil.MathTools.binomNum(power + order, order)}});
            result = result.add(antiderivTerm);
        }
        return result;
    }

    public double integrate(double x1, double x2) {
        // create a new polynomial that using the antiderivative method
        Polynomial antiderivative = antiderivative(1);
        // use the fundamental theorem of calculus to evaluate the integral by antiderivative at endpoints
        return antiderivative.evaluate(x2) - antiderivative.evaluate(x1);
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

    public Polynomial subtract(Polynomial px) {
        return this.add(px.additiveInverse());
    }

    public Polynomial divide(double constant) {
        return this.times(1.0 / constant);
    }

    /*
     * makes all the coefficients of opposite sign
     */
    public Polynomial additiveInverse() {
        Polynomial result = new Polynomial();
        double coef;
        double exponent;
        for (Map.Entry<Double, Double> e : this.entrySet()) {
            coef = 0 - e.getValue();
            exponent = e.getKey();
            result.put(exponent, coef);
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

    public Polynomial times(double constant) {
        Polynomial result = new Polynomial();
        for (Map.Entry<Double, Double> e : this.entrySet()) {
            result.put(e.getKey(), Double.valueOf(e.getValue() * constant));
        }
        return result;
    }

    /**
     * used by child classes to convert a Polynomial object into a specific type
     * of polynomial object
     *
     * @param identity the polynomial that will be converted into a specific
     * type
     */
    public void become(Polynomial identity) {
        this.clear();
        for (Map.Entry<Double, Double> e : identity.entrySet()) {
            this.put(e.getKey(), e.getValue());
        }
    }

    public boolean equals(Polynomial px) {
        this.clean();
        px.clean();
        if (this.size() != px.size()) {
            return false;
        } else {
            double coef;
            double exponent;

            Iterator<Map.Entry<Double, Double>> iter = this.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<Double, Double> e = iter.next();
                coef = e.getValue();
                exponent = e.getKey();
                if (coef != px.get(exponent)) {
                    return false;
                }
            }
            return true;
        }
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
        return toText("x");
    }

    public String toText(String varName) {
        String result = "";
        String add = " + ";
        String subtract = " - ";
        String exp = "^";
        String operator = add;
        for (Map.Entry<Double, Double> e : this.entrySet()) {
            double coef = e.getValue();
            double exponent = e.getKey();
            // if the coefficient is negative, and the term is not a constant, use subtract String and positive coefficient
            if (coef < 0 & exponent != 0) {
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
                    result += varName;
                }
                // if the term is not a constant and its exponent is not one, we need to show the exponent
                if (exponent != 0 & exponent != 1) {
                    result += exp + exponent;
                }
            }
        }
        // formats the leading operation string
        String leadingOperator = result.substring(0, operator.length());
        if (leadingOperator.equals(add)) {
            result = result.substring(add.length());
        }
        if (leadingOperator.equals(subtract)) {
            result = "-" + result.substring(subtract.length());
        }
        if (result.equals("")) {
            return "0";
        }
        return result;
    }

    public void print(String varName) {
        System.out.println(this.toText(varName));
    }

    public void print() {
        System.out.println(this.toText("x"));
    }

    public XYSeries createPlot(double logBase, double min, double max) {

        // curve pts
        double xmin = MathTools.logBase(logBase, min);
        double xmax = MathTools.logBase(logBase, max);
        double numCurvePts = 10e3;
        double increment = (xmax - xmin) / numCurvePts;
        double xMarker;
        double yMarker;
        XYSeries curveSeries = new XYSeries("Polynomial P(x)");
        for (int i = 0; i < numCurvePts; i++) {
            if (logBase != 0) {
                xMarker = Math.pow(logBase, xmin + increment * i);
            } else {
                xMarker = xmin + increment * i;
            }
            yMarker = evaluate(xMarker);
            curveSeries.add(MathTools.logBase(logBase, xMarker), MathTools.logBase(logBase, yMarker));
        }

        return curveSeries;
    }

    /**
     * Creates a plot of P(x) vs x in log or linear space
     *
     * @param logBase to plot in linear space use logBase = 0, else use the log
     * base here
     * @param min linear space x min
     * @param max linear space x max
     */
    public ChartPanel plot(double logBase, double min, double max, boolean visible) {
        ArrayList<XYSeries> dataSets = new ArrayList<XYSeries>();
        dataSets.add(createPlot(logBase, min, max));
        return numutil.Plot.plot(toText(), dataSets, visible);
    }
}
