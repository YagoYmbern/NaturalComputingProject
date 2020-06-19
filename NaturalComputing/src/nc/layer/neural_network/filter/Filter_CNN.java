/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.layer.neural_network.filter;

import nc.input_output.InputOutput_Tensor;
import nc.neurocomputing.activation.Activation_Function;
import nc.solution.Chromosome;
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
 * A convolutional neural network in 2 D filter.
 * @author olao
 */
public class Filter_CNN extends Filter<Filter_CNN> {

    /**
     * Create a filter of window_size pixels.The channel size comes determined by the deepness of the previous layer
     *
     * @param init
     * @param stu
     * @param recombine
     * @param column_size
     * @param row_size
     * @param a
     */
    public Filter_CNN(ChromosomeGenerator init, StrategyToUpdateChromosome stu, RecombinationChromosomes recombine, int column_size, int row_size, Activation_Function a) {
        super(init,stu,recombine, column_size, row_size, a);
    }          
    
    /**
     * Create a filter setting the container parameters of the nc to default
     * @param column_size
     * @param row_size
     * @param a 
     */
    public Filter_CNN(int column_size, int row_size, Activation_Function a)
    {
        super(new ChromosomeGenerator(new GeneratorVDoubleGaussian(new NStepSizeSelfAdaptation(0.1, 0.1, 0.5), 0.1)),
        new StrategyToUpdateChromosomeGeneric(),
        new RecombinationPairedVariables(new RecombinationVRandom()),
        column_size, row_size, a);        
    }
    

    @Override
    public InputOutput_Tensor getOutput(Chromosome s, InputOutput_Tensor in) {
        double[][][][] output_matrix = new double[1][1][in.getN_rows() - row_size][in.getN_columns() - column_size];
//        int padding_at_each_side = (row_size - 1) / 2;
// Output has exactly the same number of columns as the input.

        // For each channel
        for (int channel = 0; channel < in.getN_channel(); channel++) {
            int roc = 0;
            // Set the upper of the window at row. Everytime we will 
            for (int row = 0; row < in.getN_rows() - row_size; row++) {
                // Set the left of the window at column
                for (int col = 0; col < in.getN_columns() - column_size; col++) {
                    int coc = 0;
                    double v =0;
                    int count = 0;
                    // For a given window, go through the rows
                    for (int e = 0; e < row_size; e++) {
                        // Go through the columns
                        for (int o = 0; o < column_size; o++) {
                            // Update the variable v so it add the convolution of the value at position row+e, col+e by the value of the filter
                            v = in.get(0,channel,row+e,col+o) * ((VDouble)s.get(count)).getI().getD();
                            count++;
                        }
                    }
                    // Average over all the convolutions
                    v/= count;                    
                    // Add bias and activate
                    output_matrix[0][channel][roc][coc] = aFunction.activate(v + ((VDouble)s.get(count)).getI().getD());
                    coc++;
                }
                roc++;
            }
        }

        return new InputOutput_Tensor(output_matrix);
    }

    @Override
    public InputOutput_Tensor compile(InputOutput_Tensor in) {
        double[][][][] output_matrix = new double[1][1][in.getN_rows() - row_size][in.getN_columns() - column_size];
        size = in.getN_channel() * row_size * column_size + 1;
        return new InputOutput_Tensor(output_matrix);
    }
    
    @Override
    public void initializeVariablesOfThisLayer(Solution s) {
        Chromosome variable = getInit().generateChromosome(size);
        s.add(variable);
    }    
}
