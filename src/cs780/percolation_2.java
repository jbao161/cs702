/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author jbao
 */
public class percolation_2 {

    static Random rand = new Random();
    static int x_size = 100; // width of grid
    static int y_size = 100; // length of grid
    static double p = 0.8; // percolation probability
    static double random_seed = 1137; // seed for random number generator
    static int verbose = 2; // use 1 to display all intermediate calculations. 0: don't show. 2: show final labels.
    static int cat = 0;
    static int cats_needed = 0;

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
                System.out.print(String.format("%04d", grid[k]) + "  ");
                if (grid[k] != 0) {
                    num_occupied++;
                }
            }
            System.out.println("");
        }
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

    static int[] categorize(int[] grid, int x_size, int y_size) {
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
        return clusters;
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

    static int[] reduce(int[] clusters, int x_size, int y_size) {
        int i, j, k;
        int cell; // the grid point under consideration
        int grid_size = x_size * y_size;

        int[] reduced_map = new int[cat + 1];
        ArrayList<ArrayList<Integer>> equivalence = new ArrayList<ArrayList<Integer>>(cat + 1);
        for (i = 0; i < cat + 1; i++) {
            ArrayList<Integer> eclass = new ArrayList<Integer>();
            eclass.add(i);
            equivalence.add(eclass);
        }
        for (i = 0; i < cat + 1; i++) {
            reduced_map[i] = equivalence.get(i).get(0);
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
        boolean changes = true;
        while (changes) {
            changes = false;
            for (i = cat; i > 0; i--) {
                // find the lowest equivalence class
                int lowest = cat;
                for (j = 0; j < equivalence.get(i).size(); j++) {
                    if (equivalence.get(i).get(j) < lowest) {
                        lowest = equivalence.get(i).get(j);
                    }
                }

                // check the ones that map to this category as lowest to update to the lower one
                for (j = 0; j < cat; j++) {
                    if (reduced_map[j] == i & reduced_map[j] > lowest) {
                        equivalence.get(j).add(lowest);
                        reduced_map[j] = lowest;
                        changes = true;
                    }
                }
                // update this category to map to the lowest equivalence class
                if (reduced_map[i] > lowest) {
                    reduced_map[i] = lowest;
                    changes = true;
                }
                // go back and set all the other members in the equivalence class to map to the lowest

                for (j = 0; j < equivalence.get(i).size(); j++) {
                    int member = equivalence.get(i).get(j);
                    if (lowest < reduced_map[member]) {
                        equivalence.get(member).add(lowest);
                        reduced_map[member] = lowest;
                        changes = true;
                    }
                }
            }
        }
        reduced_map[0] = 0;

        return reduced_map;
    }

    static void down(int i, int j, int lowest, ArrayList<ArrayList<Integer>> equivalence, int[] reduced_map) {
        System.out.println("i,j," + i + " " + j);
        System.out.println(equivalence.get(i).size());
        int member = equivalence.get(i).get(j);
        if (lowest < reduced_map[member]) {
            reduced_map[member] = lowest;
            for (int k = 0; k < equivalence.get(member).size(); k++) {
                reduced_map[member] = lowest;
                System.out.println("sub exe");
                down(j, k, lowest, equivalence, reduced_map);
                System.out.println("end sub exe");
            }
        }
    }

    static int[] shrink_labels(int[] clusters, int[] reduced_map) {
        int i, j, k;
        cats_needed = 0; // number of categories needed
        for (i = 1; i < reduced_map.length; i++) {
            int label = reduced_map[i];
            if (i == label) { // if the numbers match, that category defines a unique class
                cats_needed++; // so increase the lowest number of categories needed by one
                // then change all the members of that class to the next lowest category number
                for (j = 0; j < reduced_map.length; j++) {
                    if (reduced_map[j] == label) {
                        reduced_map[j] = cats_needed;
                    }
                }
            }
        }
        return reduced_map;
    }

    static double check_span(int[] clusters, int[] reduced_map, int[] counts) {
        // find the largest populated cluster
        int max = 0;
        int max_cat = 0;
        int i, j, k;
        for (i = 0; i <= cats_needed; i++) {
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

    static int[] rename(int[] clusters, int[] reduced_map) {
        int i, j, k;
        k = 0;
        for (i = 0; i < x_size; i++) {
            for (j = 0; j < y_size; j++, k++) {
                clusters[k] = reduced_map[clusters[k]];
            }
        }
        return clusters;
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

    static double single_run(int x, int y, double prob) {
        x_size = x;
        y_size = y;
        p = prob;
        int[] grid = get_grid(x_size, y_size, p);
        //print_grid(grid, x_size, y_size, p);
        int[] clusters = categorize(grid, x_size, y_size);
        //print_grid(clusters, x_size, y_size, p);
        int[] reduced_map = reduce(clusters, x_size, y_size);
        //print_grid(reduced_map, 1, cat + 1, p);
        reduced_map = shrink_labels(clusters, reduced_map);
        // print_grid(reduced_map, 1, reduced_map.length, p);
        clusters = rename(clusters, reduced_map);
        //print_grid(clusters, x_size, y_size, p);
        int[] counts = count(clusters, x_size * y_size, cats_needed + 1);
         //print_grid(counts, 1, cats_needed + 1, p);
        double pterm = check_span(clusters, reduced_map, counts);
        return pterm;
    }

    static double avg_run(int x, int y, double prob) {
        int iter = 1;
        double avg_pterm = 0;
        for (int i = 0; i < iter; i++) {
            double pterm = single_run(x, y, prob);
            if (pterm == 0) {
                i--;
            } else {
                avg_pterm += pterm;
            }
        }
        avg_pterm /= iter;
        return avg_pterm;
    }

    public static void main(String[] args) {
        int x = 600;
        int y = 600;
        double prob = 0.592746;
        double avg_pterm = 0;
//        avg_pterm = single_run(x, y, prob);
//        System.out.println(prob + " " + avg_pterm);
//        avg_pterm = avg_run(x, y, prob);
//        System.out.println(prob + " " + avg_pterm);
        for (prob = 0.8; prob >0.5; prob -= 0.01) {
            avg_pterm = avg_run(x, y, prob);
            System.out.println(prob + " " + avg_pterm);
        }
    }
}
