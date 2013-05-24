/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package numutil;

/**
 *
 * @author jbao
 */
public class Ellipse {

    /**
     * calculates the points on an ellipse using the equation x^2/a^2 + y^2/b^2
     * = 1 using the parameterization x = a * cos(theta), y = b * sin(theta)
     *
     * @param xDenom the a in x^2/a^2
     * @param yDenom the b in y^2/b^2
     * @param numOfPoints the number n of data pairs returned by the method
     * @return {{x1,y1},{x2,y2},...,{x_n,y_n}}
     */
    public static double[][] calcPoints(double xDenom, double yDenom, int numOfPoints) {
        double startAngle = 0;
        double endAngle = 2*Math.PI;
        double increment = (endAngle - startAngle) / numOfPoints;
        double x;
        double y;

        double[][] ellipsePairs = new double[numOfPoints+1][2];
        for (double angle = startAngle, i=0; i<=numOfPoints; angle += increment, i++) {
            x = xDenom * Math.cos(angle);
            y = yDenom * Math.sin(angle);
            ellipsePairs[(int)i] = new double[]{x,y};
        }
        
        return ellipsePairs;
    }
}
