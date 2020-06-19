/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToAscertainParentsToMate.mate;

import java.util.ArrayList;
import maths.random.RandomMultinomial;
import nc.Optimizer;
import nc.solution.Solution_Weighted;
import nc.strategyToAscertainParentsToMate.Couple;
import nc.strategyToAscertainParentsToMate.couple.CoupleSize;
import nc.strategyToAscertainParentsToMate.fitness.StrategyFitnessCouple;
import nc.strategyToAscertainParentsToMate.weight.StrategyProbabilityParent;

/**
 *
 * @author Oscar Lao
 */
public class ReproductiveSystem {

    private StrategyProbabilityParent f;
    private int number_of_couples;
    private CoupleSize n;
    protected StrategyFitnessCouple fc;    
    
    /**
     * Define a way to generate number_of_couples by using a function f(fitness of couple)
     * @param number_of_couples the number of couples that we are going to generate at a given iteration
     * @param fc the way how the fitness of all the elements of a couple is combined to generate the fitness of the couple
     * @param f the way how each parental solution is compared to other parental solutions in the population
     * @param n a function describing the number of parents that define a couple. 
     */
    public ReproductiveSystem(int number_of_couples, StrategyFitnessCouple fc, StrategyProbabilityParent f, CoupleSize n)
    {
        this.number_of_couples = number_of_couples;
        this.fc = fc;
        this.f = f;
        this.n = n;        
    }

    /**
     * Get the wat how the fitness of the couple is computed
     * @return 
     */
    public StrategyFitnessCouple getFc() {
        return fc;
    }

    /**
     * Set the way how the fitness of the couple is going to be computed
     * @param fc 
     */
    public void setFc(StrategyFitnessCouple fc) {
        this.fc = fc;
    }
     

    /**
     * Set the number of couples that reproduce
     *
     * @param k
     */
    public void setK(int k) {
        this.number_of_couples = k;
    }

    /**
     * Set the number of parents at each parental unit
     *
     * @param n
     */
    public void setN(CoupleSize n) {
        this.n = n;
    }

    /**
     * Set the function to weight
     *
     * @param f
     */
    public void setF(StrategyProbabilityParent f) {
        this.f = f;
    }

    /**
     * Get the function to weight
     *
     * @return
     */
    public StrategyProbabilityParent getF() {
        return f;
    }

    /**
     * Return all the couples to reproduce. Add a weight to each parent
     * depending on its position in the array.
     *
     * @param this_iteration
     * @return
     */

    public ArrayList<Couple> generate_couples(Optimizer this_iteration) {
        // Probability of each solution of being ascertained given its fitness in the population
        double[] p = new double[this_iteration.size()];
        double t = 0;
        // For each element of the optimizer, compute f(fitness)
        for (int e = 0; e < this_iteration.size(); e++) {
            // This is the weight that we give according to the fitness
            p[e] = f.getWeight(this_iteration.get(e));
            t += p[e];
        }
// p must add to 1. Divide by the total        
        for (int e = 0; e < this_iteration.size(); e++) {
            p[e] /= t;
        }
// RandomMultinomial to ascertain for each couple the parents
        RandomMultinomial rm = new RandomMultinomial(p);
// Couples (up to k)
        ArrayList<Couple> mp = new ArrayList<>();
// for each couple, pick the individuals according to a mating strategy
        for (int couple = 0; couple < number_of_couples; couple++) {
            // The new couple
            Couple new_couple = new Couple(fc);
            mp.add(new_couple);
            // This is the number of parents of the new couple
            int number_of_elements_of_couple = n.number_of_parents();
            // It can be that the same individual is taken more than once to do mating
            int[] ids = rm.sample(number_of_elements_of_couple);            
            // add the solution to the couple
            for (int i : ids) {
                new_couple.add(new Solution_Weighted(this_iteration.get(i), this_iteration.get(i).getFitness()));
            }
        }
        return mp;
    }
}

