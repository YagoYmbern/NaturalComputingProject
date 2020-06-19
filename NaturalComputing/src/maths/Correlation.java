/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maths;

/**
 *
 * @author Administrator
 */
public class Correlation {

    /**
     * Get the Pearson correlation between the two variables
     * @param scores1
     * @param scores2
     * @return 
     */
    public static double getPearsonCorrelation(double[] scores1, double[] scores2) {
        double sum_sq_x = 0;
        double sum_sq_y = 0;
        double sum_coproduct = 0;
        double mean_x = scores1[0];
        double mean_y = scores2[0];
        for (int i = 2; i < scores1.length + 1; i++) {
            double sweep = (i - 1) / (double)i;
            double delta_x = scores1[i - 1] - mean_x;
            double delta_y = scores2[i - 1] - mean_y;
            sum_sq_x += delta_x * delta_x * sweep;
            sum_sq_y += delta_y * delta_y * sweep;
            sum_coproduct += delta_x * delta_y * sweep;
            mean_x += delta_x / i;
            mean_y += delta_y / i;
        }
        double pop_sd_x = (double) Math.sqrt(sum_sq_x / scores1.length);
        double pop_sd_y = (double) Math.sqrt(sum_sq_y / scores1.length);
        double cov_x_y = sum_coproduct / scores1.length;
        return cov_x_y / (pop_sd_x * pop_sd_y);
    }
    
    /**
     * Return the weighted correlation using weights
     * @param scores1
     * @param scores2
     * @param weight
     * @return 
     */
    public static double getWeightedPearsonCorrelation(double[] scores1, double[] scores2, double [] weight) {
        double mean_x = 0;
        double mean_y = 0;
        double wT = 0;
        for(int e=0;e<weight.length;e++)
        {
            mean_x += weight[e]*scores1[e];
            mean_y += weight[e]*scores2[e];
            wT+= weight[e];
        }
        mean_x/=wT;
        mean_y/=wT;
        double cov_x_y = 0;
        double cov_x_x = 0;
        double cov_y_y = 0;
        
        for(int e=0;e<weight.length;e++)
        {
            cov_x_y += weight[e]*(scores1[e]-mean_x)*(scores2[e]-mean_y);
            cov_x_x += weight[e]*(scores1[e]-mean_x)*(scores1[e]-mean_x);
            cov_y_y += weight[e]*(scores2[e]-mean_y)*(scores2[e]-mean_y);            
        }
// Covariances        
        cov_x_y /= wT;
        cov_x_x /= wT;
        cov_y_y /= wT;

        return cov_x_y / Math.pow(cov_x_x * cov_y_y,0.5);
    }      

    public static void main(String[] args) {
        double [] v1 = {1,2,3,4,5,6,7,8,9,10};
        double [] v2 = {1,2,3,4,5,6,7,8,9,10};
 //        double [][] v1 = {{0.210028981,0.220716478},{0.203134562,0.22071757},{0.205041482,0.219757007},{0.200695452,0.221364503},{0.203507204,0.220672766},{0.201610118,0.222871462},{0.206330976,0.220082659}};
//        double [][] v2 = {{0.219870657,0.219276179},{0.223397094,0.218241306},{0.220821385,0.2193942},{0.221690154,0.218245677},{0.220872747,0.218418338},{0.220360228,0.218150604},{0.222532696,0.220209422},{0.220570044,0.218519967},{0.219059806,0.218653288}};
        System.out.println(Correlation.getPearsonCorrelation(v1, v2));
    }
}
