/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vneuron;

import java.util.Objects;
import nc.neurocomputing.activation.Activation_Function;
import nc.neurocomputing.activation.Activation_Generalized;

/**
 * A synapsis encodes a real number (the weight) between two neurons: input and
 * output. It also contains the activation function that is going to be used to
 * pass the input from the input_neuron to the output
 *
 * @author Oscar Lao
 */
public class Synapsis {

    private final CellBody input_neuron;
    private final CellBodyHidden output_neuron;
    private Activation_Generalized af;
    private boolean synapsis_has_been_activated = false;
// This variable will be used for weighting the activation function of new synapses. At the begining, the activation return will be close to 0, so the synapsis is accepted    
//    private int time_of_creation;
//// This variable will be used for weighting the activation function of a synapsis that is set to degenerate. At the begining, the activation return will be close to the one without degeneration
//// After several generations, the synapsis will have a value of activation close to 0 and will be removed    
//    private int time_when_decided_to_degenerate;

    /**
     * Create a new connector between two neurons with the weight between the
     * input neuron and the output neuron. This is the object that is stored as
     * vector in the chromosome. The parameters we want to estimate of the
     * synapsis are related to the generalized ReLU
     *
     * @param input_neuron
     * @param output_neuron
     * @param af the activation function between the two neurons
     */
    public Synapsis(CellBody input_neuron, CellBodyHidden output_neuron, Activation_Generalized af) {
        this.input_neuron = input_neuron;
        this.output_neuron = output_neuron;
        this.af = af;
//        time_of_creation = 0;
//        time_when_decided_to_degenerate = -1;
    }

    /**
     * Set the activation function of this connection
     * @param af 
     */
    public void setAf(Activation_Generalized af) {
        this.af = af;
    }

    /**
     * Get the associated activation generalized of this synapsis
     * @return 
     */
    public Activation_Generalized getAf() {
        return af;
    }       
    
    /**
     * Check that the synapsis has been activated
     * @return 
     */
    public boolean isSynapsis_has_been_activated() {
        return synapsis_has_been_activated;
    }
    
    /**
     * Reset the synapsis so it loses memory of having being activated 
     */
    public void reset()
    {
        synapsis_has_been_activated = false;
    }

    /**
     * Get the neuron that connects to the output through a weight
     *
     * @return
     */
    public CellBody getInput_neuron() {
        return input_neuron;
    }

    /**
     * Get the neuron that receives from the input given a weight connection
     *
     * @return
     */
    public CellBodyHidden getOutput_neuron() {
        return output_neuron;
    }

    /**
     * Get the activation of the value v using the associated activation
     * function. Once called, the synapsis is set to have been activated.
     * @param v
     * @return
     */
    public double getActivationV(double v) {
        double a = af.activate(v);
        synapsis_has_been_activated = true;
        return a;
    }    
   
    /**
     * Copy the activation function
     * @return 
     */
    public Activation_Generalized copy_Activation_Function()
    {
        return (Activation_Generalized)af.copy();
    }
    

    /**
     * Update the parameters of the activation function associated to this
     * synapsis. If it was degenerating, then set the neuron active again
     */
    public void update() {
        af.updateParameters();
    }

    /**
     * Generate a new copy of this Synapsis
     *
     * @return
     */
    public Synapsis copy() {
        Synapsis new_s = new Synapsis((CellBody) getInput_neuron().copy(), (CellBodyHidden)getOutput_neuron().copy(), copy_Activation_Function());
        return new_s;
    }

    /**
     * Two synapses equals if they have the same input and output neuron
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Synapsis) {
            Synapsis so = (Synapsis) o;
            return so.getInput_neuron().equals(this.getInput_neuron()) && so.getOutput_neuron().equals(this.getOutput_neuron());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.input_neuron);
        hash = 29 * hash + Objects.hashCode(this.output_neuron);
        return hash;
    }

    @Override
    public String toString() {
        return input_neuron.toString() + " " + output_neuron.toString() + " " + af.toString();
    }

}
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nc.variable.vneuron;
//
//import nc.neurocomputing.activation.Activation_Generalized_Elliot;
//import nc.variable.vdouble.VDouble;
//
///**
// * A synapsis encodes a real number (the weight) between two neurons: input and
// * output. It also contains the activation function that is going to be used to pass the input from the input_neuron to the output
// *
// * @author Oscar Lao
// */
//public class Synapsis {
//
//    private final CellBody input_neuron, output_neuron;
//    private final VDouble weight;
//    private final Activation_Generalized_Elliot af;
//
//    /**
//     * Create a new connector between two neurons with the weight between the
//     * input neuron and the output neuron.This is the object that is stored as
// vector in the chromosome
//     *
//     * @param input_neuron
//     * @param output_neuron
//     * @param af the activation function between the two neurons
//     * @param weight
//     */
//    public Synapsis(CellBody input_neuron, CellBody output_neuron, Activation_Generalized_Elliot af, VDouble weight) {
//        this.weight = weight;
//        this.input_neuron = input_neuron;
//        this.output_neuron = output_neuron;
//        this.af = af;
//    }
//
//    /**
//     * Get the neuron that connects to the output through a weight
//     *
//     * @return
//     */
//    public CellBody getInput_neuron() {
//        return input_neuron;
//    }
//
//    /**
//     * Get the neuron that receives from the input given a weight connection
//     *
//     * @return
//     */
//    public CellBody getOutput_neuron() {
//        return output_neuron;
//    }
//
//    /**
//     * Get the activation function
//     * @return 
//     */
//    public Activation_Generalized_Elliot getAf() {
//        return af;
//    }
//
//    /**
//     * Get the variable with the weight
//     * @return 
//     */
//    public VDouble getWeight() {
//        return weight;
//    }
//
//    /**
//     * Generate a new copy of this Synapsis
//     *
//     * @return
//     */
//    public Synapsis copy() {
//        return new Synapsis(getInput_neuron().copy(), getOutput_neuron().copy(), af, (VDouble)weight.copy());
//    }
//}
