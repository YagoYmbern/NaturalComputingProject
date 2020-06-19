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
//public class ParticleSwarmOptimizationMovementCanonical extends StrategyToUpdateSolution {
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
//     * @param probability_of_being_mutated
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
//     */
//    public ParticleSwarmOptimizationMovementCanonical(double probability_of_being_mutated, double c1, double c2, double min_momentum_weight, double max_momentum_weight, int maximum_number_iterations) {
//        super(probability_of_being_mutated);
//        this.c1 = c1;
//        this.c2 = c2;
//        this.maximum_number_iterations = maximum_number_iterations;
//        this.min_momentum_weight = max_momentum_weight;
//        this.max_momentum_weight = max_momentum_weight;
//    }
//
//    protected double c1, c2;
//    protected final double min_momentum_weight, max_momentum_weight;
//    protected final int maximum_number_iterations;
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
//     * Update the solution using a Swarm movement.To do the Swarm movement,
// take the best chromosome so far, the speed of the particle, update.
//     *
//     * @param a
//     * @param v
//     */
//    @Override
//    protected void apply_update(Solution a, int v) {
//        // the Momentum
//        double momentum_Weight = max_momentum_weight - o.getN_iterations() * (max_momentum_weight - min_momentum_weight) / maximum_number_iterations;
//        // Update speed
//        // Throw a random number between 0 and 1
//        double r1 = ThreadLocalRandom.current().nextDouble();
//        double r2 = ThreadLocalRandom.current().nextDouble();
//        // The update of the speed has two elements: the movement towards the best proposed value so far given c1
//        double speed1 = c1 * r1 * (a.bestProposedSolutionFar().getVariable(v).getValue().getV() - a.getVariable(v).getValue().getV());
//        // The update of the speed given c2
//        double speed2 = c2 * r2 * (o.get_Best_Solution().getVariable(v).getValue().getV() - a.getVariable(v).getValue().getV());
//        a.getVariable(v).getSta().getSpeed().setV(momentum_Weight * a.getVariable(v).getSta().getSpeed().getV() + speed1 + speed2);
//        // update the values with the new speed
//        a.getVariable(v).getValue().setV(a.getVariable(v).getValue().getV() + a.getVariable(v).getSta().getSpeed().getV());
//    }
//}
