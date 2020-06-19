///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nc.strategyToUpdateSolution;
//
//import java.util.concurrent.ThreadLocalRandom;
//import nc.function.Solution;
//import nc.Variable;
//
///**
// * Class to implement an update of the solution based on a Brownian walk in
// * which the standard error of the normal distribution is updated at each
// * generation in order to optimize the probability of acceptance of the proposed
// * solution
// *
// * @author olao
// */
//public class N_Step_Size_SelfAdaptation_Update extends StrategyToUpdateSolution {
//
//    /**
//     * Apply a self-adaptation algorithm to the mutation search (page 77 in
//     * Natural Computing)
//     *
//     * @param percentage_of_mutation the number of variables that are going to
//     * be updated.
//     * @param learning_rate from Natural Computing, typically the learning rate
//     * is inverse to the root of the size of the problem (2*(n)^0.5)^-0.5
//     */
//    public N_Step_Size_SelfAdaptation_Update(double percentage_of_mutation, double learning_rate) {
//        super(percentage_of_mutation);
//        this.learning_rate = learning_rate;
//    }
//
//    private double learning_rate;
//
//    /**
//     * Set the overall learning rate
//     *
//     * @param learning_rate
//     */
//    public void setLearning_rate(double learning_rate) {
//        this.learning_rate = learning_rate;
//    }
//
//    /**
//     * Update the values of the solution a following a random walk updated by a learning process
//     *
//     * @param a
//     * @param v
//     */
//    @Override
//    protected void apply_update(Solution a, int v) {
//// propose a jump in the standard deviation of the gaussian distribution
//        double jump_r = ThreadLocalRandom.current().nextGaussian();
//        double jump_rprima = ThreadLocalRandom.current().nextGaussian();
//// new standard deviation is updated
//        Variable var = a.getVariable(v);
//        double sd = var.getSta().getSd_movement().getV();
//        double learn = var.getSta().getLearning_rate().getV();
//        double n_sd = sd * Math.exp(jump_rprima * learn + learning_rate * jump_r);
//        var.getSta().getSd_movement().setV(n_sd);
//// the solution is updated   
//        double nwv = var.getValue().getV() + ThreadLocalRandom.current().nextGaussian() * var.getSta().getSd_movement().getV();
//        var.getValue().setV(nwv);
//    }
//}
