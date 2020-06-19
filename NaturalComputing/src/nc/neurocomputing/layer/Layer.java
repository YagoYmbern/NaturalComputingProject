/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.neurocomputing.layer;

import nc.solution.Solution;
import nc.solution.chromosome.generator.ChromosomeGeneratorAbstract;
import nc.solution.chromosome.recombine.RecombinationChromosomes;
import nc.solution.chromosome.update.StrategyToUpdateChromosome;

/**
 *
 * @author Oscar Lao
 * @param <C>
 */
public abstract class Layer<C extends ChromosomeGeneratorAbstract> {       
    
    private final ChromosomeGeneratorAbstract init;
    private final StrategyToUpdateChromosome stu;
    private final RecombinationChromosomes recombine;
    
    /**
     * Create a layer with all the elements to estimate the elements associated to this layer
     * @param chr_nc_strategy 
     */
    
        /**
     * Generate a container with the required parameters to run the NC
     * @param init the initializer of the variables that are going to be in the chromosome
     * @param stu the strategy (number of breakpoints and how these are distributed, if the elements can be repeated in the chromosome, etc)
     * @param recombine the strategy to mix the elements of the chromosome to generate a new chromosome
     */

    public Layer(C init, StrategyToUpdateChromosome stu, RecombinationChromosomes recombine)
    {
        this.init = init;
        this.stu = stu;
        this.recombine = recombine;
    }
    
    
   /**
     * Initialize the variables of this layer using the initializeVariable and add them to the Solution s
     * @param s
     */
    public abstract void initializeVariablesOfThisLayer(Solution s);

    /**
     * Get the strategy to initialize the chromosome
     * @return 
     */
    public C getInit() {
        return (C)init;
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
    
    /**
     * The name of the layer
     * @return 
     */
    public String name()
    {
        return getClass().getSimpleName();        
    }       
    
    /**
     * The number of parameters (weights) of this layer
     * @return 
     */
    public abstract int size();
}
