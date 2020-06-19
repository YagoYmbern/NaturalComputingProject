/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToUpdateSolution;

/**
 *
 * @author olao
 */
public class GaussIteratedMap extends ChaoticMap{

    /**
     * Create a Gauss iterated map. xt+1 =  exp(-alphaXt^2) + beta
     * @param alpha
     * @param beta 
     */
    public GaussIteratedMap(double alpha, double beta)
    {
        this.alpha = alpha;
        this.beta = beta;
    }
    
    private double alpha, beta;

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }
    
        
    @Override
    /**
     * Get the value at x_t
     */
    public double getValue_t1(double x_t) {
        return Math.exp(-alpha*Math.pow(x_t,2)) + beta;
    }
}
