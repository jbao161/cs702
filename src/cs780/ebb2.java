/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

import numutil.Matrix;

/**
 *
 * @author jbao
 */
public class ebb2 {

    public static double get_e(int nx, int ny) {
        return Math.pow(nx, 2) + Math.pow(ny, 2);
    }

    public static void main(String[] args) {
        double area = 20152;
        double radius = get_radius(area);
        double max_energy = radius * radius;
        double min_energy = get_e(1, 1);
        int num_energy_levels = 10;
        double increment = (max_energy - min_energy) / num_energy_levels;
        double increment_r;
        // area = 200;
        double[][] pairs = populate_pairs((int) area);
        Matrix printer = new Matrix(pairs);
        //printer.print();
        System.out.println(radius * radius);
        int k = 0;
        for (int i = 0; i < num_energy_levels-1;  i++) {
            double r = Math.sqrt(min_energy + increment * i);
            populate_pairs((int) r * r);
        }
    }

    public static double get_radius(double area) {
        double r;
        r = Math.sqrt(4 * area / Math.PI);
        return r;
    }

    public static double get_y(double x, double radius) {
        double y = Math.sqrt(radius * radius - x * x);
        return y;
    }

    public static double[][] populate_pairs(double area) {
        double radius = get_radius(area);
        System.out.println("radius:" + radius);
        double[][] result = new double[(int) area][3];
        int k = 0;
        for (int x = 1; x <= radius; x++) {
            double y_max = get_y(x, radius);
            //System.out.println("k:"+k);
            //System.out.println("x:"+x);
            //System.out.println("area:"+x*x);
            //System.out.println("Ymax: "+y_max);
            for (int y = 1; y <=y_max; y++, k++) {
                result[k][0] = x;
                result[k][1] = y;
                result[k][2] = get_e(x, y);
            }
        }
        System.out.println("k:" + k);
        System.out.println("energy: "+(radius*radius));
        return result;
    }
}
