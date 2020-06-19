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
public class NeuronInput extends Neuron<CellBodyInput> {

    /**
     * Create an empty neuron input
     *
     * @param c
     */
    public NeuronInput(CellBodyInput c) {
        super(c);
    }

    /**
     * The neuron input has by default a value determined by the input.
     *
     * @param c
     * @param input
     */
    public NeuronInput(CellBodyInput c, double input) {
        super(c);
        setInput(input);
    }

    /**
     * Set the input value
     *
     * @param input
     */
    public void setInput(double input) {
// If we are in training mode, add in probability some noise        
        if (training_mode) {
            activated_output = (ThreadLocalRandom.current().nextDouble() < 0.05) ? input + 0.01 * ThreadLocalRandom.current().nextGaussian() : input;
        } else {
            activated_output = input;
        }
    }

    @Override
    public double getSumOfInputs() {
        return activated_output;// + 0.1*ThreadLocalRandom.current().nextGaussian();
    }

    @Override
    public boolean reachesInputNeuron() {
        return true;
    }
}
