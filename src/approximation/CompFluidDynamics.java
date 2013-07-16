/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package approximation;

import function.FunctionModel;

/**
 *
 * @author jbao
 */
public class CompFluidDynamics {

    // u_t + c*u_x = 0
    public static double[][] LinearConvection1D(FunctionModel fx) {
        // centered difference in space, forward difference in time
        // u_i(n+1) - u_i(n) / dt + c/2/dx ( u_i+1(n) - u_i-1(n)) = 0
        int numSteps = 100;
        double[][] u = new double[numSteps][numSteps];

        /*
         * desired differential equation:
         * du/dt + c * du/dx = 0
         * 
         * approximations:
         * du/dt ~= u_i(n+1) - u_i(n) / dt
         * du/dx ~= ( u_i+1(n) - u_i-1(n) ) / 2 / dx
         * 
         * approximate differential equation:
         * u_i(n+1) - u_i(n) / dt + c/2/dx ( u_i+1(n) - u_i-1(n)) = 0
         * 
         * exact numerical terms:
         * u_i(n+1) = u_i(n) + dt * du/dt_i(n) + dt^2/2*du^2/dt^2 + O(dt^3)
         * u_i+1(n) = u_i(n) + dx * du/dx_i(n) + dx^2/2*du^2/dx^2 + dx^3/6*du^3/dx^3 + O(dx^4)
         * u_i-1(n) = u_i(n) - dx * du/dx_i(n) - dx^2/2*du^2/dx^2 - dx^3/6*du^3/dx^3 - O(dx^4)
         * 
         * exact numerical differential equation we are actually solving:
         * du/dt_i(n) + dt/2*du^2/dt^2 + c / 2 / dx * du/dx_i(n) + dx^2/2*du^2/dx^2 + dx^3/6*du^3/dx^3 + O(dt^3) + O(dx^4) = 0
         * -->
         * (du/dt + c * du/dx)_i(n) = - dt / 2 * d^2u/dt^2_i(n) - c * dx^2/6*d^3u/dx^3_i(n) + O(dt^2) + O(dx^3)
         * 
         * solve the eq in terms of du/dt, find d^2u/dt^2, then substitute back in to eliminate the dt dependence
         * du/dt = -c * du/dx + O(dt) + O(dx^2)
         * d^2u/dt^2 = -c * d^2u/dxdt - dt / 2 * d^3u/dt^3 - c * dx^2/6*d^4u/dx^3dt + O(dt^3) + O(dx^4)
         * substituting d^2u/dxdt = d^2u/dtdx by analytic property of polynomials and using d/dx(du/dt) = d/dx ( -c * du/dx + O(dt,dx^2)) = -c * d^2u/dx^2 + O(dt,dx^2)
         * d^2u/dt^2 = c^2 * d^2u/dx^2 - dt / 2 * d^3u/dt^3 - c * dx^2/6*d^4u/dx^3dt + O(dt^3) + O(dx^4)
         *           = c^2 * d^2u/dx^2 + O(dt) + O(dx^2)
         * (du/dt + c * du/dx)_i(n) = - dt / 2 * c^2 * d^2u/dx^2 - c * dx^2/6*d^3u/dx^3 + O(dt^2) + O(dx^3)
         *                          = - dt / 2 * c^2 * d^2u/dx^2 + O(dx^2) + O(dt^2)
         */
        double dt = 10e-4;
        for (int i = 0; i < numSteps; i++) {
            for (int n = 0; n < numSteps; n++) {
                double term1 = (u[i][n + 1] - u[i][n]) / dt;
                
               
            }

        }
        function.FunctionModel ex = new function.FunctionModel() {
            @Override
            public double compute(double input, double[] equationParams) {
                double result = Double.NaN;
                double a1 = equationParams[0];
                double a2 = equationParams[1];
                double a3 = equationParams[2];
                double fx1 = equationParams[3];
                double c1 = equationParams[4];

                if (input <= a1 || input >= a3) {
                    result = fx1;
                } else if (input < a2) {
                    result = c1 * (input - a1);
                } else {
                    result = c1 * (a3 - input);
                }
                return result;
            }

            @Override
            public double dcompute(int derivative, double input, double[] equationParams) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };


        return new double[][]{{}};
    }
}
