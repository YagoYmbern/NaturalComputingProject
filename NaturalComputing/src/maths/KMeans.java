/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maths;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Oscar Lao
 */
public class KMeans {

    /**
     * Get the cluster assignment of each element in m (row) using classical K
     * means algorithm
     *
     * @param m
     * @param k
     * @return
     */
    public static int[] k_means(double[][] m, int k) {
// First standardize everything, so all the variables are centered at 0 and have a standard deviation of 1
        for (int v = 0; v < m[0].length; v++) {
            double x = 0;
            double x2 = 0;
            for (int i = 0; i < m.length; i++) {
                x += m[i][v];
                x2 += Math.pow(m[i][v], 2);
            }
            x /= m.length;
            x2 /= m.length;
            double sd = Math.pow(x2 - Math.pow(x, 2), 0.5);
            // if the variable is really variable among the different elements, then standardize the individuals
            if (sd != 0) {
                for (int i = 0; i < m.length; i++) {
                    m[i][v] = (m[i][v]-x)/sd;
                }
            }
        }
        int[] K = new int[m.length];
        // Means of each group for the position
        double[][] means_of_k = new double[k][m[0].length];
        // Assign each individual to one of the groups at random
        for (int i = 0; i < K.length; i++) {
            K[i] = ThreadLocalRandom.current().nextInt(k);
        }
        // Variable to check if the assignation to each cluster changes the next generation. If no changes are observed, we have reached convergence
        boolean assignation_has_changed_from_previous_iteration;
        do {
            assignation_has_changed_from_previous_iteration = false;
            // Compute the mean of each group for each variable
            int[] n = new int[k];
            for (int i = 0; i < K.length; i++) {
                n[K[i]]++;
                for (int v = 0; v < m[i].length; v++) {
                    means_of_k[K[i]][v] += m[i][v];
                }
            }
            // means of each cluster
            for (int c = 0; c < k; c++) {
                for (int v = 0; v < means_of_k[c].length; v++) {
                    means_of_k[c][v] /= n[c];
                }
            }
            // now assign each element to one of the groups based on its distance
            for (int i = 0; i < K.length; i++) {
                int gr = 0;
                double min_distance = d(m[i], means_of_k[0]);
                for (int c = 1; c < k; c++) {
                    double dd = d(m[i], means_of_k[c]);
                    if (dd < min_distance) {
                        gr = c;
                        min_distance = dd;
                    }
                }
                // New assignation. If it has  changed with regards to the previous iteration, then keep going
                if (K[i] != gr) {
                    assignation_has_changed_from_previous_iteration = true;
                }
                // New assignation
                K[i] = gr;
            }
        } while (assignation_has_changed_from_previous_iteration);
        return K;
    }

    /**
     * Compute the Euclidean distance
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double d(double[] v1, double[] v2) {
        double d = 0;
        for (int e = 0; e < v1.length; e++) {
            d += Math.pow(v1[e] - v2[e], 2);
        }

        return Math.pow(d, 0.5);
    }                
}
