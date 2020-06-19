/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToUpdateSolution;

/**
 * Class to model the chaotic behavior of some functions
 * @author olao
 */
public abstract class ChaoticMap {
    
    /**
     * Get the value at t+1 using x
     * @param x_t
     * @return 
     */
    public abstract double getValue_t1(double x_t);               
}
