/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.input_output;

import java.util.ArrayList;
import nc.input_output.InputOutput_Vector;

/**
 * Get the Output Parameters of the function to be estimated
 *
 * @author Oscar Lao
 */
public class OutputParameters extends ArrayList<InputOutput_Vector> {

    // For each variable and bin category, the inputoutput_vectors that belong to that category
    private ArrayList<Integer>[][] variable_bins;
    private double[][] rank_by_variable;
    private double[][] mean_dist;

    /**
     * 
     * @return 
     */
    public double [][] get_mean_dist()
    {
        return mean_dist;
    }
    
    /**
     * For each variable, compute the mean distance of each point to each other
     * point in the sample
     */
    public void compute_mean_dist() {
        if (!this.isEmpty()) {
            mean_dist = new double[this.size()][this.get(0).getV().length];
            for (int v = 0; v < this.get(0).getV().length; v++) {
                for (int i = 0; i < this.size(); i++) {
                    for (int i2 = 0; i2 < this.size(); i2++) {
                        if (i != i2) {
                            mean_dist[i][v] += Math.abs(this.get(i).getV()[v] - this.get(i2).getV()[v]);
                        }
                    }
                    mean_dist[i][v] /= (this.size() - 1);
                }
            }
        }
    }

    /**
     * Compute the min and max values of each variable given the
     * input_output_vectors that have been loaded so far.
     */
    public void compute_variable_ranks() {
        if (!this.isEmpty()) {
            rank_by_variable = new double[this.get(0).getV().length][2];
            for (int v = 0; v < this.get(0).getV().length; v++) {
                rank_by_variable[v][0] = Double.MAX_VALUE;
                rank_by_variable[v][1] = Double.MIN_VALUE;
                for (InputOutput_Vector i : this) {
                    double vv = i.getV()[v];
                    if (vv < rank_by_variable[v][0]) {
                        rank_by_variable[v][0] = vv;
                    } else if (vv > rank_by_variable[v][1]) {
                        rank_by_variable[v][1] = vv;
                    }
                }
            }
        }
    }

    /**
     * Set each variable in the rank 0/1. First the compute_variable_ranks is
     * called.
     */
    public void set_to_interval_0_1() {
        compute_variable_ranks();
        this.forEach((io) -> {
            for (int v = 0; v < rank_by_variable.length; v++) {
                io.set(v, scale_by_rank(v, io.getV()[v]));
            }
        });
    }

    /**
     * Scale by the rank the value of the variable vv given that the min and max
     * of each variable has been previously computed by calling
     * compute_variable_ranks
     *
     * @param v
     * @param vv
     * @return
     */
    public double scale_by_rank(int v, double vv) {
        if (rank_by_variable != null) {
            double b = (vv - rank_by_variable[v][0]) / (rank_by_variable[v][1] - rank_by_variable[v][0]);
            return Math.max(0, Math.min(1, b));
        }
        return vv;
    }

    /**
     * Return the unscaled value of vv at variable v. vv ranges between 0 and 1
     *
     * @param v
     * @param vv
     * @return
     */
    public double unscale(int v, double vv) {
        if (vv >= 0 && vv <= 1 && rank_by_variable != null) {
            return vv * (rank_by_variable[v][1] - rank_by_variable[v][0]) + rank_by_variable[v][0];
        }
        return vv;
    }

    /**
     * Create a output parameters where each variable stored in the
     * InputOutput_Vector is divided in n_bins. This will be used in the error
     * estimation to estimate the average error by bin rather than the
     *
     * @param n_bins
     */
    public void generate_bins_for_each_variable(int n_bins) {
        if (this.size() > 1) {
            variable_bins = new ArrayList[get(0).getV().length][n_bins];
            for (ArrayList<Integer>[] variable_bin : variable_bins) {
                for (int bin = 0; bin < variable_bin.length; bin++) {
                    variable_bin[bin] = new ArrayList<>();
                }
            }
// for each variable fin min and max. Divide the interval in n_bins
            for (int v = 0; v < variable_bins.length; v++) {
                double min = Double.MAX_VALUE;
                double max = Double.MIN_VALUE;
                for (InputOutput_Vector i : this) {
                    double vv = i.getV()[v];
                    if (vv < min) {
                        min = vv;
                    } else if (vv > max) {
                        max = vv;
                    }
                }
                // interval increment
                double increment = (max - min) / n_bins;
                for (int b = 0; b < n_bins; b++) {
                    variable_bins[v][b] = new ArrayList<>();
                }
                int c = 0;
                for (InputOutput_Vector i : this) {
                    double vv = i.getV()[v] - min;
                    try {
                        variable_bins[v][(int) (vv / increment)].add(c);
                    } catch (IndexOutOfBoundsException tok) {
                        variable_bins[v][(int) ((vv / increment) - 1)].add(c);
                    }
                    c++;
                }
            }
        }
    }

    /**
     * Get the variable bins. Each inputoutput for each variable has been
     * associated to a category based on n_bins (if generate_bins has been
     * called!)
     *
     * @return
     */
    public ArrayList<Integer>[][] getVariable_bins() {
        return variable_bins;
    }
}
