/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package maths.random;

/**
 *
 * @author Oscar
 */
public class RandomDirichlet {
    public RandomDirichlet(int [] alphas)
    {
        this.alphas = alphas;
    }

    private int [] alphas;

    public double [] sample() {
        double [] xs = new double[alphas.length];
        double sum = 0;
        for(int i=0;i<alphas.length;i++)
        {
            xs[i] = RandomGamma.sampleGamma(alphas[i], 1);
            sum+=xs[i];
        }

        for(int i=0;i<alphas.length;i++)
        {
            xs[i] = xs[i]/sum;
        }
        return xs;
    }

    public static void main(String [] args)
    {
        int [] alphas = {2,4,7};
        RandomDirichlet random = new RandomDirichlet(alphas);
        for(int r=0;r<10000;r++)
        {
            System.out.println(java.util.Arrays.toString(random.sample()));
        }
    }
}
