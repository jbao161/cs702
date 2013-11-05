/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author jbao
 */
public class hw06 {

    static double simplewalk(int num_steps, int num_walks, int dim) {
        Random rand = new Random(1137);
        int direction;
        double x, y, z, d1;
        double displacement_avg = 0;
        for (int k = 0; k < num_walks; k++) {
            x = 0;
            y = 0;
            z = 0;
            d1 = 0;
            for (int i = 0; i < num_steps; i++) { // steps in a walk
                direction = rand.nextInt(dim * 2);
                switch (direction) {
                    case 0:
                        x++;
                        break;
                    case 1:
                        x--;
                        break;
                    case 2:
                        y++;
                        break;
                    case 3:
                        y--;
                        break;
                    case 4:
                        z++;
                        break;
                    case 5:
                        z--;
                        break;
                    case 6:
                        d1++;
                        break;
                    case 7:
                        d1--;
                        break;

                }
            }
            double displacement = Math.sqrt(x * x + y * y + z * z + d1 * d1);
            displacement_avg += displacement / num_walks;
        }
        return displacement_avg;
    }

    public static void ebb6a() {
        for (int i = 0; i < 1000; i += 10) {
            double dp = simplewalk(i, 1000, 2);
            System.out.println(dp);
        }
        System.out.println("break at 1000");
        for (int i = 1000; i < 2500; i += 100) {
            double dp = simplewalk(i, 1000, 2);
            System.out.println(dp);
        }
    }

    public static void ebb6b() {
        for (int i = 0; i < 1000; i += 10) {
            double dp = simplewalk(i, 1000, 3);
            System.out.println(dp);
        }
        System.out.println("break at 1000");
        for (int i = 1000; i < 2500; i += 100) {
            double dp = simplewalk(i, 1000, 3);
            System.out.println(dp);
        }
        for (int i = 0; i < 1000; i += 10) {
            double dp = simplewalk(i, 1000, 4);
            System.out.println(dp);
        }
        System.out.println("break at 1000");
        for (int i = 1000; i < 2500; i += 100) {
            double dp = simplewalk(i, 1000, 4);
            System.out.println(dp);
        }
    }

    static void ex28() {
        double v = 500;
        double lambda = 1;
        int num_steps = (int) (v / lambda);
        int num_iter = 100;

    }

    static double randomwalk(int num_steps, int num_runs) {
        double avg_displacement = 0;
        Random rand = new Random(1137);
        double sq_displacement = 0;
        double[] position = null;
        double[] direction;
        double costheta; // z for random walk
        double phi; // polar angle for random walk
        double theta;
        double x, y, z;

        for (int i = 0; i < num_runs; i++) {
            position = new double[]{0, 0, 0}; // start at origin
            for (int j = 0; j < num_steps; j++) {
                phi = rand.nextDouble() * 2 * Math.PI; // 0 to 2*pi random number

                //  System.out.println(phi);
                costheta = -1 + rand.nextDouble() * 2; //-1 to 1 random number
                //System.out.println(costheta);
                theta = Math.acos(costheta);
                //System.out.println(theta);
                x = Math.cos(phi) * Math.sin(theta);
                y = Math.sin(phi) * Math.sin(theta);
                z = costheta;
                direction = new double[]{x, y, z};
                // numutil.MathTools.printdata(direction);
                //System.out.println(distance(direction));
                position = add(position, direction);

            }
            //numutil.MathTools.printdata(position);
            //System.out.println(distance(position));
            avg_displacement += distance(position);
            sq_displacement += Math.pow(distance(position), 2);

        }
        avg_displacement /= num_runs;
        sq_displacement /= num_runs;
        System.out.println(sq_displacement);
        // System.out.println(avg_displacement);
        return avg_displacement;
    }

    static double distance(double[] v) {
        double sum = 0;
        for (int i = 0; i < v.length; i++) {
            sum += Math.pow(v[i], 2);
        }
        return Math.sqrt(sum);
    }

    static double[] add(double[] v1, double[] v2) {
        double[] result = new double[v1.length];
        for (int i = 0; i < v1.length; i++) {
            result[i] = v1[i] + v2[i];
        }
        return result;
    }

    static double randomwalk2(int num_steps, int num_runs) {
        double avg_displacement = 0;
        double sq_displacement = 0;
        Random rand = new Random(1137);

        double[] position = null;
        double[] direction;
        double costheta; // z for random walk
        double phi; // polar angle for random walk
        double theta;
        double x, y, z;

        for (int i = 0; i < num_runs; i++) {
            position = new double[]{0, 0, 0}; // start at origin
            for (int j = 0; j < num_steps; j++) {
                phi = rand.nextDouble() * 2 * Math.PI; // 0 to 2*pi random number

                //  System.out.println(phi);
                costheta = -1 + rand.nextDouble() * 2; //-1 to 1 random number
                //System.out.println(costheta);
                theta = Math.acos(costheta);
                if (theta > 0 && theta < Math.PI / 4) {
                    // donothing
                } else {
                    //System.out.println(theta);
                    x = Math.cos(phi) * Math.sin(theta);
                    y = Math.sin(phi) * Math.sin(theta);
                    z = costheta;
                    direction = new double[]{x, y, z};
                    // numutil.MathTools.printdata(direction);
                    //System.out.println(distance(direction));
                    position = add(position, direction);
                }
            }
            //numutil.MathTools.printdata(position);
            //System.out.println(distance(position));
            avg_displacement += distance(position);
            sq_displacement += Math.pow(distance(position), 2);
        }
        avg_displacement /= num_runs;
        sq_displacement /= num_runs;
        System.out.println(sq_displacement);
        // System.out.println(avg_displacement);
        return avg_displacement;
    }

    static double randomwalk3(int num_steps, int num_runs) {
        double avg_displacement = 0;
        double sq_displacement = 0;
        Random rand = new Random(1137);

        double[] position = null;
        double[] direction;
        double costheta; // z for random walk
        double phi; // polar angle for random walk
        double theta;
        double x, y, z;

        for (int i = 0; i < num_runs; i++) {
            position = new double[]{0, 0, 0}; // start at origin
            for (int j = 0; j < num_steps; j++) {
                phi = rand.nextDouble() * 2 * Math.PI; // 0 to 2*pi random number

                //  System.out.println(phi);
                costheta = -1 + rand.nextDouble() * 2; //-1 to 1 random number
                //System.out.println(costheta);
                theta = Math.acos(costheta);

                //System.out.println(theta);
                x = Math.cos(phi) * Math.sin(theta);
                y = Math.sin(phi) * Math.sin(theta);
                z = costheta;
                direction = new double[]{x, y, z};
                // numutil.MathTools.printdata(direction);
                //System.out.println(distance(direction));
                double[] oldposition = position;
                position = add(position, direction);
                if (position[0] > 0 && position[1] > 0 && position[1] > position[0]) {
                    position = oldposition;
                }
            }

            //numutil.MathTools.printdata(position);
            //System.out.println(distance(position));
            avg_displacement += distance(position);
            sq_displacement += Math.pow(distance(position), 2);
        }
        avg_displacement /= num_runs;
        sq_displacement /= num_runs;
        // System.out.println(sq_displacement);
        // System.out.println(avg_displacement);
        return avg_displacement;
    }

    static double[] histogram(int bin_size, int num_bins, int num_steps, int num_runs) {
        double avg_displacement = 0;
        double displacement = 0;
        Random rand = new Random(1137);
        double[] hist = new double[num_bins];

        double[] position = null;
        double[] direction;
        double costheta; // z for random walk
        double phi; // polar angle for random walk
        double theta;
        double x, y, z;
        for (int i = 0; i < num_runs; i++) {
            position = new double[]{0, 0, 0}; // start at origin
            for (int j = 0; j < num_steps; j++) {
                phi = rand.nextDouble() * 2 * Math.PI; // 0 to 2*pi random number
                costheta = -1 + rand.nextDouble() * 2; //-1 to 1 random number
                theta = Math.acos(costheta);
                //System.out.println(theta);
                x = Math.cos(phi) * Math.sin(theta);
                y = Math.sin(phi) * Math.sin(theta);
                z = costheta;
                direction = new double[]{x, y, z};
                position = add(position, direction);

            }
            //numutil.MathTools.printdata(position);
            //System.out.println(distance(position));

            displacement = distance(position);
            avg_displacement += displacement;
            for (int k = 0; k < hist.length; k++) {
                if (displacement < (k + 1) * bin_size) {
                    hist[k]++;
                    break;
                }
            }
        }
        avg_displacement /= (double) num_runs;

//        for (int k = 0; k < hist.length; k++) {
//            hist[k] /= (double)num_runs;
//        }
        System.out.println(avg_displacement);
        //numutil.MathTools.printdata(hist);
        return hist;
    }

    static void ex428() {
        double velocity = 500;
        double meanfreepath = 1;
        int num_steps = (int) (velocity / meanfreepath);
        int num_molecules = 100;
        double avg_distance = randomwalk(num_steps, num_molecules);
        System.out.println(avg_distance);
    }

    static void ex429() {
        int num_steps = 100;
        int bin_size = 1;
        int num_bins = 100;
        int num_walks = 1000;
        double[] hist = histogram(bin_size, num_bins, num_steps, num_walks);
        numutil.MathTools.printdata(hist);
        hist = histogram(bin_size, num_bins, 200, num_walks);
        numutil.MathTools.printdata(hist);
        hist = histogram(bin_size, num_bins, 300, num_walks);
        numutil.MathTools.printdata(hist);
        hist = histogram(bin_size, num_bins, 400, num_walks);
        numutil.MathTools.printdata(hist);
        hist = histogram(bin_size, num_bins, 500, num_walks);
        numutil.MathTools.printdata(hist);

    }

    static void ex430() {
        for (int i = 0000; i < 3000; i += 100) {
            double displacement = randomwalk(i, 1500);
            System.out.println(displacement);
        }
    }

    static void ebb7a() {
        for (int i = 0000; i < 3000; i += 100) {
            double displacement = randomwalk2(i, 1500);
            //System.out.println(displacement);
        }
    }

    static void ebb7b() {
        for (int i = 0000; i < 3000; i += 100) {
            double displacement = randomwalk3(i, 1500);
            System.out.println(displacement);
        }
    }

    static void ebb6c() {
        for (int i = 0000; i < 3000; i += 100) {
            double displacement = randomwalk(i, 1500);
            //System.out.println(displacement);
        }
    }

    public static void main(String[] args) {
        //ebb6a();
        //ebb6b();
        ebb6c();
        //ex428();
        // ex429();
        //ex430();
        //ebb7a();
        //ebb7b();

//        double step = 10;
//        for (double walk = 0; walk < 1000; walk += step) {
//            double avg_displace = randomwalk((int) walk, 1000);
//            System.out.println(avg_displace);
//        }
//        for (int i = 1; i < 6; i++) {
//             histogram(i,i*100, 10000);
//        }
    }
}
