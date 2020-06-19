/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToAscertainOffspring.offspring_to_parents_competition;

import nc.solution.Solution;
import nc.strategyToAscertainParentsToMate.Couple;

/**
 *
 * @author Oscar Lao
 */
public abstract class StrategyToDecideHowGoodIsAnOffspringComparedToItsParents {
    
    /**
     * Use a strategy to compare how good a given offspring solution is compared to the parents that created it.
     * Return a solution that can be the offspring solution or some other type of strategy.
     * @param parents
     * @param offspring
     * @return 
     */
    public abstract Solution compareToParents(Couple parents, Solution offspring); 
    
}
