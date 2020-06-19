/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc;

import java.util.ArrayList;
import nc.function.optimize.FunctionToOptimize;
import nc.solution.Solution;
import nc.strategyToAscertainOffspring.brood_competition.BroodCompetition;
import nc.strategyToAscertainParentsToMate.mate.ReproductiveSystem;
import nc.strategyToCombineParentsAndOffspring.StrategyToCombineParentsAndOffspring;
import nc.strategyToExchangeSolutionsAmongOptimizers.StrategyToExchangeSolutionsAmongOptimizers;
import nc.strategyToGenerateSolutionsFromParents.Procreation;

/**
 * A compendium of Optimizers, that exchange migrants according to a migrant
 * rule
 *
 * @author olao
 */
public class MetaOptimizer {

    private StrategyToExchangeSolutionsAmongOptimizers stesao;
    private final ArrayList<Optimizer> optimizers = new ArrayList<>();
    private final FunctionToOptimize fto;

    /**
     * Create a compendium of optimizers for the function to optimize fto.
     * Exchange solutions at each generation using the stesao
     *
     * @param fto
     * @param stesao
     */
    public MetaOptimizer(FunctionToOptimize fto, StrategyToExchangeSolutionsAmongOptimizers stesao) {
        this.fto = fto;
        this.stesao = stesao;
    }

//    /**
//     * Add a new optimizer to solve the function to optimize
//     *
//     * @param mas
//     * @param rs
//     * @param off_comp_parents
//     * @param sta
//     * @param scp
//     * @return
//     */
//    public boolean add_optimizer(StrategyToAscertainParentsOfCouple mas, Procreation rs, StrategyToDecideHowGoodIsAnOffspringComparedToItsParents off_comp_parents, StrategyOfCompetitionWithinBrood sta, StrategyToCombineParentsAndOffspring scp) {
//        return optimizers.add(new Optimizer(fto, mas, rs, off_comp_parents, sta, scp));
//    }        
    /**
     * Add a new optimizer to solve the function to optimize
     *
     * @param mas
     * @param rs
     * @param sta
     * @param scp
     * @return
     */
    public boolean add_optimizer(ReproductiveSystem mas, Procreation rs, BroodCompetition sta, StrategyToCombineParentsAndOffspring scp) {
        return optimizers.add(new Optimizer(fto, mas, rs, sta, scp));
    }

    /**
     * Get the optimizer at position o
     *
     * @param o
     * @return
     */
    public Optimizer getOptimizer(int o) {
        return optimizers.get(o);
    }

    /**
     * Get the number of optimizers considered in this metaOptimizer
     *
     * @return
     */
    public int size() {
        return optimizers.size();
    }

    /**
     * get the strategy to exchange solutions among optimizers
     *
     * @return
     */
    public StrategyToExchangeSolutionsAmongOptimizers getStesao() {
        return stesao;
    }

    /**
     * set the strategy to exchange solutions among optimizers
     *
     * @param stesao
     */
    public void setStesao(StrategyToExchangeSolutionsAmongOptimizers stesao) {
        this.stesao = stesao;
    }

    /**
     * Do next iteration
     */
    public void nextIteration() {
//        optimizers.forEach((o) -> {
//            o.resetFitness();
//            // Compute the fitness with the new batch
//            o.computeFitnessOfSolutions();
//        });
        // Update the input-output so in case we are running in batch, all the optimizers use the same batch
        boolean batch = fto.update_input_output();
        // If we have generated a new batch, the fitness must be recomputed
        if (batch) {
            // Reset the fitness of each solution at each optimizer. Re-compute the fitness in the population of answers
            optimizers.forEach((o) -> {
                o.resetFitness();
                // Compute the fitness with the new batch
                o.computeFitnessOfSolutions();
            });
        }
        // Exchange the solutions between Optimizers following the strategy to exchange solutions among optimizers
        stesao.exchangeSolutions(this);
        // Update the iteration
        fto.getT().update_number_iterations();
    }

    /**
     * Get the best solution (the one that minimizes/maximizes the problem) over
     * all the optimizers. The population must be sorted first
     *
     * @return
     */
    public Solution get_Best_Solution() {
        // Get the best solution of the first optimizer
        Solution s = optimizers.get(0).get_Best_Solution();
        // Iterate over all the optimizers. Pick the best solution among all them
        for (int e = 1; e < optimizers.size(); e++) {
            if (s.compareTo(optimizers.get(e).get_Best_Solution()) > 0) {
                s = optimizers.get(e).get_Best_Solution();
            }
        }
        return s;
    }
}
