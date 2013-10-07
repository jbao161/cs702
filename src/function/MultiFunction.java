/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

/**
 *
 * @author jbao
 */
public abstract class MultiFunction {

    public abstract double compute(double[][] atom_positions);

    public double dcompute(int atom_index, int var_index, double step_size, double[][] atom_positions) {
        double[] atom = atom_positions[atom_index];
        double alpha = atom[var_index]; // variable we wish to partial derivate
        atom[var_index] = alpha + step_size;
        // centered difference approximation
        double forward = compute( atom_positions);
        atom[var_index] = alpha - step_size;
        double backward = compute(atom_positions);
        atom[var_index] = alpha;

        double derivative = (forward - backward) * 0.5 / step_size; // error of order (step_size ^2)
        return derivative;
    }
}
