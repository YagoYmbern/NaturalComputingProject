/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maths.random;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * RandomMultinomial uses classical shorting from smaller to largest and then
 * computing cummulative pdf.
 *
 * @author Administrator
 */
public class RandomMultinomial {
    
    private final ValueValue[] vals;
    private final double[] cdf;   
    
    public RandomMultinomial(double[] p) {
        vals = new ValueValue[p.length];
        for (int v = 0; v < vals.length; v++) {
            vals[v] = new ValueValue(p[v], v);
        }

        Arrays.sort(vals);

        cdf = new double[vals.length];
        cdf[0] = vals[0].getValueOne();
        for (int e = 1; e < vals.length; e++) {
            cdf[e] = cdf[e - 1] + vals[e].getValueOne();
        }
    }

    public RandomMultinomial(Double[] pe) {
        vals = new ValueValue[pe.length];
        for (int v = 0; v < vals.length; v++) {
            vals[v] = new ValueValue(pe[v], v);
        }

        Arrays.sort(vals);

        cdf = new double[vals.length];
        cdf[0] = vals[0].getValueOne();
        for (int e = 1; e < vals.length; e++) {
            cdf[e] = cdf[e - 1] + vals[e].getValueOne();
        }
    }


    
    /**
     * Ids refers to each of the k levels in p
     *
     * @param size
     * @return
     */
    public int[] sample(int size) {
        int[] ids = new int[size];

        for (int i = 0; i < ids.length; i++) {
            double rV = ThreadLocalRandom.current().nextDouble();
            int e = 0;
            try {
                while (rV > cdf[e]) {
                    e++;
                }
            } catch (ArrayIndexOutOfBoundsException s) {
                e--;
            }
            ids[i] = (int) vals[e].getValueTwo();
        }
        return ids;
    }
}
