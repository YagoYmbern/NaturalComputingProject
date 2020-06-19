/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.layer.neural_network;

import nc.input_output.InputOutput;
import nc.input_output.InputOutput_Tensor;
import nc.solution.Chromosome;

/**
 *
 * @author olao
 */
public class Layer_WinnerTakesItAll extends Layer_NeuralNetwork_Without_Weights {

    /**
     * For each channel, pick the neuron that is mostly activated
     */
    public Layer_WinnerTakesItAll() {
    }

    @Override
    public InputOutput_Tensor getOutput(Chromosome s, InputOutput_Tensor i) {
        double[][][][] output = new double[1][1][1][i.getN_channel()];
        for (int ch = 0; ch < i.getN_channel(); ch++) {
            double max = Double.MIN_VALUE;
            for (int r = 0; r < i.getN_rows(); r++) {
                for (int c = 0; c < i.getN_columns(); c++) {
                    double v = i.get(0, ch, r, c);
                    if (v > max) {
                        max = v;
                    }
                }
            }
            output[0][0][0][ch] = max;
        }
        return new InputOutput_Tensor(output);
    }

//    @Override
//    public Layer_WinnerTakesItAll copy() {
//        return new Layer_WinnerTakesItAll();
//    }   
    @Override
    public InputOutput_Tensor compile(InputOutput_Tensor i) {
        double[][][][] output = new double[1][1][1][i.getN_channel()];
        return new InputOutput_Tensor(output);
    }
}
