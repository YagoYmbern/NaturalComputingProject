///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nc.strategyToUpdateSolution;
//
//import java.util.concurrent.ThreadLocalRandom;
//import nc.function.Solution;
//
///**
// *
// * @author olao
// */
//public class ParticleSwarmOptimizationMovementAtomicCharged extends StrategyToUpdateSolution {
//
//    /**
//     * Generate a new movement of Swarm type.Low values for c1 and c2 encourage
//     * particles to explore far away from already uncovered good points as there
//     * is less emphasis on past learning.High values of these parameters
//     * encourage more intensive search of regions close to these.Exploration is
//     * encouraged by selecting a high value of W = wmax -
//     * itercurr*(wmax-wmin)/itermax relative to the values of c1 and c2
//     * points.(Extracted from Natural Computing)
//     *
//     * @param probability_of_mutation
//     * @param c1 is the weight coefficient that controls the velocity of best
//     * solution associated to this object
//     * @param c2 is the weight coefficient that controls the velocity of best
//     * solution associated to the whole population
//     * @param min_momentum_weight the minimum momentum weight to multiply. For
//     * example 0.4 current speed
//     * @param max_momentum_weight the maximum momentum weight to multiply. For
//     * example 0.9 current speed
//     * @param maximum_number_iterations maximum number of iterations that our
//     * algorithm is running
//     * @param probability_of_being_charged the probability that a particle is
//     * charged. Is set to 0, then classical Canonical is done
//     * @param pdist the maximum distance with respect to the standard deviation
//     * of the variable at which two particles are charged. pdist must be
//     * positive. For example, a pdist of 1 means that any distance that is
//     * greater than the standard deviation will be considered as not related.
//     * Not used if probability_of_being_charged = 0
//     * @param pcoredist the minimum distance with respect to the standard
//     * deviation of the variable at which two particles that are charged will
//     * use formula 8.18 in Natural Computing and start using formula 8.19.
//     * pcoredist must be positive and smaller than pdist. For example, a
//     * pcoredist of 0.1 means that a distance at a variable between two
//     * solutions smaller than 0.1*standard deviation will follow formula 8.19.
//     * Not used if probability_of_being_charged = 0
//     */
//    public ParticleSwarmOptimizationMovementAtomicCharged(double probability_of_mutation, double c1, double c2, double min_momentum_weight, double max_momentum_weight, int maximum_number_iterations, double probability_of_being_charged, double pdist, double pcoredist) {
//        super(probability_of_mutation);
//        this.c1 = c1;
//        this.c2 = c2;
//        this.maximum_number_iterations = maximum_number_iterations;
//        this.min_momentum_weight = max_momentum_weight;
//        this.max_momentum_weight = max_momentum_weight;
//        this.probability_of_being_charged = probability_of_being_charged;
//        this.pdist = pdist;
//        this.pcoredist = pcoredist;
//    }
//
//    private double probability_of_being_charged, pdist, pcoredist;
//    protected double c1, c2;
//    protected final double min_momentum_weight, max_momentum_weight;
//    protected final int maximum_number_iterations;
//
//    /**
//     * Set the probability of a solution being charged
//     *
//     * @param probability_of_being_charged
//     */
//    public void setProbability_of_being_charged(double probability_of_being_charged) {
//        this.probability_of_being_charged = probability_of_being_charged;
//    }
//
//    /**
//     * Get the probability of a solution being charged
//     *
//     * @return
//     */
//    public double getProbability_of_being_charged() {
//        return probability_of_being_charged;
//    }
//
//    /**
//     * Set the weight of how close we must go away from the best solution that
//     * this solution has found so far
//     *
//     * @param c1
//     */
//    public void setC1(double c1) {
//        this.c1 = c1;
//    }
//
//    /**
//     * Set the weight of how close we must go to the best solution found in the
//     * population
//     *
//     * @param c2
//     */
//    public void setC2(double c2) {
//        this.c2 = c2;
//    }
//
//    /**
//     * Update the solution using a Swarm movement. In probability, the particle
//     * can be charged. If charged, look for other particles charged in
//     * probability and apply the repulsion
//     *
//     * @param a
//     */
//    @Override
//    public void apply_update(Solution a, int v) {
//        // the Momentum
//        double momentum_Weight = max_momentum_weight - o.getN_iterations() * (max_momentum_weight - min_momentum_weight) / maximum_number_iterations;
//// repulsion factor. To be computed to each variable for this solution comparing with other solutions of the same population
//        double[] rij = new double[a.size()];
//
//        if (ThreadLocalRandom.current().nextDouble() <= probability_of_being_charged) {
//// It is a charged particle. Generate the rij vector
//            for (Solution s : o) {
//                // Do not compare with itself (makes no sense)
//                if (s != a) {
//                    // is s a charged particle? If not, it does not matter
//                    if (ThreadLocalRandom.current().nextDouble() <= probability_of_being_charged) {
//// number of charged particles in regard to a
//                        double dij = a.getVariable(v).getValue().getV() - s.getVariable(v).getValue().getV();
//                        double p = pdist * o.get_sd_of_Variable(v);
//                        // Defined as cosed points dij <=p; p> pcore
//                        if (Math.abs(dij) <= p) {
//                            double variance_of_variable = Math.pow(o.get_sd_of_Variable(v), 2);
//                            double p2core = pcoredist * o.get_sd_of_Variable(v);
//                            // if it is too close, then apply 8.19 in Natural Computing
//                            if (Math.abs(dij) <= p2core) {
//                                rij[v] += dij * variance_of_variable / (p2core * Math.abs(dij));
//                            } else {
//                                // apply formula 8.18
//                                rij[v] += dij * variance_of_variable / Math.pow(Math.abs(dij), 3);
//                            }
//                        }
//
//                    }
//                }
//            }
////            // Average repulsion by all the carged particles
////            if (charged > 0) {
////                for (int r = 0; r < rij.length; r++) {
////                    rij[r] /= charged;
////                }
////            }
//        }
//        // Throw a random number between 0 and 1
//        double r1 = ThreadLocalRandom.current().nextDouble();
//        double r2 = ThreadLocalRandom.current().nextDouble();
//        // Throw a random gaussian value centered at 0 with sd determined by the user. 
//        //Do a logit transformation so this becomes a probability. 
//        //Multiply it by 2 so the value ranges between 0 (c1 would not contribute at all, everything would come from c2)
//        //to 2 (everything is c1). The value of 1 produces the cannonical particle swarm optimization
//        // The update of the speed has two elements: the movement towards the best proposed value so far given c1
//        Solution best = a.bestProposedSolutionFar();
//        double speed1 = c1 * r1 * (best.getVariable(v).getValue().getV() - a.getVariable(v).getValue().getV());
//        // The update of the speed given c2
//        double speed2 = c2 * r2 * (o.get_Best_Solution().getVariable(v).getValue().getV() - a.getVariable(v).getValue().getV());
//        a.getVariable(v).getSta().getSpeed().setV(momentum_Weight * a.getVariable(v).getSta().getSpeed().getV() + speed1 + speed2 + rij[v]);
//        // update the values with the new speed
//        a.getVariable(v).getValue().setV(a.getVariable(v).getValue().getV() + a.getVariable(v).getSta().getSpeed().getV());
//
//    }
//}
