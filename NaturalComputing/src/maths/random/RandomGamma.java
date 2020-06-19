/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maths.random;

import java.util.Calendar;
import java.util.Random;

/**
 *
 * @author Oscar
 */
public class RandomGamma {

    public static void main(String [] args)
    {
        for(int i=0;i<1000;i++)
        {
            System.out.println(RandomGamma.sampleGamma(5, 2));
        }
    }

//    /**
//     * Get random values from a gamma distribution with shape equal to a and b=1
//     * @param a
//     */
//    public RandomGamma(double a)
//    {
//        this.a = a;
//    }
//
//    private double a;
//
//
//    public double sample()
//    {
//        double Y = Math.tan(Math.PI*Math.random());
//        double X = Math.pow(2*a-1,0.5)*Y + a-1;
//        if(X<=0)
//        {
//            return sample();
//        }
//
//        double V = Math.random();
//
//        double v = (1+ Math.pow(Y,2))*Math.exp((a-1)*Math.log(X/(a-1))-Math.pow(2*a-1,0.5)*Y);
//
//        if(V>v)
//        {
//            return sample();
//        }
//        return X;
//    }
//
//}
//public class Samplers {
    private static Random rng = new Random(
            Calendar.getInstance().getTimeInMillis() +
            Thread.currentThread().getId());

    /**
     * k is the shape, theta is the scale
     * @param k
     * @param theta
     * @return
     */
    public static double sampleGamma(double k, double theta) {
        boolean accept = false;
        if (k < 1) {
            // Weibull algorithm
            double c = (1 / k);
            double d = ((1 - k) * Math.pow(k, (k / (1 - k))));
            double u, v, z, e, x;
            do {
                u = rng.nextDouble();
                v = rng.nextDouble();
                z = -Math.log(u);
                e = -Math.log(v);
                x = Math.pow(z, c);
                if ((z + e) >= (d + x)) {
                    accept = true;
                }
            } while (!accept);
            return (x * theta);
        } else {
            // Cheng's algorithm
            double b = (k - Math.log(4));
            double c = (k + Math.sqrt(2 * k - 1));
            double lam = Math.sqrt(2 * k - 1);
            double cheng = (1 + Math.log(4.5));
            double u, v, x, y, z, r;
            do {
                u = rng.nextDouble();
                v = rng.nextDouble();
                y = ((1 / lam) * Math.log(v / (1 - v)));
                x = (k * Math.exp(y));
                z = (u * v * v);
                r = (b + (c * y) - x);
                if ((r >= ((4.5 * z) - cheng)) ||
                        (r >= Math.log(z))) {
                    accept = true;
                }
            } while (!accept);
            return (x * theta);
        }
    }
}
