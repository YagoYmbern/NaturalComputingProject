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
public class ProbabilityParentUniform extends StrategyProbabilityParent{

    @Override
    public double getWeight(Solution s) {
        return 1;
    }            
}
