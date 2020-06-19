/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.layer.neural_network;

import nc.solution.Chromosome;
import nc.solution.Solution;
import nc.solution.chromosome.generator.ChromosomeGenerator;

/**
 *
 * @author Oscar Lao
 */
public abstract class Layer_NeuralNetwork_Without_Weights extends Layer_NeuralNetwork<Chromosome, ChromosomeGenerator> {

    /**
     * A layer where the neuron that is mostly activated gets everything
     */
    public Layer_NeuralNetwork_Without_Weights() {
        super(null,null,null);
    }
    
    @Override
    public int size()
    {
        return 0;
    }
    
    @Override
    public void initializeVariablesOfThisLayer(Solution s)
    {
        Chromosome c = new Chromosome();        
        // no variables are added
        s.add(c);
    }    
}
