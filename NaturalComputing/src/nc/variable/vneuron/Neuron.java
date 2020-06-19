/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vneuron;

/**
 * The neuron class models the behaviour of a neuron.It gets outputs from
 * incoming neurons through dendrites and generate an output
 *
 * @author Oscar Lao
 * @param <C>
 */
public abstract class Neuron<C extends CellBody> {

    protected double activated_output = 0;
    private final CellBody c;
    // Axonal connections. To which neurons this neuron is the input
    protected final AxonalConnections axonal_connections = new AxonalConnections();
    protected boolean is_activated = false;
    // Indicate if we have to compute the activations
    protected boolean compute_activation = true;
    // Memory of the previous activation (if we are in a recurrent neural network we will use the previous value)
    protected double previous_activation = 0;
    // Are we in training mode? By definition yes, which means that we will add some noise to the output
    protected boolean training_mode = true;

    /**
     * Create a neuron
     *
     * @param c
     */
    public Neuron(C c) {
        this.c = c;
    }

    /**
     * Get the cell body
     *
     * @return
     */
    public C getC() {
        return (C) c;
    }

    /**
     * Are we in training mode? If yes, noise will be added in probability to
     * the neuron. By default, we are in training mode.
     *
     * @param training_mode
     */
    public void setTraining_mode(boolean training_mode) {
        this.training_mode = training_mode;
    }

    /**
     * Add an axon connecting this neuron as input to another neuron as output
     *
     * @param d
     * @return
     */
    public boolean add_axon_connection(AxonConnection d) {
        return axonal_connections.add(d);
    }

    /**
     * Set the activation output of this neuron to 0, the activation value 0 at
     * t = 0, and ask to compute the activation value. All the synapses from
     * dendrites are reset so they have not been activated before
     */
    public void cleanActivationValue() {
        compute_activation = true;
        activated_output = 0;
        previous_activation = 0;
        for (DendriteConnection d : getDendrites()) {
            d.getV().getI().reset();
        }
    }

    /**
     * define the neuron to compute the activation
     */
    public void setNeuronToComputeActivation() {
        compute_activation = true;
    }

    /**
     * Return the dendrite connections (inputs) to this neuron. By default, the
     * list is empty
     *
     * @return
     */
    public DendriteConnections getDendrites() {
        return new DendriteConnections();
    }

    /**
     * Get the connections to which this neuron sends its output
     *
     * @return
     */
    public AxonalConnections getAxons() {
        return axonal_connections;
    }

    /**
     * Is this neuron in a recursion. If this is the case, then it means we have
     * a recursive loop
     *
     * @return
     */
    public boolean is_in_recursion() {
        return is_activated;
    }

    /**
     * Shall we compute the activation of this neuron
     *
     * @return
     */
    public boolean isCompute_activation() {
        return compute_activation;
    }

    /**
     * Get the previous activation of this neuron
     *
     * @return
     */
    public double getPrevious_activation() {
        return previous_activation;
    }

    /**
     * Check that there is a path from this neuron to reach the output.
     * Otherwise, it means that this neuron is in an evolutionary dead-end road
     *
     * @return
     */
    public boolean reachesOutput() {
        is_activated = true;
        // By default, a neuron does not reaches the output before testing it
        boolean reaches_output = false;
        // for each dendrite connected to this output neuron, compute the activation from the inputs
        for (AxonConnection ax : axonal_connections) {
            boolean goes_to_output;
            if (ax.getNeuron().is_in_recursion()) {
                // We are in a loop. Take the value at t-1 of the neuron
                goes_to_output = false;
            } else {
                goes_to_output = ax.getNeuron().reachesOutput();
            }
            // As far as there is at least one path thar reaches an output, the information from the neuron reaches the output
            reaches_output = reaches_output || goes_to_output;
        }
        // Return the activation
        is_activated = false;
        return reaches_output;
    }

    /**
     * Return true if the neuron has dendrites and all its descendents have also
     * dendrites (or if it is an input neuron) or false otherwise
     *
     * @return
     */
    public abstract boolean reachesInputNeuron();

    /**
     * Get the sum of inputs of this neuron
     *
     * @return
     */
    public abstract double getSumOfInputs();
}
