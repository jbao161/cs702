/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

/**
 *
 * @author jbao
 */
import java.util.ArrayList;
import java.util.Random;

public class percolation_1 {

    /**
     * set variables here:
     */
    static Random rand = new Random();
    static int x_size = 100; // width of grid
    static int y_size = 100; // length of grid
    static double p = 0.4; // percolation probability
    static double random_seed = 1137; // seed for random number generator
    static int verbose = 2; // use 1 to display all intermediate calculations. 0: don't show. 2: show final labels.
    static int cat = 0;

    /*
     * 
     */
    /**
     * randomly constructs a X by Y percolation grid
     *
     * @param x_size number of rows
     * @param y_size number of columns
     * @param p probability of a point being occupied
     * @return
     */
    static int[] get_grid(int x_size, int y_size, double p) {
        // random number generator

        double random_choice;

        int count = 0; // number of occupied positions (to check randomness)

        int i, j, k;
        // percolation grid
        int grid_size = x_size * y_size;
        int[] grid = new int[grid_size];
        for (i = 0, k = 0; i < x_size; i++) { // row
            for (j = 0; j < y_size; j++, k++) { // column
                // randomly pick whether the position is occupied
                random_choice = rand.nextDouble();
                if (random_choice < p) {
                    grid[k] = 1;
                    count++;
                } else {
                    grid[k] = 0;
                }
            }
        }
        return grid;
    }

    static void print_grid(int[] grid, int x_size, int y_size, double p) {
        int num_occupied = 0;
        int i, j, k;
        System.out.println(x_size + "x" + y_size + " percolation grid:\r\n");
        for (i = 0, k = 0; i < x_size; i++) {
            for (j = 0; j < y_size; j++, k++) {
                System.out.print(String.format("%03d ", grid[k]));
                if (grid[k] != 0) {
                    num_occupied++;
                }
            }
            System.out.println("");
        }
    }

    static void print_cluster(int[] grid, int x_size, int y_size) {
        int num_occupied = 0;
        int i, j, k;
        System.out.println(x_size + "x" + y_size + " grid:\r\n");
        for (i = 0, k = 0; i < x_size; i++) {
            for (j = 0; j < y_size; j++, k++) {
                System.out.print(String.format("%02d ", grid[k]));
            }
            System.out.println("");
        }
        System.out.println("\r\n");
    }

    static int[] copy(int[] grid, int grid_size) {
        int i;
        int[] grid_copy;
        grid_copy = new int[grid_size];
        for (i = 0; i < grid_size; i++) {
            grid_copy[i] = grid[i];
        }
        return grid_copy;
    }

    /**
     * assigns a label to an occupied point using the smaller category of the
     * neighbors to the left and above.
     *
     * @param left category of the neighbor to the left
     * @param above category of the neighbor above
     * @param cat the number of categories used
     * @return
     */
    static int assign(int left, int above) {
        // if there are no neighbors, put it in a new category
        if (left == 0 && above == 0) {
            cat++;
            return cat;
        }
        // if there is one neighbor to the left, return its category
        if (left == 0) {
            return above;
        }
        // if there is one neighbor to the top, return its category
        if (above == 0) {
            return left;
        }
        // if there are two neighbors, return the smaller of the two categories
        if (left > above) {
            return above;
        }
        return left;
    }

    static void check(int left, int above, int cell, ArrayList<ArrayList<Integer>> equivalence) {
        // if two categories are connected
        if (left != 0) {
            // find which category is larger
            int max = left;
            int min = cell;
            if (cell > left) {
                max = cell;
                min = left;
            }

            if (!(equivalence.get(max).contains(min))) {
                equivalence.get(max).add(min);
            }
            if (!(equivalence.get(min).contains(max))) {
                equivalence.get(min).add(max);
            }


        }
        if (above != 0) {
            // find which category is larger
            int max = above;
            int min = cell;
            if (cell > above) {
                max = cell;
                min = above;
            }
            if (!equivalence.get(max).contains(min)) {
                equivalence.get(max).add(min);
            }
            if (!equivalence.get(min).contains(max)) {
                equivalence.get(min).add(max);
            }
        }
    }

    static int[] count(int[] clusters, int cluster_size, int cats) {
        int i, j;
        int cell;
        int[] counts;
        if (cats > 1) {
            counts = new int[cats];
            for (i = 0; i < cluster_size; i++) {
                cell = clusters[i];
                for (j = 0; j < cats; j++) {
                    if (cell == j) {
                        counts[j]++;
                    }
                }
            }
        } else {
            counts = new int[2];
            counts[0] = 0;
            counts[1] = cluster_size;
        }

        return counts;
    }

    /**
     * assigns labels to a percolation grid using a Hoshen Kopelman style
     * algorithm
     *
     * @param grid
     * @param x_size
     * @param y_size
     */
    static double categorize(int[] grid, int x_size, int y_size) {
        int i, j, k;
        cat = 0; // the number of labels used

        int cell; // the grid point under consideration
        int grid_size = x_size * y_size;

        /* 
         * construct a labeled grid
         */
        int[] clusters = copy(grid, grid_size);
        // separate occupied points in the first row by horizontal gaps
        if (grid[0] == 1) {
            cat++;
        }
        for (k = 1; k < y_size; k++) {
            cell = grid[k];
            if (cell == 1) {
                if (grid[k - 1] == 0) {
                    cat++;
                }
                clusters[k] = cat;
            }
        }

        // label each cell in the remaining rows using its neighbors
        for (i = 1; i < x_size; i++) {
            // the first element in each row 
            cell = grid[k];
            if (cell == 1) {
                // label depends only on the cell above
                int above = clusters[k - y_size];
                clusters[k] = assign(0, above);
            }
            k++;
            // the remaining elements in the row
            for (j = 1; j < y_size & k < grid.length; j++, k++) {
                cell = grid[k];
                if (cell == 1) {
                    // label depends on cell above and cell to the left
                    int left = clusters[k - 1];
                    int above = clusters[k - y_size];
                    clusters[k] = assign(left, above);
                }
            }
        }
        if (verbose == 2) {
            System.out.println("first pass initial label:\r\n");
            print_cluster(clusters, x_size, y_size);
        }

        /**
         * create an equivalence class partition table showing which categories
         * connect
         */
        ArrayList<ArrayList<Integer>> equivalence = new ArrayList<ArrayList<Integer>>(cat);
        for (i = 0; i < cat; i++) {
            ArrayList<Integer> eclass = new ArrayList<Integer>();
            eclass.add(i);
            equivalence.add(eclass);
        }

        k = y_size; // start checking from the second row; it will take care of the first row
        for (i = 1; i < x_size; i++) {
            cell = clusters[k];
            if (cell != 0) {
                int above = clusters[k - y_size];
                check(0, above, cell, equivalence);
            }
            k++;
            for (j = 1; j < y_size; j++, k++) {
                cell = clusters[k];
                if (cell != 0) {
                    int left = clusters[k - 1];
                    int above = clusters[k - y_size];
                    check(left, above, cell, equivalence);
                }
            }
        }
        if (verbose == 1) {
            // printf("equivalence class matrix:\r\n");
            //printf("    (if X ~ Y then matrix[X][Y] = Y and matrix[Y][X] = X)\r\n");
            for (int m = 0; m < equivalence.size(); m++) {
                System.out.print("equivalence: ");
                for (int r = 0; r < equivalence.get(m).size(); r++) {
                    System.out.print(equivalence.get(m).get(r) + " ");
                }
                System.out.println("");
            }
        }


        /**
         * create a reduced equivalence class mapping from each category to its
         * smallest equivalent
         */
        int[] remap = new int[cat];
        for (i = 0; i < cat; i++) {
            remap[i] = i + 1;
        }
        for (i = cat - 1; i > 0; i--) {
            // find the lowest equivalence class
            int lowest = cat;
            for (j = 0; j < equivalence.get(i).size(); j++) {
                if (equivalence.get(i).get(j) < lowest) {
                    lowest = equivalence.get(i).get(j);
                }
            }
            remap[i] = lowest;
        }
        remap[0] = 0;
        if (verbose == 1) {
            //printf("equivalence class mapping:\r\n");
            // printf("    map[X] = Y   X ~ Y, where Y is the smallest member of the equivalence class\r\n");
            print_cluster(remap, 1, cat);
        }
        /**
         * renumber the mapping using the smallest numbers necessary
         */
        int cats_needed = 0; // number of categories needed
        for (i = 1; i < remap.length; i++) {
            int label = remap[i];
            if (i == label) { // if the numbers match, that category defines a unique class
                cats_needed++; // so increase the lowest number of categories needed by one
                // then change all the members of that class to the next lowest category number
                for (j = 0; j < remap.length; j++) {
                    if (remap[j] == label) {
                        remap[j] = cats_needed;
                    }
                }
            }
        }
        if (verbose == 2) {
            // printf("reduced equivalence class mapping:\r\n");
            //printf("    map[X] = Y   X ~ Y, where Y is the smallest member of the equivalence class\r\n");
            // printf("                        and Y is the smallest numbering required\r\n");
            print_cluster(remap, 1, cat);
        }
        /**
         * use the renumbered mapping to convert all the labels to their reduced
         * forms
         */
        k = 0;
        for (i = 0; i < x_size; i++) {
            for (j = 0; j < y_size; j++, k++) {
                if (clusters[k] != 0) {
                    clusters[k] = remap[clusters[k]-1];
                }
            }
        }

        int[] counts = count(clusters, grid_size, cats_needed);
        if (counts[0] == 0) {
            cats_needed++;
        }

        if (verbose == 2) {
            //printf("identified clusters labeled on a percolation grid:\r\n");
            print_cluster(clusters, x_size, y_size);
            // printf("number of points in each category: unoccupied, category01, category02, ...\r\n");
            print_cluster(counts, 1, cats_needed);
        }
        // find the largest populated cluster
        int max = 0;
        int max_cat = 0;
        for (i = 1; i < cats_needed; i++) {
            if (counts[i] > max) {
                max = counts[i];
                max_cat = i;
            }
        }
        if (max == 0) {
            return 0;
        }

// see if that cluster spans
        //System.out.println("max, maxcat " + max + "  " + max_cat);
        int spans = 0;
        // does it go north south
        int check_n = 0;
        int check_s = 0;
        int check_e = 0;
        int check_w = 0;
        int num_points = x_size * y_size;
        for (i = 0; i < y_size; i++) {
            if (clusters[i] == max_cat) {

                check_n++;
            }
        }
        for (i = num_points - 1; i > num_points - 1 - y_size; i--) {
            if (clusters[i] == max_cat) {
                check_s++;
            }
        }
        for (i = 0; i < x_size; i++) {
            if (clusters[i * y_size] == max_cat) {
                check_w++;
            }
        }
        for (i = 1; i < x_size; i++) {
            if (clusters[i * y_size - 1] == max_cat) {
                check_e++;
            }
        }
        if (check_n > 0 & check_s > 0) {
            spans = 1;
            //System.out.println("vertical span");
            //printf("vertical span\r\n");
            //printf("%d %d\r\n", check_n, check_s);
        }
        if (check_e > 0 & check_w > 0) {
            spans = 1;
            //System.out.println("horizontal span");
            //printf("horizontal span\r\n");
            //printf("%d %d\r\n", check_e, check_w);
        }
        double pterm = 0;
        if (spans == 1) {
            double n_cluster = (double) max;
            double n_occupied = (double) (num_points - counts[0]);
            //System.out.println("max " + max);
            pterm = n_cluster / n_occupied;
            //printf("%3.5f\r\n", pterm);
        }

        return pterm;
    }

    /*
     * 
     */
    public static void main(String[] args) {
        double j;
        x_size = 10;
        y_size = 10;
        for (j = .5; j < .51; j += 0.01) {
            double avg_probability = 0;
            int i;
            //System.out.println(j);
            double num_trials = 1;
            for (i = 0; i < num_trials; i++) {
                int[] lattice = get_grid(x_size, y_size, j);
                if (verbose == 1) {
                    print_grid(lattice, x_size, y_size, j);
                }
                double pterm = categorize(lattice, x_size, y_size);
//               print_grid(clusters, x_size, y_size, p);
                if (pterm == 0) {
                    //i--;
                } else {
                    avg_probability += pterm;
                }

            }
            avg_probability /= num_trials;
            System.out.println("p, pterm " + j + "  " + avg_probability);
        }

    }
}
/* 
 * File:   main.cpp
 * Author: jbao
 *
 * Created on October 27, 2013, 7:03 PM
 */
