/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.neurocomputing.activation;

/**
 *
 * @author olao
 */
public abstract class Activation_Function {

    /**
     * Activate the value v with the ActivationFunction
     *
     * @param v
     * @return
     */
    public abstract double activate(double v);

    /**
     * Update the parameters that are associated to this activation function
     */
    public abstract void updateParameters();

    /**
     * Copy this activation function
     *
     * @return
     */
    public abstract Activation_Function copy();

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
