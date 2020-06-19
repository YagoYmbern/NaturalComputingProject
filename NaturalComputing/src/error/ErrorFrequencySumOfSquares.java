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
public class ErrorFrequencySumOfSquares extends FunctionToEstimateHowCloseIsTheSolutionToExpectedValue {


    public ErrorFrequencySumOfSquares(OutputParameters observed_in_solution) {
        super(observed_in_solution);    }

    /**
     * Compute the error expected in training using the sum of squares
     *
     * @param produced_in_training_by_solution
     * @return
     */
    @Override
    public double get_error(OutputParameters produced_in_training_by_solution) {
//        System.out.println(getObserved_in_solution().size() + " " + produced_in_training_by_solution.size());
        double err = 0;
//        if (getObserved_in_solution().getVariable_bins() != null) {
//            ArrayList<Integer>[][] ids_by_var_and_bin = getObserved_in_solution().getVariable_bins();
//            int v = 0;
//            int count = 0;
//            double[] err_by_variable = new double[ids_by_var_and_bin.length];
//            // for each variable, retrieve all the bins
//            for (ArrayList<Integer>[] ids_by_bin : ids_by_var_and_bin) {
//                // for each bin, retrieve the observations that fall within the bin
//                for (ArrayList<Integer> ids : ids_by_bin) {
//                    double err_bin = 0;
//// for each observation within the bin, compute the error between the predicted and the observed
//                    for (int i : ids) {
//                        err_bin += Math.abs(produced_in_training_by_solution.get(i).getV()[v] - getObserved_in_solution().get(i).getV()[v])/average_distance_in_each_variable[v];//,2)/avera;                                            
//                    }
//                    // mean error of the bin
//                    err_bin /= ids.size();
//                    // add the error
//                    err_by_variable[v] += err_bin;
//                    count++;
//                }
//                err_by_variable[v] /= ids_by_bin.length;
//                v++;
//            }
//            double max = Double.MIN_VALUE;
//            for (double e : err_by_variable) {
//                if (e > max) {
//                    max = e;
//                }
//            }
//            err = max;
//
//        } else {
// for each observation tested in the training data, we get an output (that can be multiple variables)        
// compute the squared error error for each variable 
        // Pick for each proposed solution the variable
        for (int e = 0; e < produced_in_training_by_solution.size(); e++) {
            double erre = 0;
            for (int c = 0; c < observed_in_solution.get(e).getV().length; c++) {
                double[] ioe = produced_in_training_by_solution.get(e).getV();
                double[] ioo = observed_in_solution.get(e).getV();
                erre += observed_in_solution.get(e).getW()[c]*Math.pow(ioe[c] - ioo[c],2);///(density_weight[e][c][1]-density_weight[e][c][0]);//, 2);
            }
            // Scale by the average distance of each variable that we expect if we pick a partner from the observed distribution
            //erre /= observed_in_solution.get(e).getV().length;
            err += erre;
        }
        // Average error among all the observations and variables
        //err /= (produced_in_training_by_solution.size());
//        }
        return err;
    }
}
