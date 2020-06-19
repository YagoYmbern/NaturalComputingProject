/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vdouble.updater.social.PSO;

/**
 *
 * @author olao
 */
public class HyperParameters_PSO {
    
    /**
     * Generate a new movement of Swarm type.Low values for c1 and c2 encourage
     * particles to explore far away from already uncovered good points as there
     * is less emphasis on past learning.High values of these parameters
     * encourage more intensive search of regions close to these.Exploration is
     * encouraged by selecting a high value of W = wmax -
     * itercurr*(wmax-wmin)/itermax relative to the values of c1 and c2
     * points.(Extracted from Natural Computing)
     *
     * @param c1 is the weight coefficient that controls the velocity of best
     * solution associated to this object
     * @param c2 is the weight coefficient that controls the velocity of best
     * solution associated to the whole population
     * @param min_momentum_weight the minimum momentum weight to multiply. For
     * example 0.4 current speed
     * @param max_momentum_weight the maximum momentum weight to multiply. For
     * example 0.9 current speed
     * @param maximum_number_iterations maximum number of iterations that our
     * algorithm is running
     */
    public HyperParameters_PSO(double c1, double c2, double min_momentum_weight, double max_momentum_weight, int maximum_number_iterations) {
        this.c1 = c1;
        this.c2 = c2;
        this.maximum_number_iterations = maximum_number_iterations;
        this.min_momentum_weight = max_momentum_weight;
        this.max_momentum_weight = max_momentum_weight;
    }

    protected double c1, c2;
    protected final double min_momentum_weight, max_momentum_weight;
    protected final int maximum_number_iterations;

    /**
     * Set the weight of how close we must go away from the best solution that
     * this solution has found so far
     *
     * @param c1
     */
    public void setC1(double c1) {
        this.c1 = c1;
    }

    /**
     * Set the weight of how close we must go to the best solution found in the
     * population
     *
     * @param c2
     */
    public void setC2(double c2) {
        this.c2 = c2;
    }

    /**
     * Get C1
     * @return 
     */
    public double getC1() {
        return c1;
    }

    /**
     * Get C2
     * @return 
     */
    public double getC2() {
        return c2;
    }

    /**
     * get maximum momentum weight
     * @return 
     */
    public double getMax_momentum_weight() {
        return max_momentum_weight;
    }

    
    /**
     * Get the maximum number of iterations that the algorithm is going to run
     * @return 
     */
    public int getMaximum_number_iterations() {
        return maximum_number_iterations;
    }

    /**
     * Get the minimum momentum weight
     * @return 
     */
    public double getMin_momentum_weight() {
        return min_momentum_weight;
    }
}
