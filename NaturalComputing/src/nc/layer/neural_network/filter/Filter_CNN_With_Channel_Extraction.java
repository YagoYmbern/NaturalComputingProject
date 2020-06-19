package nc.layer.neural_network.filter;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nc.neurocomputing.layer.filter;
//
//import java.util.ArrayList;
//import nc.input_output.InputOutput;
//import nc.input_output.InputOutput_Matrix;
//import nc.neurocomputing.activation.Activation_Function;
//import nc.solution.Chromosome;
//import nc.solution.Solution;
//import nc.solution.chromosome.recombine.StrategyToRecombineChromosomes;
//import nc.variable.initialize.variable_Double.updater.StrategyToUpdate_Variable_Double;
//import nc.variable.Variable;
//import nc.variable.initialize.variable_Integer.Variable_Integer;
//import nc.variable.initialize.variable_Double.Variable_Double;
//import nc.variable.initialize.InitializeVariable_SetOfNonRepeated_Ids;
//import nc.variable.initialize.variable_Double.Generator_Variable_Double;
//
///**
// * A filter convolutional neural network that is only applied to some of all the
// * channels
// *
// * @author Oscar Lao
// */
//public class Filter_CNN_With_Channel_Extraction extends Filter<Filter_CNN> {
//
//    private final InitializeVariable_SetOfNonRepeated_Ids channel_extraction_initialization;
//
//    /**
//     * Create a filter of window_size pixels.The channel size comes determined
//     * by the deepness of the previous layer
//     *
//     * @param init the strategy to initialize the weights of the filter
//     * @param window_size the window_size of the filter (how many regions we are considering at each window)
//     * @param channel_extraction_initialization the strategy to initialize the channels that are going to be considered
//     * @param a
//     */
//    public Filter_CNN_With_Channel_Extraction(int window_size, Activation_Function a, InitializeVariable_SetOfNonRepeated_Ids channel_extraction_initialization, Generator_Variable_Double init, StrategyToRecombineChromosomes recombine) {
//        super(init, recombine, window_size, a);
//        // Last element is the bias unit
//        this.channel_extraction_initialization = channel_extraction_initialization;
//    }
//     
//    @Override
//    public InputOutput getOutput(Chromosome s, InputOutput in) {
//        // The ids of the channel are stored in a variable set non repeated ids that corresponds to the last weight of the vector
//        int [] ids_channel = ((Variable_Integer)s.get(weights[weights.length - 1])).getId_set().getIds();
//        double[][] input = in.get();
//        double[][] output_matrix = new double[1][input[0].length - window_size];
//        int padding_at_each_side = (window_size - 1) / 2;
////        int padding_at_each_side = 0;
//// Output has exactly the same number of columns as the input.
//        for (int w = 0; w < (input[0].length - window_size - padding_at_each_side); w++) {
//            int count = 0;            
//            // iterate over all the ascertained ids of the channel
//            for (int id : ids_channel) {
//                double[] input1 = input[id];
//                for (int sliding = 0; sliding < window_size; sliding++) {
//                    output_matrix[0][padding_at_each_side + w] += input1[w + sliding] * ((Variable_Double) s.get(weights[count])).getValue().getV();
//                    count++;
//                }
//            }
//            // Average over all the weights of the filter. It is weights.length - 2 because the last two elements of the weight corresponds to the bias of the filter and to the set of ids of the channels considered by the Filter
//            output_matrix[0][padding_at_each_side + w] /= (weights.length - 2);
//            // Add bias
//            output_matrix[0][padding_at_each_side + w] = (!Double.isNaN(output_matrix[0][padding_at_each_side + w])) ? (float) aFunction.activate(output_matrix[0][padding_at_each_side + w] + ((Variable_Double) s.get(weights[weights.length - 2])).getValue().getV()) : 0;
//        }
//        return new InputOutput_Matrix(output_matrix);
//    }
//
//    @Override
//    public InputOutput compile(int pointer_starts_at, InputOutput in) {
//        double[][] input = in.get();
//        double[][] output_matrix = new double[1][input[0].length - window_size];
//        weights = new int[1 + in.getN_channel() * window_size + 1];
//        // First the variable containing the extraction of channels
//        // Update the parameters of the set of ids. We can take channels from 0
//        channel_extraction_initialization.getIds().setFrom(0);
//        // To the number of channels defined by the input
//        channel_extraction_initialization.getIds().setTo(in.getN_channel());
//        // Link with pointers of the weights solution
//        for (int i = 0; i < weights.length-1; i++) {
//            weights[i] = pointer_starts_at;
//            pointer_starts_at++;
//        }
//        // it only contains one variable that contains all the ids of the channels:
//        weights[weights.length-1] = pointer_starts_at;        
//        pointer_starts_at++;
//        return new InputOutput_Matrix(output_matrix);
//    }
//
//    @Override
//    public int size() {
//        return weights.length;
//    }
//
//    @Override
//    public void initializeVariablesOfThisLayer(Solution s) {
//        ArrayList<Variable> variable = new ArrayList<>();
//        // Weights of the filter
//        for (int e = 0; e < size()-1; e++) {
//            // Create a new variable
//            Variable_Double vav = new Variable_Double((Generator_Variable_Double) init, (StrategyToUpdate_Variable_Double) stuv.copy());
//            // Add it to the arraylist
//            variable.add(vav);
//        }
//        // channels ascertained of the filter
//        variable.add(new Variable_Integer(channel_extraction_initialization));        
//        s.addAll(variable);
//    }
//}
