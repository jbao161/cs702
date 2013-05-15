/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NumUtil;

import NumUtil.Polynomial;

/**
 * assumes numbers are either constants, or variables of the same type x
 *
 * @author jbao
 */
public class Number {

    public double coef = Double.NaN;
    public double variablePower = 0;

    public final Number PI = new Number(Math.PI);
    public Number() {
    }

    public Number(double coef) {
        this.coef = coef;
    }

    public Number(double coef, double variablePower) {
        this.coef = coef;
        this.variablePower = variablePower;
    }

    public Polynomial times(Number[] array) {
       Polynomial expression = new  Polynomial();
        for (int i = 0; i < array.length; i++) {
            double coef = this.coef * array[i].coef;
            double power = this.variablePower * array[i].variablePower;
            if (expression.containsKey(power)){
                expression.put(power, coef + expression.get(power));
            } else {
                expression.put(power, coef);
            }
        }
        return expression;
    }

    public Number times(Number number) {
        double coef = 1;
        double variablePower = 0;
        Number[] pair = new Number[]{this, number};
        for (int i = 0; i < pair.length; i++) {
            coef *= pair[i].coef;
            variablePower += pair[i].variablePower;
        }
        Number product = new Number(coef, variablePower);
        return product;
    }

    public Number add(Number number) {
        Number result = null;
        if (this.variablePower == number.variablePower) {
            result = new Number(this.coef + number.coef, variablePower);
        } else {
            throw new IllegalArgumentException("addends must have the same variable power. this number power: " + this.variablePower + " add number power: " + number.variablePower);
        }
        return result;
    }
}
