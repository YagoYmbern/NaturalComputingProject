/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution.chromosome.recombine;

import java.util.ArrayList;
import nc.solution.ChromosomeWeighted;
import nc.solution.Chromosome;

/**
 *
 * @author Oscar Lao
 */
public abstract class RecombinationChromosomes {
    
    public RecombinationChromosomes()
    {
        
    }
    
    /**
     * Generate a recombination
     * @param parents
     * @return 
     */
    public abstract Chromosome recombine(ArrayList<ChromosomeWeighted> parents);
    
}
