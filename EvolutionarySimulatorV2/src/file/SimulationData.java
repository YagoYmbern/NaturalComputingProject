/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import data.input_output.OutputParameters;
import java.util.ArrayList;
import nc.input_output.InputOutput_Tensor;
import nc.input_output.InputOutput_Vector;

/**
 *
 * @author Oscar Lao
 */
public class SimulationData {
    private ArrayList<Double> param_simulation = new ArrayList<>();
    private InputOutput_Tensor inputOutput;
    private InputOutput_Vector outputParameter;
    
    public SimulationData(ArrayList<Double> param_simulation, InputOutput_Tensor inputOutput, InputOutput_Vector outputParameter)
    {
        this.param_simulation = param_simulation;
        this.inputOutput = inputOutput;
        this.outputParameter = outputParameter;
    }

    /**
     * Get the input sfs by window
     * @return 
     */
    public InputOutput_Tensor getInputOutput() {
        return inputOutput;
    }

    /**
     * Get the amount of introgression
     * @return 
     */
    public InputOutput_Vector getOutputParameter() {
        return outputParameter;
    }

    /**
     * Get the parameters of the simulation
     * @return 
     */
    public ArrayList<Double> getParam_simulation() {
        return param_simulation;
    }
}
