/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vdouble;

import java.util.concurrent.ThreadLocalRandom;
import nc.variable.strategyToUpdateSolution.common.StrategyToUpdateV;


/**
 *
 * @author Oscar Lao
 */
public class GeneratorVDoubleGaussian extends GeneratorVDouble{
    
    private final double mean, sd;
    
    /**
     * Initialize the variable using a random normal distribution with mean 0 and sd determined by the user
     * @param stu
     * @param sd 
     */
    public GeneratorVDoubleGaussian(StrategyToUpdateV<RealNumber> stu, double sd)
    {
        super(stu);
        this.mean = 0;
        this.sd = sd;
    }
    
    /**
     * Initialize the variable using a random normal distribution with mean and sd determined by the user
     * @param stu
     * @param mean
     * @param sd 
     */
    public GeneratorVDoubleGaussian(StrategyToUpdateV<RealNumber> stu, double mean, double sd)
    {
        super(stu);
        this.mean = mean;
        this.sd = sd;
    }
    

    /**
     * Get the mean
     * @return 
     */
    public double getMean() {
        return mean;
    }

    /**
     * Get the standard deviation
     * @return 
     */
    public double getSd() {
        return sd;
    }

    @Override
    public VDouble generateVariable() {
        return new VDouble(new RealNumber(sd*ThreadLocalRandom.current().nextGaussian() + mean), stu.copy());
    }
    
    
    
}
