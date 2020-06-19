/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToAscertainParentsToMate.fitness;

import nc.strategyToAscertainParentsToMate.Couple;


/**
 * Define the fitness of the couple as the average fitness among the elements of the couple.
 * @author olao
 */
public class FitnessCoupleAverage extends StrategyFitnessCouple{    
    
    
    @Override
    /***
     * Compute the average fitness among the elements of the couple
     */
    public double fitness(Couple c)
    {
        double f = 0;
        f = c.stream().map((s) -> s.getSolution().getFitness()).reduce(f, (accumulator, _item) -> accumulator + _item);
        return f / c.size();
    }
    
    
}
