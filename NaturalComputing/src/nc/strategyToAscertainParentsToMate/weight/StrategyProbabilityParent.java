/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToAscertainParentsToMate.weight;

import nc.solution.Solution;

/**
 *
 * @author olao
 */
public abstract class StrategyProbabilityParent {
    
    /**
     * Get the weight to the solution
     * @param s
     * @return 
     */
    public abstract double getWeight(Solution s);

}
