/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vneuron;

/**
 * a signal using this neuron either as input (it would be a
 * DendriticConnection) or as output (it would be an AxonConnection)
 *
 * @author Oscar Lao
 */
public abstract class SynapticConnection {

    private final Neuron neuron;
    private final VSynapsis v;

    /**
     * Create a new synaptic connection
     *
     * @param neuron will be the input if we are doing a DendriteConnection (we
     * are going backward)
     * @param v the synapsis.
     */
    public SynapticConnection(Neuron neuron, VSynapsis v) {
        this.neuron = neuron;
        this.v = v;
    }

    /**
     * Get the neuron
     *
     * @return
     */
    public Neuron getNeuron() {
        return neuron;
    }

    /**
     * Get the synapsis
     *
     * @return
     */
    public VSynapsis getV() {
        return v;
    }
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
// * a signal using this neuron either as input (it would be a DendriticConnection) or as output (it would be an AxonConnection)
// * @author Oscar Lao
// */
//public class SynapticConnection {
//    
//    private final Neuron neuron;
//    private final VDouble weight;
//    private Activation_Function a;
//    
//    /**
//     * Create a new synaptic connection
//     * @param neuron
//     * @param a
//     * @param weight 
//     */
//    public SynapticConnection(Neuron neuron, Activation_Function a, VDouble weight)
//    {
//        this.weight = weight;
//        this.neuron = neuron;
//        this.a = a;
//    }
//
//    /**
//     * Get the neuron
//     * @return 
//     */
//    public Neuron getNeuron() {
//        return neuron;
//    }
//
//    /**
//     * Get the weight
//     * @return 
//     */
//    public VDouble getWeight() {
//        return weight;
//    }
//
//    public Activation_Function getA() {
//        return a;
//    }
//
//    public void setA(Activation_Function a) {
//        this.a = a;
//    }
//}
