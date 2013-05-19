/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author jbao
 */
public class ShapesMain {
      public static void main(String[] args) {
          double[][] ellipse = NumUtil.Ellipse.calcPoints(1.0,10,10000);
          NumUtil.Plot.plot("Ellipse", ellipse, true);
      }
}
