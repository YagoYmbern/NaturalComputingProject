/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution.chromosome.update;

import maths.random.RandomUniformWithoutResampling;
import nc.solution.Chromosome;
import nc.variable.V;


/**
 *
 * @author Oscar Lao
 */
public class StrategyToUpdateChromosomeGeneric extends StrategyToUpdateChromosome{
   
    /**
     * Update 1 variable of the chromosome following an update strategy
     * @param c 
     */
    @Override
    public void update(Chromosome c)
    {
        // Pick the positions in c of the m_n elements that will be updated
        RandomUniformWithoutResampling ruw = new RandomUniformWithoutResampling(0, c.size());
        int[] ch_filters = ruw.sample(1);
        ((V)c.get(ch_filters[0])).apply_update();
    }    
}
