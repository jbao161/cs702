/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

/**
 *
 * @author jbao
 */
import java.util.Random;

public class percolation {

    /**
     * set variables here:
     */
    static Random rand = new Random();
    static int x_size = 10; // width of grid
    static int y_size = 10; // length of grid
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
                System.out.print(String.format("%02d", grid[k])+"  ");
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
                System.out.print( String.format("%02d", grid[k])+"  ");
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

    static void check(int left, int above, int cell, int[] equivalence) {
        int k1, k2;
        // if two categories are connected
        if (left != 0) {
            // find which category is larger
            int max = left;
            int min = cell;
            if (cell > left) {
                max = cell;
                min = left;
            }
            k1 = (max - 1) * cat + min - 1;
            k2 = (min - 1) * cat + max - 1;
            equivalence[k1] = min;
            equivalence[k2] = max;

        }
        if (above != 0) {
            // find which category is larger
            int max = above;
            int min = cell;
            if (cell > above) {
                max = cell;
                min = above;
            }
            k1 = (max - 1) * cat + min - 1;
            k2 = (min - 1) * cat + max - 1;
            equivalence[k1] = min;
            equivalence[k2] = max;
        }
    }

    static int[] count(int[] clusters, int cluster_size, int cats) {
        int i, j;
        int cell;
        int[] counts = new int[cats];

        for (i = 0; i < cluster_size; i++) {
            cell = clusters[i];
            for (j = 0; j < cats; j++) {
                if (cell == j) {
                    counts[j]++;
                }
            }
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
    static int[] categorize(int[] grid, int x_size, int y_size) {
        int i, j, k;
        cat = 1; // the number of labels used

        int cell; // the grid point under consideration
        int grid_size = x_size * y_size;

        /* 
         * construct a labeled grid
         */
        int[] clusters = copy(grid, grid_size);
        // separate occupied points in the first row by horizontal gaps
        for (j = 0, k = 1; j < y_size; j++, k++) {
            cell = grid[k];
            if (cell == 1) {
                if (k > 0) {
                    if (grid[k - 1] == 0) {
                        cat++;
                    }
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
        if (verbose == 1) {
            System.out.println("first pass initial label:\r\n");
            print_cluster(clusters, x_size, y_size);
        }

        /**
         * create an equivalence class partition table showing which categories
         * connect
         */
        int[] equivalence = new int[cat * cat];
        for (i = 0, k = 0; i < cat; i++) {
            for (j = 0; j < cat; j++, k++) {
                equivalence[k] = i + 1;
            }
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
            print_cluster(equivalence, cat, cat);
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
            k = i * cat - 1;
            // find the lowest equivalence class
            int lowest = cat;
            for (j = 0; j < cat; j++) {
                k++;
                if (equivalence[k] < lowest) {
                    lowest = equivalence[k];
                }
            }
            // check the ones that map to this category as lowest to update to the lower one
            for (j = 0; j < cat; j++) {
                if (remap[j] == i + 1) {
                    remap[j] = lowest;
                }
            }
            // update this category to map to the lowest equivalence class
            if (remap[i] > lowest) {
                remap[i] = lowest;
            }
            // go back and set all the other members in the equivalence class to map to the lowest
            k = i * cat - 1;
            for (j = 0; j < cat; j++) {
                k++;
                //set the ones that connect to it that aren't the lowest to map to the lowest
                int member = equivalence[k];
                if (lowest < remap[member - 1]) {
                    remap[member - 1] = lowest;
                }
            }
        }
        if (verbose == 1) {
            //printf("equivalence class mapping:\r\n");
            // printf("    map[X] = Y   X ~ Y, where Y is the smallest member of the equivalence class\r\n");
            print_cluster(remap, 1, cat);
        }
        /**
         * renumber the mapping using the smallest numbers necessary
         */
        int cats_needed = 0; // number of categories needed
        for (i = 1; i <= cat; i++) {
            int label = remap[i - 1];
            if (i == label) { // if the numbers match, that category defines a unique class
                cats_needed++; // so increase the lowest number of categories needed by one
                // then change all the members of that class to the next lowest category number
                for (j = 0; j < cat; j++) {
                    if (remap[j] == label) {
                        remap[j] = cats_needed;
                    }
                }
            }
        }
        if (verbose == 1) {
            // printf("reduced equivalence class mapping:\r\n");
            //printf("    map[X] = Y   X ~ Y, where Y is the smallest member of the equivalence class\r\n");
            // printf("                        and Y is the smallest numbering required\r\n");
            print_cluster(remap, 1, cat);
        }
        /**
         * use the renumbered mapping to convert all the labels to their reduced
         * forms
         */
        for (i = 0, k = 0; i < x_size; i++) {
            for (j = 0; j < y_size; j++, k++) {
                if (clusters[k] != 0) {
                    clusters[k] = remap[clusters[k] - 1];
                }
            }
        }

        if (verbose == 2) {
            //printf("identified clusters labeled on a percolation grid:\r\n");
            print_cluster(clusters, x_size, y_size);
            int[] counts = count(clusters, grid_size, cats_needed);
            // printf("number of points in each category: unoccupied, category01, category02, ...\r\n");
            print_cluster(counts, 1, cats_needed);
        }

        return clusters;
    }

    /*
     * 
     */
    public static void main(String[] args) {
        int[] lattice = get_grid(x_size, y_size, p);
        if (verbose == 1 || verbose == 2) {
            print_grid(lattice, x_size, y_size, p);
        }
        int[] clusters = categorize(lattice, x_size, y_size);

    }
}
/* 
 * File:   main.cpp
 * Author: jbao
 *
 * Created on October 27, 2013, 7:03 PM
 */
