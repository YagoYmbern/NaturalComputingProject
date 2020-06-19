///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nc.strategyToAscertainOffspring.offspring_to_parents_competition;
//
//import java.util.concurrent.ThreadLocalRandom;
//import nc.solution.Solution;
//import nc.strategyToAscertainParentsToMate.Couple;
//
///**
// *
// * @author Oscar Lao
// */
//public class ProposedOffSpringIsComparedtoItsParentsByFNNComplexity extends StrategyToDecideHowGoodIsAnOffspringComparedToItsParents {
//
//    public ProposedOffSpringIsComparedtoItsParentsByFNNComplexity() {
//    }
//
//    /**
//     * Compare the offspring to the parents by means of counting the number of synapses that have in their FNN chromosomes
//     *
//     * @param parents
//     * @param offspring
//     * @return
//     */
//    @Override
//    public Solution compareToParents(Couple parents, Solution offspring) {
//        // Current iteration of the optimizer
//        int iteration = offspring.getO().getFto().getT().get_current_iteration();
//        // Current temperature
//        double T = Math.pow(alpha, iteration) * t;
//        // Average fitness of the parents        
//        double f_p = parents.getFitness();
//        // Fitness of the offspring
//        double f_o = offspring.getFitness();
//        // If the offspring has a better fitness that the average of the parents, then return it as solution
//        if (f_o <= f_p) {
//            return offspring;
//        }
//        // otherwise we have a problem. The offspring is worse than the average of the parents. Accept it in probability using a simulated annealing approach.
//        // if the offspring is not accepted, then pick one of the parents at random and return it as offspring.
//        double p = Math.exp(-(f_o - f_p) / T);
//        // Accept the offspring in probability
//        if (ThreadLocalRandom.current().nextDouble() <= p) {
//            return offspring;
//        }
//        // We do not accept the offspring solution when comparing it with the parents. Pick one of the parents at random and return it as offspring.
//        return parents.get(ThreadLocalRandom.current().nextInt(parents.size())).getSolution().copy();
//    }
//}
