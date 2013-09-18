/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

import java.util.Arrays;
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
        int num_energy_levels = 4;
        double increment = (max_energy - min_energy) / num_energy_levels;
        double increment_r;
        // area = 200;
        double[][] pairs = populate_pairs((int) area);
        Matrix printer = new Matrix(pairs);
        //printer.print();
        double[][] histogramdata = new double[num_energy_levels][];
        for (int i = 0; i < num_energy_levels; i++) {
            double energy = min_energy + increment * i;
        }
        histogramdata = put_into_histogram(area, num_energy_levels);
        printer.array = (histogramdata);
        printer.printdata();
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
            for (int y = 1; y <= y_max; y++, k++) {
                result[k][0] = x;
                result[k][1] = y;
                result[k][2] = get_e(x, y);
            }
        }
        System.out.println("k:" + k);
        System.out.println("energy: " + (radius * radius));
        return result;
    }

    public static double[][] put_into_histogram(double area, int num_of_energylevels) {
        double radius = get_radius(area);
        System.out.println("radius:" + radius);
        double[][] result = new double[(int) area][3];
        double[][] energylevels = new double[num_of_energylevels][3];
        double energy_min = 2;
        double energy_max = radius * radius;
        double energy;
        double increment = (energy_max - energy_min) / num_of_energylevels;
        for (int i = 0; i < num_of_energylevels; i++) {
            energylevels[i][1] = energy_min + i * increment;
            energylevels[i][0] = energy_min + (i-1) * increment;
            energylevels[i][2] = 0;
        }
        Matrix printer = new Matrix();
        printer.array = energylevels;
        printer.print();
        int k = 0;
        for (int x = 1; x <= radius; x++) {
            double y_max = get_y(x, radius);
            //System.out.println("k:"+k);
            //System.out.println("x:"+x);
            //System.out.println("area:"+x*x);
            //System.out.println("Ymax: "+y_max);
            for (int y = 1; y <= y_max; y++, k++) {
                energy = get_e(x, y);
                for (int i = 0; i < num_of_energylevels; i++) {
                    if (energy > energylevels[i][0] && energy < energylevels[i][1]) {
                        energylevels[i][2] += 1;
                    }
                }
            }
        }
        System.out.println("k:" + k);
        System.out.println("energy: " + (radius * radius));
        return result;
    }

    public static double[] histogram(double energy) {
        double radius = Math.sqrt(energy);
        double[] result = new double[3];
        int k = 0;
        for (int x = 1; x <= radius; x++) {
            double y_max = get_y(x, radius);
            for (int y = 1; y <= y_max; y++, k++) {
            }
        }
        result[0] = energy;
        result[1] = radius;
        result[2] = k;
        return result;
    }

    public static double calc_energy(double radius) {
        return radius * radius;
    }

    public static double calc_radius(double energy) {
        return Math.sqrt(energy);
    }
}
