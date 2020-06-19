/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.function.optimize;

import data.input_output.InputData;
import data.input_output.OutputParameters;
import error.FunctionToEstimateHowCloseIsTheSolutionToExpectedValue;
import maths.random.RandomUniformWithoutResampling;
import nc.solution.Solution;
import nc.input_output.InputOutput_Tensor;
import nc.input_output.InputOutput_Vector;
import nc.neurocomputing.layer.Layer;
import nc.layer.neural_network.Layer_NeuralNetwork;

/**
 * Implement a solution coded as a network
 *
 * @author olao
 */
public class Network extends FunctionToOptimize<Layer_NeuralNetwork> {

    // input data
    protected InputData ip;
    // the Network has never been compiled
    private boolean compiled = false;
    // the batch size
    private final int batch_size;
    // the ids of the samples at this iteration
    private int[] ids;

    /**
     * Implement a neural network topology.Here the function to implement and
     * the solution are coded at the same time
     *
     * @param training the input data
     * @param error
     */
    public Network(InputData training, FunctionToEstimateHowCloseIsTheSolutionToExpectedValue error) {
        super(error);
        this.ip = training;
        // Use all the observations
        this.batch_size = -1;
    }

    /**
     * Implement a neural network topology.Here the function to implement and
     * the solution are coded at the same time
     *
     * @param training the input data
     * @param batch_size the number of samples that are going to be used at each
     * iteration
     * @param error
     */
    public Network(InputData training, int batch_size, FunctionToEstimateHowCloseIsTheSolutionToExpectedValue error) {
        super(error);
        this.batch_size = batch_size;
        this.ip = training;
    }

    /**
     * Set the input parameters
     *
     * @param ip
     */
    public void setIp(InputData ip) {
        this.ip = ip;
    }

    /**
     * Get the input parameters
     *
     * @return
     */
    public InputData getIp() {
        return ip;
    }

    /**
     * Add a new layer
     *
     * @param l
     * @return
     */
    @Override
    public boolean add(Layer_NeuralNetwork l) {
        // Only allow to add a new layer if the network has not been yet compiled
        if (compiled) {
            return false;
        }
        return super.add(l);
    }

    /**
     * Compile the current network
     */
    private void compile() {
        InputOutput_Tensor inout = ip.get(0);
        for (Layer_NeuralNetwork l : this) {
// initialize the correspondence of variables in each layer            
            inout = l.compile(inout);
        }
        compiled = true;
    }

    /**
     * Compute the output parameters and evaluate the fitness
     *
     * @return
     */
    @Override
    public OutputParameters compute_f_x_Of_solution(Solution s) {
        OutputParameters f_x = new OutputParameters();
        if (ids == null) {
            int obs = ip.size();
            // for each element
            for (int i = 0; i < obs; i++) {
                // this is the input
                InputOutput_Tensor io = ip.get(i);
                // Add the final output to the outputParameters list
                f_x.add(compute_f_x_Of_solution(io, s));
            }
        } else {
            // we are running in batch effect mode!
            for (int i:ids) {
                // this is the input
                InputOutput_Tensor io = ip.get(i);                
                // Add the final output to the outputParameters list
                f_x.add(compute_f_x_Of_solution(io, s));
            }
        }

        return f_x;
    }

    /**
     * Compute the function of x at io using the parameters of solution s
     *
     * @param io
     * @param s
     * @return 
     */
    public InputOutput_Vector compute_f_x_Of_solution(InputOutput_Tensor io, Solution s) {
        InputOutput_Tensor ioo = io;
        // iterate over all the layers. Take as input io. Produce as output io            
        int i = 0;
        for (Layer l : this) {
            ioo = ((Layer_NeuralNetwork) l).getOutput(s.get(i), ioo);
            i++;
        }
        return new InputOutput_Vector(ioo.get()[0][0][0]);
    }

    /**
     * Generate a new solution by asking to each layer to provide the variables
     * to be estimated
     *
     * @return
     */
    @Override
    public Solution generateSolution() {
        // Compile the network
        if (!compiled) {
            compile();
        }
        Solution s = new Solution(this);

        this.forEach((Layer l) -> {
            l.initializeVariablesOfThisLayer(s);
        });
        return s;
    }

    @Override
    public boolean update_input_output() {
        if (batch_size > 0) {
            RandomUniformWithoutResampling run = new RandomUniformWithoutResampling(0, ip.size());
            ids = run.sample(batch_size);
            return true;
        }
        return false;
    }
}
