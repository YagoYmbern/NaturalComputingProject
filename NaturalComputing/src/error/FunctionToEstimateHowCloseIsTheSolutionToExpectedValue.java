/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package error;

import data.input_output.OutputParameters;

/**
 *
 * @author Oscar Lao
 */
public abstract class FunctionToEstimateHowCloseIsTheSolutionToExpectedValue {
    
    public FunctionToEstimateHowCloseIsTheSolutionToExpectedValue(OutputParameters observed_in_solution)
    {
        this.observed_in_solution = observed_in_solution;
    }
    // these are the parameters observed in solution
    protected OutputParameters observed_in_solution;

    /**
     * Get the output parameters observed in solution
     * @return 
     */
    public OutputParameters getObserved_in_solution() {
        return observed_in_solution;
    }
    
    /**
     * Compute the fitness between the observed parameters in solution and expected in the training_dataset 
     * @param expected_in_training
     * @return 
     */
    public abstract double get_error(OutputParameters expected_in_training);    
}
