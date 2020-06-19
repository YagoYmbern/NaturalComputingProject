/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution.chromosome.generator;

import nc.solution.Chromosome;
import nc.variable.initialize.Generator_Variable;


/**
 * A Strategy to initialize the chromosome of variables
 * @author Oscar Lao
 */
public class ChromosomeGenerator extends ChromosomeGeneratorAbstract<Chromosome>{
       
    
    public ChromosomeGenerator(Generator_Variable init)
    {
        super(init);
    }  
   
    /**
     * Generate the chromosome
     * @param size the number of variables of this chromosome
     * @return 
     */
//    @Override
    public Chromosome generateChromosome(int size)
    {
        Chromosome variable = new Chromosome();
        for (int e = 0; e < size; e++) {
            // Create a new variable and add it to the arraylist
            variable.add((init).generateVariable());
        }
        return variable;
    }        
}
