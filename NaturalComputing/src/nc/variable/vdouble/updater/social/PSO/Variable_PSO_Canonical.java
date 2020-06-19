package nc.variable.vdouble.updater.social.PSO;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nc.variable.strategyToUpdateSolution.PSO;
//
//import java.util.concurrent.ThreadLocalRandom;
//import nc.variable.strategyToUpdateSolution.common.StrategyToUpdateVariableV;
//import nc.variable.Real_Value;
//import nc.variable.Variable_Double;
//
///**
// *
// * @author olao
// */
//public class Variable_PSO_Canonical extends StrategyToUpdateVariableV {
//
//    /**
//     * Create a new Variable_PSO_Canonical
//     *
//     * @param speed
//     * @param hyp
//     */
//    public Variable_PSO_Canonical(Real_Value speed, HyperParameters_PSO hyp) {
//        this.speed = speed;
//        this.hyp = hyp;
//    }
//// The hyperparameters of the algorithm
//    protected final HyperParameters_PSO hyp;
//// the speed of this particle
//    protected final Real_Value speed;
//
//    public Real_Value getSpeed() {
//        return speed;
//    }
//
//    public HyperParameters_PSO getHyp() {
//        return hyp;
//    }
//
//    /**
//     * Update the solution using a Swarm movement.To do the Swarm movement, take
//     * the best chromosome so far, the speed of the particle, update.
//     *
//     * @param v
//     */
//    @Override
//    public void update(Variable_Double v) {
//// Weights to move towards best solution so far in the population or to the best solution found by this particular particle
//        double c1 = hyp.getC1();
//        double c2 = hyp.getC2();
//        // the Momentum
//        double momentum_Weight = hyp.getMax_momentum_weight() - s.getO().get_current_iteration()* (hyp.getMax_momentum_weight() - hyp.getMin_momentum_weight()) / hyp.getMaximum_number_iterations();
//        // Throw a random number between 0 and 1
//        double r1 = ThreadLocalRandom.current().nextDouble();
//        double r2 = ThreadLocalRandom.current().nextDouble();
//        // The update of the speed has two elements: the movement towards the best proposed value so far given c1
//        double speed1 = c1 * r1 * (((Variable_Double)s.bestProposedSolutionFar().get(i)).getValue() - v.getValue());
//        // The update of the speed given c2
//        double speed2 = c2 * r2 * (((Variable_Double)s.getO().get_Best_Solution().get(i)).getValue().getV() - v.getValue().getV());
//        getSpeed().setV(momentum_Weight * getSpeed().getV() + speed1 + speed2);
//        // update the values with the new speed
//        v.getValue().setV(v.getValue().getV() + getSpeed().getV());
//    }
//
//    @Override
//    public Variable_PSO_Canonical copy() {
//        return new Variable_PSO_Canonical(speed.copy(), hyp);
//    }
//}
