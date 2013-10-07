/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780.geometry;

/**
 *
 * @author jbao
 */
public class PartialD {

    static double step = 0.01;

    public static double compute(int atom_index, int coord_index, double[][] atom_positions) {
        int i = atom_index;
        int j = coord_index;
        double coord = atom_positions[i][j];
        atom_positions[i][j] = coord + step;
        double fplus = Project01.get_potential(atom_positions);
        atom_positions[i][j] = coord - step;
        double fminus = Project01.get_potential(atom_positions);
        double deriv = 0.5 / step * (fplus - fminus);
        return deriv;
    }

    public static double dcompute(int atom_index, int coord_index, double[][] atom_positions) {
        int i = atom_index;
        int j = coord_index;
        double coord = atom_positions[i][j];
        atom_positions[i][j] = coord + step;
        double fplus = compute(i, j, atom_positions);
        atom_positions[i][j] = coord - step;
        double fminus = compute(i, j, atom_positions);
        double dderiv = 0.5 / step * (fplus - fminus);
        return dderiv;
    }
}
