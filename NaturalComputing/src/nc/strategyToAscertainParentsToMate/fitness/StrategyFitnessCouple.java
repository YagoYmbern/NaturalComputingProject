/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToAscertainParentsToMate.fitness;

import nc.strategyToAscertainParentsToMate.Couple;

/**
 * Defines the way to combine the fitness of all the parents that represent a couple
 * @author olao
 */
public abstract class StrategyFitnessCouple {
    
    /**
     * compute the fitness of the couple c
     * @param c
     * @return 
     */
    public abstract double fitness(Couple c);
    
}
