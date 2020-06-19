/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.layer.neural_network.filter;

import java.util.ArrayList;
import nc.input_output.InputOutput_Tensor;
import nc.layer.neural_network.Layer_NeuralNetwork_With_Weights;
import nc.solution.Chromosome;
import nc.solution.Solution;

/**
 * A container of filters defining a layer.
 *
 * @author olao
 */
public class SetOf_Filter_CNN extends Layer_NeuralNetwork_With_Weights {
    
// Array to store the filters
    private final ArrayList<Filter_CNN> filters = new ArrayList<>();

    /**
     * A set of filters
     */
    public SetOf_Filter_CNN() {
        super(null,null,null);
    }

    /**
     * total number of filters considered for this set of filters
     * @return 
     */
    public int n_filters() {
        return filters.size();
    }

    /**
     * Add a new filter
     *
     * @param f
     * @return
     */
    public boolean add(Filter_CNN f) {
        return filters.add(f);
    }

    /**
     * Get filter in position i
     *
     * @param i
     * @return
     */
    public Filter_CNN get(int i) {
        return filters.get(i);
    }

    @Override
    public InputOutput_Tensor getOutput(Chromosome s, InputOutput_Tensor in) {
        double[][][][] output = new double[1][this.size()][][];
        for (int f = 0; f < this.size(); f++) {
            InputOutput_Tensor o = this.get(f).getOutput(s, in);
            output[f] = o.get()[0];
        }
        return new InputOutput_Tensor(output);
    }

    @Override
    public String toString() {
        StringBuilder a = new StringBuilder();
        filters.forEach((f) -> {
            a.append(f.toString()).append("\n");
        });
        return a.toString();
    }

    /**
     * Compute the total number of weights among all the filters
     * @return 
     */
    @Override
    public int size() {
        int n = 0;
        n = filters.stream().map((f) -> f.size()).reduce(n, Integer::sum);
        return n;
    }

    /**
     * Compile each filter
     * @param in
     * @return 
     */
    @Override
    public InputOutput_Tensor compile(InputOutput_Tensor in) {
        double[][][][] output = new double[1][this.size()][][];
        for (int f = 0; f < this.size(); f++) {
            InputOutput_Tensor o = this.get(f).compile(in);
            output[f] = o.get()[0];
        }
        return new InputOutput_Tensor(output);
    }
    /**
     * Initialize each filter
     */
    @Override
    public void initializeVariablesOfThisLayer(Solution s) {
        filters.forEach((f) -> {
            f.initializeVariablesOfThisLayer(s);
        });              
    }      
}
