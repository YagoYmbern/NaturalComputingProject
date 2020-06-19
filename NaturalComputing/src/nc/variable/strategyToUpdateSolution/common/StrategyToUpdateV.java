/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.strategyToUpdateSolution.common;

//import nc.solution.Solution;
import nc.variable.V;

/**
 *
 * @author olao
 * @param <T>
 */
public abstract class StrategyToUpdateV<T> {

//    protected Solution s;
//    
//    /**
//     * Set the solution to which the variable refers
//     * @param s 
//     */
//    public void setS(Solution s) {
//        this.s = s;
//    }    
    
    /**
     * Update the variable of type T following a strategy
     * @param b
     */
    public abstract void update(V<T> b);
    
    /**
     * Copy the strategy to update the variable
     * @return 
     */
    public abstract StrategyToUpdateV copy();
    
}
