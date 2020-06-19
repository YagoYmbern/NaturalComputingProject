///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package maths;
//
//import Jama.EigenvalueDecomposition;
//import Jama.Matrix;
//
///**
// *
// * @author Oscar
// */
//public class MDS {
//
//    private Matrix matrix;
//    private EigenvalueDecomposition eig;
//    private double[] meanAr;
//    private double grandMean;
//
//    /**
//     * Perform classical MDS (Principal Coordinates) using Torgerson approach
//     * (see Multidimensional Scaling, Cox and Cox, chapter 2.2.5 A practical
//     * algorithm for classical scaling)
//     *
//     * @param d_matrix
//     */
//    public MDS(double[][] d_matrix) {
//        this.matrix = new Matrix(d_matrix);
//        performTorgensonTransformation();
//        doMDS();
//    }
//
//    /**
//     * Perform eigendecomposition using Jama library
//     *
//     * @param k
//     * @return
//     */
//    private void doMDS() {
//        eig = matrix.eig();
//    }
//
//    /**
//     * Get the eigenvalues of the classical MDS
//     *
//     * @return
//     */
//    public double[] getEigenValues() {
//        double[] eigVal = eig.getRealEigenvalues();
//        double[] eV = new double[eigVal.length - 1];
//        System.arraycopy(eigVal, 1, eV, 0, eV.length);
//        return eV;
//    }
//
//    /**
//     * Get the EigenVectors of the n_dimensions
//     *
//     * @param n_dimensions of the number of dimensions
//     * @return
//     */
//    public double[][] getEigenVectors(int n_dimensions) {
//        double[][] v = new double[eig.getV().getArray().length][n_dimensions];
//        for (int i = 0; i < v.length; i++) {
//            System.arraycopy(eig.getV().getArray()[i], 0, v[i], 0, n_dimensions);
//        }
//        return v;
//    }
//
//    /**
//     * Perform the Torgenson transformation
//     */
//    private void performTorgensonTransformation() {
//        int n = matrix.getColumnDimension();
//        meanAr = new double[n];
//        double[][] d2 = new double[n][n];
//        for (int i = 0; i < n; i++) {
//            for (int i2 = 0; i2 < n; i2++) {
//                d2[i][i2] = -0.5 * Math.pow(matrix.get(i, i2), 2);
//                meanAr[i] += d2[i][i2];
//            }
//            grandMean += meanAr[i];
//            meanAr[i] /= (double) (n);
//        }
//
//        grandMean *= Math.pow(n, -2);
//
//        for (int i = 0; i < n; i++) {
//            for (int i2 = 0; i2 < n; i2++) {
//                d2[i][i2] = d2[i][i2] - meanAr[i] - meanAr[i2] + grandMean;
//            }
//        }
//        matrix = new Matrix(d2);
//    }
//
//    /**
//     * Get the distance matrix
//     *
//     * @return
//     */
//    public Matrix getDistanceMatrix() {
//        return matrix;
//    }
//
////    public double[] eigenValuesNewSample(double[] distanceToOthers) {
////        try {
////            double n = meanAr.length;
////
////            double m = 0;
////
////            for (int e = 0; e < distanceToOthers.length; e++) {
////                distanceToOthers[e] = -0.5 * Math.pow(distanceToOthers[e], 2);
////                m += distanceToOthers[e];
////            }
////            m /= n;
////
////            for (int i = 0; i < n; i++) {
////                distanceToOthers[i] = distanceToOthers[i] - m - meanAr[i] + grandMean;
////            }
////
////            
////            
////        } catch (NullPointerException p) {
////// Not Torgerson transformation            
////        }
////    }
//}
