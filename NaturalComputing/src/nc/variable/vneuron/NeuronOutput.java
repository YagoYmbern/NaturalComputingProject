/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vneuron;

import nc.neurocomputing.activation.Activation_Function;

/**
 * Defines a type of neuron that is output
 * @author Oscar Lao
 */
public class NeuronOutput extends NeuronHidden{
    
    private final Activation_Function af;
    
    public NeuronOutput(CellBodyOutput c, Activation_Function af) {
        super(c);
        this.af = af;
    }            
    
    /**
     * Return the activation of the Neuron Output
     * @return 
     */
    public double getActivationOutput()
    {
        return af.activate(getSumOfInputs());
    }

    /**
     * Get the activation function
     * @return 
     */
    public Activation_Function getAf() {
        return af;
    }
    
    /**
     * An output neuron always reaches output
     * @return
     */
    @Override
    public boolean reachesOutput() {
        return true;
    }    
}
