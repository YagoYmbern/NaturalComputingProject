/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vneuron;

import nc.variable.V;
import nc.variable.strategyToUpdateSolution.common.StrategyToUpdateV;

/**
 *
 * @author Oscar Lao
 */
public class VSynapsisUpdater extends StrategyToUpdateV<Synapsis> {

    /**
     * Time to update the parameters of the activation function or the weights
     * of the synapsis. We can update the bias of the input neuron, the bias of
     * the output neuron and the weight
     *
     * @param v
     */
    @Override
    public void update(V<Synapsis> v) {
        v.getI().update();
    }

    @Override
    public VSynapsisUpdater copy() {
        return new VSynapsisUpdater();
    }
}

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nc.variable.vneuron;
//
//import java.util.concurrent.ThreadLocalRandom;
//import nc.variable.V;
//import nc.variable.strategyToUpdateSolution.common.StrategyToUpdateV;
//
///**
// *
// * @author Oscar Lao
// */
//public class VSynapsisUpdater extends StrategyToUpdateV<Synapsis> {
//
//    /**
//     * Time to update the parameters of the activation function or the weights
//     * of the synapsis. We can update the bias of the input neuron, the bias of
//     * the output neuron and the weight
//     *
//     * @param v
//     */
//    @Override
//    public void update(V<Synapsis> v) {
//        // What shall we update? The bias of neuron input, the bias of neuron output or the weight?
//        double p = ThreadLocalRandom.current().nextDouble();
//        if (p <= 1 / 2) {
//            // Update the parameters of the activation function
//            v.getI().getAf().updateParameters();
//        } else {
//            // Update the weight
//            v.getI().getWeight().apply_update();
//        }
//    }
//
//    @Override
//    public VSynapsisUpdater copy() {
//        return new VSynapsisUpdater();
//    }
//}
