/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vdouble.updater.self_adaptation;

import java.util.concurrent.ThreadLocalRandom;
import nc.variable.strategyToUpdateSolution.common.StrategyToUpdateV;
import nc.variable.V;
import nc.variable.vdouble.RealNumber;

/**
 *
 * @author olao
 */
public class NStepSizeSelfAdaptation extends StrategyToUpdateV<RealNumber>{
    
    /**
     * Create a new variable uploading all the values.
     * @param sd_movement magnitude of the jump for Brownian movement
     * @param lrateV The learning rate specific of this variable
     * @param learning the learning rate over all the variables
     */
    public NStepSizeSelfAdaptation(double sd_movement, double lrateV, double learning)
    {
        this.sd_movement = sd_movement;
        this.lrateV = lrateV;
        this.learning = learning;
    }    
    // jump magnitude (standard deviation of the normal distribution) and learning rate of the variable
    private double sd_movement;
    private final double lrateV, learning;

    /**
     * Get the sd of the movement
     * @return 
     */
    public double getSd_movement() {
        return sd_movement;
    }

    /**
     * Get the learning rate
     * @return 
     */
    public double getLearning_rate_of_Variable() {
        return lrateV;
    }
    
    /**
     * Get the overall learning rate
     * @return 
     */
    public double getLearning()
    {
        return learning;
    }
    
    /**
     * Update the values of the solution a following a random walk updated by a learning process.
     * sd(t) is updated using sd(t) = sd(t-1)*exp(gaussian1*lrateV + learning * gaussian2)
     *
     * @param v
     */
    @Override
    public void update(V<RealNumber> v) {
// propose a jump in the standard deviation of the gaussian distribution
        double jump_r = ThreadLocalRandom.current().nextGaussian();
        double jump_rprima = ThreadLocalRandom.current().nextGaussian();
// new standard deviation is updated
        double learn = getLearning_rate_of_Variable();
        double n_sd = sd_movement * Math.exp(jump_rprima * learn + getLearning() * jump_r);
        sd_movement = n_sd;
// the solution is updated   
        double nwv = v.getI().getD() + ThreadLocalRandom.current().nextGaussian() * sd_movement;
        v.getI().setD(nwv);
    } 

    @Override
    public NStepSizeSelfAdaptation copy() {
        return new NStepSizeSelfAdaptation(getSd_movement(),getLearning_rate_of_Variable(),getLearning());
    }
    
    
    @Override
    public String toString()
    {
        return getSd_movement() + "\t" + getLearning_rate_of_Variable() + "\t" + getLearning();
    }
}
