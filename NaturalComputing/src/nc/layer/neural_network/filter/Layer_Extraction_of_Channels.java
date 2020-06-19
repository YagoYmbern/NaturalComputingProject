/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.layer.neural_network.filter;

import nc.input_output.InputOutput_Tensor;
import nc.layer.neural_network.Layer_NeuralNetwork_With_Weights;
import nc.solution.Chromosome;
import nc.solution.Solution;
import nc.solution.chromosome.generator.ChromosomeGenerator_Non_Repeated_Variables;
import nc.solution.chromosome.recombine.RecombinationNonRepeatedVariables;
import nc.solution.chromosome.update.StrategyToUpdateChromosome_Of_Non_Repeated_Variables;
import nc.variable.vinteger.GeneratorVIntegerRange;
import nc.variable.vinteger.UpdateVIntegerRange;
import nc.variable.vinteger.VInteger;

/**
 * A layer that extracts the channels of the given input.
 *
 * @author Oscar Lao
 * @param <Channel_Extraction_CNN>
 */
public class Layer_Extraction_of_Channels<Channel_Extraction_CNN> extends Layer_NeuralNetwork_With_Weights<Chromosome, ChromosomeGenerator_Non_Repeated_Variables> {

    private final int channels_to_extract;

    /**
     * Create a new extraction of channels of channels_to_extract size
     *
     * @param channels_to_extract
     */
    public Layer_Extraction_of_Channels(int channels_to_extract) {
        super(new ChromosomeGenerator_Non_Repeated_Variables(new GeneratorVIntegerRange(new UpdateVIntegerRange(0, 0))),
                new StrategyToUpdateChromosome_Of_Non_Repeated_Variables(),
                new RecombinationNonRepeatedVariables());
        this.channels_to_extract = channels_to_extract;
    }

    @Override
    public InputOutput_Tensor getOutput(Chromosome s, InputOutput_Tensor input) {
        double[][][][] m = new double[1][s.size()][input.getN_rows()][input.getN_columns()];
        for (int i = 0; i < s.size(); i++) {
            for (int r = 0; r < input.getN_rows(); r++) {
                System.arraycopy(input.get()[((VInteger) s.get(i)).getI()][r], 0, m[i][r], 0, m[i][r].length);
            }
        }
        return new InputOutput_Tensor(m);
    }

    @Override
    public InputOutput_Tensor compile(InputOutput_Tensor input) {
// Update the upper limit of the range to the number of input channels        
        ((UpdateVIntegerRange) ((GeneratorVIntegerRange) getInit().getInit()).getStu()).setTo(input.getN_channel());
        // it only contains one variable that contains all the ids of the channels:
// Return the same matrix as in input, but only with the channels that are defined by the size at the parameters of the set of non-repeated elements
        return new InputOutput_Tensor(new double[1][channels_to_extract][input.getN_rows()][input.getN_columns()]);
    }

    @Override
    public void initializeVariablesOfThisLayer(Solution s) {
        Chromosome ar = getInit().generateChromosome(size);
        s.add(ar);
    }
}
