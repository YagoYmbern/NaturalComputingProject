///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nc.evolution.common;
//
//import nc.strategyToGenerateSolutionsFromParents.StrategyToGenerateSolutionsGivenParents;
//import java.util.ArrayList;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import nc.strategyToAscertainParentsToMate.StrategyToAscertainParentsOfCouple;
//import nc.Optimizer;
//import nc.function.optimize.FunctionToOptimize;
//import nc.solution.Solution;
//import nc.strategyToAscertainOffspring.StrategyOfCompetitionBetweenOfsspringOfSameCouple;
//import nc.strategyToCombineParentsAndOffspring.StrategyToCombineParentsAndOffspring;
//import nc.strategyToAscertainParentsToMate.couple.Couple;
//import nc.strategyToAscertainParentsToMate.couple.Offspring;
//
///**
// *
// * @author Oscar Lao
// */
//public class OptimizerEvolution extends Optimizer {
//
//    /**
//     * In order to do evolutionary optimization we need to define
//     *
//     * @param fto the function to optimize
//     * @param mas the mating strategy that will generate new solutions
//     * @param rs the strategy to combine the mating solutions to generate new
//     * ones
//     * @param sta
//     * @param scp the strategy to decide which are the parents and offspring
//     * that finally go to the next generation
//     */
//    public OptimizerEvolution(FunctionToOptimize fto, StrategyToAscertainParentsOfCouple mas, StrategyToGenerateSolutionsGivenParents rs, StrategyOfCompetitionBetweenOfsspringOfSameCouple sta, StrategyToCombineParentsAndOffspring scp) {
//        super(fto, scp.getSize_of_solutions());
//        // Add the optimizer to the strategy to update the solutions
//        this.mas = mas;
//        this.rs = rs;
//        this.scp = scp;
//        this.sta = sta;
//    }
//
//    private StrategyToAscertainParentsOfCouple mas;
//    private StrategyToGenerateSolutionsGivenParents rs;
//    private StrategyToCombineParentsAndOffspring scp;
//    private StrategyOfCompetitionBetweenOfsspringOfSameCouple sta;
//
//    /**
//     * Set the strategy of competition between offspring of the same couple
//     * @param sta 
//     */
//    public void setSta(StrategyOfCompetitionBetweenOfsspringOfSameCouple sta) {
//        this.sta = sta;
//    }
//    
//    
//    /**
//     * Set the strategy to combine parents
//     *
//     * @param scp
//     */
//    public void setScp(StrategyToCombineParentsAndOffspring scp) {
//        this.scp = scp;
//    }
//
//    /**
//     * Set the mating strategy
//     *
//     * @param mas
//     */
//    public void setMas(StrategyToAscertainParentsOfCouple mas) {
//        this.mas = mas;
//    }
//
//    /**
//     * Set the recombination strategy
//     *
//     * @param rs
//     */
//    public void setRs(StrategyToGenerateSolutionsGivenParents rs) {
//        this.rs = rs;
//    }
//
//    /**
//     * Get the mating strategy
//     *
//     * @return
//     */
//    public StrategyToAscertainParentsOfCouple getMas() {
//        return mas;
//    }
//
//    /**
//     * Get the recombination strategy
//     *
//     * @return
//     */
//    public StrategyToGenerateSolutionsGivenParents getRs() {
//        return rs;
//    }
//
//    /**
//     * Get the strategy to combine parents and offspring
//     *
//     * @return
//     */
//    public StrategyToCombineParentsAndOffspring getScp() {
//        return scp;
//    }
//
//    /**
//     * get the strategy of competition between offspring of the same couple
//     * @return 
//     */
//    public StrategyOfCompetitionBetweenOfsspringOfSameCouple getSta() {
//        return sta;
//    }
//    
//    
//
//    @Override
//    protected void proposeNextPopulation() {
//// Generate the parental units (can be more than one parent at each parental unit. Recombination must know how to work with the parental units)
//        ArrayList<Couple> sols_to_mate = getMas().mate(this);
//        ArrayList<Offspring> offsprings_by_couple = new ArrayList<>();
//// new population of answers. c are the couples that mate to generate the offspring
//        int n = 0;
//        for(int c=0;c<sols_to_mate.size();c++){
//// offsprings of this couple            
//            Offspring o = getRs().generateSolutionsFromParents(sols_to_mate.get(c));
//// add the offsprings to the list            
//            offsprings_by_couple.add(o);
//            n += o.size();
//        }        
//// Compute fitness of the solutions. Use a multithread approach
//        ExecutorService executor_fitness = Executors.newFixedThreadPool(n);
//        offsprings_by_couple.forEach((offsprings) -> {
//            offsprings.forEach((o) -> {
//                executor_fitness.execute(o);
//            });
//        });
//
//        executor_fitness.shutdown();
//        while (!executor_fitness.isTerminated()) {
//        }
//// Final survivors in the population
//        ArrayList<Solution> offsprings = new ArrayList<>();        
//        offsprings_by_couple.forEach((of) -> {
//// Do competition between solutions within the same couple. Local intensive search            
//            offsprings.addAll(sta.getSurvivorOffspring(of));
//        });    
//        
//// Compute the final list of solutions
//        ArrayList<Solution> f = scp.ascertain_members(this, offsprings);
//// Replace the solutions with the new population
//        this.clear();
//// f is the new generation of solutions
//        this.addAll(f);
//    }
//}
