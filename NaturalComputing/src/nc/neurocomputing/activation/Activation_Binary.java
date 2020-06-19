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
public class Activation_Binary extends Activation_Function{

    @Override
    public double activate(double v) {
        return(v<0)?-1:1;
    }

    @Override
    public Activation_Function copy() {
        return new Activation_Binary();
    }

    @Override
    public void updateParameters() {
    }
}
