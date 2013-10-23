/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs780;

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
            sizes[i] = 1000 + Math.pow(size_step,i);
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

    public static void main(String[] args) {
        // ebb6a();
        ebb6c();
    }
}
