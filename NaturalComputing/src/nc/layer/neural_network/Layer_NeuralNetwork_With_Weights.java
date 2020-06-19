/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.layer.neural_network;

import nc.solution.Chromosome;
import nc.solution.chromosome.generator.ChromosomeGeneratorAbstract;
import nc.solution.chromosome.recombine.RecombinationChromosomes;
import nc.solution.chromosome.update.StrategyToUpdateChromosome;

/**
 *
 * @author Oscar Lao
 * @param <C>
 * @param <Y>
 */
public abstract class Layer_NeuralNetwork_With_Weights<C extends Chromosome, Y extends ChromosomeGeneratorAbstract> extends Layer_NeuralNetwork<C,Y>{        
    
    protected int size;    
    
    /**
     * Create a layer with weights that must be estimated.
     * 
     * @param init
     * @param stu
     * @param recombine
     */
    public Layer_NeuralNetwork_With_Weights(Y init, StrategyToUpdateChromosome stu, RecombinationChromosomes recombine)
    {
        super(init, stu, recombine);
    }    
    
    @Override
    public int size()
    {
        return size;
    }
}
