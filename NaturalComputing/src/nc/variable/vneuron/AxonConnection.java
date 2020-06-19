/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vneuron;

/**
 *
 * @author Oscar Lao
 */
public class AxonConnection extends SynapticConnection{
    
    
    /**
     * Create a Axon that sends output signals from neuron at weight and being activated with a
     * @param neuron
     * @param v
     */
    public AxonConnection(Neuron neuron, VSynapsis v)
    {
        super(neuron, v);
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
// *
// * @author Oscar Lao
// */
//public class AxonConnection extends SynapticConnection{
//    
//    
//    /**
//     * Create a Axon that sends output signals from neuron at weight and being activated with a
//     * @param neuron
//     * @param a
//     * @param weight 
//     */
//    public AxonConnection(Neuron neuron, Activation_Function a, VDouble weight)
//    {
//        super(neuron, a, weight);
//    }       
//}
