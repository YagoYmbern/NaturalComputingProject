/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.layer.neural_network;

import nc.input_output.InputOutput_Tensor;
import nc.solution.Chromosome;
import nc.neurocomputing.layer.Layer;
import nc.solution.chromosome.generator.ChromosomeGeneratorAbstract;
import nc.solution.chromosome.recombine.RecombinationChromosomes;
import nc.solution.chromosome.update.StrategyToUpdateChromosome;

/**
 * The basic type of layer for running an artificial neural network
 * @author olao
 * @param <C>
 * @param <Y>
 */
public abstract class Layer_NeuralNetwork<C extends Chromosome, Y extends ChromosomeGeneratorAbstract> extends Layer<Y>{

    public Layer_NeuralNetwork(Y init, StrategyToUpdateChromosome stu, RecombinationChromosomes recombine)
    {
        super(init,stu,recombine);
    }
        
    /**
     * Compile this neuronal layer so it links to the previous neuronal layer, if needed
     * @param input
     * @return 
     */
    public abstract InputOutput_Tensor compile(InputOutput_Tensor input);
    
    /**
     * Return the output from an input after going through this layer
     * @param s
     * @param input
     * @return 
     */
    public abstract InputOutput_Tensor getOutput(C s, InputOutput_Tensor input);    
    
}
