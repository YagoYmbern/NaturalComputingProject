/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.layer.neural_network;

import nc.neurocomputing.activation.Activation_Function;
import nc.input_output.InputOutput_Tensor;
import nc.solution.Chromosome;
import nc.solution.Container_NC;
import nc.solution.Solution;
import nc.solution.chromosome.generator.ChromosomeGenerator;
import nc.solution.chromosome.recombine.RecombinationChromosomes;
import nc.solution.chromosome.recombine.RecombinationPairedVariables;
import nc.solution.chromosome.update.StrategyToUpdateChromosome;
import nc.solution.chromosome.update.StrategyToUpdateChromosomeGeneric;
import nc.variable.strategyToRecombine.RecombinationVRandom;
import nc.variable.vdouble.GeneratorVDoubleGaussian;
import nc.variable.vdouble.VDouble;
import nc.variable.vdouble.updater.self_adaptation.NStepSizeSelfAdaptation;

/**
 *
 * @author Oscar Lao
 */
public class Layer_Perceptron extends Layer_NeuralNetwork_With_Weights<Chromosome<VDouble>, ChromosomeGenerator> {

    // Neurons from the previous layer
    private int neurons_previous;
    // Neurons of this layer
    private final int neurons_layer;
    // Activation function
    private final Activation_Function act;

    /**
     * Neurons of the layer
     * @param init
     * @param stu
     * @param recombine
     * @param neurons_layer the neurons of this layer
     * @param act the activation function to apply to the output
     */
    public Layer_Perceptron(ChromosomeGenerator init, StrategyToUpdateChromosome stu, RecombinationChromosomes recombine, int neurons_layer, Activation_Function act) {
        super(init,stu,recombine);
// activation function
        this.act = act;
        this.neurons_layer = neurons_layer;
    }
    
    
    /**
     * A layer perceptron with default values of the strategy of the nc parameters
     * It uses self adaptation for each variable with parameters sd = 0.1, learning rate of variable = 0.1, learning rate overall =0.5
     * It uses atomic recombination between parents
     * the strategy to update the chromosome is 0.01
     * 
     * @param neurons_layer
     * @param act 
     */
    public Layer_Perceptron(int neurons_layer, Activation_Function act) {        
        super(new ChromosomeGenerator(new GeneratorVDoubleGaussian(new NStepSizeSelfAdaptation(0.1, 0.1, 0.5), 0.1)),
        new StrategyToUpdateChromosomeGeneric(),
        new RecombinationPairedVariables(new RecombinationVRandom()));
// activation function
        this.act = act;
        this.neurons_layer = neurons_layer;
    }    

    /**
     * Compute the output of the solution
     *
     * @param s
     * @param i
     * @return
     */
    @Override
    public InputOutput_Tensor getOutput(Chromosome<VDouble> s, InputOutput_Tensor i) {
        double[][][][] output = new double[1][1][1][neurons_layer];
        int counter = 0;
        // Go from the input neurons
        for (int r = 0; r < i.getN_columns(); r++) {
            // To the output neurons
            for (int c = 0; c < output[0][0].length; c++) {
                output[0][0][0][c] += i.get(0,0,0,r) * ((VDouble) s.get(counter)).getI().getD();
                counter++;
            }
        }
// add the bias
        for (int c = 0; c < output[0][0][0].length; c++) {
            output[0][0][0][c] += ((VDouble) s.get(counter)).getI().getD();
            output[0][0][0][c] = act.activate(output[0][0][0][c]);
            counter++;
        }
        return new InputOutput_Tensor(output);
    }

    @Override
    public InputOutput_Tensor compile(InputOutput_Tensor i) {
// the output is one vector with neuron_layer neurons
        double[][][][] output = new double[1][1][1][neurons_layer];        
        neurons_previous = i.getN_columns();
        size = neurons_previous * neurons_layer + neurons_layer;
        return new InputOutput_Tensor(output);
    }

    /**
     * Initialize all the variables of this layer
     */
    @Override
    public void initializeVariablesOfThisLayer(Solution s) {
        Chromosome<VDouble> variable = getInit().generateChromosome(size);    
        s.add(variable);
    }
}
