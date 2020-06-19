/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToExchangeSolutionsAmongOptimizers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import nc.MetaOptimizer;
import nc.Optimizer;
import nc.solution.Solution;

/**
 * Define a strategy to exchange solutions between optimizers based on shuffling at random some individuals from each optimizer
 * @author olao
 */
public class ExchangeSolutionsBetweenOptimizersAtRandom extends StrategyToExchangeSolutionsAmongOptimizers {

    public ExchangeSolutionsBetweenOptimizersAtRandom(int number_of_solutions_to_exchange_by_Optimizer) {
        this.number_of_solutions_to_exchange_by_Optimizer = number_of_solutions_to_exchange_by_Optimizer;
    }

    private int number_of_solutions_to_exchange_by_Optimizer;

    /**
     * Set the number of solutions to exchange by optimizer
     *
     * @param number_of_solutions_to_exchange_by_Optimizer
     */
    public void setNumber_of_solutions_to_exchange_by_Optimizer(int number_of_solutions_to_exchange_by_Optimizer) {
        this.number_of_solutions_to_exchange_by_Optimizer = number_of_solutions_to_exchange_by_Optimizer;
    }

    /**
     * Get the number of solutions that are exchanged between optimizers
     *
     * @return
     */
    public int getNumber_of_solutions_to_exchange_by_Optimizer() {
        return number_of_solutions_to_exchange_by_Optimizer;
    }

    @Override
    public void exchangeSolutions(MetaOptimizer mo) {        
        ArrayList<Solution> ascertained_sols = new ArrayList<>();
        // for each optimizer, pick number_of_solutions_to_exchange_by_optimizer
        for(int s = 0; s < mo.size(); s++)
        {
            Optimizer o = mo.getOptimizer(s);
            o.nextIteration();
            for (int i = 0; i < number_of_solutions_to_exchange_by_Optimizer; i++) {
                int id = ThreadLocalRandom.current().nextInt(o.size());
                // add the ascertained solutions to ascertained_sols
                ascertained_sols.add(o.remove(id));
            }            
        }

        // Shuffle the ascertained sols at random
        Collections.shuffle(ascertained_sols);
        
        for(int s = 0; s < mo.size(); s++)
        {
            Optimizer o = mo.getOptimizer(s);
        // add number_of_solutions_to_exchange_by_optimizer to each optimizer            
            for (int i = 0; i < number_of_solutions_to_exchange_by_Optimizer; i++) {
                // add the ascertained solutions to ascertained_sols
                Solution sol = ascertained_sols.get(i);
                o.add(sol);
                sol.setO(o);
            }            
            Collections.sort(o);
        }
    }
}
