/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionModel;

/**
 *
 * @author jbao
 */
public interface FunctionModel {

    public abstract double compute(double input, double[] equationParams);
    public abstract double dcompute(double input, double[] equationParams);

}
