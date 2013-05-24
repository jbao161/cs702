/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author jbao
 */
public class ShapesMain {
      public static void main(String[] args) {
          double[][] ellipse = numutil.Ellipse.calcPoints(1.0,10,10000);
          numutil.Plot.plot("Ellipse", ellipse, true);
      }
}
