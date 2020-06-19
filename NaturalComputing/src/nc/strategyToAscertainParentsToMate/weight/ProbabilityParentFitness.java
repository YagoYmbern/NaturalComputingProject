/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToAscertainParentsToMate.weight;

import nc.Optimizer;
import nc.solution.Solution;

/**
 * The probability of being the parent of a solution is inversely proportional to its fitness.
 * @author olao
 */
public class ProbabilityParentFitness extends StrategyProbabilityParent
{
    @Override
    public double getWeight(Solution s) {
        Optimizer optimizer = s.getO();
        return 1-(optimizer.get(optimizer.size()-1).getFitness()-s.getFitness())/(optimizer.get(optimizer.size()-1).getFitness() - optimizer.get(0).getFitness());        
    }    
}