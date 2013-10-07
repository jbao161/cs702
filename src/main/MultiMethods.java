///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package main;
//
//import function.MultiFunction;
//import solvermethods.MultiNewton;
//import solvermethods.SolverMethod;
//
///**
// *
// * @author jbao
// */
//public class MultiMethods {
//    static double[] null_params = null;
//    static MultiFunction sincos = new MultiFunction() {
//        public double compute(double[] vars, double[] params) {
//            double x = vars[0];
//            double y = vars[1];
//            return Math.sin(x) + Math.cos(y);
//        }
//    };
//
//    public static void test_multifunction() {
//
//        double[] vars = new double[]{1, 1};
//        
//        double printer = sincos.compute(vars, null_params);
//        System.out.println(printer);
//    }
//
//    public static void test_multinewton() {
//        SolverMethod newton = new MultiNewton();
//        double step_size = 0.01;
//        double[] init_vars = new double[]{Math.PI*2/3,Math.PI/2};
//        Object[] args = new Object[5];
//        args[0] = sincos;
//        args[1] = 0;
//        args[2] = step_size;
//        args[3] = init_vars;
//        args[4] = null_params;
//        double x = newton.solve(args);
//        System.out.println(x);
//    }
//
//    public static void main(String args[]) {
//        test_multifunction();
//        test_multinewton();
//
//    }
//}
