/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution.chromosome.generator;

import nc.solution.Chromosome;
import nc.variable.initialize.Generator_Variable;

/**
 *
 * @author Oscar Lao
 * @param <C>
 */
public abstract class ChromosomeGeneratorAbstract<C extends Chromosome> {
    
    protected final Generator_Variable init;    
    
    public ChromosomeGeneratorAbstract(Generator_Variable init)
    {
        this.init = init;
    }  
//   
//    /**
//     * Generate the chromosome
//     * @param size the number of variables of this chromosome
//     * @return 
//     */
//    public abstract C generateChromosome(int size);

    /**
     * Get the generator of a new variable
     * @return 
     */
    public Generator_Variable getInit() {
        return init;
    }
}
