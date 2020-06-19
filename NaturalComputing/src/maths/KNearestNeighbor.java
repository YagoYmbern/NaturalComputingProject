/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maths;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author olao
 */
public class KNearestNeighbor {

    /**
     * Compute the k nearest neighbours of each observation (row) given features
     * (columns) in matrix m using the convolution as measure of how similar the
     * two patterns are
     *
     * @param m
     * @param k
     * @return
     */
    public static Pair[][] getKNearest(double[][] m, int k) {
        ArrayList<Pair>[] ids_k_nearest = new ArrayList[m.length];
// For each vector of pairs, account the id with the maximum value (it cannot be greater than k!)        
        for (int i = 0; i < ids_k_nearest.length; i++) {
            ids_k_nearest[i] = new ArrayList<>();
        }

        for (int i1 = 0; i1 < m.length - 1; i1++) {
            for (int i2 = i1 + 1; i2 < m.length; i2++) {
// Distance                
                double dis = 0;
                for (int v = 0; v < m[i1].length; v++) {
                    dis += Math.pow(m[i1][v] - m[i2][v],4);
                }
// Sorted list of pairs in i1 of size k
                ids_k_nearest[i1].add(new Pair(i2, 0, dis));
                if (ids_k_nearest[i1].size() > k) {
                    Collections.sort(ids_k_nearest[i1]);
                    ids_k_nearest[i1].remove(k);
                }
// Sorted list of pairs in i2 of size k                
                ids_k_nearest[i2].add(new Pair(i1, 0, dis));
                if (ids_k_nearest[i2].size() > k) {
                    Collections.sort(ids_k_nearest[i2]);
                    ids_k_nearest[i2].remove(k);
                }
            }
        }

        Pair [][] p = new Pair[ids_k_nearest.length][k];
        
        for (int i1 = 0; i1 < m.length; i1++) {
            ids_k_nearest[i1].toArray(p[i1]);
        }
        return p;
    }
    
    
    /**
     * Compute the k nearest neighbours of each observation (row) given features
     * (columns) in matrix m using the convolution as measure of how similar the
     * two patterns are
     *
     * @param m
     * @param w the weights of the variables (MUST ADD TO 1!)
     * @param k
     * @return
     */
    public static Pair[][] getKNearest(double[][] m, double [] w, int k) {
        ArrayList<Pair>[] ids_k_nearest = new ArrayList[m.length];
// For each vector of pairs, account the id with the maximum value (it cannot be greater than k!)        
        for (int i = 0; i < ids_k_nearest.length; i++) {
            ids_k_nearest[i] = new ArrayList<>();
        }

        for (int i1 = 0; i1 < m.length - 1; i1++) {
            for (int i2 = i1 + 1; i2 < m.length; i2++) {
// Distance                
                double dis = 0;
                for (int v = 0; v < m[i1].length; v++) {
                    dis += w[v]*Math.abs(m[i1][v] - m[i2][v]);
                }
// Sorted list of pairs in i1 of size k
                ids_k_nearest[i1].add(new Pair(i2, 0, dis));
                if (ids_k_nearest[i1].size() > k) {
                    Collections.sort(ids_k_nearest[i1]);
                    ids_k_nearest[i1].remove(k);
                }
// Sorted list of pairs in i2 of size k                
                ids_k_nearest[i2].add(new Pair(i1, 0, dis));
                if (ids_k_nearest[i2].size() > k) {
                    Collections.sort(ids_k_nearest[i2]);
                    ids_k_nearest[i2].remove(k);
                }
            }
        }

        Pair [][] p = new Pair[ids_k_nearest.length][k];
        
        for (int i1 = 0; i1 < m.length; i1++) {
            ids_k_nearest[i1].toArray(p[i1]);
        }
        return p;
    }

    

//    public static Pair[] getKNearest(double[] input, double[][] m, int k) {
//        Pair[] ids_k_nearest = new Pair[k];
//        Pair[] d = new Pair[m.length];
//        for (int i1 = 0; i1 < m.length; i1++) {
//            double dis = 0;
//            for (int v = 0; v < m[i1].length; v++) {
//                dis += Math.pow(m[i1][v] - input[v], 2);
//            }
//            d[i1] = new Pair(i1, 0, dis);
//        }
//// Now get the closest neighbours        
//        java.util.Arrays.sort(d);
//        // take the closest k elements
//        System.arraycopy(d, 0, ids_k_nearest, 0, k);
//        return ids_k_nearest;
//    }

//
//    /**
//     * Compute the k nearest neighbours of each observation (row) given features
//     * (columns) in matrix m using the convolution as measure of how similar the
//     * two patterns are
//     *
//     * @param m
//     * @param k
//     * @return
//     */
//    public static Pair[][] getKNearest(double[][] m, int k) {
//        Pair[][] ids_k_nearest = new Pair[m.length][k];
//        Pair[][] d = new Pair[m.length][m.length];
//        for (int i1 = 0; i1 < m.length - 1; i1++) {
//            // Compare with itself gives the maximum value
//            d[i1][i1] = new Pair(i1, 0, Double.MAX_VALUE);
//            for (int i2 = i1 + 1; i2 < m.length; i2++) {
//                double dis = 0;
//                for (int v = 0; v < m[i1].length; v++) {
//                    dis += Math.pow(m[i1][v] - m[i2][v], 2);
//                }
//                d[i1][i2] = new Pair(i2, 0, dis);
//                d[i2][i1] = new Pair(i1, 0, dis);
//            }
//        }
//        d[m.length - 1][m.length - 1] = new Pair(m.length - 1, 0, Double.MAX_VALUE);
//// Now get the closest neighbours        
//        for (int i1 = 0; i1 < m.length; i1++) {
//            java.util.Arrays.sort(d[i1]);
//            // take the closest k elements
//            for (int e = 0; e < k; e++) {
//                ids_k_nearest[i1][e] = d[i1][e];
//            }
//        }
//        return ids_k_nearest;
//    }
//
//    public static Pair[] getKNearest(double[] input, double[][] m, int k) {
//        Pair[] ids_k_nearest = new Pair[k];
//        Pair[] d = new Pair[m.length];
//        for (int i1 = 0; i1 < m.length; i1++) {
//            double dis = 0;
//            for (int v = 0; v < m[i1].length; v++) {
//                dis += Math.pow(input[v] - m[i1][v], 2);
//            }
//            d[i1] = new Pair(i1, 0, dis);
//        }
//// Now get the closest neighbours        
//        java.util.Arrays.sort(d);
//        // take the closest k elements
//        System.arraycopy(d, 0, ids_k_nearest, 0, k);
//        return ids_k_nearest;
//    }
}
