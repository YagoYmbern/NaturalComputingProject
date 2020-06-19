/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToExchangeSolutionsAmongOptimizers;

import nc.MetaOptimizer;

/**
 *
 * @author olao
 */
public abstract class StrategyToExchangeSolutionsAmongOptimizers {
    
    /**
     * Use a strategy to exchange the solutions
     * @param mo 
     */
    public abstract void exchangeSolutions(MetaOptimizer mo);
    
}
