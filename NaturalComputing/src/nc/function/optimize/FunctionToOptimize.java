/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.function.optimize;

import data.input_output.OutputParameters;
import error.FunctionToEstimateHowCloseIsTheSolutionToExpectedValue;
import java.util.ArrayList;
import nc.Timer;
import nc.solution.Solution;

/**
 *
 * @author Oscar Lao
 * @param <T>
 */
public abstract class FunctionToOptimize<T> extends ArrayList<T> {

    protected FunctionToEstimateHowCloseIsTheSolutionToExpectedValue err;
    private final Timer t = new Timer();

    public FunctionToOptimize(FunctionToEstimateHowCloseIsTheSolutionToExpectedValue error) {
        this.err = error;
    }

    /**
     * Get the timer associated to this function to optimize. I.e. how many iterations this function to optimize has been checked
     * @return 
     */
    public Timer getT() {
        return t;
    }        

    /**
     * Get the function to estimate how close is the solution to the expected
     * value
     *
     * @return
     */
    public FunctionToEstimateHowCloseIsTheSolutionToExpectedValue getErr() {
        return err;
    }

    /**
     * Generate a solution
     *
     * @return
     */
    public abstract Solution generateSolution();

    /**
     * Compute the output f(x) of the proposed solution
     *
     * @param s
     * @return
     */
    public abstract OutputParameters compute_f_x_Of_solution(Solution s);

    /**
     * Update the input output with batch effect.If true the input_output has
     * been updated and all the elements of the current solution must be
     * evaluated again
     *
     * @return
     */
    public abstract boolean update_input_output();
}
