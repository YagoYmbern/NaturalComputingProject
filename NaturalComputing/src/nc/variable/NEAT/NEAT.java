/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.NEAT;

/**
 *
 * @author Oscar Lao
 */
public class NEAT implements Comparable<NEAT> {

    private final int input_neuron, output_neuron;
    private boolean activated;
    private double weight;

    /**
     *
     * @param input_neuron the source
     * @param output_neuron the output
     * @param activated a boolean variable indicating if the connection is
     * active (input passes its output to output_neuron) or not
     * @param weight the weight between the two neurons (only matters if it is
     * active)
     */
    public NEAT(int input_neuron, int output_neuron, boolean activated, double weight) {
        this.input_neuron = input_neuron;
        this.output_neuron = output_neuron;
        this.activated = activated;
        this.weight = weight;
    }

    /**
     * Set the connection as active or inactive
     *
     * @param activated
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    /**
     * Set the weight
     *
     * @param weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Get the input neuron
     *
     * @return
     */
    public int getInput_neuron() {
        return input_neuron;
    }

    /**
     * Get the output neuron
     *
     * @return
     */
    public int getOutput_neuron() {
        return output_neuron;
    }

    /**
     * Get the weight
     *
     * @return
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Return true if this connection is activated
     *
     * @return
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Compare the NEAT value by input and output neuron.
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(NEAT o) {
        int c_input = Integer.compare(input_neuron, o.getInput_neuron());
        if (c_input == 0) {
            return Integer.compare(output_neuron, o.getOutput_neuron());
        }
        return c_input;
    }

    /**
     * Two objects NEAT are considered equals if the input and output neuron is the same
     * @param o
     * @return 
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof NEAT) {
            NEAT no = (NEAT) o;
            return input_neuron == no.getInput_neuron() && output_neuron == no.getOutput_neuron();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.input_neuron;
        hash = 79 * hash + this.output_neuron;
        return hash;
    }
    
    public NEAT copy()
    {
        return new NEAT(input_neuron, output_neuron, activated, weight);
    }
}
