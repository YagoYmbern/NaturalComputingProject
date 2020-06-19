/*
* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc;

import nc.solution.Solution;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import nc.function.optimize.FunctionToOptimize;
import nc.strategyToAscertainOffspring.brood_competition.BroodCompetition;
import nc.strategyToAscertainParentsToMate.mate.ReproductiveSystem;
import nc.strategyToAscertainParentsToMate.Couple;
import nc.strategyToAscertainParentsToMate.Brood;
import nc.strategyToCombineParentsAndOffspring.StrategyToCombineParentsAndOffspring;
import nc.strategyToGenerateSolutionsFromParents.Procreation;

/**
 * An algorithm to optimize a problem
 *
 * @author Oscar Lao
 */
public class Optimizer extends ArrayList<Solution> implements Comparable<Optimizer> {

//    // Total number of variables to estimate
//    private final int n;
    // Function to optimize
    protected FunctionToOptimize fto;
    private ReproductiveSystem mas;
    private Procreation rs;
    private StrategyToCombineParentsAndOffspring scp;
    private BroodCompetition sta;
//    private StrategyToDecideHowGoodIsAnOffspringComparedToItsParents off_comp_parents;

    /**
     * Generate a new optimizer.To work, an optimizer requires
     *
     * @param fto function to optimize. The Solutions will code in a vector the
     * parameters of the function to optimize
     * @param mas the way how parents are ascertained from the pool of solutions
     * to create new solutions
     * @param rs the way how the parents combine to generate new solutions
     * @param sta the strategy how offspring from the same couple compete
     * between them
     * @param scp the strategy to decide how many parents and offspring are
     * retained in the next generation
     */
    public Optimizer(FunctionToOptimize fto, ReproductiveSystem mas, Procreation rs, BroodCompetition sta, StrategyToCombineParentsAndOffspring scp) {
//        n = fto.size();
        // Standard deviation and mean of each variable
//        sd_of_each_variable = new double[n];
//        mean_of_each_variable = new double[n];
        this.fto = fto;
        // Add the optimizer to the strategy to update the solutions
        this.mas = mas;
        this.rs = rs;
        this.scp = scp;
        this.sta = sta;
        initialize();
    }

    @Override
    public boolean add(Solution s)
    {
        s.setO(this);
        return super.add(s);
    }
    
    /**
     * Set the strategy of competition between offspring of the same couple
     *
     * @param sta
     */
    public void setSta(BroodCompetition sta) {
        this.sta = sta;
    }

    /**
     * Set the strategy to combine parents
     *
     * @param scp
     */
    public void setScp(StrategyToCombineParentsAndOffspring scp) {
        this.scp = scp;
    }

    /**
     * Set the mating strategy
     *
     * @param mas
     */
    public void setMas(ReproductiveSystem mas) {
        this.mas = mas;
    }

    /**
     * Set the recombination strategy
     *
     * @param rs
     */
    public void setRs(Procreation rs) {
        this.rs = rs;
    }

//    /**
//     * Set the strategy to compare offspring with parents
//     *
//     * @param off_comp_parents
//     */
//    public void setOff_comp_parents(StrategyToDecideHowGoodIsAnOffspringComparedToItsParents off_comp_parents) {
//        this.off_comp_parents = off_comp_parents;
//    }
//
//    /**
//     * Get the strategy to compare offspring with parents
//     *
//     * @return
//     */
//    public StrategyToDecideHowGoodIsAnOffspringComparedToItsParents getOff_comp_parents() {
//        return off_comp_parents;
//    }
    /**
     * Get the mating strategy
     *
     * @return
     */
    public ReproductiveSystem getMas() {
        return mas;
    }

    /**
     * Get the recombination strategy
     *
     * @return
     */
    public Procreation getRs() {
        return rs;
    }

    /**
     * Get the strategy to combine parents and offspring
     *
     * @return
     */
    public StrategyToCombineParentsAndOffspring getScp() {
        return scp;
    }

    /**
     * get the strategy of competition between brood of a couple
     *
     * @return
     */
    public BroodCompetition getSta() {
        return sta;
    }

    /**
     * Initialize the population of solutions
     *
     * @param size
     */
    private void initialize() {
// Populate the solutions        
        for (int e = 0; e < scp.getSize_of_solutions(); e++) {
            // Generate a copy
            Solution s = fto.generateSolution();
            // link the solution to this optimizer
            s.setO(this);
            add(s);
        }
        computeFitnessOfSolutions();
        System.out.println("Initialized!");
    }

    /**
     * Compute the fitness of solutions
     */
    public void computeFitnessOfSolutions() {
// Compute fitness of this initial population. Use a multithread approach
        ExecutorService executor_fitness = Executors.newFixedThreadPool(this.size());
        this.forEach((o) -> {
            executor_fitness.execute(o);
        });
        executor_fitness.shutdown();
        while (!executor_fitness.isTerminated()) {
        }
        // Sort by fitness
        Collections.sort(this);
    }

    /**
     * Do a new iteration. At each iteration, update the population according to
     * the different strategy algorithms (StrategyToUpdateSolution,
     * StrategyToGenerateSolutionsGivenParents, etc) and compute the standard
     * error of each variable (how variable is each of the variables to
     * estimate)
     */
    public void nextIteration() {
        // Propose a new population of solutions
        proposeNextPopulation();
    }

    /**
     * Reset the fitness of each solution, so it has to be recomputed again
     */
    public void resetFitness() {
        this.forEach((s) -> {
            s.resetFitness();
        });
    }

    /**
     * Propose a new population
     */
    protected void proposeNextPopulation() {
// Generate the parental units (can be more than one parent at each parental unit. Recombination must know how to work with the parental units)
        ArrayList<Couple> sols_to_mate = getMas().generate_couples(this);
        ArrayList<Brood> offsprings_by_couple = new ArrayList<>();
// new population of answers. c are the couples that mate to generate the offspring
        int size_of_brood = 0;
        for (int c = 0; c < sols_to_mate.size(); c++) {
// offsprings of this couple            
            Brood o = getRs().getBrood(sols_to_mate.get(c));
            size_of_brood += o.size();
// add the offsprings to the list            
            offsprings_by_couple.add(o);
        }
// Compute fitness of the solutions. Use a multithread approach
        ExecutorService executor_fitness = Executors.newFixedThreadPool(size_of_brood);
        offsprings_by_couple.forEach((offsprings) -> {
            offsprings.forEach((o) -> {
                executor_fitness.execute(o);
            });
        });

        executor_fitness.shutdown();
        while (!executor_fitness.isTerminated()) {
        }
// Final survivors in the population
        ArrayList<Solution> offsprings = new ArrayList<>();
        offsprings_by_couple.forEach((Brood of) -> {
// Decide if the offspring compared to the parents of the couple is good enough
//            for (int i = 0; i < of.size(); i++) {
//                // Solution after comparing the offspring with the parents
//                Solution off_compared = off_comp_parents.compareToParents(of.getParents(), of.get(i));
//                // This solution replaces the offspring
//                of.set(i, off_compared);
//            }
// Do competition of the brood of the same couple. Local intensive search            
            offsprings.addAll(sta.getSurvivorBrood(of));
        });

// THIS IS WRONG. THE FINAL SELECTION SHOULD BE DONE AT THE LEVEL OF THE METAOPTIMIZER       
        
// Compute the final list of solutions
        ArrayList<Solution> f = scp.ascertain_members(this, offsprings);
// Replace the solutions with the new population
        this.clear();
// f is the new generation of solutions
        this.addAll(f);
    }

    
    
        /**
     * Propose a new generation
     * @return the offspring of this generation
     */
    public ArrayList<Solution> generateOffspring() {
// Generate the parental units (can be more than one parent at each parental unit. Recombination must know how to work with the parental units)
        ArrayList<Couple> sols_to_mate = getMas().generate_couples(this);
        ArrayList<Brood> offsprings_by_couple = new ArrayList<>();
// new population of answers. c are the couples that mate to generate the offspring
        int size_of_brood = 0;
        for (int c = 0; c < sols_to_mate.size(); c++) {
// offsprings of this couple            
            Brood o = getRs().getBrood(sols_to_mate.get(c));
            size_of_brood += o.size();
// add the offsprings to the list            
            offsprings_by_couple.add(o);
        }
// Compute fitness of the solutions. Use a multithread approach
        ExecutorService executor_fitness = Executors.newFixedThreadPool(size_of_brood);
        offsprings_by_couple.forEach((offsprings) -> {
            offsprings.forEach((o) -> {
                executor_fitness.execute(o);
            });
        });

        executor_fitness.shutdown();
        while (!executor_fitness.isTerminated()) {
        }
// Final survivors in the population
        ArrayList<Solution> offsprings = new ArrayList<>();
        offsprings_by_couple.forEach((Brood of) -> {
// Decide if the offspring compared to the parents of the couple is good enough
//            for (int i = 0; i < of.size(); i++) {
//                // Solution after comparing the offspring with the parents
//                Solution off_compared = off_comp_parents.compareToParents(of.getParents(), of.get(i));
//                // This solution replaces the offspring
//                of.set(i, off_compared);
//            }
// Do competition of the brood of the same couple. Local intensive search            
            offsprings.addAll(sta.getSurvivorBrood(of));
        });        
        return offsprings;
    }
    
    /**
     * Get the function to optimize
     *
     * @return
     */
    public FunctionToOptimize getFto() {
        return fto;
    }

    /**
     * Get the best solution (the one that minimizes/maximizes the problem). The
     * population must be sorted first. If the optimizer is empty. then return null
     * @return
     */
    public Solution get_Best_Solution() {
        return (this.isEmpty())? null:this.get(0);
    }

    /**
     * Compare the optimizer by the best solution
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Optimizer o) {
        return get_Best_Solution().compareTo(o.get_Best_Solution());
    }
}
