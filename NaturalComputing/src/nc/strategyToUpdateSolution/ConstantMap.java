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
public class ConstantMap extends ChaoticMap{

    /**
     * Return 1. That is, this is the classical implementation of Spectral Spread.
     * @param x_t
     * @return 
     */
    @Override
    public double getValue_t1(double x_t) {
        return 1;
    }            
}
