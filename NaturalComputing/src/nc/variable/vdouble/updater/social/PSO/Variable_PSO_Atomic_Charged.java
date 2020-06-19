package nc.variable.vdouble.updater.social.PSO;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nc.strategyToUpdateSolution.variable.PSO;
//
//import java.util.concurrent.ThreadLocalRandom;
//import nc.Optimizer;
//import nc.function.Solution;
//import nc.V;
//
//
///**
// *
// * @author olao
// */
//public class Variable_PSO_Atomic_Charged extends Variable_PSO_Canonical{
//    
//    public Variable_PSO_Atomic_Charged(V value, V speed, HyperParameters_PSO_Atomic_Charged hyp) {
//         super(value, speed, hyp);
//    }     
//
//    /**
//     * Update the solution using a Swarm movement. In probability, the particle
//     * can be charged. If charged, look for other particles charged in
//     * probability and apply the repulsion
//     *
//     * @param o
//     */
//    @Override
//    public void apply_update(Solution a, int layer, int v,  Optimizer o) {
//        // the Momentum
//// Weights to move towards best solution so far in the population or to the best solution found by this particular particle
//        double c1 = hyp.getC1();
//        double c2 = hyp.getC2();
//        double pdist = ((HyperParameters_PSO_Atomic_Charged)hyp).getPdist();
//        double pcoredist = ((HyperParameters_PSO_Atomic_Charged)hyp).getPcoredist();
//        
//        // the Momentum
//        double momentum_Weight = hyp.getMax_momentum_weight() - o.getN_iterations() * (hyp.getMax_momentum_weight() - hyp.getMin_momentum_weight()) / hyp.getMaximum_number_iterations();
//        // repulsion factor. To be computed to each variable for this solution comparing with other solutions of the same population
//        double rij = 0;
//
//        if (ThreadLocalRandom.current().nextDouble() <= ((HyperParameters_PSO_Atomic_Charged)hyp).getProbability_of_being_charged()) {
//// It is a charged particle. Generate the rij vector
//            for (Solution s : o) {
//                // Do not compare with itself (makes no sense)
//                if (s != a) {
//                    // is s a charged particle? If not, it does not matter
//                    if (ThreadLocalRandom.current().nextDouble() <= ((HyperParameters_PSO_Atomic_Charged)hyp).getProbability_of_being_charged()) {
//// number of charged particles in regard to a
//                        double dij = getValue().getV() - s.get(layer).getWeight(v).getValue().getV();
//                        double p = pdist * o.get_sd_of_Variable(v);
//                        // Defined as cosed points dij <=p; p> pcore
//                        if (Math.abs(dij) <= p) {
//                            double variance_of_variable = Math.pow(o.get_sd_of_Variable(v), 2);
//                            double p2core = pcoredist * o.get_sd_of_Variable(v);
//                            // if it is too close, then apply 8.19 in Natural Computing
//                            if (Math.abs(dij) <= p2core) {
//                                rij += dij * variance_of_variable / (p2core * Math.abs(dij));
//                            } else {
//                                // apply formula 8.18
//                                rij += dij * variance_of_variable / Math.pow(Math.abs(dij), 3);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        // Throw a random number between 0 and 1
//        double r1 = ThreadLocalRandom.current().nextDouble();
//        double r2 = ThreadLocalRandom.current().nextDouble();
//        // The update of the speed has two elements: the movement towards the best proposed value so far given c1
//        double speed1 = c1 * r1 * (a.bestProposedSolutionFar().get(layer).getWeight(v).getValue().getV() - getValue().getV());
//        // The update of the speed given c2
//        double speed2 = c2 * r2 * (o.get_Best_Solution().get(layer).getWeight(v).getValue().getV() - getValue().getV());
//        getSpeed().setV(momentum_Weight * getSpeed().getV() + speed1 + speed2 + rij);
//        // update the values with the new speed
//        getValue().setV(getValue().getV() + getSpeed().getV());
//    }    
//}
