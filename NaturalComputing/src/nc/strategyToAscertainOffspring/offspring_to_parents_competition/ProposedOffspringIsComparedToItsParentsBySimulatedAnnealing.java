/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToAscertainOffspring.offspring_to_parents_competition;

import java.util.concurrent.ThreadLocalRandom;
import nc.solution.Solution;
import nc.strategyToAscertainParentsToMate.Couple;

/**
 * A class that models the comparison between offspring and parents. The
 * offspring is better than parents if its fitness is smaller than the combined
 * of the parents. If it is bigger, then it is better in probability following a
 * simulated annealing approach
 *
 * @author Oscar Lao
 */
public class ProposedOffspringIsComparedToItsParentsBySimulatedAnnealing extends StrategyToDecideHowGoodIsAnOffspringComparedToItsParents {
// initial temperature

    private final double t;
// alpha cooling (between 0 and 1)
    private final double alpha;

    /**
     * Create a new object that uses a simulated annealing approach to compare
     * the parental solution with the offspring.
     *
     * @param t is the temperature
     * @param alpha From Natural Computing "The slower the rate of cooling, the
     * more likely the algorithm is to find a good quality solution. However,
     * slower cooling schedules also increase the algorithm’s run time. A common
     * cooling schedule is exponential cooling"
     */
    public ProposedOffspringIsComparedToItsParentsBySimulatedAnnealing(double t, double alpha) {
        this.t = t;
        this.alpha = alpha;
    }

    /**
     * Compare the offspring to the parents given a simulated annealing
     * strategy. Compare the fitness of the offspring with the combined fitness
     * of the parents If the fitness of the offspring is smaller, then accept it
     * as it is. If it is larger than the one from the parents, then decide in
     * probability to accept it. If not accepted, return a copy of one of the
     * parents as offspring solution.
     *
     * @param parents
     * @param offspring
     * @return
     */
    @Override
    public Solution compareToParents(Couple parents, Solution offspring) {
        // Current iteration of the optimizer
        int iteration = offspring.getO().getFto().getT().get_current_iteration();
        // Current temperature
        double T = Math.pow(alpha, iteration) * t;
        // Average fitness of the parents        
        double f_p = parents.getFitness();
        // Fitness of the offspring
        double f_o = offspring.getFitness();
        // If the offspring has a better fitness that the average of the parents, then return it as solution
        if (f_o <= f_p) {
            return offspring;
        }
        // otherwise we have a problem. The offspring is worse than the average of the parents. Accept it in probability using a simulated annealing approach.
        // if the offspring is not accepted, then pick one of the parents at random and return it as offspring.
        double p = Math.exp(-(f_o - f_p) / T);
        // Accept the offspring in probability
        if (ThreadLocalRandom.current().nextDouble() <= p) {
            return offspring;
        }
        // We do not accept the offspring solution when comparing it with the parents. Pick one of the parents at random and return it as offspring.
        return parents.get(ThreadLocalRandom.current().nextInt(parents.size())).getSolution().copy();
    }
}
