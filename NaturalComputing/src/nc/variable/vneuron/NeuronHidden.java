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
public class NeuronHidden extends Neuron<CellBodyHidden> {

    private final DendriteConnections get_input_from = new DendriteConnections();

    /**
     * Create a Hidden neuron.The input (dendrite connections) comes from other
     * neurons
     *
     * @param c
     */
    public NeuronHidden(CellBodyHidden c) {
        super(c);
    }

    /**
     * Add a new input neuron connected to this neuron. Add to the input neuron
     * a new axon with output this neuron
     *
     * @param d
     * @return
     */
    public boolean add_dendrite_connection(DendriteConnection d) {
        // The output from the neuron in d is this neuron
        d.getNeuron().add_axon_connection(new AxonConnection(this, d.getV()));
        return get_input_from.add(d);
    }

    /**
     * Get the dendrites associated to this neuron
     *
     * @return
     */
    @Override
    public DendriteConnections getDendrites() {
        return get_input_from;
    }

//    /**
//     * Add an axon connection to send the output of this neuron to another neuron
//     * @param a
//     * @return
//     */
//    public boolean add_axon_connection(AxonConnection a)
//    {
//        return send_output_to.add(a);
//    }
    /**
     * Get the sum of input activations associated to this neuron. Recall that
     * the activation function is not in the Neuron, but in the synapsis. This
     * means that the Neuron is just the container of the sum of the activated
     * outputs, but it does NOT returns an activation.
     *
     * @return
     */
    @Override
    public double getSumOfInputs() {
        // Only re-compute everything if there is need for it
        if (compute_activation) {
            is_activated = true;
            double activation = 0;
            // for each dendrite connected to this output neuron, compute the activation from the inputs
            for (DendriteConnection d : get_input_from) {
                // If we are attempting to get the activation value of a neuron that has asked for our value, it means that we are in a recursion
                if (d.getNeuron().is_in_recursion()) {
                    // We are in a loop. Take the value at t-1 of the neuron
                    activation += d.activate(d.getNeuron().getPrevious_activation());
                } else {
                    double g = d.getNeuron().getSumOfInputs();
                    // Add the output from the dendrites
                    activation += d.activate(g);                    
                }
            }
            // If we are in training mode, add in probability some noise        
//            if (training_mode) {
//                activated_output = activation+(((0.1*ThreadLocalRandom.current().nextDouble()+0.1)*activation)* (2*ThreadLocalRandom.current().nextInt(2)-1));                
////                activated_output = (ThreadLocalRandom.current().nextDouble() < 0.1) ? activation+((0.1*activation)* (2*ThreadLocalRandom.current().nextInt(2)-1)) : activation;
//            } else {
                activated_output = activation;
//            }
            // Update the previout activation with the new activation value
            previous_activation = activated_output;
            // Return the activation
            is_activated = false;
            // we do not have to compute the activation of this neuron
            compute_activation = false;
        }
//        if(ThreadLocalRandom.current().nextDouble() < 0.01)
//        {
//            return 0;
//        }
        return activated_output;
    }

    /**
     * Check if there is a path that bring this neuron to a, at least, an input
     * neuron
     *
     * @return
     */
    @Override
    public boolean reachesInputNeuron() {
        // If at least one of the dendrite connections reaches the input cells, then return true
        // If at least one of the dendrites (or descendent in backward) reaches the input, then return true
        // If it has dendrites and all of them reach the input neurons, then return true

        return get_input_from.stream().anyMatch((d) -> (d.getNeuron().reachesInputNeuron()));
    }
}

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nc.variable.vneuron;
//
///**
// *
// * @author Oscar Lao
// */
//public class NeuronHidden extends Neuron {
//
//    private final DendriteConnections get_input_from = new DendriteConnections();
//    // Is this a recursive neuron?
//    private boolean is_recursive = false;
//
//    /**
//     * Create a Hidden neuron.The input (dendrite connections) comes from other
//     * neurons
//     *
//     * @param c
//     */
//    public NeuronHidden(CellBody c) {
//        super(c);
//    }
//
//    /**
//     * Add a new input neuron connected to this neuron. Add to the input neuron
//     * a new axon with output this neuron
//     *
//     * @param d
//     * @return
//     */
//    public boolean add_dendrite_connection(DendriteConnection d) {
//        d.getNeuron().add_axon_connection(new AxonConnection(this, d.getA(), d.getWeight()));
//        return get_input_from.add(d);
//    }
//
//    /**
//     * Get the dendrites associated to this neuron
//     *
//     * @return
//     */
//    @Override
//    public DendriteConnections getDendrites() {
//        return get_input_from;
//    }
//
////    /**
////     * Add an axon connection to send the output of this neuron to another neuron
////     * @param a
////     * @return
////     */
////    public boolean add_axon_connection(AxonConnection a)
////    {
////        return send_output_to.add(a);
////    }
//    /**
//     * Get the activation value of this neuron
//     *
//     * @return
//     */
//    @Override
//    public double getActivationValue() {
//        // Only re-compute everything if there is need for it
//        if (compute_activation) {
//            is_activated = true;
//            double activation = 0;
//            for (DendriteConnection d : get_input_from) {
//                // If we are attempting to get the activation value of a neuron that has asked for our value, it means that we are in a recursion
//                if (d.getNeuron().is_in_recursion()) {
//                    // We are in a loop. Take the value at t-1 of the neuron
//                    activation += d.getNeuron().getPrevious_activation();
//                } else {
//                    activation += d.getNeuron().getActivationValue() * d.getWeight().getI().getD();
//                }
//            }
//            activated_output = activation;
//            // Update the previout activation with the new activation value
//            previous_activation = activated_output;
//            // Return the activation
//            is_activated = false;
//            // we do not have to compute the activation of this neuron
//            compute_activation = false;
//        }
//        return activated_output;
//    }
//}
