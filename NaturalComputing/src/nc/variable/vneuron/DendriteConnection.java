/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vneuron;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Oscar Lao
 */
public class DendriteConnection extends SynapticConnection {
//// The statistics of the activation
//    private double meanDendrite = 0;
//    private double meanDendrite2 = 0;
//    private double meanDendrite_by_Neuron = 0;
//    private int n = 0;
    
    /**
     * Create a Dendrite that gets inputs from neuron at weight and the input
     * neuron is activated using function a
     *
     * @param neuron
     * @param v
     */
    public DendriteConnection(Neuron neuron, VSynapsis v) {
        super(neuron, v);
    }

    /**
     * Activate the input value with the activation that is in this dendrite
     * @param v
     * @return 
     */
    public double activate(double v) {
//        if(ThreadLocalRandom.current().nextDouble() < 0.01)
//        {
//            return 0;
//        }
        double activated = getV().getI().getActivationV(v);
//        meanDendrite+=activated;
//        meanDendrite2+=Math.pow(activated,2);
//        n++;
        return activated;
    }
    
//    /**
//     * Get the standard deviation of the activations observed in this dendrite
//     * @return 
//     */
//    public double get_sd()
//    {
//        return Math.pow(meanDendrite2/n - Math.pow(meanDendrite/n,2),0.5);
//    }
//    
//    /**
//     * Get the mean of the activations of the dendrite
//     * @return 
//     */
//    public double get_mean()
//    {
//        return meanDendrite/n;
//    }
}

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nc.variable.vneuron;
//
//import nc.neurocomputing.activation.Activation_Function;
//import nc.variable.vdouble.VDouble;
//
///**
// *
// * @author Oscar Lao
// */
//public class DendriteConnection extends SynapticConnection {
//
//    /**
//     * Create a Dendrite that gets inputs from neuron at weight and the input
//     * neuron is activated using function a
//     *
//     * @param neuron
//     * @param a
//     * @param weight
//     */
//    public DendriteConnection(Neuron neuron, Activation_Function a, VDouble weight) {
//        super(neuron, a, weight);
//    }
//}
