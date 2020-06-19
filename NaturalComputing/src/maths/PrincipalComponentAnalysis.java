///*
// * EigenAnalysis.java
// *
// * Created on 9 maart 2007, 17:15
// *
// * To change this template, choose Tools | Template Manager
// * and open the template in the editor.
// */
//package maths;
//
//import Jama.EigenvalueDecomposition;
//import Jama.Matrix;
//import java.util.Random;
//
///**
// *
// * @author 503760
// */
//public class PrincipalComponentAnalysis {
//
//    /**
//     * Creates a new instance of PCA Computes the PCA of a matrix Rows are SNPs
//     * and columns are individuals
//     *
//     * @param matrix
//     */
//    public PrincipalComponentAnalysis(double[][] matrix) {
//// Add the matrix of genotypes
//        double[][] stDat = Standardize(matrix.length, matrix[0].length, matrix);
//        X = new Matrix(stDat);
//// Transpose the matrix, so now the loci are the columns and the individuals the rows                
//        Xprime = X.transpose();
//    }
//
//    private double[] Evals;
////    private double sumEigenValues = Double.NaN;
////    private double lsquare = Double.NaN;
////    private int m = -1;
////    private int n = -1;
//    private Matrix X = null;
//    private Matrix Xprime = null;
//    private Matrix Evecs = null;
//    private double[][] matrixOfRows = null;
//    private double[] colmeans;
//    private double[] colstdevs;
//
//    public void performEigenAnalysis() {
//        // use Jama matrix class
//
//// Sums of squares and cross-products matrix. The matrix is a numberOfIndividuals X numberOfIndividuals matrix
//// The idea is that the eigenvalues represent number of groups that can be obtained
//        Matrix SSCP = Xprime.times(X);
//
//        // Eigen decomposition
//        EigenvalueDecomposition evaldec = SSCP.eig();
//        Matrix evecs = evaldec.getV();
//        double[] evals = evaldec.getRealEigenvalues();
//
//        // evecs contains the cols. ordered right to left
//        // Evecs is the more natural order with cols. ordered left to right
//        // So to repeat: leftmost col. of Evecs is assoc with largest Evals
//        // Evals and Evecs ordered from left to right
//        double tot = 0.0;
//        for (int j = 0; j < evals.length; j++) {
//            tot += evals[j];
//        }
//
//        // reverse order of evals into Evals
//        Evals = new double[evals.length];
//        for (int j = 0; j < evals.length; j++) {
//            Evals[j] = evals[evals.length - j - 1] / tot;
//        }
//        // reverse order of Matrix evecs into Matrix Evecs
//        double[][] tempold = evecs.getArray();
//        double[][] tempnew = new double[tempold.length][tempold.length];
//        for (int j1 = 0; j1 < tempold.length; j1++) {
//            for (int j2 = 0; j2 < tempold.length; j2++) {
//                tempnew[j1][j2] = tempold[j1][tempold.length - j2 - 1];
//            }
//        }
//        Evecs = new Matrix(tempnew);
//
//        Matrix rowProjections = X.times(Evecs);
//
//        matrixOfRows = rowProjections.getArray();
//    }
//
//    public double[][] getTransformationPCA() {
//        return matrixOfRows;
//    }
//
//    /**
//     * Get the EigenValues (Amount of variation explained for each variable)
//     *
//     * @return
//     */
//    public double[] getEigenValues() {
//        return Evals;
//    }
//
//    /**
//     * Project the variable in the new space
//     *
//     * @param queryArray
//     * @return
//     */
//    public double[] eigenTransformation(double[] queryArray) {
//        double[][] array = new double[1][queryArray.length];
//        for (int e = 0; e < queryArray.length; e++) {
//            array[0][e] = (queryArray[e] - colmeans[e]) / (Math.sqrt((double) X.getRowDimension()) * colstdevs[e]);
//        }
//        Matrix Xtest = new Matrix(array);
//        Matrix rowProjection = Xtest.times(Evecs);
//        return rowProjection.getArray()[0];
//    }
//
//    //-------------------------------------------------------------------
//    /**
//     * Method for standardizing the input data
//     * <p>
//     * Note the formalas used (since these vary between implementations):<br>
//     * reduction: (vect - meanvect)/sqrt(nrow)*colstdev <br>
//     * colstdev: sum_cols ((vect - meanvect)^2/nrow) <br>
//     * if colstdev is close to 0, then set it to 1.
//     *
//     * @param nrow number of rows in input matrix
//     * @param ncol number of columns in input matrix
//     * @param A input matrix values
//     */
//    private double[][] Standardize(int nrow, int ncol, double[][] A) {
//        colmeans = new double[ncol];
//        colstdevs = new double[ncol];
//        // Adat will contain the standardized data and will be returned
//        double[][] Adat = new double[nrow][ncol];
//        double[] tempcol = new double[nrow];
//        double tot;
//
//        // Determine means and standard deviations of variables/columns
//        for (int j = 0; j < ncol; j++) {
//            tot = 0.0;
//            for (int i = 0; i < nrow; i++) {
//                tempcol[i] = A[i][j];
//                tot += tempcol[i];
//            }
//
//            // For this col, det mean
//            colmeans[j] = tot / (double) nrow;
//            for (int i = 0; i < nrow; i++) {
//                colstdevs[j] += Math.pow(tempcol[i] - colmeans[j], 2.0);
//            }
//            colstdevs[j] = Math.sqrt(colstdevs[j] / ((double) nrow));
//            if (colstdevs[j] < 0.0001) {
//                colstdevs[j] = 1.0;
//            }
//        }
//
//        // Now ceter to zero mean, and reduce to unit standard deviation
//        for (int j = 0; j < ncol; j++) {
//            for (int i = 0; i < nrow; i++) {
//                Adat[i][j] = (A[i][j] - colmeans[j])
//                        / (Math.sqrt((double) nrow) * colstdevs[j]);
//            }
//        }
//        return Adat;
//    } // Standardize
//    //-------------------------------------------------------------------
//
//    public static void main(String[] args) {
//        double[][] v = new double[10][3];
//        Random ran = new Random();
//        for (int r = 0; r < v.length; r++) {
//            for (int c = 0; c < v[r].length - 1; c++) {
//                v[r][c] = ran.nextGaussian();
//            }
//            v[r][2] = 3 * v[r][0] + 3 + 0.1 * ran.nextGaussian();
//            System.out.println(java.util.Arrays.toString(v[r]));
//        }
//
//        PrincipalComponentAnalysis pca = new PrincipalComponentAnalysis(v);
//
//        pca.performEigenAnalysis();
//
//        double[][] transf = pca.getTransformationPCA();
//        System.out.println("*********************************");
//        for (int e = 0; e < transf.length; e++) {
//            System.out.println(java.util.Arrays.toString(transf[e]));
//        }
//        System.out.println("*********************************");
//        System.out.println(java.util.Arrays.toString(pca.getEigenValues()));
//        System.out.println("*********************************");
//        double[] trial = new double[3];
//        for (int c = 0; c < trial.length - 1; c++) {
//            trial[c] = ran.nextGaussian();
//        }
//        trial[2] = 3 * trial[0] + 3 + 0.1 * ran.nextGaussian();
//        double [] transfTrial = pca.eigenTransformation(trial);
//        System.out.println(java.util.Arrays.toString(trial));
//        System.out.println(java.util.Arrays.toString(transfTrial));
//    }
//}
