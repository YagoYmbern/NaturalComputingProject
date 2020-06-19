/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maths.random;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Oscar Lao
 */
public class RandomNormal extends RandomFunction{
    
    /**
     * A random normal distribution with mean and sd
     * @param mean
     * @param sd 
     */
    public RandomNormal(double mean, double sd)
    {
        this.mean = mean;
        this.sd = sd;
    }
    
    private final double mean,sd;

    @Override
    public double next() {
        return sd*ThreadLocalRandom.current().nextGaussian()+ mean;
    }
}
