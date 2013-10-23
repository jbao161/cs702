/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

import java.util.Arrays;
import java.util.Random;
import numutil.Matrix;

/**
 * Genetic Algorithm for optimizing geometry of atomic clusters
 *
 * see ga() method for the actual code. the useful methods are get_rij for the
 * distances, get_potential for the Lennard-Jones potential, generate_cluster
 * for initial random seeding, sort, splice, crossover, and mutate. the rest of
 * the methods and tests aren't used at all in the ga() method. 
 *
 * @author jbao
 */
public class project02 {

    static double grid_size = 2;
    static Random rand = new Random();
    static int population_size = (int) 1E2;
    static double crossover_rate = 0.7;
    static double mutation_rate = 0.001;

    public static double get_rij(double[] atom1, double[] atom2) {
        double rij = 0;
        double xterm = Math.pow(atom1[0] - atom2[0], 2);
        double yterm = Math.pow(atom1[1] - atom2[1], 2);
        double zterm = Math.pow(atom1[2] - atom2[2], 2);
        rij = Math.sqrt(xterm + yterm + zterm);
        return rij;
    }

    public static double get_potential2(double[][] atoms) {
        return Project01.get_potential(atoms);
    }

    public static double get_potential(double[][] atoms) {
        double potential = 0;
        int num_atoms = atoms.length;
        double sum_forces;
        for (int i = 0; i < num_atoms; i++) {
            sum_forces = 0;
            for (int j = i + 1; j < num_atoms; j++) {
                double rij = get_rij(atoms[i], atoms[j]);
                double term1 = Math.pow(rij, -12);
                double term2 = Math.pow(rij, -6);
                sum_forces += term1 - term2;
            }
            potential += sum_forces;
        }
        return potential;
    }

    public static double[][] generate_cluster(int num_atoms) {
        int num_coordinates = 3;
        double[][] cluster = new double[num_atoms][];
        for (int i = 0; i < num_atoms; i++) {
            cluster[i] = new double[num_coordinates];
            for (int j = 0; j < num_coordinates; j++) {
                cluster[i][j] = rand.nextDouble() * grid_size;
            }
        }
        return cluster;
    }

    public static void sort(double[][][] population, double[] fitness) {
        int population_size, c, d;
        population_size = population.length;
        double swap_fitness;
        double[][] swap_cluster;
        for (c = 0; c < (population_size - 1); c++) {
            for (d = 0; d < population_size - c - 1; d++) {
                if (fitness[d] < fitness[d + 1]) /* For descending order use < */ {
                    swap_fitness = fitness[d];
                    swap_cluster = population[d];
                    fitness[d] = fitness[d + 1];
                    fitness[d + 1] = swap_fitness;
                    population[d] = population[d + 1];
                    population[d + 1] = swap_cluster;
                }
            }
        }
    }

    /**
     * performs a single point crossover halfway along the chromosome, at the
     * nearest atom rounded down. warning: changes the input arrays, so make a
     * copy before using if you want to keep the original!
     *
     * @param parent1
     * @param parent2
     */
    public static void splice(double[][] parent1, double[][] parent2) {
        double[] swap;
        int num_atoms = parent1.length;
        int splice_location = num_atoms / 2;
        for (int i = 0; i < splice_location; i++) {
            swap = parent1[i];
            parent1[i] = parent2[i];
            parent2[i] = swap;
        }
    }

    /**
     * makes a single point crossover at a randomly selected atom, with a
     * probability equal to the crossover rate
     *
     * @param parent1
     * @param parent2
     */
    static void crossover(double[][] parent1, double[][] parent2) {
        double skip = rand.nextDouble();
        if (skip > crossover_rate) { // chance of crossover = crossover_rate
            return;
        }
        double[] swap;
        int num_atoms = parent1.length;
        int splice_location = 1 + rand.nextInt(num_atoms - 1); // pick a crossover point at a random atom
        for (int i = 0; i < splice_location; i++) {
            swap = parent1[i];
            parent1[i] = parent2[i];
            parent2[i] = swap;
        }
    }

    /**
     * each gene (x,y,or z coordinate) has a small chance of changing into a
     * random value
     *
     * @param parent
     * @return
     */
    static int mutate(double[][] parent) {
        int count = 0;
        for (int i = 0; i < parent.length; i++) {
            for (int j = 0; j < parent[i].length; j++) {
                double skip = rand.nextDouble();
                if (skip < mutation_rate) {
                    count++;
                    parent[i][j] = rand.nextDouble() * grid_size;
                }
            }
        }
        return count;
    }

    public static void splice2(double[][] parent1, double[][] parent2) {
        double[] swap;
        int num_atoms = parent1.length;
        int splice_location = num_atoms / 3;
        for (int i = 0; i < splice_location; i++) {
            swap = parent1[i];
            parent1[i] = parent2[i];
            parent2[i] = swap;
        }
        for (int i = num_atoms - splice_location; i < num_atoms; i++) {
            swap = parent1[i];
            parent1[i] = parent2[i];
            parent2[i] = swap;
        }
    }

    static void mutate_swap(double[][] parent) {
        int num_atoms = parent.length;
        int random_atom1 = rand.nextInt(num_atoms);
        int random_atom2 = random_atom1;
        while (random_atom2 == random_atom1) {
            random_atom2 = rand.nextInt(num_atoms);
        }
        int num_coord = parent[random_atom1].length;
        int random_coord = rand.nextInt(num_coord);
        double swap = parent[random_atom1][random_coord];
        parent[random_atom1][random_coord] = parent[random_atom2][random_coord];
        parent[random_atom2][random_coord] = swap;
    }

    static void select_mates(double[][][] population, double[] fitness) {
        double total_fitness = 0;
        for (int i = 0; i < fitness.length; i++) {
            total_fitness += fitness[i];
        }
        double selection = rand.nextDouble() * total_fitness;
        double find_choice = 0;
        int parent1 = 0;
        for (int i = 0; i < fitness.length; i++) {
            find_choice += fitness[i];
            if (selection > find_choice) {
                parent1 = i;
            }
        }
        selection = rand.nextDouble() * total_fitness;
        find_choice = 0;
        int parent2 = 0;
        for (int i = 0; i < fitness.length; i++) {
            find_choice += fitness[i];
            if (selection > find_choice) {
                parent1 = i;
            }
        }
        crossover(population[parent1], population[parent2]);
    }

    public static void print(double[][][] array, double[] fitness) {
        int num_elements = array.length;
        Matrix printer = new Matrix();
        for (int i = 0; i < num_elements; i++) {
            printer.array = array[i];
            printer.print_plain();
            double potential = get_potential(array[i]);
            System.out.println(potential);
            System.out.println(fitness[i]);
        }
    }

    public static void test01() { // generate a random cluster
        double[][] cluster01 = generate_cluster(5);
        Matrix printer = new Matrix(cluster01);
        printer.print();
    }

    public static void test02() { // generate a population of random clusters
        int num_atoms = 5;
        double[][][] population = new double[population_size][][];
        for (int i = 0; i < population_size; i++) {
            population[i] = generate_cluster(num_atoms);
            Matrix printer = new Matrix(population[i]);
            printer.print();
        }
    }

    public static void test03() { // get the rij between two atoms
        double[][] cluster01 = generate_cluster(5);
        double rij = get_rij(cluster01[0], cluster01[1]);
        System.out.println(rij);
    }

    public static void test04() { // get the potential of a cluster
        double[][] cluster01 = generate_cluster(5);
        double potential = get_potential(cluster01);
        System.out.println(potential);
    }

    public static void test05() { // generate a population of negative potential clusters
        int num_atoms = 5;
        double[][][] population = new double[population_size][][];
        for (int i = 0; i < population_size; i++) {
            population[i] = generate_cluster(num_atoms);
            double potential = get_potential(population[i]);
            while (potential >= 0) {
                population[i] = generate_cluster(num_atoms);
                potential = get_potential(population[i]);
            }
            Matrix printer = new Matrix(population[i]);
            printer.print();
            System.out.println(potential);
        }

    }

    public static void test06() { // calculate the fitness for each cluster
        int num_atoms = 5;
        double max_potential = -Double.MAX_VALUE;
        double min_potential = Double.MAX_VALUE;
        double[][][] population = new double[population_size][][];
        double[] fitness = new double[population_size];
        for (int i = 0; i < population_size; i++) {

            double potential = 0;
            while (potential >= 0) {
                population[i] = generate_cluster(num_atoms);
                potential = get_potential(population[i]);
            }
            fitness[i] = potential;

        }
        System.out.println(Arrays.toString(fitness));
        max_potential = numutil.MathTools.max(fitness);
        min_potential = numutil.MathTools.min(fitness);
        for (int i = 0; i < population_size; i++) {
            // System.out.println(fitness[i]);
            fitness[i] = (max_potential - fitness[i]) / (max_potential - min_potential);
            // System.out.println(fitness[i]);
        }
        System.out.println(Arrays.toString(fitness));
    }

    public static void test07() { // sort the population by fitness
        int num_atoms = 5;
        double max_potential;
        double min_potential;
        double[][][] population = new double[population_size][][];
        double[] fitness = new double[population_size];
        for (int i = 0; i < population_size; i++) {
            double potential = 0;
            while (potential >= 0) {
                population[i] = generate_cluster(num_atoms);
                potential = get_potential(population[i]);
            }
            fitness[i] = potential;
        }
        max_potential = numutil.MathTools.max(fitness);
        min_potential = numutil.MathTools.min(fitness);
        for (int i = 0; i < population_size; i++) {
            fitness[i] = (max_potential - fitness[i]) / (max_potential - min_potential);
        }
        print(population, fitness);
        System.out.println(Arrays.toString(fitness));
        sort(population, fitness);
        print(population, fitness);
        System.out.println(Arrays.toString(fitness));

    }

    public static void test08() { // 1 point crossover
        int num_atoms = 5;
        double[][] p1 = generate_cluster(num_atoms);
        double[][] p2 = generate_cluster(num_atoms);
        numutil.MathTools.print(p1);
        numutil.MathTools.print(p2);
        splice(p1, p2);
        numutil.MathTools.print(p1);
        numutil.MathTools.print(p2);
    }

    public static void test09() { // make new population based on fitness
        int num_atoms = 5;
        double max_potential;
        double min_potential;
        double[][][] population = new double[population_size][][];
        double[] fitness = new double[population_size];
        for (int i = 0; i < population_size; i++) {
            double potential = 0;
            while (potential >= 0) {
                population[i] = generate_cluster(num_atoms);
                potential = get_potential(population[i]);
            }
            fitness[i] = potential;
        }
        max_potential = numutil.MathTools.max(fitness);
        min_potential = numutil.MathTools.min(fitness);
        for (int i = 0; i < population_size; i++) {
            fitness[i] = (max_potential - fitness[i]) / (max_potential - min_potential);
        }
        sort(population, fitness);
        // keep 2 most fit
        // change the others
        for (int i = 2; i < population_size; i++) {
            double[][] parentA = numutil.MathTools.copy(population[0]);
            splice(parentA, population[i]);
        }
        numutil.MathTools.print(population);
    }

    static void test10() { // 2 point crossover
        int num_atoms = 5;
        double[][] p1 = generate_cluster(num_atoms);
        double[][] p2 = generate_cluster(num_atoms);
        numutil.MathTools.print(p1);
        numutil.MathTools.print(p2);
        splice2(p1, p2);
        numutil.MathTools.print(p1);
        numutil.MathTools.print(p2);
    }

    static void test11() { // random swap mutation
        int num_atoms = 5;
        double[][] p1 = generate_cluster(num_atoms);
        numutil.MathTools.print(p1);
        mutate_swap(p1);
        numutil.MathTools.print(p1);

    }

    static void test12() { // one complete generation
        int num_atoms = 5;
        double max_potential;
        double min_potential;
        double[][][] population = new double[population_size][][];
        double[] fitness = new double[population_size];
        for (int i = 0; i < population_size; i++) {
            double potential = 0;
            while (potential >= 0) {
                population[i] = generate_cluster(num_atoms);
                potential = get_potential(population[i]);
            }
            fitness[i] = potential;
        }
        max_potential = numutil.MathTools.max(fitness);
        min_potential = numutil.MathTools.min(fitness);
        for (int i = 0; i < population_size; i++) {
            fitness[i] = (max_potential - fitness[i]) / (max_potential - min_potential);
        }
        sort(population, fitness);
        numutil.MathTools.print(population);
        // keep most fit
        double[][] parentA = numutil.MathTools.copy(population[0]);
        // mutate the second most fit
        double[][] parentB = numutil.MathTools.copy(population[1]);
        mutate_swap(population[1]);
        // make a 1 point crossover between A and B
        splice(parentA, parentB);
        population[population_size - 1] = parentB;
        population[population_size - 2] = parentA;
        // change the others
        for (int i = 2; i < population_size - 2; i++) {
            parentA = numutil.MathTools.copy(population[0]);
            splice2(parentA, population[i]);
        }
        System.out.println("");
        System.out.println("");
        numutil.MathTools.print(population);
    }

    static void test13() {
        grid_size = 3;
        int num_generations = 1000;
        int num_atoms = 13;
        int population_size = 10;
        double max_potential;
        double min_potential;
        double[][][] population = new double[population_size][][];
        double[] fitness = new double[population_size];
        // seed the initial population
        for (int i = 0; i < population_size; i++) {
            double potential = 0;
            while (potential >= 0) {
                population[i] = generate_cluster(num_atoms);
                potential = get_potential(population[i]);
            }
        }
        System.out.println("initial seed complete");
        // get the potentials for each cluster individual
        for (int i = 0; i < population_size; i++) {
            fitness[i] = get_potential(population[i]);
        }
        max_potential = numutil.MathTools.max(fitness);
        min_potential = numutil.MathTools.min(fitness);
        // assign each individual a fitness rating
        for (int i = 0; i < population_size; i++) {
            fitness[i] = (max_potential - fitness[i]) / (max_potential - min_potential);
        }
        // reorder the individuals by fitness
        sort(population, fitness);
        numutil.MathTools.print_plain(population[0]);
        System.out.println(min_potential);
        // create generations
        for (int k = 0; k < num_generations; k++) {
            // get the potentials for each cluster individual
            for (int i = 0; i < population_size; i++) {
                fitness[i] = get_potential(population[i]);
            }
            max_potential = numutil.MathTools.max(fitness);
            min_potential = numutil.MathTools.min(fitness);
            // assign each individual a fitness rating
            for (int i = 0; i < population_size; i++) {
                fitness[i] = (max_potential - fitness[i]) / (max_potential - min_potential);
            }
            // reorder the individuals by fitness
            sort(population, fitness);

            /*
             * start genetic algorithm criteria
             */
            // keep most fit
            double[][] parentA = numutil.MathTools.copy(population[0]);
            // mutate the second most fit
            double[][] parentB = numutil.MathTools.copy(population[1]);
            // make a 1 point crossover between A and B
            splice(parentA, parentB);
            population[population_size - 1] = parentB;
            population[population_size - 2] = parentA;
            // change the others
            for (int i = 2; i < population_size - 2; i++) {
                parentA = numutil.MathTools.copy(population[0]);
                splice2(parentA, population[i]);
            }
            /*
             * end genetic algorithm criteria
             */
        }
        for (int i = 0; i < population_size; i++) {
            fitness[i] = get_potential(population[i]);
        }
        max_potential = numutil.MathTools.max(fitness);
        min_potential = numutil.MathTools.min(fitness);
        numutil.MathTools.print_plain(population[0]);
        System.out.println(min_potential);
        System.out.println(max_potential);
    }

    static void test14() { // crossover step
        int num_atoms = 5;
        double[][] p1 = generate_cluster(num_atoms);
        double[][] p2 = generate_cluster(num_atoms);
        numutil.MathTools.print(p1);
        numutil.MathTools.print(p2);
        crossover(p1, p2);
        numutil.MathTools.print(p1);
        numutil.MathTools.print(p2);
    }

    static void test15() { // mutation
        int num_atoms = 5;
        double[][] p1 = generate_cluster(num_atoms);
        numutil.MathTools.print(p1);
        int count = 0;
        for (int i = 0; i < 1.0 / mutation_rate; i++) {
            // expect to see num_atoms*num_coord number of mutations (5x3) = 15 mutations
            count += mutate(p1);
        }
        numutil.MathTools.print(p1);
        System.out.println("expected mutations: " + num_atoms * 3 + "; actual: " + count + " mutations");
    }

    public static void main(String[] args) {
        //test01();
        //test02();
        //test03();
        //test04();
        //test05();
        //test06();
        //test07();
        //test08();
        //test09();
        // test10();
        // test11();
        // test12();
        // test13();
        // test14();
        // test15();
        ga();
    }

    static void ga() {
        grid_size = 3;
        population_size = 100;
        int num_generations = 10000;
        int num_atoms = 13;
        double seed_potential = -2; // highest potential allowed for initial seeding

        double max_potential;
        double min_potential;
        double[][][] population = new double[population_size][][];
        double[] fitness = new double[population_size];
        // seed the initial population
        for (int i = 0; i < population_size; i++) {
            double potential = 0;
            while (potential >= seed_potential) {
                population[i] = generate_cluster(num_atoms);
                potential = get_potential(population[i]);
            }
        }
        // get the potentials for each cluster individual
        for (int i = 0; i < population_size; i++) {
            fitness[i] = get_potential(population[i]);
        }
        max_potential = numutil.MathTools.max(fitness);
        min_potential = numutil.MathTools.min(fitness);
        // assign each individual a fitness rating
        for (int i = 0; i < population_size; i++) {
            fitness[i] = (max_potential - fitness[i]) / (max_potential - min_potential);
        }
        // reorder the individuals by fitness
        sort(population, fitness);
        System.out.println("initial seed complete");
        // iterate over generations
        for (int k = 0; k < num_generations; k++) {
            // get the potentials for each cluster individual
            for (int i = 0; i < population_size; i++) {
                fitness[i] = get_potential(population[i]);
            }
            max_potential = numutil.MathTools.max(fitness);
            min_potential = numutil.MathTools.min(fitness);
            // assign each individual a fitness rating
            for (int i = 0; i < population_size; i++) {
                fitness[i] = (max_potential - fitness[i]) / (max_potential - min_potential);
            }
            // reorder the individuals by fitness
            sort(population, fitness);
            // calculate total fitness for mating selection
            double total_fitness = 0;
            for (int i = 0; i < fitness.length; i++) {
                total_fitness += fitness[i];
            }

            // display trend of fittest member of population
            if (k % (num_generations / 10) == 0) { // show ten snapshots
                // numutil.MathTools.print_plain(population[0]);
                System.out.println(min_potential);
                System.out.println(max_potential);
            }

            /*
             * start genetic algorithm criteria
             */
            // keep most fit
            double[][] parentA = numutil.MathTools.copy(population[0]);
            double[][] parentB = numutil.MathTools.copy(population[1]);
            // make a 1 point crossover between A and B
            splice(parentA, parentB);
            population[population_size - 1] = parentB;
            population[population_size - 2] = parentA;
            // mate using selection for everybody except lowest rated individual
            // (first individual j=0 is A the fittest, he's left untouched)
            // (the last two individuals are the offspring of A and B)
            for (int j = 1; j < population_size - 2; j++) {
                double potential = 1;
                double[][] parent1 = population[j];
                while (potential > 0) { // don't let the offspring have positive potential
                    double selection1 = rand.nextDouble() * total_fitness; // pick a random point on the interval from zero to total fitness
                    double find_choice = 0;
                    int mate1 = 0;
                    // now see which indivdual that random point maps to by adding individual fitness ratings until the interval that individual is assigned to brackets the random point
                    for (int i = 0; i < fitness.length; i++) {
                        find_choice += fitness[i];
                        if (selection1 > find_choice) {
                            mate1 = i; // identified the individual chosen for mating
                        }
                    }
                    double selection2 = selection1;
                    while (selection2 == selection1) { // don't pick the same individual to be its mating partner
                        selection2 = rand.nextDouble() * total_fitness;
                    }
                    // repeat the random point mapping as in the first selection for the second mating individual
                    find_choice = 0;
                    int mate2 = 0;
                    for (int i = 0; i < fitness.length; i++) {
                        find_choice += fitness[i];
                        if (selection2 > find_choice) {
                            mate2 = i;
                        }
                    }
                    parent1 = numutil.MathTools.copy(population[mate1]);
                    double[][] parent2 = numutil.MathTools.copy(population[mate2]);
                    crossover(parent1, parent2);
                    mutate(parent1);
                    potential = get_potential(parent1);
                }
                population[j] = parent1;
            }
            /*
             * end genetic algorithm criteria
             */
        }
        for (int i = 0; i < population_size; i++) {
            fitness[i] = get_potential(population[i]);
        }
        max_potential = numutil.MathTools.max(fitness);
        min_potential = numutil.MathTools.min(fitness);
        //numutil.MathTools.print_plain(population[0]);
        System.out.println(min_potential);
        System.out.println(max_potential);
    }
}
