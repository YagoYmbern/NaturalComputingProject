/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToAscertainOffspring.brood_competition;

import java.util.Collections;
import java.util.List;
import nc.solution.Solution;
import nc.strategyToAscertainParentsToMate.Brood;

/**
 *
 * @author olao
 */
public class FittestBroodSelection extends BroodCompetition {
    
    public FittestBroodSelection(double percentage_of_accepted_offspring)
    {
        this.percentage_of_accepted_offspring = percentage_of_accepted_offspring;
    }
    
    private double percentage_of_accepted_offspring;

    /**
     * Set the percentage of accepted offspring
     * @param percentage_of_accepted_offspring 
     */
    public void setPercentage_of_accepted_offspring(double percentage_of_accepted_offspring) {
        this.percentage_of_accepted_offspring = percentage_of_accepted_offspring;
    }

    /**
     * Get the percentage of accepted offspring
     * @return 
     */
    public double getPercentage_of_accepted_offspring() {
        return percentage_of_accepted_offspring;
    }        
    
    /**
     * Get the best o.size()*percentage_of_accepted_offspring for the next generation. 
     * @param o
     * @return 
     */
    @Override
    public List<Solution> getSurvivorBrood(Brood o)
    {
        int n_accepted = (int)(o.size()*percentage_of_accepted_offspring);
        if(n_accepted==0)
        {
            n_accepted = 1;
        }
        Collections.sort(o);        
        return o.subList(0, n_accepted);
    }
}
