/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package error;

import data.input_output.OutputParameters;
import nc.input_output.InputOutput_Tensor;

/**
 *
 * @author olao
 */
public class MinimumValueOfFunction extends FunctionToEstimateHowCloseIsTheSolutionToExpectedValue {

    public MinimumValueOfFunction() {
        super(null);
    }

    /**
     * Compute the error expected in training using the sum of squares
     *
     * @param expected_in_training
     * @return
     */
    @Override
    public double get_error(OutputParameters expected_in_training) {
        double[] ioe = expected_in_training.get(0).getV();
        return ioe[0];
    }
}
