/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.layer.neural_network;

import nc.input_output.InputOutput_Tensor;
import nc.solution.Chromosome;

/**
 *
 * @author olao
 */
public class Layer_Unfold extends Layer_NeuralNetwork_Without_Weights {

    /**
     * Unfold the elements of the layer so it becomes a simple Layer_Perceptron
     */
    public Layer_Unfold() {
    }

    @Override
    public InputOutput_Tensor getOutput(Chromosome s, InputOutput_Tensor i) {
        double[][][][] output = new double[1][1][1][i.getN_time_series()* i.getN_channel() * i.getN_rows()* i.getN_columns()];
        int k = 0;
        for(int t=0;t<i.getN_time_series();t++)
        {
            for(int ch=0;ch<i.getN_channel();ch++)
            {
                for(int r=0;r<i.getN_rows();r++)
                {
                    for(int c=0;c<i.getN_columns();c++)
                    {
                        output[0][0][0][k] = i.get(t, ch, r, c);
                        k++;
                    }
                }
            }
        }
        return new InputOutput_Tensor(output);
    }

//    @Override
//    public Layer_Unfold copy() {
//        return new Layer_Unfold();
//    }
    @Override
    public InputOutput_Tensor compile(InputOutput_Tensor i) {
        double[][][][] output = new double[1][1][1][i.getN_time_series()* i.getN_channel() * i.getN_rows()* i.getN_columns()];
        return new InputOutput_Tensor(output);
    }
}
