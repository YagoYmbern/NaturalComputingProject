/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution;

import nc.solution.chromosome.generator.ChromosomeGeneratorAbstract;
import nc.solution.chromosome.recombine.RecombinationChromosomes;
import nc.solution.chromosome.update.StrategyToUpdateChromosome;

/**
 * A container of the strategies that are going to be used to initialize the chromosome, to update the elements of the chromosome and to recombine the chromosome
 * @author Oscar Lao
 */
public class Container_NC {
    
    private final ChromosomeGeneratorAbstract init;
    private final StrategyToUpdateChromosome stu;
    private final RecombinationChromosomes recombine;
    
    /**
     * Generate a container with the required parameters to run the NC
     * @param init the initializer of the variables that are going to be in the chromosome
     * @param stu the strategy (number of breakpoints and how these are distributed, if the elements can be repeated in the chromosome, etc)
     * @param recombine the strategy to mix the elements of the chromosome to generate a new chromosome
     */
    public Container_NC(ChromosomeGeneratorAbstract init, StrategyToUpdateChromosome stu, RecombinationChromosomes recombine)
    {
        this.init = init;
        this.stu = stu;
        this.recombine = recombine;
    }

    /**
     * Get the strategy to initialize the chromosome
     * @return 
     */
    public ChromosomeGeneratorAbstract getInit() {
        return init;
    }

    /**
     * Get the strategy to recombine the chromosome
     * @return 
     */
    public RecombinationChromosomes getRecombine() {
        return recombine;
    }

    /**
     * Get the strategy to update the chromosome
     * @return 
     */
    public StrategyToUpdateChromosome getStu() {
        return stu;
    }
}
