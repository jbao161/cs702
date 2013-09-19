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

    public static void lowest20k() {
        double area = 200500;
        double[][] pairs = populate_pairs((int) area);
        Matrix printer = new Matrix(pairs);
        //printer.printdata();
    }

    public static void dos() {
        double area = 200500;
        double radius = get_radius(area);
        double max_energy = radius * radius;
        double min_energy = get_e(1, 1);
        int num_energy_levels = 100;
        double increment = (max_energy - min_energy) / num_energy_levels;
        // density of state plotdata
        for (double energy = min_energy; energy < max_energy;) {
            double[] histogramdata = histogram(energy, energy + increment);
            energy += increment;
            numutil.MathTools.printdata(histogramdata);
        }
    }

    public static void energy_diff_main() {
        int num_energies = 100000;
        double[][] energies = populate_pairs(num_energies);
        Matrix printer = new Matrix(energies);
        sort(energies);
        //printer.printdata();
        double[][] diff1 = get_differences(energies);
        printer.array = diff1;
        //printer.printdata();
        double[][] diffplot = consolidate(diff1, 100);
        printer.array = diffplot;
        //printer.printdata();
        double[][] diff2 = get_differences(energies, 2);
        printer.array = diff2;
        printer.printdata();

    }

    public static void energy_fit_main() {
        int num_energies = 100000;
        double[][] energies = populate_pairs(num_energies);
        Matrix printer = new Matrix(energies);
        sort(energies);
        //printer.printdata();
        double[][] diff1 = get_differences(energies);
        printer.array = diff1;
        //printer.printdata();
        double[][] diff_fit_1a = get_last(diff1, 100);
        printer.array = diff_fit_1a;
        printer.printdata();
        double[][] diff_fit_1b = get_first(diff1, 100);
        printer.array = diff_fit_1b;
        printer.printdata();
        double[][] diff2 = get_differences(energies, 2);
        printer.array = diff2;
        //printer.printdata();
        double[][] diff_fit2 = get_last(diff2, 100);
        printer.array = diff_fit2;

    }

    public static double[][] get_last(double[][] array, int size) {
        sort(array);
        double[][] result = new double[size][3];
        int max_size = array.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = array[max_size - i - 1][j];
            }
        }
        return result;
    }

    public static double[][] get_first(double[][] array, int size) {
        sort(array);
        double[][] result = new double[size][3];
        int max_size = array.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = array[i][j];
            }
        }
        return result;
    }

    public static double[][] get_differences(double[][] energies) {
        int num_energies = energies.length;
        double[][] result = new double[num_energies - 1][];
        for (int i = 0; i < num_energies - 1; i++) {
            double[] diff_entry = new double[3];
            double diff = energies[i + 1][2] - energies[i][2];
            diff_entry[0] = energies[i][2];
            diff_entry[1] = energies[i + 1][2];
            diff_entry[2] = diff;
            result[i] = diff_entry;
        }
        return result;
    }

    public static double[][] get_differences(double[][] energies, int level) {
        int num_energies = energies.length;
        double[][] result = new double[num_energies - level][];
        for (int i = 0; i < num_energies - level; i++) {
            double[] diff_entry = new double[3];
            double diff = energies[i + level][2] - energies[i][2];
            diff_entry[0] = energies[i][2];
            diff_entry[1] = energies[i + level][2];
            diff_entry[2] = diff;
            result[i] = diff_entry;
        }
        return result;
    }

    public static double[][] consolidate(double[][] differences, int size) {
        int num_energies = differences.length;
        int increment = num_energies / size;
        double[][] result = new double[size][3];
        int k;
        for (int i = 0; i < size; i++) {
            k = i * increment;
            for (int j = 0; j < 3; j++) {
                result[i][j] = differences[k][j];
            }
        }
        return result;
    }

    // useless, cause forgot about the energies like 1,3 1,4 and i don't know how to order them
    public static double[][] get_energies(int num_energies) {
        double[][] result = new double[num_energies][];

        int k = 1; // adds +1 or +0
        int x;
        int y;
        for (int i = 0; i < num_energies; i++) {
            double[] energy = new double[3];
            k = i % 3;
            x = 1 + i / 3;
            y = x;
            if (k == 1) {
                y += 1;
            }
            if (k == 2) {
                x += 1;
            }
            energy[0] = x;
            energy[1] = y;
            energy[2] = Math.pow(x, 2) + Math.pow(y, 2);
            result[i] = energy;
        }
        //numutil.MathTools.printelements(energies);
        return result;
    }

    public static void sort(double[][] array) {
        java.util.Arrays.sort(array, new java.util.Comparator<double[]>() {
            public int compare(double[] a, double[] b) {
                return Double.compare(a[2], b[2]);
            }
        });
    }

    public static void unique_energies() {
        int num_energies = 10000;
        double[] energies = new double[num_energies];
        for (int i = 0; i < num_energies; i++) {
            energies[i] = Math.pow(1 + i / 2, 2) + Math.pow(1 + i / 2 + i % 2, 2);
        }
        numutil.MathTools.printelements(energies);
    }

    public static void main(String[] args) {
        if (false) {
            lowest20k();
        }
        if (false) {
            dos();
        }
        if (false) {
            energy_diff_main();
        }
        if (true) {
            energy_fit_main();
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
            for (int y = 1; y <= y_max; y++) {
                result[k][0] = x;
                result[k][1] = y;
                result[k][2] = get_e(x, y);
                k++;
            }
        }

        // reduce size
        double[][] reducedresult = new double[k][3];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < 3; j++) {
                reducedresult[i][j] = result[i][j];
            }
        }
        System.out.println("k:" + k);
        System.out.println("energy: " + (radius * radius));
        return reducedresult;
    }

    public static double[] histogram(double e_min, double e_max) {
        double r_min = Math.sqrt(e_min);
        double r_max = Math.sqrt(e_max);
        double[] result = new double[3];
        int k = 0;
        for (int x = 1; x <= r_max; x++) {
            double y_max = get_y(x, r_max);
            double y_min = get_y(x, r_min);
            for (int y = (int) Math.ceil(y_min); y <= y_max; y++, k++) {
            }
        }
        result[0] = e_min / 1000; // meV
        result[1] = e_max / 1000;
        result[2] = k / (e_max - e_min);
        return result;
    }

    public static double[] histogram_2(double e_min, double e_max) {
        double r_min = Math.sqrt(e_min);
        double r_max = Math.sqrt(e_max);
        double[] result = new double[3];
        int k = 0;
        for (int x = 1; x <= r_max; x++) {
            double y_max = get_y(x, r_max);
            double y_min = get_y(x, r_min);
            for (int y = (int) Math.ceil(y_min); y <= y_max; y++, k++) {
            }
        }
        result[0] = Math.pow(r_min, 2) / 1000; // meV
        result[1] = Math.pow(r_max, 2) / 1000;
        result[2] = 1000 * k / (result[1] - result[0]);
        return result;
    }

    public static double calc_energy(double radius) {
        return radius * radius;
    }

    public static double calc_radius(double energy) {
        return Math.sqrt(energy);
    }
}
