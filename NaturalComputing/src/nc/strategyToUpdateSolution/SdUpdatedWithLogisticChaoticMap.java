///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nc.strategyToUpdateSolution;
//
//import maths.RandomUniformWithoutResampling;
//import nc.Solution;
//import nc.Variable;
//
///**
// *
// * @author olao
// */
//public class SdUpdatedWithLogisticChaoticMap extends StrategyToUpdateSolution {
//
//    private final double n;
//
//    /**
//     * Apply a Sd updated with Chaotic Map (according to Misaghi et al, Journal of Computational Design and Engineering 6 (2019) 284-295
//     *
//     * @param percentage_of_mutation the number of variables that are going to be updated.
//     * @param sd_final the final standard deviation to do the search
//     * @param T the total number of iterations
//     * @param n a power to use when computing sd of current time. sdt = Math.pow((T-t)/T,n)*(sdinitial - sdfinal) + sdfinal*z(t)
//     * where z(t) comes from the learning rate of the variable and must range between 0 and 1 in order to do a logistic chaotic map
//     * @param cm a chaotic map to update the standard deviation given the previous value
//     */
//    public SdUpdatedWithLogisticChaoticMap(double percentage_of_mutation, double sd_final, int T, double n, ChaoticMap cm) {
//        this.percentage_of_mutation = percentage_of_mutation;
//        this.sd_final = sd_final;
//        this.T = T;
//        this.n = n;
//        this.cm = cm;                
//    }
//    private double percentage_of_mutation, sd_final;
//    private final int T;
//    private ChaoticMap cm;
//
//    /**
//     * Set the percentage of elements that are going to be mutated
//     *
//     * @param percentage_of_mutation
//     */
//    public void setPercentage_of_mutation(double percentage_of_mutation) {
//        this.percentage_of_mutation = percentage_of_mutation;
//    }
//
//    /**
//     * Set the chaotic map
//     * @param cm 
//     */
//    public void setCm(ChaoticMap cm) {
//        this.cm = cm;
//    }
//
//    /**
//     * Get the chaotic map
//     * @return 
//     */
//    public ChaoticMap getCm() {
//        return cm;
//    }        
//
//    /**
//     * Update the values of the solution a following a random walk
//     *
//     * @param a
//     */
//    @Override
//    protected void apply_update(Solution a) {
//        // how many variables to use?
//        int n_v = (int) (a.size() * percentage_of_mutation);
//        // variables to sample
//        RandomUniformWithoutResampling sampleValues = new RandomUniformWithoutResampling(0, a.size());
//        int[] s = sampleValues.sample(n_v);
//        for (int i = 0; i < n_v; i++) {
//// which variable
//            int v = s[i];
//// new standard deviation is updated
//            Variable var = a.getVariable(v);
//            var.getLearning_rate().setV(cm.getValue_t1(var.getLearning_rate().getV()));
//            double n_sd = Math.pow((T-o.getN_iterations())/T,n) *(var.getSd_movement().getV() - sd_final) + sd_final*var.getLearning_rate().getV();
//            var.getSd_movement().setV(n_sd);
//// the solution is updated using the formula 5 in page 286 of Journal of Computational Design and Engineering 6 (2019) 284-295  
//            double nwv = var.getValue().getV() * var.getSd_movement().getV() + (o.get_Best_Solution().getVariable(v).getValue().getV() - var.getValue().getV());
//            var.getValue().setV(nwv);
//        }
//    }
//}