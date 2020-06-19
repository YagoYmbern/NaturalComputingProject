/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.layer.neural_network.pooling;

import nc.input_output.InputOutput_Tensor;
import nc.layer.neural_network.Layer_NeuralNetwork_Without_Weights;
import nc.solution.Chromosome;

/**
 * Pool the values of a window of window_pooling length from each InputOutput
 * channel by using the poolingF function
 *
 * @author olao
 */
public class Layer_Pool extends Layer_NeuralNetwork_Without_Weights {

    private final int column_pooling, row_pooling;
    private PoolingFunction poolingF;

    /**
     * Pool the elements in the window using the pooling function
     *
     * @param row_pooling
     * @param column_pooling
     * @param poolingF
     */
    public Layer_Pool(int row_pooling, int column_pooling, PoolingFunction poolingF) {
        this.column_pooling = column_pooling;
        this.row_pooling = row_pooling;
        this.poolingF = poolingF;
    }

    public void setPoolingF(PoolingFunction poolingF) {
        this.poolingF = poolingF;
    }

    public PoolingFunction getPoolingF() {
        return poolingF;
    }

    @Override
    public InputOutput_Tensor getOutput(Chromosome s, InputOutput_Tensor i) {
        int shrink_row = i.getN_rows() / row_pooling;
        int shrink_column = i.getN_columns() / column_pooling;
        shrink_row += i.getN_rows() % row_pooling;
        shrink_column += i.getN_columns() % column_pooling;

        double[][][][] output = new double[i.getN_time_series()][i.getN_channel()][shrink_row][shrink_column];
        // For each channel
        for (int time = 0; time < i.getN_time_series(); time++) {
            for (int channel = 0; channel < i.getN_channel(); channel++) {
                int roc = 0;
                for (int row = 0; row < i.getN_rows() - row_pooling; row += row_pooling) {
                    for (int col = 0; col < i.getN_columns() - column_pooling; col += column_pooling) {
                        int coc = 0;
                        double[][] v = new double[row_pooling][column_pooling];
                        for (int e = 0; e < row_pooling; e++) {
                            for (int o = 0; o < column_pooling; o++) {
                                v[e][o] = i.get(time, channel, row + e, col + o);
                            }
                        }
                        output[time][channel][roc][coc] = poolingF.pool(v);
                        coc++;
                    }
                    roc++;
                }
            }
        }

        return new InputOutput_Tensor(output);
    }

//    @Override
//    public Layer_Pool copy() {
//        return new Layer_Pool(window_pooling, poolingF);
//    }   
    @Override
    public InputOutput_Tensor compile(InputOutput_Tensor i) {
        int shrink_row = i.getN_rows() / row_pooling;
        int shrink_column = i.getN_columns() / column_pooling;
        shrink_row += i.getN_rows() % row_pooling;
        shrink_column += i.getN_columns() % column_pooling;
        double[][][][] output = new double[i.getN_time_series()][i.getN_channel()][shrink_row][shrink_column];
        return new InputOutput_Tensor(output);
    }
}
