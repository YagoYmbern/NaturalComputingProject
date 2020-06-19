/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.function.optimize;

import error.FunctionToEstimateHowCloseIsTheSolutionToExpectedValue;
import nc.neurocomputing.layer.Layer;
import nc.neurocomputing.layer.Layer_Generic;
import nc.solution.Solution;


/**
 *
 * @author Oscar Lao
 */
public abstract class FunctionToOptimizeGeneric extends FunctionToOptimize<Layer_Generic> {
   
    public FunctionToOptimizeGeneric(FunctionToEstimateHowCloseIsTheSolutionToExpectedValue error)
    {
        super(error);
    }  
    
    /**
     * Generate a solution to solve this function to optimize. The class must do the position conversion of
     * parameters in the solution 
     * @return 
     */  
    @Override
    public Solution generateSolution() {
        // This is a new solution
        Solution s = new Solution(this);
        // Add the variables initialized by the 
        this.forEach((Layer l) -> {
            l.initializeVariablesOfThisLayer(s);
        });
        return s;
    }

    @Override
    public boolean update_input_output() {
// Do nothing
        return false;
    }        
}
