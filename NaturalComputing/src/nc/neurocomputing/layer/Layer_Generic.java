/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.neurocomputing.layer;

import nc.solution.Chromosome;
import nc.solution.Container_NC;
import nc.solution.Solution;
import nc.solution.chromosome.generator.ChromosomeGenerator;
import nc.solution.chromosome.generator.ChromosomeGeneratorAbstract;
import nc.solution.chromosome.recombine.RecombinationChromosomes;
import nc.solution.chromosome.update.StrategyToUpdateChromosome;

/**
 *
 * @author Oscar Lao
 */

public class Layer_Generic extends Layer<ChromosomeGenerator>{
    
    private final int number_of_variables;
    
    /**
     * Create an object Layer_Generic using the chr_nc_strategy to generate number_of_variables
     * @param init
     * @param stu
     * @param recombine
     * @param number_of_variables 
     */
    public Layer_Generic(ChromosomeGenerator init, StrategyToUpdateChromosome stu, RecombinationChromosomes recombine, int number_of_variables)
    {
        super(init,stu,recombine);
        this.number_of_variables = number_of_variables;
    }

    @Override
    public void initializeVariablesOfThisLayer(Solution s) {
        // New chromosome as defined by the initialize chromosome strategy
        Chromosome ch = getInit().generateChromosome(number_of_variables);
        s.add(ch);
    }

    @Override
    public int size() {
        return number_of_variables;
    }    
}
