/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution.chromosome.update;

import nc.solution.Chromosome;
import nc.variable.V;

/**
 *
 * @author Oscar Lao
 * @param <C>
 */
public abstract class StrategyToUpdateChromosome {
    
    /**
     * Update 1 variable of the chromosome following an update strategy
     * @param c 
     */
    public abstract void update(Chromosome c);
    
    /**
     * Update the positions defined in n of c
     * @param n
     * @param c 
     */
    protected void n_update(int [] n, Chromosome c)
    {
        // Update the element i of the chromosome
        for(int i:n)
        {
            ((V)c.get(i)).apply_update();
        }
    }
}
