/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package numutil1;

import numutil1.Polynomial;

/**
 * a two term grouping of the form (x-a)
 *
 * @author jbao
 */
public class Pair extends Polynomial {

    public Pair(double a) {
        this.put(1.0, 1.0); // variable x
        this.put(0.0, -a); // constant a
    }
}
