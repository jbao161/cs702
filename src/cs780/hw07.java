/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import numutil.Matrix;

/**
 *
 * @author jbao
 */
public class hw07 {

    static int num_points_sc = 8;
    static int num_points_bcc = 9;
    static int num_points_fcc = 14;
    static int num_coords = 3;
    static Random rand = new Random();

    static double[][] sc_plain(double length) {
        double[][] cube = new double[num_points_sc][num_coords];
        cube[0] = new double[]{0, 0, 0};
        cube[1] = new double[]{length, 0, 0};
        cube[2] = new double[]{0, length, 0};
        cube[3] = new double[]{0, 0, length};
        cube[4] = new double[]{length, length, 0};
        cube[5] = new double[]{0, length, length};
        cube[6] = new double[]{length, 0, length};
        cube[7] = new double[]{length, length, length};
        return cube;
    }

    static double[][] sc_cell(double length, double[] origin) {
        int n = 0;
        double[][] cube = sc_plain(length);
        for (int i = 0; i < num_points_sc; i++) {
            cube[i] = add(cube[i], origin);
        }
        return cube;
    }

    static double[][][] sc_grid(double length) {
        int num_cubes = 5;
        int num_blocks = (int) Math.pow(num_cubes, 3);
        System.out.println(num_blocks);
        int n = 0;
        double[][][] grid = new double[num_blocks][num_points_sc][num_coords];
        double[] cell = {0, 0, 0};
        for (int i = 0; i < num_cubes; i++) {
            cell[0] = length * i;
            for (int j = 0; j < num_cubes; j++) {
                cell[1] = length * j;
                for (int k = 0; k < num_cubes; k++) {
                    cell[2] = length * k;
                    grid[n] = sc_cell(length, cell);
                    n++;
                }
            }
        }
        print(grid);
        return grid;
    }

    static double[][] bcc_plain(double length) {
        double[][] cube = new double[num_points_bcc][num_coords];
        cube[0] = new double[]{0, 0, 0};
        cube[1] = new double[]{length, 0, 0};
        cube[2] = new double[]{0, length, 0};
        cube[3] = new double[]{0, 0, length};
        cube[4] = new double[]{length, length, 0};
        cube[5] = new double[]{0, length, length};
        cube[6] = new double[]{length, 0, length};
        cube[7] = new double[]{length, length, length};
        cube[8] = new double[]{length / 2, length / 2, length / 2};
        return cube;
    }

    static double[][] bcc_cell(double length, double[] origin) {
        int n = 0;
        double[][] cube = bcc_plain(length);
        for (int i = 0; i < num_points_bcc; i++) {
            cube[i] = add(cube[i], origin);
        }
        return cube;
    }

    static double[][][] bcc_grid(double length) {
        int num_cubes = 5;
        int num_blocks = (int) Math.pow(num_cubes, 3);
        System.out.println(num_blocks);
        int n = 0;
        double[][][] grid = new double[num_blocks][num_points_bcc][num_coords];
        double[] cell = {0, 0, 0};
        for (int i = 0; i < num_cubes; i++) {
            cell[0] = length * i;
            for (int j = 0; j < num_cubes; j++) {
                cell[1] = length * j;
                for (int k = 0; k < num_cubes; k++) {
                    cell[2] = length * k;
                    grid[n] = bcc_cell(length, cell);
                    n++;
                }
            }
        }
        print(grid);
        return grid;
    }

    static double[][] fcc_plain(double a) {
        double[][] cube = new double[num_points_fcc][num_coords];
        double b = a / 2;
        cube[0] = new double[]{0, 0, 0};
        cube[1] = new double[]{a, 0, 0};
        cube[2] = new double[]{0, a, 0};
        cube[3] = new double[]{0, 0, a};
        cube[4] = new double[]{a, a, 0};
        cube[5] = new double[]{0, a, a};
        cube[6] = new double[]{a, 0, a};
        cube[7] = new double[]{a, a, a};
        cube[8] = new double[]{b, b, 0};
        cube[9] = new double[]{b, 0, b};
        cube[10] = new double[]{0, b, b};
        cube[11] = new double[]{b, b, a};
        cube[12] = new double[]{b, a, b};
        cube[13] = new double[]{a, b, b};
        return cube;
    }

    static double[][] fcc_cell(double length, double[] origin) {
        int n = 0;
        double[][] cube = fcc_plain(length);
        for (int i = 0; i < num_points_fcc; i++) {
            cube[i] = add(cube[i], origin);
        }
        return cube;
    }

    static double[][][] fcc_grid(double length) {
        int num_cubes = 5;
        int num_blocks = (int) Math.pow(num_cubes, 3);
        System.out.println(num_blocks);
        int n = 0;
        double[][][] grid = new double[num_blocks][num_points_fcc][num_coords];
        double[] cell = {0, 0, 0};
        for (int i = 0; i < num_cubes; i++) {
            cell[0] = length * i;
            for (int j = 0; j < num_cubes; j++) {
                cell[1] = length * j;
                for (int k = 0; k < num_cubes; k++) {
                    cell[2] = length * k;
                    grid[n] = fcc_cell(length, cell);
                    n++;
                }
            }
        }
        print(grid);
        return grid;
    }

    static double[] add(double[] vector1, double[] vector2) {
        double[] result = new double[num_coords];
        for (int i = 0; i < num_coords; i++) {
            result[i] = vector1[i] + vector2[i];
        }
        return result;
    }

    static void print(double[][][] array) {
        String line;
        for (int k = 0; k < array.length; k++) {
            for (int i = 0; i < array[i].length; i++) {
                line = "h  ";
                for (int j = 0; j < array[i][j].length; j++) {
                    line += array[k][i][j] + "    ";
                }
                System.out.println(line);
            }
        }
    }

    static void dendrite() { // not working
        int x_size = 30;
        int y_size = 30;
        int num_iterations = 2000;
        double[][] grid = new double[x_size][y_size];
        grid[x_size - 1][y_size / 2] = 1;

        double p1 = 0.8;
        double p2 = 0.1;
        int num_branches = 1;
        int pick;
        Integer[] cell;
        ArrayList<Integer[]> options = new ArrayList<Integer[]>(1);
        Integer[] origin = new Integer[2];
        origin[1] = y_size / 2;
        origin[0] = x_size - 1;
        options.add(origin);
        for (int i = 0; i < num_iterations; i++) {
            try {
                pick = rand.nextInt(num_branches);
                System.out.println(pick);
                options.get(pick);
                cell = options.get(pick);
                int x = cell[0];
                int y = cell[1];
                options.remove(pick);
                pick = rand.nextInt(3);

                grid[x][y] = 1;
                if (pick == 0) {
                    if (grid[x - 1][y] == 0) {
                        Integer[] up = new Integer[2];
                        up[0] = cell[0];
                        up[1] = cell[1] - 1;
                        options.add(up);
                        // num_branches++;
                    }
                }
                if (pick == 1) {
                    if (grid[x][y - 1] == 0) {
                        Integer[] left = new Integer[2];
                        left[0] = cell[0] - 1;
                        left[1] = cell[1];
                        options.add(left);
                        //num_branches++;
                    }
                } else {
                    if (grid[x][y + 1] == 0) {
                        Integer[] right = new Integer[2];
                        right[0] = cell[0] + 1;
                        right[1] = cell[1];
                        options.add(right);
                        // num_branches++;
                    }
                }
            } catch (Exception e) {
            }
        }
        numutil.MathTools.print(grid);
    }

    static Integer[] add(Integer[] a, Integer[] b) {
        int size = a.length;
        Integer[] sum = new Integer[size];
        for (int i = 0; i < size; i++) {
            sum[i] = a[i] + b[i];
        }
        return sum;
    }

    static void dendrite2() { // working
        ArrayList<Integer[]> tree = new ArrayList<Integer[]>(100);
        ArrayList<Integer[][]> nodes = new ArrayList<Integer[][]>(100);

        // allowed movement
        Integer[] n = new Integer[]{0, 1};
        Integer[] w = new Integer[]{-1, 0};
        Integer[] e = new Integer[]{1, 0};
        Integer[] ne = new Integer[]{-1, 1};
        Integer[] nw = new Integer[]{1, 1};
        Integer[][] movements = {n, w, e, ne, nw};
        // preferred direction of movement
        Integer[] preferred;
        preferred = n;
        // random chosen movement
        double p = 0.90; // chance for preferred movement
        int num_directions = 5;
        int num_iter = 100;
        int num_nodes = 1;

        // initial seed at origin
        Integer[] point;
        point = new Integer[]{0, 0};
        tree.add(point);
        nodes.add(new Integer[][]{point, preferred});
        
        // grow the dendrite
        for (int k = 0; k < num_iter; k++) {
            for (int j = 0; j < num_nodes; j++) {
                point = nodes.get(j)[0];
                preferred = nodes.get(j)[1];
                double pick = rand.nextDouble();
                if (pick > p) { // if we do not move in the preferred direction, keep the node
                    int chosen = rand.nextInt(num_directions - 1);
                    preferred = movements[1 + chosen]; // pick one of the possible directions
                } else {
                    nodes.remove(j); // if we move in the preferred direction, remove the node
                }
                point = add(point, preferred);
                if (tree.contains(point)) { // if we already have that point, remove the node
                    nodes.remove(j);
                    break;
                }
                tree.add(point);
                nodes.add(new Integer[][]{point, preferred});
            }
        }
        for (int i = 0; i < nodes.size(); i++) {
            point = nodes.get(i)[0];
            System.out.println("N " + point[0] + " " + point[1] + " 0");
        }
        for (int i = 0; i < tree.size(); i++) {
            point = tree.get(i);
            System.out.println("C " + point[0] + " " + point[1] + " 0");
        }
    }

    public static void main(String[] args) {
        //  sc_grid(1);
        // bcc_grid(1);
        // fcc_grid(1);
        dendrite2();
    }
}
