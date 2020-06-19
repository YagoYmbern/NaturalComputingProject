/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.layer.neural_network;

import nc.exception.ActivationFNNException;
import nc.input_output.InputOutput_Tensor;
import nc.neurocomputing.activation.Activation_Function;
import nc.solution.ChromosomeFNN;
import nc.solution.Solution;
import nc.solution.chromosome.generator.ChromosomeFNNGenerator;
import nc.solution.chromosome.recombine.RecombinationChromosomeFNN;
import nc.solution.chromosome.recombine.RecombinationPairedVariables;
import nc.solution.chromosome.update.StrategyToUpdateChromosomeFNN;
import nc.variable.strategyToRecombine.RecombinationVRandom;
import nc.variable.vneuron.FreeNeuronNetwork;

/**
 *
 * @author Oscar Lao
 */
public class Layer_FreeNN extends Layer_NeuralNetwork_With_Weights<ChromosomeFNN, ChromosomeFNNGenerator> {

    // Neurons from the previous layer
    private int ch, r, col;
    

    /**
     * A layer Free Neuron Network with default values of the strategy of the nc
     * parameters
     *
     * @param time_series if true it means that the neurons_output are generated for each t of the input
     * @param neurons_output
     * @param output
     */
    public Layer_FreeNN(boolean time_series, int neurons_output, Activation_Function output) {
        super(new ChromosomeFNNGenerator(output, time_series, neurons_output),
                new StrategyToUpdateChromosomeFNN(),
                new RecombinationChromosomeFNN());
    }

    /**
     * Compute the output of the solution
     *
     * @param s
     * @param i
     * @return
     */
    @Override
    public InputOutput_Tensor getOutput(ChromosomeFNN s, InputOutput_Tensor i) {
        FreeNeuronNetwork fr = s.getFr();
        fr.reset_activations();
        double[][][][] output = new double[1][1][1][fr.getOutputs().size()];
        // Go from the input neurons
        try {
            output[0][0][0] = fr.predict(i);
        } catch (ActivationFNNException tok) {
        }
        return new InputOutput_Tensor(output);
    }

    @Override
    public InputOutput_Tensor compile(InputOutput_Tensor i) {
// Input parameters 
        this.ch = i.getN_channel();
        this.r = i.getN_rows();
        this.col = i.getN_columns();
// the output is one vector with neuron_layer neurons
        double[][][][] output = new double[1][1][1][(getInit()).getNumber_of_output_neurons()];
        return new InputOutput_Tensor(output);
    }

    /**
     * Initialize all the variables of this layer
     */
    @Override
    public void initializeVariablesOfThisLayer(Solution s) {
        ChromosomeFNN variable = getInit().generateChromosome(ch,r,col);
        s.add(variable);
    }
}
