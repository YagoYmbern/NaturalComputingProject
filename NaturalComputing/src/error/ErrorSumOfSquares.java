/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package error;

import data.input_output.OutputParameters;
import nc.input_output.InputOutput_Vector;

/**
 *
 * @author olao
 */
public class ErrorSumOfSquares extends FunctionToEstimateHowCloseIsTheSolutionToExpectedValue {

//    private final double[][][] density_weight;
//    private final double[][] mean_sd;

    public ErrorSumOfSquares(OutputParameters observed_in_solution) {
        super(observed_in_solution);
//        mean_sd = new double[observed_in_solution.get(0).getV().length][2];
//        for(int v=0;v<mean_sd.length;v++)
//        {
//            double x = 0;
//            double x2= 0;
//            for(InputOutput_Vector io:observed_in_solution)
//            {
//                x+= io.getV()[v];
//                x2+=Math.pow(io.getV()[v],2);
//            }
//            x/=observed_in_solution.size();
//            x2/=observed_in_solution.size();
//            mean_sd[v][0] = x;
//            mean_sd[v][1] = Math.pow(x2-Math.pow(x,2),0.5);
//        }
        
//        density_weight = new double[observed_in_solution.size()][observed_in_solution.get(0).getV().length][2];
//        for(double [][] dd:density_weight)
//        {
//            for(double [] dv:dd)
//            {
//                dv[0] = Double.MAX_VALUE;
//                dv[1] = Double.MIN_VALUE;
//            }
//        }
//        average_distance_in_each_variable = new double[observed_in_solution.get(0).getV().length];
//        for (int v = 0; v < average_distance_in_each_variable.length; v++) {
////            IntToDouble [] into = new IntToDouble[density_weight.length];
//            // mean distance
//            for (int i1 = 0; i1 < observed_in_solution.size() - 1; i1++) {
////                into[i1] = new IntToDouble(i1,observed_in_solution.get(i1).getV()[v]);
//                for (int i2 = i1 + 1; i2 < observed_in_solution.size(); i2++) {
//                    double dd = Math.abs(observed_in_solution.get(i1).getV()[v] - observed_in_solution.get(i2).getV()[v]);
//                    if (density_weight[i1][v][1] < dd) {
//                        density_weight[i1][v][1] = dd;
//                    }
//                    else if(density_weight[i1][v][0] > dd)
//                    {
//                        density_weight[i1][v][0] = dd;                        
//                    }
//                    if (density_weight[i2][v][1] < dd) {
//                        density_weight[i2][v][1] = dd;
//                    }
//                    else if(density_weight[i1][v][0] > dd)
//                    {
//                        density_weight[i1][v][0] = dd;                        
//                    }
//                    average_distance_in_each_variable[v] += dd;
//                }
//            }
//            average_distance_in_each_variable[v] /= observed_in_solution.size() * (observed_in_solution.size() - 1) / 2;
////            // last element
////            into[density_weight.length - 1] = new IntToDouble(density_weight.length - 1, observed_in_solution.get(density_weight.length - 1).getV()[v]);
////            // Order the elements in descending order. This is the accumulative distribution function
////            java.util.Arrays.sort(into);
////            // compute the distance between consecutive elements.

//        }
    }

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
                erre += Math.pow(ioe[c] - ioo[c],2);///(density_weight[e][c][1]-density_weight[e][c][0]);//, 2);
                
//                erre += Math.abs(ioe[c] - (ioo[c]-mean_sd[c][0])/mean_sd[c][1]);///(density_weight[e][c][1]-density_weight[e][c][0]);//, 2);
            }
            // Scale by the average distance of each variable that we expect if we pick a partner from the observed distribution
            erre /= observed_in_solution.get(e).getV().length;
            err += erre;
        }
        // Average error among all the observations and variables
        err /= (produced_in_training_by_solution.size());
//        }
        return err;
    }
}
