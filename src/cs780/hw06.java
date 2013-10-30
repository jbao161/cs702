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

    public static void ebb6a() {
        //2d random walk
        Random rand = new Random();
        int direction;
        double x, y, z, d1;
        x = 0;
        y = 0;
        z = 0;
        d1 = 0;
        int num_steps = (int) 1000000;
        int num_walks = 100;
        // displacement is difference between final and initial positions in the walk
        double displacement_avg = 0;
        double[] sizes = {1e3, 1e4, 1e5, 1e6};
        for (int j = 0; j < sizes.length; j++) {
            displacement_avg = 0;
            for (int k = 0; k < num_walks; k++) {
                x = 0;
                y = 0;
                z = 0;
                d1 = 0;
                for (int i = 0; i < sizes[j]; i++) { // steps in a walk
                    direction = rand.nextInt(8);
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
            System.out.println(x + "," + y + "," + z + "," + d1 + ",");
            System.out.println(displacement_avg + ",");
        }
    }

    public static void ebb6b() {
        Random rand = new Random();
        int direction;
        double x, y, z, d1;
        int num_steps = (int) 1e6;
        int num_walks = 100;
        // displacement is difference between final and initial positions in the walk
        double displacement_avg = 0;
        for (int k = 0; k < num_walks; k++) {
            x = 0;
            y = 0;
            z = 0;
            d1 = 0;
            for (int i = 0; i < num_steps; i++) { // steps in a walk
                direction = rand.nextInt(8);
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

        System.out.println(displacement_avg);
    }

    public static void ebb6c() {
        //2d random walk
        Random rand = new Random();
        int direction;
        double x, y, z, d1;
        x = 0;
        y = 0;
        z = 0;
        d1 = 0;
        int num_steps = (int) 1000000;
        int num_walks = 100;
        // displacement is difference between final and initial positions in the walk
        double displacement_avg = 0;
        double d2_avg = 0;
        int num_sizes = 15;
        double size_step = 10;
        double[] sizes = new double[num_sizes];
        for (int i = 0; i < num_sizes; i++) {
            sizes[i] = 1000 + Math.pow(size_step, i);
        }
        for (int j = 0; j < sizes.length; j++) {
            displacement_avg = 0;
            for (int k = 0; k < num_walks; k++) {
                x = 0;
                y = 0;
                z = 0;
                d1 = 0;
                for (int i = 0; i < sizes[j]; i++) { // steps in a walk
                    int blocked = rand.nextInt(6);
                    direction = rand.nextInt(6);
                    if (direction == blocked) {
                        //do nothing
                    } else {
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
                }
                double d2 = (x * x + y * y + z * z + d1 * d1);
                double displacement = Math.sqrt(d2);
                d2_avg += d2 / num_walks;
                displacement_avg += displacement / num_walks;
            }
            //System.out.println(x + "," + y + "," + z + "," + d1 + ",");
            System.out.println(displacement_avg + ",");
            //System.out.println(d2_avg + ",");
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

        double[] position = null;
        double[] direction;
        double costheta; // z for random walk
        double phi; // polar angle for random walk
        double theta;
        double x, y, z;
        for (int i = 0; i < 1000; i++) {
            rand.nextDouble(); // throw out first few numbers in the sequence
        }
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
        }
        avg_displacement /= num_runs;
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

    static void histogram(int bin_size, int num_steps, int num_runs) {
        double avg_displacement = 0;
        double displacement = 0;
        Random rand = new Random();
        int num_bins = 100;
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
            for (int k = 0; k < hist.length; k++) {
                if (displacement < (k + 1) * bin_size) {
                    hist[k]++;
                    break;
                }
            }
        }
        avg_displacement /= num_runs;

        for (int k = 0; k < hist.length; k++) {
            hist[k] /= num_runs;
        }
        //numutil.MathTools.printdata(hist);
    }

    public static void main(String[] args) {
        // ebb6a();
        // ebb6c();
        double step = 10;
        for (double walk = 0; walk < 1000; walk += step) {
            double avg_displace = randomwalk((int) walk,1000);
            System.out.println(avg_displace);
        }
//        for (int i = 1; i < 6; i++) {
//             histogram(i,i*100, 10000);
//        }
    }
}
